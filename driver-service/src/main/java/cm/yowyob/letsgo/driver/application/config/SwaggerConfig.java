/*
 * This file is part of Openrouteservice.
 *
 * Openrouteservice is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, see <https://www.gnu.org/licenses/>.
 */

package cm.yowyob.letsgo.driver.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerConfig {

    private static final String SERVICE_NAME = "Yowyob User Resource Service ";


    @Bean
    public OpenAPI customOpenAPI() {

        Server devServer = new Server();
        devServer.url("http://localhost:7090");
        devServer.description("Development Server");

        return new OpenAPI(SpecVersion.V31)
                .servers(List.of(devServer))
                .info(apiInfo());
    }

    /**
     * This gives a properly versioned swagger endpoint at api-docs/v2 for the ors v2 version.
     * To introduce, e.g., v3 just use this function and replace v2 with v3.
     */
    @Bean
    public GroupedOpenApi orsV2ApiPath() {
        String[] paths = {"/v2/**"};
        return GroupedOpenApi.builder().setGroup("v2").pathsToMatch(paths)
                .build();
    }

    /**
     * This function provides the API v2 at the root api-docs/ path, as this was the old path of service the swagger.
     * For proper API versioning see orsV2ApiPath().
     */
    @Bean
    public GroupedOpenApi oldOrsV2ApiPath() {
        String[] paths = {"/v2/**"};
        return GroupedOpenApi.builder().setGroup("").pathsToMatch(paths)
                .build();
    }

    private Info apiInfo() {
        return new Info()
                .title(SERVICE_NAME)
                .description("This is the Letsgo Localisation Service API documentation for  " + "1.0" + ".")
                .version("v2")
                .contact(apiContact())
                .license(apiLicence());
    }

    private License apiLicence() {
        return new License()
                .name("Yowyob Dev Licence")
                .url("https://github.com/LICENSE");
    }

    private Contact apiContact() {
        return new Contact()
                .name(SERVICE_NAME)
                .email("support@yowyob.cm")
                .url("https://gitlab.yowyob.com");
    }

}

