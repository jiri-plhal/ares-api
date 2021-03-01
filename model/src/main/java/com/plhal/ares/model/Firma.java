package com.plhal.ares.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Builder
@Data
public class Firma {

    @NonNull
    private String nazevFirmy;

    @NonNull
    private String pravniForma;

    @NonNull
    private List<StatutarniOrgan> clenoveStatutarnihoOrganu;

    @NonNull
    private String zakladniKapital;

    @NonNull
    private String sidlo;

    @NonNull
    private List<String> predmetPodnikani;

}
