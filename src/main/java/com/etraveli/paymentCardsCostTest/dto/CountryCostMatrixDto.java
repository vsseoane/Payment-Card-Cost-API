package com.etraveli.paymentCardsCostTest.dto;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "Country cannot be null")
    @NotBlank(message = "Country cannot be blank")
    @Pattern(regexp = "^[A-Za-z]{2}$", message = "Invalid country code format, it should be iso2-code")
    private  String country;

    @NotNull(message = "Cost cannot be null")
    @Positive(message = "The cost must be greater than 0")
    private double costUSD;
}
