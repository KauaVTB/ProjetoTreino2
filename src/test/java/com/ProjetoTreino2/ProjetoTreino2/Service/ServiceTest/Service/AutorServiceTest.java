package com.ProjetoTreino2.ProjetoTreino2.Service.ServiceTest.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ProjetoTreino2.ProjetoTreino2.dto.AutorDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorResponseDTO;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Autor;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;
import com.ProjetoTreino2.ProjetoTreino2.Repository.AutorRepository;
import com.ProjetoTreino2.ProjetoTreino2.Services.AutorService;






@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    private Autor autor;
    private Livro livro;

    @BeforeEach
    void setUp() {
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Java Basics");

        autor = new Autor();
        autor.setId(1L);
        autor.setNome("John Doe");
        autor.setLivros(List.of(livro));
    }

    @Test
    void testFindByIdSuccess() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        AutorResponseDTO result = autorService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getNome());
        assertEquals(1, result.getLivros().size());
        verify(autorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(autorRepository.findById(999L)).thenReturn(Optional.empty());

        AutorResponseDTO result = autorService.findById(999L);

        assertNull(result);
        verify(autorRepository, times(1)).findById(999L);
    }

    @Test
    void testFindAll() {
        when(autorRepository.findAll()).thenReturn(List.of(autor));

        List<AutorResponseDTO> result = autorService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getNome());
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void testDelete() {
        autorService.delete(1L);

        verify(autorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateSuccess() {
        AutorDTO autorDTO = new AutorDTO(1L, "Jane Doe", List.of(1L));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        AutorDTO result = autorService.update(autorDTO, 1L);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getNome());
        verify(autorRepository, times(1)).findById(1L);
    }
}