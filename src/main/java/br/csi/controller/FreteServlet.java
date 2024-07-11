package br.csi.controller;

import br.csi.util.Retorno;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import br.csi.service.FreteService;

@WebServlet("/frete")
public class FreteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Armazenamento dos parâmetros da requisição
        String async = req.getParameter("async");
        String cod = req.getParameter("cod");
        String operacao = req.getParameter("operacao");
        String offset = req.getParameter("offset");
        String limit = req.getParameter("limit");

        // Se a requisição for assíncrona
        try {
            if ("true".equals(async)) {
                Object retorno = null;
                if ("selectUnique".equals(operacao)) {
                    if (cod != null) {
                        retorno = new FreteService().selectUnique(cod);
                    } else {
                        throw new Exception("Código de caminhão inválido!");
                    }
                } else if ("selectAll".equals(operacao)) {
                    retorno = new FreteService().selectAll(offset, limit);
                }

                String json = new Gson().toJson(retorno);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(json);
            } else if ("delete".equals(operacao)) {
                new FreteService().delete(cod);

                resp.sendRedirect(req.getContextPath() + "/frete");
            } else {
                // Redirecione para o servlet de motorista
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/frete.jsp");
                rd.forward(req, resp);
            }
        }
        catch (Exception e){
            String json = new Gson().toJson(new Retorno(e.getMessage()));

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            resp.getWriter().write(json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operacao = req.getParameter("operacao");
        String codFrete = req.getParameter("cod");
        String origem = req.getParameter("origem");
        String destino = req.getParameter("destino");
        String valorTonelada = req.getParameter("valorTonelada");
        String peso = req.getParameter("peso");
        String observacao = req.getParameter("observacao");
        String estado = req.getParameter("estado");
        String codMotorista = req.getParameter("motorista");
        String codCaminhao = req.getParameter("caminhao");


        try {
            new FreteService().persist(operacao, codFrete, origem, destino, valorTonelada, peso, observacao, estado, codMotorista, codCaminhao);

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/frete.jsp");
            rd.forward(req, resp);
        }
        catch (Exception e){
            e.printStackTrace();
            req.setAttribute("erro", e.getMessage());

            resp.sendRedirect(req.getContextPath() + "/frete");
        }
    }
}
