package com.ProjetoTreino2.ProjetoTreino2.Services;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorDTO;
import com.ProjetoTreino2.ProjetoTreino2.Repository.AutorRepository;
import com.ProjetoTreino2.ProjetoTreino2.Repository.LivroRepository;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Autor;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.AutorNotFoundException;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.ClienteNotFoundException;
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
                                mapLivroTitulos(autor));

        }

        public AutorDTO create(AutorDTO autorDTO) {
                Autor autor = new Autor();
                autor.setId(autorDTO.getId());
                autor.setNome(autorDTO.getNome());
                if (autorDTO.getLivros() != null) {
                        autor.setLivros(livroRepository.findAllById(autorDTO.getLivros()));
                }
                autorRepository.save(autor);
                return new AutorDTO(
                                autor.getId(),
                                autor.getNome(),
                                mapLivroIds(autor));
        }

        public AutorDTO update(AutorDTO autorDTO, Long id) {
                Autor autor = autorRepository.findById(id).orElseThrow(() -> new AutorNotFoundException());
                autor.setNome(autorDTO.getNome());
                if (autorDTO.getLivros() != null) {
                        autor.setLivros(livroRepository.findAllById(autorDTO.getLivros()));
                }
                autorRepository.save(autor);
                return new AutorDTO(
                                autor.getId(),
                                autor.getNome(),
                                mapLivroIds(autor));

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
                                                mapLivroTitulos(autor)))
                                .toList();
        }

        public AutorResponseDTO findByNome(String nome){
                Autor autor = autorRepository.findByNome(nome).orElseThrow(()-> new ClienteNotFoundException());
                
                return new AutorResponseDTO(
                        autor.getId(),
                        autor.getNome(),
                        mapLivroTitulos(autor)
                );
                
        }

        private List<String> mapLivroTitulos(Autor autor) {
                List<Livro> livros = autor.getLivros();
                if (livros == null) {
                        return Collections.emptyList();
                }
                return livros.stream().map(Livro::getTitulo).toList();
        }

        private List<Long> mapLivroIds(Autor autor) {
                List<Livro> livros = autor.getLivros();
                if (livros == null) {
                        return Collections.emptyList();
                }
                return livros.stream().map(Livro::getId).toList();
        }
}
