package com.crypto.demo.repositories;

import com.crypto.demo.domain.Crypto;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Doesn't need tests until you add custom methods.
 */
public interface CryptoRepository extends PagingAndSortingRepository<Crypto, Long> {
}
