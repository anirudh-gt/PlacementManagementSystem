package placementapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlacementDataStore {
    private List<Student> students = new ArrayList<>();
    private List<Company> companies = new ArrayList<>();

    public PlacementDataStore() {
        seedInitialData();
    }

    private void seedInitialData() {
        // Sample Students
        students.add(new Student("STU101", "Aarav Sharma", "CSE", 8.8, 0, "Placed", "Google"));
        students.add(new Student("STU102", "Priya Verma", "ECE", 7.4, 1, "Unplaced", "None"));
        students.add(new Student("STU103", "Rohan Gupta", "CSE", 9.1, 0, "Placed", "Microsoft"));
        students.add(new Student("STU104", "Ananya Reddy", "EEE", 6.8, 0, "Unplaced", "None"));
        students.add(new Student("STU105", "Vikram Singh", "MECH", 8.2, 0, "Placed", "TATA Motors"));
        students.add(new Student("STU106", "Neha Patel", "CSE", 7.9, 2, "Unplaced", "None"));

        // Sample Recruiting Companies
        companies.add(new Company("Google", "Software Engineer", 8.5, 0, 24.0));
        companies.add(new Company("Microsoft", "Systems Engineer", 8.0, 0, 18.5));
        companies.add(new Company("TATA Motors", "Design Engineer", 7.5, 0, 9.0));
        companies.add(new Company("Infosys", "Specialist Programmer", 6.5, 2, 6.5));
    }

    public List<Student> getAllStudents() { return students; }
    public List<Company> getAllCompanies() { return companies; }

    public void addStudent(Student s) { students.add(s); }
    public void addCompany(Company c) { companies.add(c); }

    public List<Student> filterStudents(String query, String deptFilter) {
        return students.stream()
            .filter(s -> (query.isEmpty() || s.getName().toLowerCase().contains(query.toLowerCase()) || s.getId().toLowerCase().contains(query.toLowerCase())))
            .filter(s -> (deptFilter.equals("All") || s.getDepartment().equalsIgnoreCase(deptFilter)))
            .collect(Collectors.toList());
    }

    public int getTotalStudents() { return students.size(); }
    
    public int getPlacedCount() {
        return (int) students.stream().filter(s -> s.getStatus().equalsIgnoreCase("Placed")).count();
    }

    public double getPlacementPercentage() {
        if (students.isEmpty()) return 0;
        return ((double) getPlacedCount() / students.size()) * 100;
    }
}