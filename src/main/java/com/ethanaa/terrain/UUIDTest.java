package com.ethanaa.terrain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.UUID;

public class UUIDTest {

    private static final Logger LOG = LoggerFactory.getLogger(UUIDTest.class);

    public static void main(String[] args) {

        UUID uuid = UUID.randomUUID();

        LOG.info("uuid = {}", uuid);

        String uuidNoDash = uuid.toString().replaceAll("-", "");

        LOG.info("uuidNoDash = {}", uuidNoDash);

        byte[] bytes = hexStringToBytes(uuidNoDash);

        LOG.info("bytes = {}", Arrays.toString(bytes));

        String bytesString = bytesToHexString(bytes);

        LOG.info("bytesString = {}", bytesString);

    }

    public static byte[] hexStringToBytes(String s) {

        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }

        return data;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHexString(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }
}
