/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5MX;

import P2Access.AccessBLL;
import P2Access.AccessDAL;
import P3File.FileBLL;
import P4Tag.TagDAL;
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
public class Garbage {
     public static ArrayList<AccessBLL>  SelectAccessByUser(Integer UserId) {
        
        ArrayList<AccessBLL> set = new ArrayList<>();
        
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AccessRepo WHERE UserId=?");

                stmt.setInt(1, UserId);
                
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
            //Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return set;
        
    }
    
     public static FileBLL SelectMeta(Integer FileId){
        
        FileBLL fx = new FileBLL();

        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fsrepo WHERE FileId=?");

                stmt.setInt(1, FileId);
                
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    fx.setFileId(FileId);
                    fx.setFilename(rs.getString("filename"));
                    fx.setFiletype(rs.getString("filetype"));
                    fx.setSize(rs.getInt("filesize"));
                }
                
                
              
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
           // Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return fx;
    }
    
     
    public static void DeleteTag(Integer FileId, String Tag) {
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM TagRepo WHERE FileId=? AND Tag=?");
                
                stmt.setInt(1, FileId);
                stmt.setString(2, Tag);
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
    
     public static void      ModifyAccess(AccessBLL ax)  {
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("UPDATE AccessRepo SET AccessRead=?, AccessWrite=?, AccessGrant=?, AccessRevoke=? WHERE AccessId=?");
                
                // check if it doesn't exist already for a particular user
               
                stmt.setInt(5, ax.getAccessId());
                
                
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
     /*
      <!-- USER SELECT -->
    <!--<select name="UserIndex" >
        <%              
        for ( UserBLL ux : UserDAL.SelectAllUsers() ){%>
            
        <option value="<%=ux.getUserId() %>"> <%=ux.getUserId() + "::" + ux.getUserName() + "::" + ux.getUserPassword() %> </option>
        
        <%};%>
    </select>

    
    
    <select>
        <% 
        
        for ( FileBLL fx : Commands.SelectUserFiles(1) ){%>
            
        <option value="<%=fx.getFileId() %>"> <%= request.getParameter("") + " " + fx.getFilename() + "." + fx.getFiletype() %> </option>
        
        <%};%>
    </select>
    
    <!--  RIGHTS  -->
    
    <!-- TAGS -->
    
    
    
        Insert File
        <form action="FileController" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command_type" value="Insert">
            <input type="file" name="data" />
            <input type="submit" />
        </form> <br>
        
        Select File 
        <form  action="FileController" method="get" enctype="multipart/form-data">
            <input type="hidden" name="command_type" value="Select">
            <input type="text" name="filename">
            <input type="submit" />
        </form><br>
        
        <!--<a href="SelectFile" >download</a>-->
        
        Delete File
        <form action="FileController" method="post" enctype="text">
            <input type="hidden" name="command_type" value="Delete">
            <input type="text" name="filename" />
            <input type="submit" />
        </form> <br>
        
        Update File
        <form action="FileController" method="post" enctype="text">
            <input type="hidden" name="command_type" value="Update">
            <input type="text" name="old_filename" />
            <input type="text" name="new_filename" />
            <input type="submit" />
        </form> <br>
        
        Display File
        <form action="FileController" method="post" enctype="text">
            <input type="hidden" name="command_type" value="Display">
            <input type="text" name="filename" />
            <input type="submit" />
        </form> <br>
        
        
        New User
        
        Delete User
        
        Grant User access to file
        
        Revoke access to file-->
     */
    
}
