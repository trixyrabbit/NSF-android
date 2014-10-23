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
	//protected static byte[] cipher;
	//protected static byte[] decrypted;
	protected static String key = "0123456789abcdef" //default
	protected static String fileLocation;
	protected static String limbo;
	protected static String IV = "AAAAAAAAAAAAAAAA"; //default
	/** constructors, fileLocation indicates the file to operate on, Key indicates the encryption key to be used, and IV is the initial vectory required for any AES cryptoraphy. */
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
	/** Methods for producing intermediate file and recovering original */
	public void makeLimbo()
	{
		try{
			//target path to write to
			limbo = fileLocation + ".limbo";
			//fos to create the intermediate encrypted file
			FileOutputStream out = new FileOutputStream(new File(limbo));
			//get path of file to decrypt
			Path path = Paths.get(fileLocation);
			plaintext = Files.readAllBytes(path); //copies file into byte array.
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

