package si.fri.rso.samples.images.dtos;

import java.time.Instant;

public class Comment {
    private Integer id;
    private String text;
    private Instant submitted;
    private Integer imageId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Instant submitted) {
        this.submitted = submitted;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
