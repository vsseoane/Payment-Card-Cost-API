package com.etraveli.paymentCardsCostTest.dto.BinTableApiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BinTableApiResponse {

    private int result;
    private String message;
    private BinTableData data;
}