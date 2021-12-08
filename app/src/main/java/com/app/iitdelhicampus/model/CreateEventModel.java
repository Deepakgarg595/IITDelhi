package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.iitdelhicampus.utility.StringUtils;

import java.util.List;

/**
 * Created by  on 18-09-2017.
 */

public class CreateEventModel implements Parcelable {

    private static CreateEventModel createEventModel;
    private String eventName;
    private String latitude;
    private String longitude;
    private String startdateTime;
    private String endDateTime;
    private String description;
    private String location;
    private String noOfParticipants;
    private String groupMembers;
    private String lastdateOfEvent;
    private String lastdateOfCancelEvent;
    private String category;
    private String eventPrice;
    private String minAgeToJoin;
    private List<MediaDetails> mediaModel;
    private boolean isFree;
    private boolean isPaid;
    private boolean isTnCAceepted;
    private boolean isAcceptance;
    private boolean isAllowChat;
    private boolean isFbShare;

    private boolean isNewEvent;
    private String action;
    private String mediaIds;
    private String eventId;
    private String eventType;
    private boolean isCustomPay;

    public CheckListModel getCheckListModel() {
        return checkListModel;
    }

    public void setCheckListModel(CheckListModel checkListModel) {
        this.checkListModel = checkListModel;
    }

    private CheckListModel checkListModel;



    private String paymentUrl;
    private String paymentDescription;
    private String paymentType;


    protected CreateEventModel(Parcel in) {
        eventName = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        startdateTime = in.readString();
        endDateTime = in.readString();
        description = in.readString();
        location = in.readString();
        noOfParticipants = in.readString();
        groupMembers = in.readString();
        lastdateOfEvent = in.readString();
        lastdateOfCancelEvent = in.readString();
        category = in.readString();
        eventPrice = in.readString();
        minAgeToJoin = in.readString();
        mediaModel = in.createTypedArrayList(MediaDetails.CREATOR);
        isFree = in.readByte() != 0;
        isPaid = in.readByte() != 0;
        isTnCAceepted = in.readByte() != 0;
        isAcceptance = in.readByte() != 0;
        isNewEvent = in.readByte() != 0;
        action = in.readString();
        mediaIds = in.readString();
        eventId = in.readString();
        eventType = in.readString();
        isAllowChat = in.readByte() != 0;
        isFbShare = in.readByte() != 0;
        isCustomPay = in.readByte() != 0;
        paymentUrl = in.readString();
        paymentDescription = in.readString();
        paymentType = in.readString();

    }

    public static final Creator<CreateEventModel> CREATOR = new Creator<CreateEventModel>() {
        @Override
        public CreateEventModel createFromParcel(Parcel in) {
            return new CreateEventModel(in);
        }

        @Override
        public CreateEventModel[] newArray(int size) {
            return new CreateEventModel[size];
        }
    };

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getQRDescription() {
        return paymentDescription;
    }

    public void setQRDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public boolean isFbShare() {
        return isFbShare;
    }

    public void setFbShare(boolean fbShare) {
        isFbShare = fbShare;
    }

    public boolean isAllowChat() {
        return isAllowChat;
    }

    public void setAllowChat(boolean allowChat) {
        isAllowChat = allowChat;
    }

    public String getMediaIds() {
        return mediaIds;
    }

    public void setMediaIds(String mediaIds) {
        this.mediaIds = mediaIds;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public boolean isNewEvent() {
        return isNewEvent;
    }

    public void setNewEvent(boolean newEvent) {
        isNewEvent = newEvent;
    }

    private CreateEventModel() {

    }

    public static CreateEventModel getInstance(boolean isNewEvent) {
        if (isNewEvent) {
            createEventModel = new CreateEventModel();
            return createEventModel;
        } else if (createEventModel == null) {
            createEventModel = new CreateEventModel();
        }
        return createEventModel;
    }


    public boolean isAcceptance() {
        return isAcceptance;
    }

    public void setAcceptance(boolean acceptance) {
        isAcceptance = acceptance;
    }

    public boolean isTnCAceepted() {
        return isTnCAceepted;
    }

    public void setTnCAceepted(boolean tnCAceepted) {
        isTnCAceepted = tnCAceepted;
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getMinAgeToJoin() {
        return minAgeToJoin;
    }

    public void setMinAgeToJoin(String minAgeToJoin) {
        this.minAgeToJoin = minAgeToJoin;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStartdateTime() {
        return startdateTime;
    }

    public void setStartdateTime(String startdateTime) {
        this.startdateTime = startdateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNoOfParticipants() {
        return noOfParticipants;
    }

    public void setMaxNoOfParticipants(String noOfParticipants) {
        this.noOfParticipants = noOfParticipants;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getLastdateOfEvent() {
        return lastdateOfEvent;
    }

    public void setEventLastDate(String lastdateOfEvent) {
        this.lastdateOfEvent = lastdateOfEvent;
    }

    public String getLastdateOfCancelEvent() {
        return lastdateOfCancelEvent;
    }

    public void setLastdateOfCancelEvent(String lastdateOfCancelEvent) {
        this.lastdateOfCancelEvent = lastdateOfCancelEvent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public void setMediaModel(List<MediaDetails> mediaModel) {
        this.mediaModel = mediaModel;
    }

    public List<MediaDetails> getMediaModel() {
        return mediaModel;
    }

    public boolean isCustomPay() {
        return isCustomPay;
    }

    public void setCustomPay(boolean customPay) {
        isCustomPay = customPay;
    }


    public boolean isFistPageFilled() {

        if (mediaModel == null) return false;
        if (StringUtils.isNullOrEmpty(eventName)) return false;
        if (StringUtils.isNullOrEmpty(location)) return false;
        if (StringUtils.isNullOrEmpty(minAgeToJoin)) return false;

        return true;
    }

    public boolean isSecondPageFilled() {
        if (StringUtils.isNullOrEmpty(startdateTime)) return false;
        if (StringUtils.isNullOrEmpty(endDateTime)) return false;
        if (StringUtils.isNullOrEmpty(lastdateOfEvent)) return false;
        if (StringUtils.isNullOrEmpty(lastdateOfCancelEvent)) return false;
        if (StringUtils.isNullOrEmpty(noOfParticipants)) return false;
        if (isFree) return true;
        if (isCustomPay) return StringUtils.isNullOrEmpty(paymentUrl) ? false : true && StringUtils.isNullOrEmpty(paymentDescription) ? false : true;
        if (isPaid) return StringUtils.isNullOrEmpty(eventPrice) ? false : true;
        return true;
    }

    public boolean isThirdPageFilled() {
        if (StringUtils.isNullOrEmpty(description)) return false;
        if (!isTnCAceepted) return false;

        return true;
    }

    public boolean isNotEmpty() {
        if (mediaModel != null) return true;
        if (!StringUtils.isNullOrEmpty(eventName)) return true;
        if (!StringUtils.isNullOrEmpty(location)) return true;
        if (!StringUtils.isNullOrEmpty(minAgeToJoin)) return true;
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(startdateTime);
        dest.writeString(endDateTime);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(noOfParticipants);
        dest.writeString(groupMembers);
        dest.writeString(lastdateOfEvent);
        dest.writeString(lastdateOfCancelEvent);
        dest.writeString(category);
        dest.writeString(eventPrice);
        dest.writeString(minAgeToJoin);
        dest.writeTypedList(mediaModel);
        dest.writeByte((byte) (isFree ? 1 : 0));
        dest.writeByte((byte) (isPaid ? 1 : 0));
        dest.writeByte((byte) (isTnCAceepted ? 1 : 0));
        dest.writeByte((byte) (isAcceptance ? 1 : 0));
        dest.writeByte((byte) (isNewEvent ? 1 : 0));
        dest.writeString(action);
        dest.writeString(mediaIds);
        dest.writeString(eventId);
        dest.writeString(eventType);
        dest.writeByte((byte) (isAllowChat ? 1 : 0));
        dest.writeByte((byte) (isFbShare ? 1 : 0));
        dest.writeByte((byte) (isCustomPay ? 1 : 0));
        dest.writeString(paymentUrl);
        dest.writeString(paymentDescription);
        dest.writeString(paymentType);

    }
}
