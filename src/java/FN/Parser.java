package FN;



import Functionality.coreFS;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ARISTOCRAT
 */
public class Parser {
    
    static enum             atom_type { comment, number, lex, flex, compute, key, opener, appender, closer, _import, _export  };
    
    static class            atom {
        public atom_type type;
        public String data;
        
        public atom(atom_type type, String data){
            this.type = type;
            this.data = data;
        }
            
    }
    
    static class            exp {
        String alias;
        ArrayList<atom_type> type;
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
    
    static void             execute_exp     (){
        
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
            atoms.add(new atom(atom_type.compute,   "(\\.[A-Za-z][A-Za-z0-9]*)"));
            atoms.add(new atom(atom_type.key ,      "([A-Za-z][A-Za-z0-9]*)"));
            atoms.add(new atom(atom_type.opener,    "(\\[)"));
            atoms.add(new atom(atom_type.appender,  "(,)"));
            atoms.add(new atom(atom_type.closer,    "(\\])"));
            atoms.add(new atom(atom_type._import,   "(::)"));
            atoms.add(new atom(atom_type._export,   "(>>)"));
        
        ArrayList<atom_type> expression = new ArrayList<>();
            expression.add(atom_type.compute);
            expression.add(atom_type._import);
            expression.add(atom_type.number);
            expression.add(atom_type.appender);
            expression.add(atom_type.number);
            expression.add(atom_type._export);
            expression.add(atom_type.key);
            
        ArrayList<atom> clc = new ArrayList<>();
            
        Integer dx_ops = 0;
        Integer dx_r1  = 2;
        Integer dx_r2  = 4;
        Integer dx_r3  = 6;
  
        while(true){
            
            //// capture input
            System.out.print(" CLI >>: ");
            Scanner reader = new Scanner(System.in);
            String sx = reader.nextLine();
            
            //// decompose input 
            System.out.println(sx);
            clc.clear();
           // clc.addAll(lexer(atoms, sx));
            
            //// output chunks
            for (atom clc1 : clc) {
                System.out.println("[ " + clc1.type + " :: " + clc1.data + " ]");
            }
            
            //// perform something
                 if ( clc.get(dx_ops).data.equalsIgnoreCase(".exit")){
                break;
            }
            else if ( compare_exp(clc, expression)){
                 
                    if ( clc.get(dx_ops).data.equalsIgnoreCase(".add") ){

                   Integer r1 = Integer.valueOf(clc.get(dx_r1).data);
                   Integer r2 = Integer.valueOf(clc.get(dx_r2).data);

                   System.out.println("[ " + clc.get(dx_r3).data + " :: " + (r1 + r2) + " ]");

               }
               else if ( clc.get(dx_ops).data.equalsIgnoreCase(".sub") ){
                   Integer r1 = Integer.valueOf(clc.get(dx_r1).data);
                   Integer r2 = Integer.valueOf(clc.get(dx_r2).data);

                   System.out.println("[ " + clc.get(dx_r3).data + " :: " + (r1 - r2) + " ]");

               }
               else if ( clc.get(dx_ops).data.equalsIgnoreCase(".div")){
                   Integer r1 = Integer.valueOf(clc.get(dx_r1).data);
                   Integer r2 = Integer.valueOf(clc.get(dx_r2).data);

                   System.out.println("[ " + clc.get(dx_r3).data + " :: " + (r1 / r2) + " ]");

               }
               else if ( clc.get(dx_ops).data.equalsIgnoreCase(".mul")){
                   Integer r1 = Integer.valueOf(clc.get(dx_r1).data);
                   Integer r2 = Integer.valueOf(clc.get(dx_r2).data);

                   System.out.println("[ " + clc.get(dx_r3).data + " :: " + (r1 * r2) + " ]");

               }
               else {
                   System.out.println("Unknown command.");
               }
            }
        }
    }
    
    ////////////////////// MODIFY SYNTAX TO GRAPH
    

    
    
    
    static class core {
        public String block;
        
        public String[] grammar = new String[] {
            "[ :new >> dx dy ]",
            "[ :delete :: dx dy ]",
            "[ xd :data 342 2323 232 12 ]",
            "[ :abstract ]",
            "[ :export ]",
            "[ :import ]",
            "[ :compute ]"
        };
        static void validate(core cx){};
        static void extract(core cx){};
        static void execute(core cx){};
        static void search(core cx){};
    
    
    static void grant_capability(){
            
    }
    static void revoke_capability(){
            
    }
    
    static void search_encapsulation(){
        
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
    
    enum mark_type { atom, group };
    
    static ArrayList<atom> lexer   (ArrayList<atom> atoms, String source ){
        
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
    
    static ArrayList<group_marker> grouper(ArrayList<atom> atoms){
           
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
     
    
    
    
    static class ast {
         
        Stack<ArrayList<group_marker>> groups;
        
        // atoms
        // import start [
        // import done ]
        // export start 
        // group_marker >>
        // group_marker <<
        
        ArrayList<atom> grammar;
        ArrayList<atom> motes;
        int offset = 0;
        
        public          ast         (String source){
            
            //System.out.println(source);
            
             grammar = new ArrayList<>();
                grammar.add(new atom(atom_type.comment,   "(/\\*.*?\\*/)"));   
                grammar.add(new atom(atom_type.flex,      "([0-9]+\\.[0-9]+)"));
                grammar.add(new atom(atom_type.number,    "([0-9]+)"));
                grammar.add(new atom(atom_type.lex,       "(\".*?\")"));
                grammar.add(new atom(atom_type.compute,   "(\\.[A-Za-z][A-Za-z0-9]*)"));
                grammar.add(new atom(atom_type.key ,      "([A-Za-z][A-Za-z0-9]*)"));
                grammar.add(new atom(atom_type.opener,    "(\\[)"));
                grammar.add(new atom(atom_type.appender,  "(,)"));
                grammar.add(new atom(atom_type.closer,    "(\\])"));
                grammar.add(new atom(atom_type._import,   "(::)"));
                grammar.add(new atom(atom_type._export,   "(>>)"));
                
            motes = new ArrayList<>();
            groups = new Stack<>();
            
            decompose(source);
                /*System.out.println(motes.size());
               for( atom mx : motes){
                   System.out.println( "[ " + mx.type + " :: " + mx.pattern + " ]");
               }*/
                
            
            
        }  
        
        public void     decompose   (String source){
            motes = lexer(grammar, source);
            groups.push(grouper(motes));
        }
        
        public String   compose     (){
            
            return "";
        }
        
        public void     search(){
            
        }
        
        public void     progress    (Integer index){
            
            index -= 1;
            
            if ( (groups.peek().size() > index) ){
                if ( groups.peek().get(index).mkt == mark_type.group ){
                    
                    ArrayList<atom> subgroup = new ArrayList<>();
                    
                    int head_index = groups.peek().get(index).head_index;
                    int tail_index = groups.peek().get(index).tail_index;
                
                //System.out.println("[ head " + head_index + " ]");
                //System.out.println("[ tail " + tail_index + " ]");
                
                    
                    for( int ax = head_index +1; ax < tail_index; ax++){
                    subgroup.add(motes.get(ax));
                }
                    offset += head_index +1;
                    groups.push( grouper(subgroup));
                }
            }
        }
        
        public void     regress     (){
            
            if ( groups.size() > 1){
                groups.pop();
                offset = groups.peek().get(0).head_index;
            }
            
        }
        
        public void     declare_atom(String element){

        }
        
        public void     discard_atom(Integer index){
            
        }
        
        public void     declare_group(){
            
        }
        
        public void     discard_group(){
            
        }
        
        public void     validate_group(){
            
        }
        
        public void     display(){
            
            
            System.out.println("--------------- GROUP // ----------------");
            
            for( int dx = 0; dx < groups.peek().size(); dx++){
                
                int head_index = groups.peek().get(dx).head_index;
                int tail_index = groups.peek().get(dx).tail_index;
                
                //System.out.println("[ head " + head_index + " ]");
                //System.out.println("[ tail " + tail_index + " ]");
                System.out.print("[ " + groups.peek().get(dx).mkt + " :: ");
                for( int ax = head_index + 1; ax < tail_index; ax++){
                    System.out.print(motes.get(offset +ax ).data);
                }
                System.out.print(" ]");
                System.out.println();
            }
            
            System.out.println("--------------- GROUP \\ ----------------");
        }
        
    }
    
    

    static void garbage(){
     
            
            
            // relation
        String declare      = ".declare             >> :x";
        String transform    = ".transform   :x :34 >> :y";
        String discard      = ".discard :x :y";
        String data         = "_ x .integer :342 :15 :2353 :3423 :234 :109";
        
        // [ searchbar :: --------------------- ]
    }
    
    static public void main(String[] args){
        
       
       
        ast exp = new ast(new String(coreFS.load("C:\\Users\\ARISTOCRAT\\Desktop\\GITHUB.SYNC\\HibernateApp\\Code\\PROTOTYPE\\src\\java\\FN\\Directory.txt")));
         
        
         exp.display();
         exp.progress(3);
         exp.display();
         exp.regress();
         exp.display();
         // retrieve internal sub_rows
         // output array of elements[]
         
         
         //////////////////////////////////////////////////////////////
         //////////////////////////////////////////////////////////////
         // progress - regress node
         // substring stack
        
        // get all elements in root
        // add node
        // remove node
        // select node
        // user circles, file 
    }
}
