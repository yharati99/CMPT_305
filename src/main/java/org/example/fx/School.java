package org.example.fx;

public enum School {
    UofA("University of Alberta", 53.5232, -113.5263, 300),
    MacEwan("MacEwan University", 53.5467, -113.5063, 200),
    NAIT("NAIT", 53.5681, -113.5016, 100),
    NorQuest("NorQuest College", 53.5416, -113.4996, 150);

    private final String displayName;
    private final double lat;
    private final double lon;
    private final int addedValue; // The constant addend

    School(String displayName, double lat, double lon, int addedValue) {
        this.displayName = displayName;
        this.lat = lat;
        this.lon = lon;
        this.addedValue = addedValue;
    }

    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public int getAddedValue() { return addedValue; }

    // The ComboBox automatically calls toString() to figure out what text to display on the screen
    @Override
    public String toString() {
        return displayName;
    }
}