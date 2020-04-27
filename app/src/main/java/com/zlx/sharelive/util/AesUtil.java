package com.zlx.sharelive.util;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: Zlx on 2018/11/26/026.
 * Email:1170762202@qq.com
 */
public class AesUtil {
    public static final String VIPARA = "1269571569321021";
    public static final String bm = "utf-8";
    private static final String KEY = "xmzd0.+.j-.1v26-";
    private static final String RULE = "xmzdxxkjqwer";

    /**
     * 字节数组转化为大写16进制字符串
     *
     * @param b
     * @return
     */
    private static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }

            sb.append(s.toUpperCase());
        }

        return sb.toString();
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param s
     * @return
     */
    private static byte[] str2ByteArray(String s) {
        int byteArrayLength = s.length() / 2;
        byte[] b = new byte[byteArrayLength];
        for (int i = 0; i < byteArrayLength; i++) {
            byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16)
                    .intValue();
            b[i] = b0;
        }

        return b;
    }


    /**
     * AES 加密
     *
     * @param content  明文
     * @param password 生成秘钥的关键字
     * @return
     */

    public static String aesEncrypt(String content, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(content.getBytes(bm));
            return Base64.encode(encryptedData);
//			return byte2HexStr(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES 解密
     *
     * @param content  密文
     * @param password 生成秘钥的关键字
     * @return
     */

    public static String aesDecrypt(String content, String password) {
        try {
            byte[] byteMi = Base64.decode(content);
//			byte[] byteMi=	str2ByteArray(content);
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData, "utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String encode(String content) {
        return aesEncrypt(content, KEY);
//        return aesEncrypt(content, KEY);
    }

    public static String decode(String content) {
        return aesDecrypt(content, KEY);
    }


}
