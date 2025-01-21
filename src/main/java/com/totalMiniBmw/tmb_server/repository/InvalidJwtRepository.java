package com.totalMiniBmw.tmb_server.repository;

import com.totalMiniBmw.tmb_server.entities.InvalidJwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvalidJwtRepository extends JpaRepository<InvalidJwtEntity, String> {
    Optional<InvalidJwtEntity> findByJwt(String jwt);
}
