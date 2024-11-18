package crystalwave.dto;

public class ReportDTO {
    private String reportType;
    private String reportDate;
    private double totalAmount;
    private int totalRooms;
    private int totalEmployees;

    // Constructor
    public ReportDTO(String reportType, String reportDate, double totalAmount, int totalRooms, int totalEmployees) {
        this.reportType = reportType;
        this.reportDate = reportDate;
        this.totalAmount = totalAmount;
        this.totalRooms = totalRooms;
        this.totalEmployees = totalEmployees;
    }

    // Getters and Setters
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(int totalEmployees) {
        this.totalEmployees = totalEmployees;
    }
}
