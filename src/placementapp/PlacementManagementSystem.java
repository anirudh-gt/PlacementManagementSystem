package placementapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class PlacementManagementSystem extends JFrame {

    private PlacementDataStore dataStore = new PlacementDataStore();

    // Dark Palette Theme Colors
    private Color bgPrimary = new Color(20, 22, 28);
    private Color cardBg = new Color(32, 36, 45);

    // Dashboard Metric UI Cards
    private CustomUIComponents.MetricCard totalStudentsCard;
    private CustomUIComponents.MetricCard placedCard;
    private CustomUIComponents.MetricCard rateCard;

    // Table Models
    private DefaultTableModel studentTableModel;
    private JTable studentTable;

    private JTextField searchField;
    private JComboBox<String> deptFilterCombo;

    public PlacementManagementSystem() {
        setTitle("University Student Placement Portal");
        setSize(1100, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initLayout();
        refreshDashboardMetrics();
        loadStudentTableData(dataStore.getAllStudents());
    }

    private void initLayout() {
        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(bgPrimary);
        root.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Section: App Header + Dashboard Cards
        JPanel topContainer = new JPanel(new BorderLayout(0, 15));
        topContainer.setOpaque(false);
        topContainer.add(createHeader(), BorderLayout.NORTH);
        topContainer.add(createDashboardMetrics(), BorderLayout.SOUTH);

        root.add(topContainer, BorderLayout.NORTH);

        // Center Section: Filter Control Toolbar + Data Table
        JPanel centerPanel = new CustomUIComponents.RoundedCardPanel(16, cardBg);
        centerPanel.setLayout(new BorderLayout(10, 10));
        centerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        centerPanel.add(createToolbar(), BorderLayout.NORTH);
        centerPanel.add(createStudentTableScrollPane(), BorderLayout.CENTER);

        root.add(centerPanel, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel title = new JLabel("Placement Management System");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Track corporate recruitment, student eligibility, and live placement statistics");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(new Color(156, 163, 175));

        panel.add(title, BorderLayout.NORTH);
        panel.add(sub, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDashboardMetrics() {
        JPanel grid = new JPanel(new GridLayout(1, 3, 20, 0));
        grid.setOpaque(false);

        totalStudentsCard = new CustomUIComponents.MetricCard("Total Registered", "0", new Color(99, 102, 241), cardBg);
        placedCard = new CustomUIComponents.MetricCard("Students Placed", "0", new Color(16, 185, 129), cardBg);
        rateCard = new CustomUIComponents.MetricCard("Placement Rate", "0.0%", new Color(245, 158, 11), cardBg);

        grid.add(totalStudentsCard);
        grid.add(placedCard);
        grid.add(rateCard);

        return grid;
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        toolbar.setOpaque(false);

        JLabel searchLbl = new JLabel("Search:");
        searchLbl.setForeground(Color.WHITE);

        searchField = new JTextField(12);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilters();
            }
        });

        JLabel filterLbl = new JLabel("Department:");
        filterLbl.setForeground(Color.WHITE);

        deptFilterCombo = new JComboBox<>(new String[]{"All", "CSE", "ECE", "EEE", "MECH", "CIVIL"});
        deptFilterCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });

        JButton addStudentBtn = CustomUIComponents.createStyledButton("+ Add Student", new Color(79, 70, 229));
        addStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentRegistrationDialog dialog = new StudentRegistrationDialog(PlacementManagementSystem.this);
                dialog.setVisible(true);
                if (dialog.isStudentAdded()) {
                    dataStore.addStudent(dialog.getCreatedStudent());
                    refreshDashboardMetrics();
                    applyFilters();
                }
            }
        });

        JButton checkEligibilityBtn = CustomUIComponents.createStyledButton("Run Company Eligibility Check", new Color(55, 65, 81));
        checkEligibilityBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runEligibilityCheck();
            }
        });

        toolbar.add(searchLbl);
        toolbar.add(searchField);
        toolbar.add(filterLbl);
        toolbar.add(deptFilterCombo);
        toolbar.add(addStudentBtn);
        toolbar.add(checkEligibilityBtn);

        return toolbar;
    }

    private JScrollPane createStudentTableScrollPane() {
        String[] columns = {"ID", "Student Name", "Department", "CGPA", "Backlogs", "Status", "Company"};
        studentTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(studentTableModel);
        studentTable.setRowHeight(28);
        studentTable.setBackground(new Color(24, 26, 32));
        studentTable.setForeground(Color.WHITE);
        studentTable.setGridColor(new Color(45, 52, 64));
        studentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(45, 52, 64));
        studentTable.getTableHeader().setForeground(Color.WHITE);

        // Center align contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < studentTable.getColumnCount(); i++) {
            studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.getViewport().setBackground(new Color(24, 26, 32));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        return scrollPane;
    }

    private void applyFilters() {
        String query = searchField.getText().trim();
        String dept = (String) deptFilterCombo.getSelectedItem();
        List<Student> filtered = dataStore.filterStudents(query, dept);
        loadStudentTableData(filtered);
    }

    private void loadStudentTableData(List<Student> list) {
        studentTableModel.setRowCount(0);
        for (Student s : list) {
            studentTableModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getDepartment(), s.getCgpa(),
                    s.getBacklogs(), s.getStatus(), s.getCompanyPlaced()
            });
        }
    }

    private void refreshDashboardMetrics() {
        totalStudentsCard.updateValue(String.valueOf(dataStore.getTotalStudents()));
        placedCard.updateValue(String.valueOf(dataStore.getPlacedCount()));
        rateCard.updateValue(String.format("%.1f%%", dataStore.getPlacementPercentage()));
    }

    private void runEligibilityCheck() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentId = (String) studentTableModel.getValueAt(selectedRow, 0);
        Student selectedStudent = dataStore.getAllStudents().stream()
                .filter(s -> s.getId().equals(studentId))
                .findFirst().orElse(null);

        if (selectedStudent != null) {
            StringBuilder report = new StringBuilder("Eligibility Analysis for " + selectedStudent.getName() + ":\n\n");
            for (Company c : dataStore.getAllCompanies()) {
                boolean eligible = c.isEligible(selectedStudent);
                report.append(String.format("- %s (%s, Package: %.1f LPA): %s\n",
                        c.getName(), c.getRole(), c.getPackageLpa(), eligible ? "ELIGIBLE" : "NOT ELIGIBLE"));
            }
            JOptionPane.showMessageDialog(this, report.toString(), "Company Eligibility Report", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PlacementManagementSystem().setVisible(true);
            }
        });
    }
}