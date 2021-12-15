/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classe.Usuario;
import DAO.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Victor
 */

@WebServlet(name = "CadastroControladora", urlPatterns = {"/CadastroControladora"})
public class CadastroControladora extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorCadastro</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorCadastro at " + request.getContextPath() + "</h1>");
            HttpSession session = request.getSession();
            String nome2 = (String) session.getAttribute("nome");
            out.println("Nome : " + nome2);
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String action = request.getParameter("action");
        if (action.equals("cadastro")) {
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String confSenha = request.getParameter("confSenha");
            String termos = request.getParameter("termos");
            
            System.out.println("TERMOS : " + termos);
            
            if (   "".equals(nome)
                || "".equals(email)
                || "".equals(senha)
                || "".equals(confSenha)
                || termos == null
                || !"on".equals(termos) )
            {
                request.setAttribute("erro", "Um dos campos não foi preenchido.");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/cadastro.jsp");
                rd.forward(request, response);
                return;
            }
            
            if ( !senha.equals(confSenha) ) {
                request.setAttribute("erro", "As senhas não são iguais.");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/cadastro.jsp");
                rd.forward(request, response);
                return;
            }
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean jaExiste = usuarioDAO.verificarEmail(email);
            if (jaExiste) {
                request.setAttribute("erro", "Já existe uma conta com esse email.");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/cadastro.jsp");
                rd.forward(request, response);
                return;
            }
            
            Usuario usuario = null;
            try {
                usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setEmail(email);
                
                String senhaSha256 = Hashing.sha256()
                .hashString(senha, StandardCharsets.UTF_8)
                .toString();
                usuario.setSenha(senhaSha256);
                
                usuarioDAO.inserir(usuario);
                response.sendRedirect("login.jsp?sucesso=true");
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
       
        } else if (action.equals("login")) {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            
            System.out.println("Email : " + email);
            System.out.println("Senha : " + senha);
            
            if (   "".equals(email)
                || "".equals(senha) ) {
                response.sendRedirect("login.jsp?erro=1");
                return;
            }
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            boolean jaExiste = usuarioDAO.verificarEmail(email);
            if (!jaExiste) {
                response.sendRedirect("login.jsp?erro=2");
                return;
            }
            
            Usuario user = usuarioDAO.autenticar(email, senha);
            if (user == null) {
                response.sendRedirect("login.jsp?erro=3");
                return;
            }
            
            // Cria-se a sessão, como a autenticação foi correta.
            HttpSession session = request.getSession(true);
            session.setAttribute("id", user.getId());
            session.setAttribute("email", email);
            session.setAttribute("nome", user.getNome());
            session.setAttribute("tipo", user.getTipo());
            
 
            // FOR TESTING, REMOVE LATER
            //processRequest(request, response);
            
            response.sendRedirect("portal.jsp?msg=1");
            return;
            
        } else if (action.equals("deslogar")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            
            response.sendRedirect("index.jsp?msg=1");
        }
    
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
