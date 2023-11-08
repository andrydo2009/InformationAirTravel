package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlightFilterImpl implements FlightFilter {
    private final LocalDateTime timeNow;

    public FlightFilterImpl() {
        this.timeNow = LocalDateTime.now();
    }

    /**
     * Метод возвращает список полетов до текущего момента времени. Исключены из тестового набора перелёты.
     * Сравнивается время сегментов полета относительно текущей даты
     *
     * @param flights список полетов
     * @return возвращает список {@link List<Flight>}, содержащий отфильтрованный список полетов
     */
    @Override
    public List<Flight> filterFlightTimeUntilNow(List<Flight> flights) {
        if (flights != null) {
            return flights.stream()
                    .filter(flight -> flight.getSegments().stream()
                            .anyMatch(segment -> timeNow.isBefore(segment.getDepartureDate())))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Метод возвращает список полетов с сегментами прилёта раньше даты вылета
     *
     * @param flights полный список полетов
     * @return возвращает список {@link List<Flight>}, содержащий отфильтрованный список полетов
     */
    @Override
    public List<Flight> filterFlightsBeforeDepartureDate(List<Flight> flights) {
        List<Flight> result = new ArrayList<>();
        if (flights != null) {
            flights.forEach(flight -> flight.getSegments()
                    .stream()
                    .filter(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())).limit(1)
                    .forEach(segment -> result.add(flight)));
            return result;
        }
        return result;
    }

    /**
     * Метод осуществляет фильтрацию списка рейсов в соответствии с условием, что общее время, проведённое на земле,
     * не превышает два часа. Учтены перелеты, у которых количество сегментов меньше или равно 1.
     *
     * @param flights полный список полетов
     * @return возвращает список {@link List<Flight>}, содержащий отфильтрованный список полетов
     */
    @Override
    public List<Flight> filterTimeOnEarthMoreTwoHours(List<Flight> flights) {
        List<Flight> result = new ArrayList<>();
        if (flights != null) {
            result = flights.stream()
                    .filter(flight -> flight.getSegments().size() > 1)
                    .filter(flight -> {
                        long countHours = IntStream.range(1, flight.getSegments().size())
                                .map(i -> Math.toIntExact(checkTimeDifference(flight.getSegments().get(i - 1).getArrivalDate(),
                                        flight.getSegments().get(i).getDepartureDate())))
                                .sum();
                        return countHours <= 2;
                    })
                    .collect(Collectors.toList());
            result.addAll(flights.stream()
                    .filter(flight -> flight.getSegments().size() <= 1)
                    .toList());
            return result;
        }
        return result;
    }

    /**
     * Служебный метод для вычисления разницы между двумя промежутками времени(прибытия и отбытия)
     *
     * @param arrival   время прибытия
     * @param departure время отбытия
     * @return возвращает целочисленное значение типа long
     */
    private long checkTimeDifference(LocalDateTime arrival, LocalDateTime departure) {
        return ChronoUnit.HOURS.between(arrival, departure);
    }
}
