package com.crypto.demo.controllers.DTO;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CryptoDTO {
    private String name;
    private String ticker;
    private long numberOfCoins;
    private BigDecimal marketCap;

    protected CryptoDTO() {
        marketCap = BigDecimal.valueOf(0L);
    }

    public static CryptoBuilder builder() {
        return new CryptoBuilder();
    }

    public static class CryptoBuilder {
        private String name;
        private String ticker;
        private long numberOfCoins;
        private BigDecimal marketCap;

        CryptoBuilder() {}

        public CryptoBuilder name(String name){
            this.name = name;
            return this;
        }

        public CryptoBuilder ticker(String ticker){
            this.ticker = ticker;
            return this;
        }

        public CryptoBuilder numberOfCoins(long numberOfCoins){
            this.numberOfCoins = numberOfCoins;
            return this;
        }

        public CryptoBuilder marketCap(BigDecimal marketCap){
            this.marketCap = marketCap;
            return this;
        }

        public CryptoDTO build() {
            var crypto = new CryptoDTO();
            crypto.marketCap = this.marketCap;
            crypto.name = this.name;
            crypto.numberOfCoins = this.numberOfCoins;
            crypto.ticker = this.ticker;
            return crypto;
        }
    }
}
