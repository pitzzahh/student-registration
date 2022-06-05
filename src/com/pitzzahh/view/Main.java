/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.pitzzahh.view;

import java.awt.*;
import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import com.pitzzahh.entity.Courses;
import com.pitzzahh.entity.Student;
import com.pitzzahh.database.Process;
import com.pitzzahh.entity.SearchingType;
import javax.swing.table.DefaultTableModel;
import com.pitzzahh.database.DatabaseConnection;
import com.pitzzahh.exception.EmptyTableException;
import javax.swing.table.DefaultTableCellRenderer;
import com.pitzzahh.exception.BlankTextFieldsException;
import com.pitzzahh.exception.InvalidStudentNumberException;
import com.pitzzahh.validation.StudentRegistrationValidator;
import static com.pitzzahh.database.Process.getStudentNumber;
import static com.pitzzahh.validation.StudentRegistrationValidator.ValidationResult.SUCCESS;

/**
 *
 * @author peter
 */
public class Main extends javax.swing.JFrame {
    
    public static ArrayList<Student> students = new ArrayList<>();
    private static final DatabaseConnection DATABASE_CONNECTION = new DatabaseConnection();
    public static final Prompt PROMPT = new Prompt();

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        Courses[] courses = Courses.values();
        String[] courseList = new String[courses.length];

        Arrays.stream(courses).forEach(course -> courseList[course.ordinal()] = course.getDescription());

        courseSelection.setModel(new javax.swing.DefaultComboBoxModel<>(courseList));

        // add enum types to JComboBox
        SearchingType[] searchingTypes = SearchingType.values();
        String[] allSearchingTypes = new String[searchingTypes.length];

        Arrays.stream(searchingTypes).forEach(type -> allSearchingTypes[type.ordinal()] = type.name());

        searchingType.setModel(new javax.swing.DefaultComboBoxModel<>(allSearchingTypes));

        setIconImage(Toolkit.getDefaultToolkit().getImage("src/com/pitzzahh/icons/ico.png"));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        refreshTable(false);
    }

    private SearchingType getSearchingType() {
        return SearchingType.valueOf(searchingType.getModel().getElementAt(searchingType.getSelectedIndex()));
    }

    /**
     * Method that refresh the table.
     * <p>First it checks if the method is used for searching a student</p>
     * <p>If it used for searching a student, it makes the table empty and only showing the student that was searched</p>
     * <p>If it not used for searching. It Retrieve first all the data from the database table, adding it to the {@code students} ArrayList</p>
     * <p>Then reset the row count of the table to 0</p>
     * <p>Lastly, looping from the {@code students} ArrayList and adding each student in the row of the table.</p>
     */
    private void refreshTable(boolean isSearching) {
        try {
            if(isSearching) {
                if(table.getModel().getRowCount() == 0 || areTextFieldsEmpty()) {
                    if (table.getModel().getRowCount() == 0) throw new EmptyTableException("NO DATA TO BE SEARCHED!\nPLEASE SELECT A ROW FROM THE TABLE IF DATA IS AVAILABLE");
                    throw new BlankTextFieldsException();
                }
                DATABASE_CONNECTION.search(Main.DATABASE_CONNECTION, makeStudentFromTextField(), getSearchingType());
            }
            else Main.DATABASE_CONNECTION.getAllData();
            DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
            defaultTableModel.setRowCount(0);
            Object[] data = new Object[defaultTableModel.getColumnCount()];

            for (Student student : students) {
                data[0] = student.getStudentNumber();
                data[1] = student.getName();
                data[2] = student.getAge();
                data[3] = student.getAddress();
                data[4] = student.getDateOfBirth();
                data[5] = student.getEmail();
                data[6] = student.getCourse();
                defaultTableModel.addRow(data);
            }
        } catch(RuntimeException runtimeException) {
            PROMPT.show.accept(runtimeException.getMessage(), true);
        }
        students.clear();
    }
    
    /**
     * Method that checks if any of the text fields are empty.
     * @return {@code true} if any of the text fields is empty.
     */
    public boolean areTextFieldsEmpty() {
        return studentNumber.getText().trim().isEmpty() || name.getText().trim().isEmpty() || age.getText().trim().isEmpty() || address.getText().trim().isEmpty();
    }

    /**
     * Method that removes all the current values that are in the text fields, leaving a space at the beginning of the text field.
     */
    private void removeInputValues() {
        studentNumber.setText(" ");
        name.setText(" ");
        age.setText(" ");
        address.setText(" ");
        dateOfBirth.setDate(null);
        email.setText(" ");
    }

    private Student makeStudentFromTextField() {
        return new Student(
                studentNumber.getText().trim(),
                name.getText().trim(),
                age.getText().trim(),
                address.getText().trim(),
                LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth.getDate().getTime())),
                email.getText().trim(),
                courseSelection.getItemAt(courseSelection.getSelectedIndex()).trim()
        );
    }

    private Student makeStudentFromTableSelection() {
        return new Student(
                String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)).trim(),
                String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 1)).trim(),
                String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 2)).trim(),
                String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 3)).trim(),
                LocalDate.parse(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 4)).trim()),
                String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 5)).trim(),
                String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 6)).trim()
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        studentNumberLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        ageLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        courseLabel = new javax.swing.JLabel();
        studentNumber = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        age = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        tableScroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        add = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        save = new javax.swing.JButton();
        search = new javax.swing.JButton();
        picture = new javax.swing.JLabel();
        courseSelection = new javax.swing.JComboBox<>();
        headerPanel = new javax.swing.JPanel();
        header = new javax.swing.JLabel();
        refresh = new javax.swing.JButton();
        searchingType = new javax.swing.JComboBox<>();
        emailLabel = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        addressLabel1 = new javax.swing.JLabel();
        dateOfBirth = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setBackground(new java.awt.Color(0, 102, 102));

        studentNumberLabel.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        studentNumberLabel.setForeground(new java.awt.Color(255, 255, 255));
        studentNumberLabel.setText("Student Number:");

        nameLabel.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(255, 255, 255));
        nameLabel.setText("Name                 :");

        ageLabel.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        ageLabel.setForeground(new java.awt.Color(255, 255, 255));
        ageLabel.setText("Age                    :");

        addressLabel.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        addressLabel.setForeground(new java.awt.Color(255, 255, 255));
        addressLabel.setText("Address             :");

        courseLabel.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        courseLabel.setForeground(new java.awt.Color(255, 255, 255));
        courseLabel.setText("Course              :");

        studentNumber.setBackground(new java.awt.Color(51, 51, 51));
        studentNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        studentNumber.setForeground(new java.awt.Color(255, 255, 255));
        studentNumber.setText(" ");

        name.setBackground(new java.awt.Color(51, 51, 51));
        name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setText(" ");

        age.setBackground(new java.awt.Color(51, 51, 51));
        age.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        age.setForeground(new java.awt.Color(255, 255, 255));
        age.setText(" ");

        address.setBackground(new java.awt.Color(51, 51, 51));
        address.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        address.setForeground(new java.awt.Color(255, 255, 255));
        address.setText(" ");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Number", "Name", "Age", "Address", "Date of birth", "Email", "Course"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setSelectionBackground(new java.awt.Color(0, 153, 153));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        tableScroll.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(1);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(1);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(2).setPreferredWidth(1);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(3).setPreferredWidth(1);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(4).setPreferredWidth(1);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(5).setPreferredWidth(1);
            table.getColumnModel().getColumn(6).setResizable(false);
        }

        add.setBackground(new java.awt.Color(204, 204, 204));
        add.setText("ADD");
        add.setToolTipText("");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        delete.setBackground(new java.awt.Color(204, 204, 204));
        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        save.setBackground(new java.awt.Color(204, 204, 204));
        save.setText("SAVE");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        search.setBackground(new java.awt.Color(204, 204, 204));
        search.setText("SEARCH");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        picture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pitzzahh/icons/students.png"))); // NOI18N

        courseSelection.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        headerPanel.setBackground(new java.awt.Color(204, 204, 204));

        header.setFont(new java.awt.Font("Liberation Sans", 1, 48)); // NOI18N
        header.setText("STUDENT REGISTRATION");
        header.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(header)
                .addGap(72, 72, 72))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
        );

        refresh.setText("REFRESH");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        emailLabel.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("Email                 :");

        email.setBackground(new java.awt.Color(51, 51, 51));
        email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setText(" ");

        addressLabel1.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        addressLabel1.setForeground(new java.awt.Color(255, 255, 255));
        addressLabel1.setText("Date of birth      :");

        dateOfBirth.setDateFormatString("yyyy, d MM");
        dateOfBirth.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScroll)
                .addGap(16, 16, 16))
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(addressLabel)
                        .addGap(6, 6, 6)
                        .addComponent(address))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(courseLabel)
                        .addGap(8, 8, 8)
                        .addComponent(courseSelection, 0, 1, Short.MAX_VALUE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(ageLabel)
                        .addGap(6, 6, 6)
                        .addComponent(age))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(studentNumberLabel)
                        .addGap(6, 6, 6)
                        .addComponent(studentNumber))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addGap(6, 6, 6)
                        .addComponent(name))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchingType, 0, 95, Short.MAX_VALUE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailLabel)
                            .addComponent(addressLabel1))
                        .addGap(6, 6, 6)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(email))))
                .addGap(18, 18, 18)
                .addComponent(picture)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentNumberLabel)
                            .addComponent(studentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ageLabel)
                            .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addressLabel)
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addressLabel1)
                            .addComponent(dateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailLabel)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(courseLabel)
                            .addComponent(courseSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add)
                            .addComponent(delete)
                            .addComponent(search)
                            .addComponent(save)
                            .addComponent(refresh)
                            .addComponent(searchingType, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(picture, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addComponent(tableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Method that searches for a row in the database table, and showing it in the table independently.
     * @param evt not used.
     */
    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        refreshTable(true);
        removeInputValues();

    }//GEN-LAST:event_searchActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try {
            if(table.getModel().getRowCount() == 0) throw new EmptyTableException("NO DATA TO BE SAVED");
            if(areTextFieldsEmpty()) throw new BlankTextFieldsException();
            Process.updateStudent(DATABASE_CONNECTION, makeStudentFromTextField(), makeStudentFromTableSelection());
        } catch (RuntimeException runtimeException) {
            PROMPT.show.accept(runtimeException.getMessage(), true);
        }
        students.clear();
        removeInputValues();
        refreshTable(false);
    }//GEN-LAST:event_saveActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        try {
            if(table.getModel().getRowCount() == 0 || areTextFieldsEmpty()) {
                if (areTextFieldsEmpty()) throw new BlankTextFieldsException();
                throw new EmptyTableException("NO DATA TO BE REMOVED!\nPLEASE SELECT A ROW FROM THE TABLE IF DATA IS AVAILABLE");
            }
            Student studentFromTextField = makeStudentFromTextField();
            if(StudentRegistrationValidator.isStudentAlreadyExists(DATABASE_CONNECTION).apply(studentFromTextField).equals(SUCCESS)) {
                Process.removeStudent(DATABASE_CONNECTION, studentFromTextField.getStudentNumber());
                PROMPT.show.accept("REMOVED SUCCESSFULLY", false);
                removeInputValues();
            }
        } catch (RuntimeException runtimeException) {
            PROMPT.show.accept(runtimeException.getMessage(), true);
        }
        students.clear();
        removeInputValues();
        refreshTable(false);
    }//GEN-LAST:event_deleteActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        try {
            if(areTextFieldsEmpty()) throw new BlankTextFieldsException();
            Student student = makeStudentFromTextField();

            if(StudentRegistrationValidator.isStudentNumberValid().apply(student) != SUCCESS) throw new InvalidStudentNumberException(studentNumber.getText().trim());
            Process.insertData(
                    DATABASE_CONNECTION,
                    student.getStudentNumber(),
                    student.getName(),
                    student.getAge(),
                    student.getAddress(),
                    student.getDateOfBirth(),
                    student.getEmail(),
                    courseSelection.getItemAt(courseSelection.getSelectedIndex()));
            PROMPT.show.accept("ADDED SUCCESSFULLY", false);

        } catch(RuntimeException runtimeException) {
            PROMPT.show.accept(runtimeException.getMessage(), true);
        }
        students.clear();
        removeInputValues();
        refreshTable(false);
        
    }//GEN-LAST:event_addActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        if(table.getModel().getRowCount() != 0) {
            Student student = makeStudentFromTableSelection();
            if(StudentRegistrationValidator.isStudentAlreadyExists(DATABASE_CONNECTION).apply(student).equals(SUCCESS)) {
                Student studentFromDatabaseTable = DATABASE_CONNECTION.getStudent(getStudentNumber(DATABASE_CONNECTION, student.getStudentNumber()));
                studentNumber.setText(" " + studentFromDatabaseTable.getStudentNumber());
                name.setText(" " + studentFromDatabaseTable.getName());
                age.setText(" " + studentFromDatabaseTable.getAge());
                address.setText(" " + studentFromDatabaseTable.getAddress());
                dateOfBirth.setDate(Date.valueOf(studentFromDatabaseTable.getDateOfBirth()));
                email.setText(" " + studentFromDatabaseTable.getEmail());
                courseSelection.getModel().setSelectedItem(studentFromDatabaseTable.getCourse());
            }
        }
    }//GEN-LAST:event_tableMouseClicked
    
    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        refreshTable(false);
        removeInputValues();
    }//GEN-LAST:event_refreshActionPerformed
    
    /**
     * Method that runs the Main class Form with a UI look.
     */
    public void run() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JTextField address;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JLabel addressLabel1;
    private javax.swing.JTextField age;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JComboBox<String> courseSelection;
    private com.toedter.calendar.JDateChooser dateOfBirth;
    private javax.swing.JButton delete;
    private javax.swing.JTextField email;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel header;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTextField name;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel picture;
    private javax.swing.JButton refresh;
    private javax.swing.JButton save;
    private javax.swing.JButton search;
    private javax.swing.JComboBox<String> searchingType;
    private javax.swing.JTextField studentNumber;
    private javax.swing.JLabel studentNumberLabel;
    public static javax.swing.JTable table;
    private javax.swing.JScrollPane tableScroll;
    // End of variables declaration//GEN-END:variables
}
