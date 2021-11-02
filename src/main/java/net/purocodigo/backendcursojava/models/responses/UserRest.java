package net.purocodigo.backendcursojava.models.responses;

import java.util.List;

public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<PostRest> post;


    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuserId() {
        return this.userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public List<PostRest> getPost(){
        return this.post;
    }

    public void setPost(List<PostRest> post) {
        this.post = post;
    }
}
