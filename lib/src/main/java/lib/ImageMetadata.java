package instagram2.imagecatalog.lib;

import java.time.Instant;

public class ImageMetadata {

    private Integer imageId;
    private String title;
    private String description;
    private Integer width;
    private Integer height;
    private Instant created;
    private String uri;
    private Integer numberOfComments;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Instant getCreated() {
        return this.created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getImageId() {
        return this.imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getNumberOfComments() {
        return this.numberOfComments;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }
}
