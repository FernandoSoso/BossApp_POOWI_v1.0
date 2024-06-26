package br.csi.controller;

import br.csi.service.CaminhaoService;
import br.csi.service.MotoristaService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/motorista")
public class MotoristaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String endereco = req.getParameter("endereco");
        String telefonePrincipal = req.getParameter("telefonePrincipal");
        String telefoneAlternativo = req.getParameter("telefoneAlternativo");
        String telefoneAlternativo2 = req.getParameter("telefoneAlternativo2");
        String codCaminhao = req.getParameter("codCaminhao");

        if (new MotoristaService().insert(nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2, codCaminhao)){
            req.setAttribute("mensagem", "Motorista cadastrado com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao cadastrar motorista!");
            req.setAttribute("erro", "true");
        }

        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("motorista", new MotoristaService().selectUnique(Integer.parseInt(req.getParameter("codMotorista"))));

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (new MotoristaService().delete(req.getParameter("codMotorista"))){
            req.setAttribute("mensagem", "Motorista deletado com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao deletar motorista!");
            req.setAttribute("erro", "true");
        }

        doGet(req, resp);
    }

}
