package com.ProjetoTreino2.ProjetoTreino2.Services;

import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteDTO;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Cliente;
import com.ProjetoTreino2.ProjetoTreino2.Repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteResponseDTO;

import java.util.ArrayList;
import java.util.List;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.*;
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
        List<Livro> livros = new ArrayList<>();

        if (clienteDTO.getLivro_id() != null && !clienteDTO.getLivro_id().isEmpty()) {
            livros = livroRepository.findAllById(clienteDTO.getLivro_id());
        }

        Cliente cliente = new Cliente(
                clienteDTO.getId(),
                clienteDTO.getNome(),
                clienteDTO.getEmail(),
                livros);
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getLivros().stream().map(Livro::getId).toList());
    }

    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());
        
        return new  ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getLivros().stream().map(livro -> livro.getTitulo()).toList());
    }

    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getLivros().stream().map(livro -> livro.getTitulo()).toList()))
                .toList();
    }

    public ClienteDTO update(@RequestBody ClienteDTO clienteDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());
        
            List<Livro> livros = livroRepository.findAllById(clienteDTO.getLivro_id());
            cliente.setNome(clienteDTO.getNome());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setLivros(livros);
            return new ClienteDTO(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getEmail(),
                    cliente.getLivros().stream().map(Livro::getId).toList());
        
    }

    public void delete(Long id) {
        clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());
        clienteRepository.deleteById(id);
    }

}
