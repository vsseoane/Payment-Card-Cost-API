package com.etraveli.paymentCardsCostTest.dto.BinTableApiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    private String name;
    private String code;
    private String flag;
    private String currency;
    private String currency_code;
}
