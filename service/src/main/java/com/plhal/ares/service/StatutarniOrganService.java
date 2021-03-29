package com.plhal.ares.service;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatutarniOrganService {

    @JsonBackReference
    private FirmaService firmaService;

    @NonNull
    private String jmeno;

    @NonNull
    private String prijmeni;

    @NonNull
    private String funkce;
}
