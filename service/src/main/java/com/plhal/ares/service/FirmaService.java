package com.plhal.ares.service;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirmaService {

    @NonNull
    private String ico;

    @NonNull
    private String nazevFirmy;

    @NonNull
    private String pravniForma;

    @JsonManagedReference
    private List<StatutarniOrganService> clenoveStatutarnihoOrganuService;

    private String zakladniKapital;

    @NonNull
    private String sidlo;

    @JsonManagedReference
    private List<PredmetPodnikaniService> predmetPodnikaniService;

}