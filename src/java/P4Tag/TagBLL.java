/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P4Tag;

/**
 *
 * @author ARISTOCRAT
 */
public class TagBLL {
    
    private Integer TagId;
    private Integer FileId;
    private String  Tag;
    
    
    public TagBLL(){
        
    }
    public TagBLL(Integer FileId, String Tag){
        this.FileId = FileId;
        this.Tag = Tag;
    }
    
    public Integer getFileId() {
        return FileId;
    }

    public void setFileId(Integer FileId) {
        this.FileId = FileId;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    public Integer getTagId() {
        return TagId;
    }

    public void setTagId(Integer TagId) {
        this.TagId = TagId;
    }

   



}
