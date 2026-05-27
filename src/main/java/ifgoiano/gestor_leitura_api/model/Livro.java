package ifgoiano.gestor_leitura_api.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "livro",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_livro_isbn", columnNames = "isbn"),
                @UniqueConstraint(name = "uk_livro_google_volume", columnNames = "google_volume_id")
        }
)
public class Livro implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O identificador do Google Books é obrigatório.")
    @Size(max = 100, message = "O identificador do Google Books deve ter no máximo 100 caracteres.")
    @Column(name = "google_volume_id", nullable = false, length = 100)
    private String googleVolumeId;

    @NotBlank(message = "O título do livro é obrigatório.")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres.")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "O ISBN é obrigatório.")
    @Size(max = 20, message = "O ISBN deve ter no máximo 20 caracteres.")
    @Column(nullable = false, length = 20)
    private String isbn;

    @Min(value = 1, message = "O número de páginas deve ser maior que zero.")
    @Column(name = "numero_paginas", nullable = false)
    private int numeroPaginas;

    @ManyToMany
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "livro_generos",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos = new ArrayList<>();

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resenha> avaliacao = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;

    @NotNull(message = "A data de publicação é obrigatória.")
    @PastOrPresent(message = "A data de publicação não pode estar no futuro.")
    @Column(name = "data_publicacao", nullable = false)
    private String dataPublicacao;

    @NotBlank(message = "A URL da capa é obrigatória.")
    @Size(max = 1000, message = "A URL da capa deve ter no máximo 1000 caracteres.")
    @Column(name = "capa_url", nullable = false, length = 1000)
    private String capa_url;

    public Livro() {

    }

    public Livro(Long id, String googleVolumeId, String titulo, String isbn, int numeroPaginas,
                 List<Autor> autores, List<Genero> generos, List<Resenha> avaliacao,
                 Editora editora, String dataPublicacao, String capa_url) {
        this.id = id;
        this.googleVolumeId = googleVolumeId;
        this.titulo = titulo;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.autores = autores != null ? autores : new ArrayList<>();
        this.generos = generos != null ? generos : new ArrayList<>();
        this.avaliacao = avaliacao != null ? avaliacao : new ArrayList<>();
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.capa_url = capa_url;
    }

    public Double calcularMediaAvaliacoes() {
        if (avaliacao == null || avaliacao.isEmpty()) {
            return 0.0;
        }

        return avaliacao.stream()
                .map(Resenha::getAvaliacao)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public String exibirFichaTecnicaCompleta() {
        String nomesAutores = autores == null || autores.isEmpty()
                ? "Não informado"
                : autores.stream().map(Autor::getNome).collect(Collectors.joining(", "));

        String nomesGeneros = generos == null || generos.isEmpty()
                ? "Não informado"
                : generos.stream().map(Genero::getNome).collect(Collectors.joining(", "));

        String nomeEditora = editora == null ? "Não informada" : editora.getNome();

        return "Título: " + titulo + "\n" +
                "ISBN: " + isbn + "\n" +
                "Autores: " + nomesAutores + "\n" +
                "Editora: " + nomeEditora + "\n" +
                "Gêneros: " + nomesGeneros + "\n" +
                "Número de páginas: " + numeroPaginas + "\n" +
                "Data de publicação: " + dataPublicacao + "\n" +
                "Média de avaliações: " + calcularMediaAvaliacoes();
    }

    public void adicionarResenha(Resenha resenha) {
        if (resenha == null) {
            throw new IllegalArgumentException("A resenha não pode ser nula.");
        }
        if (resenha.getLeitor() == null) {
            throw new IllegalArgumentException("A resenha deve possuir um leitor.");
        }

        boolean leitorJaAvaliou = avaliacao.stream()
                .anyMatch(r -> r.getLeitor() != null && r.getLeitor().equals(resenha.getLeitor()));

        if (leitorJaAvaliou) {
            throw new IllegalArgumentException("O leitor já possui uma resenha para este livro.");
        }

        resenha.setLivro(this);
        avaliacao.add(resenha);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.googleVolumeId);
        hash = 41 * hash + Objects.hashCode(this.titulo);
        hash = 41 * hash + Objects.hashCode(this.isbn);
        hash = 41 * hash + this.numeroPaginas;
        hash = 41 * hash + Objects.hashCode(this.autores);
        hash = 41 * hash + Objects.hashCode(this.generos);
        hash = 41 * hash + Objects.hashCode(this.avaliacao); 
        hash = 41 * hash + Objects.hashCode(this.editora);
        hash = 41 * hash + Objects.hashCode(this.dataPublicacao);
        hash = 41 * hash + Objects.hashCode(this.capa_url);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro livro)) return false;
        return id != null && Objects.equals(id, livro.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleVolumeId() {
        return googleVolumeId;
    }

    public void setGoogleVolumeId(String googleVolumeId) {
        this.googleVolumeId = googleVolumeId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Resenha> getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(List<Resenha> avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getCapa_url() {
        return capa_url;
    }

    public void setCapa_url(String capa_url) {
        this.capa_url = capa_url;
    }


 
}
