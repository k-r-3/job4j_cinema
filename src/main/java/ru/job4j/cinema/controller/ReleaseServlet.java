package ru.job4j.cinema.controller;

import ru.job4j.cinema.repository.Place;
import ru.job4j.cinema.service.PlaceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ReleaseServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sc = req.getSession();
        Place place = (Place) sc.getAttribute("place");
        PlaceService.releaseThisPlace(place);
        sc.setAttribute("place", null);
        resp.sendRedirect(req.getContextPath());
    }
}
