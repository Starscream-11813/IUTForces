/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * This class handles the initiation and termination of the server.
 *
 * @author Rifat
 */
public class Server implements Runnable
{
    private final ServerSocket serverSocket;
    private final Database database;
    private boolean isRunning;

    /**
     * This is the constructor of this class.
     *
     * @param port The port number with which the server is to be started.
     * @throws IOException IOException Signals that an I/O exception of some
     * sort has occurred. This class is the general class of exceptions produced
     * by failed or interrupted I/O operations.
     * @throws SQLException An exception that provides information on a database
     * access error or other errors.
     */
    public Server(int port) throws IOException, SQLException
    {
        this.serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
        database = new Database();
        isRunning = true;
    }

    /**
     * This function stops the server.
     */
    public void stopServer()
    {
        isRunning = false;
    }

    /**
     * This function overrides the run() function of the Runnable class.
     */
    @Override
    public void run()
    {
        //To change body of generated methods, choose Tools | Templates.
        System.out.println("server is running");

        while (true)
        {

            try
            {
                Socket sc = serverSocket.accept();
                Thread t = new Thread(new MultiThread(sc, database));
                t.start();
                System.out.println("Client " + t.getId() + " connected");
            } catch (IOException ex)
            {
                System.out.println("New Thread Error " + ex);
                if (isRunning == false)
                {
                    break;
                }
            }
            if (isRunning == false)
            {
                break;
            }
        }
        try
        {
            serverSocket.close();
            database.conn.close();
        } catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server didn't close! " + ex);
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
