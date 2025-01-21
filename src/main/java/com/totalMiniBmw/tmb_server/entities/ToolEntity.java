package com.totalMiniBmw.tmb_server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.totalMiniBmw.tmb_server.entities.enums.ToolCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tools")
public class ToolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Name required.")
    private String name;

    @NotNull(message = "BMW Group required.")
    @Min(value = 1, message = "BMW group required.")
    private int bmwGroup;

    @NotNull(message = "BMW Sub Group required.")
    @Min(value = 1, message = "BMW sub group required.")
    private int bmwSubGroup;

    @NotBlank(message = "Cabinet required.")
    private String cabinet;

    @NotBlank(message = "Location required.")
    private String location;

    private String image;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ToolCondition condition = ToolCondition.OKAY;

    @NotBlank(message = "Chassis required.")
    private String chassis;

    @NotBlank(message = "Description required.")
    private String description;

    @Column(columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> comments;

    @Column(unique = true)
    private long barcode;

    @NotBlank(message = "Tool board required.")
    private String toolBoard;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    @JsonBackReference
    private UserEntity user;
}
