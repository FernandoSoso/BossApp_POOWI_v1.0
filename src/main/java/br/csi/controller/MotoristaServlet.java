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
        String cpf = req.getParameter("cpf");
        String endereco = req.getParameter("endereco");
        String telefonePrincipal = req.getParameter("telefonePrincipal");
        String telefoneAlternativo = req.getParameter("telefoneAlternativo");
        String telefoneAlternativo2 = req.getParameter("telefoneAlternativo2");
        String codCaminhao = req.getParameter("codCaminhao");

        if (new MotoristaService().insert(nome, cpf, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2, Integer.parseInt(codCaminhao))){
            req.setAttribute("mensagem", "Motorista cadastrado com sucesso!");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp?error=false");
            rd.forward(req, resp);
        } else {
            req.setAttribute("mensagem", "Erro ao cadastrar motorista!");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp?error=true");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("motorista", new MotoristaService().selectUnique(Integer.parseInt(req.getParameter("codMotorista"))));

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (new MotoristaService().delete(Integer.parseInt(req.getParameter("codMotorista")))){
            req.setAttribute("mensagem", "Motorista deletado com sucesso!");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp?error=false");
            rd.forward(req, resp);
        } else {
            req.setAttribute("mensagem", "Erro ao deletar motorista!");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp?error=true");
            rd.forward(req, resp);
        }
    }

}
