// src/components/ItemForm.js
import React, { useState } from 'react';

function ItemForm({ onItemAdded }) {
    const [nome, setNome] = useState('');
    const [descricao, setDescricao] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!nome) {
            alert('O nome do item é obrigatório!');
            return;
        }

        const novoItem = { nome, descricao };

        try {
            const response = await fetch('http://localhost:8080/api/items', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(novoItem),
            });

            if (!response.ok) {
                // Tenta pegar uma mensagem de erro do backend, se houver
                const errorData = await response.text(); // ou response.json() se o backend enviar JSON no erro
                throw new Error(`Erro ao adicionar item: ${response.status} - ${errorData || response.statusText}`);
            }

            const itemAdicionado = await response.json();
            onItemAdded(itemAdicionado); // Chama a função do App.js para atualizar a lista
            setNome(''); // Limpa o formulário
            setDescricao('');
        } catch (error) {
            console.error("Falha ao adicionar item:", error);
            alert(`Não foi possível adicionar o item: ${error.message}`);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="item-form">
            <h2>Adicionar Novo Item</h2>
            <div>
                <label htmlFor="nome">Nome:</label>
                <input
                    type="text"
                    id="nome"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="descricao">Descrição:</label>
                <textarea
                    id="descricao"
                    value={descricao}
                    onChange={(e) => setDescricao(e.target.value)}
                />
            </div>
            <button type="submit">Adicionar Item</button>
        </form>
    );
}

export default ItemForm;