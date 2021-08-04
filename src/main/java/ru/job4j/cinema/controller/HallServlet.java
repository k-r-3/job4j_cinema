package ru.job4j.cinema.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cinema.repository.Place;
import ru.job4j.cinema.service.PlaceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HallServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Place place = GSON.fromJson(req.getReader(), Place.class);
        if (PlaceService.getPlaceById(Integer.parseInt(place.getName())).getStatus()) {
            HttpSession sc = req.getSession();
            sc.setAttribute("place", place);
            System.out.println("hallServlet");
            System.out.println(place);
            PlaceService.takeThePlaseOf(place, Integer.parseInt(place.getName()));
        }
    }
}
