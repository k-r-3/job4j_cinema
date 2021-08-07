package ru.job4j.cinema.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class PlaceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        HttpSession sc = req.getSession();
        if (!uri.equals("/cinema/")) {
            if (!uri.equals("/cinema/hall")) {
                if (Objects.isNull(sc.getAttribute("place"))) {
                    resp.sendRedirect(req.getContextPath());
                }
            }
        }
        chain.doFilter(req, resp);
    }
}
