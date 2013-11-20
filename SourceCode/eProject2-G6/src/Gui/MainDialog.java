/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Connect.ConnectSQL;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ultity.EncryptMD5;
import ultity.SQLTools;

/**
 *
 * @author notte_000
 */
public class MainDialog extends javax.swing.JDialog {
    SQLTools st = new SQLTools();
    ConnectSQL cs = new ConnectSQL();
    String AccountType = "";
    int Department = 0;
    Frame parent;
    /**
     * Creates new form MainDialog
     */
    public MainDialog(java.awt.Frame parent, boolean modal, String card) {
        super(parent, modal);
        initComponents();
        CardLayout cl = (CardLayout) MainPanel.getLayout();
        cl.show(MainPanel, card);
        writeConfig();
        switch(card){
            case "cardLogin": this.setSize(380, 180);break;
            case "cardConfig": this.setSize(340, 280);break;
            case "cardUsersGuide": this.setSize(408, 285);break;
            default: this.setSize(380, 180);break;
        }
        this.setResizable(false);
        this.setTitle("Login");
        this.parent = parent;
        this.setLocationRelativeTo(rootPane);
    }
    
    public String getAccountType(){
        this.setVisible(true);
        if(!AccountType.equals("")){
            return AccountType;
        }
        else{
            return "";
        }
    }
    
    public int getDepartment(){
        return Department;
    }
    
    private boolean writeConfig(){
        Writer output = null;
        try {
            File file = new File("lib/config.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            StringBuilder data = new StringBuilder();
            data.append(txtServerDB.getText().trim());
            data.append(";");
            data.append(txtPortDB.getText().trim());
            data.append(";");
            data.append(txtNameDB.getText().trim());
            data.append(";");
            data.append(txtUserDB.getText().trim());
            data.append(";");
            data.append(new String(txtPassDB.getPassword()).trim());
            output = new BufferedWriter(new FileWriter(file));
            output.write(data.toString());
            output.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    
    private void LoginForm(){
        if(!txtUsername.getText().trim().equals("") && txtPassword.getPassword().length != 0){
            if(cs.checkConnection()){
                String username = txtUsername.getText().trim();
                String password = EncryptMD5.MD5Convert(String.valueOf(txtPassword.getPassword()));
                try {
                    ResultSet rs = st.selectSQL("Select * FROM Users WHERE Username = '" + username + "'");
                    if(rs.next()){
                        String loginPass = rs.getString("password");
                        if(loginPass.equals(password)){
                            int loginStatus = rs.getInt("status");
                            if(loginStatus == 1){
                                int Id = rs.getInt("id");
                                int loginAccountType = rs.getInt("accounttype");
                                switch(loginAccountType){
                                    case 1: {
                                            AccountType = "admin";
                                            this.dispose();
                                        break;
                                    }
                                    case 2: {
                                        ResultSet rs1 = st.resultSQL("SELECT * FROM Employee WHERE userid = " + Id);
                                        if((rs1 != null)){
                                            AccountType = "employee";
                                            Department = rs1.getInt("department");
                                            this.dispose(); 
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(rootPane, "Your are not subscribed in this department!");
                                        }
                                        break;
                                    } 
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(rootPane, "Your account has been deactive!");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(rootPane, "Wrong password, please try again!");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(rootPane, "Username does not exist!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, "Cannot connect to Database, please config in Options Menu!");
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Cannot connect to Database, please config in Options Menu!");
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Username or Password can not be blank!");
        }
    }
    
    private void TestConnect(){
        writeConfig();
        if(cs.checkConnection()){
            JOptionPane.showMessageDialog(rootPane, "Connected to Database!");
            CardLayout cl = (CardLayout) MainPanel.getLayout();
            cl.show(MainPanel, "cardLogin");
            this.setSize(380, 180);
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Cannot connect to Database!");
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

        MainPanel = new javax.swing.JPanel();
        pnLogin = new javax.swing.JPanel();
        btnOption = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        pnConfig = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtServerDB = new javax.swing.JTextField();
        txtPortDB = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNameDB = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUserDB = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnTestConnect = new javax.swing.JButton();
        btnCFCancel = new javax.swing.JButton();
        txtPassDB = new javax.swing.JPasswordField();
        pnUsersGuide = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        MainPanel.setLayout(new java.awt.CardLayout());

        btnOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_option.png"))); // NOI18N
        btnOption.setText("Option");
        btnOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionActionPerformed(evt);
            }
        });

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_help.png"))); // NOI18N
        btnHelp.setText("Help");
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel1.setText("Username:");

        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        jLabel2.setText("Password:");

        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsernameFocusGained(evt);
            }
        });
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
        });

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_login.png"))); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnLoginLayout = new javax.swing.GroupLayout(pnLogin);
        pnLogin.setLayout(pnLoginLayout);
        pnLoginLayout.setHorizontalGroup(
            pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnLoginLayout.createSequentialGroup()
                        .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(txtUsername)))
                    .addGroup(pnLoginLayout.createSequentialGroup()
                        .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHelp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOption)))
                .addGap(42, 42, 42))
        );
        pnLoginLayout.setVerticalGroup(
            pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLoginLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOption)
                    .addComponent(btnHelp)
                    .addComponent(btnCancel)
                    .addComponent(btnLogin))
                .addContainerGap(127, Short.MAX_VALUE))
        );

        MainPanel.add(pnLogin, "cardLogin");

        jLabel4.setText("Server Name:");

        txtServerDB.setText("localhost");
        txtServerDB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtServerDBFocusGained(evt);
            }
        });
        txtServerDB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtServerDBKeyPressed(evt);
            }
        });

        txtPortDB.setText("1433");
        txtPortDB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPortDBFocusGained(evt);
            }
        });
        txtPortDB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPortDBKeyPressed(evt);
            }
        });

        jLabel5.setText("Port:");

        jLabel6.setText("Database:");

        txtNameDB.setText("MyProject");
        txtNameDB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameDBFocusGained(evt);
            }
        });
        txtNameDB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameDBKeyPressed(evt);
            }
        });

        jLabel7.setText("Username:");

        txtUserDB.setText("sa");
        txtUserDB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserDBFocusGained(evt);
            }
        });
        txtUserDB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserDBKeyPressed(evt);
            }
        });

        jLabel8.setText("Password:");

        btnTestConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_connect.png"))); // NOI18N
        btnTestConnect.setText("Test & Connect");
        btnTestConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestConnectActionPerformed(evt);
            }
        });

        btnCFCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_cancel.png"))); // NOI18N
        btnCFCancel.setText("Cancel");
        btnCFCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCFCancelActionPerformed(evt);
            }
        });

        txtPassDB.setText("123456");
        txtPassDB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassDBFocusGained(evt);
            }
        });
        txtPassDB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassDBKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnConfigLayout = new javax.swing.GroupLayout(pnConfig);
        pnConfig.setLayout(pnConfigLayout);
        pnConfigLayout.setHorizontalGroup(
            pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnConfigLayout.createSequentialGroup()
                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnConfigLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnConfigLayout.createSequentialGroup()
                                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(52, 52, 52)
                                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUserDB, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassDB, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnConfigLayout.createSequentialGroup()
                                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(38, 38, 38)
                                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNameDB, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPortDB, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtServerDB, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(pnConfigLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnTestConnect)
                        .addGap(48, 48, 48)
                        .addComponent(btnCFCancel)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        pnConfigLayout.setVerticalGroup(
            pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnConfigLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtServerDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPortDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNameDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtUserDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPassDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(pnConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTestConnect)
                    .addComponent(btnCFCancel))
                .addGap(20, 20, 20))
        );

        MainPanel.add(pnConfig, "cardConfig");
        pnConfig.getAccessibleContext().setAccessibleName("pnConfig");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Admin Department:\n + User: admin\t\t+ Pass: admin\nOrder Department:\n + User: demo1\t\t+ Pass: 123456\nService Department:\n + User: demo2\t\t+ Pass: 123456\nComplaint Department:\n + User: demo3\t\t+ Pass: 123456\nMaintenance Department:\n + User: demo4\t\t+ Pass: 123456");
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_close.png"))); // NOI18N
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        javax.swing.GroupLayout pnUsersGuideLayout = new javax.swing.GroupLayout(pnUsersGuide);
        pnUsersGuide.setLayout(pnUsersGuideLayout);
        pnUsersGuideLayout.setHorizontalGroup(
            pnUsersGuideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnUsersGuideLayout.setVerticalGroup(
            pnUsersGuideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnUsersGuideLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(pnUsersGuide, "cardUsersGuide");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        MainPanel.getAccessibleContext().setAccessibleName("MainPanel");
        MainPanel.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        LoginForm();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnTestConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestConnectActionPerformed
        TestConnect();
    }//GEN-LAST:event_btnTestConnectActionPerformed

    private void btnCFCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCFCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCFCancelActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionActionPerformed
        CardLayout cl = (CardLayout) MainPanel.getLayout();
        cl.show(MainPanel, "cardConfig");
        this.setTitle("Config Database");
        this.setSize(340, 280);
    }//GEN-LAST:event_btnOptionActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        CardLayout cl = (CardLayout) MainPanel.getLayout();
        cl.show(MainPanel, "cardUsersGuide");
        this.setTitle("Users Guide");
        this.setSize(408, 285);
    }//GEN-LAST:event_btnHelpActionPerformed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            LoginForm();
        }
        else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_txtUsernameKeyPressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            LoginForm();
        }
        else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
        txtUsername.selectAll();
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        txtPassword.selectAll();
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtServerDBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtServerDBKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TestConnect();
        }
        else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_txtServerDBKeyPressed

    private void txtPortDBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPortDBKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TestConnect();
        }
        else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_txtPortDBKeyPressed

    private void txtNameDBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameDBKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TestConnect();
        }
        else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_txtNameDBKeyPressed

    private void txtUserDBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserDBKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TestConnect();
        }
        else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_txtUserDBKeyPressed

    private void txtPassDBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassDBKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TestConnect();
        }
        else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_txtPassDBKeyPressed

    private void txtServerDBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtServerDBFocusGained
        txtServerDB.selectAll();
    }//GEN-LAST:event_txtServerDBFocusGained

    private void txtPortDBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPortDBFocusGained
        txtPortDB.selectAll();
    }//GEN-LAST:event_txtPortDBFocusGained

    private void txtNameDBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameDBFocusGained
        txtNameDB.selectAll();
    }//GEN-LAST:event_txtNameDBFocusGained

    private void txtUserDBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserDBFocusGained
        txtUserDB.selectAll();
    }//GEN-LAST:event_txtUserDBFocusGained

    private void txtPassDBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassDBFocusGained
        txtPassDB.selectAll();
    }//GEN-LAST:event_txtPassDBFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton btnCFCancel;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnOption;
    private javax.swing.JButton btnTestConnect;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel pnConfig;
    private javax.swing.JPanel pnLogin;
    private javax.swing.JPanel pnUsersGuide;
    private javax.swing.JTextField txtNameDB;
    private javax.swing.JPasswordField txtPassDB;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPortDB;
    private javax.swing.JTextField txtServerDB;
    private javax.swing.JTextField txtUserDB;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
