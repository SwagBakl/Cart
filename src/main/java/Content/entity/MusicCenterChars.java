package Content.entity;

import javax.persistence.*;

@Entity
@Table(name = "musicCenterChars")
public class MusicCenterChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "musicCenter_id")
    private MusicCenter musicCenter;

    public MusicCenterChars(){}

    public MusicCenterChars(String name, String value, MusicCenter musicCenter){
        this.name = name;
        this.value = value;
        this.musicCenter = musicCenter;
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

    public MusicCenter getMusicCenter() {
        return musicCenter;
    }

    public void setMusicCenter(MusicCenter musicCenter) {
        this.musicCenter = musicCenter;
    }
}
