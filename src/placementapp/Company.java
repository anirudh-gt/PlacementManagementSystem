package placementapp;

public class Company {
    private String name;
    private String role;
    private double minCgpa;
    private int maxBacklogs;
    private double packageLpa;

    public Company(String name, String role, double minCgpa, int maxBacklogs, double packageLpa) {
        this.name = name;
        this.role = role;
        this.minCgpa = minCgpa;
        this.maxBacklogs = maxBacklogs;
        this.packageLpa = packageLpa;
    }

    public String getName() { return name; }
    public String getRole() { return role; }
    public double getMinCgpa() { return minCgpa; }
    public int getMaxBacklogs() { return maxBacklogs; }
    public double getPackageLpa() { return packageLpa; }

    public boolean isEligible(Student s) {
        return s.getCgpa() >= minCgpa && s.getBacklogs() <= maxBacklogs;
    }
}