package P5MX;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static P1User.UserDAL.SelectAllUsers;
import static P1User.UserDAL.SelectUsersByAccess;
import static P1User.UserDAL.SelectUsersByFile;
import P2Access.AccessBLL;
import P2Access.AccessDAL;
import static P3File.FileDAL.SelectFile;
import static P3File.FileDAL.SelectFilesByUser;
import P4Tag.TagBLL;
import static P4Tag.TagDAL.DeleteTag;
import static P4Tag.TagDAL.InsertTag;
import static P4Tag.TagDAL.SelectTagsByFile;
import static P4Tag.TagDAL.SelectTagsByUser;
import static P5MX.Commands.FileDelete;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
/**
 *
 * @author ARISTOCRAT
 */
@MultipartConfig
@WebServlet(name = "CommandEngine", urlPatterns = {"/CommandEngine"})
public class CommandEngine extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
   
        
        if ( ServletFileUpload.isMultipartContent(request)){
            
            Components.ImportFile(request, response);
            
        }
        else {
            JSONArray output = new JSONArray();
           response.setContentType("application/json");
           String command = request.getParameter("command");
           String Index = null;
           String Index_B = null;
           System.out.println("[ command :> " + request.getParameter("command") + " ]");

        
        
        switch ( request.getParameter("command")){
            case "select_users": 
                output.put(SelectAllUsers());
                break;
            case "select_user_files": 
                Index = request.getParameter("UserId");
                System.out.println("[ UserId " + Index + " ]");
                output.put(SelectFilesByUser(Integer.valueOf(Index)));
                    break;
            case "select_user_tags" : 
                Index = request.getParameter("UserId");
                System.out.println("[ UserId " + Index + " ]");
                output.put(SelectTagsByUser(Integer.valueOf(Index)));
                break;
            case "select_file_tags": 
                Index = request.getParameter("FileId");
                System.out.println("[ FileId " + Index + " ]");
                output.put(SelectTagsByFile(Integer.valueOf(Index)));
                break;
            case "select_file_users":
                Index = request.getParameter("FileId");
                Index_B = request.getParameter("UserId");
                System.out.println("[ FileId " + Index + " ]");
                output.put(SelectUsersByFile(Integer.valueOf(Index_B),Integer.valueOf(Index)));
                break;
            case "select_users_by_access" :
                Index = request.getParameter("FileId");
                output.put(SelectUsersByAccess(Integer.valueOf(Index)));
                break;
            case "download_file":              
                Index = request.getParameter("FileId");
                System.out.println("[ Download " + Index + " ]");
                Components.ExportFile(SelectFile(Integer.valueOf(Index)), request, response);                
                break;
            case "delete_file":
                Index = request.getParameter("FileId");
                FileDelete(Integer.valueOf(Index));
                System.out.println("[ Delete " + Index + " ]");
                break;
            case "add_tag":
                Index = request.getParameter("FileId");
                Index_B = request.getParameter("Tag");
                
                System.out.println("[ add tag " + Index + ":" + Index_B + " ]");
                InsertTag(new TagBLL(Integer.valueOf(Index), Index_B));
                
                break;
            case "remove_tag":
                Index = request.getParameter("TagId");
                System.out.println("[ remove tag " + Index + " ]");
                DeleteTag(Integer.valueOf(Index));
                break;
            case "grant_access":
                Index = request.getParameter("UserId");
                Index_B = request.getParameter("FileId");
                AccessDAL.GrantAccess(new AccessBLL(Integer.valueOf(Index), Integer.valueOf(Index_B)));
                
                System.out.println("[ Grant [user:" + Index + "] to [file:" + Index_B + " ]");
                break;
            case "revoke_access":
                Index = request.getParameter("UserId");
                Index_B = request.getParameter("FileId");
                AccessDAL.RevokeAccess(new AccessBLL(Integer.valueOf(Index), Integer.valueOf(Index_B)));
                System.out.println("[ Revoke [user:" + Index + "] to [file:" + Index_B + " ]");
                break;
            case "cli": 
                    output.put("works");
                    output.put("dilbert");
                    output.put("momma");
                System.out.println("[ compute >> " + command + " ]");
                break;
        }
        
            System.out.println( "" + Math.random() + output);

            try (PrintWriter out = response.getWriter()) {

               out.println(output.toString());
               out.flush();
           }
        }
  
        
        
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



}
