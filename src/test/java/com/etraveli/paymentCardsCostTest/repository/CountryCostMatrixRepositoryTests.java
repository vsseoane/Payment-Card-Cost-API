package com.etraveli.paymentCardsCostTest.repository;

import com.etraveli.paymentCardsCostTest.models.CountryCostMatrix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CountryCostMatrixRepositoryTests {

    @Autowired
    private ICountryCostMatrixRepository countryCostMatrixRepository;

    @Test
    public void CountryCostMatrixRepository_getAll() {
        CountryCostMatrix countryCostMatrix1 = new CountryCostMatrix();
        countryCostMatrix1.setCostUSD(100);
        countryCostMatrix1.setCountry("AR");

        CountryCostMatrix countryCostMatrix2 = new CountryCostMatrix();
        countryCostMatrix2.setCostUSD(200);
        countryCostMatrix2.setCountry("BR");

        this.countryCostMatrixRepository.save(countryCostMatrix1);
        this.countryCostMatrixRepository.save(countryCostMatrix2);

        List<CountryCostMatrix> paymentCardsCost = this.countryCostMatrixRepository.findAll();

        Assertions.assertThat(paymentCardsCost.size()).isEqualTo(2);
    }

    @Test
    public void CountryCostMatrixRepository_getByCountry() {
        CountryCostMatrix countryCostMatrix1 = new CountryCostMatrix();
        countryCostMatrix1.setCostUSD(100);
        countryCostMatrix1.setCountry("AR");

        CountryCostMatrix countryCostMatrix2 = new CountryCostMatrix();
        countryCostMatrix2.setCostUSD(200);
        countryCostMatrix2.setCountry("BR");

        this.countryCostMatrixRepository.save(countryCostMatrix1);
        this.countryCostMatrixRepository.save(countryCostMatrix2);

        CountryCostMatrix countryCostMatrix = this.countryCostMatrixRepository.findByCountry("BR").get();

        Assertions.assertThat(countryCostMatrix).isNotNull();
        Assertions.assertThat(countryCostMatrix.getCostUSD()).isEqualTo(200);
    }

    @Test
    public void CountryCostMatrixRepository_getByCountry_NotFound() {
        CountryCostMatrix countryCostMatrix1 = new CountryCostMatrix();
        countryCostMatrix1.setCostUSD(100);
        countryCostMatrix1.setCountry("AR");

        CountryCostMatrix countryCostMatrix2 = new CountryCostMatrix();
        countryCostMatrix2.setCostUSD(200);
        countryCostMatrix2.setCountry("BR");

        this.countryCostMatrixRepository.save(countryCostMatrix1);
        this.countryCostMatrixRepository.save(countryCostMatrix2);

        Assertions.assertThat(this.countryCostMatrixRepository.findByCountry("PE")).isEmpty();
    }

    @Test
    public void CountryCostMatrixRepository_save() {
        CountryCostMatrix countryCostMatrix = new CountryCostMatrix();
        countryCostMatrix.setCostUSD(100);
        countryCostMatrix.setCountry("AR");

        CountryCostMatrix savedCountryCostMatrix = this.countryCostMatrixRepository.save(countryCostMatrix);
        Assertions.assertThat(savedCountryCostMatrix).isNotNull();
        Assertions.assertThat(savedCountryCostMatrix.getCostUSD()).isEqualTo(countryCostMatrix.getCostUSD());
    }


    @Test
    public void CountryCostMatrixRepository_update() {
        CountryCostMatrix countryCostMatrix = new CountryCostMatrix();
        countryCostMatrix.setCostUSD(100);
        countryCostMatrix.setCountry("AR");

        CountryCostMatrix savedCountryCostMatrix = this.countryCostMatrixRepository.save(countryCostMatrix);

        CountryCostMatrix foundCountryCostMatrix = this.countryCostMatrixRepository
                .findById(savedCountryCostMatrix.getId()).get();

        foundCountryCostMatrix.setCostUSD(200);
        foundCountryCostMatrix.setCountry("ARG");

        CountryCostMatrix updatedSavedCountryCostMatrix = this.countryCostMatrixRepository.save(foundCountryCostMatrix);

        Assertions.assertThat(updatedSavedCountryCostMatrix.getCountry()).isEqualTo("ARG");
        Assertions.assertThat(updatedSavedCountryCostMatrix.getCostUSD()).isEqualTo(200);
    }

    @Test
    public void CountryCostMatrixRepository_delete() {
        CountryCostMatrix countryCostMatrix = new CountryCostMatrix();
        countryCostMatrix.setCostUSD(100);
        countryCostMatrix.setCountry("AR");

        CountryCostMatrix savedCountryCostMatrix = this.countryCostMatrixRepository.save(countryCostMatrix);

        this.countryCostMatrixRepository.deleteById(savedCountryCostMatrix.getId());
        Optional<CountryCostMatrix> foundCountryCostMatrix = this.countryCostMatrixRepository.findById(savedCountryCostMatrix.getId());

        Assertions.assertThat(foundCountryCostMatrix).isEmpty();
    }
}
