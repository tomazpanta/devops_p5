package br.com.tomazpanta.backend.controller;

import br.com.tomazpanta.backend.model.Item;
import br.com.tomazpanta.backend.repository.ItemRepository; // Ou ItemService se você criar um
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marca esta classe como um controlador REST
@RequestMapping("/api/items") // Todos os endpoints aqui começarão com /api/items
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (para desenvolvimento). Em produção, restrinja para a URL do seu frontend.
public class ItemController {

    @Autowired // Injeta a dependência do ItemRepository
    private ItemRepository itemRepository;

    // Endpoint para criar um novo item
    // POST http://localhost:8080/api/items
    @PostMapping
    public ResponseEntity<Item> criarItem(@RequestBody Item item) {
        try {
            Item novoItem = itemRepository.save(item);
            return new ResponseEntity<>(novoItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para listar todos os itens
    // GET http://localhost:8080/api/items
    @GetMapping
    public ResponseEntity<List<Item>> listarItens() {
        try {
            List<Item> itens = itemRepository.findAll();
            if (itens.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para buscar um item por ID (opcional para o início)
    // GET http://localhost:8080/api/items/1
    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarItemPorId(@PathVariable("id") long id) {
        Optional<Item> itemData = itemRepository.findById(id);

        if (itemData.isPresent()) {
            return new ResponseEntity<>(itemData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}