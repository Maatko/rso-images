package si.fri.rso.samples.images.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

import si.fri.rso.samples.images.dtos.Comment;

@Entity(name = "images")
@NamedQueries(value =
        {
                @NamedQuery(name = "Image.getAll", query = "SELECT o FROM images o"),
                @NamedQuery(name = "Image.findById", query = "SELECT o FROM images o WHERE o.id = " + ":id")
        })
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String caption;

    private String url;

    private Instant posted;

    @Transient
    private List<Comment> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public Instant getPosted() {
        return posted;
    }

    public void setPosted(Instant posted) {
        this.posted = posted;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
