package org.example.fx;

import java.util.Objects;

public class PropertyAssessment implements Comparable<PropertyAssessment> {
    private final int accountNumber;
    private final String address;
    private final long assessedValue;
    private final String neighbourhood;
    private final String assessmentClass;

    private final String col13;
    private final String col14;
    private final String col15;

    private final double lat;
    private final double lon;
    private double adjustedValue;

    // Property Assessment
    public PropertyAssessment(String[] csvRow) {
        this.accountNumber = Integer.parseInt(csvRow[0]);
        this.address = (csvRow[1] + " " + csvRow[2] + " " + csvRow[3]).trim().replaceAll(" +", " ");
        this.neighbourhood = csvRow[5];
        this.assessedValue = parseLongSafe(csvRow[7]);

        this.lat = parseDoubleSafe(csvRow.length > 16 ? csvRow[16] : "0");
        this.lon = parseDoubleSafe(csvRow.length > 17 ? csvRow[17] : "0");

        this.assessmentClass = csvRow[10].toUpperCase();

        if (csvRow.length > 13) {
            this.col13 = csvRow[13];
        } else {
            this.col13 = "";
        }

        if (csvRow.length > 14) {
            this.col14 = csvRow[14];
        }  else {
            this.col14 = "";
        }

        if (csvRow.length > 15) {
            this.col15 = csvRow[15];
        }  else {
            this.col15 = "";
        }
    }

    // Getter for lat
    public double getLat() {
        return lat;
    }

    // Getter for lon
    public double getLon() {
        return lon;
    }

    // Getter for adjusted value
    public double getAdjustedValue() {
        return adjustedValue;
    }

    // Setter for adjusted value
    public void setAdjustedValue() {
        this.adjustedValue = this.assessedValue * 0.005;
    }

    // Getter for assessed value
    public long getAssessedValue() {
        return assessedValue;
    }

    // Getter for Address
    public String getAddress() {
        return address;
    }

    // Getter for assessment class
    public String getAssessmentClass() {
        return assessmentClass;
    }

    // Getter for neighbourhood
    public String getNeighbourhood() {
        return neighbourhood;
    }

    // Checks if classes match
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

    // Checks if is residential class
    public boolean isResidential() {
        if (assessmentClass.equals("RESIDENTIAL") || assessmentClass.equals("OTHER RESIDENTIAL")) {
            return true;
        }

        return assessmentClass.equals("COMMERCIAL") && (isNum(col13) || isNum(col15));
    }

    // Checks if is commercial class
    private boolean isCommercial() {
        if (assessmentClass.equals("COMMERCIAL")) {
            return true;
        }

        return (assessmentClass.equals("RESIDENTIAL") || assessmentClass.equals("OTHER RESIDENTIAL"))
                && isNum(col14);
    }

    // Converts string to long
    private long parseLongSafe(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Converts string to double
    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0;
        }
    }

    // Checks if is a number
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
        return Long.compare(this.assessedValue, other.assessedValue);
    }

    @Override
    public String toString() {
        return String.format("Account: %-10s | Address: %-25s | Neighbourhood: %-20s | Rent: $%.0f/mo",
                accountNumber, address, neighbourhood, adjustedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessedValue, neighbourhood, assessmentClass);
    }
}
