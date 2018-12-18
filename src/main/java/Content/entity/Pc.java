package Content.entity;


import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pc {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String model;
    private Integer price;
    private String filename;
    private Integer quantity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pc")
    private List<PcCharacts> characts = new ArrayList<>();

    public Pc(){}

    public Pc(String model, Integer price, Integer quantity){
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    public List<PcCharacts> getCharacts() {
        return characts;
    }

    public void setCharacts(List<PcCharacts> characts) {
        this.characts = characts;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
