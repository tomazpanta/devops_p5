// src/components/ItemList.js
import React from 'react';

function ItemList({ items }) {
    if (!items || items.length === 0) {
        return <p>Nenhum item cadastrado ainda.</p>;
    }

    return (
        <div className="item-list">
            <h2>Itens Cadastrados</h2>
            <ul>
                {items.map(item => (
                    <li key={item.id}>
                        <strong>{item.nome}</strong>
                        <p>{item.descricao}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ItemList;