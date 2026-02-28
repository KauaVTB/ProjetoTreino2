package com.ProjetoTreino2.ProjetoTreino2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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


    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "Nome deve conter apenas letras")
    @NotBlank(message = "O nome do autor é obrigatório")
    private String nome;
    private java.util.List<Long> livros;
}
