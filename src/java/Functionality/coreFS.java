/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ARISTOCRAT
 */
public class coreFS {
    
    public static byte[]   load(String path){
        
        byte[] data = null;
        
        try {
            File fx = new File(path);
            FileInputStream fs = new FileInputStream(fx);
            
            data = new byte[(int)fx.length()];
            fs.read(data,0, data.length);
            
            fs.close();
            
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(coreFS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(coreFS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    public static boolean  store(String path, byte[] data){
        
        boolean cc = false;
        try {
            
            File fx = new File(path);
            FileOutputStream fs = new FileOutputStream(fx);
            
            fs.write(data);
            fs.close();
            
            cc = true;
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(coreFS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(coreFS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cc;
    }


}
