package com.my.demoApp.repository;

import com.my.demoApp.model.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Userr, Long> {
    Optional<Userr> findByUsername(String username);
}
