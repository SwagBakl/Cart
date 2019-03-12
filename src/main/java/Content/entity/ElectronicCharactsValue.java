package Content.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "electCharValue")
public class ElectronicCharactsValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "* Заполните поле.")
    private String charValue;

    public ElectronicCharactsValue(){}

    public ElectronicCharactsValue(String charValue){
        this.charValue = charValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharValue() {
        return charValue;
    }

    public void setCharValue(String charValue) {
        this.charValue = charValue;
    }
}
