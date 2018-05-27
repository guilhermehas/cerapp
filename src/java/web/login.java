/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import auxiliar.LoginController;
import Estabelecimento.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guilherme
 */
public class login extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Estabelecimento e = new Estabelecimento();      
            
            try{
                e.setEmail(request.getParameter("email"));
                e.setSenha(request.getParameter("senha"));              
            }catch(Exception ex){
                JsonObject json = (JsonObject) Json.createObjectBuilder()
                    .add("status", false)
                    .add("mensagem", "Problema no email ou senha").build();
                    out.println(json);
                throw new Exception(ex);
            }
            
            try{
                if(!new LoginController().efetuaLogin(e, request.getSession())){
					JsonObject json = Json.createObjectBuilder()
                    .add("status", false)
                    .add("mensagem", "Usuario ou senha inválidos").build();
                    out.println(json);
				}
            } catch(Exception ex){
                JsonObject json = Json.createObjectBuilder()
                    .add("status", false)
                    .add("mensagem", "Erro no banco de dados").build();
                    out.println(json);
                throw new Exception(ex);
            }
            
            JsonObject json = (JsonObject) Json.createObjectBuilder()
                    .add("status", true)
                    .add("mensagem", "Acessado com sucesso")
                    .build();
                    out.println(json);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
