package com.totalMiniBmw.tmb_server.repository;

import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.entities.enums.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.checkedOut WHERE u.id = :userId")
    UserEntity findByIdWithCheckedOutTools(@Param("userId") String userId);

    @Query(value = "SELECT * FROM users u WHERE :authority = ANY (SELECT jsonb_array_elements_text(u.authorities))", nativeQuery = true)
    List<UserEntity> findAllByAuthority(@Param("authority") String authority);

    @Modifying
    @Query(value = "UPDATE users " +
            "SET authorities = authorities - :authorityToRemove " +
            "WHERE id = :id",
            nativeQuery = true)
    void removeAuthorityFromUser(@Param("id") String id, @Param("authorityToRemove") String authorityToRemove);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmployeeNumber(long employeeNumber);

}
