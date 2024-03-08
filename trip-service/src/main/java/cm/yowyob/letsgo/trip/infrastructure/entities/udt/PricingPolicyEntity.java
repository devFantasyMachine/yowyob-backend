package cm.yowyob.letsgo.trip.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;



@Data
@Builder
@AllArgsConstructor
@UserDefinedType("pricing_policy")
public class PricingPolicyEntity {



}
