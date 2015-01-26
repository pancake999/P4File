/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P3File;

/**
 *
 * @author ARISTOCRAT
 */
public class FileBLL {
    
    //private Integer FileId;
    //private boolean Access; // Private - Public
    //private Integer UserId - Toch niet, meerdere users hebben access tot bestand
    private Integer FileId;
    private String  Filename;
    private String  Filetype;
    private Integer FileSize;
    private byte[]  File;

    public FileBLL() {
        
    }
    public FileBLL(String Filename, String Filetype, Integer Size, byte[] Data){
        this.Filename = Filename;
        this.Filetype = Filetype;
        this.FileSize = Size;
        this.File = Data;
    } 
   
    public String getFilename() {
        return Filename;
    }

    public void setFilename(String Filename) {
        this.Filename = Filename;
    }

    public String getFiletype() {
        return Filetype;
    }

    public void setFiletype(String Filetype) {
        this.Filetype = Filetype;
    }

    public Integer getSize() {
        return FileSize;
    }

    public void setSize(Integer Size) {
        this.FileSize = Size;
    }

    public byte[] getData() {
        return File;
    }

    public void setData(byte[] Data) {
        this.File = Data;
    }

    public Integer getFileId() {
        return FileId;
    }

    public void setFileId(Integer FileId) {
        this.FileId = FileId;
    }
    
    
}
