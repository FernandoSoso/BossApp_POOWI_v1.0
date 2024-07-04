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
}
