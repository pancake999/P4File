<%-- 
    Document   : newjsp
    Created on : Jan 12, 2015, 4:38:01 AM
    Author     : ARISTOCRAT
--%>


<%@page import="P5MX.Commands"%>
<%@page import="P1User.UserBLL"%>
<%@page import="P1User.UserDAL"%>
<%@page import="P3File.FileDAL"%>
<%@page import="P3File.FileDAL.*"%>
<%@page import="P3File.FileBLL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>       
        <script src="js/jquery-2.1.1.js" type="text/javascript"></script>
    </head>
    <body>
    
    
    <h1> LOGIN PART </h1>
    
    
        <script>
 
        function select_file_tags(){
            $('#file_tags').empty();
            
                $.get(  'CommandEngine',                  // Servlet
                        { 
                            command : 'select_file_tags',
                            FileId : $('#user_files').val()
                        },                               // JSON data
                        function(data){ 
                            
                            for ( var key in data ){     
                                for ( var yek in data[key] ){     
                                   
                                    $('#file_tags').append(
                                            $("<option></option>")
                                            .attr("value", data[key][yek]['tagId'] )
                                            .text(data[key][yek]['tagId'] + data[key][yek]['tag']));
                              }
                            }
                        }  // Callback      
                );
        }
        
        function select_file_users(){
            $('#file_users').empty();
            
                $.get(  'CommandEngine',                  // Servlet
                        { 
                            command : 'select_file_users',
                            FileId : $('#user_files').val(),
                            UserId : $('#users').val()
                        },                               // JSON data
                        function(data){ 
                            
                            for ( var key in data ){     
                                for ( var yek in data[key] ){     
                                   
                                    $('#file_users').append(
                                            $("<option></option>")
                                            .attr("value", data[key][yek]['userId'] )
                                            .text(data[key][yek]['userName']));

                              }
                            }
                        }  // Callback      
                );
        }
        
        function select_users_by_access(){
            $('#access_users').empty();
            
                $.get(  'CommandEngine',                  // Servlet
                        { 
                            command : 'select_users_by_access',
                            FileId : $('#user_files').val()
                        },                               // JSON data
                        function(data){ 
                            
                            for ( var key in data ){     
                                for ( var yek in data[key] ){     
                                   
                                    $('#access_users').append(
                                            $("<option></option>")
                                            .attr("value", data[key][yek]['userId'] )
                                            .text(data[key][yek]['userName']));

                              }
                            }
                        }  // Callback      
                );
        }
        
        function select_user_files (){     
                
                $('#user_files').empty();
            
                $.get(  'CommandEngine',                  // Servlet
                        { 
                            command : 'select_user_files',
                            UserId : $('#users').val()
                      
                        },                               // JSON data
                        function(data){ 
                            
                            for ( var key in data ){     
                                for ( var yek in data[key] ){     
                                   
                                    $('#user_files').append(
                                            $("<option></option>")
                                            .attr("value", data[key][yek]['fileId'] )
                                            .text(data[key][yek]['filename'] + '.' + data[key][yek]['filetype']));

                              }
                            }
                            
                            select_file_tags();
                            select_file_users();
                        }  // Callback
                                
                );
        }
     
        function select_user_tags(){
            $('#user_tags').empty();
            
                $.get(  'CommandEngine',                  // Servlet
                        { 
                            command : 'select_user_tags',
                            UserId : $('#users').val()
                        },                               // JSON data
                        function(data){ 
                            
                            for ( var key in data ){     
                                for ( var yek in data[key] ){     
                                   
                                    $('#user_tags').append(
                                            $("<option></option>")
                                            .attr("value", data[key][yek]['tagId'] )
                                            .text(data[key][yek]['tag']));

                              }
                            }
                        }  // Callback      
                );
        }
                 
        function select_users(){
            
            $('#users').empty();
            
            $.get(  'CommandEngine',                  // Servlet
                    { 
                        command : 'select_users'
                    },                               // JSON data
                    function(data){ 

                        for ( var key in data ){     
                            for ( var yek in data[key] ){    

                                $('#users').append(
                                         $("<option></option>")
                                        .attr("value", data[key][yek]['userId'] )
                                        .text(data[key][yek]['userName']));
                            }
                        }

                        select_user_files();
                        select_user_tags();
                        
                        
                        $('input[name="UserId"]').val($('#users').val());
                   }  // Callback    

            );
        }
        
        function trigger_users(){
            select_user_files();
            select_user_tags();
            
            $('input[name="UserId"]').val($('#users').val());
        }
        
        function trigger_files(){
            select_file_tags();
            select_file_users();
            select_users_by_access();
            
            $('input[name="UserId"]').val($('#users').val());
            $('input[name="FileId"]').val($('#user_files').val());
            
        }
        
        select_users();
        
        function delete_file(){
            $.get(  'CommandEngine',                  // Servlet
                    { 
                        command : 'delete_file',
                        FileId : $('#user_files').val()
                    },                               // JSON data
                    function(data){ 
                    }
                 );  
         
         
            $('user_files').empty();
            trigger_users();
        };
        
        function add_tag(){
            $.get(  'CommandEngine',                  // Servlet
                    { 
                        command : 'add_tag',
                        FileId : $('#user_files').val(),
                        Tag :  $('input[name="tbtag"]').val()
                    },                               // JSON data
                    function(data){ 
                        trigger_files();
                    }
                );
        };
     
        function remove_tag(){
            $.get(  'CommandEngine',                  // Servlet
                    { 
                        command : 'remove_tag',
                        TagId : $('#file_tags').val()
                    },                               // JSON data
                    function(data){ 
                        trigger_files();
                    }
                );
        };
        
        function grant_access(){
            $.get(  'CommandEngine',                  // Servlet
                    { 
                        command : 'grant_access',
                        UserId : $('#access_users').val(),
                        FileId : $('#user_files').val()     
                    },                               // JSON data
                    function(data){ 
                        trigger_files();
                    }
                );
        };
          
        function revoke_access(){
            $.get(  'CommandEngine',                  // Servlet
                    { 
                        command : 'revoke_access',
                        UserId : $('#file_users').val(),
                        FileId : $('#user_files').val()
                    },                               // JSON data
                    function(data){ 
                        trigger_files();
                    }
                );
        };
       
        
        </script>
        

    <div class="ui-widget">
        USER :: <select id="users" onchange="trigger_users()"></select>
        >>= <input type="text" id="cli_input" autocomplete="off"  style="width: 500px; height: 15px;" >
    </div>
    
    <div id="cli_output"></div>
    <div> 
        CC >>: <select id="user_tags"></select>
        USER FILES >>: <select id="user_files" onchange="trigger_files()"></select> 
        TAGS >>: <select id="file_tags"></select>
        
        <br><button id="delete_file" onclick="delete_file()">Delete File</button>

            <form action="CommandEngine" method="get" enctype="multipart/form-data" >
                <input type="hidden" name="command" value="download_file">
                <input type="hidden" name="FileId" value="" >
                <button type="submit" >Download File</button>
            </form>
    
        <form action="CommandEngine" method="post" enctype="multipart/form-data" >
            <input type="file" name="file" required/>
            <input type="text" name="UserId" value="" readonly >
            <button type="submit" value="upload_file" >Upload File</button>
        </form>
    </div>
    <br>
    
 
    
    <div>
        GRANT ACCESS
        <select id="access_users"></select>
        <button onclick="grant_access()">GRANT</button>
    </div>
    <div>
        REVOKE ACCESS
        <select id="file_users"></select>
        <button onclick="revoke_access()">REVOKE</button>
    </div><br>
    <div>
        NEW TAG
        <input type="text" value="qsd" name="tbtag">
        <button onclick="add_tag()">ADD TAG</button>
    </div>
    <div> 
        DELETE TAG
        
        <button onclick="remove_tag()">DELETE TAG</button>
    </div><br>
    
   
    
    
 
    <script>
        $('#cli_input').keydown(function(){

                    $('#cli_output').empty();

                    if($('#cli_input').val()){    
                        $.get(  'CommandEngine',                  // Servlet
                                { 
                                    command : 'cli'
                                    UserId : $('#users').val(),
                                    term : $('#cli_input').val()
                                },             // JSON data
                                function(data){ array = data; }  // Callback      
                        );

                        for ( var key in array ){     
                            $('#cli_output').append('<div>' + array[key] + "</div>");
                        }
                    }
                    else {
                        $('#cli_output').append('<div>' + "Clear" + "</div>");
                    }
             });
    </script>
    </body>
</html>
