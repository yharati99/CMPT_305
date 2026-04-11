package org.example.fx;

// Defines the target universities used, storing readable names and coordinates for distance calculations and map plotting.
public enum School {
    UofA("University of Alberta", 53.5232, -113.5263),
    MacEwan("MacEwan University", 53.5467, -113.5063),
    NAIT("NAIT", 53.5681, -113.5016),
    NorQuest("NorQuest College", 53.5416, -113.4996);

    private final String displayName;
    private final double lat;
    private final double lon;

    // Constructs each university constant with its specific location data.
    School(String displayName, double lat, double lon) {
        this.displayName = displayName;
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() { return lat; }
    public double getLon() { return lon; }

    @Override
    public String toString() {
        return displayName;
    }
}