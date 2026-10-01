package placementapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentRegistrationDialog extends JDialog {

    private JTextField idField, nameField, cgpaField, backlogsField;
    private JComboBox<String> deptCombo;
    private boolean studentAdded = false;
    private Student createdStudent;

    public StudentRegistrationDialog(JFrame parent) {
        super(parent, "Register New Student", true);
        setSize(380, 420);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new CustomUIComponents.RoundedCardPanel(0, new Color(34, 38, 48));
        panel.setLayout(new GridLayout(6, 2, 10, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel idLbl = createLabel("Student ID:");
        idField = new JTextField();

        JLabel nameLbl = createLabel("Full Name:");
        nameField = new JTextField();

        JLabel deptLbl = createLabel("Department:");
        deptCombo = new JComboBox<>(new String[]{"CSE", "ECE", "EEE", "MECH", "CIVIL"});

        JLabel cgpaLbl = createLabel("CGPA:");
        cgpaField = new JTextField();

        JLabel backlogsLbl = createLabel("Active Backlogs:");
        backlogsField = new JTextField();

        JButton saveBtn = CustomUIComponents.createStyledButton("Save Student", new Color(16, 185, 129));
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAndSave()) {
                    studentAdded = true;
                    dispose();
                }
            }
        });

        panel.add(idLbl); panel.add(idField);
        panel.add(nameLbl); panel.add(nameField);
        panel.add(deptLbl); panel.add(deptCombo);
        panel.add(cgpaLbl); panel.add(cgpaField);
        panel.add(backlogsLbl); panel.add(backlogsField);
        panel.add(new JLabel()); panel.add(saveBtn);

        add(panel);
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        return l;
    }

    private boolean validateAndSave() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String dept = (String) deptCombo.getSelectedItem();
            double cgpa = Double.parseDouble(cgpaField.getText().trim());
            int backlogs = Integer.parseInt(backlogsField.getText().trim());

            if (id.isEmpty() || name.isEmpty() || cgpa < 0 || cgpa > 10 || backlogs < 0) {
                JOptionPane.showMessageDialog(this, "Please fill out valid details.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            createdStudent = new Student(id, name, dept, cgpa, backlogs, "Unplaced", "None");
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "CGPA and Backlogs must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean isStudentAdded() { return studentAdded; }
    public Student getCreatedStudent() { return createdStudent; }
}