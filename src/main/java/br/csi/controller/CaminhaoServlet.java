package br.csi.controller;

import br.csi.model.Caminhao;
import br.csi.service.CaminhaoService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/caminhao")
public class CaminhaoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("listaCaminhao", new CaminhaoService().selectAll());

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/caminhao.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String placa = req.getParameter("placa");
        String modelo = req.getParameter("modelo");
        String marca = req.getParameter("marca");
        String ano = req.getParameter("ano");
        String capacidade = req.getParameter("capacidade");
        String percentualMotorista = req.getParameter("percentualMotorista");
        String status = req.getParameter("status");
        String motorista = req.getParameter("motorista");

        if (new CaminhaoService().insert(placa, modelo, marca, Integer.parseInt(ano), Integer.parseInt(capacidade), Double.parseDouble(percentualMotorista), status, Integer.parseInt(motorista))){
            req.setAttribute("mensagem", "Caminhão cadastrado com sucesso!");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/caminhao.jsp?error=false");
            rd.forward(req, resp);
        } else {
            req.setAttribute("mensagem", "Erro ao cadastrar caminhão!");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/caminhao.jsp?error=true");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("caminhao", new CaminhaoService().selectUnique(Integer.parseInt(req.getParameter("codCaminhao"))));

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/caminhao.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (new CaminhaoService().delete(Integer.parseInt(req.getParameter("codCaminhao")))){
            req.setAttribute("mensagem", "Caminhão deletado com sucesso!");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/caminhao.jsp?error=false");
            rd.forward(req, resp);
        } else {
            req.setAttribute("mensagem", "Erro ao deletar caminhão!");

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/caminhao.jsp?error=true");
            rd.forward(req, resp);
        }
    }
}
