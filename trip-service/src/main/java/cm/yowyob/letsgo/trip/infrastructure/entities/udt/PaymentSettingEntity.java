package cm.yowyob.letsgo.trip.infrastructure.entities.udt;


import cm.yowyob.letsgo.trip.domain.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@UserDefinedType("payment_setting")
public class PaymentSettingEntity {

    @Column(value = "currency")
    private String currencyCode;

    @Column(value = "payment_accounts")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = {CassandraType.Name.VARCHAR, CassandraType.Name.VARCHAR})
    private Map<PaymentMethod, String> paymentAccounts;


}
