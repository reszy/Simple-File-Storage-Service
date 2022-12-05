package pl.reszy.filestorage.domain;

import com.google.common.annotations.VisibleForTesting;

import java.util.UUID;

public class WEBID {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int charsLength = CHARS.length();
    private static final int separator = '-';
    private static final int jump = 11;

    private String value;

    private WEBID(char[] webId) {
        value = String.valueOf(webId);
    }

    private WEBID(String webId) {
        // TODO validate
        value = webId;
    }

    public static WEBID fromString(String webId) {
        return new WEBID(webId);
    }

    public static WEBID randomWEBID() {
        return WEBID.fromUUID(UUID.randomUUID());
    }

    public static WEBID fromUUID(UUID uuid) {
        Integer[] uuidHex = convertToOffsetArray(uuid);
        char[] webId = new char[uuidHex.length];
        for (int i = 0, sequence = 0; i < uuidHex.length; i++) {
            sequence += uuidHex[i] + jump;
            if (sequence >= CHARS.length()) {
                sequence = sequence - CHARS.length();
            }
            webId[i] = CHARS.charAt(sequence);
        }
        return new WEBID(webId);
    }

    public UUID toUUID() {
        int webIdLength = value.length();
        char[] uuid = new char[webIdLength];
        for (int i = 0, previousJump = CHARS.indexOf(value.charAt(i)) - jump; i < webIdLength; i++) {
            uuid[i] = toHexCharacter(previousJump);
            if (i + 1 < webIdLength) {
                previousJump = getCharOffset(CHARS.indexOf(value.charAt(i)), CHARS.indexOf(value.charAt(i + 1))) - jump;
            }
        }
        return UUID.fromString(String.valueOf(uuid));
    }

    private static Integer[] convertToOffsetArray(UUID uuid) {
        String uuidStr = uuid.toString();
        return uuidStr.chars().boxed().map(i -> i == separator ? 17 : (int) Character.digit(i, 16))
                .toArray(Integer[]::new);
    }

    private static char toHexCharacter(int i) {
        return i == 17 ? '-' : i < 10 ? (char) (48 + i) : (char) (i - 10 + 97);
    }

    private int getCharOffset(int it, int next) {
        if (it > next) {
            next += charsLength;
        }
        return next - it;
    }

    @Override
    public String toString() {
        return value;
    }

}