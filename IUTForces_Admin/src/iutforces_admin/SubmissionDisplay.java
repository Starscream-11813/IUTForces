/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_admin;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 * This is the interface class for displaying a particular submission.
 * @author Maksud
 */
public class SubmissionDisplay extends javax.swing.JFrame
{
    /**
     * Creates new form SubmissionDisplay
     */
    public SubmissionDisplay(AdminSocket adminsocket)
    {
        initComponents();
        
        SubDetailsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        SubDetailsTable.setRowHeight(25);
        this.setVisible(rootPaneCheckingEnabled);
        SubDetailsTable.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) 
                {
                    evt.consume();
                    int row = SubDetailsTable.rowAtPoint(evt.getPoint());
                    int col = SubDetailsTable.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col == 2) 
                    {
                        DefaultTableModel tablemodel = (DefaultTableModel) SubDetailsTable.getModel();
                        String temp = tablemodel.getValueAt(row, 2).toString();
                        int x = temp.indexOf('-',28);
                        String problemid = temp.substring(28, x);

                        adminsocket.sendData("ProbFile[" + problemid + "]");
                        NewProblem problem = adminsocket.getProblem();
                        try {
                            FileOutputStream fos = new FileOutputStream(problemid + ".pdf");
                            fos.write(problem.getProb());
                            fos.close();
                        } catch (FileNotFoundException ex) {
                            System.out.println("At probshow problem write Err: " + ex.getMessage());
                        } catch (IOException ex) {
                            System.out.println("At probshow problem write Err: " + ex.getMessage());
                        }
                        ProblemDisplay problemDisplay = new ProblemDisplay(problem.getProblemName(), problem.getTimeLimit(),problem.getMemoryLimit());
                        problemDisplay.viewPdf(new File(problemid + ".pdf"));
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

        SubDetailsScrollPane = new javax.swing.JScrollPane();
        SubDetailsTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        CopyButton = new javax.swing.JButton();
        SourceCodeLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        SourceCodeTextArea = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SubDetailsTable.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        SubDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "#", "Author", "Problem ID", "Language", "Verdict", "Time", "Submitted"
            }
        ));
        SubDetailsTable.setFocusable(false);
        SubDetailsTable.setRowHeight(25);
        SubDetailsTable.setRowSelectionAllowed(false);
        SubDetailsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        SubDetailsTable.setShowHorizontalLines(false);
        SubDetailsScrollPane.setViewportView(SubDetailsTable);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        CopyButton.setFont(new java.awt.Font("Product Sans", 1, 14)); // NOI18N
        CopyButton.setText("Copy");
        CopyButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                CopyButtonActionPerformed(evt);
            }
        });

        SourceCodeLabel.setFont(new java.awt.Font("Product Sans", 1, 18)); // NOI18N
        SourceCodeLabel.setForeground(new java.awt.Color(255, 51, 51));
        SourceCodeLabel.setText("Source Code");

        SourceCodeTextArea.setEditable(false);
        SourceCodeTextArea.setBackground(new java.awt.Color(239, 240, 242));
        SourceCodeTextArea.setColumns(20);
        SourceCodeTextArea.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        SourceCodeTextArea.setForeground(new java.awt.Color(0, 51, 51));
        SourceCodeTextArea.setRows(5);
        jScrollPane2.setViewportView(SourceCodeTextArea);

        closeButton.setFont(new java.awt.Font("Product Sans", 1, 14)); // NOI18N
        closeButton.setText("Exit");
        closeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(SourceCodeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CopyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 816, Short.MAX_VALUE)
                        .addComponent(closeButton)
                        .addGap(10, 10, 10))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SourceCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CopyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeButton))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SubDetailsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1096, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SubDetailsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 353, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(67, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CopyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopyButtonActionPerformed
        try {
            SourceCodeTextArea.getToolkit().getSystemClipboard().setContents(new StringSelection(SourceCodeTextArea.getText()), null);
            Image img = Toolkit.getDefaultToolkit().createImage("icon.png");
            SystemTray tray = SystemTray.getSystemTray();
            TrayIcon trayicon = new TrayIcon(img);
            tray.add(trayicon);
            trayicon.displayMessage("Source Code", "Code Copied", TrayIcon.MessageType.INFO);
        } catch (AWTException ex) {
            Logger.getLogger(SubmissionDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_CopyButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_closeButtonActionPerformed
    {//GEN-HEADEREND:event_closeButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    
    public void setSubDetailsTable(Object subID, Object author, Object problem, Object lang, Object verdict, Object time, Object submitted) 
    {
        Object[][] table = {{subID, author, problem, lang, verdict, time, submitted}};
        Object[] columns = { "#", "Author", "Problem ID", "Lang", "Verdict", "Time", "Submitted"};
        DefaultTableModel tablemodel = new DefaultTableModel(table, columns)
        {
            public boolean isCellEditable(int row, int col) 
            {
                return false;
            }
        };
        SubDetailsTable.setModel(tablemodel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        SubDetailsTable.setDefaultRenderer(Object.class, centerRenderer);
        JTableHeader subdetailstableheader = SubDetailsTable.getTableHeader();
        ((DefaultTableCellRenderer)subdetailstableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER); 
    }
    
    public void setSourceCOde(NewSubmission submission)
    {
        SourceCodeTextArea.setTabSize(4);
        SourceCodeTextArea.setText(new String(submission.getCodeF()));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CopyButton;
    private javax.swing.JLabel SourceCodeLabel;
    private javax.swing.JTextArea SourceCodeTextArea;
    private javax.swing.JScrollPane SubDetailsScrollPane;
    private javax.swing.JTable SubDetailsTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}
