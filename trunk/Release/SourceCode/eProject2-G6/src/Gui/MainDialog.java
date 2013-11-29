/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Connect.ConnectSQL;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    int EmployeeId = 0;
    Frame parent;

    public MainDialog(java.awt.Frame parent, boolean modal, String card) {
        super(parent, modal);
        initComponents();
        CardLayout cl = (CardLayout) MainPanel.getLayout();
        cl.show(MainPanel, card);
        writeConfig();
        switch(card){
            case "cardLogin": this.setSize(408, 285);break;
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
    
    private boolean writeConfig(){
        Writer output = null;
        String dbhost = txtServerDB.getText().trim();
        String dbname = txtNameDB.getText().trim();
        String dbport = txtPortDB.getText().trim();
        String dbuser = txtUserDB.getText().trim();
        String dbpass = new String(txtPassDB.getPassword()).trim();
        StringBuilder data = new StringBuilder();
        data.append(dbhost);
        data.append(";");
        data.append(dbport);
        data.append(";");
        data.append(dbname);
        data.append(";");
        data.append(dbuser);
        data.append(";");
        data.append(dbpass);
        try {
            File file = new File("lib/config.txt");
            if(file.exists()){
                if(!cs.checkConnection()){
                    String[] strArr = null;
                    BufferedReader reader = null;
                    reader = new BufferedReader(new FileReader(file));
                    String text = null;
                    while ((text = reader.readLine()) != null)
                    {
                       strArr = text.split(";");
                    }
                    if(!strArr[0].equals(dbhost) || !strArr[1].equals(dbport) || !strArr[2].equals(dbname) || !strArr[3].equals(dbuser) || !strArr[4].equals(dbpass)){
                        output = new BufferedWriter(new FileWriter(file));
                        output.write(data.toString());
                        output.close();
                    }
                }
            }
            else {
                file.createNewFile();
                output = new BufferedWriter(new FileWriter(file));
                output.write(data.toString());
                output.close();
            }
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    
    public int getEmployeeId(){
        return EmployeeId;
    }
    
    private void LoginForm(){
        if(!txtUsername.getText().trim().equals("") && txtPassword.getPassword().length != 0){
            if(cs.checkConnection()){
                String username = txtUsername.getText().trim();
                String password = EncryptMD5.MD5Convert(String.valueOf(txtPassword.getPassword()));
                try {
                    ResultSet rs = st.getUser(username);
                    if(rs != null){
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
                                        ResultSet rs1 = st.getEmployee(Id);
                                        if((rs1 != null)){
                                            AccountType = "employee";
                                            EmployeeId = rs1.getInt("id");
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
                            txtPassword.setBackground(Color.red);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(rootPane, "Username does not exist!");
                        txtUsername.setBackground(Color.red);
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
            txtUsername.setBackground(Color.red);
            txtPassword.setBackground(Color.red);
        }
    }
    
    private void TestConnect(){
        writeConfig();
        if(cs.checkConnection()){
            JOptionPane.showMessageDialog(rootPane, "Connected to Database!");
            CardLayout cl = (CardLayout) MainPanel.getLayout();
            cl.show(MainPanel, "cardLogin");
            this.setSize(408, 285);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
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

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loginBackground.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnLoginLayout = new javax.swing.GroupLayout(pnLogin);
        pnLogin.setLayout(pnLoginLayout);
        pnLoginLayout.setHorizontalGroup(
            pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnLoginLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnLoginLayout.createSequentialGroup()
                        .addComponent(btnLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHelp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOption)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnLoginLayout.createSequentialGroup()
                        .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))))
        );
        pnLoginLayout.setVerticalGroup(
            pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLoginLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnCancel)
                    .addComponent(btnHelp)
                    .addComponent(btnOption))
                .addGap(19, 19, 19))
        );

        MainPanel.add(pnLogin, "cardLogin");

        pnConfig.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnConfigComponentShown(evt);
            }
        });

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

        txtNameDB.setText("ElevationSystem");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
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
        jTextArea1.setText("Admin Department:\n + User: admin\t\t\n + Pass: admin\n\nEmployee Department:\n + User: demo\t\t\n + Pass: 123456\n");
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
        txtUsername.setBackground(Color.white);
        txtUsername.selectAll();
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        txtPassword.setBackground(Color.white);
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

    private void pnConfigComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnConfigComponentShown
        File file = new File("lib/config.txt");
        if(file.exists()){
            try {
                String[] str = null;
                BufferedReader reader = null;
                reader = new BufferedReader(new FileReader(file));
                String text = null;
                while ((text = reader.readLine()) != null)
                {
                    str = text.split(";");
                }
                txtServerDB.setText(str[0]);
                txtPortDB.setText(str[1]);
                txtNameDB.setText(str[2]);
                txtUserDB.setText(str[3]);
                txtPassDB.setText(str[4]);
            } catch (IOException ex) {
                Logger.getLogger(MainDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_pnConfigComponentShown

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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
