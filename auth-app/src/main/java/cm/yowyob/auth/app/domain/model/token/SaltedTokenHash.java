/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.model.token;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Objects;

public record SaltedTokenHash(String hash, String salt, Boolean enabled, Instant expireAt) {

    public static final SaltedTokenHash NONE = new SaltedTokenHash("", "", false, null);

    public boolean verify(final String token) {
        final String theirValue = calculateV1Hash(salt, token);

        return MessageDigest.isEqual(
                theirValue.getBytes(StandardCharsets.UTF_8),
                hash.getBytes(StandardCharsets.UTF_8));
    }


    private static final int SALT_SIZE = 16;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static SaltedTokenHash generateFor(final String token, Instant expireAt) {
        final String salt = generateSalt();
        final String hash = calculateV1Hash(salt, token);
        return new SaltedTokenHash(hash, salt, true, expireAt);
    }

    private static String generateSalt() {
        final byte[] salt = new byte[SALT_SIZE];
        SECURE_RANDOM.nextBytes(salt);
        return HexFormat.of().formatHex(salt);
    }

    private static String calculateV1Hash(final String salt, final String token) {
        try {
            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA1").digest((salt + token).getBytes(StandardCharsets.UTF_8)));
        } catch (final NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaltedTokenHash that)) return false;
        return hash.equals(that.hash) && salt.equals(that.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, salt);
    }

    public SaltedTokenHash disable() {
        return new SaltedTokenHash(hash, salt, false, expireAt);
    }

}
