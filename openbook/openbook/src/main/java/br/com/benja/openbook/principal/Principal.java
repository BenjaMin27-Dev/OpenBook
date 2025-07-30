package br.com.benja.openbook.principal;

import br.com.benja.openbook.consultaApi.ConsultaApi;
import br.com.benja.openbook.consultaApi.ConversorData;
import br.com.benja.openbook.modelo.*;
import br.com.benja.openbook.repository.AutorRepository;
import br.com.benja.openbook.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import br.com.benja.openbook.modelo.Data;


import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class Principal {

    private final Scanner keyboard = new Scanner(System.in);
    private final ConsultaApi consultaApi = new ConsultaApi();
    private final ConversorData conversorData = new ConversorData();
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void start() {
        var option = -1;
        while (option != 0) {
            var menu = """
                \n
                ======================================
                              OpenBook
                ======================================
                \n
                --- Escolha a opção ---

                1 - Buscar um livro pelo título
                2 - Lista de livros já registrados
                3 - Lista de autores registrados
                4 - Lista de autores vivos
                5 - Lista de livros por idioma
                6 - Listar top 10 livros mais baixados
                7 - Mostrar estatísticas da base de dados

                0 - Sair
                """;
            System.out.println(menu);

            if (keyboard.hasNextInt()) {
                option = keyboard.nextInt();
                keyboard.nextLine(); // Limpa buffer
                switch (option) {
                    case 1 -> buscarLivroPorTitulo();
                    case 2 -> listaLivrosRegistrados();
                    case 3 -> listaAutores();
                    case 4 -> autoresVivos();
                    case 5 -> listaIdiomas();
                    case 6 -> listTop10();
                    case 7 -> showDbStatistics();
                    case 0 -> System.out.println("\nEncerrando o aplicativo...");
                    default -> System.out.println("\nOpção inválida");
                }
            } else {
                System.out.println("\nEntrada inválida");
                keyboard.next();
            }
        }
    }

    @Transactional
    public void buscarLivroPorTitulo() {
        final String BASE_URL = "https://gutendex.com/books/?search=";
        System.out.println("\nDigite o nome do livro:");
        String titulo = keyboard.nextLine();

        if (!titulo.isBlank() && !isANumber(titulo)) {
            var json = consultaApi.obterDados(BASE_URL + titulo.replace(" ", "%20"));
            var data = conversorData.obterData(json, Data.class);

            Optional<LivroData> searchBook = data.results()
                    .stream()
                    .filter(b -> b.title().toLowerCase().contains(titulo.toLowerCase()))
                    .findFirst();

            if (searchBook.isPresent()) {
                LivroData livroData = searchBook.get();

                if (!verifiedBookExistence(livroData)) {
                    Livro livro = new Livro(livroData);

                    if (!livroData.autor().isEmpty()) {
                        AutorData autorData = livroData.autor().get(0);
                        Optional<Autor> autorOptional = autorRepository.findByNome(autorData.nome());

                        Autor autor = autorOptional.orElseGet(() -> new Autor(autorData));
                        autor.adicionarLivro(livro);
                        autorRepository.save(autor);

                        autorRepository.save(autor); // Isso salva autor e livro (se cascade estiver ativado)
                    } else {
                        System.out.println("\nLivro não possui autor. Salvamento cancelado.");
                        return;
                    }

                    livroRepository.save(livro);
                    System.out.println("\nLivro salvo com sucesso!");
                } else {
                    System.out.println("\nLivro já adicionado anteriormente.");
                }
            } else {
                System.out.println("\nLivro não encontrado.");
            }
        } else {
            System.out.println("\nEntrada inválida.");
        }
    }

    private boolean verifiedBookExistence(LivroData livroData) {
        return livroRepository.verifiedBDExistence(livroData.title());
    }

    private void listaLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (!livros.isEmpty()) {
            System.out.println("\n----- Livros registrados -----");
            livros.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum livro encontrado.");
        }
    }

    private void listaAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (!autores.isEmpty()) {
            System.out.println("\n----- Autores registrados -----");
            autores.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum autor encontrado.");
        }
    }

    private void autoresVivos() {
        System.out.println("\nDigite o ano desejado para consulta:");
        if (keyboard.hasNextInt()) {
            int ano = keyboard.nextInt();
            List<Autor> autores = autorRepository.findAuthorsAlive(ano);
            if (!autores.isEmpty()) {
                System.out.println("\n----- Autores vivos em " + ano + " -----");
                autores.forEach(System.out::println);
            } else {
                System.out.println("\nNenhum autor encontrado nesse ano.");
            }
        } else {
            System.out.println("\nEntrada inválida.");
            keyboard.next();
        }
    }

    private void listaIdiomas() {
        String idioma = "";
        System.out.println("\nSelecione o idioma:");
        var menu = """
            1 - Inglês
            2 - Português
            3 - Espanhol
            4 - Francês
            5 - Italiano
            """;
        System.out.println(menu);

        if (keyboard.hasNextInt()) {
            int opcao = keyboard.nextInt();
            switch (opcao) {
                case 1 -> idioma = "en";
                case 2 -> idioma = "pt";
                case 3 -> idioma = "es";
                case 4 -> idioma = "fr";
                case 5 -> idioma = "it";
                default -> System.out.println("\nOpção inválida.");
            }

            List<Livro> livros = livroRepository.findlivrosByLanguage(idioma);
            if (!livros.isEmpty()) {
                livros.forEach(System.out::println);
            } else {
                System.out.println("\nNenhum livro encontrado para este idioma.");
            }
        } else {
            System.out.println("\nEntrada inválida.");
            keyboard.next();
        }
    }

    private void listTop10() {
        List<Livro> livros = livroRepository.findTop10();
        if (!livros.isEmpty()) {
            System.out.println("\n----- Top 10 livros mais baixados -----");
            livros.forEach(l -> System.out.println(l.getTitle()));
        } else {
            System.out.println("\nNenhum dado disponível.");
        }
    }

    private void showDbStatistics() {
        List<Livro> livros = livroRepository.findAll();
        if (!livros.isEmpty()) {
            IntSummaryStatistics stats = livros.stream()
                    .filter(l -> l.getDownloads() > 0)
                    .collect(Collectors.summarizingInt(Livro::getDownloads));

            System.out.println("\n----- Estatísticas -----");
            System.out.println("Média de downloads: " + stats.getAverage());
            System.out.println("Máximo de downloads: " + stats.getMax());
            System.out.println("Mínimo de downloads: " + stats.getMin());
            System.out.println("Quantidade de livros: " + stats.getCount());
        } else {
            System.out.println("\nNenhum livro na base.");
        }
    }

    private boolean isANumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
