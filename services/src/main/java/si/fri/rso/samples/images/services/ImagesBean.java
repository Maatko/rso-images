package si.fri.rso.samples.images.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.images.dtos.Comment;
import si.fri.rso.samples.images.entities.Image;
import si.fri.rso.samples.images.services.configuration.AppProperties;
import com.kumuluz.ee.discovery.annotations.DiscoverService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.Optional;

@ApplicationScoped
public class ImagesBean {

    private Logger log = Logger.getLogger(ImagesBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private AppProperties appProperties;

    @Inject
    private ImagesBean imagesBean;

    private Client httpClient;

    @Inject
    @DiscoverService("rso-comments")
    private Optional<String> baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        // baseUrl = "http://localhost:8081"; // only for demonstration
    }

    public List<Image> getImages(UriInfo uriInfo) {



        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Image.class, queryParameters);

    }

    public Image getImage(Integer imageId) {

        Image image = em.find(Image.class, imageId);

        if (image == null) {
            throw new NotFoundException();
        }

        List<Comment> comments = imagesBean.getComments(imageId);
        image.setComments(comments);

        return image;
    }

    // outer service

    public List<Comment> getComments(Integer imageId) {

        if (appProperties.isExternalServicesEnabled() && baseUrl.isPresent()) {
            try {
                return httpClient
                        .target(baseUrl.get() + "/v1/comments?where=imageId:EQ:" + imageId)
                        .request().get(new GenericType<List<Comment>>() {
                        });
            } catch (WebApplicationException | ProcessingException e) {
                log.severe(e.getMessage());
                throw new InternalServerErrorException(e);
            }
        }

        return null;

    }

    public Image createImage(Image image) {

        try {
            beginTx();
            em.persist(image);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return image;
    }

    public boolean deleteImage(Integer imageId) {

        Image image = em.find(Image.class, imageId);

        if (image != null) {
            try {
                beginTx();
                em.remove(image);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
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
