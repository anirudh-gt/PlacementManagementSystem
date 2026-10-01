package placementapp;

public class Student {
    private String id;
    private String name;
    private String department;
    private double cgpa;
    private int backlogs;
    private String status; // "Eligible", "Placed", "Unplaced"
    private String companyPlaced;

    public Student(String id, String name, String department, double cgpa, int backlogs, String status, String companyPlaced) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.cgpa = cgpa;
        this.backlogs = backlogs;
        this.status = status;
        this.companyPlaced = companyPlaced;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getCgpa() { return cgpa; }
    public int getBacklogs() { return backlogs; }
    public String getStatus() { return status; }
    public String getCompanyPlaced() { return companyPlaced; }

    public void setStatus(String status) { this.status = status; }
    public void setCompanyPlaced(String companyPlaced) { this.companyPlaced = companyPlaced; }
}