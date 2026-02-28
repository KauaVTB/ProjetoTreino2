package com.ProjetoTreino2.ProjetoTreino2.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LivroDTO {
    private Long id;

    @NotBlank(message = "O campo titulo é obrigatório.")
    private String titulo;

    @NotNull(message = "O campo autor é obrigatório.")
    private Long autor_id;

    @NotNull(message = "O campo anoPublicacao é obrigatório.")
    private Integer anoPublicacao;
    
    private List<Long> cliente_id;

}