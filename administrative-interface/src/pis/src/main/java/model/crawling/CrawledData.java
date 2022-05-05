/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.crawling;

import java.util.List;

/**
 *
 * @author vitor
 */
public class CrawledData {
    String loja;
    String data;
    CrawledIphone iphone;
    List<CrawledRating> avaliacoes;

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public CrawledIphone getIphone() {
        return iphone;
    }

    public void setIphone(CrawledIphone iphone) {
        this.iphone = iphone;
    }

    public List<CrawledRating> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<CrawledRating> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}