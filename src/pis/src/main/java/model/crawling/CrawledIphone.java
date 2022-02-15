/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.crawling;

/**
 *
 * @author vitor
 */
public class CrawledIphone {
    String cor;
    String mem_int;
    String modelo_nome;
    String modelo_cod;
    String voltagem;
    String link_iphone;
    String link_imagem;
    String tam_tela;
    String resolucao_cam_front;
    String resolucao_cam_tras;
    String mem_ram;
    String titulo;
    String preco_avista;
    String preco_aprazo;
    String media_nota;
    String quantidade_avaliacoes;

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
    
    public String getMem_int() {
        return mem_int;
    }

    public void setMem_int(String mem_int) {
        this.mem_int = mem_int;
    }

    public String getModelo_nome() {
        return modelo_nome;
    }

    public void setModelo_nome(String modelo_nome) {
        this.modelo_nome = modelo_nome;
    }

    public String getModelo_cod() {
        return modelo_cod;
    }

    public void setModelo_cod(String modelo_cod) {
        this.modelo_cod = modelo_cod;
    }

    public String getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(String voltagem) {
        this.voltagem = voltagem;
    }

    public String getLink_iphone() {
        return link_iphone;
    }

    public void setLink_iphone(String link_iphone) {
        this.link_iphone = link_iphone;
    }

    public String getLink_imagem() {
        return link_imagem;
    }

    public void setLink_imagem(String link_imagem) {
        this.link_imagem = link_imagem;
    }

    public String getTam_tela() {
        return tam_tela;
    }

    public void setTam_tela(String tam_tela) {
        this.tam_tela = tam_tela;
    }

    public String getResolucao_cam_front() {
        return resolucao_cam_front;
    }

    public void setResolucao_cam_front(String resolucao_cam_front) {
        this.resolucao_cam_front = resolucao_cam_front;
    }

    public String getResolucao_cam_tras() {
        return resolucao_cam_tras;
    }

    public void setResolucao_cam_tras(String resolucao_cam_tras) {
        this.resolucao_cam_tras = resolucao_cam_tras;
    }

    public String getMem_ram() {
        return mem_ram;
    }

    public void setMem_ram(String mem_ram) {
        this.mem_ram = mem_ram;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPreco_avista() {
        return preco_avista;
    }

    public void setPreco_avista(String preco_avista) {
        this.preco_avista = preco_avista;
    }

    public String getPreco_aprazo() {
        return preco_aprazo;
    }

    public void setPreco_aprazo(String preco_aprazo) {
        this.preco_aprazo = preco_aprazo;
    }

    public String getMedia_nota() {
        return media_nota.replace(",", ".");
    }

    public void setMedia_nota(String media_nota) {
        media_nota = media_nota.replace(",", ".");
        this.media_nota = media_nota;
    }

    public String getQuantidade_avaliacoes() {
        return quantidade_avaliacoes;
    }

    public void setQuantidade_avaliacoes(String quantidade_avaliacoes) {
        this.quantidade_avaliacoes = quantidade_avaliacoes;
    }
}