/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import models.TableCellListener;
import models.ElevatorOrderTable;
import Connect.ObjectsData;
import java.awt.Frame;
import javax.swing.JOptionPane;
import objects.Bill;
import objects.Client;
import objects.Elevator;
import objects.Order;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import objects.Employee;
import ultity.SQLTools;

/**
 *
 * @author notte_000
 */
public class ElevatorOrder extends javax.swing.JDialog {
    public List<Elevator> listElevator;
    public ElevatorOrderTable tbProductModel;
    public Client client;
    Action action;
    Frame parent;
    Order order;
    Employee employee;
    
    public ElevatorOrder(java.awt.Frame parent, boolean modal, Order o, Client cl, List<Elevator> list, Employee employee) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
        this.listElevator = list;
        this.order = o;
        this.client = cl;
        this.employee = employee;
        setValue();
        TableCellListener tcl = new TableCellListener(tbProduct, action);
        btnRemove.setEnabled(false);
        if(cl != null){
            txtClientName.setText(cl.getName());
        }
        if(o != null){
            System.out.println(o.getClientname());
            btnChooseClient.setEnabled(false);
            txtClientName.setText(o.getClientname());
            btnOrder.setText("Update");
            btnReset.setText("Cancel");
            btnDlCancel.setText("Close");
            btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_cancel.png")));
            btnDlCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_close.png")));
        }
        this.setTitle("Order");
        this.setLocationRelativeTo(rootPane);
    }

    public void setValue() {
        /* tbElevator Data */
        float price = 0;
        if(listElevator != null){
            for (Elevator el : listElevator) {
                price += el.getPrice() * el.getQuantity();
            }
        }
        tbProductModel = new ElevatorOrderTable(listElevator);
        tbProduct.setModel(tbProductModel);
        tbProduct.getColumnModel().getColumn(0).setMinWidth(155);
        tbProduct.getColumnModel().getColumn(1).setMinWidth(95);
        tbProduct.getColumnModel().getColumn(2).setMinWidth(50);
        txtPrice.setText(price + "");
        /* End tbElevator Data */
    }

    public void setClient(Client cli) {
        if(cli != null){
            this.client = cli;
            txtClientName.setText(client.getName());
        }
    }

    public void Order() {
        if(!txtClientName.getText().equals("")){
            long time = System.currentTimeMillis();
            Date date = txtDeliveryDate.getDate();
            SimpleDateFormat sf = new SimpleDateFormat("M/d/Y");
            String deliverydate = sf.format(date);
            order = new Order(client.getId(), time, deliverydate, employee.getId(), Float.parseFloat(txtPrice.getText()), 1);
            SQLTools st = new SQLTools();
            int s = st.AddOrder(order);
            if (s == 1) {
                ObjectsData od = new ObjectsData();
                Order o = od.getLastOrder();
                for (Elevator el : listElevator) {
                    Bill b = new Bill(o.getId(), el.getId(), el.getQuantity());
                    st.AddBill(b);
                }
                JOptionPane.showMessageDialog(this, "Order successful!");
                this.dispose();
            }
        }
        else{
            JOptionPane.showMessageDialog(parent, "Please choose client!");
        }
    }
    
    public void Update() {
        SQLTools st = new SQLTools();
        if(listElevator != null){
            order.setEmployeeid(employee.getId());
            order.setPrice(Float.parseFloat(txtPrice.getText()));
            int status = order.getStatus();
            if(status < 2){
                int e = st.EditOrder(order);
                if(e == 1){
                    int r = st.removeBill(order.getId());
                    if(r == 1){
                        for (Elevator el : listElevator) {
                            Bill b = new Bill(order.getId(), el.getId(), el.getQuantity());
                            st.AddBill(b);
                        }
                        JOptionPane.showMessageDialog(this, "Order updated!");
                        this.dispose();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Order not found!");
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Cannot update this order!");
            }
        }
        else{
            int confirm = JOptionPane.showConfirmDialog(this, "Empty order! Do you want cancel this order?",null, 2);
            if(confirm == 0){
                Cancel();
            }
        }
    }
    
    public void Cancel(){
        SQLTools st = new SQLTools();
        int e = st.EditOrder(order);
        if(e == 1){
            JOptionPane.showMessageDialog(this, "Order cancelled!");
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Order not found!");
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

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnOrder = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnDlCancel = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnChooseClient = new javax.swing.JButton();
        txtClientName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDeliveryDate = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbProduct = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(610, 244));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(280, 199));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(247, 35));

        btnOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_order.png"))); // NOI18N
        btnOrder.setText("Order");
        btnOrder.setToolTipText("");
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });
        jPanel3.add(btnOrder);

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_reset.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel3.add(btnReset);

        btnDlCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_cancel.png"))); // NOI18N
        btnDlCancel.setText("Cancel");
        btnDlCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDlCancelActionPerformed(evt);
            }
        });
        jPanel3.add(btnDlCancel);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(280, 165));

        txtPrice.setEditable(false);

        jLabel6.setText("Total Price:");

        jLabel5.setText("Delivery Date");

        jLabel3.setText("Client's Name:");

        btnChooseClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_client.png"))); // NOI18N
        btnChooseClient.setText("Choose Client");
        btnChooseClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseClientActionPerformed(evt);
            }
        });

        txtClientName.setEditable(false);

        jLabel2.setText("USD");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnChooseClient)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(txtClientName)
                    .addComponent(txtDeliveryDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(btnChooseClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDeliveryDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setPreferredSize(new java.awt.Dimension(322, 199));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(322, 35));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel5.add(btnAdd);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_remove.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jPanel5.add(btnRemove);

        jPanel2.add(jPanel5, java.awt.BorderLayout.SOUTH);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 130));

        tbProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbProductMousePressed(evt);
            }
        });
        tbProduct.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbProductPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(tbProduct);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setPreferredSize(new java.awt.Dimension(322, 35));

        jLabel1.setText("Products Order");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(248, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChooseClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseClientActionPerformed
        ClientManagement cm = new ClientManagement(this, true, 1); /* 1 is order */
        cm.setVisible(true);
    }//GEN-LAST:event_btnChooseClientActionPerformed

    private void btnDlCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDlCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnDlCancelActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        if(btnReset.getText().equals("Reset")){
            txtPrice.setText("");
            txtClientName.setText("");
        }
        else{
            order.setStatus(0);
            Cancel();
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        switch (btnOrder.getText()) {
            case "Order":
                Order();
                break;
            case "Update":
                Update();
                break;
        }
    }//GEN-LAST:event_btnOrderActionPerformed

    private void tbProductPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbProductPropertyChange
        action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableCellListener tcl = (TableCellListener)e.getSource();
                Elevator el = listElevator.get(tcl.getRow());
                for (Elevator elevator : listElevator) {
                    if(el.getId() == elevator.getId()){
                        elevator.setQuantity((int) tcl.getNewValue());
                    }
                }
                setValue();
            }
        };
    }//GEN-LAST:event_tbProductPropertyChange

    private void tbProductMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductMousePressed
        btnRemove.setEnabled(true);
    }//GEN-LAST:event_tbProductMousePressed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        this.dispose();
        new ElevatorOrderMore(parent, true, order, client, listElevator, employee).setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int rows[] = tbProduct.getSelectedRows();
        if(rows.length > 0 && rows.length <= listElevator.size()){
            List<Elevator> delList = new ArrayList<>();
            for (int i : rows) {
                Elevator el = listElevator.get(i);
                delList.add(el);
            }
            listElevator.removeAll(delList);
            setValue();
            if(listElevator.isEmpty()){
                listElevator = null;
            }
        }
        else{
            JOptionPane.showMessageDialog(parent, "Please choose atleast one!");
        }
        btnRemove.setEnabled(false);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnChooseClient;
    private javax.swing.JButton btnDlCancel;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbProduct;
    private javax.swing.JTextField txtClientName;
    private com.toedter.calendar.JDateChooser txtDeliveryDate;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
