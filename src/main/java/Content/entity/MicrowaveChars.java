package Content.entity;

import javax.persistence.*;

@Entity
@Table(name = "microwaveChars")
public class MicrowaveChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "micro_id")
    private Microwave microwave;

    public MicrowaveChars(){}

    public MicrowaveChars(String name, String value, Microwave microwave){
        this.name = name;
        this.value = value;
        this.microwave = microwave;
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

    public Microwave getMicrowave() {
        return microwave;
    }

    public void setMicrowave(Microwave microwave) {
        this.microwave = microwave;
    }
}
