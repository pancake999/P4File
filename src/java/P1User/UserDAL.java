/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P1User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ARISTOCRAT
 */
public class UserDAL {
    
    public static void DeleteUser(String username) {
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM userrepo WHERE username=?");
                
                stmt.setString(1, username);
                int rowsaffected = stmt.executeUpdate();
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void UpdateUser(String username, String password)  {
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("UPDATE userrepo SET password=? WHERE username=?");
                
                // check if it doesn't exist already for a particular user
                
                stmt.setString(1, password);
                stmt.setString(2, username);
                
                int rowsaffected = stmt.executeUpdate();
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                 stmt.close();  
                 conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void InsertUser(UserBLL ux)  {
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO userrepo(username, userpassword, userrank)  VALUES (?,?,?)");
                
                // check if it doesn't exist already for a particular user
                
                stmt.setString(1, ux.getUserName());
                stmt.setString(2, ux.getUserPassword());
                stmt.setString(3, ux.getRank());          
                
                int rowsaffected = stmt.executeUpdate();
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                 stmt.close();  
                 conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static UserBLL SelectUser(String username) {
        
        UserBLL ux = null;
        
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM userrepo WHERE username=?");

                stmt.setString(1, username);
                
                ResultSet rs = stmt.executeQuery();
                
                if ( rs.next() ) {
                    ux = new UserBLL();
                    
                    ux.setUserId(rs.getInt("userid"));
                    ux.setUserName(username);
                    ux.setUserPassword(rs.getString("userpassword"));
                    ux.setRank(rs.getString("userrank"));
                }
                
                
              
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return ux;
        
    }
    
    public static ArrayList<UserBLL> SelectAllUsers() {
        
        ArrayList<UserBLL> set = new ArrayList<>();
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM userrepo");
                
                ResultSet rs = stmt.executeQuery();
                
                while ( rs.next()) {
                    UserBLL ux = new UserBLL();
                        ux.setUserId(rs.getInt("userid"));
                        ux.setUserName(rs.getString("username"));
                        ux.setUserPassword(rs.getString("userpassword"));
                        ux.setRank(rs.getString("userrank"));
                        
                    set.add(ux);
                }
                
               
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return set;
        
    }
    
    public static ArrayList<UserBLL> SelectUsersByFile(Integer UserId, Integer FileId){
        
        
        // select all AccessBLL by FileId > [UserId]
                // select all UserBLL by UserId
        ArrayList<UserBLL> set = new ArrayList<>();
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT DISTINCT userrepo.* FROM userrepo "
                                + "INNER JOIN accessrepo "
                                + "ON accessrepo.UserId=userrepo.UserId "
                                + "WHERE accessrepo.FileId=? AND userrepo.userid!=?"
                );
                
                stmt.setInt(1, FileId);
                stmt.setInt(2, UserId);
                ResultSet rs = stmt.executeQuery();
                
                while ( rs.next()) {
                    UserBLL ux = new UserBLL();
                        ux.setUserId(rs.getInt("userid"));
                        ux.setUserName(rs.getString("username"));
                        ux.setRank(rs.getString("userrank"));
                        
                   set.add(ux);
                }
                
               
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return set;
        
    }
    
    public static ArrayList<UserBLL> SelectUsersByAccess(Integer FileId){
                ArrayList<UserBLL> set = new ArrayList<>();
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT DISTINCT userrepo.* FROM userrepo "
                                + " LEFT JOIN accessrepo "
                                + " ON accessrepo.UserId=userrepo.UserId AND accessrepo.FileId=? "
                                + " WHERE accessrepo.FileId IS NULL"
                );
                
                stmt.setInt(1, FileId);
                ResultSet rs = stmt.executeQuery();
                
                while ( rs.next()) {
                    UserBLL ux = new UserBLL();
                        ux.setUserId(rs.getInt("userid"));
                        ux.setUserName(rs.getString("username"));
                        ux.setRank(rs.getString("userrank"));
                        
                   set.add(ux);
                }
                
               
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(UserDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return set;
    }
    
    
    
    
    
    
    
    static public void main(String[] args){
        System.out.println("Hello");
        
       /* UserBLL ux = new UserBLL();
            ux.setUserName("Foo");
            ux.setUserPassword("123");
            ux.setRank("Rookie");*/
            
       // InsertUser(ux);
        
        ArrayList<UserBLL> user_set = SelectUsersByAccess(5);
        
        for ( UserBLL ux : user_set ){
            System.out.println("[ " + ux.getUserId() + " :: " + ux.getUserName() + " " + ux.getRank() + " ]");
        }
        
        System.out.println("Size :: " + user_set.size());
        
        
    }
}
