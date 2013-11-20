/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import models.OrderDetailTable;
import models.ElevatorDetailTable;
import models.TableColorRender;
import models.OrderTable;
import models.ElevatorTable;
import Connect.ObjectsData;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import objects.*;
import ultity.*;
/**
 *
 * @author notte_000
 */
public class EmployeeMain extends javax.swing.JFrame {
    ElevatorTable etm;
    ElevatorDetailTable edt;
    OrderTable otm;
    OrderDetailTable odt;
    SQLTools st = new SQLTools();
    List<Elevator> listElevator = new ArrayList<>();
    List<Order> listOrder = new ArrayList<>();
    List<Elevator> listElevatorOrder = new ArrayList<>();
    ObjectsData od = new ObjectsData();
    boolean isSearchFocus;
    Integer totalRows = 0;
    Integer pageNumber = 1;
    Integer totalPage = 1;
    Integer rowsPerPage = 40;
    String slSearch = "";
    String textSearch = "";
    
    /**
     * Creates new form NewJFrame
     * @param select
     */
    @SuppressWarnings("unchecked")
    public EmployeeMain(int select) {
        initComponents();
        this.setLocationRelativeTo(rootPane);
        mnConfig.setEnabled(false);
        CardLayout cl = (CardLayout) MainPanel.getLayout();
        String departName = "";
        switch(select){
            case 1: cl.show(MainPanel, "cardOrder");departName = "Order Department";break;
            case 2: cl.show(MainPanel, "cardService");departName = "Service Department";break;
            case 3: cl.show(MainPanel, "cardComplaint");departName = "Complaint Department";break;
            case 4: cl.show(MainPanel, "cardMaintenance");departName = "Maintenance Department";break;
        }
        this.setVisible(true);
        this.setTitle(departName + " - Employee Manager");
    }
    
    //begin page
    @SuppressWarnings("unchecked")    
    private void initListOrder(String s, String i){
        totalRows = od.getTotalOrder(s, i);
        listOrder.clear();
        listOrder = od.getAllOrder(pageNumber, rowsPerPage, s, i);
        otm = new OrderTable(listOrder);
        tbListOrder.setModel(otm);
        tbListOrder.setDefaultRenderer(Object.class, new TableColorRender());
        //autoResizeColumn(tbListOrder);
        tbListOrder.getColumnModel().getColumn(0).setMinWidth(80);
        tbListOrder.getColumnModel().getColumn(1).setMinWidth(165);
        tbListOrder.getColumnModel().getColumn(2).setMinWidth(220);
        tbListOrder.getColumnModel().getColumn(3).setMinWidth(90);
        tbListOrder.getColumnModel().getColumn(4).setMinWidth(50);
        Paging();
    }
    
    @SuppressWarnings("unchecked")    
    private void initListElevator(String s, String i) {
        totalRows = od.getTotalElevator(s, i);
        listElevator.clear();
        listElevator = od.getAllElevator(pageNumber, rowsPerPage, s, i);
        etm = new ElevatorTable(listElevator);
        tbListElevator.setModel(etm);
        tbListElevator.setDefaultRenderer(Object.class, new TableColorRender());
        //autoResizeColumn(tbListElevator);
        tbListElevator.getColumnModel().getColumn(0).setMinWidth(170);
        tbListElevator.getColumnModel().getColumn(1).setMinWidth(110);
        tbListElevator.getColumnModel().getColumn(2).setMinWidth(90);
        Paging();
    }

    @SuppressWarnings("unchecked")    
    private void Paging(){
        Double dblTotPage = Math.ceil(totalRows.doubleValue()/rowsPerPage.doubleValue());
        totalPage = dblTotPage.intValue();
        if (pageNumber == 1) {
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
        } 
        else {
            btnFirst.setEnabled(true);
            btnPrevious.setEnabled(true);
        }

        if (pageNumber.equals(totalPage)) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
        } 
        else {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        }
        if(totalRows == 0){
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            pageNumber = 0;
        }
        txtPageNumber.setText(String.valueOf(pageNumber));
        lblPageOf.setText(" of " + totalPage + " ");
        lblTotalRecord.setText("Total record " + totalRows + " Elevators.");
    }
    
    @SuppressWarnings("unchecked")    
    private void autoResizeColumn(JTable jTable1) {
        JTableHeader header = jTable1.getTableHeader();
        int rowCount = jTable1.getRowCount();

        final Enumeration columns = jTable1.getColumnModel().getColumns();
        while(columns.hasMoreElements()){
            TableColumn column = (TableColumn)columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int)jTable1.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(jTable1, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
            for(int row = 0; row<rowCount; row++){
                int preferedWidth = (int)jTable1.getCellRenderer(row, col).getTableCellRendererComponent(jTable1, jTable1.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column); // this line is very important
            column.setWidth(width+jTable1.getIntercellSpacing().width);
        }
    }
    //end page
    
    @SuppressWarnings("unchecked")    
    public void viewImage(Elevator e) {
        File fdir = new File(System.getProperty("java.class.path"));
        File dir = fdir.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        File imgcheck = new File(path + "/Images/" + e.getImage());
        ImageIcon icon;
        if(imgcheck.exists()){
            icon = new ImageIcon(path + "/Images/" + e.getImage());
        }
        else{
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
        btnOrder = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        cbSearch = new javax.swing.JComboBox();
        txtSearch = new javax.swing.JTextField();
        MainPanel = new javax.swing.JPanel();
        pnOrder = new javax.swing.JPanel();
        pnTab = new javax.swing.JTabbedPane();
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
        pnService = new javax.swing.JPanel();
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
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnUsersGuide = new javax.swing.JMenuItem();
        mnAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(640, 530));
        setPreferredSize(new java.awt.Dimension(640, 530));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        ToolbarPanel.setMaximumSize(new java.awt.Dimension(32767, 23));
        ToolbarPanel.setMinimumSize(new java.awt.Dimension(0, 23));
        ToolbarPanel.setLayout(new java.awt.BorderLayout());

        jToolBar2.setRollover(true);

        btnOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/order.png"))); // NOI18N
        btnOrder.setToolTipText("Order this");
        btnOrder.setFocusable(false);
        btnOrder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOrder.setMaximumSize(new java.awt.Dimension(32, 32));
        btnOrder.setMinimumSize(new java.awt.Dimension(32, 32));
        btnOrder.setName(""); // NOI18N
        btnOrder.setPreferredSize(new java.awt.Dimension(32, 32));
        btnOrder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });
        jToolBar2.add(btnOrder);

        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view.png"))); // NOI18N
        btnView.setToolTipText("View detail");
        btnView.setFocusable(false);
        btnView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnView.setMaximumSize(new java.awt.Dimension(32, 32));
        btnView.setMinimumSize(new java.awt.Dimension(32, 32));
        btnView.setPreferredSize(new java.awt.Dimension(32, 32));
        btnView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jToolBar2.add(btnView);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh.png"))); // NOI18N
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setMaximumSize(new java.awt.Dimension(32, 32));
        btnRefresh.setMinimumSize(new java.awt.Dimension(32, 32));
        btnRefresh.setPreferredSize(new java.awt.Dimension(32, 32));
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar2.add(btnRefresh);

        ToolbarPanel.add(jToolBar2, java.awt.BorderLayout.WEST);

        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(2147483647, 22));
        jToolBar1.setPreferredSize(new java.awt.Dimension(250, 25));

        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Name", "Type", "Price", "Speed", "Weight", "Warranty", "Made in" }));
        cbSearch.setMaximumSize(new java.awt.Dimension(71, 20));
        cbSearch.setName(""); // NOI18N
        cbSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSearchActionPerformed(evt);
            }
        });
        jToolBar1.add(cbSearch);

        txtSearch.setForeground(new java.awt.Color(204, 204, 204));
        txtSearch.setText("Search");
        txtSearch.setToolTipText("Enter to complete search");
        txtSearch.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        txtSearch.setMinimumSize(new java.awt.Dimension(55, 20));
        txtSearch.setPreferredSize(new java.awt.Dimension(85, 20));
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });
        jToolBar1.add(txtSearch);

        ToolbarPanel.add(jToolBar1, java.awt.BorderLayout.EAST);

        getContentPane().add(ToolbarPanel, java.awt.BorderLayout.NORTH);

        MainPanel.setLayout(new java.awt.CardLayout());

        pnOrder.setLayout(new java.awt.BorderLayout());

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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListElevatorMouseClicked(evt);
            }
        });
        tbListElevator.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbListElevatorMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbListElevatorMouseMoved(evt);
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
            .addComponent(lbImage, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
        );
        rightListElevatorLayout.setVerticalGroup(
            rightListElevatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightListElevatorLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbImage, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(199, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(rightListElevator);

        pnListElevator.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pnTab.addTab("List Elevator", pnListElevator);

        pnListOrder.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnListOrderComponentShown(evt);
            }
        });
        pnListOrder.setLayout(new java.awt.BorderLayout());

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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
        );

        pnListOrder.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pnTab.addTab("List Order", pnListOrder);

        pnOrder.add(pnTab, java.awt.BorderLayout.CENTER);

        MainPanel.add(pnOrder, "cardOrder");

        javax.swing.GroupLayout pnServiceLayout = new javax.swing.GroupLayout(pnService);
        pnService.setLayout(pnServiceLayout);
        pnServiceLayout.setHorizontalGroup(
            pnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );
        pnServiceLayout.setVerticalGroup(
            pnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 599, Short.MAX_VALUE)
        );

        MainPanel.add(pnService, "cardService");

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
                .addGap(0, 484, Short.MAX_VALUE))
        );
        PagePanelLayout.setVerticalGroup(
            PagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PagePanelLayout.createSequentialGroup()
                .addGroup(PagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPageNumber, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPageOf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagePanelLayout.createSequentialGroup()
                        .addGroup(PagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalRecord, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(PagePanel, java.awt.BorderLayout.SOUTH);

        jMenu1.setText("Settings");

        mnLogout.setText("Logout");
        mnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(mnLogout);

        mnConfig.setText("Config");
        jMenu1.add(mnConfig);

        mnExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mnExit.setText("Exit");
        mnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnExit);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Account");

        jMenuItem1.setText("My Infomation");
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Change Password");
        jMenu3.add(jMenuItem2);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Help");

        mnUsersGuide.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mnUsersGuide.setText(" Users Guide");
        mnUsersGuide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUsersGuideActionPerformed(evt);
            }
        });
        jMenu2.add(mnUsersGuide);

        mnAbout.setText(" About");
        jMenu2.add(mnAbout);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        txtSearch.setText("");
        txtSearch.setForeground(new java.awt.Color(0, 0, 0));
        isSearchFocus = true;
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        if(txtSearch.getText().equals("")){
            int i = cbSearch.getSelectedIndex();
            switch(i){
                case 1: txtSearch.setText("Search by Name"); break;
                case 2: txtSearch.setText("Search by Type"); break;
                case 3: txtSearch.setText("Search by Price"); break;
                case 4: txtSearch.setText("Search by Speed"); break;
                case 5: txtSearch.setText("Search by Weight"); break;
                case 6: txtSearch.setText("Search by Warranty"); break;
                case 7: txtSearch.setText("Search by Made in"); break;
                default: txtSearch.setText("Search"); break;
            }
            txtSearch.setForeground(new java.awt.Color(204, 204, 204));
            isSearchFocus = false;
        }
    }//GEN-LAST:event_txtSearchFocusLost

    private void cbSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSearchActionPerformed
        int i = cbSearch.getSelectedIndex();
        if(isSearchFocus == false){
            switch(i){
                case 1: txtSearch.setText("Search by Name"); break;
                case 2: txtSearch.setText("Search by Type"); break;
                case 3: txtSearch.setText("Search by Price"); break;
                case 4: txtSearch.setText("Search by Speed"); break;
                case 5: txtSearch.setText("Search by Weight"); break;
                case 6: txtSearch.setText("Search by Warranty"); break;
                case 7: txtSearch.setText("Search by Made in"); break;
                default: txtSearch.setText("Search"); break;
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
        new MainDialog(this, true, "cardUsersGuide").setVisible(true);
    }//GEN-LAST:event_mnUsersGuideActionPerformed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        int rows[] = tbListElevator.getSelectedRows();
        if(rows.length > 0){
            listElevatorOrder.removeAll(listElevatorOrder);
            for (int i : rows) {
                Elevator el = (Elevator) listElevator.get(i);
                listElevatorOrder.add(el);
            }
            ElevatorOrder oe = new ElevatorOrder(this, true, listElevatorOrder);
            oe.setVisible(true);
        } 
        else {
            JOptionPane.showMessageDialog(this, "Please choose atleast one!");
        }
    }//GEN-LAST:event_btnOrderActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        if(tbListElevator.getSelectedRowCount() == 1){
            int i = tbListElevator.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose an Elevator");
            } else {
                Elevator e = etm.getElevator(i);
                ElevatorViewEdit eve = new ElevatorViewEdit(this, true, e, 1, true);
                eve.setVisible(true);
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Please select one!");
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void tbListElevatorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListElevatorMouseClicked
        if(evt.getClickCount() == 2){
            int i = tbListElevator.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Please choose an Elevator");
            } else {
                Elevator e = etm.getElevator(i);
                ElevatorViewEdit eve = new ElevatorViewEdit(this, true, e, 1, true);
                eve.setVisible(true);
            }
        }
        
    }//GEN-LAST:event_tbListElevatorMouseClicked

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
        int i = tbListElevator.getSelectedRow();
        Elevator e = etm.getElevator(i);
        edt = new ElevatorDetailTable(e);
        tbListElevatorDetail.setModel(edt);
        viewImage(e);
        TableColorRender.rows = tbListElevator.getSelectedRows();
        tbListElevator.repaint();
    }//GEN-LAST:event_tbListElevatorMousePressed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            int select = cbSearch.getSelectedIndex();
            if(pnListElevator.isVisible()){
                switch(select){
                    case 0: slSearch = "ALL"; break;
                    case 1: slSearch = "name"; break;
                    case 2: slSearch = "type"; break;
                    case 3: slSearch = "price"; break;
                    case 4: slSearch = "speed"; break;
                    case 5: slSearch = "weight"; break;
                    case 6: slSearch = "warranty"; break;
                    case 7: slSearch = "madein"; break;
                    default: slSearch = ""; break;
                }
                textSearch = txtSearch.getText().trim();
                initListElevator(textSearch, slSearch);
            }
            else{
                switch(select){
                    case 0: slSearch = "ALL"; break;
                    case 1: slSearch = "name"; break;
                    case 2: slSearch = "price"; break;
                    default: slSearch = ""; break;
                }
                textSearch = txtSearch.getText().trim();
                initListOrder(textSearch, slSearch);
            }
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        textSearch = "";
        slSearch = "";
        pageNumber = 1;
        txtSearch.setText("");
        cbSearch.setSelectedIndex(0);
        isSearchFocus = false;
        if(pnListElevator.isVisible()){
            initListElevator(textSearch,slSearch);
        }
        else{
            initListOrder(textSearch, slSearch);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        pageNumber = 1;
        initListElevator(textSearch,slSearch);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        if (pageNumber > 1) {
            pageNumber -= 1;
            initListElevator(textSearch,slSearch);
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (pageNumber < totalPage) {
            pageNumber += 1;
            initListElevator(textSearch,slSearch);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        pageNumber = totalPage;
        initListElevator(textSearch,slSearch);
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtPageNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPageNumberKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            pageNumber = Integer.parseInt(txtPageNumber.getText());
            initListElevator(textSearch,slSearch);
        }
    }//GEN-LAST:event_txtPageNumberKeyPressed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int dividerLocation = jSplitPane1.getWidth() - (170 + Math.round((jSplitPane1.getWidth() - 170)/10));
        jSplitPane1.setDividerLocation(dividerLocation);
    }//GEN-LAST:event_formComponentResized

    private void pnListOrderComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnListOrderComponentShown
        totalRows = 0;
        pageNumber = 1;
        totalPage = 1;
        rowsPerPage = 40;
        slSearch = "";
        textSearch = "";
        tbListOrder.clearSelection();
        tbListOrder.setRowSelectionInterval(2, 3);
        initListOrder(textSearch,slSearch);
        TableColorRender.clear = true;
        tbListOrder.revalidate();
        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Name", "Price" }));
    }//GEN-LAST:event_pnListOrderComponentShown

    private void pnListElevatorComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnListElevatorComponentShown
        totalRows = 0;
        pageNumber = 1;
        totalPage = 1;
        rowsPerPage = 40;
        slSearch = "";
        textSearch = "";
        Elevator e = new Elevator();
        tbListElevator.clearSelection();
        initListElevator(textSearch,slSearch);
        TableColorRender.clear = true;
        tbListElevator.revalidate();
        viewImage(e);
        cbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Name", "Type", "Price", "Speed", "Weight", "Warranty", "Made in" }));
    }//GEN-LAST:event_pnListElevatorComponentShown

    private void tbListElevatorMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListElevatorMouseMoved
        TableColorRender.rowcolumn = tbListElevator.rowAtPoint(evt.getPoint());
        tbListElevator.repaint();
    }//GEN-LAST:event_tbListElevatorMouseMoved

    private void tbListElevatorMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListElevatorMouseDragged
        TableColorRender.rows = tbListElevator.getSelectedRows();
        tbListElevator.repaint();
    }//GEN-LAST:event_tbListElevatorMouseDragged

    private void tbListOrderMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListOrderMouseMoved
        TableColorRender.rowcolumn = tbListOrder.rowAtPoint(evt.getPoint());
        tbListOrder.repaint();
    }//GEN-LAST:event_tbListOrderMouseMoved

    private void tbListOrderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListOrderMouseDragged
        TableColorRender.rows = tbListOrder.getSelectedRows();
        tbListOrder.repaint();
    }//GEN-LAST:event_tbListOrderMouseDragged

    private void tbListOrderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListOrderMousePressed
        int row = tbListOrder.getSelectedRow();
        Order o = listOrder.get(row);
        listElevatorOrder.clear();
        listElevatorOrder = od.getOrderDetail(o.getId());
        odt = new OrderDetailTable(listElevatorOrder);
        tbListOrderDetail.setModel(odt);
        TableColorRender.rows = tbListOrder.getSelectedRows();
        tbListOrder.repaint();
    }//GEN-LAST:event_tbListOrderMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmployeeMain(1).setVisible(true);
            }
        });
    }    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel PagePanel;
    private javax.swing.JPanel ToolbarPanel;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox cbSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lbImage;
    private javax.swing.JLabel lblPageOf;
    private javax.swing.JLabel lblTotalRecord;
    private javax.swing.JPanel leftListElevator;
    private javax.swing.JMenuItem mnAbout;
    private javax.swing.JMenuItem mnConfig;
    private javax.swing.JMenuItem mnExit;
    private javax.swing.JMenuItem mnLogout;
    private javax.swing.JMenuItem mnUsersGuide;
    private javax.swing.JPanel pnListElevator;
    private javax.swing.JPanel pnListOrder;
    private javax.swing.JPanel pnOrder;
    private javax.swing.JPanel pnService;
    private javax.swing.JTabbedPane pnTab;
    private javax.swing.JPanel rightListElevator;
    private javax.swing.JTable tbListElevator;
    private javax.swing.JTable tbListElevatorDetail;
    private javax.swing.JTable tbListOrder;
    private javax.swing.JTable tbListOrderDetail;
    private javax.swing.JTextField txtPageNumber;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
