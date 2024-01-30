package com.etraveli.paymentCardsCostTest.repository;

import com.etraveli.paymentCardsCostTest.models.CountryCostMatrix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICountryCostMatrixRepository extends JpaRepository<CountryCostMatrix, Long> {

    Optional<CountryCostMatrix> findByCountry(String country);
}
