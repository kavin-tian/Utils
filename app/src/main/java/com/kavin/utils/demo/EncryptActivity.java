package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kavin.utils.R;
import com.kavin.myutils.tools.EncodeUtils;
import com.kavin.myutils.tools.EncryptUtils;

public class EncryptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
    }

    public void encryptMD5ToString(View view) {
        String md5ToString = EncryptUtils.encryptMD5ToString("123456");
        System.out.println(md5ToString);
    }

    public void encryptSHA1ToString(View view) {
        String md5ToString = EncryptUtils.encryptSHA1ToString("123456");
        System.out.println(md5ToString);
    }

    public void encryptSHA256ToString(View view) {
        String md5ToString = EncryptUtils.encryptSHA256ToString("123456");
        System.out.println(md5ToString);
    }

    public void encryptDES2HexString(View view) {
        byte[] data = "123456".getBytes();
        byte[] key = "123456789".getBytes();
        byte[] iv = new byte[1024];
        String md5ToString = EncryptUtils.encryptDES2HexString(data, key, "DES", iv);
        System.out.println(md5ToString); //103E702E0737327C
    }

    public void decryptHexStringDES(View view) {
        String data = "103E702E0737327C";
        byte[] key = "123456789".getBytes();
        byte[] iv = new byte[1024];
        byte[] des = EncryptUtils.decryptHexStringDES(data, key, "DES", iv);
        System.out.println(new String(des)); //123456
    }


    public void encryptDES2Base64(View view) {
        byte[] data = "123456".getBytes();
        byte[] key = "123456789".getBytes();
        byte[] iv = new byte[1024];
        byte[] des = EncryptUtils.encryptDES2Base64(data, key, "DES", iv);
        System.out.println(new String(des));//ED5wLgc3Mnw=
    }

    public void decryptBase64DES(View view) {
        byte[] data = "ED5wLgc3Mnw=".getBytes();
        byte[] key = "123456789".getBytes();
        byte[] iv = new byte[1024];
        byte[] des = EncryptUtils.decryptBase64DES(data, key, "DES", iv);
        System.out.println(new String(des)); //123456
    }


    public void encryptAES2HexString(View view) {
        byte[] data = "123456".getBytes();
        byte[] key = "0123456789123456".getBytes(); //Key length must be 128/192/256 bits.
        byte[] iv = new byte[1024];
        String aes = EncryptUtils.encryptAES2HexString(data, key, "AES", iv);
        System.out.println(aes); //F4C0D92F832DC2E2A0FE4931E3572EF3
    }

    public void decryptHexStringAES(View view) {
        String data = "F4C0D92F832DC2E2A0FE4931E3572EF3";
        byte[] key = "0123456789123456".getBytes(); //Key length must be 128/192/256 bits.
        byte[] iv = new byte[1024];
        byte[] aes = EncryptUtils.decryptHexStringAES(data, key, "AES", iv);
        System.out.println(new String(aes)); //123456
    }


    //在线生成密钥对： http://web.chacuo.net/netrsakeypair
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeKplqZhYkjXcaaLOJCYv+IxOKvGFc3AzjfktZ" +
            "3diqNUtGS5b+MBeLglz0EFbwrEQ9ph0m2mztttuSml7tR5+jAChK9D3DLr3X/Geo9d9R5wMQT6Tr" +
            "IkiTnHDsRCjIJ+LUnHtkoYCiNvsjChmHZ+Am+9/aJPLssxfqFqh30eYD2QIDAQAB";


    String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAN4qmWpmFiSNdxpos4kJi/4jE4q8" +
            "YVzcDON+S1nd2Ko1S0ZLlv4wF4uCXPQQVvCsRD2mHSbabO2225KaXu1Hn6MAKEr0PcMuvdf8Z6j1" +
            "31HnAxBPpOsiSJOccOxEKMgn4tSce2ShgKI2+yMKGYdn4Cb739ok8uyzF+oWqHfR5gPZAgMBAAEC" +
            "gYAmQIdxvmoJmSHstiJTcgVcmIWA2baZ2nmNoe0vZ/cqN0riq5kv4u/q4WNH0phlkAOf6b4TNoCS" +
            "3W0o7NNzX2WoxOhtA6A8Ge/YrUpS8mq4DogfooSpFwjm0tqG1JHoPJIQxSX9yDe1YRW2iTrLtmJi" +
            "108Z6qwpFoX18kiTzI3V0QJBAPiHE6vi4SlqcuQYBFBfDAyT71iusBaKgx+KSmyBL5psTs8dsJGl" +
            "z8AHqiY1CwqKuGbhWtONdia0ldrUd1kNoBMCQQDk2J6NegQAhG4k22O+sPLKXhwYWbMFhsG5Zi+F" +
            "PSFYJEZL5ZPL2cmk499HI/ztfBATLjP098EVE3XODnyuKwHjAkBkabcR+L3+jMrUy7taebyVjGPl" +
            "EdeFk/s8kBRX2K5SFzaUSiuf/T1+GdquSzolzTyKHL0kwoAdTVNKshNygKXPAkAfNO8CKfZrv6Np" +
            "gUVr1qN1Nzork+fWxBac2rN2PuzSITg411i9PrjBtJVZFS5DkFx80Rsit7przliNPeqAsbqzAkBZ" +
            "4NOBTBuOZbnskxAlC8QrMQVszWGrr3vVUC/Z316dalYBQe+20LROcCchGH8X0kXdBO0LVh/yUWwE" +
            "cpzQ3vY/";

    /**
     * 只能私钥加密，公钥解密，如果也想公钥加密私钥解密请参考我的 RSADemo工程
     * https://github.com/kavin-tian/RSADemo
     */
    public void encryptRSA2Base64(View view) {
        byte[] privateKeyBytes = EncodeUtils.base64Decode(privateKey);
        byte[] data = "123456".getBytes();
        byte[] aes = EncryptUtils.encryptRSA2Base64(data, privateKeyBytes, 1024, "RSA/ECB/PKCS1PADDING");
        System.out.println("aes: " + new String(aes));
    }

    /**
     * 只能私钥加密，公钥解密，如果也想公钥加密私钥解密请参考我的 RSADemo工程
     * https://github.com/kavin-tian/RSADemo
     */
    public void decryptBase64RSA(View view) {
        //上面123456加密后的结果
        String content = "WveLzg/nHif47wl9Cb3wO2hdX449hZ6H2QTL0WbMpDg9heJ4Nxfu0ux/AJruHc1Cd8kKW745LsWnB+XRT8xbwtBNC1eaNFvgfEhoVR4hvTM+/BwOvc658P1PCsWi624CuW01y8VMeB9qMiaRLjwzW58/VrXQMFQ7W16qQBxULZo=";
        byte[] publicKeyBytes = EncodeUtils.base64Decode(publicKey);
        byte[] data = content.getBytes();
        byte[] aes = EncryptUtils.decryptBase64RSA(data, publicKeyBytes, 1024, "RSA/ECB/PKCS1PADDING");
        System.out.println("aes: " + new String(aes));
    }


}
