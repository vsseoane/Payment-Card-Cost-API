package com.etraveli.paymentCardsCostTest.controller;


import com.etraveli.paymentCardsCostTest.dto.CountryCostMatrixDto;
import com.etraveli.paymentCardsCostTest.services.ICountryCostMatrixService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CountryCostMatrixController {
    private ICountryCostMatrixService countryCostMatrixService;

    @Autowired
    public CountryCostMatrixController(ICountryCostMatrixService countryCostMatrixService) {
        this.countryCostMatrixService = countryCostMatrixService;
    }

    @GetMapping("country-cost-matrix")
    public ResponseEntity<List<CountryCostMatrixDto>> getCountryCostsMatrix() {
        return new ResponseEntity<>(this.countryCostMatrixService.getAllCountryCostsMatrix(), HttpStatus.OK);
    }

    @GetMapping("country-cost-matrix/{country}")
    public ResponseEntity<CountryCostMatrixDto> getCountryCostMatrixByCountry(
            @PathVariable
            @Pattern(regexp = "^[A-Za-z]{2}$", message = "Invalid country code format")
            String country) {
        return ResponseEntity.ok(this.countryCostMatrixService.getCountryCostMatrixByCountry(country));
    }

    @PostMapping("country-cost-matrix")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CountryCostMatrixDto> createCountryCostMatrix(@Valid @RequestBody CountryCostMatrixDto countryCostMatrixDto) {

        return new ResponseEntity<>(this.countryCostMatrixService.createCountryCostMatrixDto(countryCostMatrixDto), HttpStatus.CREATED);
    }


    @PutMapping("country-cost-matrix/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CountryCostMatrixDto> updatePaymentCardCost(@Valid @RequestBody CountryCostMatrixDto countryCostMatrixDto,
                                                                    @PathVariable("id")
                                                                    @Positive(message = "Invalid id. It must be a positive number.")
                                                                    long paymentCardCostId) {
        return new ResponseEntity<>(this.countryCostMatrixService
                .updateCountryCostMatrix(countryCostMatrixDto, paymentCardCostId), HttpStatus.OK);
    }

    @DeleteMapping("country-cost-matrix/{id}")
    public ResponseEntity<String> deletePaymentCardCost(
            @PathVariable("id")
            @Positive(message = "Invalid id. It must be a positive number.")
            long countryCostMatrixId) {

        this.countryCostMatrixService.deleteCountryCostMatrix(countryCostMatrixId);
        return new ResponseEntity<>("Country Cost Matrix deleted", HttpStatus.OK);
    }

}
