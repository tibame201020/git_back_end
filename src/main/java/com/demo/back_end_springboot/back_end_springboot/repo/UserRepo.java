package com.demo.back_end_springboot.back_end_springboot.repo;

import com.demo.back_end_springboot.back_end_springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findFirstByMail(String mail);

    Optional<User> findFirstByPhone(String phone);
}
