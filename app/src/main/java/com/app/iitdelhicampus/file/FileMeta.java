package com.app.iitdelhicampus.file;

import android.widget.ImageView;


/**
 * Created by Pawan on 31/12/16.
 */
public class FileMeta {
    private ImageView imageView;
    private String fileKey;
    private String timeStamp;
    private boolean isCircular;
    private int defaultImage;
    private String downloadId;
    private DIR dirKey;
//    private RealmMessage.MSG_TYPE msgType;
    private byte[] fileData;
    private boolean writeInFile;
    private String outFile;

    public FileMeta(FileBuilder builder){
        this.imageView = builder.imageView;
        this.fileKey = builder.fileKey;
        this.timeStamp =builder.timeStamp;
        this.isCircular = builder.isCircular;
        this.defaultImage = builder.defaultImage;
        this.downloadId = builder.downloadId;
        this.dirKey = builder.dirKey;
//        this.msgType = builder.msgType;
        this.writeInFile = builder.writeInFile;
        this.outFile = builder.outFile;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getFileKey() {
        return fileKey;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public boolean isCircular() {
        return isCircular;
    }

    public int getDefaultImage() {
        return defaultImage;
    }

    public String getDownloadId() {
        return downloadId;
    }

    public DIR getDirKey() {
        return dirKey;
    }

//    public RealmMessage.MSG_TYPE getMsgType(){
//        return msgType;
//    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public boolean isWriteInFile(){
        return writeInFile;
    }

    public String getOutFile(){
        return outFile;
    }

    static class FileBuilder{

       private ImageView imageView;
       private String fileKey;
       private String timeStamp;
       private boolean isCircular;
       private int defaultImage;
       private String downloadId;
        private DIR dirKey;
//        private RealmMessage.MSG_TYPE msgType;
        private boolean writeInFile;
        private String outFile;

        public FileBuilder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public FileBuilder setFileKey(String fileKey) {
            this.fileKey = fileKey;
            return this;
        }

        public FileBuilder setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public FileBuilder setCircular(boolean circular) {
            isCircular = circular;
            return this;
        }

        public FileBuilder setDefaultImage(int defaultImage) {
            this.defaultImage = defaultImage;
            return this;
        }

        public FileBuilder setDownloadId(String downloadId) {
            this.downloadId = downloadId;
            return this;
        }

        public FileBuilder setDirKey(DIR dirkey){
            this.dirKey = dirkey;
            return this;
        }

//        public FileBuilder setMsgType(RealmMessage.MSG_TYPE msgType){
//            this.msgType = msgType;
//            return this;
//        }

        public FileBuilder setWriteInFile(boolean value){
            this.writeInFile = value;
            return this;
        }

        public FileBuilder setOutFile(String outFile){
            this.outFile = outFile;
            return this;
        }

        public FileMeta build(){
          return  new FileMeta(this);
        }

    }

    public enum DIR{
        PROFILE, CHAT;
    }
}
