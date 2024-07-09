package br.csi.controller;

import br.csi.model.Caminhao;
import br.csi.model.Despesa;
import br.csi.service.CaminhaoService;
import br.csi.service.DespesaService;
import br.csi.service.MotoristaService;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/caminhao")
public class CaminhaoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String async = req.getParameter("async");

        if ("true".equals(async)) {
            // Trate a requisição como assíncrona
            String offset = req.getParameter("offset");
            List<Caminhao> listaCaminhao = new CaminhaoService().selectAll(offset);

            String json = new Gson().toJson(listaCaminhao);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            resp.getWriter().write(json);
        } else {
            // Redirecione para o servlet de motorista
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/caminhao.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operacao = req.getParameter("operacao");
        String codCaminhao = req.getParameter("codCaminhao");
        String placa = req.getParameter("placa");
        String modelo = req.getParameter("modelo");
        String marca = req.getParameter("marca");
        String ano = req.getParameter("ano");
        String capacidade = req.getParameter("capacidade");
        String percentualMotorista = req.getParameter("percentualMotorista");
        String status = req.getParameter("status");
        String codMotorista = req.getParameter("motorista");

        if (new CaminhaoService().persist(operacao,codCaminhao,placa, modelo, marca, ano, capacidade, percentualMotorista, status, codMotorista)){
            req.setAttribute("mensagem", "Operação realizada com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao realizar operação!");
            req.setAttribute("erro", "true");
        }
        
        resp.sendRedirect(req.getContextPath() + "/caminhao");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (new CaminhaoService().delete(req.getParameter("codCaminhao"))){
            req.setAttribute("mensagem", "Caminhão deletado com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao deletar caminhão!");
            req.setAttribute("erro", "true");
        }

        doGet(req, resp);
    }
}
