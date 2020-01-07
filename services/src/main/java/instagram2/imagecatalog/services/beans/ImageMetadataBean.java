package instagram2.imagecatalog.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import instagram2.imagecatalog.lib.ImageMetadata;
import instagram2.imagecatalog.models.converters.ImageMetadataConverter;
import instagram2.imagecatalog.models.entities.ImageMetadataEntity;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RequestScoped
public class ImageMetadataBean {

    private Logger log = Logger.getLogger(ImageMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    private Client httpClient;

    private String imgProperties = "{" +
            "\"imageId\":1," +
            "\"description\":\"test\"" +
            "\"title\":\"testTitle\"" +
            "\"uri\":\"testUri\"" +
            "\"width\":6" +
            "\"height\":5" +
            "}";



    private JSONParser jsonParser = new JSONParser();

    private ImageMetadata testImg;

    @Inject
    @DiscoverService("comments")
    private Optional<String> baseUrl;

    private List<ImageMetadata> mockDB = new ArrayList<>();

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        //baseUrl = "http://localhost:8081";
    }


    public List<ImageMetadata> getImageMetadata() {
        /*if (this.mockDB == null) {
            return null;
        }
        return this.mockDB;*/
        TypedQuery<ImageMetadataEntity> query = em.createNamedQuery("ImageMetadataEntity.getAll",
                ImageMetadataEntity.class);

        return query.getResultList().stream().map(ImageMetadataConverter::toDto).collect(Collectors.toList());
    }

    public List<ImageMetadata> getImageMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, ImageMetadataEntity.class, queryParameters).stream()
                .map(ImageMetadataConverter::toDto).collect(Collectors.toList());
    }

    public ImageMetadata getImageMetadataById(Integer id) {
        /*ImageMetadata imageMetadata = null;

        for (ImageMetadata image : this.mockDB) {
            if (image.getImageId().equals(id)){
                imageMetadata = image;
            }
        }

        if (imageMetadata == null) {
            throw new NotFoundException();
        }

        imageMetadata.setNumberOfComments(getCommentCount(id));

        return imageMetadata;*/

        ImageMetadataEntity imageMetadataEntity = em.find(ImageMetadataEntity.class, id);

        if (imageMetadataEntity == null) {
            throw new NotFoundException();
        }

        ImageMetadata imageMetadata = ImageMetadataConverter.toDto(imageMetadataEntity);
        //imageMetadata.setNumberOfComments(getCommentCount(id));

        return imageMetadata;
    }

    public ImageMetadata createImageMetadata(ImageMetadata imageMetadata) {
        /*this.mockDB.add(imageMetadata);
        return imageMetadata;*/
        ImageMetadataEntity imageMetadataEntity = ImageMetadataConverter.toEntity(imageMetadata);

        try {
            beginTx();
            em.persist(imageMetadataEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (imageMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ImageMetadataConverter.toDto(imageMetadataEntity);

    }

    public ImageMetadata putImageMetadata(Integer id, ImageMetadata imageMetadata) {

        ImageMetadataEntity c = em.find(ImageMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        ImageMetadataEntity updatedImageMetadataEntity = ImageMetadataConverter.toEntity(imageMetadata);

        try {
            beginTx();
            updatedImageMetadataEntity.setId(c.getId());
            updatedImageMetadataEntity = em.merge(updatedImageMetadataEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return ImageMetadataConverter.toDto(updatedImageMetadataEntity);
    }

    public boolean deleteImageMetadata(Integer id) {

        /*ImageMetadata storedImageMetadata = null;

        for (ImageMetadata image : this.mockDB) {
            if (image.getImageId() == id){
                storedImageMetadata = image;
            }
        }

        if (storedImageMetadata != null) {
            this.mockDB.remove(storedImageMetadata);
        } else
            return false;

        return true;*/
        ImageMetadataEntity imageMetadata = em.find(ImageMetadataEntity.class, id);

        if (imageMetadata != null) {
            try {
                beginTx();
                em.remove(imageMetadata);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
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
    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
