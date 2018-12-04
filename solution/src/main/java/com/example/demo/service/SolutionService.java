package com.example.demo.service;

import com.example.demo.dto.FinalPoolDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SolutionService {

    List<FinalPoolDTO> calculateFinalDistribute(List<FinalPoolDTO> finalPools, Double spendAmt);
}
