/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import models.*;
import Connect.ObjectsData;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import objects.*;
import ultity.*;

/**
 *
 * @author notte_000
 */
public class AdminMain extends javax.swing.JFrame {

    ElevatorTable etm;
    ElevatorDetailTable edt;
    OrderTable otm;
    OrderDetailTable odt;
    ComplaintTable ct;
    MaintenanceTable mt;
    EmployeeTable et;
    ClientTable ctb;
    SQLTools st = new SQLTools();
    List<Elevator> listElevator = new ArrayList<>();
    List<Order> listOrder = new ArrayList<>();
    List<Employee> listEmployee = new ArrayList<>();
    List<Elevator> listElevatorOrder = new ArrayList<>();
    List<Complaint> listComplaint = new ArrayList<>();
    List<Maintenance> listMaintenance = new ArrayList<>();
    List<Client> listClient = new ArrayList<>();
    ObjectsData od = new ObjectsData();
    boolean isSearchFocus;
    Integer totalRows = 0;
    Integer pageNumber = 1;
    Integer totalPage = 1;
    Integer rowsPerPage = 20;
    String slSearch = "";
    String textSearch = "";
    Employee employee;

    public AdminMain() {
        initComponents();
        this.setLocationRelativeTo(rootPane);
        mnConfig.setEnabled(false);
        this.setVisible(true);
        this.setTitle("Administrator Manager");
        employee = new Employee(1, 1, "admin", "Administrator", 1, null, null);
    }

    //begin page
    private void initListOrder(String s, String i) {
        totalRows = od.getTotalOrder(s, i);
        listOrder = od.getAllOrder(pageNumber, rowsPerPage, s, i);
        otm = new OrderTable(listOrder);
        tbListOrder.setModel(otm);
        TableColorRender.clear();
        tbListOrder.setDefaultRenderer(Object.class, new TableColorRender());
        tbListOrder.getColumnModel().getColumn(0).setMinWidth(80);
        tbListOrder.getColumnModel().getColumn(1).setMinWidth(165);
        tbListOrder.getColumnModel().getColumn(2).setMinWidth(200);
        tbListOrder.getColumnModel().getColumn(3).setMinWidth(90);
        tbListOrder.getColumnModel().getColumn(4).setMinWidth(50);
        Paging();
    }

    private void initListEmployee(String s, String i) {
        totalRows = od.getTotalEmployee(s, i);
        listEmployee = od.getAllEmployee(pageNumber, rowsPerPage, s, i);
        et = new EmployeeTable(listEmployee);
        tbListEmployee.setModel(et);
        TableColorRender.clear();
        tbListEmployee.setDefaultRenderer(Object.class, new TableColorRender());
        Paging();
    }

    private void initListClient(String s, String i) {
        totalRows = od.getTotalClient(s, i);
        listClient = od.getAllClient(pageNumber, rowsPerPage, s, i);
        ctb = new ClientTable(listClient);
        tbListClient.setModel(ctb);
        TableColorRender.clear();
        tbListClient.setDefaultRenderer(Object.class, new TableColorRender());
        Paging();
    }

    private void initListMaintenance(String s, String i) {
        totalRows = od.getTotalMaintenance(s, i);
        listMaintenance = od.getAllMaintenance(pageNumber, rowsPerPage, s, i);
        mt = new MaintenanceTable(listMaintenance);
        tbMaintenance.setModel(mt);
        TableColorRender.clear();
        tbMaintenance.setDefaultRenderer(Object.class, new TableColorRender());
        Paging();
    }

    public void initListElevator(String s, String i) {
        totalRows = od.getTotalElevator(s, i, null);
        listElevator = od.getAllElevator(pageNumber, rowsPerPage, s, i, null);
        etm = new ElevatorTable(listElevator);
        tbListElevator.setModel(etm);
        TableColorRender.clear();
        tbListElevator.setDefaultRenderer(Object.class, new TableColorRender());
        tbListElevator.getColumnModel().getColumn(0).setMinWidth(170);
        tbListElevator.getColumnModel().getColumn(1).setMinWidth(110);
        tbListElevator.getColumnModel().getColumn(2).setMinWidth(90);
        Paging();
    }

    public void initListComplaint(String s, String i, String o) {
        totalRows = od.getTotalComplaint(s, i);
        listComplaint = od.getAllComplaint(pageNumber, rowsPerPage, s, i, o);
        ct = new ComplaintTable(listComplaint);
        tbListComplaint.setModel(ct);
        TableColorRender.clear();
        tbListComplaint.setDefaultRenderer(Object.class, new TableColorRender());
        Paging();
    }

    private void Paging() {
        Double dblTotPage = Math.ceil(totalRows.doubleValue() / rowsPerPage.doubleValue());
        totalPage = dblTotPage.intValue();
        if (pageNumber == 1) {
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
        } else {
            btnFirst.setEnabled(true);
            btnPrevious.setEnabled(true);
        }

        if (pageNumber.equals(totalPage)) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        }
        if (totalRows == 0) {
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            pageNumber = 0;
        }
        txtPageNumber.setText(String.valueOf(pageNumber));
        lblPageOf.setText(" of " + totalPage + " ");
        lblTotalRecord.setText("Total record " + totalRows);
    }

    public void viewImage(Elevator e) {
        File fdir = new File(System.getProperty("java.class.path"));
        File dir = fdir.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        File imgcheck = new File(path + "/Images/" + e.getImage());
        ImageIcon icon;
        if (imgcheck.exists()) {
            icon = new ImageIcon(path + "/Images/" + e.getImage());
        } else {
            icon = new ImageIcon(getClass().getResource("/Images/no-available-image.png"));
        }
        Image img = icon.getImage();
        float width = icon.getIconWidth();
        int height = icon.getIconHeight();
        int h = (int) (height / (width / 186));
        int w = 186;
        Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        Icon newIcon = new ImageIcon(newimg);
        lbImage.setIcon(newIcon);
    }

    public void Cancel(Order o) {
        o.setStatus(0);
        int e = st.EditOrder(o);
        if (e == 1) {
            JOptionPane.showMessageDialog(this, "Order cancelled!");
            tbListOrder.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Order not found!");
        }
    }

    public void Bill(Order o) {
        o.setStatus(2);
        int e = st.EditOrder(o);
        if (e == 1) {
            JOptionPane.showMessageDialog(this, "Order billed!");
            tbListOrder.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Order not found!");
        }
    }

    public void Ship(Order o) {
        Date deliverydate = new Date(System.currentTimeMillis());
        Date orderdate = new Date(o.getOrderdate());
        SimpleDateFormat sf = new SimpleDateFormat("M/d/Y");
        o.setStatus(3);
        o.setDeliverydate(sf.format(deliverydate));
        int e = st.EditOrder(o);
        List<Bill> nListBill = od.getBillByOrderId(o.getId());
        for (Bill bill : nListBill) {
            Elevator ell = od.getElevator(bill.getElevatorid());
            String firstyear = Tools.nextYear(sf.format(deliverydate));
            String secondyear = "";
            String thirdyear = "";
            if (ell.getWarranty() > 1) {
                secondyear = Tools.nextYear(firstyear);
                if (ell.getWarranty() > 2) {
                    thirdyear = Tools.nextYear(secondyear);
                }
            }
            Maintenance m = new Maintenance(bill.getId(), bill.getOrderid(), sf.format(orderdate), o.getDeliverydate(), firstyear, secondyear, thirdyear);
            st.addMaintenance(m);
        }
        if (e == 1) {
            JOptionPane.showMessageDialog(this, "Order shipped!");
            tbListOrder.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Order not found!");
        }
    }

    private void searchResult() {
        int select = cbSearch.getSelectedIndex();
        if (pnListElevator.isVisible()) {
            switch (select) {
                case 0:
                    slSearch = "ALL";
                    break;
                case 1:
                    slSearch = "name";
                    break;
                case 2:
                    slSearch = "type";
                    break;
                case 3:
                    slSearch = "price";
                    break;
                case 4:
                    slSearch = "speed";
                    break;
                case 5:
                    slSearch = "weight";
                    break;
                case 6:
                    slSearch = "warranty";
                    break;
                case 7:
                    slSearch = "madein";
                    break;
                default:
                    slSearch = "";
                    break;
            }
            textSearch = txtSearch.getText().trim();
            pageNumber = 1;

            initListElevator(textSearch, slSearch);
        } else if (pnListOrder.isVisible()) {
            switch (select) {
                case 0:
                    slSearch = "ALL";
                    break;
                case 1:
                    slSearch = "name";
                    break;
                case 2:
                    slSearch = "price";
                    break;
                default:
                    slSearch = "";
                    break;
            }
            textSearch = txtSearch.getText().trim();
            pageNumber = 1;
            initListOrder(textSearch, slSearch);
        } else if (pnListComplaint.isVisible()) {
            switch (select) {
                case 0:
                    slSearch = "ALL";
                    break;
                case 1:
                    slSearch = "complaint";
                    break;
                case 2:
                    slSearch = "resolve";
                    break;
                default:
                    slSearch = "";
                    break;
            }
            textSearch = txtSearch.getText().trim();
            pageNumber = 1;
            initListComplaint(textSearch, slSearch, "ASC");
        }
        else if(pnListEmployee.isVisible()){
            switch (select) {
                case 0:
                    slSearch = "ALL";
                    break;
                case 1:
                    slSearch = "username";
                    break;
                case 2:
                    slSearch = "fullname";
                    break;
                case 3:
                    slSearch = "email";
                    break;
                case 4:
                    slSearch = "contact";
                    break;
                default:
                    slSearch = "";
                    break;
            }
            textSearch = txtSearch.getText().trim();
            pageNumber = 1;
            initListEmployee(textSearch, slSearch);
        }
        else if(pnListClient.isVisible()){
            switch (select) {
                case 0:
                    slSearch = "ALL";
                    break;
                case 1:
                    slSearch = "name";
                    break;
                case 2:
                    slSearch = "company";
                    break;
                case 3:
                    slSearch = "address";
                    break;
                case 4:
                    slSearch = "email";
                    break;
                case 5:
                    slSearch = "mobile";
                    break;
                default:
                    slSearch = "";
                    break;
            }
            textSearch = txtSearch.getText().trim();
            pageNumber = 1;
            initListClient(textSearch, slSearch);
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

        ToolbarPanel = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        cbSearch = new javax.swing.JComboBox();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        MainPanel = new javax.swing.JPanel();
        pnOrder = new javax.swing.JPanel();
        pnTab = new javax.swing.JTabbedPane();
        pnListEmployee = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbListEmployee = new javax.swing.JTable();
        pnListClient = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbListClient = new javax.swing.JTable();
        pnListElevator = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        leftListElevator = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListElevator = new javax.swing.JTable();
        rightListElevator = new javax.swing.JPanel();
        lbImage = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListElevatorDetail = new javax.swing.JTable();
        pnListOrder = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbListOrder = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbListOrderDetail = new javax.swing.JTable();
        pnListComplaint = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        pnMessage = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtResolve = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        btncReset = new javax.swing.JButton();
        btncReply = new javax.swing.JButton();
        lbrDate = new javax.swing.JLabel();
        pnResolve = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtComplaint = new javax.swing.JTextArea();
        lbcDate = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbListComplaint = new javax.swing.JTable();
        pnListMaintenance = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbMaintenance = new javax.swing.JTable();
        PagePanel = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtPageNumber = new javax.swing.JTextField();
        lblPageOf = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblTotalRecord = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnLogout = new javax.swing.JMenuItem();
        mnConfig = new javax.swing.JMenuItem();
        mnExit = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        mnAddElevator = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mnNewEmployee = new javax.swing.JMenuItem();
        mnChangePassword = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnUsersGuide = new javax.swing.JMenuItem();
        mnAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(640, 550));
        setPreferredSize(new java.awt.Dimension(768, 578));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        ToolbarPanel.setMaximumSize(new java.awt.Dimension(32767, 23));
        ToolbarPanel.setMinimumSize(new java.awt.Dimension(0, 23));

        jToolBar2.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/m_order.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setToolTipText("Order this");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setMaximumSize(new java.awt.Dimension(58, 58));
        btnAdd.setMinimumSize(new java.awt.Dimension(58, 58));
        btnAdd.setName(""); // NOI18N
        btnAdd.setPreferredSize(new java.awt.Dimension(58, 58));
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar2.add(btnAdd);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/m_edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setToolTipText("Edit");
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setMaximumSize(new java.awt.Dimension(58, 58));
        btnEdit.setMinimumSize(new java.awt.Dimension(58, 58));
        btnEdit.setPreferredSize(new java.awt.Dimension(58, 58));
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar2.add(btnEdit);

        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/m_view.png"))); // NOI18N
        btnView.setText("View");
        btnView.setToolTipText("View detail");
        btnView.setFocusable(false);
        btnView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnView.setMaximumSize(new java.awt.Dimension(58, 58));
        btnView.setMinimumSize(new java.awt.Dimension(58, 58));
        btnView.setPreferredSize(new java.awt.Dimension(58, 58));
        btnView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar2.add(btnView);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/m_delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setMaximumSize(new java.awt.Dimension(58, 58));
        btnDelete.setMinimumSize(new java.awt.Dimension(58, 58));
        btnDelete.setPreferredSize(new java.awt.Dimension(58, 58));
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar2.add(btnDelete);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/m_reload.png"))); // NOI18N
        btnRefresh.setText("Reload");
        btnRefresh.setToolTipText("Refresh");
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setMaximumSize(new java.awt.Dimension(58, 58));
        btnRefresh.setMinimumSize(new java.awt.Dimension(58, 58));
        btnRefresh.setPreferredSize(new java.awt.Dimension(58, 58));
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRefresh);

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/m_print.png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setToolTipText("Print the invoice");
        btnPrint.setFocusable(false);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrint.setMaximumSize(new java.awt.Dimension(58, 58));
        btnPrint.setMinimumSize(new java.awt.Dimension(58, 58));
        btnPrint.setPreferredSize(new java.awt.Dimension(58, 58));
        btnPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jToolBar2.add(btnPrint);

        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Name", "Type", "Price", "Speed", "Weight", "Warranty", "Made in" }));
        cbSearch.setMaximumSize(new java.awt.Dimension(71, 20));
        cbSearch.setName(""); // NOI18N
        cbSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSearchActionPerformed(evt);
            }
        });

        txtSearch.setForeground(new java.awt.Color(204, 204, 204));
        txtSearch.setText("Search");
        txtSearch.setToolTipText("Enter to complete search");
        txtSearch.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        txtSearch.setMinimumSize(new java.awt.Dimension(55, 20));
        txtSearch.setPreferredSize(new java.awt.Dimension(85, 20));
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_search.png"))); // NOI18N
        btnSearch.setBorderPainted(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setMaximumSize(new java.awt.Dimension(22, 22));
        btnSearch.setMinimumSize(new java.awt.Dimension(22, 22));
        btnSearch.setPreferredSize(new java.awt.Dimension(22, 22));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ToolbarPanelLayout = new javax.swing.GroupLayout(ToolbarPanel);
        ToolbarPanel.setLayout(ToolbarPanelLayout);
        ToolbarPanelLayout.setHorizontalGroup(
            ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ToolbarPanelLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(cbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ToolbarPanelLayout.setVerticalGroup(
            ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ToolbarPanelLayout.createSequentialGroup()
                .addGroup(ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ToolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(ToolbarPanel, java.awt.BorderLayout.NORTH);

        MainPanel.setLayout(new java.awt.BorderLayout());

        pnOrder.setLayout(new java.awt.BorderLayout());

        pnTab.setName(""); // NOI18N

        pnListEmployee.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnListEmployeeComponentShown(evt);
            }
        });
        pnListEmployee.setLayout(new java.awt.BorderLayout());

        tbListEmployee.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbListEmployeeMousePressed(evt);
            }
        });
        tbListEmployee.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbListEmployeeMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbListEmployeeMouseDragged(evt);
            }
        });
        jScrollPane9.setViewportView(tbListEmployee);

        pnListEmployee.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        pnTab.addTab("List Employee", new javax.swing.ImageIcon(getClass().getResource("/Icons/s_employee.png")), pnListEmployee); // NOI18N

        pnListClient.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnListClientComponentShown(evt);
            }
        });

        tbListClient.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbListClientMousePressed(evt);
            }
        });
        tbListClient.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbListClientMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbListClientMouseDragged(evt);
            }
        });
        jScrollPane10.setViewportView(tbListClient);

        javax.swing.GroupLayout pnListClientLayout = new javax.swing.GroupLayout(pnListClient);
        pnListClient.setLayout(pnListClientLayout);
        pnListClientLayout.setHorizontalGroup(
            pnListClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
        );
        pnListClientLayout.setVerticalGroup(
            pnListClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnListClientLayout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 155, Short.MAX_VALUE))
        );

        pnTab.addTab("List Client", new javax.swing.ImageIcon(getClass().getResource("/Icons/s_client.png")), pnListClient); // NOI18N

        pnListElevator.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnListElevatorComponentShown(evt);
            }
        });
        pnListElevator.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(4);
        jSplitPane1.setAutoscrolls(true);

        leftListElevator.setAutoscrolls(true);
        leftListElevator.setMinimumSize(new java.awt.Dimension(500, 519));
        leftListElevator.setPreferredSize(new java.awt.Dimension(500, 519));
        leftListElevator.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setAutoscrolls(true);

        tbListElevator.setAutoCreateRowSorter(true);
        tbListElevator.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListElevator.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbListElevator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbListElevatorMousePressed(evt);
            }
        });
        tbListElevator.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbListElevatorMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbListElevatorMouseDragged(evt);
            }
        });
        jScrollPane2.setViewportView(tbListElevator);

        leftListElevator.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(leftListElevator);

        rightListElevator.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));
        rightListElevator.setMaximumSize(new java.awt.Dimension(200, 32767));
        rightListElevator.setMinimumSize(new java.awt.Dimension(200, 100));
        rightListElevator.setPreferredSize(new java.awt.Dimension(200, 519));

        tbListElevatorDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Property", "Value"
            }
        ));
        tbListElevatorDetail.setAutoscrolls(false);
        jScrollPane1.setViewportView(tbListElevatorDetail);

        javax.swing.GroupLayout rightListElevatorLayout = new javax.swing.GroupLayout(rightListElevator);
        rightListElevator.setLayout(rightListElevatorLayout);
        rightListElevatorLayout.setHorizontalGroup(
            rightListElevatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(lbImage, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );
        rightListElevatorLayout.setVerticalGroup(
            rightListElevatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightListElevatorLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbImage, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(rightListElevator);

        pnListElevator.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pnTab.addTab("List Elevator", new javax.swing.ImageIcon(getClass().getResource("/Icons/s_product.png")), pnListElevator); // NOI18N

        pnListOrder.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnListOrderComponentShown(evt);
            }
        });
        pnListOrder.setLayout(new java.awt.BorderLayout());

        tbListOrder.setAutoCreateRowSorter(true);
        tbListOrder.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbListOrderMousePressed(evt);
            }
        });
        tbListOrder.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbListOrderMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbListOrderMouseDragged(evt);
            }
        });
        jScrollPane3.setViewportView(tbListOrder);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
        );

        pnListOrder.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));

        tbListOrderDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Product Name", "Quantity", "Unit Price", "Amount"
            }
        ));
        jScrollPane4.setViewportView(tbListOrderDetail);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
        );

        pnListOrder.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pnTab.addTab("List Order", new javax.swing.ImageIcon(getClass().getResource("/Icons/s_order.png")), pnListOrder); // NOI18N

        pnListComplaint.setName(""); // NOI18N
        pnListComplaint.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnListComplaintComponentShown(evt);
            }
        });
        pnListComplaint.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(763, 207));
        jPanel3.setLayout(new java.awt.BorderLayout());

        txtResolve.setColumns(20);
        txtResolve.setLineWrap(true);
        txtResolve.setRows(2);
        txtResolve.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtResolveFocusGained(evt);
            }
        });
        jScrollPane7.setViewportView(txtResolve);

        jLabel3.setText("Resolve:");

        btncReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_reset.png"))); // NOI18N
        btncReset.setText("Reset");
        btncReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncResetActionPerformed(evt);
            }
        });

        btncReply.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_ok.png"))); // NOI18N
        btncReply.setText("Reply");
        btncReply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncReplyActionPerformed(evt);
            }
        });

        lbrDate.setText("jLabel5");

        javax.swing.GroupLayout pnMessageLayout = new javax.swing.GroupLayout(pnMessage);
        pnMessage.setLayout(pnMessageLayout);
        pnMessageLayout.setHorizontalGroup(
            pnMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMessageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lbrDate))
                .addGap(34, 34, 34)
                .addGroup(pnMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnMessageLayout.createSequentialGroup()
                        .addComponent(btncReply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncReset))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        pnMessageLayout.setVerticalGroup(
            pnMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMessageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbrDate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMessageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncReset)
                    .addComponent(btncReply))
                .addGap(20, 20, 20))
        );

        jPanel3.add(pnMessage, java.awt.BorderLayout.SOUTH);

        pnResolve.setPreferredSize(new java.awt.Dimension(763, 87));

        jLabel2.setText("Message:");

        txtComplaint.setEditable(false);
        txtComplaint.setColumns(20);
        txtComplaint.setLineWrap(true);
        txtComplaint.setRows(2);
        jScrollPane6.setViewportView(txtComplaint);

        lbcDate.setText("jLabel4");

        javax.swing.GroupLayout pnResolveLayout = new javax.swing.GroupLayout(pnResolve);
        pnResolve.setLayout(pnResolveLayout);
        pnResolveLayout.setHorizontalGroup(
            pnResolveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnResolveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnResolveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lbcDate))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );
        pnResolveLayout.setVerticalGroup(
            pnResolveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnResolveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbcDate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnResolveLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel3.add(pnResolve, java.awt.BorderLayout.NORTH);

        pnListComplaint.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel4.setPreferredSize(new java.awt.Dimension(763, 300));

        tbListComplaint.setAutoCreateRowSorter(true);
        tbListComplaint.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListComplaint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListComplaintMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbListComplaintMousePressed(evt);
            }
        });
        tbListComplaint.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbListComplaintMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbListComplaintMouseMoved(evt);
            }
        });
        jScrollPane5.setViewportView(tbListComplaint);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );

        pnListComplaint.add(jPanel4, java.awt.BorderLayout.CENTER);

        pnTab.addTab("Complaint", new javax.swing.ImageIcon(getClass().getResource("/Icons/s_complaint.png")), pnListComplaint, ""); // NOI18N

        pnListMaintenance.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnListMaintenanceComponentShown(evt);
            }
        });
        pnListMaintenance.setLayout(new java.awt.BorderLayout());

        tbMaintenance.setAutoCreateRowSorter(true);
        tbMaintenance.setModel(new javax.swing.table.DefaultTableModel(
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
        tbMaintenance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbMaintenanceMousePressed(evt);
            }
        });
        tbMaintenance.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbMaintenanceMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbMaintenanceMouseDragged(evt);
            }
        });
        jScrollPane8.setViewportView(tbMaintenance);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
        );

        pnListMaintenance.add(jPanel6, java.awt.BorderLayout.CENTER);

        pnTab.addTab("Maintenance", new javax.swing.ImageIcon(getClass().getResource("/Icons/s_maintenance.png")), pnListMaintenance); // NOI18N

        pnOrder.add(pnTab, java.awt.BorderLayout.PAGE_START);

        MainPanel.add(pnOrder, java.awt.BorderLayout.CENTER);

        getContentPane().add(MainPanel, java.awt.BorderLayout.CENTER);

        PagePanel.setMinimumSize(new java.awt.Dimension(100, 35));
        PagePanel.setPreferredSize(new java.awt.Dimension(768, 35));

        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/first.png"))); // NOI18N
        btnFirst.setBorderPainted(false);
        btnFirst.setContentAreaFilled(false);
        btnFirst.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFirstMouseExited(evt);
            }
        });
        btnFirst.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnFirstMouseMoved(evt);
            }
        });
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/previous.png"))); // NOI18N
        btnPrevious.setBorderPainted(false);
        btnPrevious.setContentAreaFilled(false);
        btnPrevious.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPreviousMouseExited(evt);
            }
        });
        btnPrevious.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnPreviousMouseMoved(evt);
            }
        });
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/last.png"))); // NOI18N
        btnLast.setBorderPainted(false);
        btnLast.setContentAreaFilled(false);
        btnLast.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLastMouseExited(evt);
            }
        });
        btnLast.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnLastMouseMoved(evt);
            }
        });
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/next.png"))); // NOI18N
        btnNext.setBorderPainted(false);
        btnNext.setContentAreaFilled(false);
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNextMouseExited(evt);
            }
        });
        btnNext.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnNextMouseMoved(evt);
            }
        });
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jLabel1.setText("Page");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txtPageNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPageNumberKeyPressed(evt);
            }
        });

        lblPageOf.setText("of 0");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblTotalRecord.setText("jLabel2");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout PagePanelLayout = new javax.swing.GroupLayout(PagePanel);
        PagePanel.setLayout(PagePanelLayout);
        PagePanelLayout.setHorizontalGroup(
            PagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagePanelLayout.createSequentialGroup()
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPageOf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalRecord)
                .addContainerGap(483, Short.MAX_VALUE))
        );
        PagePanelLayout.setVerticalGroup(
            PagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagePanelLayout.createSequentialGroup()
                .addGroup(PagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addComponent(txtPageNumber)
                    .addComponent(lblPageOf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addComponent(lblTotalRecord, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PagePanelLayout.createSequentialGroup()
                        .addGroup(PagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(PagePanel, java.awt.BorderLayout.SOUTH);

        jMenu1.setText("Settings");

        mnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_logout.png"))); // NOI18N
        mnLogout.setText("Logout");
        mnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(mnLogout);

        mnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_option.png"))); // NOI18N
        mnConfig.setText("Config");
        jMenu1.add(mnConfig);

        mnExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_remove.png"))); // NOI18N
        mnExit.setText("Exit");
        mnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnExit);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("Elevator");

        mnAddElevator.setText("Add Elevator");
        mnAddElevator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAddElevatorActionPerformed(evt);
            }
        });
        jMenu4.add(mnAddElevator);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Account");

        mnNewEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_information.png"))); // NOI18N
        mnNewEmployee.setText("New Employee");
        mnNewEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNewEmployeeActionPerformed(evt);
            }
        });
        jMenu3.add(mnNewEmployee);

        mnChangePassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_password.png"))); // NOI18N
        mnChangePassword.setText("Change Password");
        mnChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnChangePasswordActionPerformed(evt);
            }
        });
        jMenu3.add(mnChangePassword);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Help");

        mnUsersGuide.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mnUsersGuide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_help.png"))); // NOI18N
        mnUsersGuide.setText(" Users Guide");
        mnUsersGuide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUsersGuideActionPerformed(evt);
            }
        });
        jMenu2.add(mnUsersGuide);

        mnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/s_about.png"))); // NOI18N
        mnAbout.setText(" About");
        mnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAboutActionPerformed(evt);
            }
        });
        jMenu2.add(mnAbout);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        if (!txtSearch.getForeground().toString().equals("java.awt.Color[r=0,g=0,b=0]")) {
            txtSearch.setText("");
            txtSearch.setForeground(new java.awt.Color(0, 0, 0));
            isSearchFocus = true;
        }
        txtSearch.selectAll();
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        if (txtSearch.getText().equals("")) {
            int i = cbSearch.getItemCount();
            for (int j = 0; j < i; j++) {
                int k = cbSearch.getSelectedIndex();
                if(j == k){
                    txtSearch.setText(cbSearch.getItemAt(j).toString());
                }
            }
            txtSearch.setForeground(new java.awt.Color(204, 204, 204));
            isSearchFocus = false;
        }
    }//GEN-LAST:event_txtSearchFocusLost

    private void cbSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSearchActionPerformed
        if (isSearchFocus == false) {
            int i = cbSearch.getItemCount();
            for (int j = 0; j < i; j++) {
                int k = cbSearch.getSelectedIndex();
                if(j == k){
                    txtSearch.setText(cbSearch.getItemAt(j).toString());
                }
            }
        }
    }//GEN-LAST:event_cbSearchActionPerformed

    private void mnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnExitActionPerformed

    private void mnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLogoutActionPerformed
        this.dispose();
        new Main().setVisible(true);
    }//GEN-LAST:event_mnLogoutActionPerformed

    private void mnUsersGuideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUsersGuideActionPerformed
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("Lib/ManualGuide.chm");
                Desktop.getDesktop().open(myFile);
            } catch (Exception ex) {
                new MainDialog(this, true, "cardUsersGuide").setVisible(true);
            }
        }
    }//GEN-LAST:event_mnUsersGuideActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (pnListElevator.isVisible()) {
            new ElevatorAdd(this, true).setVisible(true);
        }
        else if(pnListEmployee.isVisible()){
            new EmployeeAdd(this, true, null).setVisible(true);
        }
        else if(pnListClient.isVisible()){
            new ClientAdd(this, true).setVisible(true);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        if (pnListElevator.isVisible()) {
            int i = tbListElevator.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose an Elevator");
            } 
            else {
                Elevator e = etm.getElevator(i);
                ElevatorViewEdit eve = new ElevatorViewEdit(this, true, e, 1, true, employee);
                eve.setVisible(true);
            }
        } 
        else if (pnListComplaint.isVisible()) {
            int i = tbListComplaint.getSelectedRow();
            if (i != -1) {
                Complaint cp = listComplaint.get(i);
                new ComplaintView(this, true, cp).setVisible(true);
            } else 
            {
                JOptionPane.showMessageDialog(this, "Please choose one");
            }
        } 
        else if (pnListMaintenance.isVisible()) {
            int i = tbMaintenance.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose one");
            } 
            else {
                Maintenance mtt = listMaintenance.get(i);
                mtt.setEmployeeid(employee.getId());
                new MaintenanceView(this, true, mtt).setVisible(true);
            }
        }
        else if(pnListEmployee.isVisible()){
            int i = tbListEmployee.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose one");
            } 
            else {
                Employee emmm = listEmployee.get(i);
                new EmployeeView(this, true, emmm).setVisible(true);
            }
        }
        else if(pnListClient.isVisible()){
            int i = tbListClient.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose one");
            } 
            else {
                Client cll = listClient.get(i);
                new ClientView(this, true, cll).setVisible(true);
            }
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnFirstMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFirstMouseExited
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/first.png")));
    }//GEN-LAST:event_btnFirstMouseExited

    private void btnFirstMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFirstMouseMoved
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/first_m.png")));
    }//GEN-LAST:event_btnFirstMouseMoved

    private void btnPreviousMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreviousMouseExited
        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/previous.png")));
    }//GEN-LAST:event_btnPreviousMouseExited

    private void btnPreviousMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreviousMouseMoved
        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/previous_m.png")));
    }//GEN-LAST:event_btnPreviousMouseMoved

    private void btnNextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseExited
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/next.png")));
    }//GEN-LAST:event_btnNextMouseExited

    private void btnNextMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseMoved
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/next_m.png")));
    }//GEN-LAST:event_btnNextMouseMoved

    private void btnLastMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseExited
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/last.png")));
    }//GEN-LAST:event_btnLastMouseExited

    private void btnLastMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseMoved
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/last_m.png")));
    }//GEN-LAST:event_btnLastMouseMoved

    private void tbListElevatorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListElevatorMousePressed
        if (evt.getClickCount() == 2) {
            int i = tbListElevator.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose an Elevator");
            } else {
                Elevator e = etm.getElevator(i);
                ElevatorViewEdit eve = new ElevatorViewEdit(this, true, e, 1, true, employee);
                eve.setVisible(true);
            }
        }
        int i = tbListElevator.getSelectedRow();
        Elevator e = etm.getElevator(i);
        edt = new ElevatorDetailTable(e);
        tbListElevatorDetail.setVisible(true);
        tbListElevatorDetail.setModel(edt);
        viewImage(e);
        TableColorRender.rows = tbListElevator.getSelectedRows();
        tbListElevator.repaint();
        btnView.setEnabled(true);
        btnDelete.setEnabled(true);
    }//GEN-LAST:event_tbListElevatorMousePressed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchResult();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        textSearch = "";
        slSearch = "";
        pageNumber = 1;
        txtSearch.setText("");
        cbSearch.setSelectedIndex(0);
        isSearchFocus = false;
        if (pnListElevator.isVisible()) {
            initListElevator(textSearch, slSearch);
        } else if (pnListOrder.isVisible()) {
            initListOrder(textSearch, slSearch);
        } else if (pnListComplaint.isVisible()) {
            initListComplaint(textSearch, slSearch, "ASC");
        } else if (pnListMaintenance.isVisible()) {
            initListMaintenance(textSearch, slSearch);
        } else if (pnListClient.isVisible()){
            initListClient(textSearch, slSearch);
        } else if(pnListEmployee.isVisible()){
            initListEmployee(textSearch, slSearch);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        pageNumber = 1;
        initListElevator(textSearch, slSearch);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        if (pageNumber > 1) {
            pageNumber -= 1;
            initListElevator(textSearch, slSearch);
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (pageNumber < totalPage) {
            pageNumber += 1;
            initListElevator(textSearch, slSearch);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        pageNumber = totalPage;
        initListElevator(textSearch, slSearch);
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtPageNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPageNumberKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pageNumber = Integer.parseInt(txtPageNumber.getText());
            if (pageNumber > totalPage) {
                JOptionPane.showMessageDialog(rootPane, "Page number cannot larger than " + totalPage);
            } else {
                initListElevator(textSearch, slSearch);
            }
        }
    }//GEN-LAST:event_txtPageNumberKeyPressed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int dividerLocation = jSplitPane1.getWidth() - (170 + Math.round((jSplitPane1.getWidth() - 170) / 10));
        jSplitPane1.setDividerLocation(dividerLocation);
    }//GEN-LAST:event_formComponentResized

    private void pnListOrderComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnListOrderComponentShown
        totalRows = 0;
        pageNumber = 1;
        totalPage = 1;
        rowsPerPage = 20;
        slSearch = "";
        textSearch = "";
        initListOrder(textSearch, slSearch);
        btnAdd.setVisible(false);
        btnEdit.setVisible(false);
        btnView.setVisible(false);
        btnPrint.setVisible(true);
        btnPrint.setEnabled(false);
        cbSearch.setVisible(true);
        txtSearch.setVisible(true);
        btnSearch.setVisible(true);
        btnDelete.setVisible(false);
        tbListOrderDetail.getColumnModel().getColumn(0).setMinWidth(320);
        tbListOrderDetail.getColumnModel().getColumn(0).setMinWidth(80);
        tbListOrderDetail.getColumnModel().getColumn(0).setMinWidth(150);
        tbListOrderDetail.getColumnModel().getColumn(0).setMinWidth(150);
        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"All", "Name", "Price"}));
        txtSearch.setText("All");
    }//GEN-LAST:event_pnListOrderComponentShown

    private void pnListElevatorComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnListElevatorComponentShown
        totalRows = 0;
        pageNumber = 1;
        totalPage = 1;
        rowsPerPage = 20;
        slSearch = "";
        textSearch = "";
        Elevator e = new Elevator();
        initListElevator(textSearch, slSearch);
        viewImage(e);
        btnView.setVisible(true);
        btnView.setEnabled(false);
        btnAdd.setVisible(true);
        btnAdd.setEnabled(true);
        btnPrint.setVisible(false);
        btnDelete.setVisible(true);
        btnDelete.setEnabled(false);
        btnEdit.setVisible(true);
        tbListElevatorDetail.setVisible(false);
        cbSearch.setVisible(true);
        txtSearch.setVisible(true);
        btnSearch.setVisible(true);
        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"All", "Name", "Type", "Price", "Speed", "Weight", "Warranty", "Made in"}));
        txtSearch.setText("All");
    }//GEN-LAST:event_pnListElevatorComponentShown

    private void tbListElevatorMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListElevatorMouseMoved
        TableColorRender.rowcolumn = tbListElevator.rowAtPoint(evt.getPoint());
        tbListElevator.repaint();
    }//GEN-LAST:event_tbListElevatorMouseMoved

    private void tbListElevatorMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListElevatorMouseDragged
        TableColorRender.rows = tbListElevator.getSelectedRows();
        tbListElevator.repaint();
        btnView.setEnabled(false);
    }//GEN-LAST:event_tbListElevatorMouseDragged

    private void tbListOrderMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListOrderMouseMoved
        TableColorRender.rowcolumn = tbListOrder.rowAtPoint(evt.getPoint());
        tbListOrder.repaint();
    }//GEN-LAST:event_tbListOrderMouseMoved

    private void tbListOrderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListOrderMouseDragged
        TableColorRender.rows = tbListOrder.getSelectedRows();
        tbListOrder.repaint();
        int rows[] = tbListOrder.getSelectedRows();
        if (rows.length > 1) {
            btnAdd.setEnabled(false);
        } else {
            btnAdd.setEnabled(true);
        }
    }//GEN-LAST:event_tbListOrderMouseDragged

    private void tbListOrderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListOrderMousePressed
        int row = tbListOrder.getSelectedRow();
        Order o = listOrder.get(row);
        listElevatorOrder = od.getOrderDetail(o.getId());
        odt = new OrderDetailTable(listElevatorOrder);
        tbListOrderDetail.setModel(odt);
        TableColorRender.rows = tbListOrder.getSelectedRows();
        btnAdd.setVisible(false);
        if (o.getStatus() == 0) {
            btnPrint.setEnabled(false);
        } else if (o.getStatus() == 1) {
            btnPrint.setEnabled(false);
        } else if (o.getStatus() == 2) {
            btnPrint.setEnabled(true);
        } else {
            btnPrint.setEnabled(true);
        }
    }//GEN-LAST:event_tbListOrderMousePressed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchResult();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        int row = tbListOrder.getSelectedRow();
        Order o = listOrder.get(row);
        Client c = od.getClientById(o.getClientid());
        new OrderPrint(this, true, c, o).setVisible(true);
    }//GEN-LAST:event_btnPrintActionPerformed

    private void mnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAboutActionPerformed
        new AboutUs(this, true).setVisible(true);
    }//GEN-LAST:event_mnAboutActionPerformed

    private void mnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnChangePasswordActionPerformed
        new UserChangePassword(this, true, employee).setVisible(true);
    }//GEN-LAST:event_mnChangePasswordActionPerformed

    private void pnListComplaintComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnListComplaintComponentShown
        totalRows = 0;
        pageNumber = 1;
        totalPage = 1;
        rowsPerPage = 20;
        slSearch = "";
        textSearch = "";
        initListComplaint(textSearch, slSearch, "ASC");
        btnPrint.setVisible(false);
        btnEdit.setVisible(false);
        btnView.setVisible(true);
        btnView.setEnabled(false);
        btnAdd.setVisible(false);
        btnEdit.setVisible(false);
        pnMessage.setVisible(false);
        pnResolve.setVisible(false);
        cbSearch.setVisible(true);
        txtSearch.setVisible(true);
        btnSearch.setVisible(true);
        btnDelete.setVisible(true);
        btnDelete.setEnabled(false);
        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"All", "Complaint", "Resolve"}));
        txtSearch.setText("All");
    }//GEN-LAST:event_pnListComplaintComponentShown

    private void tbListComplaintMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListComplaintMousePressed
        int i = tbListComplaint.getSelectedRow();
        if (i != -1) {
            Complaint cp = listComplaint.get(i);
            pnMessage.setVisible(true);
            pnResolve.setVisible(true);
            txtComplaint.setText(cp.getComplaint());
            txtResolve.setText(cp.getResolve());
            lbcDate.setText(cp.getCdate());
            lbrDate.setText(cp.getRdate());
            if (!txtResolve.getText().equals("")) {
                btncReply.setText("Update");
            } else {
                btncReply.setText("Reply");
            }
        }
        btnView.setEnabled(true);
        btnDelete.setEnabled(true);
        TableColorRender.rows = tbListComplaint.getSelectedRows();
    }//GEN-LAST:event_tbListComplaintMousePressed

    private void btncResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncResetActionPerformed
        txtResolve.setText("");
    }//GEN-LAST:event_btncResetActionPerformed

    private void btncReplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncReplyActionPerformed
        int select = tbListComplaint.getSelectedRow();
        Complaint cp = listComplaint.get(select);
        String resolve = txtResolve.getText().trim();
        String rTest[] = resolve.split(" ");
        if (rTest.length > 3) {
            cp.setResolve(txtResolve.getText().trim());
            cp.setStatus(1);
            cp.setEmployeeid(employee.getId());
            cp.setRdate(new Date().toString());
            int ok = st.editComplaint(cp);
            if (ok == 1) {
                JOptionPane.showMessageDialog(rootPane, "Complaint updated!");
                initListComplaint(textSearch, slSearch, "ASC");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Cannot update this complaint!");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "please enter more than 4 letters");
            txtComplaint.setBackground(Color.red);
        }
    }//GEN-LAST:event_btncReplyActionPerformed

    private void txtResolveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtResolveFocusGained
        txtResolve.setBackground(Color.white);
    }//GEN-LAST:event_txtResolveFocusGained

    private void tbListComplaintMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListComplaintMouseDragged
        TableColorRender.rows = tbListComplaint.getSelectedRows();
        tbListComplaint.repaint();
        int rows[] = tbListComplaint.getSelectedRows();
        if (rows.length > 1) {
            btnView.setEnabled(false);
        } else {
            btnView.setEnabled(true);
        }
    }//GEN-LAST:event_tbListComplaintMouseDragged

    private void tbListComplaintMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListComplaintMouseMoved
        TableColorRender.rowcolumn = tbListComplaint.rowAtPoint(evt.getPoint());
        tbListComplaint.repaint();
    }//GEN-LAST:event_tbListComplaintMouseMoved

    private void tbListComplaintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListComplaintMouseClicked
        if (evt.getClickCount() == 2) {
            int i = tbListComplaint.getSelectedRow();
            Complaint cp = listComplaint.get(i);
            new ComplaintView(this, true, cp).setVisible(true);
        }
    }//GEN-LAST:event_tbListComplaintMouseClicked

    private void pnListMaintenanceComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnListMaintenanceComponentShown
        totalRows = 0;
        pageNumber = 1;
        totalPage = 1;
        rowsPerPage = 20;
        slSearch = "";
        textSearch = "";
        initListMaintenance(textSearch, slSearch);
        btnEdit.setVisible(false);
        btnPrint.setVisible(false);
        btnView.setVisible(true);
        btnView.setEnabled(false);
        btnAdd.setVisible(false);
        pnMessage.setVisible(false);
        pnResolve.setVisible(false);
        cbSearch.setVisible(false);
        txtSearch.setVisible(false);
        btnSearch.setVisible(false);
        btnDelete.setVisible(true);
        btnDelete.setEnabled(false);
    }//GEN-LAST:event_pnListMaintenanceComponentShown

    private void tbMaintenanceMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMaintenanceMousePressed
        if (evt.getClickCount() == 2) {
            int i = tbMaintenance.getSelectedRow();
            if (i != -1) {
                Maintenance mmt = listMaintenance.get(i);
                mmt.setEmployeeid(employee.getId());
                new MaintenanceView(this, true, mmt).setVisible(true);
            }
        }
        btnView.setEnabled(true);
        btnDelete.setEnabled(true);
        TableColorRender.rows = tbMaintenance.getSelectedRows();
        tbMaintenance.repaint();
    }//GEN-LAST:event_tbMaintenanceMousePressed

    private void tbMaintenanceMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMaintenanceMouseDragged
        TableColorRender.rows = tbMaintenance.getSelectedRows();
        tbMaintenance.repaint();
        int rows[] = tbMaintenance.getSelectedRows();
        if (rows.length > 1) {
            btnView.setEnabled(false);
        } else {
            btnView.setEnabled(true);
        }
    }//GEN-LAST:event_tbMaintenanceMouseDragged

    private void tbMaintenanceMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMaintenanceMouseMoved
        TableColorRender.rowcolumn = tbMaintenance.rowAtPoint(evt.getPoint());
        tbMaintenance.repaint();
    }//GEN-LAST:event_tbMaintenanceMouseMoved

    private void mnNewEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNewEmployeeActionPerformed
        new EmployeeAdd(this, true, null).setVisible(true);
    }//GEN-LAST:event_mnNewEmployeeActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if(pnListElevator.isVisible()){
            int rows[] = tbListElevator.getSelectedRows();
            if (rows.length > 0) {
                if (rows.length > 1) {
                    JOptionPane.showMessageDialog(this, "Please choose only one Elevator!");
                } else{
                    int i = tbListElevator.getSelectedRow();
                    Elevator e = etm.getElevator(i);
                    ElevatorViewEdit eve = new ElevatorViewEdit(this, true, e, 0, false, null);
                    eve.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please choose an Elevator!");
            }
        }
        else if(pnListEmployee.isVisible()){
            int rows[] = tbListEmployee.getSelectedRows();
            if (rows.length > 0) {
                if (rows.length > 1) {
                    JOptionPane.showMessageDialog(this, "Please choose only one Employee!");
                } else{
                    int i = tbListEmployee.getSelectedRow();
                    Employee emm = listEmployee.get(i);
                    new EmployeeAdd(this, true, emm).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please choose an Employee!");
            }
        }
        else if(pnListClient.isVisible()){
            int rows[] = tbListClient.getSelectedRows();
            if (rows.length > 0) {
                if (rows.length > 1) {
                    JOptionPane.showMessageDialog(this, "Please choose only one Client!");
                } else{
                    int i = tbListClient.getSelectedRow();
                    Client cll = listClient.get(i);
                    Dialog d = new Dialog(this);
                    new ClientEdit(d, true, cll).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please choose an Client!");
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void mnAddElevatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAddElevatorActionPerformed
        new ElevatorAdd(this, true).setVisible(true);
    }//GEN-LAST:event_mnAddElevatorActionPerformed

    private void pnListEmployeeComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnListEmployeeComponentShown
        totalRows = 0;
        pageNumber = 1;
        totalPage = 1;
        rowsPerPage = 20;
        slSearch = "";
        textSearch = "";
        initListEmployee(slSearch, slSearch);
        btnAdd.setVisible(true);
        btnPrint.setVisible(false);
        btnView.setVisible(true);
        btnView.setEnabled(false);
        btnEdit.setVisible(true);
        btnEdit.setEnabled(false);
        cbSearch.setVisible(true);
        txtSearch.setVisible(true);
        btnSearch.setVisible(true);
        btnDelete.setVisible(true);
        btnDelete.setEnabled(false);
        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"All", "Username", "Fullname", "Email", "Contact"}));
        txtSearch.setText("All");
    }//GEN-LAST:event_pnListEmployeeComponentShown

    private void tbListEmployeeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListEmployeeMousePressed
        if (evt.getClickCount() == 2) {
            int i = tbListEmployee.getSelectedRow();
            if (i != -1) {
                Employee emm = listEmployee.get(i);
                new EmployeeView(this, true, emm).setVisible(true);
            }
        }
        btnView.setEnabled(true);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        TableColorRender.rows = tbListEmployee.getSelectedRows();
        tbListEmployee.repaint();
    }//GEN-LAST:event_tbListEmployeeMousePressed

    private void tbListEmployeeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListEmployeeMouseDragged
        TableColorRender.rows = tbListEmployee.getSelectedRows();
        tbListEmployee.repaint();
        int rows[] = tbListEmployee.getSelectedRows();
        if (rows.length > 1) {
            btnView.setEnabled(false);
        } else {
            btnView.setEnabled(true);
        }
    }//GEN-LAST:event_tbListEmployeeMouseDragged

    private void tbListEmployeeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListEmployeeMouseMoved
        TableColorRender.rowcolumn = tbListEmployee.rowAtPoint(evt.getPoint());
        tbListEmployee.repaint();
    }//GEN-LAST:event_tbListEmployeeMouseMoved

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(pnListElevator.isVisible()){
            int i = tbListElevator.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose an Elevator");
            } 
            else {
                Elevator rel = listElevator.get(i);
                listElevator.remove(rel);
                if(st.removeElevator(rel) == 1){
                    initListElevator(textSearch, slSearch);
                    JOptionPane.showMessageDialog(rootPane, "Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "failed");
                }
            }
        }
        else if(pnListEmployee.isVisible()){
            int i = tbListEmployee.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose an Employee");
            } 
            else {
                Employee rem = listEmployee.get(i);
                listEmployee.remove(rem);
                if(st.removeEmployee(rem) == 1){
                    initListEmployee(textSearch, slSearch);
                    JOptionPane.showMessageDialog(rootPane, "Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "failed");
                }
            }
        }
        else if(pnListComplaint.isVisible()){
            int i = tbListComplaint.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose a Complaint");
            } 
            else {
                Complaint cm = listComplaint.get(i);
                listComplaint.remove(cm);
                if(st.removeComplaint(cm) == 1){
                    initListComplaint(textSearch, slSearch, "");
                    JOptionPane.showMessageDialog(rootPane, "Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "failed");
                }
            }
        }
        else if(pnListMaintenance.isVisible()){
            int i = tbMaintenance.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose a Maintenance");
            } 
            else {
                Maintenance cm = listMaintenance.get(i);
                listMaintenance.remove(cm);
                if(st.removeMaintenance(cm) == 1){
                    initListMaintenance(textSearch, slSearch);
                    JOptionPane.showMessageDialog(rootPane, "Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "failed");
                }
            }
        }
        else{
            int i = tbListClient.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose a Client");
            } 
            else {
                Client cll = listClient.get(i);
                listClient.remove(cll);
                if(st.removeClient(cll) == 1){
                    initListClient(textSearch, slSearch);
                    JOptionPane.showMessageDialog(rootPane, "Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "failed");
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void pnListClientComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnListClientComponentShown
        totalRows = 0;
        pageNumber = 1;
        totalPage = 1;
        rowsPerPage = 20;
        slSearch = "";
        textSearch = "";
        initListClient(slSearch, slSearch);
        btnAdd.setVisible(true);
        btnPrint.setVisible(false);
        btnView.setVisible(true);
        btnView.setEnabled(false);
        btnEdit.setVisible(true);
        btnEdit.setEnabled(false);
        cbSearch.setVisible(true);
        txtSearch.setVisible(true);
        btnSearch.setVisible(true);
        btnDelete.setVisible(true);
        btnDelete.setEnabled(false);
        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"All", "Name", "Company", "Address", "Email", "Phone"}));
        txtSearch.setText("All");
    }//GEN-LAST:event_pnListClientComponentShown

    private void tbListClientMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListClientMousePressed
        if (evt.getClickCount() == 2) {
            int i = tbListClient.getSelectedRow();
            if (i != -1) {
                Client clie = listClient.get(i);
                new ClientView(this, true, clie).setVisible(true);
            }
        }
        btnView.setEnabled(true);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        TableColorRender.rows = tbListClient.getSelectedRows();
        tbListClient.repaint();
    }//GEN-LAST:event_tbListClientMousePressed

    private void tbListClientMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListClientMouseDragged
        TableColorRender.rows = tbListClient.getSelectedRows();
        tbListClient.repaint();
        int rows[] = tbListClient.getSelectedRows();
        if (rows.length > 1) {
            btnView.setEnabled(false);
            btnEdit.setEnabled(false);
        } else {
            btnView.setEnabled(true);
            btnEdit.setEnabled(true);
        }
    }//GEN-LAST:event_tbListClientMouseDragged

    private void tbListClientMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListClientMouseMoved
        TableColorRender.rowcolumn = tbListClient.rowAtPoint(evt.getPoint());
        tbListClient.repaint();
    }//GEN-LAST:event_tbListClientMouseMoved

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel PagePanel;
    private javax.swing.JPanel ToolbarPanel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btncReply;
    private javax.swing.JButton btncReset;
    private javax.swing.JComboBox cbSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lbImage;
    private javax.swing.JLabel lbcDate;
    private javax.swing.JLabel lblPageOf;
    private javax.swing.JLabel lblTotalRecord;
    private javax.swing.JLabel lbrDate;
    private javax.swing.JPanel leftListElevator;
    private javax.swing.JMenuItem mnAbout;
    private javax.swing.JMenuItem mnAddElevator;
    private javax.swing.JMenuItem mnChangePassword;
    private javax.swing.JMenuItem mnConfig;
    private javax.swing.JMenuItem mnExit;
    private javax.swing.JMenuItem mnLogout;
    private javax.swing.JMenuItem mnNewEmployee;
    private javax.swing.JMenuItem mnUsersGuide;
    private javax.swing.JPanel pnListClient;
    private javax.swing.JPanel pnListComplaint;
    private javax.swing.JPanel pnListElevator;
    private javax.swing.JPanel pnListEmployee;
    private javax.swing.JPanel pnListMaintenance;
    private javax.swing.JPanel pnListOrder;
    private javax.swing.JPanel pnMessage;
    private javax.swing.JPanel pnOrder;
    private javax.swing.JPanel pnResolve;
    private javax.swing.JTabbedPane pnTab;
    private javax.swing.JPanel rightListElevator;
    private javax.swing.JTable tbListClient;
    private javax.swing.JTable tbListComplaint;
    private javax.swing.JTable tbListElevator;
    private javax.swing.JTable tbListElevatorDetail;
    private javax.swing.JTable tbListEmployee;
    private javax.swing.JTable tbListOrder;
    private javax.swing.JTable tbListOrderDetail;
    private javax.swing.JTable tbMaintenance;
    private javax.swing.JTextArea txtComplaint;
    private javax.swing.JTextField txtPageNumber;
    private javax.swing.JTextArea txtResolve;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
