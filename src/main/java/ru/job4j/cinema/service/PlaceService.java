package ru.job4j.cinema.service;

import ru.job4j.cinema.models.Place;
import ru.job4j.cinema.repository.Storage;

import java.util.*;

public class PlaceService {

    public static Collection<Place> occupiedPlace() {
        return Storage.instOf().findOccupiedPlace();
    }

    public static void takeThePlaseOf(Place place, int sessionId) {
        Storage.instOf().changePlaceStatus(place, sessionId);
    }

    public static void releaseThisPlace(Place place) {
        Storage.instOf().changePlaceStatus(place, Integer.parseInt(place.getName()));
    }

    public static Place getPlaceById(int id) {
        return Storage.instOf().findById(id);
    }

    public static Collection<Place> placeForJson(int id) {
        Place place = getPlaceById(id);
        return new ArrayList<>(Collections.singletonList(place));
    }
}
