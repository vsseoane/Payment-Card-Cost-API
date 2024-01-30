package com.etraveli.paymentCardsCostTest.controller;

import com.etraveli.paymentCardsCostTest.dto.CountryCostMatrixDto;
import com.etraveli.paymentCardsCostTest.models.CountryCostMatrix;
import com.etraveli.paymentCardsCostTest.services.ICountryCostMatrixService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CountryCostMatrixController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CountryCostMatrixControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICountryCostMatrixService countryCostMatrixService;
    @Autowired
    private ObjectMapper objectMapper;
    private CountryCostMatrix countryCostMatrix;
    private CountryCostMatrixDto countryCostMatrixDto;

    @BeforeEach
    public void init() {
        countryCostMatrix = CountryCostMatrix.builder()
                .costUSD(100).country("AR").build();
        countryCostMatrixDto = CountryCostMatrixDto.builder()
                .costUSD(100).country("AR").build();
    }

    @Test
    public void CountryCostMatrixController_CreateCountryCostMatrix() throws Exception {
        given(this.countryCostMatrixService.createCountryCostMatrixDto(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/country-cost-matrix")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(countryCostMatrixDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(countryCostMatrixDto.getCountry())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.costUSD", CoreMatchers.is(countryCostMatrixDto.getCostUSD())));

    }

    @Test
    public void CountryCostMatrixController_GetAllCountryCostMatrix() throws Exception {

        when(this.countryCostMatrixService.getAllCountryCostsMatrix()).thenReturn(Arrays.asList(countryCostMatrixDto));

        ResultActions response = mockMvc.perform(get("/api/country-cost-matrix")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void CountryCostMatrixController_GetCountryCostMatrixByCountry() throws Exception {

        when(this.countryCostMatrixService.getAllCountryCostsMatrix()).thenReturn(Arrays.asList(countryCostMatrixDto));

        ResultActions response = mockMvc.perform(get("/api/country-cost-matrix/AR")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryCostMatrixDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void CountryCostMatrixController_UpdateCountryCostMatrixByCountry() throws Exception {

        Long countryCostMatrixId = Long.valueOf(1);
        when(this.countryCostMatrixService.updateCountryCostMatrix(countryCostMatrixDto, countryCostMatrixId))
                .thenReturn(countryCostMatrixDto);

        ResultActions response = mockMvc.perform(put("/api/country-cost-matrix/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryCostMatrixDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(countryCostMatrixDto.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.costUSD", CoreMatchers.is(countryCostMatrixDto.getCostUSD())));

    }

    @Test
    public void CountryCostMatrixController_DeleteCountryCostMatrixByCountry() throws Exception {

        Long countryCostMatrixId = Long.valueOf(1);

        doNothing().when(this.countryCostMatrixService).deleteCountryCostMatrix(countryCostMatrixId);

        ResultActions response = mockMvc.perform(delete("/api/country-cost-matrix/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }


}
