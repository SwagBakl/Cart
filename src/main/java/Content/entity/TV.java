package Content.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tv")
public class TV {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "* Заполните поле.")
    private String model;

    @NotNull(message = "* Заполните поле.")
    private Integer price;

    private String filename;

    @NotNull(message = "* Заполните поле.")
    private Integer quantity;

    @NotBlank(message = "* Заполните поле.")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tv")
    private List<TvChars> characts = new ArrayList<>();

    public TV(){}

    public TV(String model, Integer price, Integer quantity, String description){
        this.model = model;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public List<TvChars> getCharacts() {
        return characts;
    }

    public void setCharacts(List<TvChars> characts) {
        this.characts = characts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
