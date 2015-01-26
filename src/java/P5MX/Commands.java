/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5MX;


import P1User.*;
import static P1User.UserDAL.*;
import P2Access.*;
import static P2Access.AccessDAL.*;
import P3File.FileBLL;
import P3File.FileDAL;
import static P3File.FileDAL.*;
import P4Tag.TagBLL;
import P4Tag.TagDAL;
import static P4Tag.TagDAL.*;
import static P5MX.Components.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author ARISTOCRAT
 */
public class Commands {
    
    
    public static class response {
        public boolean succesfull;
        public String message;
        
        public response(boolean succesfull, String message){
            this.succesfull = succesfull;
            this.message = message;
        }
    }
    

    // VAULT COMMANDS
    public static response UserNew(String username, String password){
        
        
             if ( UsernameInvalid(username) ) 
                 return new response(false,"Invalid username.");
        else if ( UsernameTaken  (username) ) 
                return new response(false, "Username already taken.");
        else if ( PasswordInvalid(password) ) 
                return new response(false, "Invalid password.");
        else {
            
            UserBLL ux = new UserBLL();
                ux.setUserName(username);
                ux.setUserPassword(password);
                ux.setRank("Rookie");
            
            InsertUser(ux);
            return new response(true, "[ Welcome, " + username + " ]");
        }    
        
    }
    
    public static void UserDelete(Integer UserId){
        // delete user
        // delete access
        // delete files if user is sole owner of file
        // delete tags of those files
        
        /*ArrayList<AccessBLL> access_set = new ArrayList<>();
        
            access_set = SelectAccessByUser(UserId);
            
        
        for( AccessBLL ax : access_set){
            
            ArrayList<AccessBLL> set = SelectAccessByUser(ax.getFileId());
            
            if ( set.size() > 1 ) DeleteAccess(ax.getAccessId());
            else {
                FileDelete(ax.getFileId());
            }
        }*/
        
    }
    
    public static response UserLogin(String username, String password){
        
        if ( username == null || password == null ) return new response(false, "+ Fields are empty.");
        
        UserBLL ux = SelectUser(username);
        if ( ux == null) {
            return new response(false, "User does not exist.");
        }
        else if ( !password.matches(ux.getUserPassword()) ){
            return new response(false, "Incorrect Password");
        }
        else {
            return new response(true, "Correct login");
        }
    }
    
    public static response UserLogout(String username){
        
        return new response(true, "Goodbye " + username);
    }
    
    
    
    // FILE COMMANDS
    /* public static void FileUpload(Integer UserId, HttpServletRequest request, HttpServletResponse response ){

            FileBLL file = ImportFile(request, response);
            Integer index = InsertFile(file);
            
           // file = SelectFileByName(file.getFilename());
            System.out.println("[ generatedKey :: " + index + " ]");
            
            AccessBLL ax = new AccessBLL();
                ax.setAccessRead(true);
                ax.setAccessWrite(true);
                ax.setAccessRevoke(true);
                ax.setAccessGrant(true);
                ax.setUserId(UserId);
                ax.setFileId(index);
            
            AccessDAL.GrantAccess(ax);
            System.out.println("Granted access!");
            // add access to user
    }
    
   public static void FileDownload(Integer FileId, HttpServletRequest request, HttpServletResponse response){
        
        ExportFile(SelectFile(FileId), request, response);
                  
    }*/
    
    public static void FileDelete(Integer FileId){
        
        
        // Delete all TagRecords to File
        ArrayList<TagBLL> tag_set = SelectAllTags(FileId);
        
        for ( TagBLL tx : tag_set ){
            DeleteTag(tx.getTagId());
        }
        
        // Delete AccessRecords to File
        ArrayList<AccessBLL> access_set = SelectAccessByFile(FileId);
        
        for ( AccessBLL ax : access_set) {
            AccessDAL.RevokeAccess(ax);
        }
       
        // Delete File
        DeleteFile(FileId);
        
    }

    
    
    // LISTS COMMANDS
    public static ArrayList<FileBLL> SelectUserFiles(Integer UserId){
        
        /*ArrayList<FileBLL> file_set = new ArrayList<>();
        
        ArrayList<AccessBLL> access_set = SelectAccessByUser(UserId);
        
        for ( AccessBLL ax : access_set ){
            file_set.add(SelectMeta(ax.getFileId()));
        }
        
        return file_set;*/
        
        return SelectFilesByUser(UserId);
    }
        
    public static ArrayList<UserBLL> SelectUsersThatHaveAccessToFile(Integer UserId, Integer FileId){
        
        return SelectUsersByFile(UserId, FileId);
    }
    
    public static AccessBLL GetUserAccessLVL(Integer UserId, Integer FileId){
        
        return SelectAccess(UserId, FileId);
    }
    
    public static ArrayList<TagBLL> SelectAllTags(Integer FileId){
        
        return TagDAL.SelectTagsByFile(FileId);
    }
    
    public static ArrayList<TagBLL> SelectAllTagsAvailableToThisUser(Integer UserId){
        
        return TagDAL.SelectTagsByUser(UserId);
    }
    
    public static ArrayList<FileBLL> SelectFilesByTag(Integer UserId, Integer TagId){
        
        return FileDAL.SelectFilesByTag(UserId, TagId);
    }
    
    
    
    // ACCESS COMMANDS

   
    
    // TAG COMMANDS
    /*public static void TagNew(Integer FileId, String TagType, String Tag){
        // if user has right to do so
        TagBLL tx = new TagBLL();
            tx.setFileId(FileId);
            tx.setTag(Tag);
            
        InsertTag(tx);
    }*/
    
    /*public static void TagDelete(Integer TagId){
        // if user has right to do so
        DeleteTag(TagId);
    }*/
    
    
    
    
    
    public static void main(String args[]){
        
      
      
        
        
        ArrayList<FileBLL> set = SelectUserFiles(14);
        
        for( FileBLL fx : set ) System.out.println(fx.getFilename());
    }
}
