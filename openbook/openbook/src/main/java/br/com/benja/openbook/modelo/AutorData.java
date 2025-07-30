package br.com.benja.openbook.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorData(
        @JsonAlias("nome") String nome,
        @JsonAlias("birth_year") Integer nascimento,
        @JsonAlias("death_year") Integer falecimento
) { }