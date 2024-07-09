package br.csi.controller;

import br.csi.model.Usuario;
import br.csi.service.UsuarioService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/signin")
public class SigninServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/signin.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String permissao = req.getParameter("permissao");

        try {
            Usuario usuario = new UsuarioService().insert(nome, email, senha, permissao);

            if (usuario != null) {
                req.setAttribute("mensagem", "Usuário cadastrado com sucesso!");
                req.setAttribute("erro", "false");

                usuario = new UsuarioService().auth(email, senha);

                HttpSession session = req.getSession(true);

                session.setAttribute("usuario", usuario);
                session.setMaxInactiveInterval(1800);

                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/home.jsp");
                rd.forward(req, resp);
            }
            else {
                req.setAttribute("mensagem", "Erro ao cadastrar usuário!");
                req.setAttribute("erro", "true");
            }
        }
        catch (IllegalArgumentException e) {
            req.setAttribute("mensagem", e.getMessage());
            req.setAttribute("erro", "true");
        }

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/signin.jsp");
        rd.forward(req, resp);
    }
}
