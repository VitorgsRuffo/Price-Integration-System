/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.crawling;


/**
 *
 * @author vitor
 */
public class CrawledRating {
    String titulo;
    String descricao;
    String data;
    String avaliador_nome;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAvaliador_nome() {
        return avaliador_nome;
    }

    public void setAvaliador_nome(String avaliador_nome) {
        this.avaliador_nome = avaliador_nome;
    }
}