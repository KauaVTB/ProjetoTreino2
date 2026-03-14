package com.ProjetoTreino2.ProjetoTreino2.Services;

import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteDTO;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Cliente;
import com.ProjetoTreino2.ProjetoTreino2.Repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteResponseDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.ClienteNotFoundException;
import com.ProjetoTreino2.ProjetoTreino2.Repository.LivroRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final LivroRepository livroRepository;

    public ClienteService(ClienteRepository clienteRepository, LivroRepository livroRepository) {
        this.clienteRepository = clienteRepository;
        this.livroRepository = livroRepository;
    }

    public ClienteDTO create(ClienteDTO clienteDTO) {
        List<Livro> livros = resolveLivros(clienteDTO.getLivro_id());

        Cliente cliente = new Cliente(
                clienteDTO.getId(),
                clienteDTO.getNome(),
                clienteDTO.getEmail(),
                livros);

        Cliente salvo = clienteRepository.save(cliente);

        return new ClienteDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                mapLivroIds(salvo));
    }

    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                mapLivroTitulos(cliente));
    }

    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        mapLivroTitulos(cliente)))
                .toList();
    }

    public ClienteDTO update(@RequestBody ClienteDTO clienteDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());

        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setLivros(resolveLivros(clienteDTO.getLivro_id()));

        Cliente saved = clienteRepository.save(cliente);

        return new ClienteDTO(
                saved.getId(),
                saved.getNome(),
                saved.getEmail(),
                mapLivroIds(saved));

    }

    public void delete(Long id) {
        clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());
        clienteRepository.deleteById(id);
    }

    public ClienteResponseDTO findByNome(String nome){
        Cliente cliente = clienteRepository.findByNome(nome).orElseThrow(() -> new ClienteNotFoundException());

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                mapLivroTitulos(cliente));
    }

    private List<Livro> resolveLivros(List<Long> livroIds) {
        if (livroIds == null || livroIds.isEmpty()) {
            return new ArrayList<>();
        }
        return livroRepository.findAllById(livroIds);
    }

    private List<String> mapLivroTitulos(Cliente cliente) {
        List<Livro> livros = cliente.getLivros();
        if (livros == null) {
            return Collections.emptyList();
        }
        return livros.stream().map(Livro::getTitulo).toList();
    }

    private List<Long> mapLivroIds(Cliente cliente) {
        List<Livro> livros = cliente.getLivros();
        if (livros == null) {
            return Collections.emptyList();
        }
        return livros.stream().map(Livro::getId).toList();
    }

}
