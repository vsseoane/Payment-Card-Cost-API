package com.etraveli.paymentCardsCostTest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCardCostResponseDto {
    private String country;
    private Double cost;
}
