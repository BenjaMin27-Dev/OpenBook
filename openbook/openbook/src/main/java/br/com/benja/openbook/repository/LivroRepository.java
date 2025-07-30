package br.com.benja.openbook.repository;

import br.com.benja.openbook.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT COUNT(b) > 0 FROM Livro b WHERE b.titulo LIKE %:titulo%")
    Boolean verifiedBDExistence(@Param("titulo") String titulo);

    @Query(value = "SELECT * FROM livros WHERE :language = ANY (livros.idioma)", nativeQuery = true)
    List<Livro> findlivrosByLanguage(@Param("language") String language);

    @Query(value = "SELECT * FROM livros ORDER BY downloads DESC LIMIT 10", nativeQuery = true)
    List<Livro> findTop10();
}