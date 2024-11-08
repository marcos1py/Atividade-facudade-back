package com.crudDeProdutos.produtos.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nome", nullable = false, unique = false, length = 100)
    private String nome;
    @Column(name = "preco", nullable = false, unique = false, length = 100)
    private float preco;


}
