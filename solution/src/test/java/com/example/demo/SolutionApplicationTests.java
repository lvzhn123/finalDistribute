package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.base.util.PostUtil;
import com.example.demo.dto.FinalPoolDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolutionApplicationTests {

    /**
     * 测试资金池支出服务
     */
    @Test
    public void test() {
        List<FinalPoolDTO> finalPools = new ArrayList<>();
        //资金池原有金额
        List<Double> finalList = Arrays.asList(20d, 47d, 21d, 41d, 29d, 9d, 76d, 97d, 29d, 66d, 60d, 92d, 94d, 27d, 43d, 75d, 74d, 41d, 61d, 64d);
        //导出金额
        Double spendAmt = 1000d;
        for (int i = 0; i < finalList.size(); i++) {
            FinalPoolDTO temp = new FinalPoolDTO();
            temp.setFundPoolId(i + 1);
            temp.setPreAllocationShotfalls(finalList.get(i));
            temp.setAllocationShotfalls(0d);
            temp.setAfterAllocationShotfalls(0d);
            finalPools.add(temp);
        }
        String finalPoolsStr = JSONArray.toJSONString(finalPools);
        String result = PostUtil.doPostJson("http://localhost:8080/solution/calculateFinalDistribute?spendAmt=" + spendAmt, finalPoolsStr);
        System.out.println("-------------------------------------------");
        System.out.println("资金池支出服务返回结果如下：");
        System.out.println(result);
        System.out.println("-------------------------------------------");
    }

}
