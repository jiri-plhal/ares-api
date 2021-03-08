package com.plhal.ares.dblayer;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "firma")
@Entity
public class Firma {

    @NonNull
    @Id
    private String ico;

    @NonNull
    private String nazevFirmy;

    @NonNull
    private String pravniForma;

    @NonNull
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "firma")
    private List<StatutarniOrgan> clenoveStatutarnihoOrganu;

    @NonNull
    private String zakladniKapital;

    @NonNull
    private String sidlo;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="firma_predmet_podnikani",
            joinColumns = @JoinColumn(name="ico"),
            inverseJoinColumns = @JoinColumn(name="predmet_podnikani_nazev"))
    @NonNull
    private List<PredmetPodnikani> predmetPodnikani;

}
