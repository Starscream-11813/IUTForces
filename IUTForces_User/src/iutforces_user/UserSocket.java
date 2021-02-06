/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_user;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 * This class is responsible for the Socket related functions from the User's side.
 * @author Rifat
 */
public class UserSocket
{
    private Socket userSocket;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    
    /**
     * This is the constructor of this UserSocket class.
     * It creates a new Socket object.
     * @throws IOException IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public UserSocket() throws IOException
    {
        this.userSocket=new Socket();
    }
    
    /**
     * This function terminates the socket.
     * @throws IOException IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public void close() throws IOException
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        userSocket.close();
    }
    
    /**
     * This function initiates the connection with a certain IP Address and a certain Port number.
     * @param ipAddress The IP Address of the server.
     * @param portNumber The Port Address with which the server has been started.
     * @return Returns true if the connection was successful, otherwise false.
     */
    public boolean connect(String ipAddress, int portNumber)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try
        {
            userSocket=new Socket(ipAddress, portNumber);
            dataOut=new DataOutputStream(userSocket.getOutputStream());
            dataIn=new DataInputStream(userSocket.getInputStream());
            objectOut=new ObjectOutputStream(userSocket.getOutputStream());
            objectIn=new ObjectInputStream(userSocket.getInputStream());
            dataOut.writeUTF("User");
            return true;
        }
        catch(IOException e)
        {
            return false;
        }
    }
    
    /**
     * This function is responsible for sending data to the Server.
     * @param data The data in String datatype.
     * @return Returns the length of the data if successful, otherwise a negative number based on the type of exception encountered.
     */
    public int sendData(String data)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try
        {
            userSocket.setSoTimeout(5000);
            dataOut.writeUTF(data);
            return data.length();
        }
        catch(SocketException e)
        {
            System.out.println("SocketException SendData: "+e.getMessage());
            return -2;
        }
        catch(IOException e)
        {
            System.out.println("IOException SendData: "+e.getMessage());
            return -1;
        }
    }
    
    /**
     * This function is responsible for receiving data from the Server.
     * @return Returns the data itself.
     */
    public String readData()
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try
        {
            userSocket.setSoTimeout(3000);
            return dataIn.readUTF();
        }
        catch(IOException e)
        {
            System.out.println("UserSocket readData() IOException Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * This function sends a new submission to the server.
     * The given code file is obtained as bytes and after that the input stream is closed. A NewSubmission object is created with the given codefile, problem ID and language.
     * Then the newly created NewSubmission object is written to the ObjectOutputStream.
     * @param codeFile The file containing the source code.
     * @param problemID The problem ID of the problem whose solution has been submitted.
     * @param language The language in which the source code has been written.
     * @return Returns 1 if adding the submission was successful, and 0 otherwise.
     */
    public int addSubmission(File codeFile, String problemID, String language) 
    {
        try 
        {
            FileInputStream codeFileInputStream = new FileInputStream(codeFile);
            byte codef[] = new byte[codeFileInputStream.available()];
            codeFileInputStream.read(codef);
            codeFileInputStream.close();
            NewSubmission newsubmission = new NewSubmission();
            newsubmission.setProblemID(problemID);
            newsubmission.setLanguage(language);
            newsubmission.setCodeF(codef);
            objectOut.writeObject(newsubmission);
            objectOut.flush();
            return 1;
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("AddSubmission FileNotFound Err "+e.getMessage());
            return 0;
        } 
        catch (IOException e) 
        {
            System.out.println("AddSubmission I/O Err "+e.getMessage());
            return 0;
        }
    }
    
    /**
     * This function receives the Status Table from the server.
     * The table is read from the ObjectInputStream.
     * @return Returns the table itself.
     */
    public String[][] getStatusTable()
    {
        String[][] table;
        try
        {
            table=(String[][])objectIn.readObject();
            return table;
        }
        catch(IOException e)
        {
            System.out.println("Socket Get Status I/O Error: "+e.getMessage());
            return null;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Socket Get Status ClassNotFound Error: "+e.getMessage());
            return null;
        }
    }
    
    /**
     * This function receives the Standings Table from the server.
     * @return Returns the table itself.
     */
    public String[][] getStandingsTable()
    {
        String[][] table;
        try
        {
            table=(String[][])objectIn.readObject();
            return table;
        }
        catch(IOException e)
        {
            System.out.println("Socket Get Standings I/O Error: "+e.getMessage());
            return null;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Socket Get Standings ClassNotFound Error: "+e.getMessage());
            return null;
        }
    }
    
    /**
     * This function receives the problem table from the server.
     * @return Returns the table itself if successful, otherwise returns null.
     */
    public String[][] getProblemTable()
    {
        String[][] table;
        try
        {
            table=(String[][]) objectIn.readObject();
            return table;
        }
        catch(IOException e)
        {
            System.out.println("Socket Get Problem I/O Error: "+e.getMessage());
            return null;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Socket Get Problem ClassNotFound Error: "+e.getMessage());
            return null;
        }
    }
    
    /**
     * This function receives a submission from the server.
     * @return Returns the object of NewSubmission itself if successful, otherwise returns null.
     */
    NewSubmission getSubmission() 
    {
        try 
        {
            return (NewSubmission) objectIn.readObject();
        } 
        catch (IOException e) 
        {
            System.out.println("AdminSocket Reading Submission I/O Error: "+e.getMessage());
            return null;
        } 
        catch (ClassNotFoundException e) 
        {
            System.out.println("AdminSocket Reading ClassNotFound Error: "+e.getMessage());
            return null;
        }
    }
    
    /**
     * This function receives a problem from the server.
     * @return Returns the object of NewProblem itself if successful, otherwise returns null.
     */
    NewProblem getProblem() 
    {
        try
        {
            return (NewProblem) objectIn.readObject();
        } 
        catch (IOException e) 
        {
            System.out.println("AdminSocket getProblem I/O Error: "+e.getMessage());
            return null;
        } 
        catch (ClassNotFoundException e) 
        {
            System.out.println("AdminSocket getProblem ClassNotFound Error: "+e.getMessage());
            return null;
        }
    }
    
}
