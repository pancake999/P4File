/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2Access;

/**
 *
 * @author ARISTOCRAT
 */
public class AccessBLL {
    
    private Integer AccessId;
    private Integer UserId;
    private Integer FileId;

    public AccessBLL(Integer UserId, Integer FileId){
        this.UserId = UserId;
        this.FileId = FileId;
    }
    
    public AccessBLL(){};
    public Integer getAccessId() {
        return AccessId;
    }

    public void setAccessId(Integer AccessId) {
        this.AccessId = AccessId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getFileId() {
        return FileId;
    }

    public void setFileId(Integer FileId) {
        this.FileId = FileId;
    }

}
