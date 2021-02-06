/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_user;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
 * This is the interface class of the dashboard of the User's interface.
 * @author Rifat
 * @author Ishrak
 */
public class UserDashboard extends javax.swing.JFrame
{
    /**
     * Creates new form UserDashboard
     */
    private final UserSocket userSocket;
    private File codeFile;
    private UserDashboard temp;
    private UserLogin userLogin;

    public UserDashboard(UserSocket userSocket, UserLogin userLogin)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        initComponents();
        this.userSocket=userSocket;
        this.codeFile=null;
        temp=this;
        this.userLogin=userLogin;
        
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
                            String tempo = tablemodel.getValueAt(row, 0).toString();
                            int x = tempo.indexOf('<', 28);
                            String problemid = tempo.substring(28, x);

                            userSocket.sendData("ProbFile[" + problemid + "]");
                            NewProblem problem = userSocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemid + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }

                            ProblemDisplay problemDisplay = new ProblemDisplay(temp, problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemDisplay.viewPdf(new File(problemid + ".pdf"), problemid);
                        }

                    }
                }
            }
        });
        
        MySubTable.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) 
                {
                    evt.consume();
                    int row = MySubTable.rowAtPoint(evt.getPoint());
                    int col = MySubTable.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col == 0) 
                    {
                        DefaultTableModel tablemodel = (DefaultTableModel) MySubTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) {
                            SubmissionDisplay submissionDisplay = new SubmissionDisplay(userSocket, temp);
                            String tempo = tablemodel.getValueAt(row, 0).toString();
                            int x = tempo.indexOf('<', 28);
                            String submissionid = tempo.substring(28, x);
                            submissionDisplay.setSubDetailsTable(submissionid, tablemodel.getValueAt(row, 2), tablemodel.getValueAt(row, 3), tablemodel.getValueAt(row, 4), tablemodel.getValueAt(row, 5), tablemodel.getValueAt(row, 6), tablemodel.getValueAt(row, 1));
                            userSocket.sendData("SrcCode-[" + submissionid + "]");
                            NewSubmission submission = userSocket.getSubmission();
                            submissionDisplay.setSourceCode(submission);
                        }
                    } 
                    else if (row >= 0 && col == 3)
                    {
                        DefaultTableModel tablemodel = (DefaultTableModel) MySubTable.getModel();
                        if (tablemodel.getValueAt(row, 3) != null) 
                        {
                            String tempo = tablemodel.getValueAt(row, 3).toString();
                            int x = tempo.indexOf('-', 28);
                            String problemid = tempo.substring(28, x);
                            userSocket.sendData("ProbFile[" + problemid + "]");
                            NewProblem problem = userSocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemid + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }
                            ProblemDisplay problemDisplay = new ProblemDisplay(temp, problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemDisplay.viewPdf(new File(problemid + ".pdf"), problemid);
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
        java.awt.GridBagConstraints gridBagConstraints;

        jDesktopPane1 = new javax.swing.JDesktopPane();
        UserDashboardTabSwitcher = new javax.swing.JTabbedPane();
        HomePanel = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        LogOutButton = new javax.swing.JButton();
        ProblemsetPanel = new javax.swing.JPanel();
        ProblemSetjScrollPane = new javax.swing.JScrollPane();
        ProblemsetTable = new javax.swing.JTable();
        StatusPanel = new javax.swing.JPanel();
        StatusScrollPane = new javax.swing.JScrollPane();
        StatusTable = new javax.swing.JTable();
        MySubPanel = new javax.swing.JPanel();
        MySubScrollPane = new javax.swing.JScrollPane();
        MySubTable = new javax.swing.JTable();
        StandingsPanel = new javax.swing.JPanel();
        StandingsScrollPane = new javax.swing.JScrollPane();
        StandingsTable = new javax.swing.JTable();
        SubmitSolPanel = new javax.swing.JPanel();
        ChooseFileLabel = new javax.swing.JLabel();
        txtProblemID = new javax.swing.JTextField();
        ProblemIDLabel = new javax.swing.JLabel();
        LanguageLabel = new javax.swing.JLabel();
        SourceCodeScrollPane = new javax.swing.JScrollPane();
        SourceCodeTextArea = new javax.swing.JTextArea();
        LanguageComboBox = new javax.swing.JComboBox();
        SourceCodeLabel = new javax.swing.JLabel();
        ChooseFileButton = new javax.swing.JButton();
        SubmitButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setBackground(new java.awt.Color(51, 51, 51));
        jDesktopPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 255, 102), new java.awt.Color(153, 255, 153), null, null));
        jDesktopPane1.setLayout(new java.awt.BorderLayout());

        UserDashboardTabSwitcher.setBackground(new java.awt.Color(204, 204, 204));
        UserDashboardTabSwitcher.setForeground(new java.awt.Color(0, 204, 0));
        UserDashboardTabSwitcher.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        UserDashboardTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                UserDashboardTabSwitcherMouseClicked(evt);
            }
        });

        HomePanel.setBackground(new java.awt.Color(255, 255, 255));
        HomePanel.setLayout(new java.awt.GridBagLayout());

        WelcomeLabel.setFont(new java.awt.Font("Product Sans", 1, 36)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(102, 255, 102));
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("Welcome To IUTForces");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(43, 40, 95, 40);
        HomePanel.add(WelcomeLabel, gridBagConstraints);

        LogOutButton.setBackground(new java.awt.Color(153, 255, 102));
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BELOW_BASELINE;
        gridBagConstraints.insets = new java.awt.Insets(38, 38, 0, 38);
        HomePanel.add(LogOutButton, gridBagConstraints);

        UserDashboardTabSwitcher.addTab("Home", HomePanel);

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
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        ProblemsetPanelLayout.setVerticalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        UserDashboardTabSwitcher.addTab("Problemset", ProblemsetPanel);

        StatusPanel.setLayout(new java.awt.BorderLayout());

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
        StatusScrollPane.setViewportView(StatusTable);

        StatusPanel.add(StatusScrollPane, java.awt.BorderLayout.CENTER);

        UserDashboardTabSwitcher.addTab("Status", StatusPanel);

        MySubPanel.setLayout(new java.awt.BorderLayout());

        MySubScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        MySubTable.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        MySubTable.setModel(new javax.swing.table.DefaultTableModel(
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
        MySubTable.setFocusable(false);
        MySubTable.setGridColor(new java.awt.Color(255, 255, 255));
        MySubTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MySubTable.setOpaque(false);
        MySubTable.setRequestFocusEnabled(false);
        MySubTable.setRowHeight(25);
        MySubTable.setRowSelectionAllowed(false);
        MySubTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        MySubTable.setShowHorizontalLines(false);
        MySubTable.getTableHeader().setReorderingAllowed(false);
        MySubScrollPane.setViewportView(MySubTable);

        MySubPanel.add(MySubScrollPane, java.awt.BorderLayout.CENTER);

        UserDashboardTabSwitcher.addTab("My Submissions", MySubPanel);

        StandingsPanel.setLayout(new java.awt.BorderLayout());

        StandingsScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StandingsTable.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        StandingsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        StandingsScrollPane.setViewportView(StandingsTable);

        StandingsPanel.add(StandingsScrollPane, java.awt.BorderLayout.CENTER);

        UserDashboardTabSwitcher.addTab("Standings", StandingsPanel);

        SubmitSolPanel.setBackground(new java.awt.Color(255, 255, 255));
        SubmitSolPanel.setForeground(new java.awt.Color(255, 0, 51));
        SubmitSolPanel.setLayout(new java.awt.GridBagLayout());

        ChooseFileLabel.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        ChooseFileLabel.setForeground(new java.awt.Color(255, 51, 51));
        ChooseFileLabel.setText("Or choose File:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 50, 0, 0);
        SubmitSolPanel.add(ChooseFileLabel, gridBagConstraints);

        txtProblemID.setFont(new java.awt.Font("Product Sans", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 156;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 0, 0, 0);
        SubmitSolPanel.add(txtProblemID, gridBagConstraints);

        ProblemIDLabel.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        ProblemIDLabel.setForeground(new java.awt.Color(255, 0, 51));
        ProblemIDLabel.setText("Problem ID: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 61;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 40, 0, 0);
        SubmitSolPanel.add(ProblemIDLabel, gridBagConstraints);

        LanguageLabel.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        LanguageLabel.setForeground(new java.awt.Color(255, 0, 51));
        LanguageLabel.setText("Language: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 40, 0, 0);
        SubmitSolPanel.add(LanguageLabel, gridBagConstraints);

        SourceCodeTextArea.setColumns(20);
        SourceCodeTextArea.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        SourceCodeTextArea.setRows(5);
        SourceCodeScrollPane.setViewportView(SourceCodeTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 540;
        gridBagConstraints.ipady = 320;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 0, 0, 160);
        SubmitSolPanel.add(SourceCodeScrollPane, gridBagConstraints);

        LanguageComboBox.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LanguageComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "C", "C++", "Java" }));
        LanguageComboBox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(102, 255, 204), null, null));
        LanguageComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                LanguageComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        SubmitSolPanel.add(LanguageComboBox, gridBagConstraints);

        SourceCodeLabel.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        SourceCodeLabel.setForeground(new java.awt.Color(255, 0, 51));
        SourceCodeLabel.setText("Source Code:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 40, 0, 0);
        SubmitSolPanel.add(SourceCodeLabel, gridBagConstraints);

        ChooseFileButton.setFont(new java.awt.Font("Product Sans", 0, 13)); // NOI18N
        ChooseFileButton.setText("Choose File");
        ChooseFileButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 255, 51), new java.awt.Color(102, 255, 102), null, null));
        ChooseFileButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ChooseFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = -2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        SubmitSolPanel.add(ChooseFileButton, gridBagConstraints);

        SubmitButton.setBackground(new java.awt.Color(255, 0, 51));
        SubmitButton.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(255, 51, 51));
        SubmitButton.setText("Submit");
        SubmitButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 181, 204)));
        SubmitButton.setContentAreaFilled(false);
        SubmitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SubmitButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                SubmitButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 100, 60, 0);
        SubmitSolPanel.add(SubmitButton, gridBagConstraints);

        UserDashboardTabSwitcher.addTab("Submit Solution", SubmitSolPanel);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.setFont(new java.awt.Font("Product Sans", 0, 16)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        jButton1.setText("Send");

        jLabel1.setText("*Formulate your queries as short and concise as possible.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 916, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(36, 36, 36)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(81, Short.MAX_VALUE))
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
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        UserDashboardTabSwitcher.addTab("Clarifications", jPanel1);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Product Sans", 0, 16)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jComboBox1.setFont(new java.awt.Font("Product Sans", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Product Sans", 0, 18)); // NOI18N
        jLabel2.setText("Problem ID: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        UserDashboardTabSwitcher.addTab("Tutorial", jPanel2);

        jDesktopPane1.add(UserDashboardTabSwitcher, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1058, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 709, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_LogOutButtonActionPerformed
    {//GEN-HEADEREND:event_LogOutButtonActionPerformed
        userLogin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void ProblemsetTableComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_ProblemsetTableComponentResized
    {//GEN-HEADEREND:event_ProblemsetTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_ProblemsetTableComponentResized

    private void LanguageComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_LanguageComboBoxActionPerformed
    {//GEN-HEADEREND:event_LanguageComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LanguageComboBoxActionPerformed

    private void ChooseFileButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ChooseFileButtonActionPerformed
    {//GEN-HEADEREND:event_ChooseFileButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("C++ Documents", "cpp"));
        filemanager.showOpenDialog(this);
        codeFile = filemanager.getSelectedFile();
        if (codeFile != null)
        {
            String language = (String) LanguageComboBox.getSelectedItem();
            if (language.equals("C"))
            {
                language = "c";
            }
            if (language.equals("C++"))
            {
                language = "cpp";
            }
            if (language.equals("Java"))
            {
                language = "java";
            }
            String extension = codeFile.getName().substring(codeFile.getName().lastIndexOf(".") + 1);
            if (extension.toLowerCase().equals(language))
            {
                ChooseFileButton.setText(codeFile.getName());
            } 
            else
            {
                JOptionPane.showMessageDialog(null, "Select Correct language or Codefile", "Error", JOptionPane.ERROR_MESSAGE);
                codeFile = null;
            }
        } 
        else
        {
            JOptionPane.showMessageDialog(null, "No file chosen!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ChooseFileButtonActionPerformed
    public void setTab(int x, String ID) 
    {
        UserDashboardTabSwitcher.setSelectedIndex(x);
        txtProblemID.setText(ID);
        txtProblemID.setEditable(false);
    }
    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SubmitButtonActionPerformed
    {//GEN-HEADEREND:event_SubmitButtonActionPerformed
        String problemid = txtProblemID.getText();
        try
        {
            if (Integer.parseInt(problemid) < 0)
            {
                JOptionPane.showMessageDialog(null, "Problem ID cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } 
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Invalid Problem ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String language = (String) LanguageComboBox.getSelectedItem();
        if (codeFile == null)
        {
            try
            {
                codeFile = new File("Submission.txt");
                FileWriter txtcodewriter = new FileWriter(codeFile);
                txtcodewriter.write(SourceCodeTextArea.getText());
                txtcodewriter.close();
            } 
            catch (IOException ex)
            {
                System.out.println("Source code writing Err: " + ex.getMessage());
            }
        }
        if (codeFile == null)
        {
            JOptionPane.showMessageDialog(null, "No file chosen!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        userSocket.sendData("AddSub--[" + codeFile.getName() + "]");
        if (userSocket.addSubmission(codeFile, problemid, language) > 0)
        {
            JOptionPane.showMessageDialog(null, "Submitted!", "Status", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_SubmitButtonActionPerformed

    private void UserDashboardTabSwitcherMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_UserDashboardTabSwitcherMouseClicked
    {//GEN-HEADEREND:event_UserDashboardTabSwitcherMouseClicked
        txtProblemID.setText(null);
        ChooseFileButton.setText("Choose file");
        codeFile = null;
        SourceCodeTextArea.setText(null);
        int x = UserDashboardTabSwitcher.getSelectedIndex();
        Object[][] table;
        switch (x)
        {
            case 1:
            userSocket.sendData("PrbTable[null]");
            table=userSocket.getProblemTable();
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
                ProblemsetTable.setModel(tablemodel);
            }
            break;
            
            case 2:
            txtProblemID.setEditable(true);
            break;
            
            case 3:
            userSocket.sendData("StTable-[nullus]");
            table=userSocket.getStatusTable();
            if (table == null)
            {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } 
            else
            {
                String[] columns = {"#", "When", "Who", "Problem", "Language", "Verdict", "Time"};
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
            
            case 4:
            userSocket.sendData("StTable-[My]");
            table = userSocket.getStatusTable();
            if (table == null)
            {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } 
            else
            {
                String[] columns = {"#", "When", "Who", "Problem", "Language", "Verdict", "Time"};
                DefaultTableModel tablemodel = new DefaultTableModel(table, columns)
                {
                    public boolean isCellEditable(int row, int col)
                    {
                        return false;
                    }
                };
                MySubTable.setModel(tablemodel);
            }
            break;
            
            case 5:
            userSocket.sendData("StdTable[null]");
            table = userSocket.getStandingsTable();
            if (table == null)
            {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } 
            else
            {
                String[] columns = {"#", "Student ID", "Problems Solved"};
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
    }//GEN-LAST:event_UserDashboardTabSwitcherMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextField1ActionPerformed
    {//GEN-HEADEREND:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBox1ActionPerformed
    {//GEN-HEADEREND:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChooseFileButton;
    private javax.swing.JLabel ChooseFileLabel;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JComboBox LanguageComboBox;
    private javax.swing.JLabel LanguageLabel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel MySubPanel;
    private javax.swing.JScrollPane MySubScrollPane;
    private javax.swing.JTable MySubTable;
    private javax.swing.JLabel ProblemIDLabel;
    private javax.swing.JScrollPane ProblemSetjScrollPane;
    private javax.swing.JPanel ProblemsetPanel;
    private javax.swing.JTable ProblemsetTable;
    private javax.swing.JLabel SourceCodeLabel;
    private javax.swing.JScrollPane SourceCodeScrollPane;
    private javax.swing.JTextArea SourceCodeTextArea;
    private javax.swing.JPanel StandingsPanel;
    private javax.swing.JScrollPane StandingsScrollPane;
    private javax.swing.JTable StandingsTable;
    private javax.swing.JPanel StatusPanel;
    private javax.swing.JScrollPane StatusScrollPane;
    private javax.swing.JTable StatusTable;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JPanel SubmitSolPanel;
    private javax.swing.JTabbedPane UserDashboardTabSwitcher;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtProblemID;
    // End of variables declaration//GEN-END:variables
}
