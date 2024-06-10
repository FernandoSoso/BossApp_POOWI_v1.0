package br.csi.controller;

import br.csi.service.UsuarioService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/pages/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UsuarioService usuarioService = new UsuarioService();

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        if (usuarioService.autenticar(email, senha)) {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/home.jsp");
            rd.forward(req, resp);
        } else {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/login.jsp");
            req.setAttribute("erro", "USUÁRIO OU SENHA INCORRETOS");
            rd.forward(req, resp);
        }
    }
}
