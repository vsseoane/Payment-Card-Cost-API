package com.etraveli.paymentCardsCostTest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCardCostRequestDto {

    @NotBlank(message = "PAN cannot be blank")
    @Pattern(regexp = "^[0-9]{8,19}$", message = "Invalid PAN format. It must be an 8 to 19 digit number.")
    private String pan;
}
