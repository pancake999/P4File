/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2Access;

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
public class AccessDAL {
    
    public static void      RevokeAccess(AccessBLL ax) {
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM AccessRepo WHERE UserId=? AND FileId=? ");
                
                stmt.setInt(1, ax.getUserId());
                stmt.setInt(2, ax.getFileId());
                int rowsaffected = stmt.executeUpdate();
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(AccessDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public static void      GrantAccess(AccessBLL ax)  {
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO AccessRepo(UserId, FileId) VALUES (?,?)");
                
                stmt.setInt(1, ax.getUserId());
                stmt.setInt(2, ax.getFileId());    
                
                int rowsaffected = stmt.executeUpdate();
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                 stmt.close();  
                 conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(AccessDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static AccessBLL SelectAccess(Integer UserId, Integer FileId)  {
        
        
        AccessBLL ax = null;
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement(
                            "SELECT * FROM accessrepo"
                                + " WHERE UserId=? AND FileId=?"
                );

                
                stmt.setInt(1, UserId);
                stmt.setInt(2, FileId);
                
                ResultSet rs = stmt.executeQuery();
                
                
                if (rs.next()) {
                    
                    ax = new AccessBLL();
                    
                    ax.setAccessId(rs.getInt("AccessId"));
                    ax.setUserId(rs.getInt("UserId"));
                    ax.setFileId(rs.getInt("FileId"));
                    
                        
                }
                
                 stmt.close();  
                 conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(AccessDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return ax;
    }
     
    public static ArrayList<AccessBLL>  SelectAccessByFile(Integer FileId) {
        
        ArrayList<AccessBLL> set = new ArrayList<>();
        
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AccessRepo WHERE FileId=?");

                stmt.setInt(1, FileId);
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    AccessBLL ax = new AccessBLL();
                    
                    ax.setAccessId(rs.getInt("AccessId"));
                    ax.setUserId(rs.getInt("UserId"));
                    ax.setFileId(rs.getInt("FileId"));
                    
                    set.add(ax);
                }
                
 
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(AccessDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return set;
        
    }
   
    static public void main(String[] args){
        AccessBLL ax = new AccessBLL(14, 136);

            
        //InsertAccess(ax);
        
        ArrayList<AccessBLL> set = new ArrayList<>();
         
            
            AccessBLL access_temp = SelectAccess(1,1);
            
            if ( access_temp != null ) {
               // System.out.println(access_temp.isAccessGrant());
            }
            
            System.out.println(set.toString());
    }
}
