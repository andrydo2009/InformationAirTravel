package com.gridnine.testing;

import java.util.List;

public interface FlightFilter {
    List<Flight> filterFlightTimeUntilNow(List<Flight> flights);
    List<Flight> filterFlightsBeforeDepartureDate(List<Flight> flights);
    List<Flight> filterTimeOnEarthMoreTwoHours(List<Flight> flights);
}
