/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5MX;

import P1User.UserBLL;
import static P1User.UserDAL.SelectUser;
import P2Access.AccessBLL;
import P2Access.AccessDAL;
import P3File.FileBLL;
import static P3File.FileDAL.InsertFile;
import P4Tag.TagBLL;
import P4Tag.TagDAL;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ARISTOCRAT
 */
public class Components {
    
    public static boolean UsernameTaken(String username){
        
        UserBLL ux = SelectUser(username);
        
        if ( ux == null) return false;  
        else return true;
    }
    
    public static boolean UsernameInvalid(String username){
        
        if ( username.matches(".{8,16}")) return false;
        else return true;
    }
    
    public static boolean PasswordInvalid(String password){
        
        if (password.matches("[A-Za-z]{8,16}")) return false;
        return true;
    }
    
    public static void ImportFile(HttpServletRequest request, HttpServletResponse response){

        System.out.println("file");
            
            FileItemFactory factory = new DiskFileItemFactory();

            ServletFileUpload upload = new ServletFileUpload(factory);
            ServletOutputStream out = null;

            Integer UserId = 0;
            
            FileBLL fx = new FileBLL();
                try {

                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();

                response.setContentType("text/html");
                out = response.getOutputStream();
                
                while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();

                        if ( item.isFormField()){
                            UserId = Integer.valueOf(item.getString());
                            System.out.println("[ name :> " + item.getFieldName() + " ]");
                            System.out.println("[ value :> " + item.getString() + " ]");
                            
                        }
                        else {
                            fx.setFilename( item.getName().split("\\.")[0] );
                            fx.setFiletype( item.getName().split("\\.")[1] );
                            fx.setData(item.get());
                            fx.setSize((int)item.getSize());                          
                        }
                }
                
                

            out.close();
        } catch (FileUploadException | IOException fue ) {}
           
        Integer FileId = InsertFile(fx);

       // file = SelectFileByName(file.getFilename());
        System.out.println("[ generatedKey :: " + FileId + " ]");

        AccessBLL ax = new AccessBLL(UserId, FileId);
        
        AccessDAL.GrantAccess(ax);
        
        TagDAL.InsertTag(new TagBLL(FileId, "Local"));
        System.out.println("Granted access!");
        
    }
    
    public static void DisplayFile(FileBLL FX, HttpServletRequest request, HttpServletResponse response){
        try {
            
            String fileId = FX.getFilename() + "." + FX.getFiletype();
            
            response.setContentType("image/jpeg");
           
            OutputStream out = response.getOutputStream();
            try (ByteArrayInputStream in = new ByteArrayInputStream(FX.getData())) {
                byte[] buffer = new byte[4096];     int length;
                while ((length = in.read(buffer)) > 0){
                    out.write(buffer, 0, length);
                }
            }
            out.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void ExportFile(FileBLL FX, HttpServletRequest request, HttpServletResponse response){
        try {
            
            String fileId = FX.getFilename() + "." + FX.getFiletype();
            
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileId + "\"");
           
            System.out.println(fileId);
            OutputStream out = response.getOutputStream();
            try (ByteArrayInputStream in = new ByteArrayInputStream(FX.getData())) {
                byte[] buffer = new byte[4096];     int length;
                while ((length = in.read(buffer)) > 0){
                    out.write(buffer, 0, length);
                }
            }
            out.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    
}
