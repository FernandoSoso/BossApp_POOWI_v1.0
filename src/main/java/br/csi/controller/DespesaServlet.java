package br.csi.controller;

import br.csi.model.Caminhao;
import br.csi.model.Despesa;
import br.csi.service.CaminhaoService;
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

@WebServlet("/despesa")
public class DespesaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Trate a requisição como assíncrona
        String cod_frete = req.getParameter("codFrete");
        String offset = req.getParameter("offset");
        List<Despesa> listaDespesa = new DespesaService().selectAll(cod_frete, offset);

        String json = new Gson().toJson(listaDespesa);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operacao = req.getParameter("operacao");
        String codDespesa = req.getParameter("codDespesa");
        String tipo = req.getParameter("tipo");
        String valor = req.getParameter("valor");
        String dataInsercao = req.getParameter("dataInsercao");
        String codFrete = req.getParameter("codFrete");

        if (new DespesaService().persist(operacao, codDespesa, tipo, valor, dataInsercao, codFrete)){
            req.setAttribute("mensagem", "Operação realizada com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao realizar operação!");
            req.setAttribute("erro", "true");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codDespesa = req.getParameter("codDespesa");

        if (new DespesaService().delete(codDespesa)){
            req.setAttribute("mensagem", "Operação realizada com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao realizar operação!");
            req.setAttribute("erro", "true");
        }
    }
}