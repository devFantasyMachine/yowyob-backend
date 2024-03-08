package cm.yowyob.letsgo.trip.application.config;



import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.netflix.eureka.RestTemplateTimeoutProperties;
import org.springframework.cloud.netflix.eureka.http.DefaultEurekaClientHttpRequestFactorySupplier;
import org.springframework.cloud.netflix.eureka.http.EurekaClientHttpRequestFactorySupplier;
import org.springframework.cloud.netflix.eureka.http.RestTemplateDiscoveryClientOptionalArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


@Configuration
public class BootstrapConfig {


    @Value("${spring.cloud.config.tls.keyStorePassword}")
    String keystorePassword;



    @Autowired
    ConfigClientProperties properties;

/*

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ConfigServicePropertySourceLocator configServicePropertySourceLocator() throws Exception {
        final char[] password = "ys0b@w-cm".toCharArray();

        System.out.println(properties.getTls());


        final ClassPathResource resource = new ClassPathResource("ssl-server.jks");

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(resource.getFile(), password, password)
                .loadTrustMaterial(resource.getFile(), password, new TrustSelfSignedStrategy()).build();

        final SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        final Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        final BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();


        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        ConfigServicePropertySourceLocator configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(properties);
        configServicePropertySourceLocator.setRestTemplate(new RestTemplate(requestFactory));
        return configServicePropertySourceLocator;
    }

*/

    @Bean
    public RestTemplateDiscoveryClientOptionalArgs  discoveryClientOptionalArgs(

    ) throws NoSuchAlgorithmException, IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, KeyManagementException {

        RestTemplateTimeoutProperties restTemplateTimeoutProperties = new RestTemplateTimeoutProperties();

        EurekaClientHttpRequestFactorySupplier eurekaClientHttpRequestFactorySupplier =
                new DefaultEurekaClientHttpRequestFactorySupplier(restTemplateTimeoutProperties);

        RestTemplateDiscoveryClientOptionalArgs args = new RestTemplateDiscoveryClientOptionalArgs(eurekaClientHttpRequestFactorySupplier);

        final char[] password = keystorePassword.toCharArray();

        URL url = getClass().getResource("/letsgo-ssl-store.jks");

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(url, password, password)
                .loadTrustMaterial(url, password, new TrustSelfSignedStrategy()).build();

        args.setSSLContext(sslContext);
        args.setHostnameVerifier(NoopHostnameVerifier.INSTANCE);

        return args;
    }

}
