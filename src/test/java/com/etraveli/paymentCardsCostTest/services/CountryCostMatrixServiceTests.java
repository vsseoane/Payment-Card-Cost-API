package com.etraveli.paymentCardsCostTest.services;

import com.etraveli.paymentCardsCostTest.dto.CountryCostMatrixDto;
import com.etraveli.paymentCardsCostTest.models.CountryCostMatrix;
import com.etraveli.paymentCardsCostTest.repository.ICountryCostMatrixRepository;
import com.etraveli.paymentCardsCostTest.services.imp.CountryCostMatrixService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertAll;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CountryCostMatrixServiceTests {

    @Mock
    private ICountryCostMatrixRepository countryCostMatrixRepository;

    @InjectMocks
    private CountryCostMatrixService countryCostMatrixService;


    @BeforeEach
    public void setUp() {
    }

    @Test
    public void CountryCostMatrixService_CreateCountryCostMatrix() {
        CountryCostMatrix countryCostMatrix = CountryCostMatrix.builder()
                .costUSD(100).country("AR").build();
        CountryCostMatrixDto countryCostMatrixDto = CountryCostMatrixDto.builder().costUSD(100).country("AR").build();


        when(this.countryCostMatrixRepository.save(Mockito.any(CountryCostMatrix.class))).thenReturn(countryCostMatrix);

        CountryCostMatrixDto savedCountryCostMatrixDto = this.countryCostMatrixService.createCountryCostMatrixDto(countryCostMatrixDto);


        Assertions.assertThat(savedCountryCostMatrixDto).isNotNull();
    }

    @Test
    public void CountryCostMatrixService_GetCountryCostMatrix() {
        CountryCostMatrix countryCostMatrix = Mockito.mock(CountryCostMatrix.class);

        when(this.countryCostMatrixRepository.findAll()).thenReturn(Arrays.asList(countryCostMatrix));


        List<CountryCostMatrixDto> savedCountryCostMatrixDto = this.countryCostMatrixService.getAllCountryCostsMatrix();


        Assertions.assertThat(savedCountryCostMatrixDto).isNotNull();
    }

    @Test
    public void CountryCostMatrixService_GetCountryCostMatrixByCountry() {
        String countryAr = "AR";
        CountryCostMatrix countryCostMatrix = CountryCostMatrix.builder()
                .costUSD(100).country(countryAr).build();

        when(this.countryCostMatrixRepository.findByCountry(countryAr)).thenReturn(Optional.ofNullable(countryCostMatrix));

        CountryCostMatrixDto foundCountryCostMatrixDto = this.countryCostMatrixService.getCountryCostMatrixByCountry(countryAr);


        Assertions.assertThat(foundCountryCostMatrixDto).isNotNull();
    }


    @Test
    public void CountryCostMatrixService_UpdateCountryCostMatrixByCountry() {
        Long ccmId = Long.valueOf(1);
        String countryAr = "AR";
        CountryCostMatrix countryCostMatrix = CountryCostMatrix.builder()
                .costUSD(100).country(countryAr).build();

        CountryCostMatrixDto countryCostMatrixDto = CountryCostMatrixDto.builder()
                .costUSD(100).country(countryAr).build();
        when(this.countryCostMatrixRepository.findById(ccmId)).thenReturn(Optional.ofNullable(countryCostMatrix));
        when(this.countryCostMatrixRepository.save(countryCostMatrix)).thenReturn(countryCostMatrix);

        CountryCostMatrixDto updatedCountryCostMatrixDto = this.countryCostMatrixService
                .updateCountryCostMatrix(countryCostMatrixDto, ccmId);


        Assertions.assertThat(updatedCountryCostMatrixDto).isNotNull();
    }

    @Test
    public void CountryCostMatrixService_DeleteCountryCostMatrixByCountry() {
        Long ccmId = Long.valueOf(1);
        String countryAr = "AR";
        CountryCostMatrix countryCostMatrix = CountryCostMatrix.builder()
                .costUSD(100).country(countryAr).build();

        when(this.countryCostMatrixRepository.findById(ccmId)).thenReturn(Optional.ofNullable(countryCostMatrix));
        doNothing().when(this.countryCostMatrixRepository).delete(countryCostMatrix);

        assertAll(() -> this.countryCostMatrixService.deleteCountryCostMatrix(ccmId));
    }
}
