package br.com.benja.openbook.modelo;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroData(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AutorData> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") int downloads
) { }
