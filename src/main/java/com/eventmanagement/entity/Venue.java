package com.eventmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "venues")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private Integer capacity;

    private String amenities;

    private Double pricePerDay;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
