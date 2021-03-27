package com.plhal.ares.service;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredmetPodnikaniService {

    @NonNull
    private String nazev;

    @JsonBackReference
    private FirmaService firmaService;

}