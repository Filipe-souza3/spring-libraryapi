package cursoJPA.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import cursoJPA.libraryapi.controller.dto.LivroDTO;
import cursoJPA.libraryapi.controller.dto.LivroDTOResponse;
import cursoJPA.libraryapi.model.Livro;
import cursoJPA.libraryapi.repository.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    // @Mapping(target = "Autor", expression = "java(autorRepository.findById(dto.autor()).orElse(null))")
    // public abstract Livro toEntity(LivroDTO dto);

    public abstract LivroDTOResponse toDTO(Livro livro);
}
