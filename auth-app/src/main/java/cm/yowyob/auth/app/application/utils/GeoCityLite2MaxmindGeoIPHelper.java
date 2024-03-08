package cm.yowyob.auth.app.application.utils;

import cm.yowyob.auth.app.domain.port.GeoIPHelper;
import cm.yowyob.auth.app.domain.model.user.Address;
import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.google.common.base.Strings;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.net.InetAddress;
import java.util.Objects;
import java.util.Optional;



@Component
public class GeoCityLite2MaxmindGeoIPHelper implements GeoIPHelper {

    private DatabaseReader databaseReader = null;


    public GeoCityLite2MaxmindGeoIPHelper() {

        // FIXME: 17/01/2024
        InputStream resourceAsStream
                = getClass().getResourceAsStream("/GeoLite2-Country.mmdb");

        DatabaseReader.Builder builder = new DatabaseReader.Builder(resourceAsStream)
                .withCache(new CHMCache());

        try {

            this.databaseReader = builder.build();
            System.out.println("GeoLite2 Country");
            System.out.println(this.databaseReader.getMetadata());
        } catch (Exception ignored) {}

    }


    @Override
    public Optional<Address> findLocation(String ip) {

        if (databaseReader == null)
            return Optional.empty();

        try {

            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse cityResponse = databaseReader.city(ipAddress);

            if (Objects.nonNull(cityResponse) && Objects.nonNull(cityResponse.getCity()) &&
                    !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {

                System.out.println(cityResponse);

                return Optional.ofNullable(Address.builder()
                        .country(Objects.isNull(cityResponse.getCountry()) ? null : cityResponse.getCountry().getName())
                        .city(cityResponse.getCity().getName())
                        .build());
            }

            CountryResponse country = databaseReader.country(ipAddress);

            System.out.println(country);

        } catch (Exception ignored) {}

        return Optional.empty();

    }



}
