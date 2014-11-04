package com.tom.ironcloud;

/**
 * Created by tom on 11/3/14.
 */
public class EncryptionTask extends AES_Crypto {


    @Override
    protected Integer doInBackground(String... params) {
        return null;
    }

    //Constructors
    public EncryptionTask(String fileLocation, String Key, String IV, String limbo)
    {
        //init
        this.fileLocation = fileLocation;
        this.IV = IV;
        this.key = key;
    }
    public EncryptionTask(String fileLocation, String Key)
    {
        //init
        this.fileLocation = fileLocation;
        this.key = key;
    }
    public EncryptionTask(String fileLocation)
    {
        //init
        this.fileLocation = fileLocation;
    }

}
