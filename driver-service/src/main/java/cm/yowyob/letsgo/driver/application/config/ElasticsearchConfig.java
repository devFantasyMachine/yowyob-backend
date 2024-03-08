package cm.yowyob.letsgo.driver.application.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.time.Duration;


@Configuration
@EnableElasticsearchRepositories(basePackages = {"cm.yowyob.letsgo.driver.infrastructure.search"})
public class ElasticsearchConfig extends ElasticsearchConfiguration {


    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port:9200}")
    private int port;

//    @Value("${elasticsearch.username}")
//    private String username;
//
//    @Value("${elasticsearch.password}")
//    private String password;
//
//    @Value("${elasticsearch.certificate}")
//    private String certificateBase64;



    @Override
    public ClientConfiguration clientConfiguration() {

        return ClientConfiguration.builder()
                .connectedTo( host + ":" + port)
                //.usingSsl()
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(10))
                //.withBasicAuth(username, password)
                .build();
    }

  /*  private SSLContext getSSLContext() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        byte[] decode = Base64.getDecoder().decode(certificateBase64);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        Certificate ca;
        try (InputStream certificateInputStream = new ByteArrayInputStream(decode)) {
            ca = cf.generateCertificate(certificateInputStream);
        }

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        return context;
    }

*/

}

