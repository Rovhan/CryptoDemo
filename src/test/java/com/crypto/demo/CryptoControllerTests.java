package com.crypto.demo;

import com.crypto.demo.controllers.CryptoController;
import com.crypto.demo.controllers.DTO.CryptoDTO;
import com.crypto.demo.domain.Crypto;
import com.crypto.demo.repositories.CryptoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CryptoController.class)
public class CryptoControllerTests {
    @MockBean
    private CryptoRepository cryptoRepository;

    @Autowired
    private MockMvc mockMvc;

    private final String controllerAddress = "/api/currencies";

    @Before
    public void SetUp(){
        var crypto1 = new Crypto.Builder().name("Bitcoin").marketCap(BigDecimal.valueOf(12848283)).ticker("BTC").numberOfCoins(1323123).build();
        var crypto2 = new Crypto.Builder().name("Test1").marketCap(BigDecimal.valueOf(22125)).ticker("BBB").numberOfCoins(22402L).build();
        var crypto3 = new Crypto.Builder().name("Test2").marketCap(BigDecimal.valueOf(22125)).ticker("CCC").numberOfCoins(32402L).build();
        var crypto4 = new Crypto.Builder().name("Test2").marketCap(BigDecimal.valueOf(22125)).ticker("AAA").numberOfCoins(32402L).build();
        var cryptoList = Arrays.asList(crypto1, crypto2, crypto3, crypto4);
        Page<Crypto> cryptoPage = new PageImpl<>(cryptoList, PageRequest.of(0, 10, Sort.by("ticker").descending()), 10);


        given(cryptoRepository.findAll()).willReturn(Arrays.asList(crypto1, crypto2, crypto3));

        given(cryptoRepository.findAll(PageRequest.of(0, 10, Sort.by("ticker").descending()))).willReturn(cryptoPage);
        given(cryptoRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(crypto2));
        given(cryptoRepository.findById(0L)).willReturn(java.util.Optional.ofNullable(crypto1));
        given(cryptoRepository.save(crypto1)).willReturn(crypto1);
    }

    @Test
    public void GetRequestWithIDValueOf1ShouldReturnCryptoWithNameTest1() throws Exception {
        var expectedResult = "{\"id\":0,\"name\":\"Test1\",\"ticker\":\"BBB\",\"numberOfCoins\":22402,\"marketCap\":22125}";
        mockMvc.perform(get("/api/currencies/1").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedResult));
    }

    @Test
    public void CreateRequestWithCorrectParametersShouldCreateANewCrypto() throws Exception{
        var urlTemplate = "/api/currencies?name=Bitcoin&marketcap=12848283&numberOfCoins=1323123&ticker=BTC";
        mockMvc.perform(post(urlTemplate).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

    }

    @Test
    public void DeleteRequestShouldDeleteEntity() throws Exception {
        var urlTemplate = "/api/currencies/0";
        mockMvc.perform(delete(urlTemplate).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void PutRequestShouldUpdateEntity() throws Exception {
        var urlTemplate = controllerAddress + "/0";
        var updatedCrypto = CryptoDTO.builder().name("Bitcoin").marketCap(BigDecimal.valueOf(64894562451L)).numberOfCoins(4548).ticker("BTC").build();
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        var updatedCryptoJSON = mapper.writeValueAsString(updatedCrypto);
        mockMvc.perform(put(urlTemplate).contentType(MediaType.APPLICATION_JSON_UTF8).content(updatedCryptoJSON))
                .andExpect(status().isOk());
    }

    @Test
    public void GetAllRequestShouldReturnOnePage() throws Exception {
        var urlTemplate = controllerAddress;
        mockMvc.perform(get(urlTemplate).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}
