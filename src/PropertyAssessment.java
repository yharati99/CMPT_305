
import java.util.Objects;
public class PropertyAssessment implements Comparable<PropertyAssessment> {
    private final int accountNumber;
    private final long assessedValue;
    private final String neighbourhood;
    private final String assessmentClass;

    private final String col13;
    private final String col14;
    private final String col15;

    public PropertyAssessment(String[] csvRow) {
        this. accountNumber = Integer.parseInt(csvRow[0]);
        this.neighbourhood = csvRow[5];
        this.assessedValue = parseLongSafe(csvRow[7]);
        this.assessmentClass = csvRow[10].toUpperCase();

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

        return String.format("Account: %-20s | Neighbourhood: %-20s | Value: $%d",
                accountNumber, neighbourhood, assessedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessedValue, neighbourhood, assessmentClass);
    }
}