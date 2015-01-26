/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P3File;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ARISTOCRAT
 */
public class FileDAL {

    public static void DeleteFile(Integer FileId) {
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM fsrepo WHERE FileId=?");
                
                stmt.setInt(1, FileId);
                int rowsaffected = stmt.executeUpdate();
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(FileDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static Integer InsertFile(FileBLL fx)  {
        
        Integer key = -1;
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO fsrepo(filename,filetype,filesize,file)  "
                        + "VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                
                // check if it doesn't exist already for a particular user
                
                stmt.setString(1, fx.getFilename());
                stmt.setString(2, fx.getFiletype());
                stmt.setInt(3, fx.getSize());          
                stmt.setBinaryStream(4, new ByteArrayInputStream(fx.getData()), fx.getData().length);

                int rowsaffected = stmt.executeUpdate();
                
                try ( ResultSet generatedKey = stmt.getGeneratedKeys()){
                    if ( generatedKey.next()){
                        key = generatedKey.getInt(1);
                    }
                }
                
                if ( rowsaffected > 0 ) System.out.println("Succesfull");
                else                    System.out.println("Failure");

                 stmt.close();  
                 conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(FileDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return key;
    }
    
    public static FileBLL SelectFile(Integer FileId) {
        
        FileBLL fx = new FileBLL();
        
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fsrepo WHERE FileId=?");

                stmt.setInt(1, FileId);
                
                ResultSet rs = stmt.executeQuery();
                rs.next();
                
                fx.setFileId(FileId);
                fx.setFilename(rs.getString("filename"));
                fx.setFiletype(rs.getString("filetype"));
                fx.setSize(rs.getInt("filesize"));
                fx.setData(rs.getBytes("file"));
              
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(FileDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return fx;
        
    }
    
    public static FileBLL SelectFileByName(String Filename) {
        
        FileBLL fx = new FileBLL();
        
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fsrepo WHERE Filename=?");

                stmt.setString(1, Filename);
                
                ResultSet rs = stmt.executeQuery();
                rs.next();
                
                fx.setFileId(rs.getInt("fileid"));
                fx.setFilename(rs.getString("filename"));
                fx.setFiletype(rs.getString("filetype"));
                fx.setSize(rs.getInt("filesize"));
                fx.setData(rs.getBytes("file"));
              
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(FileDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return fx;
        
    }
    
    public static ArrayList<FileBLL> SelectFilesByUser(Integer UserId){
        
        ArrayList<FileBLL> file_set = new ArrayList<>();
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT DISTINCT fsrepo.fileId, fsrepo.filename, fsrepo.filetype, fsrepo.filesize FROM userrepo"
                                + " JOIN accessrepo"
                                + " ON accessrepo.userid=?"
                                + " JOIN fsrepo"
                                + " ON accessrepo.fileid=fsrepo.fileid"
                        );

                stmt.setInt(1, UserId);
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    FileBLL fx = new FileBLL();
                        fx.setFileId(rs.getInt("fileId"));
                        fx.setFilename(rs.getString("filename"));
                        fx.setFiletype(rs.getString("filetype"));
                        fx.setSize(rs.getInt("filesize"));
                        
                    file_set.add(fx);
                }
                
                
              
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(FileDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return file_set;
    }
    
    public static ArrayList<FileBLL> SelectFilesByTag(Integer UserId, Integer TagId){
                ArrayList<FileBLL> file_set = new ArrayList<>();
        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT DISTINCT fsrepo.fileId, fsrepo.filename, fsrepo.filetype, fsrepo.filesize FROM userrepo"
                                + " JOIN accessrepo"
                                + " ON accessrepo.userid=?"
                                + " JOIN tagrepo"
                                + " ON accessrepo.fileid=tagrepo.fileid"
                                + " JOIN fsrepo"
                                + " ON tagrepo.fileid=fsrepo.fileid"
                                + " WHERE tagrepo.tagid=?"
                        );

                stmt.setInt(1, UserId);
                stmt.setInt(2, TagId);
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    FileBLL fx = new FileBLL();
                        fx.setFileId(rs.getInt("fileId"));
                        fx.setFilename(rs.getString("filename"));
                        fx.setFiletype(rs.getString("filetype"));
                        fx.setSize(rs.getInt("filesize"));
                        
                    file_set.add(fx);
                }
                
                
              
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(FileDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return file_set;
    }
    
    public static ArrayList<FileBLL> SelectAllMeta(){
        
        ArrayList<FileBLL> set = new ArrayList<>();

        
        try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fsrepo");

 
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next() ) {
                    FileBLL fx = new FileBLL();
                    
                    fx.setFileId(rs.getInt("fileid"));
                    fx.setFilename(rs.getString("filename"));
                    fx.setFiletype(rs.getString("filetype"));
                    fx.setSize(rs.getInt("filesize"));
                    
                    
                    set.add(fx);
                }
                
                stmt.close();  
                conn.close();
	} 
         catch (SQLException ex) { 
            Logger.getLogger(FileDAL.class.getName()).log(Level.SEVERE, null, ex);
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
        
        ArrayList<FileBLL> file_set = SelectFilesByTag(2, 1);
        
        for ( FileBLL fx : file_set ){
            System.out.println("[ " + fx.getFilename() + " :: " + fx.getFiletype() + " ]");
        }
        
        
        
    }

}
