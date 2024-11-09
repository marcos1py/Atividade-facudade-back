package com.crudDeProdutos.produtos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.crudDeProdutos.produtos.response.ApiResponse;
import com.crudDeProdutos.produtos.service.ProdutoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/produtos")
public class ProdutoController {

    @Autowired
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        try {
            List<Produto> produtos = produtoService.buscarTodos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse(500, "Erro interno do servidor", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorID(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarPorID(id);
            if (produto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(new ApiResponse(404, "Produto não encontrado", "ID: " + id));
            }
            return ResponseEntity.ok(produto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse(500, "Erro interno do servidor", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Produto produto) {
        try {
            if (produto.getNome() == null || produto.getPreco() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body(new ApiResponse(400, "Erro na entrada de dados", "Nome e preço são obrigatórios"));
            }
            Produto produtoSalvo = produtoService.salvar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse(500, "Erro interno do servidor", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorID(@PathVariable Long id) {
        try {
            produtoService.deletarPorID(id);
            return ResponseEntity.ok(new ApiResponse(200, "Produto deletado com sucesso", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse(500, "Erro interno do servidor", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.alterarProduto(id, produto.getNome(), produto.getPreco());
            if (produtoAtualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(new ApiResponse(404, "Produto não encontrado", "ID: " + id));
            }
            return ResponseEntity.ok(produtoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse(500, "Erro interno do servidor", e.getMessage()));
        }
    }
}
