package com.crypto.demo.controllers.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CryptoDTO {
    private String name;
    private String ticker;
    private long numberOfCoins;
    private BigDecimal marketCap;
}
