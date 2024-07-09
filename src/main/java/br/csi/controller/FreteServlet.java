package br.csi.controller;

import br.csi.model.Despesa;
import br.csi.model.Frete;
import br.csi.service.DespesaService;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import br.csi.service.FreteService;
import org.jetbrains.annotations.NotNull;

@WebServlet("/frete")
public class FreteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String async = req.getParameter("async");

        if ("true".equals(async)) {
            // Trate a requisição como assíncrona
            String offset = req.getParameter("offset");
            List<Frete> listaFretes = new FreteService().selectAll(offset);

            String json = new Gson().toJson(listaFretes);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            resp.getWriter().write(json);
        } else {
            // Redirecione para o servlet de motorista
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/frete.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String operacao = req.getParameter("operacao");
        String codFrete = req.getParameter("codFrete");
        String origem = req.getParameter("origem");
        String origemData = req.getParameter("origemData");
        String destino = req.getParameter("destino");
        String destinoData = req.getParameter("destinoData");
        String valorTonelada = req.getParameter("valorTonelada");
        String peso = req.getParameter("peso");
        String observacao = req.getParameter("observacao");
        String estado = req.getParameter("estado");
        String codMotorista = req.getParameter("codMotorista");
        String codCaminhao = req.getParameter("codCaminhao");

        if (new FreteService().persist(operacao, codFrete, origem, origemData, destino, destinoData, valorTonelada, peso, observacao, estado, codMotorista, codCaminhao)){
            req.setAttribute("mensagem", "Operação realizada com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao realizar operação!");
            req.setAttribute("erro", "true");
        }

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/frete.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codFrete = req.getParameter("codFrete");

        if (new FreteService().delete(codFrete)){
            req.setAttribute("mensagem", "Operação realizada com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao realizar operação!");
            req.setAttribute("erro", "true");
        }

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/frete.jsp");
        rd.forward(req, resp);
    }
}
