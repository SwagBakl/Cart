package Content.entity;

import javax.persistence.*;

@Entity
@Table(name = "homeTheaterChars")
public class HomeTheaterChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "homeTheater_id")
    private HomeTheater homeTheater;

    public HomeTheaterChars() {
    }

    public HomeTheaterChars(String name, String value, HomeTheater homeTheater) {
        this.name = name;
        this.value = value;
        this.homeTheater = homeTheater;
    }

    public HomeTheater getHomeTheater() {
        return homeTheater;
    }

    public void setHomeTheater(HomeTheater homeTheater) {
        this.homeTheater = homeTheater;
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
