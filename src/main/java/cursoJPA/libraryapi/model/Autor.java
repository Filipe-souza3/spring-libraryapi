package cursoJPA.libraryapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

/*
 * @See AutorRepositoryTest
 */

@Entity
@Table(name = "autor")
//@Getter //gerar automaticamente ao compilar getter e setters ou usar anotaçao @data q ja tem os 2 e mais uns (tostring, hash)
//@Setter
@Data
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class) // precisa colocar para as anotacoes da auditoria funcione - precisa colocar outra no application tbm
public class Autor {

    //no java se nao tiver um construtor ele ja cria um construtor vazio

    //se quiser cria um construtor vazio e coloca anotaçao deprecated para ngm usar
    //e depois criar o construtor com seus parametros para usar

    @Id // avisar q e primary key 
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID) //gerar automaticamente id
    private UUID id;

    @Column(name = "nome", length = 100,nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor")
    //@Transient ///avisa para o  spring eesse atributo n tem nd a ver com o mapeamento
    private List<Livro> livros;

    @CreatedDate // jpa isere a data e hora atual ao cadastrar - anotacao de auditoria, para funcionar precisa colocar entitylisteners
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate // jpa atualiza data e hora da ultima atualizacao no registro - anotacao de auditoria
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;
}
