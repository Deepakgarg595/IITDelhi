package com.app.iitdelhicampus.utility;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteJsonFileUtils {
    Context context;
    String DIR_NAME="/RBStory_Cache/";
    private static ReadWriteJsonFileUtils instance;

    public static ReadWriteJsonFileUtils getInstance(Context context){
        if(instance==null){
            instance=new ReadWriteJsonFileUtils(context);
        }
        return instance;
    }
    private ReadWriteJsonFileUtils(Context context){
       this.context=context;
    }

    public void createJsonFileData(String directoryname, String filename, String mJsonResponse) {
//        if (Config.isDataCacheEnabled) {
            try {
                String filestr = context.getApplicationInfo().dataDir + DIR_NAME + directoryname + File.separator;
                File checkFile = new File(filestr);
                if (!checkFile.exists()) {
                    checkFile.mkdirs();
                }

                FileWriter file = new FileWriter(checkFile.getAbsolutePath() + "/" + filename);
                file.write(mJsonResponse);
                file.flush();
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
    }

    public String readJsonFileData(String directoryname, String filename) {
//        if (Config.isDataCacheEnabled) {
            try {
                File f = new File(context.getApplicationInfo().dataDir + DIR_NAME + directoryname + "/" + filename);
                if (!f.exists()) {
                    return null;
                }
                FileInputStream is = new FileInputStream(f);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                return new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
//        }

    }

    public void deleteFile() {
        File f = new File(context.getApplicationInfo().dataDir + DIR_NAME);
        File[] files = f.listFiles();
        for (File fInDir : files) {
            fInDir.delete();
        }
    }

    public void deleteFile(String directoryname, String fileName) {
        File f = new File(context.getApplicationInfo().dataDir + DIR_NAME + directoryname + "/" + fileName);
        if (f.exists()) {
            f.delete();
        }
    }
}