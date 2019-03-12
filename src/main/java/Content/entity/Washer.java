package Content.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "washer")
public class Washer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "* Заполните поле.")
    private String model;

    @NotNull(message = "* Заполните поле.")
    private Double price;

    private String filename;

    @NotNull(message = "* Заполните поле.")
    private Integer quantity;

    @NotBlank(message = "* Заполните поле.")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "washer")
    private List<WasherChars> washerChars = new ArrayList<>();

    public Washer(){}

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WasherChars> getWasherChars() {
        return washerChars;
    }

    public void setWasherChars(List<WasherChars> washerChars) {
        this.washerChars = washerChars;
    }
}
