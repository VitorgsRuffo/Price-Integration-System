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
    String likes;
    String deslikes;
    String nota;

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
        return data.replace("/", "-");
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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDeslikes() {
        return deslikes;
    }

    public void setDeslikes(String deslikes) {
        this.deslikes = deslikes;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}