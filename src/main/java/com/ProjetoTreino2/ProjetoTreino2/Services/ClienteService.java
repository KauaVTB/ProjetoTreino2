package com.ProjetoTreino2.ProjetoTreino2.Services;

import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteDTO;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Cliente;
import com.ProjetoTreino2.ProjetoTreino2.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteResponseDTO;

import java.util.ArrayList;
import java.util.List;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;
import com.ProjetoTreino2.ProjetoTreino2.Repository.LivroRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private LivroRepository livroRepository;

    public ClienteDTO CriarCliente(ClienteDTO clienteDTO) {
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

    public ClienteResponseDTO FindById(Long id) {
        return clienteRepository.findById(id).stream().map(cliente -> new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getLivros().stream().map(livro -> livro.getTitulo()).toList())).findFirst().orElse(null);

    }

    public List<ClienteResponseDTO> ListarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getLivros().stream().map(livro -> livro.getTitulo()).toList()))
                .toList();
    }

    public ClienteDTO Atualizar(@RequestBody ClienteDTO clienteDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
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
        return null;
    }

    public void Deletar(Long id) {
        clienteRepository.deleteById(id);
    }

}
