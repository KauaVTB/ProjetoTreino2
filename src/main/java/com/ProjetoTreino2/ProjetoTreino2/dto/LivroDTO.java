package com.ProjetoTreino2.ProjetoTreino2.dto;

import java.util.List;

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
    private String titulo;
    private Long autor_id;
    private Integer anoPublicacao;
    private List<Long> cliente_id;

}