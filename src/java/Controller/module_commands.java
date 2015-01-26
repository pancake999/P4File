/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Functionality.command_engine.abstract_command;
import Functionality.coreDB;
import java.util.ArrayList;

/**
 *
 * @author ARISTOCRAT
 */
public class module_commands {
    abstract class command extends abstract_command {
        
        public coreDB $ = new coreDB("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");

    }
    

     
    public static class new_file extends command {
        
        @Override
        public ArrayList<String> compute(ArrayList<String> set){
            
        $   .source("fsrepo")
            .set("filename='Test', filetype='txt', filesize='12', file='Hello World!'");
            
            return set;
        }
    }
    
    public static class load_file extends command {
        
        @Override
        public ArrayList<String> compute(ArrayList<String> set){
            
        $   .source("fsrepo") 
            .column("file")
            .record("filetype='jpg'")
            .query (set);
     
            return set;
        }
    }
     
    public static class delete_file extends command {
        
        @Override
        public ArrayList<String> compute(ArrayList<String> set){
        
        $   .source("fsrepo")
            .column("filename, filetype, file")
            .record("filename='Test'")
            .delete();
          
            return set;
        }
    }
  
}
