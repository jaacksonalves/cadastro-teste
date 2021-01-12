package br.com.jackson.cadastroteste.controller;

import br.com.jackson.cadastroteste.entity.Cliente;
import br.com.jackson.cadastroteste.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public Page<Cliente> mostrarTodos(@PageableDefault(size = 5) Pageable paginacao) {
        return clienteRepository.findAll(paginacao);

    }

    @GetMapping("/{id}")
    public Optional<Cliente> mostrarUm(@PathVariable Long id) {
        return clienteRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid Cliente cliente) {
        clienteRepository.save(cliente);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        String nome = clienteRepository.findById(id).get().getNome();
        clienteRepository.deleteById(id);
        return ResponseEntity.ok("Cliente " + nome + " Deletado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {
        Optional encontrado = clienteRepository.findById(id);
        if (encontrado.isPresent()) {
            clienteRepository.save(cliente);
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }


}
