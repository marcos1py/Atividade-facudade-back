package com.crudDeProdutos.produtos.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudDeProdutos.produtos.entidades.Produto;
import com.crudDeProdutos.produtos.service.ProdutoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/produtos")
public class ProdutoController {
    
    @Autowired
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<HashMap<String, Produto>> buscarTodos(){
        return ResponseEntity.ok(produtoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorID(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.buscarPorID(id));
    }

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto){
        return ResponseEntity.ok(produtoService.salvar(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorID(@PathVariable Long id){
        produtoService.deletarPorID(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        return produtoService.alterarProduto(id, produto.getNome(), produto.getPreco());
    }

}
