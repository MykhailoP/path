package com.titan.path.algorithms.astar;

import com.titan.path.core.Country;
import org.springframework.stereotype.Component;

@Component
public class HaversineScorer implements IScorer<Country> {

    @Override
    public double computeCost(Country from, Country to) {
        double R = 6372.8; // In kilometers

        double dLat = Math.toRadians(to.getLatitude() - from.getLatitude());
        double dLon = Math.toRadians(to.getLongitude() - from.getLongitude());
        double lat1 = Math.toRadians(from.getLatitude());
        double lat2 = Math.toRadians(to.getLatitude());

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

}
