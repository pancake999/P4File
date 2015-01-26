/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ARISTOCRAT
 */
public class CLI {
    
     static enum             atom_type { comment, number, lex, flex, compute, key, opener, appender, closer, _import, _export  };
     enum mark_type { atom, group };
    
    static class atom {
        public atom_type type;
        public String data;
        
        public atom(atom_type type, String data){
            this.type = type;
            this.data = data;
        }
            
    }
    static class group_marker {
            
            public mark_type mkt;
            public int head_index, tail_index;
            
            public group_marker(mark_type mkt, int head_index, int tail_index){
                this.mkt = mkt;
                this.head_index = head_index;
                this.tail_index = tail_index;
            }
    }
    
    static class            exp {
        String alias;
        ArrayList<atom_type> type;
    }
   
    static ArrayList<String>    symmetric_decompose     (String delimiter, String lex){
        
        //String lex = new String();
        ArrayList<String> segments = new ArrayList<>();
        
        
        int mark_index = 0;
        for(int dx = 0; dx < (lex.length()-delimiter.length()); ){
            if ( delimiter.equalsIgnoreCase(lex.substring(dx, dx + delimiter.length()))  ){
                System.out.println("[ indices :: " + mark_index + " - " + dx + " ]"); // lex.substring(mark_index, dx));
                segments.add(lex.substring(mark_index, dx));
                mark_index = ( dx + delimiter.length());
                dx += delimiter.length();
            }
            else {
                dx++;
            }
        }
        
        if ( mark_index != lex.length()){
            System.out.println("[ indices :: " + mark_index + " - " + lex.length() + " ]"); // lex.substring(mark_index, dx));
            segments.add(lex.substring(mark_index, lex.length()));
        }
       
        return segments;
    }
    
    static ArrayList<String>    assymmetric_decompose   (String delimiter_alpha, String delimiter, String delimiter_omega, String lex){
        
        ArrayList<String> segments = new ArrayList<>();

        
        class node {
            public String data;
            public ArrayList<Integer>   index;
        }
        
        ArrayList<node> root = new ArrayList<>();  
        ArrayList<node> graph = new ArrayList<>();
        int selected_index;
        
        int mark_index = 0;
        int lvl = 0;
        for(int dx = 0; dx < lex.length(); ){
                if( delimiter_alpha.equalsIgnoreCase(lex.substring(dx, dx + delimiter_alpha.length()))  ){
                lvl++;
                mark_index = dx + delimiter_alpha.length();
                
                dx += delimiter_alpha.length();
            }
            else if ( delimiter.equalsIgnoreCase(lex.substring(dx, dx + delimiter.length()))  ){
                
                node nx = new node();
                    nx.data = lex.substring(mark_index, dx-1);
                graph.add(nx);
                
                dx += delimiter.length();
            }
            else if( delimiter_omega.equalsIgnoreCase(lex.substring(dx, dx + delimiter_omega.length()))  ) {
                
                node nx = new node();
                    nx.data = lex.substring(mark_index, dx-1);
                graph.add(nx);
                
                lvl--;
                dx += delimiter_omega.length();
            }
             
        }
        
        return segments;
    }
    
    static ArrayList<atom>          lexer   (ArrayList<atom> atoms, String source ){
        
        ArrayList<atom> segments = new ArrayList<>();
        
        Pattern px;
        Matcher mx;
        Integer source_index = 0;
        
        while(source_index < source.length()){
            
            for(int dx = 0; dx < atoms.size(); dx++ ){
                
                px = Pattern.compile(atoms.get(dx).data + "[. \\s\\S]*");
                mx = px.matcher(source.subSequence(source_index, source.length()));
                
                if ( mx.matches()){
                    
                    segments.add(new atom(atoms.get(dx).type, mx.group(1)));
                    source_index += (mx.group(1).length());
                    source_index -= 1;
                    break;
                }
            }
            
            ++source_index;
        }
        
        return segments;
    }
    
    static ArrayList<group_marker>  grouper (ArrayList<atom> atoms){
           
        ArrayList<group_marker> gm = new ArrayList<>();
        
         int tail_index = 0;
         int head_index = 1;
         int lvl = 1;
         
         
         // find each element inside
         while(lvl > 0){
             if ( atoms.get(head_index).type == atom_type.opener){
                 ++lvl;
             }
             else if ( atoms.get(head_index).type == atom_type.closer){
                 --lvl;
             }
                      
             if (      lvl == 1 && atoms.get(head_index).type == atom_type.appender
                  || ( lvl == 0 && atoms.get(head_index).type == atom_type.closer)){
                 
                 if ( atoms.get(tail_index + 1).type == atom_type.opener){
                     gm.add(new group_marker(mark_type.group, tail_index, head_index));
                 }
                 else {
                     
                     gm.add(new group_marker(mark_type.atom, tail_index, head_index));
                 }
                 
                 //System.out.println("group added");
                 
                 tail_index = head_index;
             }

             ++head_index;
         }
         
         return gm;
    }
   
    
    static boolean          compare_exp     (ArrayList<atom> atoms, ArrayList<atom_type> types){

        if ( atoms.size() != types.size() ) return false;
        
        Integer dx = 0;
        while (  dx < atoms.size() ){
            if ( atoms.get(dx).type != types.get(dx)){
                return false;
            }
            
            ++dx;
        }
        
        return true;
    }
    
        static String             execute_exp     (String term){
        
        /*
            TYPE NUM8 16 32 64
            TYPE FX 16 32 64
            TYPE LEX 
            TYPE CR
            TYPE BOOLEAN
        
            + - / * % ++ -- -= *= += > < <= >= != == = ! || &&
        
         // 2 types  - recognize -> capture
            //          - recognize -> discard
            // atom or composite
            // file or folder
            // nested parser
        
        
            // collection of data
            // collection of compute on that data
        
        dmx :data
            dx :int     34
            dy :string  "Hello World"
            dz :float   34.5
        
        dmx :compute 
            add :: dx 534 -> [dm]
            concat :: dy "ops" -> [result]
            fma :: 
        */
        
        ArrayList<atom> atoms = new ArrayList<>();
            atoms.add(new atom(atom_type.comment,   "(/\\*.*\\*/)"));   
            atoms.add(new atom(atom_type.flex,      "([0-9]+\\.[0-9]+)"));
            atoms.add(new atom(atom_type.number,    "([0-9]+)"));
            atoms.add(new atom(atom_type.lex,       "(\".*\")"));
            atoms.add(new atom(atom_type.compute,   "(:[A-Za-z][A-Za-z0-9]*)"));
           // atoms.add(new atom(atom_type.compute,   "(\\.[A-Za-z][A-Za-z0-9]*)"));
            atoms.add(new atom(atom_type.key ,      "([A-Za-z][A-Za-z0-9]*)"));
            atoms.add(new atom(atom_type.opener,    "(\\[)"));
            atoms.add(new atom(atom_type.appender,  "(,)"));
            atoms.add(new atom(atom_type.closer,    "(\\])"));
            atoms.add(new atom(atom_type._import,   "(::)"));
            atoms.add(new atom(atom_type._export,   "(>>)"));
        
        ArrayList<atom_type> expression = new ArrayList<>();
            expression.add(atom_type.compute);
            //expression.add(atom_type._import);
            expression.add(atom_type.number);
            //expression.add(atom_type.appender);
            expression.add(atom_type.number);
            //expression.add(atom_type._export);
            //expression.add(atom_type.key);
            
        ArrayList<atom> clc = new ArrayList<>();
            
        Integer dx_ops = 0;
        Integer dx_r1  = 1;
        Integer dx_r2  = 2;
  
        
        clc.addAll(lexer(atoms, term));
        /*while(true){
            
            //// capture input
            System.out.print(" CLI >>: ");
            Scanner reader = new Scanner(System.in);
            String sx = reader.nextLine();
            
            //// decompose input 
            System.out.println(sx);
            clc.clear();
            clc.addAll(lexer(atoms, sx));
            
            
            
            //// perform something
            if ( clc.get(dx_ops).data.equalsIgnoreCase(":exit")){
                break;
            }*/
        
        //// output chunks
            for (atom clc1 : clc) {
                System.out.println("[ " + clc1.type + " :: " + clc1.data + " ]");
            }
            
        String output = "";
               if ( compare_exp(clc, expression)){
                 
                    if ( clc.get(dx_ops).data.equalsIgnoreCase(":add") ){

                        Integer r1 = Integer.valueOf(clc.get(dx_r1).data);
                        Integer r2 = Integer.valueOf(clc.get(dx_r2).data);

                        output = "[ " + (r1 + r2) + " ]";

                    }
                    else if ( clc.get(dx_ops).data.equalsIgnoreCase(":sub") ){
                        Integer r1 = Integer.valueOf(clc.get(dx_r1).data);
                        Integer r2 = Integer.valueOf(clc.get(dx_r2).data);

                        output = "[ " + (r1 * r2) + " ]";

                    }
                    else if ( clc.get(dx_ops).data.equalsIgnoreCase(":div")){
                        Integer r1 = Integer.valueOf(clc.get(dx_r1).data);
                        Integer r2 = Integer.valueOf(clc.get(dx_r2).data);

                        output = "[ " + (r1 / r2) + " ]";

                    }
                    else if ( clc.get(dx_ops).data.equalsIgnoreCase(":mul")){
                        Integer r1 = Integer.valueOf(clc.get(dx_r1).data);
                        Integer r2 = Integer.valueOf(clc.get(dx_r2).data);


                        output = "[ " + (r1 * r2) + " ]";

                    }
                    else {
                        output = "Unknown command.";
                    }
            }
        //}
               
        return output;
    }
    
    ////////////////////// MODIFY SYNTAX TO GRAPH
    public static void main(String[] args){
        
        //String dx = "[ Hello [ Hai ]]";
        
        //System.out.println(execute_exp(":add 13 1312"));
        
        
         ArrayList<atom> atoms = new ArrayList<>();
            atoms.add(new atom(atom_type.comment,   "(/\\*.*\\*/)"));   
            atoms.add(new atom(atom_type.flex,      "([0-9]+\\.[0-9]+)"));
            atoms.add(new atom(atom_type.number,    "([0-9]+)"));
            atoms.add(new atom(atom_type.lex,       "(\".*\")"));
            atoms.add(new atom(atom_type.compute,   "(:[A-Za-z][A-Za-z0-9]*)"));
            atoms.add(new atom(atom_type.key ,      "([A-Za-z][A-Za-z0-9]*)"));
            atoms.add(new atom(atom_type.opener,    "(\\[)"));
            atoms.add(new atom(atom_type.appender,  "(,)"));
            atoms.add(new atom(atom_type.closer,    "(\\])"));
            atoms.add(new atom(atom_type._import,   "(::)"));
            atoms.add(new atom(atom_type._export,   "(>>)"));
            
        String dx = " 3423 2423.0 \" Hello World!\"  /* Dit is een comment*/";
        
        ArrayList<atom> segments = lexer(atoms,dx);
        
        for ( atom sx : segments){
            System.out.println("[ " + sx.type + " :: " + sx.data + " ]");
        }
        
        System.out.println(execute_exp(":add 231 23"));
    }
}
