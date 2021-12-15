/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classe.Concurso;
import Classe.EntradaConcurso;
import Classe.Imagem;
import Classe.Usuario;
import DAO.ConcursoDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Victor
 */
@WebServlet(name = "ConcursoControladora", urlPatterns = {"/ConcursoControladora"})
@MultipartConfig
public class ConcursoControladora extends HttpServlet {

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
        
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        
        if (action == null) {
            response.sendRedirect("portal.jsp?erro=3");
            return;
        }
        
        if (action.equals("abrirconcurso")) {
            
            // Validate Session
            HttpSession session = request.getSession(false);
            if ( session == null || session.getAttribute("id") == null ) {
                // Error will be automatically generated if the user has no session, which will take him to the login page with error message.
                response.sendRedirect("portal.jsp");
                return;
            }
            
            Integer userId = (Integer) session.getAttribute("id");
            
            ConcursoDAO concursoDAO = new ConcursoDAO();
            Concurso concurso = concursoDAO.buscarConcurso(Integer.valueOf(id));
            if (concurso == null) {
                response.sendRedirect("portal.jsp?erro=2");
                return;
            }
            
            RequestDispatcher rd;
           
            Date date = new Date();
            
            // Verificar se concurso está aberto no momento.
            if (concurso.getInicio().before(date) && concurso.getFim().after(date) ) {
                // Concurso Aberto
                if ( concursoDAO.usuarioParticipa(Integer.valueOf(id), userId) ) {
                    // Usuario participa do concurso.
                    List<EntradaConcurso> listaEntrada = concursoDAO.exibirEntradas(concurso);
                    request.setAttribute("listaEntrada", listaEntrada);
                    request.setAttribute("concurso", concurso);
                    rd = getServletContext().getRequestDispatcher("/WEB-INF/concurso_exibir_hidden.jsp");
                    rd.forward(request, response);
                    return;
                } else {
                    // Usuario ainda não participa do concurso, levar para página de apresentação / cadastro do concurso.
                    request.setAttribute("concurso", concurso);
                    rd = getServletContext().getRequestDispatcher("/WEB-INF/concurso_entrada_hidden.jsp");
                    rd.forward(request, response);
                    return;
                }
            } else if (concurso.getFim().before(date)) {
                // Concurso Expirado
                List<EntradaConcurso> listaEntrada = concursoDAO.exibirEntradas(concurso);
                request.setAttribute("listaEntrada", listaEntrada);
                request.setAttribute("concurso", concurso);
                
                EntradaConcurso entrada = concursoDAO.exibirVencedor(concurso);
                request.setAttribute("vencedor", entrada);
                rd = getServletContext().getRequestDispatcher("/WEB-INF/concurso_exibir_hidden.jsp");
                rd.forward(request, response);
                return;
            } else {
                // Concurso Futuro (Erro)
                response.sendRedirect("portal.jsp?erro=2");
                return;
            }
            
        } else if (action.equals("abrirentrada")) {
            // Validate Session
            HttpSession session = request.getSession(false);
            if ( session == null || session.getAttribute("id") == null ) {
                // Error will be automatically generated if the user has no session, which will take him to the login page with error message.
                response.sendRedirect("portal.jsp");
                return;
            }
            
            String codEntrada = request.getParameter("id");
            if (codEntrada == null || StringUtils.isNumeric(codEntrada) == false) {
                response.sendRedirect("portal.jsp?erro=3");
                return;
            }
            
            ConcursoDAO concursoDAO = new ConcursoDAO();
            EntradaConcurso entrada = concursoDAO.buscarEntrada( Integer.valueOf(codEntrada) );
            if (entrada == null) {
                response.sendRedirect("portal.jsp?erro=2");
                return;
            }
            
            // Verificar se concurso existe
            Concurso concurso = concursoDAO.buscarConcurso(entrada.getConcurso().getCodConcurso());
            if (concurso == null) {
                response.sendRedirect("portal.jsp?erro=2");
                return;
            }
            
            // Verificar se concurso dessa entrada já acabou
            Date date = new Date();
            if ( !( concurso.getInicio().before(date) && concurso.getFim().after(date) ) ) {
                request.setAttribute("inativo", true);
            }
            
            Integer codCliente = (Integer) session.getAttribute("id");
            // Checar por / Contar visualização
            concursoDAO.contarVisualizacao(Integer.valueOf(codEntrada), codCliente);
            
            Boolean likeStatus = concursoDAO.getAvaliacao( Integer.valueOf(codEntrada), codCliente);
            
            RequestDispatcher rd;
            request.setAttribute("entrada", entrada);
            request.setAttribute("likeStatus", likeStatus);
            
            rd = getServletContext().getRequestDispatcher("/WEB-INF/entrada_exibir_hidden.jsp");
            rd.forward(request, response);
            return;
        } else if (action.equals("avaliarentrada")) {
            // Validate Session
            HttpSession session = request.getSession(false);
            if ( session == null || session.getAttribute("id") == null ) {
                // Error will be automatically generated if the user has no session, which will take him to the login page with error message.
                response.sendRedirect("portal.jsp");
                return;
            }
            
            String codEntrada = request.getParameter("id");
            String oldLikeStatus = request.getParameter("likeStatus");
            if (codEntrada == null || StringUtils.isNumeric(codEntrada) == false || (!oldLikeStatus.equals("true") && !oldLikeStatus.equals("false")) ) {
                response.sendRedirect("portal.jsp?erro=3");
                return;
            }
            
            ConcursoDAO concursoDAO = new ConcursoDAO();
            EntradaConcurso entrada = concursoDAO.buscarEntrada( Integer.valueOf(codEntrada) );
            if (entrada == null) {
                response.sendRedirect("portal.jsp?erro=2");
                return;
            }
            
            // Verificar se concurso existe
            Concurso concurso = concursoDAO.buscarConcurso(entrada.getConcurso().getCodConcurso());
            if (concurso == null) {
                response.sendRedirect("portal.jsp?erro=2");
                return;
            }
            
            // Verificar se concurso dessa entrada já acabou, bloquear avaliação
            Date date = new Date();
            if ( !(concurso.getInicio().before(date) && concurso.getFim().after(date)) ) {
                response.sendRedirect("portal.jsp?erro=4");
                return;
            }
            
            Integer codCliente = (Integer) session.getAttribute("id");
            Boolean likeStatus = concursoDAO.getAvaliacao( Integer.valueOf(codEntrada), codCliente);
            
            RequestDispatcher rd;
            
            System.out.println("Old Like Status : " + Boolean.valueOf(oldLikeStatus));
            System.out.println("Exp Like Status : " + likeStatus);
            
            if ( !Objects.equals(Boolean.valueOf(oldLikeStatus), likeStatus) ) { // Vote is different than expected, so don't register and reload page to right state.
                /*
                request.setAttribute("entrada", entrada);
                request.setAttribute("likeStatus", likeStatus);
                rd = getServletContext().getRequestDispatcher("/WEB-INF/entrada_exibir_hidden.jsp");
                rd.forward(request, response);
                return;
                */
                rd = getServletContext().getRequestDispatcher("/ConcursoControladora?action=abrirentrada&id=" + codEntrada);
                rd.forward(request, response);
                return;
            }
            
            System.out.println("Step 2");
            
            likeStatus = concursoDAO.setAvaliacao(Integer.valueOf(codEntrada), codCliente, likeStatus);
            
            request.setAttribute("entrada", entrada);
            request.setAttribute("likeStatus", likeStatus);
            rd = getServletContext().getRequestDispatcher("/WEB-INF/entrada_exibir_hidden.jsp");
            rd.forward(request, response);
            return;
            
        } else {
            response.sendRedirect("index.jsp?msg=2");
            return;
        }
        
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
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("portal.jsp?erro=3");
            return;
        }

        if (action.equals("cadastrarconcurso")) {
            String nome = request.getParameter("nome");
            String desc = request.getParameter("desc");
            String datepickerStart = request.getParameter("datepicker-start");
            String datepickerEnd = request.getParameter("datepicker-end");
            
            String minFotos = request.getParameter("minFotos");
            String maxFotos = request.getParameter("maxFotos");
            
            System.out.println("DATE STRING (1) : " + datepickerStart);
            
            // Processar / Salvar Imagem
            Part filePart = request.getPart("logoInput");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            System.out.println("File name : " + fileName);
            
            if (   "".equals(nome)
                || "".equals(desc)
                || "".equals(datepickerStart)
                || "".equals(datepickerEnd)
                || "".equals(fileName)
                || "".equals(minFotos)
                || "".equals(maxFotos)
                    ) {
                response.sendRedirect("cadastrar_concurso.jsp?msg=1");
                return;
            }
            
            String ext = FilenameUtils.getExtension(fileName);
            
            if (!"png".equals(ext)) {
                response.sendRedirect("cadastrar_concurso.jsp?msg=2");
                return;
            }
            
            if (fileName.length() > 20) {
                response.sendRedirect("cadastrar_concurso.jsp?msg=3");
                return; 
            }
            
            InputStream input;
            try {
                input = filePart.getInputStream();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
            ConcursoDAO concursoDAO = new ConcursoDAO();
            String id = concursoDAO.criarArquivo(ext, input);
            
            Concurso concurso;
            // Inserção Concurso
            try {
                concurso = new Concurso();
                concurso.setNome(nome);
                concurso.setDescricao(desc);
                // Verificar antes se data fim vem depois da data de inicio.
                concurso.setInicio(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(datepickerStart));
                concurso.setFim(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(datepickerEnd));
                concurso.setCodImagem(id);
                concurso.setMinFotos( Integer.parseInt(minFotos) );
                concurso.setMaxFotos( Integer.parseInt(maxFotos) );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
            concursoDAO.inserirConcurso(concurso);
            response.sendRedirect("portal.jsp?msg=2");
        } else if (action.equals("cadastrarentrada")) {
            String idConcurso = request.getParameter("idConcurso");
            
            ConcursoDAO concursoDAO = new ConcursoDAO();
            Concurso concurso = concursoDAO.buscarConcurso(Integer.valueOf(idConcurso));
            
            
            // Concurso não encontrado.
            if (concurso == null) {
                response.sendRedirect("portal.jsp?erro=2");
                return;
            }
            
            
            // Validate Session
            HttpSession session = request.getSession(false);
            if ( session == null || session.getAttribute("id") == null ) {
                // Error will be automatically generated if the user has no session, which will take him to the login page with error message.
                response.sendRedirect("portal.jsp");
                return;
            }
            
            Integer userId = (Integer) session.getAttribute("id");
            
            // - Campos de Entrada
            List<String> listTitulo = new ArrayList<>();
            List<String> listDescricao = new ArrayList<>();
            List<Part> listPart = new ArrayList<>();
            
            for (int i=1; i < 6; i++) {
                String titulo = request.getParameter("titulo" + i);
                String desc = request.getParameter("desc" + i);
                Part filePart = request.getPart("logoInput" + i);
                
                if (filePart == null || filePart.getSize() == 0)
                    continue;
                
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                
                String ext = FilenameUtils.getExtension(fileName);
                if (!"png".equals(ext)) {
                    response.sendRedirect("ConcursoControladora?action=abrirconcurso&id=" + concurso.getCodConcurso() + "&msg=2");
                    return;
                }
                
                if (   "".equals(titulo)
                    || "".equals(desc)
                    || "".equals(fileName)
                        ) {
                    continue;
                }
                
                listTitulo.add(titulo);
                listDescricao.add(desc);
                listPart.add(filePart);
            }
            
            
            // Verificar se todos os dados foram preenchidos corretamente.
            if (listTitulo.size() < concurso.getMinFotos()) {
                response.sendRedirect("ConcursoControladora?action=abrirconcurso&id=" + concurso.getCodConcurso() + "&msg=1");
                return;
            }
            
            // Checar se o concurso encontra-se aberto.
            
            // Preparar lista com dados para enviar ao DAO
            
            // Definição Usuário
            
            
            Usuario user = new Usuario();
            user.setId(userId);
            
            List<EntradaConcurso> listEntrada = new ArrayList<>();
            for (int i = 0; i < listTitulo.size(); i++) {
                EntradaConcurso entradaConcurso = new EntradaConcurso();
                entradaConcurso.setTitulo(listTitulo.get(i));
                entradaConcurso.setDescricao(listDescricao.get(i));
                
                InputStream input;
                try {
                    input = listPart.get(i).getInputStream();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                
                // Imagem
                Imagem img = new Imagem();
                img.setBlobImagem(input);
                entradaConcurso.setImg(img);
                
                // Usuario
                entradaConcurso.setUsuario(user);
                
                // Concurso
                entradaConcurso.setConcurso(concurso);
                listEntrada.add(entradaConcurso);
            }
            
            concursoDAO.inserirEntradas(listEntrada);
            
            
            response.sendRedirect("portal.jsp?msg=3");
            return;
            
            // Fazer função DAO de inserção.
            // Exibição das imagens de um concurso em ordem aleatoria (por enquanto).
            // Funcionalidade de abrir imagem (visualização) e like.
            // Alterar exibição de imagens de um concurso para ordernar por usuários com mais quantidades de likes totais naquele concurso.
            // Ao abrir visualização de concurso que já expirou, mostrar vencedor em destaque, restantes, e não permitir inscrição.
            
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
