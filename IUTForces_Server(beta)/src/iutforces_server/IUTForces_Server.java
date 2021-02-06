/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_server;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * This class is the class consisting of the main function.
 * @author Rifat
 */
public class IUTForces_Server 
{
    /**
     * @param args the command line arguments
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public static void main(String[] args) throws IOException, SQLException 
    {
        // TODO code application logic here
        ServerInterface serverStarter = new ServerInterface();
        serverStarter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                serverStarter.getServer().stopServer();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                serverStarter.getServer().stopServer();
            }

            @Override
            public void windowIconified(WindowEvent e) {
                return;
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                return;
            }

            @Override
            public void windowActivated(WindowEvent e) {
                return;
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
               return;
            }
        });
        serverStarter.startGUI();
    }
    
}
