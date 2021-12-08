package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaDetails implements Parcelable {
    private String media;
    private String thumbNail;
    private String type;
    private String imageUrl;
    private boolean isSelected;
    private int position;
    private String name;
    private String mode;
//    private static MediaDetails mediaDetails;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }




    protected MediaDetails(Parcel in) {
        media = in.readString();
        thumbNail = in.readString();
        type = in.readString();
        imageUrl = in.readString();
        isSelected = in.readByte() != 0;
        position = in.readInt();
        name = in.readString();
        isVideo = in.readByte() != 0;
        mediaId = in.readString();
        mode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(media);
        dest.writeString(thumbNail);
        dest.writeString(type);
        dest.writeString(imageUrl);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeInt(position);
        dest.writeString(name);
        dest.writeByte((byte) (isVideo ? 1 : 0));
        dest.writeString(mediaId);
        dest.writeString(mode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaDetails> CREATOR = new Creator<MediaDetails>() {
        @Override
        public MediaDetails createFromParcel(Parcel in) {
            return new MediaDetails(in);
        }

        @Override
        public MediaDetails[] newArray(int size) {
            return new MediaDetails[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    private boolean isVideo;

//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }
//
//    private int image;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    private String mediaId;

    public MediaDetails() {
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public static MediaDetails getInstance(boolean isNewEvent) {
//        if (isNewEvent) {
//            mediaDetails = new MediaDetails();
//            return mediaDetails;
//        } else if (mediaDetails == null)
//            mediaDetails = new MediaDetails();
//        return mediaDetails;
//    }
//

}
