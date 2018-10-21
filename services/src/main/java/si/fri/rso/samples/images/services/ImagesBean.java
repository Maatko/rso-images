package si.fri.rso.samples.images.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.images.entities.Image;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ImagesBean {

    private Logger log = Logger.getLogger(ImagesBean.class.getName());

    @Inject
    private EntityManager em;

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

        return image;
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
