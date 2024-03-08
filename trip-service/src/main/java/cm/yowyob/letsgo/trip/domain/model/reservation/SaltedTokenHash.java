/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.letsgo.trip.domain.model.reservation;

import lombok.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.Objects;

public record SaltedTokenHash(String hash, String salt) {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    public boolean verify(final String token) {

        final String theirValue = calculateV1Hash(salt, token);

        return MessageDigest.isEqual(
                theirValue.getBytes(StandardCharsets.UTF_8),
                hash.getBytes(StandardCharsets.UTF_8));
    }


    private static final int SALT_SIZE = 16;
    private static String generateSalt() {
        return generate(SALT_SIZE);
    }


    public static @NonNull SaltedTokenHash generateFor(final String token) {
        final String salt = generateSalt();
        final String hash = calculateV1Hash(salt, token);
        return new SaltedTokenHash(hash, salt);
    }
    private static String calculateV1Hash(final String salt, final String token) {
        try {
            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA1").digest((salt + token).getBytes(StandardCharsets.UTF_8)));
        } catch (final NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }


    private static final int TOKEN_SIZE = 32;
    public static String generatePlainText() {
        return generate(TOKEN_SIZE);
    }

    private static String generate(int tokenSize) {
        final byte[] plain = new byte[tokenSize];
        SECURE_RANDOM.nextBytes(plain);
        return HexFormat.of().formatHex(plain);
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

}
