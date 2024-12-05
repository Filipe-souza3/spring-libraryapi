package cursoJPA.libraryapi.controller.mappers;

import org.mapstruct.Mapper;


import cursoJPA.libraryapi.controller.dto.AutorDTO;
import cursoJPA.libraryapi.controller.dto.AutorDTOResponse;
import cursoJPA.libraryapi.model.Autor;


@Mapper(componentModel = "spring") // mapstruct
public interface AutorMapper {

    /*quando algum field do dto e diferente da entidade */
    // @Mapping(source = "nome", target = "nome")
    Autor toEntity(AutorDTO dto);

    AutorDTOResponse toDTO(Autor autor);

}
