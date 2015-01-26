/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ARISTOCRAT
 */
public class command_engine {
    
    public static abstract class abstract_command {
        
        coreDB $;
        
        public abstract ArrayList<String> compute(ArrayList<String> set);       
    }
       
    HashMap<String, abstract_command> commands = new HashMap<>(); 
    
    public void new_command(String key, abstract_command compute){
        
        if ( !commands.containsKey(key) ) commands.put(key, compute);
        else System.out.println("Already exists.");
           
    }
    
    public ArrayList<String> execute(String key, ArrayList<String> parameters){
        
        ArrayList<String> set = new ArrayList<>();
        
        if ( commands.containsKey(key)) {
            set = commands.get(key).compute(parameters);
            System.out.println("found function");
        }
        else {
            System.out.println("Doesn't exist");
        }
        
        return set;
    }

    
    
}
