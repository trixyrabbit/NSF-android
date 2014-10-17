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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.io.*; /uness depend

public class AES_Code
{
    static String IV = "AAAAAAAAAAAAAAAA";
    static String plaintext; 
    static String ciphertext; 
    static String encryptionKey = "0123456789abcdef";
//args0 input: file to be encrypted
    public static void main(String[] args)
    {
	System.out.println("Welcome to AES file ENCRYPTION/DECRYPTION test program");
        try {
	    Scanner scan = new Scanner(System.in); //scanner object for user input

	    String fileLocation = scan.next(); //user input for file to be encryted
		//PrintWriter for outputfile
	    PrintWriter out = new PrintWriter(fileLocation + ".enc");
	    Path path = Paths.get(fileLocation); //saves location
	    String ext = fileLocation.substring(fileLocation.lastIndexOf("."), fileLocation.length());
		//for recovering the original
	    FileOutputStream fos = new FileOutputStream("itworks." + ext);
		//read all the data from the user specified path to byte[]
	    byte[] data = Files.readAllBytes(path);
		//creates a string from byte[] read from file
	    plaintext = new String(data, "UTF-8");
		//what it looks like...
            System.out.println("plainText:   String..." + plaintext);
		//encrypt the String
            byte[] cipher = encrypt(plaintext, encryptionKey);
	    ciphertext = new String(cipher, "UTF-8");
            System.out.print("cipherText stored @: " + fileLocation + ".enc");
		//write ciphertext String to file for later access
	    out.print(ciphertext);
		//call decryption method
            String decrypted = decrypt(cipher, encryptionKey);
		byte[] dec = decrypted.getBytes();
		fos.write(dec);
		fos.close();
            System.out.println("decrypted Plain Text stored in wd");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText),"UTF-8");
    }
}

