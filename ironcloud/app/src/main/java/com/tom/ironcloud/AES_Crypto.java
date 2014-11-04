package com.tom.ironcloud;

/**
 * Created by tom on 11/3/14.
 */
/**
 * Modified by Thomas M Veale 10.10.14'
 */

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.io.IOUtils;
import android.os.AsyncTask;

public abstract class AES_Crypto extends AsyncTask<String, Long, Integer>
{
    protected static byte[] plaintext; //
    protected static byte[] ciphertext;
    protected static String key = "0123456789abcdef"; //default
    protected static String fileLocation;
    protected static String limbo;
    protected static String IV = "AAAAAAAAAAAAAAAA"; //default
    //construct
    /** Methods for producing intermediate file and recovering original */
    public void makeLimbo()
    {
        try{
//target path to write to
            limbo = fileLocation + ".limbo";
//fos to create the intermediate encrypted file
            FileOutputStream out = new FileOutputStream(new File(limbo));
//get path of file to decrypt
            plaintext = org.apache.commons.io.FileUtils.readFileToByteArray(new File(fileLocation));
            ciphertext = encrypt(plaintext, key); //calls encryption method
            IOUtils.write(ciphertext, out); //prints the byte[] to file
            out.close(); //close fos
        } catch(Exception e) {e.printStackTrace();}
    }
    public void makeOriginal(String where)
    {
        try{
//the fos for reproducing the original file, where is the file path & name
            FileOutputStream fos = new FileOutputStream(new File(where));
            plaintext = decrypt(ciphertext, key);
            IOUtils.write(plaintext, fos); //prints the byte[] to file
            fos.close();
        } catch(Exception e) {e.printStackTrace();}
    }
    /** Methods for encryption and decryption*/
    public static byte[] encrypt(byte[] plainText, String encryptionKey) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText);
    }
    public static byte[] decrypt(byte[] cipherText, String encryptionKey) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(cipherText);
    }
}
