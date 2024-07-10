package br.csi.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class Utf8EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nenhuma configuração inicial necessária
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Define o encoding da requisição e da resposta para UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Define o tipo de conteúdo da resposta para incluir o charset UTF-8
        resp.setContentType("text/html; charset=UTF-8");

        // Continua a cadeia de filtros
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        // Nenhuma ação necessária na destruição do filtro
    }
}