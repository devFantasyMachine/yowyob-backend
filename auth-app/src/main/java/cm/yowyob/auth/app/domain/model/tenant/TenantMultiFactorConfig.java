package cm.yowyob.auth.app.domain.model.tenant;

import cm.yowyob.auth.app.domain.model.MultiFactorLoginPolicy;


public class TenantMultiFactorConfig {


    public MultiFactorAuthenticatorMethod authenticator;
    public Boolean mFactorEmailMethodEnabled;
    public MultiFactorLoginPolicy loginPolicy;
    public Boolean multiFactorSMSMethodEnabled;



    public TenantMultiFactorConfig(MultiFactorAuthenticatorMethod authenticator, boolean mFactorEmailMethodEnabled, MultiFactorLoginPolicy loginPolicy, boolean multiFactorSMSMethodEnabled) {
        this.authenticator = authenticator;
        this.mFactorEmailMethodEnabled = mFactorEmailMethodEnabled;
        this.loginPolicy = loginPolicy;
        this.multiFactorSMSMethodEnabled = multiFactorSMSMethodEnabled;
    }

    public TenantMultiFactorConfig() {
        this.authenticator = new MultiFactorAuthenticatorMethod();
        this.multiFactorSMSMethodEnabled = true;
        this.mFactorEmailMethodEnabled = true;
        this.loginPolicy = MultiFactorLoginPolicy.Disabled;
    }


    public static class MultiFactorAuthenticatorMethod {

        private boolean enabled;
        public TOTPAlgorithm algorithm;
        public int codeLength;
        public int timeStep;

        public MultiFactorAuthenticatorMethod() {
            this.algorithm = TOTPAlgorithm.HmacSHA1;
            this.codeLength = 6;
            this.timeStep = 30;
        }

        public MultiFactorAuthenticatorMethod(MultiFactorAuthenticatorMethod var1) {
            this.algorithm = var1.algorithm;
            this.codeLength = var1.codeLength;
            this.enabled = var1.enabled;
            this.timeStep = var1.timeStep;
        }

        public enum TOTPAlgorithm {
            HmacSHA1,
            HmacSHA256,
            HmacSHA512;

            TOTPAlgorithm() {
            }
        }


    }


}
