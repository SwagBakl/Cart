package Content.entity;


import javax.persistence.*;

@Entity
public class Pc {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String model;
    private Integer price;
    private Long charact_id;
    private String filename;



    public Pc(){}

    public Pc(String model, Integer price){
        this.model = model;
        this.price = price;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getCharact_id() {
        return charact_id;
    }

    public void setCharact_id(Long charact_id) {
        this.charact_id = charact_id;
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
