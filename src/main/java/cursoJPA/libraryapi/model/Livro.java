package cursoJPA.libraryapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import cursoJPA.libraryapi.enums.GeneroLivro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

/*
 * @See LivroRepositoryTest
 */

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "autor")
// @AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "data_pulicaco", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "titulo", length = 150)
    private String titulo;

    @Column(name = "genero", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2, nullable = false)
    private BigDecimal preco;

    // @ManyToOne(cascade = CascadeType.ALL) raramnte usado pois pode causar mts problemas
    /*
     * 
     * lazy usado para sempre q pedir para jpa buscar um livro nao fazer join para buscar o autor tambem,
     * so vai buscar autor se na funcao de buscar tiver a anotaçao @transactional do spring
     * usei em livroRepositoryTest metodo buscarLivro
    */
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_autor")
    private Autor autor;







}
