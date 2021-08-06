package ru.job4j.cinema.service;

import ru.job4j.cinema.repository.Place;
import ru.job4j.cinema.repository.Storage;

import java.util.*;

public class PlaceService {

    public static Collection<Place> occupiedPlace() {
        return Storage.instOf().findOccupiedPlace();
    }

    public static void takeThePlaseOf(Place place, int sessionId) {
        System.out.println("takeThePlaseOf");
        Storage.instOf().changePlaceStatus(place, sessionId);
    }

    public static void releaseThisPlace(Place place) {
        System.out.println("place name is " + place.getName());
        Storage.instOf().changePlaceStatus(place, Integer.parseInt(place.getName()));
    }



    public static Place getPlaceById(int id) {
        System.out.println("getPlaceById");
        return Storage.instOf().findById(id);
    }

    public static Place getPlaceBySession(int sessionId) {
        Optional<Place> place = Storage.instOf().findPlaceBySession(sessionId);
        return place.get();
    }

    public static Collection<Place> placeForJson(int id) {
//        return List.of(getPlaceById(id));
        return new ArrayList<>(Collections.singletonList(getPlaceById(id)));
    }
}
