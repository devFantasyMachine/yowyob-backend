package cm.yowyob.auth.app;


import cm.yowyob.auth.app.domain.handlers.APIKeyManager;
import cm.yowyob.auth.app.domain.handlers.AdminHandler;
import cm.yowyob.auth.app.domain.handlers.TenantManager;
import cm.yowyob.auth.app.domain.model.tenant.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "auth.app.setup")
public class SystemSetup {


    @Autowired
    AdminHandler adminHandler;

    @Autowired
    TenantManager tenantManager;

    @Autowired
    APIKeyManager apiKeyManager;



    private String superAdminEmail;
    private Organisation organisation;
    private String defaultIssuer;




    public void init(){


    }








}
