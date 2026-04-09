package org.example.fx;

import java.util.Objects;
public class PropertyAssessment implements Comparable<PropertyAssessment> {
    private final int accountNumber;
    private final long assessedValue;
    private final String neighbourhood;
    private final String assessmentClass;
    private final double lat;
    private final double lon;
    private double adjustedValue;

    private final String col13;
    private final String col14;
    private final String col15;

    public PropertyAssessment(String[] csvRow) {
        this. accountNumber = Integer.parseInt(csvRow[0]);
        this.neighbourhood = csvRow[5];
        this.assessedValue = parseLongSafe(csvRow[7]);
        this.assessmentClass = csvRow[10].toUpperCase();
        this.lat = csvRow.length > 16 ? parseDoubleSafe(csvRow[16]) : 0;
        this.lon = csvRow.length > 17 ? parseDoubleSafe(csvRow[17]) : 0;

        if (csvRow.length >13) {
            this.col13 = csvRow[13];
        } else {
            this.col13 = "";
        }

        if (csvRow.length >14) {
            this.col14 = csvRow[14];
        }  else {
            this.col14 = "";
        }

        if (csvRow.length >15) {
            this.col15 = csvRow[15];
        }  else {
            this.col15 = "";
        }
    }

    public void setAdjustedValue(School school) {
        double baseRent = this.assessedValue * 0.01;

        // Calculate distance in km
        double distance = calculateDistanceTo(school);

        // Apply addend ONLY if within 2.0 km
        if (distance <= 2.0) {
            this.adjustedValue = baseRent + school.getAddedValue();
        } else {
            this.adjustedValue = baseRent;
        }
    }

    private double calculateDistanceTo(School s) {
        double R = 6371; // Earth radius in km
        double dLat = Math.toRadians(s.getLat() - this.lat);
        double dLon = Math.toRadians(s.getLon() - this.lon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(s.getLat())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public double getLat() {
        return lat;
    }
    public double getLon() {
        return lon;
    }

    public double getAdjustedValue() {
        return adjustedValue;
    }

    public long getAssessedValue() {
        return assessedValue;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }


    public boolean matchesClass(String searchClass) {
        searchClass = searchClass.toUpperCase();

        if (searchClass.equals("RESIDENTIAL")) {
            return isResidential();
        }
        else if (searchClass.equals("COMMERCIAL")) {
            return isCommercial();
        }

        return this.assessmentClass.equals(searchClass);
    }

    public boolean isResidential() {
        if (assessmentClass.equals("RESIDENTIAL") || assessmentClass.equals("OTHER RESIDENTIAL")) {
            return true;
        }

        return assessmentClass.equals("COMMERCIAL") && (isNum(col13) || isNum(col15));
    }

    private boolean isCommercial() {
        if (assessmentClass.equals("COMMERCIAL")) {
            return true;
        }

        return (assessmentClass.equals("RESIDENTIAL") || assessmentClass.equals("OTHER RESIDENTIAL"))
                && isNum(col14);
    }

    private long parseLongSafe(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double parseDoubleSafe(String value) {
        try { return Double.parseDouble(value); } catch (Exception e) { return 0.0; }
    }

    private boolean isNum(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            return Double.parseDouble(value.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public int compareTo(PropertyAssessment other) {
        //return Long.compare(this.assessedValue, other.assessedValue);
        return Double.compare(this.adjustedValue, other.adjustedValue);
    }

    @Override
    public String toString() {

        return String.format("Account: %-20s | Neighbourhood: %-20s | Value: $%d       |       Rent: $%.2f",
                accountNumber, neighbourhood, assessedValue, adjustedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessedValue, neighbourhood, assessmentClass);
    }
}