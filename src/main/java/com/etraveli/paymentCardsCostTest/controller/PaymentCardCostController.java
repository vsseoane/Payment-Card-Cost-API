package com.etraveli.paymentCardsCostTest.controller;


import com.etraveli.paymentCardsCostTest.dto.CountryCostMatrixDto;
import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostDto;
import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostRequestDto;
import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostResponseDto;
import com.etraveli.paymentCardsCostTest.services.IPaymentCardCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class PaymentCardCostController {

    IPaymentCardCostService paymentCardCostService;


    @Autowired
    public PaymentCardCostController(IPaymentCardCostService paymentCardCostService) {
        this.paymentCardCostService = paymentCardCostService;

    }
    @PostMapping("payment-cards-cost")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentCardCostResponseDto> calculatePaymentCardCost(@RequestBody PaymentCardCostRequestDto paymentCardCostRequestDto) {

        PaymentCardCostDto paymentCardCostDto = new PaymentCardCostDto();
        paymentCardCostDto.setIin(paymentCardCostRequestDto.getPan().substring(0,5)); //TODO: validate size
        return new ResponseEntity<>(this.paymentCardCostService.calculatePaymentCardCost(paymentCardCostDto), HttpStatus.OK);
    }





}
