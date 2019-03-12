package Content.entity;

import javax.persistence.*;

@Entity
@Table(name = "cellPhoneChars")
public class CellPhoneChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "phone_id")
    private CellPhone cellPhone;

    public CellPhoneChars(){}

    public CellPhoneChars(String name, String value, CellPhone cellPhone) {
        this.name = name;
        this.value = value;
        this.cellPhone = cellPhone;
    }

    public CellPhone getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(CellPhone cellPhone) {
        this.cellPhone = cellPhone;
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
