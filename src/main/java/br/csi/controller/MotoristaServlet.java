package br.csi.controller;

import br.csi.util.Retorno;
import br.csi.service.MotoristaService;
import com.google.gson.Gson;
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

                if ("selectUnique".equals(operacao)) {
                    if (cod != null) {
                        retorno = new MotoristaService().selectUnique(cod);
                    } else {
                        throw new Exception("Código de motorista inválido!");
                    }
                } else if ("selectAll".equals(operacao)) {
                    retorno = new MotoristaService().selectAll(offset, limit);
                }

                String json = new Gson().toJson(retorno);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(json);
            } else if ("delete".equals(operacao)) {
                new MotoristaService().delete(cod);

                resp.sendRedirect(req.getContextPath() + "/motorista");
            } else {
                // Redirecione para o servlet de motorista
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp");
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
        String codMotorista = req.getParameter("cod");
        String nome = req.getParameter("nome");
        String endereco = req.getParameter("endereco");
        String telefonePrincipal = req.getParameter("telefonePrincipal");
        String telefoneAlternativo = req.getParameter("telefoneAlternativo");
        String telefoneAlternativo2 = req.getParameter("telefoneAlternativo2");
        String codCaminhao = req.getParameter("caminhao");

        try {
            new MotoristaService().persist(operacao, codMotorista, nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2, codCaminhao);

            resp.sendRedirect(req.getContextPath() + "/motorista");
        }
        catch (Exception e){
            e.printStackTrace();

            req.setAttribute("erro", e.getMessage());

            resp.sendRedirect(req.getContextPath() + "/frete");
        }

    }
}
