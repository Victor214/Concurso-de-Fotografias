/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classe.Concurso;
import Classe.EntradaConcurso;
import Classe.Imagem;
import Classe.Usuario;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor
 */
public class ConcursoDAO {
    public String criarArquivo(String ext, InputStream input) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("INSERT INTO Imagem (blobImagem) VALUES (?)");
            st.setBinaryStream(1, input);
            st.executeUpdate();
            input.close();
            
            st = con.prepareStatement("SELECT last_insert_id() AS nextVal;");
            rs = st.executeQuery();
            rs.next();
            String nextVal = Integer.toString(rs.getInt("nextVal"));
            
            return nextVal;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void inserirConcurso(Concurso concurso) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            // Insert 
            // Select last insert id
            // Update
            
            java.sql.Timestamp sqlDateInicio = new java.sql.Timestamp(concurso.getInicio().getTime());
            java.sql.Timestamp sqlDateFim = new java.sql.Timestamp(concurso.getFim().getTime());
            
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("INSERT INTO Concurso (nome, descricao, inicio, fim, codImagem, minFotos, maxFotos) VALUES (?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, concurso.getNome());
            st.setString(2, concurso.getDescricao());
            st.setTimestamp(3, sqlDateInicio);
            st.setTimestamp(4, sqlDateFim);
            st.setInt(5, Integer.parseInt(concurso.getCodImagem()));
            st.setInt(6, concurso.getMinFotos());
            st.setInt(7, concurso.getMaxFotos());
            st.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Concurso> buscarConcursosDisponiveis() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM Concurso WHERE inicio < CURRENT_TIMESTAMP() AND fim > CURRENT_TIMESTAMP();");
            rs = st.executeQuery();
            
            List<Concurso> list = new ArrayList<Concurso>();
            while (rs.next()) {
                Concurso concurso = new Concurso();
                concurso.setCodConcurso(rs.getInt("codConcurso"));
                concurso.setNome(rs.getString("nome"));
                concurso.setDescricao(rs.getString("descricao"));
                
                concurso.setInicio( new java.util.Date(rs.getTimestamp("inicio").getTime()) );
                concurso.setFim( new java.util.Date(rs.getTimestamp("fim").getTime()) );
                
                concurso.setMinFotos(rs.getInt("minFotos"));
                concurso.setMaxFotos(rs.getInt("maxFotos"));
                
                concurso.setCodImagem(rs.getString("codImagem"));
                
                list.add(concurso);
            }
            
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Concurso> buscarConcursosExpirados() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM Concurso WHERE fim < CURRENT_TIMESTAMP();");
            rs = st.executeQuery();
            
            List<Concurso> list = new ArrayList<Concurso>();
            while (rs.next()) {
                Concurso concurso = new Concurso();
                concurso.setCodConcurso(rs.getInt("codConcurso"));
                concurso.setNome(rs.getString("nome"));
                concurso.setDescricao(rs.getString("descricao"));
                
                concurso.setInicio( new java.util.Date(rs.getTimestamp("inicio").getTime()) );
                concurso.setFim( new java.util.Date(rs.getTimestamp("fim").getTime()) );
                
                concurso.setMinFotos(rs.getInt("minFotos"));
                concurso.setMaxFotos(rs.getInt("maxFotos"));
                
                concurso.setCodImagem(rs.getString("codImagem"));
                
                list.add(concurso);
            }
            
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public InputStream getImagemPorID(Integer id) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT blobImagem FROM Imagem WHERE codImagem = ?;");
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            return rs.getBinaryStream("blobImagem");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean usuarioParticipa( int codConcurso, int codCliente ) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT codCliente FROM EntradaConcurso WHERE codConcurso = ? AND codCliente = ?;");
            st.setInt(1, codConcurso);
            st.setInt(2, codCliente);
            rs = st.executeQuery();
            
            return rs.next(); // Se tiver entrada, quer dizer que o usuário possui pelo menos uma foto no concurso dado
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public Concurso buscarConcurso(int codConcurso) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT * FROM Concurso WHERE codConcurso = ?");
            st.setInt(1, codConcurso);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Concurso concurso = new Concurso();
                concurso.setCodConcurso(rs.getInt("codConcurso"));
                concurso.setNome(rs.getString("nome"));
                concurso.setDescricao(rs.getString("descricao"));
                
                concurso.setInicio( new java.util.Date(rs.getTimestamp("inicio").getTime()) );
                concurso.setFim( new java.util.Date(rs.getTimestamp("fim").getTime()) );
                
                concurso.setMinFotos(rs.getInt("minFotos"));
                concurso.setMaxFotos(rs.getInt("maxFotos"));
                
                concurso.setCodImagem(rs.getString("codImagem"));
                return concurso;
            }
            return null;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void inserirEntradas( List<EntradaConcurso> list ) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;  
        ConcursoDAO concursoDAO = new ConcursoDAO();
        
        try {
            for (EntradaConcurso ent : list) {
                String imgID = concursoDAO.criarArquivo("png", ent.getImg().getBlobImagem());
                
                st = con.prepareStatement("INSERT INTO EntradaConcurso(codCliente, codConcurso, codImagem, titulo, descricao) VALUES (?, ?, ?, ?, ?)");
                st.setInt(1, ent.getUsuario().getId());
                st.setInt(2, ent.getConcurso().getCodConcurso());
                st.setInt(3, Integer.valueOf(imgID));
                st.setString(4, ent.getTitulo());
                st.setString(5, ent.getDescricao());
                st.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<EntradaConcurso> exibirEntradas(Concurso concurso) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT e.codEntrada, e.titulo, e.descricao, u.codCliente, u.nome, i.codImagem, i.blobImagem FROM EntradaConcurso e, Imagem i, Usuario u, Concurso c WHERE e.codImagem = i.codImagem AND e.codCliente = u.codCliente AND e.codConcurso = c.codConcurso AND c.codConcurso = ?;");
            st.setInt(1, concurso.getCodConcurso());
            rs = st.executeQuery();
            
            List<EntradaConcurso> list = new ArrayList<EntradaConcurso>();
            while (rs.next()) {
                System.out.println("Entrada");
                
                Usuario user = new Usuario();
                user.setId(rs.getInt("codCliente"));
                user.setNome(rs.getString("nome"));
                
                Imagem img = new Imagem();
                img.setCodImagem(rs.getInt("codImagem"));
                
                EntradaConcurso entrada = new EntradaConcurso();
                entrada.setCodEntrada(rs.getInt("codEntrada"));
                entrada.setTitulo(rs.getString("titulo"));
                entrada.setDescricao(rs.getString("descricao"));
                
                entrada.setUsuario(user);
                entrada.setImg(img);
                entrada.setConcurso(concurso);
                list.add(entrada);
            }
            
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EntradaConcurso buscarEntrada(Integer codEntrada) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            ConcursoDAO concursoDAO = new ConcursoDAO();
            
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT c.codConcurso, c.nome, e.codEntrada, e.titulo, e.descricao, u.codCliente, u.nome, i.codImagem, i.blobImagem FROM EntradaConcurso e, Imagem i, Usuario u, Concurso c WHERE e.codImagem = i.codImagem AND e.codCliente = u.codCliente AND e.codConcurso = c.codConcurso AND e.codEntrada = ?;");
            st.setInt(1, codEntrada);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("codCliente"));
                user.setNome(rs.getString("nome"));
                
                Imagem img = new Imagem();
                img.setCodImagem(rs.getInt("codImagem"));
                
                Concurso concurso = new Concurso();
                concurso.setCodConcurso(rs.getInt("codConcurso"));
                concurso.setNome(rs.getString("nome"));
                
                EntradaConcurso entrada = new EntradaConcurso();
                entrada.setCodEntrada(rs.getInt("codEntrada"));
                entrada.setTitulo(rs.getString("titulo"));
                entrada.setDescricao(rs.getString("descricao"));
                
                System.out.println("codEntrada : " + codEntrada);
                
                entrada.setUsuario(user);
                entrada.setImg(img);
                
                entrada.setConcurso(concurso);
                
                return entrada;
            }
            
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean getAvaliacao(Integer codEntrada, Integer codCliente) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT codEntrada FROM Avaliacao WHERE codEntrada = ? AND codCliente = ?;");
            st.setInt(1, codEntrada);
            st.setInt(2, codCliente);
            rs = st.executeQuery();
            
            boolean rsNext = rs.next();
            System.out.println("getStatus : " + rsNext);
            return rsNext; // Se tiver entrada, quer dizer que já existe avaliacao.
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }   
    }
    
    public Boolean setAvaliacao(Integer codEntrada, Integer codCliente, Boolean likeStatus) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        
        System.out.println("codEntrada DB : " + codEntrada);
        System.out.println("codCliente DB : " + codCliente);
        
        ConcursoDAO concursoDAO = new ConcursoDAO();
        if (likeStatus) {
            // Caso já tenha like
            try {
                
                st = con.prepareStatement("DELETE FROM avaliacao WHERE codEntrada = ? AND codCliente = ?");
                st.setInt(1, codEntrada);
                st.setInt(2, codCliente);
                st.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
            return false;
        } else {
            // Caso ainda não tenha like
            try {
                st = con.prepareStatement("INSERT INTO avaliacao (codCliente, codEntrada) VALUES (?, ?)");
                st.setInt(1, codCliente);
                st.setInt(2, codEntrada);
                st.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
            return true;
        }
    }
    
    public void contarVisualizacao(Integer codEntrada, Integer codCliente) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT codEntrada FROM Visualizacao WHERE codEntrada = ? AND codCliente = ?;");
            st.setInt(1, codEntrada);
            st.setInt(2, codCliente);
            rs = st.executeQuery();
            
            if (!rs.next()) { // Se não existir visualização ainda, contar.
                st = con.prepareStatement("INSERT INTO Visualizacao (codEntrada, codCliente) VALUES (?, ?)");
                st.setInt(1, codEntrada);
                st.setInt(2, codCliente);
                st.executeUpdate();
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }   
    }

    public EntradaConcurso exibirVencedor(Concurso concurso) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement(
                  "SELECT codEntrada, IFNULL(MAX(cA), 0) AS mcA, IFNULL(MAX(cV),0) AS mcV FROM ( "
                + "( SELECT e.codEntrada,  COUNT(a.codAvaliacao) as cA, NULL AS cV "
                + "FROM EntradaConcurso e, Avaliacao a "
                + "WHERE e.codEntrada = a.codEntrada "
                + "GROUP BY a.codEntrada "
                + ") "
                + "UNION ALL "
                + "( SELECT e.codEntrada, NULL, COUNT(v.codVisualizacao) "
                + "FROM EntradaConcurso e, Visualizacao v "
                + "WHERE e.codEntrada = v.codEntrada "
                + "GROUP BY e.codEntrada "
                + ") ) collection "
                + "GROUP BY codEntrada "
                + "ORDER BY (mcA/mcV) DESC "
                + "LIMIT 1; "
            );
            rs = st.executeQuery();
            if (rs.next()) {
                Integer codEntrada = rs.getInt("codEntrada");
                st = con.prepareStatement("SELECT e.codEntrada, e.titulo, e.descricao, u.codCliente, u.nome, i.codImagem, i.blobImagem FROM EntradaConcurso e, Imagem i, Usuario u, Concurso c WHERE e.codImagem = i.codImagem AND e.codCliente = u.codCliente AND e.codConcurso = c.codConcurso AND e.codEntrada = ?;");
                st.setInt(1, codEntrada);
                rs = st.executeQuery();
                
                if (rs.next()) {
                    Usuario user = new Usuario();
                    user.setId(rs.getInt("codCliente"));
                    user.setNome(rs.getString("nome"));

                    Imagem img = new Imagem();
                    img.setCodImagem(rs.getInt("codImagem"));

                    EntradaConcurso entrada = new EntradaConcurso();
                    entrada.setCodEntrada(rs.getInt("codEntrada"));
                    entrada.setTitulo(rs.getString("titulo"));
                    entrada.setDescricao(rs.getString("descricao"));

                    entrada.setUsuario(user);
                    entrada.setImg(img);
                    entrada.setConcurso(concurso);
                    
                    return entrada;
                }
                
                return null;
                
            }
            
            return null;   
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
