package Content.entity;

import javax.persistence.*;

@Entity
@Table(name="playerChars")
public class PlayerChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public PlayerChars(){}

    public PlayerChars(String name, String value, Player player){
        this.name = name;
        this.value = value;
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
