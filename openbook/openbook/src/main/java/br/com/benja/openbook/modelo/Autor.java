package br.com.benja.openbook.modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer birth_year;
    private Integer death_year;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Livro> livro = new ArrayList<>();

    public Autor() {}

    public Autor(AutorData autorData) {
        this.nome = autorData.nome();
        this.birth_year = autorData.nascimento();
        this.death_year = autorData.falecimento();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Integer getBirth_year() { return birth_year; }

    public void setBirth_year(Integer birth_year) { this.birth_year = birth_year; }

    public Integer getDeath_year() { return death_year; }

    public void setDeath_year(Integer death_year) { this.death_year = death_year; }

    public List<Livro> getLivro() { return livro; }

    public void setLivro(List<Livro> livros) {
        this.livro = new ArrayList<>();
        livros.forEach(livro -> {
            livro.setAutor(this);
            this.livro.add(livro);
        });

    }

    public void adicionarLivro(Livro livro) {
        livro.setAutor(this);
        this.livro.add(livro);
    }

    @Override
    public String toString() {
        List<String> livros = this.getLivro().stream().map(Livro::getTitle).toList();
        return "\n---------------------" +
                "\nNome: " + nome +
                "\nNascimento: " + birth_year +
                "\nFalecimento: " + death_year +
                "\nLivros: " + livros +
                "\n---------------------";
    }
}