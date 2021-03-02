package com.plhal.ares.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

// Třída statutárního orgánu (instance tohoto objektu jsou přidávány do třídy Firma)
@Builder
@Data
public class StatutarniOrgan {

    @NonNull
    private String jmeno;

    @NonNull
    private String prijmeni;

    @NonNull
    private String funkce;

}
