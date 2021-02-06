/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_server;

/**
 * This class handles all the Login and Signup operations.
 *
 * @author Rifat
 */
public class LoginSignUpHandler
{
    private final String data, clienttype;
    private final Database database;

    /**
     * This is the constructor of this class.
     *
     * @param data The String containing the username and password.
     * @param clienttype The type of client, User or Admin.
     * @param database The database object where all the credentials are stored.
     */
    public LoginSignUpHandler(String data, String clienttype, Database database)
    {
        this.data = data;
        this.clienttype = clienttype;
        this.database = database;
    }

    /**
     * This function determines whether a set of credentials are valid or not.
     *
     * @return Returns true of valid, otherwise false.
     */
    public boolean isValid()
    {
        int x = data.indexOf(']', 9);
        int y = data.lastIndexOf(']');
        String username = data.substring(9, x);
        String password = data.substring(x + 2, y);
        System.out.println(username + " " + password);
        if (clienttype.equals("Admin"))
        {
            String temp = database.getAdminPassword(username);

            if (temp.equals(password))
            {
                return true;
            } 
            else
            {
                return false;
            }
        } 
        else if (clienttype.equals("User"))
        {
            String temp = database.getUserPassword(username);
            if (temp.equals(password))
            {
                return true;
            } 
            else
            {
                return false;
            }
        } 
        else
        {
            return false;
        }
    }

    /**
     * This function completes the Signup process for new clients.
     *
     * @return Returns true if Signup was successful, otherwise false.
     */
    public boolean SignUp()
    {
        int x = data.indexOf(']', 9);
        int y = data.lastIndexOf(']');
        String username = data.substring(9, x);
        String password = data.substring(x + 2, y);
        System.out.println(username + " " + password);
        if (clienttype.equals("Admin"))
        {
            if (database.updateAdmin(username, password))
            {
                return true;
            }
        } 
        else if (clienttype.equals("User"))
        {
            if (database.updateUser(username, password))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This function checks an account with the provided credentials already
     * exists or not.
     *
     * @return Returns true if exists, otherwise false.
     */
    public boolean doesExist()
    {
        int x = data.indexOf(']', 9);
        int y = data.lastIndexOf(']');
        String username = data.substring(9, x);
        String password = data.substring(x + 2, y);
        System.out.println(username + " " + password);
        if (clienttype.equals("Admin"))
        {
            String temp = database.getAdminPassword(username);

            if (temp.equals("No#Data"))
            {
                return false;
            } 
            else
            {
                return true;
            }
        } 
        else if (clienttype.equals("User"))
        {
            String temp = database.getUserPassword(username);

            if(temp.equals("No#Data"))
            {
                return false;
            } 
            else
            {
                return true;
            }
        } 
        else
        {
            return false;
        }
    }

}
