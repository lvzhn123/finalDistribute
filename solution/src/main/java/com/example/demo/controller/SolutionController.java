package com.example.demo.controller;

import com.example.demo.controller.vo.CommonListVO;
import com.example.demo.controller.vo.CommonVO;
import com.example.demo.dto.FinalPoolDTO;
import com.example.demo.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@RestController
@RequestMapping(value = "/solution")
public class SolutionController extends WebMvcConfigurerAdapter {

    @Autowired
    private SolutionService solutionService;

    @RequestMapping(value = "/calculateFinalDistribute", method = RequestMethod.POST)
    CommonListVO<FinalPoolDTO> calculateFinalDistribute(@RequestBody List<FinalPoolDTO> finalPool, @RequestParam(value = "spendAmt") Double spendAmt) throws Exception{
        if(spendAmt == null || spendAmt < 0){
            throw new Exception("支出金额不能小于0!");
        }
        List<FinalPoolDTO> resultFinalPool = solutionService.calculateFinalDistribute(finalPool, spendAmt);
        CommonListVO<FinalPoolDTO> vo = new CommonListVO<FinalPoolDTO>();
        vo.setList(resultFinalPool);
        vo.setResultMsg("success");
        return vo;
    }


    @ExceptionHandler(Exception.class)
    public CommonVO exceptionHandler(Exception e) {
        CommonVO vo = new CommonVO();
        vo.setResultCode(-1);
        vo.setResultMsg("系统调用异常，异常原因：" + e.getMessage());
        return vo;
    }

}
