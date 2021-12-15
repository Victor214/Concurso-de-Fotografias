/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classe;

import java.io.InputStream;
import java.util.Date;

/**
 *
 * @author Victor
 */
public class Concurso {
    private int codConcurso;
    private String nome;
    private String descricao;
    private Date inicio;
    private Date fim;
    private String codImagem;
    private Imagem imagem;
    private Integer minFotos;
    private Integer maxFotos;

    public int getCodConcurso() {
        return codConcurso;
    }

    public void setCodConcurso(int codConcurso) {
        this.codConcurso = codConcurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public String getCodImagem() {
        return codImagem;
    }

    public void setCodImagem(String codImagem) {
        this.codImagem = codImagem;
    }

    public Integer getMinFotos() {
        return minFotos;
    }

    public void setMinFotos(Integer minFotos) {
        this.minFotos = minFotos;
    }

    public Integer getMaxFotos() {
        return maxFotos;
    }

    public void setMaxFotos(Integer maxFotos) {
        this.maxFotos = maxFotos;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }
    
}
