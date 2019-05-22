package com.crypto.demo;

import com.crypto.demo.domain.Crypto;
import com.crypto.demo.repositories.CryptoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataLoader implements ApplicationRunner {
    private CryptoRepository cryptoRepository;
    private Logger logger = LoggerFactory.getLogger("demo");

    @Autowired
    public DataLoader(CryptoRepository repository){
        this.cryptoRepository = repository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        var crypto1 = new Crypto.Builder().name("Bitcoin").marketCap(BigDecimal.valueOf(12848283)).ticker("BTC").numberOfCoins(1323123).build();
        var crypto2 = new Crypto.Builder().name("Ripple").marketCap(BigDecimal.valueOf(2212345)).ticker("RPL").numberOfCoins(22402L).build();
        var crypto3 = new Crypto.Builder().name("Gulden").marketCap(BigDecimal.valueOf(82232125)).ticker("GLD").numberOfCoins(32402L).build();
        var crypto4 = new Crypto.Builder().name("Etheruem").marketCap(BigDecimal.valueOf(42125)).ticker("ETH").numberOfCoins(32402L).build();
        var crypto5 = new Crypto.Builder().name("Eos").marketCap(BigDecimal.valueOf(49555000)).ticker("EOS").numberOfCoins(100045L).build();
        var cryptoToSave = Arrays.asList(crypto1, crypto2, crypto3, crypto4,  crypto5);
        logger.info("Initial DB seed");

        cryptoRepository.saveAll(cryptoToSave);
    }
}
