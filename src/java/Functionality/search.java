/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

import java.util.ArrayList;

/**
 *
 * @author ARISTOCRAT
 */


public class search {
    
    public static ArrayList<String> sub         (String key, ArrayList<String> set){
            
            ArrayList<String> match_set = new ArrayList<>();

            for(int set_index = 0; set_index < set.size(); set_index++) {                    
                    if (key.length() <= set.get(set_index).length()){
                        if ( key.equalsIgnoreCase(set.get(set_index).substring(0, key.length()))){
                            match_set.add(set.get(set_index));
                        }
                    }
            }
            
            return match_set;
        }
        
    public static ArrayList<String> partial     (String key, ArrayList<String> set){

        ArrayList<String> match_set = new ArrayList<>();

        for(int set_index = 0; set_index < set.size(); set_index++) { 	                 
                if (key.length() <= set.get(set_index).length()){          
                    for(int dx = 0; dx < (set.get(set_index).length() - key.length()+1); dx++ ){
                        if ( key.equalsIgnoreCase(set.get(set_index).substring(dx, dx + key.length()))){
                            match_set.add(set.get(set_index));
                            break;
                        }
                    }
                }
        }

        return match_set;
    }

    public static ArrayList<String> strict      (String key, ArrayList<String> set){

        ArrayList<String> match_set = new ArrayList<>();

        for(int dx = 0; dx < set.size(); dx++){
           if ( key.equalsIgnoreCase(set.get(dx))) match_set.add(set.get(dx));
        }

        return match_set;
    }

    public static ArrayList<String> fuzzy       (String key, ArrayList<String> set){

        // Liechtenstein Table
        // ranking
        // heuristics

        class dex {
            public int distance;
            public int set_index;

            dex(int distance, int set_index){
                this.distance = distance;
                this.set_index = set_index;
            }
        }

        ArrayList<String> match_set = new ArrayList<>();
        ArrayList<dex> indices = new ArrayList<>();

        // fill dex array
        for(int set_index = 0; set_index < set.size(); set_index++){
            if ( key.length() <= set.get(set_index).length()){

                int distance = key.length() + 1;
                int dtemp;        
                for(int dx = 0; dx < (set.get(set_index).length() - key.length()+1); dx++ ){

                    dtemp = edit_distance(key, set.get(set_index).substring(dx, dx + key.length()));

                    if ( dtemp < distance ) distance = dtemp;
                    //if ( distance == 0 ) { break;}
                }

                if ( distance < (key.length())) indices.add(new dex ( distance, set_index ));
            }

        }


        if ( indices.isEmpty() ) return match_set;
        else if ( indices.size() == 1 ) {


            match_set.add(set.get(indices.get(0).set_index));
            return match_set;
        }
        else {
        
        // sort array
        for(int dx = 0; dx < indices.size() - 1;){
            if (indices.get(dx).distance > indices.get(dx + 1).distance){
                dex temp = new dex(indices.get(dx).distance, indices.get(dx).set_index);
                indices.get(dx).distance = indices.get(dx + 1).distance;
                indices.get(dx).set_index = indices.get(dx + 1).set_index;
                indices.get(dx + 1).distance  = temp.distance;
                indices.get(dx + 1).set_index = temp.set_index;

                if ( dx != 0 ) dx--;
            }
            else
            {
                dx++;
            }
        }

        // add to match_set
        for(int dx = 0; dx < indices.size(); dx++){
            
            match_set.add(set.get(indices.get(dx).set_index));
        }

        return match_set;
        }

    }

    static int               edit_distance      (String lexA, String lexB) {

        // degenerate cases
        if ( lexA.compareToIgnoreCase(lexB) == 0 )      return 0;
        if ( lexA.length() == 0 || lexB.length() == 0 ) return lexB.length();

        // create two work vectors of integer distances
        int[] v0 = new int[lexB.length() + 1];
        int[] v1 = new int[lexB.length() + 1];

        for ( int i = 0; i < v0.length; i++ ) v0[i] = i;

        // calculate distance
        for (int i = 0; i < lexA.length(); i++){
            v1[0] = i + 1;
            for (int j = 0; j < lexB.length(); j++)
            {
                int cost = (lexA.charAt(i) == lexB.charAt(j)) ? 0 : 1; 

                boolean conditionDELETE     = (v1[j] + 1) <= (v0[j + 1] + 1) && (v1[j] + 1) <= (v0[j] + cost);
                boolean conditionINSERT     = (v0[j + 1] + 1) <= (v1[j] + 1) && (v0[j + 1] + 1) <= (v0[j] + cost);
                boolean conditionSUBSTITUTE = (conditionDELETE != true && conditionINSERT != true);

                if ( conditionDELETE     ) v1[j + 1] = v1[j] + 1;
                if ( conditionINSERT     ) v1[j + 1] = v0[j + 1] + 1;
                if ( conditionSUBSTITUTE ) v1[j + 1] = v0[j] + cost;
            }

            System.arraycopy(v1, 0, v0, 0, v0.length);
        }

        return v1[lexB.length()];
    }

    static void              out                (ArrayList<String> set){
        for(int idx = 0; idx < set.size(); idx++){
            System.out.println(set.get(idx));
        }
    }
    
    
    
  
    
}

