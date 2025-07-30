package br.com.benja.openbook.modelo;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")

    private Autor autor;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idioma = new ArrayList<>();
    private int downloads;

    public Livro() { }

    public Livro(LivroData livroData) {
        this.titulo = livroData.title();
        this.idioma = livroData.idiomas();
        this.downloads = livroData.downloads();
//            this.autor = new Autor (livroData.autor().get(0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return titulo;
    }

    public void setTitle(String title) {
        this.titulo = title;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "\n---------------------" +
                "\nTitle: " + titulo +
                "\nAutor: " + (autor != null ? autor.getNome() : "null") +
                "\nLanguages: " + idioma +
                "\nDownloads: " + downloads +
                "\n---------------------";
    }
}

