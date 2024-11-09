package com.crudDeProdutos.produtos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crudDeProdutos.produtos.entidades.Produto;
import com.crudDeProdutos.produtos.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public Produto buscarPorID(Long id) {
        return produtoRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Produto não encontrado.")
        );
    }

    @Transactional
    public Produto salvar(Produto produto) {
        if (produto.getPreco() < 0.0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        return produtoRepository.save(produto);
    }

    @Transactional
    public void deletarPorID(Long id) {
        Produto produto = buscarPorID(id); // Ensures the product exists before deletion
        produtoRepository.delete(produto);
    }

    @Transactional
    public Produto alterarProduto(Long id, String nome, float preco) {
        Produto produto = buscarPorID(id);
        if (preco < 0.0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        produto.setNome(nome);
        produto.setPreco(preco);
        return produtoRepository.save(produto); // Ensure updated product is saved
    }
    

    @Transactional(readOnly = true)
    public List<Produto> buscarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos;
    }
    
}
