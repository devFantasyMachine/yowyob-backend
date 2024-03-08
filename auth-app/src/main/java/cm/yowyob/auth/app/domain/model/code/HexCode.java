package cm.yowyob.auth.app.domain.model.code;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class HexCode implements Code{

    private String value;

}
