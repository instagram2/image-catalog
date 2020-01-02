package instagram2.imagecatalog.api.v1.resources;

import instagram2.imagecatalog.lib.ImageMetadata;
import instagram2.imagecatalog.services.beans.ImageMetadataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/images")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImageMetadataResource {

    @Inject
    private ImageMetadataBean imageMetadataBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getImageMetadata() {

        List<ImageMetadata> imageMetadata = imageMetadataBean.getImageMetadata();

        return Response.status(Response.Status.OK).entity(imageMetadata).build();
    }

    @GET
    @Path("/{imageMetadataId}")
    public Response getImageMetadataById(@PathParam("imageMetadataId") Integer imageMetadataId) {

        ImageMetadata imageMetadata = imageMetadataBean.getImageMetadataById(imageMetadataId);

        if (imageMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(imageMetadata).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createImageMetadata(ImageMetadata imageMetadata) {
        imageMetadata = imageMetadataBean.createImageMetadata(imageMetadata);
        //return Response.status(Response.Status.CONFLICT).entity(imageMetadata).build();
    }

    @PUT
    @Path("/{imageMetadataId}")
    public Response putImageMetadata(@PathParam("imageMetadataId") Integer imageMetadataId,
                                     ImageMetadata imageMetadata) {

        imageMetadata = imageMetadataBean.putImageMetadata(imageMetadataId, imageMetadata);

        if (imageMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();

    }

    @DELETE
    @Path("/{imageMetadataId}")
    public Response deleteImageMetadata(@PathParam("imageMetadataId") Integer imageMetadataId) {

        boolean deleted = imageMetadataBean.deleteImageMetadata(imageMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
