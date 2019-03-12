package Content.entity;

import javax.persistence.*;

@Entity
@Table(name = "tvChars")
public class TvChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "tv_id")
    private TV tv;

    public TvChars(){}

    public TvChars(String name, String value, TV tv) {
        this.name = name;
        this.value = value;
        this.tv = tv;
    }

    public TV getTv() {
        return tv;
    }

    public void setTv(TV tv) {
        this.tv = tv;
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
