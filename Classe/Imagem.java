/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classe;

import java.io.InputStream;

/**
 *
 * @author ygorc
 */
public class Imagem {
    private int codImagem;
    private InputStream blobImagem;

    public int getCodImagem() {
        return codImagem;
    }

    public void setCodImagem(int codImagem) {
        this.codImagem = codImagem;
    }

    public InputStream getBlobImagem() {
        return blobImagem;
    }

    public void setBlobImagem(InputStream blobImagem) {
        this.blobImagem = blobImagem;
    }
    
    
}
