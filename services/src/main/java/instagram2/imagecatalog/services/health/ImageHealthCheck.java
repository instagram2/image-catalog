package instagram2.imagecatalog.services.health;

import instagram2.imagecatalog.services.config.IntegrationProperties;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@Readiness
@ApplicationScoped
public class ImageHealthCheck implements HealthCheck {
    @Inject
    private IntegrationProperties ing;

    @Override
    public HealthCheckResponse call() {
        try {
            if (ing.isIntegrateWithCommentsService()) {
                return HealthCheckResponse.up(ImageHealthCheck.class.getSimpleName());
            } else {
                return HealthCheckResponse.down(ImageHealthCheck.class.getSimpleName());
            }
        } catch (Exception e) {
            Logger.getLogger(ImageHealthCheck.class.getSimpleName()).warning(e.getMessage() + " in ");
        }
        return HealthCheckResponse.down(ImageHealthCheck.class.getSimpleName());

    }
}
