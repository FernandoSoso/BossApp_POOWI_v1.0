package br.csi.controller;

import br.csi.service.CaminhaoService;
import br.csi.service.UsuarioService;
import br.csi.util.Retorno;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String async = req.getParameter("async");
        String offset = req.getParameter("offset");
        String limit = req.getParameter("limit");
        String cod = req.getParameter("cod");
        String operacao = req.getParameter("operacao");

        try {
            // Se a requisição for assíncrona
            if ("true".equals(async)) {
                Object retorno = null;

                if ("selectUnique".equals(operacao)){
                    if (cod != null){
                        retorno = new UsuarioService().selectUnique(cod);
                    }
                    else {
                        throw new Exception("Código de usuario inválido!");
                    }
                }
                else if ("selectAll".equals(operacao)){
                    retorno = new UsuarioService().selectAll(offset, limit);
                }

                String json = new Gson().toJson(retorno);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(json);
            }
            else if ("delete".equals(operacao)){
                new UsuarioService().delete(cod);

                resp.sendRedirect(req.getContextPath() + "/usuario");
            }
            else {
                // Redirecione para o servlet de motorista
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/usuario.jsp");
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
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String permissao = req.getParameter("permissao");
        String operacao = req.getParameter("operacao");
        String codExterno = req.getParameter("cod");

        try {
            new UsuarioService().persist(operacao, codExterno, nome, email, senha, permissao);

            resp.sendRedirect(req.getContextPath() + "/usuario");
        }
        catch (Exception e){
            e.printStackTrace();
            req.setAttribute("erro", e.getMessage());

            resp.sendRedirect(req.getContextPath() + "/frete");
        }

    }
}
