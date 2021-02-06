/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_server;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 * This class is responsible for all the database operations.
 *
 * @author Rifat
 * @author Ishrak
 * @author Maksud
 */
public class Database
{
    Connection conn;
    PreparedStatement preparedStatement;
    Statement statement;

    /**
     * This is the constructor which attempts to establish a connection to the
     * given database URL. The prepared statement and statement variables are
     * set to null.
     *
     * @throws SQLException An exception that provides information on a database
     * access error or other errors.
     */
    public Database() throws SQLException
    {
        conn = DriverManager.getConnection("jdbc:sqlite::resource:Database/database.db");
        preparedStatement = null;
        statement = null;
    }

    /**
     * This function returns the password of a particular Admin account.
     *
     * @param username The username of the Admin account.
     * @return resultSet.getString(1) The password in string format which is in the 1st
     * column of the found tuple.
     */
    public synchronized String getAdminPassword(String username)
    {
        String query = "SELECT password from teacher where username = '" + username + "'";

        try
        {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next() == false)
            {
                return "No#Data";
            }

            return resultSet.getString(1);

        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Statement Error: " + ex);
            return "No#Data";
        }

    }

    /**
     * This function returns the password of a particular User account.
     *
     * @param username The username of the User account.
     * @return resultSet.getString(1) The password in string format which is in the 1st
     * column of the found tuple.
     */
    public synchronized String getUserPassword(String username)
    {
        String query = "SELECT password FROM student WHERE username = '" + username + "'";

        try
        {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next() == false)
            {
                return "No#Data";
            }

            return resultSet.getString(1);

        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Statement Error: " + ex);
            return "No#Data";
        }
    }

    /**
     * This function updates the credentials of a particular Admin account.
     *
     * @param username The username of the Admin account.
     * @param password The password of the account.
     * @return success/failure It will return true if the database was
     * successfully updated, otherwise it will return false.
     */
    public synchronized boolean updateAdmin(String username, String password)
    {

        String update = "INSERT INTO teacher(username,password) values(?,?)";

        try
        {
            preparedStatement = conn.prepareStatement(update);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * This function updates the credentials of a particular User account.
     *
     * @param username The username of the User account.
     * @param password The password of the account.
     * @return success/failure It will return true if the database was
     * successfully updated, otherwise it will return false.
     */
    public synchronized boolean updateUser(String username, String password)
    {
        String update = "INSERT INTO student(username,password) values(?,?)";

        try
        {
            preparedStatement = conn.prepareStatement(update);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * This function adds a new problem to the database. The problem ID is
     * determined by figuring out the ProblemID with the maximum value. The next
     * problem which is added has an ID which is 1 more than the max ID.
     *
     * @param problem The object of NewProblem class.
     * @param username The username of the problem's author.
     * @return success/failure Returns true if adding was successful and false
     * if adding resulted in failure.
     */
    public synchronized boolean addProblemToDB(NewProblem problem, String username)
    {
        String rowCountQuery = "SELECT MAX(ProblemID) AS ROWCOUNT FROM ProblemSet";
        String update = "INSERT INTO ProblemSet(ProblemID, ProblemName, ProblemSetter, TimeLimit, MemoryLimit, ProblemStatement, Inputs, Outputs) VALUES(?,?,?,?,?,?,?,?)";
        int rowcount = 0;

        try
        {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(rowCountQuery);

            if (resultSet.next())
            {
                rowcount = resultSet.getInt("ROWCOUNT");
            }
        } catch (SQLException ex)
        {
            System.out.println("RowCount Error: " + ex.getMessage());
        }
        try
        {
            System.out.println(username + ' ' + problem.getOutp().length);
            preparedStatement = conn.prepareStatement(update);

            preparedStatement.setInt(1, rowcount + 1);
            preparedStatement.setString(2, problem.getProblemName());
            preparedStatement.setString(3, username);
            preparedStatement.setInt(4, Integer.parseInt(problem.getTimeLimit()));
            preparedStatement.setInt(5, Integer.parseInt(problem.getMemoryLimit()));
            preparedStatement.setBytes(6, problem.getProb());
            preparedStatement.setBytes(7, problem.getInp());
            preparedStatement.setBytes(8, problem.getOutp());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex)
        {
            System.out.println("Insert problem Error: " + ex.getMessage());
            return false;
        }
    }

    /**
     * This function deletes a problem and submissions of that problem from the
     * database.
     *
     * @param problemID The Problem ID of the problem.
     */
    public synchronized void deleteProblem(int problemID)
    {
        String update = "DELETE FROM ProblemSet WHERE ProblemID = ?";
        System.out.println(update);
        try
        {
            preparedStatement = conn.prepareStatement(update);
            preparedStatement.setInt(1, problemID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex)
        {
            System.out.println("Delete Problem Error " + ex.getMessage());
        }
        update = "DELETE FROM Submissions WHERE ProblemID = ?";
        System.out.println(update);
        try
        {
            preparedStatement = conn.prepareStatement(update);
            preparedStatement.setInt(1, problemID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex)
        {
            System.out.println("Delete Problem -> Delete corresponding Submissions Error: " + ex.getMessage());
        }

    }

    /**
     * This function adds a new submission to the database.
     *
     * @param submission The object of NewSubmission class.
     * @param username The username of the student that submitted the
     * submission.
     * @return rowcount+1 One more than the number of rows in the table.
     */
    public synchronized int addSubmissionToDB(NewSubmission submission, String username)
    {
        String rowCountQuery = "SELECT COUNT(*) AS ROWCOUNT FROM Submissions";
        String update = "INSERT INTO Submissions(SubmissionID, ProblemID, Language, SubmittedBy, CodeFile, TimeOfSubmission, TimeTaken, Verdict) VALUES(?,?,?,?,?,?,?,?)";
        int rowcount = 0;

        try
        {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(rowCountQuery);

            if (resultSet.next())
            {
                rowcount = resultSet.getInt("rowcount");
            }
        } catch (SQLException ex)
        {
            System.out.println("RowCountErr " + ex.getMessage());
        }

        try
        {
            String timeStamp = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(Calendar.getInstance().getTime());

            preparedStatement = conn.prepareStatement(update);

            preparedStatement.setInt(1, rowcount + 1);
            preparedStatement.setInt(2, Integer.parseInt(submission.getProblemID()));
            preparedStatement.setString(3, submission.getLanguage());
            preparedStatement.setString(4, username);
            preparedStatement.setBytes(5, submission.getCodeF());
            preparedStatement.setString(6, timeStamp);
            preparedStatement.setInt(7, -1);
            preparedStatement.setString(8, "Not Judged");

            preparedStatement.executeUpdate();
            return rowcount + 1;

        } catch (SQLException ex)
        {
            System.out.println("Insert Submission Error: " + ex.getMessage());
            return -1;
        }

    }

    /**
     * This function is responsible for updating the verdict of a submission.
     *
     * @param sumbimmissionID The submission ID of the submission whose verdict
     * will be updated.
     * @param verdict The actual verdict of the submission.
     * @param timetaken The time taken by the source code to generate the
     * outputs.
     */
    public synchronized void updateVerdict(int sumbimmissionID, String verdict, int timetaken)
    {
        String update = "UPDATE Submissions SET Verdict = ?, TimeTaken = ? WHERE SubmissionID = ?";
        System.out.println("Database Update Verdict called!");

        try
        {
            preparedStatement = conn.prepareStatement(update);
            preparedStatement.setString(1, verdict);
            preparedStatement.setInt(3, sumbimmissionID);
            preparedStatement.setInt(2, timetaken);

            preparedStatement.executeUpdate();

        } catch (SQLException ex)
        {
            System.out.println("Database updateVerdict Error: " + ex.getMessage());
        }

    }

    /**
     * This function returns an object of the NewProblem class with the contents
     * of the problem with the given Problem ID.
     *
     * @param problemID The ID of the problem.
     * @return The NewProblem object of the given Problem ID.
     */
    public synchronized NewProblem getProblem(int problemID)
    {

        String query = "SELECT * FROM ProblemSet WHERE ProblemID = " + problemID;

        try
        {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next() == false)
            {
                return null;
            }

            NewProblem problem = new NewProblem();
            problem.setProblemName(resultSet.getString("ProblemName"));
            problem.setProblemID(Integer.toString(resultSet.getInt("ProblemID")));
            problem.setTimeLimit(Integer.toString(resultSet.getInt("TimeLimit")));
            problem.setMemoryLimit(Integer.toString(resultSet.getInt("MemoryLimit")));
            problem.setProb(resultSet.getBytes("ProblemStatement"));
            problem.setInp(resultSet.getBytes("Inputs"));
            problem.setOutp(resultSet.getBytes("Outputs"));

            return problem;

        } catch (SQLException ex)
        {
            System.out.println("Problem Query Err " + ex.getMessage());
            return null;
        }

    }

    /**
     * This function returns an object of the NewSubmission class with the
     * contents of the submission of the given submission ID.
     *
     * @param submissionID The submission ID of the submission.
     * @return The NewSubmission object with the contents of the submission with
     * the given submission ID.
     */
    public synchronized NewSubmission getSubmission(int submissionID)
    {

        String query = "SELECT * FROM Submissions WHERE SubmissionID = " + submissionID;

        try
        {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next() == false)
            {
                return null;
            }

            NewSubmission submission = new NewSubmission();

            submission.setProblemID(resultSet.getString("ProblemID"));
            submission.setLanguage(resultSet.getString("Language"));
            submission.setCodeF(resultSet.getBytes("CodeFile"));

            return submission;

        } catch (SQLException ex)
        {
            System.out.println("problem query Err " + ex.getMessage());
            return null;
        }

    }

    /**
     * This function returns a 2D String array which is the table of problems
     * displayed in the following tabs: Problems (User + Admin), My Problems
     * (Delete Problem, only Admin).
     *
     * @param identifier Identifier string to identify which format of the
     * Problem table has to be generated. ("null" for Problems tab listing out
     * all the problems)
     * @param identifier2 Identifier string to identify which format of the
     * Problem table has to be generated. ("MyDel" for the My Problem tab's
     * Delete Problem sub-tab)
     * @return The two-dimensional String array containing the Problems Table.
     */
    public synchronized String[][] getProblemTable(String identifier, String identifier2)
    {
        String query;
        if (identifier.equals("null"))
        {
            query = "SELECT ProblemID, ProblemName, ProblemSetter FROM Problemset";
        } else
        {
            query = "SELECT ProblemID, ProblemName, ProblemSetter FROM Problemset WHERE ProblemSetter = '" + identifier + "'";
        }

        ResultSet resultSet;
        try
        {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);

            int x;
            if (identifier2.equals("MyDel"))
            {
                String[][] table = new String[40][4];
                while (resultSet.next())
                {
                    x = resultSet.getRow() - 1;
                    table[x][0] = "<HTML><U><FONT COLOR='BLUE'>" + Integer.toString(resultSet.getInt("ProblemID")) + "</FONT></U></HTML>";
                    table[x][1] = "<HTML><U><FONT COLOR='BLUE'>" + resultSet.getString("ProblemName") + "</FONT></U></HTML>";
                    table[x][2] = resultSet.getString("ProblemSetter");
                    table[x][3] = "<HTML><U><FONT COLOR='RED'>DELETE</FONT></U></HTML>";

                    System.out.println(table[x][0] + " " + table[x][1] + " " + table[x][2] + " " + table[x][3]);
                }
                return table;
            } 
            else
            {
                String[][] table = new String[40][3];
                while (resultSet.next())
                {
                    x = resultSet.getRow() - 1;
                    table[x][0] = "<HTML><U><FONT COLOR='BLUE'>" + Integer.toString(resultSet.getInt("ProblemID")) + "</FONT></U></HTML>";
                    table[x][1] = "<HTML><U><FONT COLOR='BLUE'>" + resultSet.getString("ProblemName") + "</FONT></U></HTML>";
                    table[x][2] = resultSet.getString("ProblemSetter");

                    System.out.println(table[x][0] + " " + table[x][1] + " " + table[x][2]);
                }
                return table;
            }

        } catch (SQLException ex)
        {
            System.out.println("Database getProblemTable Error: " + ex.getMessage());
            return null;
        }

    }

    /**
     * This function returns a 2D String array which is the table of submissions
     * displayed in the following tabs: Status (User + Admin), My Submissions
     * (only User).
     *
     * @param identifier Identifier string to identify which format of the
     * Status table has to be generated. ("nullad" for Admin's Status Table,
     * "nullus" for User's Status Table, otherwise the User's own Status table
     * in My Submissions tab)
     * @return The two-dimensional String array containing the Status Table.
     */
    public synchronized String[][] getStatusTable(String identifier)
    {
        String query;
        System.out.println(identifier);
        if (identifier.equals("nullad") || identifier.equals("nullus"))
        {
            query = "SELECT Submissions.SubmissionID AS SubmissionID, Submissions.SubmittedBy AS SubmittedBy, Submissions.ProblemID AS ProblemID, Problemset.ProblemName AS ProblemName, Submissions.Language AS Language, Submissions.TimeOfSubmission AS TimeOfSubmission, Submissions.Verdict AS Verdict, Submissions.TimeTaken AS TimeTaken FROM Submissions, Problemset WHERE Submissions.ProblemID=Problemset.ProblemID";
        } 
        else
        {
            query = "SELECT Submissions.SubmissionID AS SubmissionID, Submissions.SubmittedBy AS SubmittedBy, Submissions.ProblemID AS ProblemID, Problemset.ProblemName AS ProblemName, Submissions.Language AS Language, Submissions.TimeOfSubmission AS TimeOfSubmission, Submissions.Verdict AS Verdict, Submissions.TimeTaken AS TimeTaken FROM Submissions, Problemset WHERE Submissions.ProblemID=Problemset.ProblemID AND Submissions.Submittedby = '" + identifier + "'";
        }

        ResultSet resultSet;
        try
        {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);

            int x;
            String[][] table = new String[40][7];

            while (resultSet.next())
            {
                x = resultSet.getRow() - 1;
                if (identifier.equals("nullus"))
                {
                    table[x][0] = Integer.toString(resultSet.getInt("SubmissionID"));
                } 
                else
                {
                    table[x][0] = "<HTML><U><FONT COLOR='BLUE'>" + Integer.toString(resultSet.getInt("SubmissionID")) + "</FONT></U></HTML>";
                }
                table[x][1] = resultSet.getString("TimeOfSubmission");
                table[x][2] = resultSet.getString("SubmittedBy");
                table[x][3] = "<HTML><U><FONT COLOR='BLUE'>" + Integer.toString(resultSet.getInt("ProblemID")) + "-" + resultSet.getString("ProblemName") + "</FONT></U></HTML>";
                table[x][4] = resultSet.getString("Language");
                table[x][5] = resultSet.getString("Verdict");
                table[x][6] = resultSet.getString("TimeTaken");
                System.out.println(table[x][0] + " " + table[x][1] + " " + table[x][2] + " " + table[x][3] + " " + table[x][4] + " " + table[x][5] + " " + table[x][6]);
            }
            return table;
        } catch (SQLException ex)
        {
            System.out.println("DB getProblemTable err" + ex.getMessage());
            return null;
        }
    }

    /**
     * This function returns a 2D String array which is the Standings Table
     * displayed in the following tab: Standings (User + Admin).
     *
     * @param identifier (not necessary) Identifier string to identify which
     * format of the Standings table has to be generated.
     * @return The two-dimensional String array containing the Standings Table.
     */
    public synchronized String[][] getStandingsTable(String identifier)
    {
        String query;
        System.out.println(identifier);
        query = "SELECT SubmittedBy AS ID, Count(DISTINCT ProblemID) AS Problems FROM Submissions WHERE Verdict='Accepted' GROUP BY ID ORDER BY Problems DESC";
        ResultSet resultSet;
        try
        {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            int x;
            String[][] table = new String[40][3];
            while (resultSet.next())
            {
                x = resultSet.getRow() - 1;
                table[x][0] = Integer.toString(x + 1);
                table[x][1] = resultSet.getString("ID");
                table[x][2] = resultSet.getString("Problems");
                System.out.println(table[x][0] + " " + table[x][1] + " " + table[x][2]);
            }
            return table;
        } catch (SQLException ex)
        {
            System.out.println("DB getProblemTable err" + ex.getMessage());
            return null;
        }

    }

}
