package br.csi.controller;

import br.csi.model.Frete;
import br.csi.model.Motorista;
import br.csi.service.MotoristaService;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/motorista")
public class MotoristaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String async = req.getParameter("async");
        String codMotorista = req.getParameter("codMotorista");

        if ("true".equals(async)) {
            // Trate a requisição como assíncrona
            if (codMotorista != null) {
                Motorista motorista = new MotoristaService().selectUnique(codMotorista);
                List<Motorista> listaMotoristas = new ArrayList<>();
                listaMotoristas.add(motorista);
                String json = new Gson().toJson(listaMotoristas);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(json);
            }
            else{
                String offset = req.getParameter("offset");
                List<Motorista> listaMotoristas = new MotoristaService().selectAll(offset);

                String json = new Gson().toJson(listaMotoristas);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(json);
            }
        } else {
            // Redirecione para o servlet de motorista
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/motorista.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operacao = req.getParameter("operacao");
        String codMotorista = req.getParameter("codMotorista");
        String nome = req.getParameter("nome");
        String endereco = req.getParameter("endereco");
        String telefonePrincipal = req.getParameter("telefonePrincipal");
        String telefoneAlternativo = req.getParameter("telefoneAlternativo");
        String telefoneAlternativo2 = req.getParameter("telefoneAlternativo2");
        String codCaminhao = req.getParameter("caminhao");

        if (new MotoristaService().persist(operacao, codMotorista, nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2, codCaminhao)){
            req.setAttribute("mensagem", "Operação realizada com sucesso!");
            req.setAttribute("erro", "false");
        } else {
            req.setAttribute("mensagem", "Erro ao realizar operação!");
            req.setAttribute("erro", "true");
        }

        resp.sendRedirect(req.getContextPath() + "/motorista");
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

        resp.sendRedirect(req.getContextPath() + "/motorista");
    }

}
