package instagram2.imagecatalog.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "image_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "ImageMetadataEntity.getAll",
                        query = "SELECT im FROM ImageMetadataEntity im")
        })
public class ImageMetadataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "created")
    private Instant created;

    @Column(name = "uri")
    private String uri;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}