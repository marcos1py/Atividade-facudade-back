package com.crudDeProdutos.produtos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Converte um objeto Produto para um HashMap
    private HashMap<String, Object> produtoToMap(Produto produto) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", produto.getId());
        map.put("nome", produto.getNome());
        map.put("preco", produto.getPreco());
        return map;
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        try {
            List<Produto> produtos = produtoService.buscarTodos();
            List<HashMap<String, Object>> produtosMap = produtos.stream()
                                                                .map(this::produtoToMap)
                                                                .collect(Collectors.toList());
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("produtos", produtosMap);
            return ResponseEntity.ok(response);
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
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("produto", produtoToMap(produto));
            return ResponseEntity.ok(response);
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
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.CREATED.value());
            response.put("produto", produtoToMap(produtoSalvo));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse(500, "Erro interno do servidor", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorID(@PathVariable Long id) {
        try {
            produtoService.deletarPorID(id);
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("mensagem", "Produto deletado com sucesso");
            return ResponseEntity.ok(response);
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
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("produto", produtoToMap(produtoAtualizado));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse(500, "Erro interno do servidor", e.getMessage()));
        }
    }
}
