package com.crudDeProdutos.produtos.response;

public class ApiResponse {

    private int status;
    private String mensagem;
    private String erro;

    // Construtores, Getters e Setters

    public ApiResponse(int status, String mensagem, String erro) {
        this.status = status;
        this.mensagem = mensagem;
        this.erro = erro;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}