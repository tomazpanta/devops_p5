package br.com.tomazpanta.backend.repository;

import br.com.tomazpanta.backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca esta interface como um componente Spring Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // JpaRepository já fornece métodos como save(), findAll(), findById(), deleteById(), etc.
    // Você pode adicionar métodos de consulta personalizados aqui se precisar.
}