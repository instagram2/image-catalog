package instagram2.imagecatalog.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import instagram2.imagecatalog.lib.ImageMetadata;
import instagram2.imagecatalog.models.entities.ImageMetadataEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@ApplicationScoped
public class ImageMetadataBean {

    private Logger log = Logger.getLogger(ImageMetadataBean.class.getName());

    private Client httpClient;

    private ImageMetadata testImg = new ImageMetadata(1);

    private String baseUrl;

    private List<ImageMetadata> mockDB = Arrays.asList(this.testImg);

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrl = "http://localhost:8081"; // only for demonstration
    }


    public List<ImageMetadata> getImageMetadata() {
        if (this.mockDB == null) {
            return null;
        }
        return this.mockDB;
    }

    public ImageMetadata getImageMetadata(Integer id) {
        ImageMetadata imageMetadata = null;

        for (ImageMetadata image : this.mockDB) {
            if (image.getImageId() == id){
                imageMetadata = image;
            }
        }

        if (imageMetadata == null) {
            throw new NotFoundException();
        }

        imageMetadata.setNumberOfComments(getCommentCount(id));

        return imageMetadata;
    }

    public ImageMetadata createImageMetadata(ImageMetadata imageMetadata) {
        if (!this.mockDB.add(imageMetadata)) {
            throw new RuntimeException("Entity was not persisted");
        }
        return imageMetadata;
    }

    public ImageMetadata putImageMetadata(Integer id, ImageMetadata imageMetadata) {

        ImageMetadata storedImageMetadata = null;

        for (ImageMetadata image : this.mockDB) {
            if (image.getImageId() == id){
                storedImageMetadata = image;
            }
        }

        if (storedImageMetadata == null) {
            return null;
        } else {
            this.mockDB.add(imageMetadata);
        }

        return imageMetadata;
    }

    public boolean deleteImageMetadata(Integer id) {

        ImageMetadata storedImageMetadata = null;

        for (ImageMetadata image : this.mockDB) {
            if (image.getImageId() == id){
                storedImageMetadata = image;
            }
        }

        if (storedImageMetadata != null) {
            this.mockDB.remove(storedImageMetadata);
        } else
            return false;

        return true;
    }


    public Integer getCommentCount(Integer imageId) {

        try {
            return httpClient
                    .target(baseUrl + "/v1/comments/count")
                    .queryParam("imageId", imageId)
                    .request().get(new GenericType<Integer>() {
                    });
        } catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

    }
}
