package com.totalMiniBmw.tmb_server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "invalid_jwts")
public class InvalidJwtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String jwt;
}
