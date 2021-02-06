/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_admin;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 * This class is responsible for the Socket related functions from the Admin's side.
 * @author Rifat
 * @author Maksud
 */
public class AdminSocket
{
    private Socket adminSocket;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    
    /**
     * This is the constructor of this AdminSocket class.
     * It creates a new Socket object.
     * @throws IOException IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public AdminSocket() throws IOException
    {
        this.adminSocket=new Socket();
    }
    
    /**
     * This function is responsible for sending data to the Server.
     * @param data The data in String datatype.
     * @return Returns the length of the data if successful, otherwise a negative number based on the type of exception encountered.
     */
    public int sendData(String data)
    {
        try
        {
            adminSocket.setSoTimeout(5000);
            dataOut.writeUTF(data);
            System.out.println("DATA: "+data);
            return data.length();
        }
        catch(SocketException e)
        {
            System.out.println("Socket Exception Send Data: "+e.getMessage());
            return -2;
        }
        catch(IOException e)
        {
            System.out.println("IOException Send Data: "+e.getMessage());
            return -1;
        }
    }
    
    /**
     * This function is responsible for receiving data from the Server.
     * @return Returns the data itself.
     */
    public String readData()
    {
        try
        {
            adminSocket.setSoTimeout(3000);
            return dataIn.readUTF();
        }
        catch(IOException e)
        {
            return null;
        }
    }
    
    /**
     * This function sends a new problem to the server.
     * The given problem statement file, inputs file and expected outputs file are obtained as bytes and after that the input stream is closed. A NewProblem object is created with the given files' contents, problem ID, problem title, time-limit, memory-limit.
     * Then the newly created NewProblem object is written to the ObjectOutputStream.
     * @param problem The problem statement's file.
     * @param inputs The input test case of the problem.
     * @param outputs The expected outputs to the given inputs.
     * @param problemID The problem ID of the new problem.
     * @param problemTitle The title of the problem.
     * @param timeLimit The time limit of the problem.
     * @param memoryLimit The memory limit.
     * @return Return 1 if successful, otherwise 0.
     * @throws IOException IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public int addProblem(File problem,File inputs,File outputs,String problemID,String problemTitle,String timeLimit,String memoryLimit) throws IOException
    {
        try
        {
            FileInputStream problemInputStream=new FileInputStream(problem);
            byte prob[]=new byte[problemInputStream.available()];
            problemInputStream.read(prob);
            FileInputStream inputsInputStream=new FileInputStream(inputs);
            byte inp[]=new byte[inputsInputStream.available()];
            inputsInputStream.read(inp);
            FileInputStream outputsInputStream=new FileInputStream(outputs);
            byte outp[]=new byte[outputsInputStream.available()];
            outputsInputStream.read(outp);
            
            NewProblem newProblem=new NewProblem();
            newProblem.setProblemID(problemID);
            newProblem.setProblemName(problemTitle);
            newProblem.setTimeLimit(timeLimit);
            newProblem.setMemoryLimit(memoryLimit);
            newProblem.setProb(prob);
            newProblem.setInp(inp);
            newProblem.setOutp(outp);
            objectOut.writeObject(newProblem);
            objectOut.flush();
            return 1;
        }
        catch(FileNotFoundException e)
        {
            Logger.getLogger(AdminSocket.class.getName()).log(Level.SEVERE,null,e);
            return 0;
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
     * This function initiates the connection with a certain IP Address and a certain Port number.
     * @param ipAddress The IP Address of the server.
     * @param portNumber The Port Address with which the server has been started.
     * @return Returns true if the connection was successful, otherwise false.
     */
    public boolean connect(String ipAddress, int portNumber)
    {
        try
        {
            adminSocket=new Socket(ipAddress,portNumber);
            dataOut=new DataOutputStream(adminSocket.getOutputStream());
            dataIn=new DataInputStream(adminSocket.getInputStream());
            objectOut=new ObjectOutputStream(adminSocket.getOutputStream());
            objectIn=new ObjectInputStream(adminSocket.getInputStream());
            dataOut.writeUTF("Admin");
            return true;
        }
        catch(IOException e)
        {
            return false;
        }
    }
    
    /**
     * This function terminates the socket.
     * @throws IOException IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    public void close() throws IOException
    {
        adminSocket.close();
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
