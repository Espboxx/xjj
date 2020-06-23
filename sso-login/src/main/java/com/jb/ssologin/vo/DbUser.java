package com.jb.ssologin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

/**
 * (DbUser)实体类
 *
 * @author makejava
 * @since 2020-06-22 20:48:27
 */
@AllArgsConstructor
@NoArgsConstructor
public class DbUser implements Serializable {
    private static final long serialVersionUID = 233600705340953470L;
    /**
    * 用户id
    */
    private Integer id;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 密码
    */
    private String passWord;
    /**
    * 用户昵称
    */
    private String nikeName;


    public DbUser(String userName,String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    @Override
    public String toString() {
        return "DbUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", nikeName='" + nikeName + '\'' +
                '}';
    }
}