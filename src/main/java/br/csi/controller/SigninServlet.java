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

        if (new UsuarioService().cadastrar(nome, email, senha, permissao)) {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/home.jsp");
            rd.forward(req, resp);
        } else {
            req.setAttribute("nome", nome);
            req.setAttribute("email", email);
            req.setAttribute("permissao", permissao);
            System.out.println("Campos obrigatórios inválidos!");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/signin.jsp?erro=true");
            req.setAttribute("erro", "Campos obrigatórios inválidos!");
            rd.forward(req, resp);
        }
    }
}
