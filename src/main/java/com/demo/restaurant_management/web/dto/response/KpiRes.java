package com.demo.restaurant_management.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiRes {
    private float revenueFirstSpin;
    private float revenueSecondSpin;
    private float revenueThirdSpin;
    private float revenuePredict;
    private float revenueNow;
}
