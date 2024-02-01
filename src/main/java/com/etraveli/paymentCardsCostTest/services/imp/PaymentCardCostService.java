package com.etraveli.paymentCardsCostTest.services.imp;

import com.etraveli.paymentCardsCostTest.apiCall.IApiCallService;
import com.etraveli.paymentCardsCostTest.dto.CountryCostMatrixDto;
import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostDto;
import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostResponseDto;
import com.etraveli.paymentCardsCostTest.exceptions.CountryCostMatrixNotFoundException;
import com.etraveli.paymentCardsCostTest.exceptions.PaymentCardCostCalculationUnProcessAbleContentException;
import com.etraveli.paymentCardsCostTest.services.ICountryCostMatrixService;
import com.etraveli.paymentCardsCostTest.services.IPaymentCardCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentCardCostService implements IPaymentCardCostService {

    private IApiCallService apiCallService;
    private ICountryCostMatrixService countryCostMatrixService;

    @Autowired
    public PaymentCardCostService(IApiCallService apiCallService, ICountryCostMatrixService countryCostMatrixService) {
        this.apiCallService = apiCallService;
        this.countryCostMatrixService = countryCostMatrixService;
    }
    @Override
    public PaymentCardCostResponseDto calculatePaymentCardCost(PaymentCardCostDto paymentCardCostDto) {
        try {
            String country = this.apiCallService.getCountryByIin(paymentCardCostDto.getIin());
            CountryCostMatrixDto countryCostMatrixDto = this.countryCostMatrixService.getCountryCostMatrixByCountry(country);
            if (countryCostMatrixDto == null) throw new CountryCostMatrixNotFoundException("Unable to retrieve " +
                    "cost for the specified IIN: " + paymentCardCostDto.getIin());
            double costIin = countryCostMatrixDto.getCostUSD();
            PaymentCardCostResponseDto paymentCardCostResponseDto = new PaymentCardCostResponseDto();
            paymentCardCostResponseDto.setCountry(country);
            paymentCardCostResponseDto.setCost(costIin);
            return paymentCardCostResponseDto;
        } catch( CountryCostMatrixNotFoundException e ) {
            throw new CountryCostMatrixNotFoundException("Unable to retrieve " +
                    "cost for the specified IIN, more info: " + e.getMessage());
        } catch( Exception e ) {
            System.out.println("Error: " + e.getMessage()); //TODO: Change it for logger
            throw new PaymentCardCostCalculationUnProcessAbleContentException("Unable to retrieve " +
                "cost for the specified IIN");

        }

    }

}
