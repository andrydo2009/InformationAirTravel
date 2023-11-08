package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterImpl flightFilter = new FlightFilterImpl();
        System.out.println("Список всех полетов:");
        printResultFilterList(flights);
        System.out.println("\n Предстоящие полеты,исключая полеты до текущего момента времени: ");
        printResultFilterList(flightFilter.filterFlightTimeUntilNow(flights));
        System.out.println("\n Список полетов без сегментов с датой прилёта раньше даты вылета.");
        printResultFilterList(flightFilter.filterFlightsBeforeDepartureDate(flights));
        System.out.println("\n Список полетов, где общее время, проведённое на земле, не превышает два часа");
        printResultFilterList(flightFilter.filterTimeOnEarthMoreTwoHours(flights));
    }
    public static void printResultFilterList(List<Flight> list) {
        list.forEach(System.out::println);
    }
}
