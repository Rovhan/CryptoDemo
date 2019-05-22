package com.crypto.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Crypto {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String ticker;
    private long numberOfCoins;
    private BigDecimal marketCap;

    protected Crypto() {}


    public static class Builder{
        private static Crypto crypto = new Crypto();

        public Builder name(String name){
            this.crypto.name = name;
            return this;
        }

        public Builder ticker(String ticker){
            this.crypto.ticker = ticker;
            return this;
        }
        public Builder numberOfCoins(long numberOfCoins){
            this.crypto.numberOfCoins = numberOfCoins;
            return this;
        }
        public Builder marketCap(BigDecimal marketCap){
            this.crypto.marketCap = marketCap;
            return this;
        }

        public static Crypto build() {
            if(crypto.name == null || crypto.ticker == null || crypto.numberOfCoins == 0 || crypto.marketCap == null){
                throw new IllegalStateException("Object not properly build");
            }else{
                var buildCrypto = new Crypto();
                buildCrypto.numberOfCoins = crypto.numberOfCoins;
                buildCrypto.marketCap = crypto.marketCap;
                buildCrypto.name = crypto.name;
                buildCrypto.ticker = crypto.ticker;
                return buildCrypto;
            }
        }
    }
}
