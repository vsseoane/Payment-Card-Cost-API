package com.etraveli.paymentCardsCostTest.services;

import com.etraveli.paymentCardsCostTest.dto.CountryCostMatrixDto;

import java.util.List;

public interface ICountryCostMatrixService {

    List<CountryCostMatrixDto> getAllCountryCostsMatrix();

    CountryCostMatrixDto getCountryCostMatrixByCountry(String country);

    CountryCostMatrixDto createCountryCostMatrixDto(CountryCostMatrixDto countryCostMatrixDto);
    CountryCostMatrixDto updateCountryCostMatrix(CountryCostMatrixDto countryCostMatrixDto, long countryCostMatrixId);
    void deleteCountryCostMatrix(long countryCostMatrixId);

}
