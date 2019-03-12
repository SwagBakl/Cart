package Content.entity;


import javax.persistence.*;

@Entity
@Table(name = "noteBookChars")
public class NoteBookChars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private NoteBook noteBook;

    public NoteBookChars(){}

    public NoteBookChars(String name, String value, NoteBook noteBook) {
        this.name = name;
        this.value = value;
        this.noteBook = noteBook;
    }

    public NoteBook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(NoteBook noteBook) {
        this.noteBook = noteBook;
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
