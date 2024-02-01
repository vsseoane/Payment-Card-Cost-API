package com.etraveli.paymentCardsCostTest.services;

import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostDto;
import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostResponseDto;

public interface IPaymentCardCostService {
    PaymentCardCostResponseDto calculatePaymentCardCost(PaymentCardCostDto paymentCardCostDto) ;
}
