package br.com.benja.openbook.repository;


import br.com.benja.openbook.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface AutorRepository extends JpaRepository<Autor, Long> {
        @Query("SELECT a FROM Autor a WHERE a.nome LIKE %:nome%")
        Optional<Autor> findByNome(@Param("nome") String nome);

    @Query("SELECT a FROM Autor a WHERE a.birth_year <= :year AND (a.death_year IS NULL OR a.death_year >= :year)")
    List<Autor> findAuthorsAlive(@Param("year") int year);
    }

