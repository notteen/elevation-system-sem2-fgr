/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Connect.ObjectsData;
import javax.swing.JOptionPane;
import objects.Employee;
import ultity.EncryptMD5;
import ultity.SQLTools;
import ultity.Validator;

/**
 *
 * @author ToanLM
 */
public class EmployeeAdd extends javax.swing.JDialog {
    Employee eme;
    Validator vd = new Validator();
    SQLTools sqltool = new SQLTools();

    public EmployeeAdd(java.awt.Frame parent, boolean modal, Employee employee) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(rootPane);
        this.setTitle("Add Employee");
        if(employee != null){
            this.eme = employee;
            btnAdd.setText("Update");
            btnReset.setVisible(false);
            txtUsername.setEditable(false);
            txtRepassword.setVisible(false);
            lbRepassword.setVisible(false);
            setValue();
       }
    }

    private void setValue(){
        txtUsername.setText(eme.getUsername());
        txtEmail.setText(eme.getEmail());
        txtFullname.setText(eme.getFullname());
        txtMobile.setText(eme.getContact());
        rbFemale.setSelected(true);
        if(eme.getGender() == 1){
            rbMale.setSelected(true);
        }
    }
    
    public void resetForm() {
        txtEmail.setText("");
        txtFullname.setText("");
        txtMobile.setText("");
        txtPassword.setText("");
        txtRepassword.setText("");
        txtUsername.setText("");
    }
    
    public void UpdateEmployee(){
        String fullname = txtFullname.getText().trim();
        String mobile = txtMobile.getText().trim();
        String email = txtEmail.getText().trim();
        int gender = 0;
        if (rbMale.isSelected()) {
            gender = 1;
        }
        eme.setFullname(fullname);
        eme.setEmail(email);
        eme.setContact(mobile);
        eme.setGender(gender);
        if(txtPassword.getPassword().length != 0){
            if(!vd.Password(txtPassword.getPassword()) && txtPassword.getPassword().length < 6){
                JOptionPane.showMessageDialog(rootPane, "Password must be >= 6 character");
            }
            else{
                String password = String.valueOf(txtPassword.getPassword());
                eme.setPassword(EncryptMD5.MD5Convert(password));
                int i = sqltool.editUser(eme);
                if(i == 1){
                    JOptionPane.showMessageDialog(rootPane, "Updated");
                    this.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "Failed");
                }
            }
        }
        else{
            int i = sqltool.editUser(eme);
            if(i == 1){
                JOptionPane.showMessageDialog(rootPane, "Updated");
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Failed");
            }
        }
    }
    
    public void AddEmployee() {
        if(vd.Password(txtPassword.getPassword()) && vd.Password(txtRepassword.getPassword())){
            String username = txtUsername.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());
            String repassword = String.valueOf(txtRepassword.getPassword());
            String fullname = txtFullname.getText().trim();
            String mobile = txtMobile.getText().trim();
            String email = txtEmail.getText().trim();
            int sex;
            if (rbMale.isSelected()) {
                sex = 1;
            } else {
                sex = 0;
            }
            if ("".equals(username) || "".equals(password) || "".equals(repassword) || "".equals(fullname) || "".equals(mobile) || "".equals(email)) {
                JOptionPane.showMessageDialog(this, "Please Complete Form!");
            } else {
                if (!vd.Email(email)) {
                    JOptionPane.showMessageDialog(this, "Please confirm your email.");

                } else {
                    if (!vd.Phone(mobile)) {
                        JOptionPane.showMessageDialog(this, "Phone must be number and more than 7 characters");
                    } else {
                        if (password.equals(repassword)) {
                            password = EncryptMD5.MD5Convert(password);
                            Employee e = new Employee(username, password, fullname, sex, email, mobile);
                            if(sqltool.existUser(username)){
                                JOptionPane.showMessageDialog(rootPane, "Existed Username!");
                            }
                            else{
                                int status = sqltool.AddUser(e);
                                if (status == 1) {
                                    ObjectsData od = new ObjectsData();
                                    Employee em = od.getLastEmployee();
                                    Employee emp = new Employee(em.getUserId(), username, password, fullname, sex, email, email);
                                    int sta = sqltool.AddEmployee(emp);
                                    if (sta == 1) {
                                        JOptionPane.showMessageDialog(this, "Add Employee successful");
                                        this.dispose();
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Add Employee failed");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Add Employee failed");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Please confirm your password");
                        }
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Password must be >= 6 characters");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngSex = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        rbMale = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        txtFullname = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        rbFemale = new javax.swing.JRadioButton();
        txtMobile = new javax.swing.JTextField();
        lbRepassword = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtRepassword = new javax.swing.JPasswordField();
        txtPassword = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add new Employee");
        setResizable(false);

        btngSex.add(rbMale);
        rbMale.setSelected(true);
        rbMale.setText("Male");

        jLabel2.setText("Password:");

        jLabel1.setText("Username:");

        btngSex.add(rbFemale);
        rbFemale.setText("Female");

        lbRepassword.setText("Re-Password:");

        jLabel5.setText("Sex:");

        jLabel4.setText("Full Name:");

        jLabel7.setText("Mobile:");

        jLabel6.setText("Email:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lbRepassword)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbMale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbFemale))
                    .addComponent(txtEmail)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)
                    .addComponent(txtRepassword)
                    .addComponent(txtFullname)
                    .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbRepassword)
                    .addComponent(txtRepassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rbMale)
                    .addComponent(rbFemale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnAdd);

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_reset.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel2.add(btnReset);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancel);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if(btnAdd.getText().equals("Add")){
            AddEmployee();
        }
        else{
            UpdateEmployee();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnReset;
    private javax.swing.ButtonGroup btngSex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbRepassword;
    private javax.swing.JRadioButton rbFemale;
    private javax.swing.JRadioButton rbMale;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtRepassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
