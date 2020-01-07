package instagram2.imagecatalog.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("configuration-properties")
@ApplicationScoped
public class IntegrationProperties {
    @ConfigValue(value = "comments.enabled", watch = true)
    private boolean integrateWithCommentsService;

    public boolean isIntegrateWithCommentsService() {
        return integrateWithCommentsService;
    }

    public void setIntegrateWithCommentsService(boolean integrateWithCommentsService) {
        this.integrateWithCommentsService = integrateWithCommentsService;
    }
}
