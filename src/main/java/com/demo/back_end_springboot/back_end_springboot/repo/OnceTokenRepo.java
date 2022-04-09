package com.demo.back_end_springboot.back_end_springboot.repo;

import com.demo.back_end_springboot.back_end_springboot.domain.OnceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OnceTokenRepo extends JpaRepository<OnceToken, Integer> {
    Optional<OnceToken> findByAccountAndToken(String account, String token);

    Optional<OnceToken> findByAccountAndShortRandom(String account, String shortRandom);
}
