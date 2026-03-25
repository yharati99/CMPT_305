package org.example.fx;

public enum School {
    UofA(53.5232, -113.5263),
    MacEwan(53.5467, -113.5063),
    NAIT(53.5681, -113.5016),
    NorQuest(53.5416, -113.4996);

    private final double lat;
    private final double lon;

    School(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() { return lat; }
    public double getLon() { return lon; }
}