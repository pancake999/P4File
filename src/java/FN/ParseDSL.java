/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FN;

/**
 *
 * @author ARISTOCRAT
 */
public class ParseDSL {
    
     // parse DSL
    // [ DEF ] [ UNION :: A1 A2 [ COMPOUND :: A3 DEF ]
    // capture as VARIABLE
    
    // PATTERN
        // COMPOUND
        // VARIABNT
    
    // REPEATED 
    // {3} times 
    // {1,3} times
    // {1,N} times
    // SET [ no order matching ]
    // VECTOR [ ordered matching ]
    // search for fuzzy pattern
    
    // SELECT SET OF STRINGS
    // SELECT SEQUENCES WHERE STRING
    // SELECT WITHIN STRING WHERE (condition)
    
    // MERGE
    // SPLIT
    // CAPTURE
    // INSERT
    // SEARCH
    // MATCH
    
    enum type { atom, compound, variant }
    class node {
        
    }
    
     static public void main(String[] args){
     
         Object dx = new String[] { "qsd", "qsdqsd"};
         System.out.println(dx.getClass().getCanonicalName().equalsIgnoreCase("java.lang.String[]"));
      
     }
     
     
     // SELECT SOURCE : FileDrive, Database, Server
     // SUB_SELECT : colomns, rows, folders, files, subwebsites
    
}
