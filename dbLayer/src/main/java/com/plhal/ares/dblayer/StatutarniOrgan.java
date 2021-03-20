package com.plhal.ares.dblayer;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "statutarni_organ", schema="ares")
public class StatutarniOrgan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statutarniOrganId;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="firma_ico")
    @JsonBackReference
    private Firma firma;

    @NonNull
    private String jmeno;

    @NonNull
    private String prijmeni;

    @NonNull
    private String funkce;
}
