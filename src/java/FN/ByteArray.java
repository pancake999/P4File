package FN;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Functionality.search;
import java.util.ArrayList;

/**
 *
 * @author ARISTOCRAT
 */
public class ByteArray {
    
    /////////////////////// BASE DATATYPES
    static class abstract_node { 
        String data;
        ArrayList<abstract_node> _import;
        ArrayList<abstract_node> _export;
        
    };
    
    static class abstract_graph {
        
        abstract_node alpha = new abstract_node();
        abstract_node selected_node = alpha;
        
        public abstract_graph(){
            
        }
        
        public void new_node(){
            
        }
        
        public void delete_node(){
            
        }
        
        public void select_import_node(){
            
        }
        
        public void select_export_node(){
            
        }
    }
 
   
    //////////////////////  MODIFY GRAPH
    
    static void             manipulate_delete   (abstract_graph ag) {
        
    }
    
    static void             manipulate_new      (abstract_graph ag){
        
    }
    
    //////////////////////  SEARCH(edit to SELECT) THROUGH GRAPH
                                                 /* pattern of nodes, graph */
   
    
    
    ////////////////////// COMPUTE GRAPH
    
    static boolean compute(abstract_graph ag){ return true; }
   
    
    
   
    
        // unique id + alias
    static class node {
        public String     clc; // [import] -clc-> [export]
        public ArrayList<node> _import;
        public ArrayList<node> _export;
        
        public node(String clc){
            this.clc = clc;
        }
        
        void _in(String ex){
            
                    
                    
        }
        
    }
    
    static class graph {
        
        public void D( String object, String clc, String subject){
            
            node nx = new node(clc);
                nx._import.add(new node(object));
                nx._export.add(new node(subject));
                
            graph.add(nx);
        }
        
        public void $(String key, String relation){
            for(int dx = 0; dx < graph.size(); dx++){
              
            }
        }
        
        public ArrayList<node> graph;
    }
    
        enum type { and, or };
    static void parser(String source){
        // #signature: id ] class [ #data: [ sdf .sdfsdf .sdfsf ] ]
        // x :int [ .234 ]
        // 32 - 127
        // START CHARACTER
        // ENDING CHARACTER
        // AND[XYZ] X AMOUNT OF TIMES
        // OR[XYZ]
        /*
            OR[X<1>Y<1>Z<1>]
        */
        
        class rule {
           public type tx; 
           public String atoms;
           public int times;
           public int error_rate;
        }
        
        // 3 or 4 or 6 times
        // any amount of times
        // REGEX + capturing + recursive + error = RII
        // naming rules isEmail = a{3}
        // d{3-31}
        // capture & bind [pattern] to key
        // perform action on [pattern]
        
        // abstract [pattern]
        // what to do when engine encounters [pattern]
            // bind to variable
            // record position
            // perform action on it
            // substitute
            // delete
            // return (found!)
        ArrayList<rule> llx = new ArrayList<>();
                rule temp = new rule();
                temp.tx = type.and;
                temp.atoms = "xyz";
            llx.add(temp);
    }
    
    enum fabric { _continue, _open, _close, _openclose };
    static void execution_engine(){
        
        
        
        class sequence { 
            
            class chain {
                public String data;
                public fabric fx;
            }
            
            public fabric fx;
            public chain cx;
        }
        
        
        // if statements [ pattern ] [ execute ]
        // [ atom , sub 
    }
    
    static class abstract_clc {
        
        public ArrayList<String[]> clc = new ArrayList<>();
        
        public void new_block(String[] block){
            if ( block.length > 1 ) clc.add(block);
        }
        
        public void display(){
            for(int dx = 0; dx < clc.size(); dx++){
                out(clc.get(dx));
            }
        }
        public void out(String[] values){
            System.out.print("[" + values[0] + " :: " + values[1] + " :: ");       
            for(int idx = 2; idx < values.length -1; idx++) System.out.print(values[idx] + ",");       
            if ( values.length > 1 ) System.out.println(values[values.length-1] + " ]");
            
        }
    }
    
    
    
    
    
    
    
    public static void main(String[] args) {
        
        /*graph gx = new graph();
            gx.D("dx", ":type", "num");
            gx.D("dx", ":value", "342");
            gx.D("void", ":declare", "dx");
            gx.D("dx", ":discard", "void");
            gx.D("dx", ":represents", "hello world!");
            
        String[] coll = new String [] { "qsdsq", "qsdsqd", "dfsf" };
            node dx = new node("main");
        
            */
        
        /*abstract_clc clc = new abstract_clc();
            clc.new_block( new String[] { "block_id", "rex", "elementA", "elementB" } );
            clc.display();
        
            
       Object dx = new Object();
       
        
        abstract_clc dy = new abstract_clc();
        dx = dy;
       System.out.println(dx.getClass());*/
        
        ArrayList<String> atoms = new ArrayList<>();
            atoms.add("tom");
            atoms.add("voetbal");
            atoms.add("pes2010");
            atoms.add("apes");
        ArrayList<String> farray = search.fuzzy("pesqsd", atoms);
        
        for(int dx= 0; dx < farray.size(); dx++){
            System.out.println(farray.get(dx));
        }
       

     }
}
