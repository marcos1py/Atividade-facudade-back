package com.crudDeProdutos.produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudDeProdutos.produtos.entidades.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}