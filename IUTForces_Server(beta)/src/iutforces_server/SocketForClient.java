/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 * This class the Socket related functions from the client's side.
 *
 * @author Rifat
 */
public class SocketForClient
{
    private Socket socket;
    private DataOutputStream dataout;
    private DataInputStream datain;
    private ObjectInputStream objectin;
    private ObjectOutputStream objectout;

    /**
     * This is the constructor of the class.
     *
     * @param socket The object of the Socket class.
     * @throws IOException IOException Signals that an I/O exception of some
     * sort has occurred. This class is the general class of exceptions produced
     * by failed or interrupted I/O operations.
     */
    public SocketForClient(Socket socket) throws IOException
    {
        this.socket = socket;
        try
        {
            dataout = new DataOutputStream(socket.getOutputStream());
            datain = new DataInputStream(socket.getInputStream());
            objectin = new ObjectInputStream(socket.getInputStream());
            objectout = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex)
        {
            System.out.println("SocketForClient contstructor IOException Error: "+ex.getMessage());
        }
    }

    /**
     * This function is responsible for sending data from server to clients.
     *
     * @param data The data in String datatype.
     * @return Returns length of data string or other numbers in case of
     * exceptions.
     */
    public int sendData(String data)
    {
        try
        {
            socket.setSoTimeout(5000);
            dataout.writeUTF(data);
            return data.length();
        } catch (SocketException ex)
        {
            return -2;
        } catch (IOException ex)
        {
            return -1;
        }

    }

    /**
     * This function is responsible for receiving data from the clients.
     *
     * @return The data in String datatype.
     */
    public String readData()
    {
        try
        {
            socket.setSoTimeout(0);
            return datain.readUTF();
        } catch (IOException ex)
        {
            System.out.println("SocketForClient readData() IOException Error: " + ex.getMessage());
            return null;
        }
    }

    /**
     * This function receives the contents of a problem.
     *
     * @return Returns the problem in the form of an object of NewProblem class.
     * @throws IOException IOException Signals that an I/O exception of some
     * sort has occurred. This class is the general class of exceptions produced
     * by failed or interrupted I/O operations.
     * @throws ClassNotFoundException Constructs a ClassNotFoundException with
     * no detail message.
     */
    public NewProblem saveProblem() throws IOException, ClassNotFoundException
    {
        NewProblem newproblem = (NewProblem) objectin.readObject();
        return newproblem;
    }

    /**
     * This function receives the contents of a submission.
     *
     * @return Returns the submission in the form of an object of NewSubmission
     * class.
     * @throws IOException IOException Signals that an I/O exception of some
     * sort has occurred. This class is the general class of exceptions produced
     * by failed or interrupted I/O operations.
     * @throws ClassNotFoundException Constructs a ClassNotFoundException with
     * no detail message.
     */
    public NewSubmission saveSubmission() throws IOException, ClassNotFoundException
    {
        NewSubmission newsubmission = (NewSubmission) objectin.readObject();
        return newsubmission;
    }

    /**
     * This function sends the contents of a problem.
     *
     * @param newproblem The object of the NewProblem class.
     * @return Returns true if sending was successful, otherwise false.
     */
    public boolean sendProblem(NewProblem newproblem)
    {
        try
        {
            objectout.writeObject(newproblem);
            return true;
        } catch (IOException ex)
        {
            System.out.println("SocketProblem Sending Error: " + ex.getMessage());
            return false;
        }
    }

    /**
     * This function sends the contents of a submission.
     *
     * @param submission The object of the NewSubmission class.
     * @return Returns true if sending was successful, otherwise false.
     */
    public boolean sendSubmission(NewSubmission submission)
    {
        try
        {
            objectout.writeObject(submission);
            return true;
        } catch (IOException ex)
        {
            System.out.println("SocketSubmision Sending Error: " + ex.getMessage());
            return false;
        }
    }

    /**
     * This function sends the Problem Table from the Server to the clients.
     *
     * @param table The 2D String array consisting of the table itself.
     * @return Returns true if sending was successful, otherwise false.
     */
    public boolean sendProblemTable(String[][] table)
    {
        try
        {
            objectout.writeObject(table);
            return true;
        } catch (IOException ex)
        {
            System.out.println("SocketProblemTable Sending Error: " + ex.getMessage());
            return false;
        }
    }

    /**
     * This function sends the Status Table from the Server to the clients.
     *
     * @param table The 2D String array consisting of the table itself.
     * @return Returns true if sending was successful, otherwise false.
     */
    public boolean sendStatusTable(String[][] table)
    {
        try
        {
            objectout.writeObject(table);
            return true;
        } catch (IOException ex)
        {
            System.out.println("SocketStatusTableSending Error: " + ex.getMessage());
            return false;
        }
    }

    /**
     * This function sends the Standings Table from the Server to the clients.
     *
     * @param table The 2D String array consisting of the table itself.
     * @return Returns true if sending was successful, otherwise false.
     */
    public boolean sendStandingsTable(String[][] table)
    {
        try
        {
            objectout.writeObject(table);
            return true;
        } catch (IOException ex)
        {
            System.out.println("SocketStandingsTable Sending Error: " + ex.getMessage());
            return false;
        }
    }

    /**
     * This function receives files from the clients.
     *
     * @param fname The name of the file in String datatype.
     * @param size The size of the file.
     * @return Returns the file itself.
     */
    public File readFile(String fname, long size)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(fname);
            byte[] data = new byte[1024];
            long receivedfilesize = 0;
            while (receivedfilesize < size)
            {
                receivedfilesize += datain.read(data);
                fos.write(data);
            }
            fos.close();
            dataout.writeUTF("EOF-----");
            return new File(fname);
        } catch (FileNotFoundException ex)
        {
            return null;
        } catch (IOException ex)
        {
            System.out.println("SocketForClient readFile() IOException Error: " + ex.getMessage());
            Logger.getLogger(SocketForClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * This function closes the Socket.
     *
     * @throws IOException IOException Signals that an I/O exception of some
     * sort has occurred. This class is the general class of exceptions produced
     * by failed or interrupted I/O operations.
     */
    public void close() throws IOException
    {
        socket.close();
    }

}
