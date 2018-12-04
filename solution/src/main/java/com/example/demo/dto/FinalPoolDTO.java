package com.example.demo.dto;

/**
 * 资金池DTO
 */
public class FinalPoolDTO {

    private int fundPoolId;

    private Double preAllocationShotfalls;

    private Double allocationShotfalls;

    private Double afterAllocationShotfalls;

    public int getFundPoolId() {
        return fundPoolId;
    }

    public void setFundPoolId(int fundPoolId) {
        this.fundPoolId = fundPoolId;
    }

    public Double getPreAllocationShotfalls() {
        return preAllocationShotfalls;
    }

    public void setPreAllocationShotfalls(Double preAllocationShotfalls) {
        this.preAllocationShotfalls = preAllocationShotfalls;
    }

    public Double getAllocationShotfalls() {
        return allocationShotfalls;
    }

    public void setAllocationShotfalls(Double allocationShotfalls) {
        this.allocationShotfalls = allocationShotfalls;
    }

    public Double getAfterAllocationShotfalls() {
        return afterAllocationShotfalls;
    }

    public void setAfterAllocationShotfalls(Double afterAllocationShotfalls) {
        this.afterAllocationShotfalls = afterAllocationShotfalls;
    }

}
