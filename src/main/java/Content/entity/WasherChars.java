package Content.entity;

import javax.persistence.*;

@Entity
@Table(name = "washerChars")
public class WasherChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String value;

    @ManyToOne
    @JoinColumn(name = "washer_id")
    private Washer washer;

    public WasherChars(){}

    public WasherChars(String name, String value, Washer washer){
        this.name = name;
        this.value = value;
        this.washer = washer;
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

    public Washer getWasher() {
        return washer;
    }

    public void setWasher(Washer washer) {
        this.washer = washer;
    }
}
