package com.crypto.demo.domain;

import lombok.Data;

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private String name;
        private String ticker;
        private long numberOfCoins;
        private BigDecimal marketCap;

        private Builder() {}

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder ticker(String ticker){
            this.ticker = ticker;
            return this;
        }
        public Builder numberOfCoins(long numberOfCoins){
            this.numberOfCoins = numberOfCoins;
            return this;
        }
        public Builder marketCap(BigDecimal marketCap){
            this.marketCap = marketCap;
            return this;
        }

        public Crypto build() {
            if(this.name == null || this.ticker == null || this.numberOfCoins == 0 || this.marketCap == null){
                throw new IllegalStateException("Object not properly build");
            }else{
                var buildCrypto = new Crypto();
                buildCrypto.numberOfCoins = this.numberOfCoins;
                buildCrypto.marketCap = this.marketCap;
                buildCrypto.name = this.name;
                buildCrypto.ticker = this.ticker;
                return buildCrypto;
            }
        }
    }
}
