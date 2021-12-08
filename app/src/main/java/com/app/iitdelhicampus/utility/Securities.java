package com.app.iitdelhicampus.utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Securities {
    private static Securities securities = null;


    private Securities() {

    }

    public static Securities getInstance() {
        if (securities == null) {
            securities = new Securities();
        }
        return securities;
    }

    static String key = "DrND1vEWud499da8";

    public static String encriptedData(String input) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(android.util.Base64.encode(crypted, android.util.Base64.DEFAULT));
    }

    public static String decriptedData(String input) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(android.util.Base64.decode(input.getBytes(), android.util.Base64.DEFAULT));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(output);
    }
}
