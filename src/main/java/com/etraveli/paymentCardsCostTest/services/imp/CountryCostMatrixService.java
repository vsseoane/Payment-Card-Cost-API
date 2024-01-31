package com.etraveli.paymentCardsCostTest.services.imp;


import com.etraveli.paymentCardsCostTest.dto.CountryCostMatrixDto;
import com.etraveli.paymentCardsCostTest.exceptions.CountryCostMatrixNotFoundException;
import com.etraveli.paymentCardsCostTest.models.CountryCostMatrix;
import com.etraveli.paymentCardsCostTest.repository.ICountryCostMatrixRepository;
import com.etraveli.paymentCardsCostTest.services.ICountryCostMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CountryCostMatrixService implements ICountryCostMatrixService {

    private ICountryCostMatrixRepository countryCostMatrixRepository;

    @Autowired
    public CountryCostMatrixService(ICountryCostMatrixRepository countryCostMatrixRepository) {
        this.countryCostMatrixRepository = countryCostMatrixRepository;
    }


    @Override
    public List<CountryCostMatrixDto> getAllCountryCostsMatrix() {
        List<CountryCostMatrix> countryCostMatrix = countryCostMatrixRepository.findAll();
        return countryCostMatrix.stream().map((c) -> mapToDto(c)).collect(Collectors.toList());
    }

    @Override
    public CountryCostMatrixDto getCountryCostMatrixByCountry(String country) {
        CountryCostMatrix countryCostMatrix = this.countryCostMatrixRepository.findByCountry(country)
                .orElseThrow(() -> new CountryCostMatrixNotFoundException("Country Cost Matrix  could not be found by country " + country));

        return mapToDto(countryCostMatrix);
    }

    @Override
    public CountryCostMatrixDto createCountryCostMatrixDto(CountryCostMatrixDto countryCostMatrixDto) {

        try{
            CountryCostMatrix countryCostMatrix = new CountryCostMatrix();
            countryCostMatrix.setCostUSD(countryCostMatrixDto.getCostUSD());
            countryCostMatrix.setCountry(countryCostMatrixDto.getCountry());
            CountryCostMatrix newCountryCostMatrix = this.countryCostMatrixRepository.save(countryCostMatrix);
            CountryCostMatrixDto countryCostMatrixDtoResponse = new CountryCostMatrixDto();
            countryCostMatrixDtoResponse.setCostUSD(newCountryCostMatrix.getCostUSD());
            countryCostMatrixDtoResponse.setCountry(newCountryCostMatrix.getCountry());
            countryCostMatrixDtoResponse.setId(newCountryCostMatrix.getId());
            return countryCostMatrixDtoResponse;

        } catch (Exception e) {
            if(e.getMessage().contains("duplicate key value violates")) {
                throw new CountryCostMatrixNotFoundException("Country must be unique");
            } else {
                throw new CountryCostMatrixNotFoundException("Is not possible to add the country cost matrix "
                        + countryCostMatrixDto.getId() + " .More info: " + e.getMessage());
            }


        }
    }

    @Override
    public CountryCostMatrixDto updateCountryCostMatrix(CountryCostMatrixDto countryCostMatrixDto, long countryCostMatrixId) {
        CountryCostMatrix countryCostMatrix = this.countryCostMatrixRepository.findById(countryCostMatrixId)
                .orElseThrow(() -> new CountryCostMatrixNotFoundException("Country Cost Matrix with " +
                        countryCostMatrixId + " could not be updated" ) );
        countryCostMatrix.setCostUSD(countryCostMatrixDto.getCostUSD());
        countryCostMatrix.setCountry(countryCostMatrixDto.getCountry());
        CountryCostMatrix updateCountryCostMatrix = this.countryCostMatrixRepository.save(countryCostMatrix);
        return mapToDto(updateCountryCostMatrix);
    }

    @Override
    public void deleteCountryCostMatrix(long countryCostMatrixId) {
        CountryCostMatrix countryCostMatrix = this.countryCostMatrixRepository.findById(countryCostMatrixId)
                .orElseThrow(() -> new CountryCostMatrixNotFoundException("Country Cost Matrix with " +
                        countryCostMatrixId + " could not be deleted" ) );
        this.countryCostMatrixRepository.delete(countryCostMatrix);
    }


    private CountryCostMatrixDto mapToDto(CountryCostMatrix countryCostMatrix) {
        return CountryCostMatrixDto.builder()
                .id(countryCostMatrix.getId())
                .country(countryCostMatrix.getCountry())
                .costUSD(countryCostMatrix.getCostUSD()).build();

    }

}
