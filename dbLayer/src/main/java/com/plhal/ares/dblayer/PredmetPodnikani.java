package com.plhal.ares.dblayer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "predmet_podnikani", schema="ares")
public class PredmetPodnikani {

    @Id
    @NonNull
    private String nazev;

    @ManyToMany(mappedBy = "predmetPodnikani")
    @JsonBackReference
    private List<Firma> seznamFirem;

}
