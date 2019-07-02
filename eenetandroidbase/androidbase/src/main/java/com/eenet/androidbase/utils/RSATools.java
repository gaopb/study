package com.eenet.androidbase.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by yao23 on 17/2/23.
 */

public class RSATools {

    /**
     * 使用open ssl公钥字符串生成加密对象， 不包含开始、结束标识，示例如下：
     * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGViBiCRyZrLOOE/UcLgrVFrFc" + "\r" +
     * "................................................................" + "\r" +
     * "F5d6e9p9S6wyvHesQTaAHyMyEpwokupnORL/o7pQ8Y40hEfY8Ch9BA3Mgn5ncUCP" + "\r" +
     * "K8eHGwxVVu72xVeA8QIDAQAB" + "\r"
     * @param OpenSSLPublicKey
     * @return
     * @throws Exception
     * 2016年5月31日
     * @author Orion
     */
    public static RSAPublicKey loadPublicKey(String OpenSSLPublicKey) throws Exception {

        byte[] buffer= Base64.decode(OpenSSLPublicKey,Base64.DEFAULT);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

//        result = new RSAEncrypt();
//        result.setModulus(String.valueOf(pubKey.getModulus()));
//        result.setPublicExponent(String.valueOf(pubKey.getPublicExponent()));

        return pubKey;
    }

    /**
     * 使用open ssl公钥字符串生成加密对象， 不包含开始、结束标识，示例如下：
     * "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMZWIGIJHJmss44T" + "\r" +
     * "................................................................" + "\r" +
     * "Egls0i0IYJZJFXUGYzuiiwrRm45Zhsg6Es4qH54G1N9HiO2E6xg2XUaJelOZFeCk" + "\r" +
     * "XIH+ji2ewm2zhQ==" + "\r"
     * @param OpenSSLPrivateKey
     * @return
     * @throws Exception
     * 2016年5月31日
     * @author Orion
     */
    public static RSAPrivateKey loadPrivateKey(String OpenSSLPrivateKey) throws Exception {

        byte[] buffer= Base64.decode(OpenSSLPrivateKey,Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        RSAPrivateKey priKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

//        result = new RSADecrypt();
//        result.setModulus(String.valueOf(priKey.getModulus()));
//        result.setPrivateExponent(String.valueOf(priKey.getPrivateExponent()));

        return priKey;
    }

    /**
     * RSA算法加密
     * ★加密模式：ECB，填充方式：PKCS1Padding
     * @param encrypt 加密参数
     * @param plaintext 加密内容（明文）
     * @return 密文
     * 2016年3月30日
     * @author Orion
     * @throws Exception 任何加密异常
     */
    public static String encrypt(RSAPublicKey encrypt, String plaintext) {
        try {
            BigInteger b1 = new BigInteger(String.valueOf(encrypt.getModulus()));
            BigInteger b2 = new BigInteger(String.valueOf(encrypt.getPublicExponent()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

            //Cipher cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding", new BouncyCastleProvider());//RSA是加密方法，ECB是加密模式，PKCS1Padding是填充方式
            Cipher cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding");//RSA是加密方法，ECB是加密模式，PKCS1Padding是填充方式
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output= cipher.doFinal(plaintext.getBytes("UTF-8"));

            return Base64.encodeToString(output,Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA解密
     * ★解密模式：ECB，填充方式：PKCS1Padding
     * @param decrypt 解密参数
     * @param ciphertext 解密内容（密文）
     * @return 明文
     * 2016年3月30日
     * @author Orion
     * @throws Exception
     */
    public static String decrypt(RSAPrivateKey decrypt, String ciphertext) {
        try {
            if (ciphertext==null || ciphertext.equals(""))
                return null;

            BigInteger b1 = new BigInteger(String.valueOf(decrypt.getModulus()));
            BigInteger b2 = new BigInteger(String.valueOf(decrypt.getPrivateExponent()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

            //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(Base64.decode(ciphertext,Base64.DEFAULT));

            return new String(output,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
