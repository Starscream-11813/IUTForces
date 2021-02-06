/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_user;

import java.io.IOException;

/**
 * This class consists of the main function.
 * @author Rifat
 */
public class IUTForces_User
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException
    {
        // TODO code application logic here
        UserSocket userSocket;
        userSocket = new UserSocket();
        UserLogin login=new UserLogin(userSocket);
    }
    
}
