package com.titan.path.core;

import java.util.StringJoiner;

public class Country implements IGraphNode {
    private final String id;
    private final String name;
    private final double latitude;
    private final double longitude;

    private double score;

    public Country(String id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Country.class.getSimpleName() + "[", "]").add("id='" + id + "'")
                .add("name='" + name + "'").add("latitude=" + latitude).add("longitude=" + longitude).toString();
    }

}
