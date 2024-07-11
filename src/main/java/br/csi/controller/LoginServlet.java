package br.csi.controller;

import br.csi.model.Usuario;
import br.csi.service.UsuarioService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsuarioService usuarioService = new UsuarioService();

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        try {
            Usuario usuario = usuarioService.auth(email, senha);

            HttpSession session = req.getSession(true);

            session.setAttribute("usuario", usuario);
            session.setMaxInactiveInterval(1800);

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/frete.jsp");
            rd.forward(req, resp);
        }
        catch (Exception e){
            e.printStackTrace();

            req.setAttribute("erro", e.getMessage());

            resp.sendRedirect(req.getContextPath() + "/login?erro=true");
        }
    }
}
