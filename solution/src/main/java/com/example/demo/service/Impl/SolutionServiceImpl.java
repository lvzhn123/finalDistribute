package com.example.demo.service.Impl;

import com.example.demo.dto.FinalPoolDTO;
import com.example.demo.service.SolutionService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * author lvzhouneng
 */
@Service
public class SolutionServiceImpl implements SolutionService {
    /**
     * 资金支出服务计算方法
     * @param finalPools
     * @param spendAmt
     * @return
     */
    @Override
    public List<FinalPoolDTO> calculateFinalDistribute(List<FinalPoolDTO> finalPools, Double spendAmt) {

        if(null == finalPools || finalPools.size() == 0){
            return finalPools;
        }
        Double avgAfter = calculateAvgAfterSpend(finalPools, spendAmt);
        //需要处理的集合
        List<FinalPoolDTO> dealList = new ArrayList<>();
        //相对平均值应该减去的数值
        List<Double> needMinus = new ArrayList<>();
        finalPools.stream().filter(dto -> {
            //大于均值才需处理
            return dto.getPreAllocationShotfalls() > avgAfter;
        }).forEach(dto -> {
            dealList.add(dto);
            needMinus.add(dto.getPreAllocationShotfalls() - avgAfter);
        });
        //不需要处理的集合
        finalPools.removeAll(dealList);
        List<FinalPoolDTO> undealList = finalPools;
        undealList.stream().forEach(dto->{
            dto.setAfterAllocationShotfalls(dto.getPreAllocationShotfalls());
        });
        //若没有需要处理的集合，则直接返回
        if(dealList.size() == 0){
            undealList.sort(Comparator.comparing(FinalPoolDTO::getFundPoolId));
            return undealList;
        }
        //一共需减掉这么多才可以让这部分方差为0
        Double needMinusAll = needMinus.stream().reduce(0d, (acc, value) -> acc + value);

        //减去后距离avg的距离之和
        Double distanceToavgAll = needMinusAll - spendAmt;
        for(int i = 0; i< dealList.size();i++){
            FinalPoolDTO dto = dealList.get(i);
            Double needMinusR = needMinus.get(i) - distanceToavgAll / (dealList.size() - i);

            //若需要增加值则设为0
            if(needMinusR < 0d){
                needMinusR = 0d;
            }
            if(needMinusR > spendAmt){
                needMinusR = spendAmt;
            }

            dto.setAllocationShotfalls(formatValue(needMinusR));
            dto.setAfterAllocationShotfalls(formatValue(dto.getPreAllocationShotfalls() - needMinusR));
            needMinusAll = needMinusAll  - needMinus.get(i);
            distanceToavgAll = needMinusAll - (spendAmt - needMinusR);
            spendAmt = spendAmt - needMinusR;
        }

        dealList.addAll(undealList);
        //按照资金池序号排序
        dealList.sort(Comparator.comparing(FinalPoolDTO::getFundPoolId));
        return dealList;
    }

    private Double formatValue(Double value){
        if(null == value){
            return 0d;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(value));
    }

    /**
     * 计算支出后平均值
     * @param finalPools
     * @param spendAmt
     * @return
     */
    private Double calculateAvgAfterSpend(List<FinalPoolDTO> finalPools, Double spendAmt) {
        Double sum = 0d;
        for (FinalPoolDTO dto : finalPools) {
            if(null == dto.getPreAllocationShotfalls()) {
                dto.setPreAllocationShotfalls(0d);
            }

            sum += dto.getPreAllocationShotfalls();
        }
        return (sum - spendAmt) / finalPools.size();
    }

}
