package com.ProjetoTreino2.ProjetoTreino2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AutorDTO {
    private Long id;
    private String nome;
    private java.util.List<Long> livros;
}
