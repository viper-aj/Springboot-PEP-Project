package com.eventmanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotNull
    @Min(1)
    private Integer capacity;

    private String amenities;

    @NotNull
    @Min(0)
    private Double pricePerDay;
}
