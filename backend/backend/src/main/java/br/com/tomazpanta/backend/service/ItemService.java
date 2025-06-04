package br.com.tomazpanta.backend.service;

 import br.com.tomazpanta.backend.model.Item;
 import br.com.tomazpanta.backend.repository.ItemRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.util.List;

 @Service
 public class ItemService {
     @Autowired
     private ItemRepository itemRepository;

     public Item salvarItem(Item item) {
         return itemRepository.save(item);
     }

     public List<Item> buscarTodosItens() {
         return itemRepository.findAll();
     }
 }