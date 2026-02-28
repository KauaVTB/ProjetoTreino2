package com.ProjetoTreino2.ProjetoTreino2.Service.ServiceTest.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ProjetoTreino2.ProjetoTreino2.Controller.LivroController;

import com.ProjetoTreino2.ProjetoTreino2.dto.LivroDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroResponseDTO;
import com.ProjetoTreino2.ProjetoTreino2.Services.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(LivroController.class)
class LivroControllerTestMvc {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private LivroService livroService;

    @Test
    @DisplayName("Criar livro válido deve retornar 200")
    void criarLivro_valido_retornaOk() throws Exception {

        LivroDTO dto = new LivroDTO(null, "Livro 1", 1L, 2020, List.of(1L));
        LivroDTO resp =
                new LivroDTO(1L, "Livro 1", 1L, 2020, List.of(1L));

        when(livroService.create(any(LivroDTO.class)))
                .thenReturn(resp);

        mockMvc.perform(post("/api/livros/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Livro criado com sucesso!"));

        verify(livroService).create(any(LivroDTO.class));
    }


    @Test
    @DisplayName("Listar todos os livros retorna 200 e lista")
    void listarLivros_retornaOk() throws Exception {
        List<LivroResponseDTO> lista = Arrays.asList(
                new LivroResponseDTO(1L, "A", "Autor A", 2020, List.of("1L")),
                new LivroResponseDTO(2L, "B", "Autor B", 2021, List.of("2L"))
        );

        when(livroService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/api/livros/findAll"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(lista.size()));
    }

    @Test
    @DisplayName("Criar livro com autor inexistente retorna 400")
    void criarLivro_autorInexistente_retornaBadRequest() throws Exception {
        LivroDTO dto = new LivroDTO(null, "Livro 1", null, 2020, Arrays.asList(1L, 2L));

        mockMvc.perform(post("/api/livros/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Criar livro com título vazio retorna 400")
    void criarLivro_tituloVazio_retornaBadRequest() throws Exception {
        LivroDTO dto = new LivroDTO(null, "", 1L, 2020, Arrays.asList(1L, 2L));

        mockMvc.perform(post("/api/livros/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Buscar livros por autor retorna 200")
    void buscarPorAutor_retornaOk() throws Exception {
        List<LivroResponseDTO> lista = List.of(
                new LivroResponseDTO(1L, "Livro 1", "Autor1", 2020, List.of("1L"))
        );
        when(livroService.findByAutor(1L)).thenReturn(lista);

        mockMvc.perform(get("/api/livros/findByAutor/{Id}", 1L))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].titulo").value("Livro 1"));

               verify(livroService).findByAutor(1L);
    }
}