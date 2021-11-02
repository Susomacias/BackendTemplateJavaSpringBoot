package net.purocodigo.backendcursojava.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="exposures")
public class ExposureEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 50)
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exposure")
    private List<PostEntity> post = new ArrayList<>();


    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public List<PostEntity> getPost() {
        return this.post;
    }

    public void setPost(List<PostEntity> post) {
        this.post = post;
    }
}
