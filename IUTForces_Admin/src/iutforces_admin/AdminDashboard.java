/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import newproblem.NewProblem;
import newsubmission.NewSubmission;
/**
 * This is the interface class of the dashboard of the Admin's interface.
 * @author Rifat
 * @author Ishrak
 */
public class AdminDashboard extends javax.swing.JFrame
{
    /**
     * Creates new form AdminDashboard
     */
    private final AdminSocket adminSocket;
    private File problem;
    private File inputs;
    private File outputs;
    AdminLogin parent;
    public AdminDashboard(AdminSocket adminSocket,AdminLogin parent)
    {
        initComponents();
        this.adminSocket=adminSocket;
        this.parent=parent;
        this.setVisible(true);
        
        
        ProblemsetTable.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) 
                {
                    evt.consume();
                    int row = ProblemsetTable.rowAtPoint(evt.getPoint());
                    int col = ProblemsetTable.columnAtPoint(evt.getPoint());

                    if (row >= 0 && (col == 0 || col == 1)) 
                    {
                        DefaultTableModel tablemodel = (DefaultTableModel) ProblemsetTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) 
                        {
                            String temp = tablemodel.getValueAt(row, 0).toString();
                            int x = temp.indexOf('<', 28);
                            System.out.println(temp + '\n' + x);
                            String problemID = temp.substring(28, x);

                            adminSocket.sendData("ProbFile[" + problemID + "]");
                            NewProblem problem = adminSocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemID + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }
                            ProblemDisplay problemDisplay = new ProblemDisplay(problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemDisplay.viewPdf(new File(problemID + ".pdf"));
                        }
                    }
                }
            }
        });
        
        
        
        StatusTable.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) 
                {
                    evt.consume();
                    int row = StatusTable.rowAtPoint(evt.getPoint());
                    int col = StatusTable.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col == 0)
                    {
                        DefaultTableModel tablemodel = (DefaultTableModel) StatusTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null)
                        {
                            SubmissionDisplay submissionDisplay = new SubmissionDisplay(adminSocket);
                            String temp = tablemodel.getValueAt(row, 0).toString();
                            System.out.println(temp);
                            int x = temp.indexOf('<', 28);
                            String submissionid = temp.substring(28, x);
                            submissionDisplay.setSubDetailsTable(submissionid, tablemodel.getValueAt(row, 2), tablemodel.getValueAt(row, 3), tablemodel.getValueAt(row, 4), tablemodel.getValueAt(row, 5), tablemodel.getValueAt(row, 6), tablemodel.getValueAt(row, 1));

                            adminSocket.sendData("SrcCode-[" + submissionid + "]");
                            NewSubmission submission = adminSocket.getSubmission();
                            submissionDisplay.setSourceCOde(submission);
                        }
                    } 
                    else if (row >= 0 && col == 3) 
                    {
                        DefaultTableModel tablemodel = (DefaultTableModel) StatusTable.getModel();
                        if (tablemodel.getValueAt(row, 3) != null) 
                        {
                            String temp = tablemodel.getValueAt(row, 3).toString();
                            int x = temp.indexOf('-', 28);
                            String problemID = temp.substring(28, x);

                            adminSocket.sendData("ProbFile[" + problemID + "]");
                            NewProblem problem = adminSocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemID + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }

                            ProblemDisplay problemDisplay = new ProblemDisplay(problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemDisplay.viewPdf(new File(problemID + ".pdf"));
                        }
                    }
                }
            }
        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        AdminDashboardDesktopPane = new javax.swing.JDesktopPane();
        AdminDashboardTabSwitcher = new javax.swing.JTabbedPane();
        HomePanel = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        LogOutButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ProblemsetPanel = new javax.swing.JPanel();
        ProblemSetjScrollPane = new javax.swing.JScrollPane();
        ProblemsetTable = new javax.swing.JTable();
        MyProblemsPanel = new javax.swing.JPanel();
        MyProblemsjScrollPane = new javax.swing.JScrollPane();
        MyProblemsTable = new javax.swing.JTable();
        ManagePanel = new javax.swing.JPanel();
        ManagePanelTabSwitcher = new javax.swing.JTabbedPane();
        AddProblemPanel = new javax.swing.JPanel();
        AddOutputButton = new javax.swing.JButton();
        ChProblemStatementButton = new javax.swing.JButton();
        AddInputButton = new javax.swing.JButton();
        MemoryLimitLabel = new javax.swing.JLabel();
        ProblemNameLabel = new javax.swing.JLabel();
        TimeLimitLabel = new javax.swing.JLabel();
        txtProblemName = new javax.swing.JTextField();
        SubmitButton = new javax.swing.JButton();
        txtTimeLimit = new javax.swing.JFormattedTextField();
        txtMemoryLimit = new javax.swing.JFormattedTextField();
        DeleteProblemPanel = new javax.swing.JPanel();
        DelProblemSetjScrollPane = new javax.swing.JScrollPane();
        DelProblemsetTable = new javax.swing.JTable();
        StatusPanel = new javax.swing.JPanel();
        StatusScrollPane = new javax.swing.JScrollPane();
        StatusTable = new javax.swing.JTable();
        StandingsPanel = new javax.swing.JPanel();
        StandingsScrollPane = new javax.swing.JScrollPane();
        StandingsTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        AdminDashboardDesktopPane.setBackground(new java.awt.Color(0, 0, 0));

        AdminDashboardTabSwitcher.setBackground(new java.awt.Color(204, 204, 204));
        AdminDashboardTabSwitcher.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AdminDashboardTabSwitcher.setForeground(new java.awt.Color(0, 204, 0));
        AdminDashboardTabSwitcher.setToolTipText("");
        AdminDashboardTabSwitcher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AdminDashboardTabSwitcher.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        AdminDashboardTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                AdminDashboardTabSwitcherMouseClicked(evt);
            }
        });

        HomePanel.setBackground(new java.awt.Color(255, 255, 255));

        WelcomeLabel.setFont(new java.awt.Font("Product Sans", 3, 36)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(102, 255, 51));
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("Welcome To IUTForces");

        LogOutButton.setBackground(new java.awt.Color(102, 255, 102));
        LogOutButton.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        LogOutButton.setForeground(new java.awt.Color(102, 255, 102));
        LogOutButton.setText("Log Out");
        LogOutButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 181, 204)));
        LogOutButton.setContentAreaFilled(false);
        LogOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LogOutButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                LogOutButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("- Problemset: See the list of all the problem statements");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("- My Problems: See the list of all the problem you uploaded");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("- Manage: Add/Delete problems");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("- Status: See the submission status of all the students");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("- Standings: See the Rank-list of all the students");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("- Clarifications: Respond to any queries from the students");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("- Tutorial: Post the solutions to the problems after the Lab class ends.");

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(WelcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomePanelLayout.createSequentialGroup()
                .addContainerGap(469, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(469, Short.MAX_VALUE))
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGap(314, 314, 314)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(WelcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        AdminDashboardTabSwitcher.addTab("Home", HomePanel);

        ProblemSetjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        ProblemSetjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        ProblemsetTable.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        ProblemsetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String []
            {
                "Problem ID", "Problem Title", "Problem Author"
            }
        ));
        ProblemsetTable.setFocusable(false);
        ProblemsetTable.setGridColor(new java.awt.Color(255, 255, 255));
        ProblemsetTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        ProblemsetTable.setMinimumSize(new java.awt.Dimension(0, 0));
        ProblemsetTable.setOpaque(false);
        ProblemsetTable.setRequestFocusEnabled(false);
        ProblemsetTable.setRowHeight(25);
        ProblemsetTable.setRowSelectionAllowed(false);
        ProblemsetTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        ProblemsetTable.setShowHorizontalLines(false);
        ProblemsetTable.getTableHeader().setReorderingAllowed(false);
        ProblemsetTable.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                ProblemsetTableComponentResized(evt);
            }
        });
        ProblemSetjScrollPane.setViewportView(ProblemsetTable);

        javax.swing.GroupLayout ProblemsetPanelLayout = new javax.swing.GroupLayout(ProblemsetPanel);
        ProblemsetPanel.setLayout(ProblemsetPanelLayout);
        ProblemsetPanelLayout.setHorizontalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        ProblemsetPanelLayout.setVerticalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        AdminDashboardTabSwitcher.addTab("Problemset", ProblemsetPanel);

        MyProblemsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        MyProblemsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        MyProblemsTable.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        MyProblemsTable.setForeground(new java.awt.Color(0, 0, 51));
        MyProblemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String []
            {
                "Problem ID", "Problem Name", "Problem Author"
            }
        ));
        MyProblemsTable.setFocusable(false);
        MyProblemsTable.setGridColor(new java.awt.Color(255, 255, 255));
        MyProblemsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MyProblemsTable.setMinimumSize(new java.awt.Dimension(0, 0));
        MyProblemsTable.setOpaque(false);
        MyProblemsTable.setRequestFocusEnabled(false);
        MyProblemsTable.setRowHeight(25);
        MyProblemsTable.setRowSelectionAllowed(false);
        MyProblemsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        MyProblemsTable.setShowHorizontalLines(false);
        MyProblemsTable.getTableHeader().setReorderingAllowed(false);
        MyProblemsTable.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                MyProblemsTableComponentResized(evt);
            }
        });
        MyProblemsjScrollPane.setViewportView(MyProblemsTable);

        javax.swing.GroupLayout MyProblemsPanelLayout = new javax.swing.GroupLayout(MyProblemsPanel);
        MyProblemsPanel.setLayout(MyProblemsPanelLayout);
        MyProblemsPanelLayout.setHorizontalGroup(
            MyProblemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyProblemsPanelLayout.createSequentialGroup()
                .addComponent(MyProblemsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        MyProblemsPanelLayout.setVerticalGroup(
            MyProblemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyProblemsPanelLayout.createSequentialGroup()
                .addComponent(MyProblemsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        AdminDashboardTabSwitcher.addTab("My Problems", MyProblemsPanel);

        ManagePanel.setBackground(new java.awt.Color(255, 255, 255));
        ManagePanel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N

        ManagePanelTabSwitcher.setForeground(new java.awt.Color(102, 102, 102));
        ManagePanelTabSwitcher.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        ManagePanelTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                ManagePanelTabSwitcherMouseClicked(evt);
            }
        });

        AddProblemPanel.setBackground(new java.awt.Color(255, 255, 255));
        AddProblemPanel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        AddOutputButton.setFont(new java.awt.Font("Product Sans", 0, 16)); // NOI18N
        AddOutputButton.setText("Add Output (*.txt)");
        AddOutputButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                AddOutputButtonActionPerformed(evt);
            }
        });

        ChProblemStatementButton.setFont(new java.awt.Font("Product Sans", 1, 16)); // NOI18N
        ChProblemStatementButton.setText("Choose Problem Statement (*.pdf,*.txt)");
        ChProblemStatementButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ChProblemStatementButtonActionPerformed(evt);
            }
        });

        AddInputButton.setFont(new java.awt.Font("Product Sans", 0, 16)); // NOI18N
        AddInputButton.setText("Add Input (*.txt)");
        AddInputButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                AddInputButtonActionPerformed(evt);
            }
        });

        MemoryLimitLabel.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        MemoryLimitLabel.setForeground(new java.awt.Color(255, 51, 51));
        MemoryLimitLabel.setText("Memory Limit (KB):");

        ProblemNameLabel.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        ProblemNameLabel.setForeground(new java.awt.Color(255, 51, 51));
        ProblemNameLabel.setText("Problem Name:");

        TimeLimitLabel.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        TimeLimitLabel.setForeground(new java.awt.Color(255, 51, 51));
        TimeLimitLabel.setText("Time Limit (ms):");

        txtProblemName.setFont(new java.awt.Font("Product Sans", 0, 14)); // NOI18N

        SubmitButton.setBackground(new java.awt.Color(255, 51, 51));
        SubmitButton.setFont(new java.awt.Font("Product Sans", 1, 24)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(255, 51, 102));
        SubmitButton.setText("Submit");
        SubmitButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 181, 204)));
        SubmitButton.setContentAreaFilled(false);
        SubmitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SubmitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SubmitButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                SubmitButtonActionPerformed(evt);
            }
        });

        txtTimeLimit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtTimeLimit.setFont(new java.awt.Font("Product Sans", 0, 14)); // NOI18N

        txtMemoryLimit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtMemoryLimit.setFont(new java.awt.Font("Product Sans", 0, 14)); // NOI18N

        javax.swing.GroupLayout AddProblemPanelLayout = new javax.swing.GroupLayout(AddProblemPanel);
        AddProblemPanel.setLayout(AddProblemPanelLayout);
        AddProblemPanelLayout.setHorizontalGroup(
            AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddProblemPanelLayout.createSequentialGroup()
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AddProblemPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddProblemPanelLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddProblemPanelLayout.createSequentialGroup()
                                .addComponent(ProblemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtProblemName, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))
                            .addGroup(AddProblemPanelLayout.createSequentialGroup()
                                .addComponent(TimeLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimeLimit))
                            .addGroup(AddProblemPanelLayout.createSequentialGroup()
                                .addComponent(MemoryLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMemoryLimit))
                            .addGroup(AddProblemPanelLayout.createSequentialGroup()
                                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(AddOutputButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AddInputButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ChProblemStatementButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(367, 367, 367))
        );
        AddProblemPanelLayout.setVerticalGroup(
            AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddProblemPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(ChProblemStatementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(AddInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(AddOutputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProblemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProblemName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TimeLimitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtTimeLimit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MemoryLimitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtMemoryLimit))
                .addGap(18, 18, 18)
                .addComponent(SubmitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );

        ManagePanelTabSwitcher.addTab("Add Problem", AddProblemPanel);

        DelProblemSetjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        DelProblemSetjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        DelProblemsetTable.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        DelProblemsetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Problem ID", "Problem Name", "Problem Author", ""
            }
        ));
        DelProblemsetTable.setFocusable(false);
        DelProblemsetTable.setGridColor(new java.awt.Color(255, 255, 255));
        DelProblemsetTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        DelProblemsetTable.setOpaque(false);
        DelProblemsetTable.setRequestFocusEnabled(false);
        DelProblemsetTable.setRowHeight(25);
        DelProblemsetTable.setRowSelectionAllowed(false);
        DelProblemsetTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        DelProblemsetTable.setShowHorizontalLines(false);
        DelProblemsetTable.getTableHeader().setReorderingAllowed(false);
        DelProblemsetTable.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                DelProblemsetTableComponentResized(evt);
            }
        });
        DelProblemSetjScrollPane.setViewportView(DelProblemsetTable);

        javax.swing.GroupLayout DeleteProblemPanelLayout = new javax.swing.GroupLayout(DeleteProblemPanel);
        DeleteProblemPanel.setLayout(DeleteProblemPanelLayout);
        DeleteProblemPanelLayout.setHorizontalGroup(
            DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1023, Short.MAX_VALUE)
            .addGroup(DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelProblemSetjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE))
        );
        DeleteProblemPanelLayout.setVerticalGroup(
            DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
            .addGroup(DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelProblemSetjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
        );

        ManagePanelTabSwitcher.addTab("Delete Problem", DeleteProblemPanel);

        javax.swing.GroupLayout ManagePanelLayout = new javax.swing.GroupLayout(ManagePanel);
        ManagePanel.setLayout(ManagePanelLayout);
        ManagePanelLayout.setHorizontalGroup(
            ManagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagePanelLayout.createSequentialGroup()
                .addComponent(ManagePanelTabSwitcher)
                .addGap(0, 0, 0))
        );
        ManagePanelLayout.setVerticalGroup(
            ManagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagePanelLayout.createSequentialGroup()
                .addComponent(ManagePanelTabSwitcher)
                .addGap(0, 0, 0))
        );

        AdminDashboardTabSwitcher.addTab("Manage", ManagePanel);

        StatusScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StatusTable.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        StatusTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String []
            {
                "#", "When", "Who", "Problem", "Language", "Verdict", "Time"
            }
        ));
        StatusTable.setFocusable(false);
        StatusTable.setGridColor(new java.awt.Color(255, 255, 255));
        StatusTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        StatusTable.setOpaque(false);
        StatusTable.setRequestFocusEnabled(false);
        StatusTable.setRowHeight(25);
        StatusTable.setRowSelectionAllowed(false);
        StatusTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        StatusTable.setShowHorizontalLines(false);
        StatusTable.getTableHeader().setReorderingAllowed(false);
        StatusTable.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                StatusTableComponentResized(evt);
            }
        });
        StatusScrollPane.setViewportView(StatusTable);

        javax.swing.GroupLayout StatusPanelLayout = new javax.swing.GroupLayout(StatusPanel);
        StatusPanel.setLayout(StatusPanelLayout);
        StatusPanelLayout.setHorizontalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        StatusPanelLayout.setVerticalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );

        AdminDashboardTabSwitcher.addTab("Status", StatusPanel);

        StandingsScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StandingsTable.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        StandingsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String []
            {
                "#", "Student ID", "Problems Solved"
            }
        ));
        StandingsTable.setFocusable(false);
        StandingsTable.setGridColor(new java.awt.Color(255, 255, 255));
        StandingsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        StandingsTable.setOpaque(false);
        StandingsTable.setRequestFocusEnabled(false);
        StandingsTable.setRowHeight(25);
        StandingsTable.setRowSelectionAllowed(false);
        StandingsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        StandingsTable.setShowHorizontalLines(false);
        StandingsTable.getTableHeader().setReorderingAllowed(false);
        StandingsTable.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                StandingsTableComponentResized(evt);
            }
        });
        StandingsScrollPane.setViewportView(StandingsTable);

        javax.swing.GroupLayout StandingsPanelLayout = new javax.swing.GroupLayout(StandingsPanel);
        StandingsPanel.setLayout(StandingsPanelLayout);
        StandingsPanelLayout.setHorizontalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StandingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        StandingsPanelLayout.setVerticalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StandingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );

        AdminDashboardTabSwitcher.addTab("Standings", StandingsPanel);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jButton1.setText("Send");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 916, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        AdminDashboardTabSwitcher.addTab("Clarifications", jPanel1);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Product Sans", 0, 16)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jComboBox1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        jLabel1.setText("Problem ID: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        AdminDashboardTabSwitcher.addTab("Tutorial", jPanel2);

        AdminDashboardDesktopPane.setLayer(AdminDashboardTabSwitcher, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout AdminDashboardDesktopPaneLayout = new javax.swing.GroupLayout(AdminDashboardDesktopPane);
        AdminDashboardDesktopPane.setLayout(AdminDashboardDesktopPaneLayout);
        AdminDashboardDesktopPaneLayout.setHorizontalGroup(
            AdminDashboardDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardDesktopPaneLayout.createSequentialGroup()
                .addComponent(AdminDashboardTabSwitcher)
                .addGap(0, 0, 0))
        );
        AdminDashboardDesktopPaneLayout.setVerticalGroup(
            AdminDashboardDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardDesktopPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(AdminDashboardTabSwitcher)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1037, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(AdminDashboardDesktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(AdminDashboardDesktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_LogOutButtonActionPerformed
    {//GEN-HEADEREND:event_LogOutButtonActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void ProblemsetTableComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_ProblemsetTableComponentResized
    {//GEN-HEADEREND:event_ProblemsetTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_ProblemsetTableComponentResized

    private void MyProblemsTableComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_MyProblemsTableComponentResized
    {//GEN-HEADEREND:event_MyProblemsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_MyProblemsTableComponentResized

    private void AddOutputButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_AddOutputButtonActionPerformed
    {//GEN-HEADEREND:event_AddOutputButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("Text Document", "txt"));
        filemanager.showOpenDialog(this);
        outputs = filemanager.getSelectedFile();
        if (outputs != null)
        {
            String extension = outputs.getName();
            int x = extension.lastIndexOf('.');
            if (x > 0)
            {
                extension = extension.substring(x);
            }

            if (extension.equals(".txt"))
            {
                AddOutputButton.setText(outputs.getName());
            } else
            {
                JOptionPane.showMessageDialog(null, "Select .txt file", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else
        {
            JOptionPane.showMessageDialog(null, "Output file missing!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AddOutputButtonActionPerformed

    private void ChProblemStatementButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ChProblemStatementButtonActionPerformed
    {//GEN-HEADEREND:event_ChProblemStatementButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        filemanager.showOpenDialog(this);
        problem = filemanager.getSelectedFile();
        if (problem != null)
        {

            String extension = problem.getName();
            int x = extension.lastIndexOf('.');
            if (x > 0)
            {
                extension = extension.substring(x);
            }

            if (extension.equals(".pdf") || extension.equals(".PDF"))
            {
                ChProblemStatementButton.setText(problem.getName());
            } else
            {
                JOptionPane.showMessageDialog(null, "Select .pdf file", "Error", JOptionPane.ERROR_MESSAGE);
                problem = null;
            }

        } else
        {
            JOptionPane.showMessageDialog(null, "Problem file missing!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ChProblemStatementButtonActionPerformed

    private void AddInputButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_AddInputButtonActionPerformed
    {//GEN-HEADEREND:event_AddInputButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("Text Documents", "txt"));
        filemanager.showOpenDialog(this);
        inputs = filemanager.getSelectedFile();

        if (inputs != null)
        {
            String extension = inputs.getName();
            int x = extension.lastIndexOf('.');
            if (x > 0)
            {
                extension = extension.substring(x);
            }

            if (extension.equals(".txt"))
            {
                AddInputButton.setText(inputs.getName());
            } else
            {
                JOptionPane.showMessageDialog(null, "Select .txt file", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else
        {
            JOptionPane.showMessageDialog(null, "Input files missing!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AddInputButtonActionPerformed

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SubmitButtonActionPerformed
    {//GEN-HEADEREND:event_SubmitButtonActionPerformed

        String problemname = txtProblemName.getText();
        String timelimit = txtTimeLimit.getText();
        String memorylimit = txtMemoryLimit.getText();
        if (problemname.length() <= 0 || timelimit.length() <= 0 || memorylimit.length() <= 0)
        {
            JOptionPane.showMessageDialog(null, "Please fill Problem Name, Timelimit, Memorylimit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Integer.parseInt(timelimit) < 0 || Integer.parseInt(memorylimit) < 0)
        {
            JOptionPane.showMessageDialog(null, "Timelimit or Memorylimit cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (inputs == null || problem == null || outputs == null)
        {
            JOptionPane.showMessageDialog(null, "Missing Files", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            adminSocket.sendData("AddProb-[" + problem.getName() + "][" + inputs.getName() + "][" + outputs.getName() + "]");
            if (adminSocket.addProblem(problem, inputs, outputs, "null", problemname, timelimit, memorylimit) > 0)
            {
                JOptionPane.showMessageDialog(null, "Problem file Sent", "Status", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Problem Submission failed!\nCheck Connection!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SubmitButtonActionPerformed

    private void DelProblemsetTableComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_DelProblemsetTableComponentResized
    {//GEN-HEADEREND:event_DelProblemsetTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_DelProblemsetTableComponentResized

    private void ManagePanelTabSwitcherMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ManagePanelTabSwitcherMouseClicked
    {//GEN-HEADEREND:event_ManagePanelTabSwitcherMouseClicked
        int x = ManagePanelTabSwitcher.getSelectedIndex();
        if (x == 1)
        {
            Object[][] table;
            adminSocket.sendData("PrbTable[MyDel]");
            table = adminSocket.getProblemTable();
            if (table == null)
            {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } 
            else
            {
                String[] columns = {"Problem ID", "Problem Name", "Problem Author", " "};
                DefaultTableModel tablemodel = new DefaultTableModel(table, columns)
                {
                    public boolean isCellEditable(int row, int col)
                    {
                        return false;
                    }
                };
                DelProblemsetTable.setModel(tablemodel);
            }
        }
    }//GEN-LAST:event_ManagePanelTabSwitcherMouseClicked

    private void StatusTableComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_StatusTableComponentResized
    {//GEN-HEADEREND:event_StatusTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusTableComponentResized

    private void StandingsTableComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_StandingsTableComponentResized
    {//GEN-HEADEREND:event_StandingsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_StandingsTableComponentResized

    private void AdminDashboardTabSwitcherMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_AdminDashboardTabSwitcherMouseClicked
    {//GEN-HEADEREND:event_AdminDashboardTabSwitcherMouseClicked
        int x = AdminDashboardTabSwitcher.getSelectedIndex();
        switch (x)
        {

            case 1:

            adminSocket.sendData("PrbTable[null]");
            Object[][] table = adminSocket.getProblemTable();
            if (table == null)
            {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } else
            {
                String[] columns = {"Problem ID", "Problem Name", "Problem Author"};
                DefaultTableModel tablemodel = new DefaultTableModel(table, columns)
                {

                    public boolean isCellEditable(int row, int col)
                    {
                        return false;
                    }
                };
                ProblemsetTable.setModel(tablemodel);
            }

            break;
            case 2:
            adminSocket.sendData("PrbTable[My]");
            table = adminSocket.getProblemTable();
            if (table == null)
            {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } 
            else
            {
                String[] columns = {"Problem ID", "Problem Name", "Problem Author"};
                DefaultTableModel tablemodel = new DefaultTableModel(table, columns)
                {
                    public boolean isCellEditable(int row, int col)
                    {
                        return false;
                    }
                };
                MyProblemsTable.setModel(tablemodel);
            }
            break;
            case 4:
            adminSocket.sendData("StTable-[nullad]");
            table = adminSocket.getStatusTable();
            if (table == null)
            {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } 
            else
            {
                String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
                DefaultTableModel tablemodel = new DefaultTableModel(table, columns)
                {
                    public boolean isCellEditable(int row, int col)
                    {
                        return false;
                    }
                };

                StatusTable.setModel(tablemodel);
            }
            break;
            case 5:
            adminSocket.sendData("StdTable[null]");
            table = adminSocket.getStandingsTable();
            if (table == null)
            {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } 
            else
            {
                String[] columns = {"#", "ID", "Problems Solved"};
                DefaultTableModel tablemodel = new DefaultTableModel(table, columns)
                {
                    public boolean isCellEditable(int row, int col)
                    {
                        return false;
                    }
                };

                StandingsTable.setModel(tablemodel);
            }
            break;
            default:
            break;

        }
    }//GEN-LAST:event_AdminDashboardTabSwitcherMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextField1ActionPerformed
    {//GEN-HEADEREND:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBox1ActionPerformed
    {//GEN-HEADEREND:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddInputButton;
    private javax.swing.JButton AddOutputButton;
    private javax.swing.JPanel AddProblemPanel;
    private javax.swing.JDesktopPane AdminDashboardDesktopPane;
    private javax.swing.JTabbedPane AdminDashboardTabSwitcher;
    private javax.swing.JButton ChProblemStatementButton;
    private javax.swing.JScrollPane DelProblemSetjScrollPane;
    private javax.swing.JTable DelProblemsetTable;
    private javax.swing.JPanel DeleteProblemPanel;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel ManagePanel;
    private javax.swing.JTabbedPane ManagePanelTabSwitcher;
    private javax.swing.JLabel MemoryLimitLabel;
    private javax.swing.JPanel MyProblemsPanel;
    private javax.swing.JTable MyProblemsTable;
    private javax.swing.JScrollPane MyProblemsjScrollPane;
    private javax.swing.JLabel ProblemNameLabel;
    private javax.swing.JScrollPane ProblemSetjScrollPane;
    private javax.swing.JPanel ProblemsetPanel;
    private javax.swing.JTable ProblemsetTable;
    private javax.swing.JPanel StandingsPanel;
    private javax.swing.JScrollPane StandingsScrollPane;
    private javax.swing.JTable StandingsTable;
    private javax.swing.JPanel StatusPanel;
    private javax.swing.JScrollPane StatusScrollPane;
    private javax.swing.JTable StatusTable;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JLabel TimeLimitLabel;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JFormattedTextField txtMemoryLimit;
    private javax.swing.JTextField txtProblemName;
    private javax.swing.JFormattedTextField txtTimeLimit;
    // End of variables declaration//GEN-END:variables
}
