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

public abstract class AES_Crypto
{
	protected static byte[] plaintext; //
	protected static byte[] ciphertext; 
	protected static byte[] cipher;
	protected static byte[] decrypted;
	protected static String key = "0123456789abcdef" //default
	protected static String fileLocation;
	protected static String limbo;
	protected static String IV = "AAAAAAAAAAAAAAAA"; //default
	//construct
	public AES_Crypto(String fileLocation, String Key, String IV, String limbo)
	{
		//init
		this.fileLocation = fileLocation;
		this.IV = IV;
		this.key = key;
	}        
	public AES_Crypto(String fileLocation, String Key)
	{
		//init
		this.fileLocation = fileLocation;
		this.key = key;
	} 
	public AES_Crypto(String fileLocation)
	{
		//init
		this.fileLocation = fileLocation;
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

