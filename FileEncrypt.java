/**
 * @author Thomas Veale on 9/28/2014
 *    Version 1.0
 *    Purpose: Encrypt a file passed through main Args
 *    Input: A file name - String
 *    Output: an encrypted steam [byes], method: AES CBC
 */

import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


//Notes: In attempt to make this as robust as possible, I am attempting
// to implement a generic FileType encryption class. The FileAttribute interface
// seemed like a good choice because all files share the same basic structure, their
// binary encoding; be it image, text, or number.
public class FileEncrypt{

   
    //Data Declarations
    static String IV = "AAAAAAAAAAAAAAAA";
    static File file;
    static String encKey = "0123456789abcdef";
    static String filePath = "/home/tom/Downloads/test.txt";
    static byte[] cipherText;
    static byte[] plainText;
    static String fileType;
   
   
    /**
     * @param args, arg[0] should be passed as the file path & name to be encrypted.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        getBytes(args[0]); //calls get bytes method for converting file to bytes, need some way to remember the file type.
        cipherText = doEncrypt();
        FileOutputStream FOS = new FileOutputStream(new File("test.txt"));
        FOS.write(cipherText); //pri8nts the file of cipherText
        FOS.close();
    }   
    //Constructors

    static void getBytes(String inPath)
    {
        Scanner scan = new Scanner(inPath);
        fileType = scan.next(); //save the file name for reconstruction later
        filePath = inPath; //assign global var
        file = new File(filePath); //new file to encrypt
        Path path = Paths.get(filePath); //return the path for the file
        try{
        plainText = Files.readAllBytes(path); //attempts to read the bytes from path
        }
        //some sort of runtime error here, null size buffer error
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{scan.close();}
       
    }
   
    //encryption method from David's Code
    static byte[] doEncrypt() throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText);
    }
   

}
