package br.com.benja.openbook.modelo;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
    public record Data (
            @JsonAlias("results") List<LivroData> results
    ) { }

