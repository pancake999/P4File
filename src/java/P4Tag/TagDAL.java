/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P4Tag;

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
public class TagDAL {
    
    public static void DeleteTag(Integer TagId) {
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM TagRepo WHERE TagId=?");
                
                stmt.setInt(1, TagId);
                int rowsaffected = stmt.executeUpdate();
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(TagDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
     
    public static void InsertTag(TagBLL tx)  {
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO TagRepo(FileId, Tag)  VALUES (?,?)");

                stmt.setInt(1, tx.getFileId());
                stmt.setString(2, tx.getTag());  
                
                int rowsaffected = stmt.executeUpdate();
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                 stmt.close();  
                 conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(TagDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static ArrayList<TagBLL> SelectTagsByFile(Integer FileId) {
        
        ArrayList<TagBLL> set = new ArrayList<>();
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT * FROM TagRepo WHERE FileId=? AND TagRepo.Tag!='Local'");

                stmt.setInt(1, FileId);
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    
                    TagBLL tx = new TagBLL();         
                        tx.setTagId(rs.getInt("TagId"));
                        tx.setFileId(rs.getInt("FileId"));
                        tx.setTag(rs.getString("Tag"));
                        
                    set.add(tx);
                }
                
                
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(TagDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return set;
        
    }

    public static ArrayList<TagBLL> SelectTagsByUser(Integer UserId) {
        
        ArrayList<TagBLL> set = new ArrayList<>();
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT DISTINCT tagrepo.* FROM accessrepo"
                                + " INNER JOIN tagrepo"
                                + " ON accessrepo.fileid=tagrepo.fileid "
                                + " WHERE userid=?"
                );

                stmt.setInt(1, UserId);
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    
                    TagBLL tx = new TagBLL();   
                        tx.setTagId(rs.getInt("TagId"));
                        tx.setFileId(rs.getInt("FileId"));
                        tx.setTag(rs.getString("Tag"));
                        
                    set.add(tx);
                }
                
                
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(TagDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return set;
        
    }

    static public void main(String[] args){
            
        TagBLL tx = new TagBLL();
            tx.setFileId(14);
            tx.setTag("New York");
            
        //InsertTag(tx);
        //DeleteTag(5);
        
        ArrayList<TagBLL> tag_set = SelectTagsByUser(1);
            
        for( TagBLL tag : tag_set )
            System.out.println("[ " + " :: " + tag.getTag() + " ]");
    }
    
}
