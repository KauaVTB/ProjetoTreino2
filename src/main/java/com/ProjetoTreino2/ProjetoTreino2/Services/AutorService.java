package com.ProjetoTreino2.ProjetoTreino2.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorDTO;
import com.ProjetoTreino2.ProjetoTreino2.Repository.AutorRepository;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Autor;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorResponseDTO;

@Service
public class AutorService {
        @Autowired
        AutorRepository autorRepository;

        public AutorResponseDTO findById(Long id) {
                return autorRepository.findById(id)
                                .stream()
                                .map(autor -> new AutorResponseDTO(
                                                autor.getId(),
                                                autor.getNome(),
                                                autor.getLivros().stream().map(livro -> livro.getTitulo()).toList()))
                                .findFirst()
                                .orElse(null);
        }

        public AutorDTO CriarAutor(@RequestBody AutorDTO autorDTO) {
                Autor autor = new Autor(
                                autorDTO.getId(),
                                autorDTO.getNome(),
                                null);
                return new AutorDTO(
                                autor.getId(),
                                autor.getNome(),
                                autor.getLivros().stream().map(livro -> livro.getId()).toList());
        }

        public AutorDTO Atualizar(@RequestBody AutorDTO autorDTO, Long id) {
                Autor autor = autorRepository.findById(id).orElse(null);
                if (autor != null) {
                        autor.setNome(autorDTO.getNome());
                        return new AutorDTO(
                                        autor.getId(),
                                        autor.getNome(),
                                        autor.getLivros().stream().map(livro -> livro.getId()).toList());
                }
                return null;
        }

        public void Deletar(Long id) {
                autorRepository.deleteById(id);
        }

        public List<AutorResponseDTO> listarAutores() {

                return autorRepository.findAll()
                                .stream()
                                .map(autor -> new AutorResponseDTO(
                                                autor.getId(),
                                                autor.getNome(),
                                                autor.getLivros().stream().map(livro -> livro.getTitulo()).toList()))
                                .toList();
        }
}
