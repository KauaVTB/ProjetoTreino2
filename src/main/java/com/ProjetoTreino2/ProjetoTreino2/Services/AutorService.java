package com.ProjetoTreino2.ProjetoTreino2.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorDTO;
import com.ProjetoTreino2.ProjetoTreino2.Repository.AutorRepository;
import com.ProjetoTreino2.ProjetoTreino2.Repository.LivroRepository;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Autor;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.AutorNotFoundException;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorResponseDTO;

@Service
public class AutorService {
        
        private final AutorRepository autorRepository;
        private final LivroRepository livroRepository;
        public AutorService(AutorRepository autorRepository, LivroRepository livroRepository) {
                this.autorRepository = autorRepository;
                this.livroRepository = livroRepository;
        }

        public AutorResponseDTO findById(Long id) {
                Autor autor = autorRepository.findById(id).orElseThrow(() -> new AutorNotFoundException());
                return new AutorResponseDTO(
                                autor.getId(),
                                autor.getNome(),
                                autor.getLivros().stream().map(livro -> livro.getTitulo()).toList());
                                
        }

        public AutorDTO create(AutorDTO autorDTO) {
                Autor autor = new Autor();
                autor.setId(autorDTO.getId());
                autor.setNome(autorDTO.getNome());
                if (autorDTO.getLivros() != null && !autorDTO.getLivros().isEmpty()) {
            autor.setLivros(
                    livroRepository.findAllById(autorDTO.getLivros()));
        } 
                autorRepository.save(autor);
                return new AutorDTO(
                                autor.getId(),
                                autor.getNome(),
                                autor.getLivros().stream().map(livro -> livro.getId()).toList());
        }

        public AutorDTO update(AutorDTO autorDTO, Long id) {
                Autor autor = autorRepository.findById(id).orElseThrow(() -> new AutorNotFoundException());
                        autor.setNome(autorDTO.getNome());
                        return new AutorDTO(
                                        autor.getId(),
                                        autor.getNome(),
                                        autor.getLivros().stream().map(livro -> livro.getId()).toList());
                
        }

        public void delete(Long id) {
                autorRepository.findById(id).orElseThrow(() -> new AutorNotFoundException());
                autorRepository.deleteById(id);
        }

        public List<AutorResponseDTO> findAll() {

                return autorRepository.findAll()
                                .stream()
                                .map(autor -> new AutorResponseDTO(
                                                autor.getId(),
                                                autor.getNome(),
                                                autor.getLivros().stream().map(livro -> livro.getTitulo()).toList()))
                                .toList();
        }
}
