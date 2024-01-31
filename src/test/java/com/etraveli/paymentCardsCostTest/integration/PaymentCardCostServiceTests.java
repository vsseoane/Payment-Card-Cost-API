package com.etraveli.paymentCardsCostTest.integration;

import com.etraveli.paymentCardsCostTest.apiCall.ApiCallService;
import com.etraveli.paymentCardsCostTest.apiCall.IApiCallService;
import com.etraveli.paymentCardsCostTest.dto.BinTableApiResponse.BinTableApiResponse;
import com.etraveli.paymentCardsCostTest.dto.BinTableApiResponse.Country;
import com.etraveli.paymentCardsCostTest.dto.CountryCostMatrixDto;
import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostDto;
import com.etraveli.paymentCardsCostTest.dto.PaymentCardCostResponseDto;
import com.etraveli.paymentCardsCostTest.exceptions.CountryCostMatrixNotFoundException;
import com.etraveli.paymentCardsCostTest.exceptions.PaymentCardCostCalculationUnProcessAbleContentException;
import com.etraveli.paymentCardsCostTest.models.CountryCostMatrix;
import com.etraveli.paymentCardsCostTest.repository.ICountryCostMatrixRepository;
import com.etraveli.paymentCardsCostTest.services.ICountryCostMatrixService;
import com.etraveli.paymentCardsCostTest.services.imp.CountryCostMatrixService;
import com.etraveli.paymentCardsCostTest.services.imp.PaymentCardCostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PaymentCardCostServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ICountryCostMatrixRepository countryCostMatrixRepository;

    @InjectMocks
    private IApiCallService apiCallService = new ApiCallService(restTemplate);

    @InjectMocks
    private ICountryCostMatrixService countryCostMatrixService = new CountryCostMatrixService(countryCostMatrixRepository);
    @InjectMocks
    private PaymentCardCostService paymentCardCostService = new PaymentCardCostService(apiCallService, countryCostMatrixService);

    PaymentCardCostResponseDto expectedPaymentCardCostResponseDto;
    PaymentCardCostDto paymentCardCostDto;
    Optional<CountryCostMatrix> countryCostMatrix;
    @BeforeEach
    public void init() {
        expectedPaymentCardCostResponseDto= PaymentCardCostResponseDto.builder().cost(20.0).country("AR").build();
        paymentCardCostDto = PaymentCardCostDto.builder().iin("411111").build();
        countryCostMatrix = Optional.of(CountryCostMatrix.builder().costUSD(20.0).country("AR").build());
    }

    @Test
    public void testPaymentCardCostService() {

        Mockito.when(this.countryCostMatrixRepository.findByCountry(Mockito.any(String.class))).thenReturn(countryCostMatrix);
        Mockito.when(this.restTemplate.getForEntity(any(String.class), eq(BinTableApiResponse.class)))
                .thenReturn(ResponseEntity.ok(this.getBinTableApiResponseMock()));

        PaymentCardCostResponseDto paymentCardCostResponseDto = paymentCardCostService.calculatePaymentCardCost(paymentCardCostDto);

        Assertions.assertEquals(expectedPaymentCardCostResponseDto, paymentCardCostResponseDto);
    }

    @Test
    public void testPaymentCardCostService_countryNotFound() {

        Mockito.when(this.countryCostMatrixRepository.findByCountry(Mockito.any(String.class))).thenReturn(Optional.empty());
        Mockito.when(this.restTemplate.getForEntity(any(String.class), eq(BinTableApiResponse.class)))
                .thenReturn(ResponseEntity.ok(this.getBinTableApiResponseMock()));


        Assertions.assertThrows(CountryCostMatrixNotFoundException.class,
                () -> paymentCardCostService.calculatePaymentCardCost(paymentCardCostDto));
    }


    @Test
    public void testPaymentCardCostService_ApiNotResponse() {
        Mockito.when(this.restTemplate.getForEntity(any(String.class), eq(BinTableApiResponse.class)))
                .thenReturn(ResponseEntity.badRequest().body(null));

        Assertions.assertThrows(PaymentCardCostCalculationUnProcessAbleContentException.class,
                () -> paymentCardCostService.calculatePaymentCardCost(paymentCardCostDto));
    }

    private BinTableApiResponse getBinTableApiResponseMock() {
        return BinTableApiResponse.builder().scheme("visa").type("debit")
                .country(Country.builder().name("Argentina").alpha2("AR").currency("ARG").build()).build();
    }

}
