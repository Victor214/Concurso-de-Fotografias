/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Classe.Usuario;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Victor
 */
public class UsuarioDAO {
    
    public void inserir(Usuario usuario) {
        Connection con = null;
        PreparedStatement st = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("INSERT INTO Usuario (nome, email, senha, tipo) VALUES (?, ?, ?, ?)");
            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getEmail());
            st.setString(3, usuario.getSenha());
            st.setInt(4, 1); // Tipo de cliente = 1
            
            st.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean verificarEmail(String email) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT codCliente FROM Usuario WHERE email = ?");
            st.setString(1, email);
            
            rs = st.executeQuery();
            return rs.next(); // Retorna se já existe um email (true) ou se não existe (false)
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public Usuario autenticar(String email, String pwd) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            String pwdHashed = Hashing.sha256()
            .hashString(pwd, StandardCharsets.UTF_8)
            .toString();
            
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT codCliente, nome, tipo FROM Usuario WHERE email = ? AND senha = ?");
            st.setString(1, email);
            st.setString(2, pwdHashed);
            
            rs = st.executeQuery();
            if (!rs.next())
                return null;
            
            Usuario user = new Usuario();
            user.setId(rs.getInt("codCliente"));
            user.setNome(rs.getString("nome"));
            user.setTipo(rs.getInt("tipo"));
            
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
