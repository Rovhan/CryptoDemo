package com.crypto.demo;

import com.crypto.demo.domain.Crypto;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class CryptoBuilderTests {

    @Test(expected = IllegalStateException.class)
    public void CryptoBuildWithNoParametersShouldGiveAnIllegalStateError(){
        Crypto.Builder.build();
    }

    @Test
    public void CryptoBuildWithParametersShouldGiveAnObject(){
        var crypto = new Crypto.Builder().name("Test").numberOfCoins(1200042).marketCap(BigDecimal.valueOf(1232521234)).ticker("EDC").build();
        assert crypto != null;
    }

    @Test
    public void CryptoBuildWithParametersShouldGiveAnObjectWithTheSameParameters(){
        var crypto = new Crypto.Builder().name("Test").numberOfCoins(1200042L).marketCap(BigDecimal.valueOf(1232521234)).ticker("EDC").build();
        assertThat(crypto.getName(), is("Test"));
        assertThat(crypto.getNumberOfCoins(), is(1200042L));
        assertThat(crypto.getMarketCap(), is(BigDecimal.valueOf(1232521234)));
        assertThat(crypto.getTicker(), is("EDC"));
    }
}
