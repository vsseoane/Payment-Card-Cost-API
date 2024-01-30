package com.etraveli.paymentCardsCostTest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryCostMatrixDto {

    private long id;
    private  String country;
    private double costUSD;
}
