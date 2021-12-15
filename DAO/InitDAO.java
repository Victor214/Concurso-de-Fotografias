/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.TimeZone;

/**
 *
 * @author Victor
 */
public class InitDAO {
    public static void inicializar () {
        Connection con = null;
        PreparedStatement st = null;
        String table = null;
        
        TimeZone.setDefault(TimeZone.getTimeZone("BRST"));
        System.out.println("Timezone set");
        
        try {
            con = ConnectionFactory.getConnection();
            table = "CREATE TABLE IF NOT EXISTS Usuario ("
                  + "codCliente int AUTO_INCREMENT,"
                  + "nome VARCHAR(200),"
                  + "email VARCHAR(100),"  
                  + "senha VARCHAR(64),"
                  + "tipo TINYINT,"
                  + "CONSTRAINT pk_cliente PRIMARY KEY (codCliente)"
                  + ");"
                    ;
            st = con.prepareStatement(table);
            st.executeUpdate(); 
  
            table = "CREATE TABLE IF NOT EXISTS Imagem ("
                  + "codImagem int AUTO_INCREMENT,"
                  + "blobImagem MEDIUMBLOB,"
                  + "CONSTRAINT pk_imagem PRIMARY KEY (codImagem)"
                  + ");"
                    ;
            st = con.prepareStatement(table);
            st.executeUpdate();
            
            table = "CREATE TABLE IF NOT EXISTS Concurso ("
                  + "codConcurso int AUTO_INCREMENT,"
                  + "nome VARCHAR(40),"
                  + "descricao VARCHAR(160),"  
                  + "inicio DATETIME,"
                  + "fim DATETIME,"
                  + "codImagem int,"
                  + "minFotos int,"  
                  + "maxFotos int,"
                  + "CONSTRAINT pk_concurso PRIMARY KEY (codConcurso),"
                  + "CONSTRAINT fk_imagem FOREIGN KEY (codImagem) REFERENCES Imagem(codImagem)"
                  + ");"
                    ; 
            st = con.prepareStatement(table);
            st.executeUpdate();
            
            
            table = "CREATE TABLE IF NOT EXISTS EntradaConcurso ("
                  + "codEntrada int AUTO_INCREMENT,"
                  + "codCliente int,"
                  + "codConcurso int,"
                  + "codImagem int,"
                  + "titulo VARCHAR(40),"
                  + "descricao VARCHAR(40),"
                  + "CONSTRAINT pk_ec_entrada PRIMARY KEY (codEntrada),"
                  + "CONSTRAINT fk_ec_cliente FOREIGN KEY (codCliente) REFERENCES Usuario(codCliente),"
                  + "CONSTRAINT fk_ec_concurso FOREIGN KEY (codConcurso) REFERENCES Concurso(codConcurso),"
                  + "CONSTRAINT fk_ec_imagem FOREIGN KEY (codImagem) REFERENCES Imagem(codImagem)"
                  + ");" 
                  ;
            st = con.prepareStatement(table);
            st.executeUpdate();
            
            table = "CREATE TABLE IF NOT EXISTS Avaliacao ("
                  + "codAvaliacao int AUTO_INCREMENT,"
                  + "codCliente int,"
                  + "codEntrada int,"
                  + "CONSTRAINT pk_a_avaliacao PRIMARY KEY (codAvaliacao),"
                  + "CONSTRAINT fk_a_cliente FOREIGN KEY (codCliente) REFERENCES Usuario(codCliente),"
                  + "CONSTRAINT fk_a_entrada FOREIGN KEY (codEntrada) REFERENCES EntradaConcurso(codEntrada),"
                  + "UNIQUE KEY `uk_a_avaliacaounica` (`codCliente`, `codEntrada`)"
                  + ");" 
                    ;
            st = con.prepareStatement(table);
            st.executeUpdate();
            
            table = "CREATE TABLE IF NOT EXISTS Visualizacao ("
                  + "codVisualizacao int AUTO_INCREMENT,"
                  + "codCliente int,"
                  + "codEntrada int,"
                  + "CONSTRAINT pk_v_visualizacao PRIMARY KEY (codVisualizacao),"
                  + "CONSTRAINT fk_v_cliente FOREIGN KEY (codCliente) REFERENCES Usuario(codCliente),"
                  + "CONSTRAINT fk_v_entrada FOREIGN KEY (codEntrada) REFERENCES EntradaConcurso(codEntrada),"
                  + "UNIQUE KEY `uk_v_visualizacaounica` (`codCliente`, `codEntrada`)"
                  + ");" 
                     ;
            st = con.prepareStatement(table);
            st.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
