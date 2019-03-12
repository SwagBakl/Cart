package Content.entity;

import javax.persistence.*;

@Entity
@Table(name = "photoChars")
public class PhotoChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    public PhotoChars() {
    }

    public PhotoChars(String name, String value, Photo photo) {
        this.name = name;
        this.value = value;
        this.photo = photo;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
