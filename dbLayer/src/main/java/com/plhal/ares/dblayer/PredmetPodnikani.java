package com.plhal.ares.dblayer;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "predmet_podnikani")
public class PredmetPodnikani {

    @Id
    @NonNull
    private String nazev;

    @ManyToMany(mappedBy = "predmetPodnikani")
    private List<Firma> seznamFirem;

}
