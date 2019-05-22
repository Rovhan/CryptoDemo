package com.crypto.demo.controllers;

import com.crypto.demo.controllers.DTO.CryptoDTO;
import com.crypto.demo.domain.Crypto;
import com.crypto.demo.repositories.CryptoRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import java.util.List;

@RestController
@RequestMapping("api/currencies")
@CrossOrigin
public class CryptoController {
    private CryptoRepository cryptoRepository;


    @Autowired
    public CryptoController(CryptoRepository cryptoRepository){
        this.cryptoRepository = cryptoRepository;
    }

    @GetMapping("/{id}")
    public Crypto getCryptoById(@PathVariable long id) {
        return cryptoRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    @GetMapping
    public Page<Crypto> getAllCrypto(@RequestParam(value = "page", defaultValue = "0", required = false)int page, @RequestParam(value = "size", defaultValue = "10", required = false)int size
            , @RequestParam(value = "sort", defaultValue = "ticker", required = false)String sort, @RequestParam(value = "ascending", defaultValue = "true", required = false)boolean ascending){
        if(ascending) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
            return cryptoRepository.findAll(pageable);
        }else{
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
            return cryptoRepository.findAll(pageable);
        }

    }

    @PostMapping
    public ResponseEntity createNewCrypto(@RequestParam(value = "name")String name, @RequestParam(value = "ticker") @Length(min = 3, max = 3) String ticker, @RequestParam(value = "marketcap")BigDecimal marketcap,
                                          @RequestParam(value = "numberOfCoins")long numberOfCoins, HttpServletRequest request) {

        try {
            var cryptoCreated = cryptoRepository.save(new Crypto.Builder().numberOfCoins(numberOfCoins).marketCap(marketcap).name(name).ticker(ticker).build());
            var uri = UriComponentsBuilder.fromUriString(request.getRequestURI()).path("/" + cryptoCreated.getId()).build().toUri();
            var response = ResponseEntity.created(uri).build();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCrypto(@PathVariable long id) {
        try {
            cryptoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCrypto(@PathVariable long id, @RequestBody CryptoDTO crypto){
        var cryptoToUpdate = cryptoRepository.findById(id);
        cryptoToUpdate.ifPresent(cr -> {
            cr.setName(crypto.getName());
            cr.setMarketCap(crypto.getMarketCap());
            cr.setNumberOfCoins(crypto.getNumberOfCoins());
            cr.setTicker(crypto.getTicker());
            cryptoRepository.save(cr);

        });
        if(cryptoToUpdate.isPresent()){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();

    }



}
