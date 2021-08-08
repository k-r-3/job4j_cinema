package ru.job4j.cinema.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cinema.models.Place;
import ru.job4j.cinema.service.PlaceService;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class HallServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Collection<Place> occupied = PlaceService.occupiedPlace();
        resp.setContentType("application/json; charset=utf-8");
        OutputStream out = resp.getOutputStream();
        String json = GSON.toJson(occupied);
        out.write(json.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Place place = GSON.fromJson(req.getReader(), Place.class);
        Place status = (PlaceService.getPlaceById(Integer.parseInt(place.getName())));
        if (status.getStatus()) {
            HttpSession sc = req.getSession();
            sc.setAttribute("place", place);
            place = PlaceService.getPlaceById(Integer.parseInt(place.getName()));
            PlaceService.takeThePlaseOf(place, Integer.parseInt(place.getName()));
        }
    }
}
