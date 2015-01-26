/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ARISTOCRAT
 */
public class coreDB {
    
        enum type { source, column, record, insert, delete, set, query };
    
        private String url;
        private String username;
        private String password;
        
        private HashMap<type, String> set = new HashMap();
               
        public coreDB(String url, String username, String password){
            this.url = url;
            this.username = username;
            this.password = password;
        }
        
        ////// SELECT
        public coreDB source  (String source){
            set.put(type.source, source );
            return this;
        }
        
        public coreDB column  (String column){
            set.put(type.column, column );
            return this;
        }
        
        public coreDB record  (String record){
            set.put(type.record, record );
            return this;
        }
        
        /////// APPLY
        public void insert(String sql){
            String sqlstmt = "";
            
            sqlstmt += "INSERT INTO " + set.get(type.source);
            
                if ( set.containsKey(type.column)){
                    sqlstmt += "(" + set.get(type.column) + ")";
                }
            
            sqlstmt += " VALUES (" + sql + ")";
            
            execute(sqlstmt);
        }
        
        public void delete(){     
            String sqlstmt = "";
            
            sqlstmt += "DELETE FROM " + set.get(type.source);
            sqlstmt += " WHERE " + set.get(type.record);
            
            execute(sqlstmt);
        }
         
        public void set(String sql){
            String sqlstmt = "";
            
            sqlstmt += " UPDATE " + set.get(type.source);
            sqlstmt +=    " SET " + sql;
            sqlstmt +=  " WHERE " + set.get(type.record);
            
            execute(sqlstmt);
        }
        
        public void query(ArrayList<String> values){
            
            String sqlstmt = "";
        
            sqlstmt += "SELECT ";
            
            if ( set.containsKey(type.column)){
                sqlstmt += set.get(type.column);
            }
            else {
                sqlstmt += " * ";
            }
            sqlstmt += " FROM " + set.get(type.source);

            if ( set.containsKey(type.record)){
                sqlstmt += " WHERE " + set.get(type.record);
            }
             
            System.out.println(sqlstmt);
            
            try {
                    Connection conn = DriverManager.getConnection(url, username, password);
                    PreparedStatement stmt = conn.prepareStatement(sqlstmt);

                    ResultSet rs = stmt.executeQuery();

                    while ( rs.next() ){
                        
                        int index = rs.getMetaData().getColumnCount();
                        
                        for(int dx = 1; dx <= index; dx++){
                            values.add(rs.getString(dx));
                        }
                    }
            }
            catch(SQLException  ex){
                System.out.println(ex.getMessage());
            }
        }
        
        public void execute(String sql){
            
            System.out.println(sql);
            
            try {
                Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement stmt = conn.prepareStatement(sql);
                
                int rowsaffected = stmt.executeUpdate();
                
               if ( rowsaffected > 0 ){
                   System.out.println("Succesfull.");
               }
               else {
                   System.out.println("Failure.");
               }
            }
            catch(SQLException  ex){
                System.out.println(ex.getMessage());
            }
            
        }


        public static void main(String[] args){
             coreDB $ = new coreDB("jdbc:mysql://localhost:3307/storagedb", "root", "usbw");
          
            ArrayList<String> dx = new ArrayList<>();
            $.source("fsrepo")
             .column("file")
             .record("filename='Hellodxx'")
             .query(dx);

            for( String sx : dx ){
                System.out.println(sx);
            }
        }

}
