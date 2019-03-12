package Content.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "fridgeChars")
public class FridgeChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;

    private String value;

    @ManyToOne
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    public FridgeChars() {
    }

    public FridgeChars(String name, String value, Fridge fridge) {
        this.name = name;
        this.value = value;
        this.fridge = fridge;
    }

    public Fridge getFridge() {
        return fridge;
    }

    public void setTab(Fridge fridge) {
        this.fridge = fridge;
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
