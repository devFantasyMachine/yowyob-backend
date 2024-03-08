package cm.yowyob.auth.app.domain.model.captcha;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.Instant;



@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Captcha {

    private String id;
    private String code;
    private Instant createAt;
}

