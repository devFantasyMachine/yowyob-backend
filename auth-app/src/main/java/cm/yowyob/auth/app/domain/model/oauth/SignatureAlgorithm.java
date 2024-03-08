package cm.yowyob.auth.app.domain.model.oauth;


import lombok.Getter;

@Getter
public enum SignatureAlgorithm {

    HS256(JwsAlgorithms.HS256),
    HS384(JwsAlgorithms.HS384),
    HS512(JwsAlgorithms.HS512),
    RS256(JwsAlgorithms.RS256),
    RS384(JwsAlgorithms.RS384),
    RS512(JwsAlgorithms.RS512),
    ES256(JwsAlgorithms.ES256),
    ES384(JwsAlgorithms.ES384),
    ES512(JwsAlgorithms.ES512),
    PS256(JwsAlgorithms.PS256),
    PS384(JwsAlgorithms.PS384),
    PS512(JwsAlgorithms.PS512)
    ;

    private final String name;

    SignatureAlgorithm(String algo){
        this.name = algo;
    }


    public static SignatureAlgorithm from(String name) {
        SignatureAlgorithm[] var1 = values();

        for (SignatureAlgorithm value : var1) {
            if (value.getName().equals(name)) {
                return value;
            }
        }

        return null;
    }


}
