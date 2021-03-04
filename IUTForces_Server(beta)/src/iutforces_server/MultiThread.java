/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_server;

import java.io.IOException;
import java.net.Socket;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 * This class handles running of multiple threads of execution and it inherits
 * the Runnable class.
 *
 * @author Rifat
 */
public class MultiThread implements Runnable
{
    private final SocketForClient socketForClient;
    private final Database database;
    private String clienttype;
    private String username;

    /**
     * This is the constructor of the MultiThread class.
     *
     * @param socketForClient Object of the Socket class.
     * @param database The Database object.
     * @throws IOException IOException Signals that an I/O exception of some
     * sort has occurred. This class is the general class of exceptions produced
     * by failed or interrupted I/O operations.
     */
    MultiThread(Socket socketForClient, Database database) throws IOException
    {
        this.socketForClient = new SocketForClient(socketForClient);
        this.database = database;
        clienttype = null;
    }

    /**
     * This function overrides the run() function of the Runnable class.
     */
    @Override
    public void run()
    {
        clienttype = socketForClient.readData();
        System.out.println(clienttype);
        while (true)
        {
            String data = socketForClient.readData();
            if (data == null)
            {
                break;
            }

            String code = data.substring(0, 8);
            System.out.println(data + " " + code);
            switch (code)
            {
                case "Login---":
                    LoginSignUpHandler loginHandler = new LoginSignUpHandler(data, clienttype, database);
                    if (loginHandler.isValid())
                    {
                        socketForClient.sendData("LoginTrue");
                        int x = data.indexOf(']', 9);
                        username = data.substring(9, x);

                    } 
                    else
                    {
                        socketForClient.sendData("LoginFalse");
                    }
                    break;

                case "SignUp--":
                    LoginSignUpHandler signupHandler = new LoginSignUpHandler(data, clienttype, database);
                    if (signupHandler.doesExist())
                    {
                        socketForClient.sendData("Exist---");
                    } 
                    else if (signupHandler.SignUp())
                    {
                        socketForClient.sendData("SignUpTr");
                    } 
                    else
                    {
                        socketForClient.sendData("SignUpFl");
                    }
                    break;

                case "AddProb-":

                    System.out.println("AddProb- called");

                    NewProblem newproblem;

                    try
                    {
                        newproblem = socketForClient.saveProblem();
                        if (database.addProblemToDB(newproblem, username))
                        {
                            System.out.println("Problem Added");
                        } 
                        else
                        {
                            System.out.println("Problem adding failed!");
                        }
                    } catch (IOException | ClassNotFoundException ex)
                    {
                        System.out.println("Problem Object reading err " + ex.getMessage());
                    }
                    break;

                case "AddSub--":
                    System.out.println("AddSub-- called");
                    int submissionID;

                    NewSubmission newsubmission = null;

                    try
                    {
                        newsubmission = socketForClient.saveSubmission();
                        submissionID = database.addSubmissionToDB(newsubmission, username);
                        if (submissionID > -1)
                        {
                            System.out.println("Submission Added!");
                        } 
                        else
                        {
                            System.out.println("Submission adding failed!");
                        }
                    } catch (IOException | ClassNotFoundException ex)
                    {
                        System.out.println("Submission object reading Error: " + ex.getMessage());
                        submissionID = -1;
                    }
                    if (newsubmission != null)
                    {
                        NewProblem problem = database.getProblem(Integer.parseInt(newsubmission.getProblemID()));
                        if (problem != null)
                        {
                            Thread t = new Thread(new CompileAndRun(problem, newsubmission, submissionID, database));
                            t.start();
                        }
                    }
                    break;

                case "PrbTable":

                    int x = data.indexOf(']', 9);

                    String identifier = data.substring(9, x);
                    String identifier2 = identifier;
                    System.out.println(identifier);
                    if (identifier.equals("My") || identifier.equals("MyDel"))
                    {
                        identifier = username;
                    }

                    if (socketForClient.sendProblemTable(database.getProblemTable(identifier, identifier2)))
                    {
                        System.out.println("ProblemTable sent!");
                    } 
                    else
                    {
                        System.out.println("Problem Table sending failed!");
                    }
                    break;

                case "StTable-":

                    x = data.indexOf(']', 9);
                    identifier = data.substring(9, x);
                    System.out.println(identifier);

                    if (identifier.equals("My"))
                    {
                        identifier = username;
                    }

                    if (socketForClient.sendStatusTable(database.getStatusTable(identifier)))
                    {
                        System.out.println("Status Table sent!");
                    } 
                    else
                    {
                        System.out.println("Status Table sending failed!");
                    }
                    break;

                case "StdTable":

                    x = data.indexOf(']', 9);
                    identifier = data.substring(9, x);
                    System.out.println(identifier);

                    if (socketForClient.sendStandingsTable(database.getStandingsTable(identifier)))
                    {
                        System.out.println("Standings Table sent!");
                    } 
                    else
                    {
                        System.out.println("Standings Table sending failed!");
                    }
                    break;

                case "SrcCode-":
                    x = data.indexOf(']', 9);
                    identifier = data.substring(9, x);
                    System.out.println(identifier);

                    if (socketForClient.sendSubmission(database.getSubmission(Integer.parseInt(identifier))))
                    {
                        System.out.println("Submission sent!");
                    } 
                    else
                    {
                        System.out.println("Submission sending failed!");
                    }
                    break;

                case "ProbFile":
                    x = data.indexOf(']', 9);
                    identifier = data.substring(9, x);
                    System.out.println(identifier);

                    if (socketForClient.sendProblem(database.getProblem(Integer.parseInt(identifier))))
                    {
                        System.out.println("Problem sent!");
                    } 
                    else
                    {
                        System.out.println("Problem sending failed");
                    }
                    break;
                case "DelProb-":
                    x = data.indexOf(']', 9);
                    identifier = data.substring(9, x);
                    System.out.println(identifier);

                    database.deleteProblem(Integer.parseInt(identifier));

                    if (socketForClient.sendProblemTable(database.getProblemTable(username, "MyDel")))
                    {
                        System.out.println("Problem Table sent!");
                    } 
                    else
                    {
                        System.out.println("Problem Table sending failed!");
                    }
                default:
                    break;
            }

        }
        System.out.println("Thread is Done!");
    }

}
