package br.csi.controller;

import br.csi.service.UsuarioService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        String mensagem = new UsuarioService().cadastrar(nome, email, senha, permissao);

        if (mensagem.equals("1")) {
            req.setAttribute("mensagem", "Usuário cadastrado com sucesso!");
            req.setAttribute("erro", "false");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/home.jsp");
            rd.forward(req, resp);
        } else {
            req.setAttribute("mensagem", mensagem);
            req.setAttribute("erro", "true");

            doGet(req, resp);
        }
    }
}
