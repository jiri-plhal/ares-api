package com.plhal.ares.dblayer;

import lombok.*;

import javax.persistence.*;

/**
 *  This class represents statutory authority of company.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statutarni_organ")
public class StatutarniOrgan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String statutarniOrganId;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name="firma_ico")
    private Firma firma;

    @NonNull
    private String jmeno;

    @NonNull
    private String prijmeni;

    @NonNull
    private String funkce;
}
