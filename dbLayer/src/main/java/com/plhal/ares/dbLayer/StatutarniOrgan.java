package com.plhal.ares.dbLayer;

import lombok.*;

import javax.persistence.*;

// Třída statutárního orgánu (instance tohoto objektu jsou přidávány do třídy Firma)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statutarni_organ")
public class StatutarniOrgan {

    @NonNull
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int statutarniOrganId;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="firma_ico")
    private Firma firma;

    @NonNull
    private String jmeno;

    @NonNull
    private String prijmeni;

    @NonNull
    private String funkce;

}
