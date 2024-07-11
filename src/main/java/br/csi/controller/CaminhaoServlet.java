package br.csi.controller;

import br.csi.service.CaminhaoService;
import br.csi.util.Retorno;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/caminhao")
public class CaminhaoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Armazenamento dos parâmetros da requisição
        String async = req.getParameter("async");
        String cod = req.getParameter("cod");
        String operacao = req.getParameter("operacao");
        String offset = req.getParameter("offset");
        String limit = req.getParameter("limit");

        try {
            // Se a requisição for assíncrona
            if ("true".equals(async)) {
                Object retorno = null;

                if ("selectUnique".equals(operacao)){
                    if (cod != null){
                        retorno = new CaminhaoService().selectUnique(cod);
                    }
                    else {
                        throw new Exception("Código de caminhão inválido!");
                    }
                }
                else if ("selectAll".equals(operacao)){
                    retorno = new CaminhaoService().selectAll(offset, limit);
                }

                String json = new Gson().toJson(retorno);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(json);
            }
            else if ("delete".equals(operacao)){
                new CaminhaoService().delete(cod);

                resp.sendRedirect(req.getContextPath() + "/caminhao");
            }
            else {
                // Redirecione para o servlet de motorista
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/caminhao.jsp");
                rd.forward(req, resp);
            }
        }
        catch (Exception e){
            resp.sendRedirect(req.getContextPath() + "/caminhao");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operacao = req.getParameter("operacao");
        String codCaminhao = req.getParameter("cod");
        String placa = req.getParameter("placa");
        String modelo = req.getParameter("modelo");
        String marca = req.getParameter("marca");
        String ano = req.getParameter("ano");
        String capacidade = req.getParameter("capacidade");
        String percentualMotorista = req.getParameter("percentualMotorista");
        String codMotorista = req.getParameter("motorista");

        try{
            new CaminhaoService().persist(operacao,codCaminhao,placa, modelo, marca, ano, capacidade, percentualMotorista, codMotorista);

            resp.sendRedirect(req.getContextPath() + "/caminhao");
        }
        catch (Exception e){
            e.printStackTrace();
            req.setAttribute("erro", e.getMessage());

            resp.sendRedirect(req.getContextPath() + "/frete");
        }
    }
}
