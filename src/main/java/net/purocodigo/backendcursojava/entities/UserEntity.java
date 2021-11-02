package net.purocodigo.backendcursojava.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "users")
@Table(indexes = {
    @Index(columnList = "userId", name = "index_userid", unique = true),
    @Index(columnList = "email", name = "index_email", unique = true),
})

public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private long id;

    @Column (nullable = false)
    private String userId;

    @Column (nullable = false, length = 50)
    private String firstName;

    @Column (nullable = false , length = 50)
    private String lastName;

    @Column (nullable = false, length = 255)
    private String email;

    @Column (nullable = false)
    private String encryptedPassword;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PostEntity> post = new ArrayList<>();


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

    public String getencryptedPassword() {
        return this.encryptedPassword;
    }

    public void setencryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<PostEntity> getPost() {
        return this.post;
    }

    public void setPost(List<PostEntity> post) {
        this.post = post;
    }
}
