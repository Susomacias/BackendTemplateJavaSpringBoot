package net.purocodigo.backendcursojava.shared.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private List<PostDto> posts;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getuserId() {
        return this.userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }
    

    public String getfirstName() {
        return this.firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return this.lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getemail() {
        return this.email;
    }

    public void setemail(String email) {
        this.email = email;
    }
    
    public String getpassword() {
        return this.password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getencryptedPassword() {
        return this.encryptedPassword;
    }

    public void setencryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<PostDto> getPosts() {
        return this.posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }
    
    
}
