package com.etraveli.paymentCardsCostTest.apiCall;

import java.util.logging.Logger;

import com.etraveli.paymentCardsCostTest.dto.BinTableApiResponse.BinTableApiResponse;
import com.etraveli.paymentCardsCostTest.exceptions.PaymentCardCostCalculationTooManyRequestsException;
import com.etraveli.paymentCardsCostTest.exceptions.PaymentCardCostCalculationUnProcessAbleContentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiCallService implements IApiCallService {

    @Value("${binTableApi.baseUrl}")
    private String baseUrl;

    private static final Logger logger = Logger.getLogger(ApiCallService.class.getName());


    private RestTemplate restTemplate;

    public ApiCallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCountryByIin(String iin) {
        try {
            String apiUrl = baseUrl + "/" + iin;
            ResponseEntity<BinTableApiResponse> response = restTemplate.getForEntity(apiUrl, BinTableApiResponse.class);
            response.getBody();
            if (response.getBody() == null)
                throw new PaymentCardCostCalculationUnProcessAbleContentException("The requested resource for iin: " + iin +
                        " could not be found");

            return response.getBody().getCountry().getAlpha2();
        } catch (Exception e) {
            String errorMessage = "The requested resource for iin: " + iin + " could not be found" ;
            logger.severe(errorMessage + " More info: " + e.getMessage());
            if (e instanceof HttpClientErrorException.TooManyRequests) {
                throw new PaymentCardCostCalculationTooManyRequestsException("The requested resource for iin: " + iin +
                        " could not be processed. Please wait a moment and try again ");
            }
            throw new PaymentCardCostCalculationUnProcessAbleContentException(errorMessage);
        }

    }

}
