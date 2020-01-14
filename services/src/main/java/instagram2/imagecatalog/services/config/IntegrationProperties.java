package instagram2.imagecatalog.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("configuration-properties")
@ApplicationScoped
public class IntegrationProperties {

    @ConfigValue(value = "dbuser", watch = true)
    private String dbuser;
    @ConfigValue(value = "dbpass", watch = true)
    private String dbpass;

    @ConfigValue(value = "comments.enabled", watch = true)
    private boolean integrateWithCommentsService;

    public boolean isIntegrateWithCommentsService() {
        return integrateWithCommentsService;
    }

    public void setIntegrateWithCommentsService(boolean integrateWithCommentsService) {
        this.integrateWithCommentsService = integrateWithCommentsService;
    }

    public String getDbpass() {
        return dbpass;
    }

    public String getDbuser() {
        return dbuser;
    }

    public void setDbpass(String dbpass) {
        this.dbpass = dbpass;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }
}
