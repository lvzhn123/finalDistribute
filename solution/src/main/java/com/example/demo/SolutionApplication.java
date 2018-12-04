package com.example.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.uber.jaeger.metrics.Metrics;
import com.uber.jaeger.metrics.NoopMetricsFactory;
import com.uber.jaeger.propagation.b3.B3TextMapCodec;
import com.uber.jaeger.reporters.RemoteReporter;
import com.uber.jaeger.reporters.Reporter;
import com.uber.jaeger.samplers.ConstSampler;
import com.uber.jaeger.senders.HttpSender;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

@SpringBootApplication
@ComponentScan("com,example.demo")
@Configuration
@EnableAutoConfiguration
public class SolutionApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(SolutionApplication.class, args);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();

        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, // 保留空的字段
                SerializerFeature.WriteNullStringAsEmpty, // String null -> ""
                SerializerFeature.WriteNullNumberAsZero);// Number null -> 0
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);
    }

    @Bean
    public Tracer jaegerTracer() {

        Reporter reporter = new RemoteReporter.Builder().withFlushInterval(10)
                .withMaxQueueSize(65000)
                .withSender(new HttpSender("http://localhost:8080/solution/"))
                .withMetrics(new Metrics(new NoopMetricsFactory()))
                .build();

        com.uber.jaeger.Tracer.Builder builder = new com.uber.jaeger.Tracer.Builder("service-c")
                .withReporter(reporter)
                .withSampler(new ConstSampler(true))
                .registerInjector(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec())
                .registerExtractor(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec());

        return builder.build();

    }
}