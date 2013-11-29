/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import objects.Elevator;
import objects.Employee;
import ultity.SQLTools;
import ultity.Tools;
import ultity.Validator;

/**
 *
 * @author ToanLM
 */
public class ElevatorViewEdit extends javax.swing.JDialog {

    Elevator e;
    int status = -1;
    int id;
    int t;
    int changeimage = 0;
    Frame parent;
    Employee employee;
    SQLTools st = new SQLTools();

    public ElevatorViewEdit(java.awt.Frame parent, boolean modal, Elevator e, int t, boolean accounttype, Employee employee) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
        this.e = e;
        this.t = t;
        if (t == 1) {
            setTitle("Detail of \"" + e.getName() + "\"");
        }
        else {
            setTitle("Edit \"" + e.getName() + "\"");
        }
        setValue(e);
        setLocationRelativeTo(rootPane);
        if(accounttype != true){
            btnOrder.setVisible(false);
        }
        else{
            btnOrder.setVisible(true);
            this.employee = employee;
        }
    }

    public void setValue(Elevator e) {
        txtNameElevator.setText(e.getName());
        txtTypeElevator.setText(e.getType());
        txtMadein.setText(e.getMadein());
        txtPrice.setText("" + e.getPrice());
        txtSpeed.setText("" + e.getSpeed());
        txtWeight.setText("" + e.getWeight());
        txtImage.setText(e.getImage());

        lbWarranty.setText(e.getWarranty() + " Year");
        cbWarranty.setSelectedIndex(e.getWarranty());
        viewImage();
        id = e.getId();
        if (t == 1) {
            lbTitle.setText("View Details of " + e.getName());
            txtNameElevator.setEditable(false);
            txtTypeElevator.setEditable(false);
            txtPrice.setEditable(false);
            txtWeight.setEditable(false);
            txtSpeed.setEditable(false);
            txtMadein.setEditable(false);
            btnUpload.setVisible(false);
            btnSumit.setVisible(false);
            cbWarranty.setVisible(false);
        } else {
            lbTitle.setText("Edit " + e.getName());
            lbWarranty.setVisible(false);
            status = 0;
            btnSumit.setText("Update");
        }
    }

    public void viewImage() {
        File fdir = new File(System.getProperty("java.class.path"));
        File dir = fdir.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        ImageIcon icon = new javax.swing.ImageIcon(path + "/Images/" + e.getImage());
        //ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/Images/" + e.getImage()));
        Image img = icon.getImage();
        float width = icon.getIconWidth();
        int height = icon.getIconHeight();
        int h = height;
        int w = (int) width;
        if (width > 135) {
            h = (int) (height / (width / 135));
            w = 135;
        }
        Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        Icon newIcon = new ImageIcon(newimg);
        lbImage.setIcon(newIcon);
    }

    public void setImage(String path) {
        ImageIcon icon = new javax.swing.ImageIcon(txtImage.getText());
        Image img = icon.getImage();
        float width = icon.getIconWidth();
        int height = icon.getIconHeight();
        int h = height;
        int w = (int) width;
        if (width > 135) {
            h = (int) (height / (width / 135));
            w = 135;
        }
        Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        Icon newIcon = new ImageIcon(newimg);
        lbImage.setIcon(newIcon);
    }

    public void UpdateElevator() {
        if ("".equals(txtNameElevator.getText()) || "".equals(txtTypeElevator.getText()) || cbWarranty.getSelectedIndex() == 0 || "".equals(txtImage.getText()) || "".equals(txtMadein.getText()) || "".equals(txtPrice.getText()) || "".equals(txtSpeed.getText()) || "".equals(txtWeight.getText())) {
            JOptionPane.showMessageDialog(this, "Please complete form");
        } else {
            Validator v = new Validator();
            String floatreg = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
            Boolean p = txtPrice.getText().matches(floatreg);
            Boolean w = txtWeight.getText().matches(floatreg);
            Boolean s = txtSpeed.getText().matches(floatreg);
            if (p == false) {
                JOptionPane.showMessageDialog(this, "Please confirm your price value. You can use Number and only one dot.");
            } else {
                if (w == false) {
                    JOptionPane.showMessageDialog(this, "Please confirm your weight value. You can use Number and only one dot.");
                } else {
                    if (s == false) {
                        JOptionPane.showMessageDialog(this, "Please confirm your speed value. You can use Number and only one dot.");
                    } else {
                        String name = v.ValidateString(txtNameElevator.getText());
                        String type = v.ValidateString(txtTypeElevator.getText());
                        Float price = v.ValidateFloat(txtPrice.getText());
                        Float weight = v.ValidateFloat(txtWeight.getText());
                        Float speed = v.ValidateFloat(txtSpeed.getText());
                        int warranty = cbWarranty.getSelectedIndex();
                        String madein = v.ValidateString(txtMadein.getText());
                        String image = txtImage.getText();
                        if (changeimage == 1) {
                            Tools tools = new Tools();
                            image = tools.CoppyFile(txtImage.getText());
                        }
                        e = new Elevator(id, name, image, type, price, weight, speed, madein, warranty);
                        status = st.UpdateElevator(e);
                        if (status == 1) {
                            JOptionPane.showMessageDialog(this, "Update success");
                            AdminMain em = (AdminMain) this.getOwner();
                            em.initListElevator("", "");
                            dispose();
                        }
                    }
                }
            }
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

        lbTitle = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        txtImage = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        txtSpeed = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNameElevator = new javax.swing.JTextField();
        txtWeight = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnSumit = new javax.swing.JButton();
        cbWarranty = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMadein = new javax.swing.JTextField();
        txtTypeElevator = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lbImage = new javax.swing.JLabel();
        lbWarranty = new javax.swing.JLabel();
        btnOrder = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lbTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTitle.setText("View and Edit Elevator");

        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_browse.png"))); // NOI18N
        btnUpload.setText("Browse");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        txtImage.setEditable(false);

        jLabel5.setText("Price:");

        jLabel6.setText("Image of product:");

        jLabel7.setText("Weight limit:");

        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPriceKeyPressed(evt);
            }
        });

        txtSpeed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSpeedKeyPressed(evt);
            }
        });

        jLabel8.setText("Warranty:");

        jLabel10.setText("Made in:");

        txtNameElevator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameElevatorKeyPressed(evt);
            }
        });

        txtWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWeightActionPerformed(evt);
            }
        });
        txtWeight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtWeightKeyPressed(evt);
            }
        });

        jLabel9.setText("Speed:");

        btnSumit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_add.png"))); // NOI18N
        btnSumit.setText("Add");
        btnSumit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumitActionPerformed(evt);
            }
        });

        cbWarranty.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose an Option", "1 Year", "2 Years", "3 Years" }));
        cbWarranty.setToolTipText("");

        jLabel11.setText("Name:");

        jLabel12.setText("Type of :");

        txtMadein.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMadeinKeyPressed(evt);
            }
        });

        txtTypeElevator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTypeElevatorKeyPressed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel13.setText("USD");

        jLabel14.setText("Kg");

        jLabel15.setText("m/min");

        lbWarranty.setText("0");
        lbWarranty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbWarrantyMouseClicked(evt);
            }
        });

        btnOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_order.png"))); // NOI18N
        btnOrder.setText("Order");
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSumit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel)
                                .addGap(10, 10, 10)
                                .addComponent(btnOrder))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNameElevator)
                                    .addComponent(txtTypeElevator)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtMadein, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtSpeed, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtWeight, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel15))))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtImage, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpload))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lbImage, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))))
                    .addComponent(lbTitle))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtNameElevator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtTypeElevator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbWarranty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtMadein, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSumit)
                            .addComponent(btnCancel)
                            .addComponent(btnOrder)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpload))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbImage, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        JFileChooser fchoose = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fchoose.setFileFilter(imageFilter);
        int choose = fchoose.showDialog(this, "Select");
        if (choose == JFileChooser.APPROVE_OPTION) {
            File file = fchoose.getSelectedFile();
            String text = file.getPath();
            txtImage.setText(text);
            setImage(txtImage.getText());
            changeimage = 1;
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void txtNameElevatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameElevatorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && t != 1) {
            UpdateElevator();
        }
    }//GEN-LAST:event_txtNameElevatorKeyPressed

    private void txtWeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWeightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWeightActionPerformed

    private void btnSumitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumitActionPerformed
        UpdateElevator();
    }//GEN-LAST:event_btnSumitActionPerformed

    private void txtTypeElevatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTypeElevatorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && t != 1) {
            UpdateElevator();
        }
    }//GEN-LAST:event_txtTypeElevatorKeyPressed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void lbWarrantyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbWarrantyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbWarrantyMouseClicked

    private void txtPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && t != 1) {
            UpdateElevator();
        }
    }//GEN-LAST:event_txtPriceKeyPressed

    private void txtWeightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtWeightKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && t != 1) {
            UpdateElevator();
        }
    }//GEN-LAST:event_txtWeightKeyPressed

    private void txtSpeedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSpeedKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && t != 1) {
            UpdateElevator();
        }
    }//GEN-LAST:event_txtSpeedKeyPressed

    private void txtMadeinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMadeinKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && t != 1) {
            UpdateElevator();
        }
    }//GEN-LAST:event_txtMadeinKeyPressed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        List<Elevator> list = new ArrayList<Elevator>();
        list.add(e);
        ElevatorOrder oe = new ElevatorOrder(parent, true, null, null, list, employee);
        this.dispose();
        oe.setVisible(true);
    }//GEN-LAST:event_btnOrderActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnSumit;
    private javax.swing.JButton btnUpload;
    private javax.swing.JComboBox cbWarranty;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbImage;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbWarranty;
    private javax.swing.JTextField txtImage;
    private javax.swing.JTextField txtMadein;
    private javax.swing.JTextField txtNameElevator;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtSpeed;
    private javax.swing.JTextField txtTypeElevator;
    private javax.swing.JTextField txtWeight;
    // End of variables declaration//GEN-END:variables
}