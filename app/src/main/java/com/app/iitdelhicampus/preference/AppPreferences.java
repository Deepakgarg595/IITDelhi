package com.app.iitdelhicampus.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.AppConstants;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.Country;
import com.app.iitdelhicampus.utility.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AppPreferences implements AppConstants {

    private static final String TAG = "AppPreferences";
    private static final String APP_PREFS = "appPrefs";
    private static final String PREFS_GUEST_CONDUCT = "PREFS_GUEST_CONDUCT";
    private static final String PREF_PASSPORT = "PREF_PASSPORT";
    private static final String PREF_NICK_NAME = "PREF_NICK_NAME";
    private static final String PREF_BANNER_RESPONSE = "PREF_BANNER_RESPONSE";
    private static final String PREF_MARRIGE_DATE = "PREF_MARRIGE_DATE";
    private static final String PREF_ROOM_NUMBER = "roomnumber";
    private static final String PREF_MY_POST_COUNT = "PREF_MY_POST_COUNT";
    private static final String PREF_BANNER_CLICK = "PREF_BANNER_CLICK";
    private static final String PREF_HOME_IMAGE = "PREF_HOME_IMAGE";
    private static final String PREFS_PROFILE_PIC = "PREFS_PROFILE_PIC";
    private static final String PREFS_WEATHER_DETAILS = "weatherDetails";
    private static final String PREFS_VENUE_DETAILS = "venueDetails";
    private static final String PREFS_PRESUNSET_DETAILS = "presunsetDetails";
    private static final String PREFS_HOTEL_DETAILS = "hotelDetails";
    private static final String PREFS_GAME_ID = "PREFS_GAME_ID";
    private static final String PREFS_LAT = "PREFS_LAT";
    private static final String PREFS_LONG = "PREFS_LONG";
    private static final String PREFS_CURRENT_LAT = "PREFS_CURRENT_LAT";
    private static final String PREFS_CURRENT_LONG = "PREFS_CURRENT_LONG";
    private static final String PREFS_WEATHER_RESPONSE = "weatherResponse";
    private static final String PREFS_UPDATED_ON = "PREFS_UPDATED_ON";
    private static final String PREFS_META_RESPONSE = "meta";
    private static final String PREFS_ITINERARY = "prog";
    private static final String PREFS_RESORT = "resort";
    private static final String PREFS_BREED = "breed";
    private static final String PREFS_PET_CATEGORY = "category";
    private static final String PREFS_PET_LIST = "petListResponse";
    private static final String PREFS_PET_NAME = "petname";
    private static final String PREFS_PET_AGE = "petage";
    private static final String PREFS_ADDRESS = "address";
    private static final String PREFS_PROFILE_IMAGE = "PREFS_PROFILE_IMAGE";
    private static final String PREFS_PET_ID = "petId";
    private static final String PREFS_CAT_NAME = "petCat";
    private static final String PREFS_OWNER_NAME = "ownerName";
    private static final String PREFS_QUALIFICATIONS = "qualification";
    private static final String PREFS_SPECIES_SPECIALISATION ="speciesSpecialisation" ;
    private static final String PREFS_REG_NO = "registrationNo";
    private static final String PREFS_EXP = "experience";
    private static final String PREFS_ON_BOARD = "onBoard";
    private static final String PREFS_SEVICE_FOR = "serviceFor";
    private static final String PREFS_SPECIALITY = "specialities";
    private static final String PREFS_CHARGES = "charges";
    private static final String PREFS_GST ="gst" ;
    private static final String PREFS_PROPERTY_TYPE = "propertyType";
    private static final String PREFS_STYLE ="style" ;
    private static final String PREFS_STAR_RATING = "starRating";
    private static final String PREFS_TIMING = "timing";
    private static final String PREFS_REGION = "region";
    private static final String PREFS_TELEPHONE ="telephone" ;
    private static final String PREFS_SOCIAL_PROFILE = "socialProfile";
    private static final String PREFS_WEEKLY_OFF = "weeklyOff";
    private static final String PREFS_PRODUCT_SUPPLIES = "productSupplies";
    private static final String PREFS_PRODUCT_LISTING = "productListing";
    private static final String PREFS_SERTVICES_OFFERED = "servicesOffered";
    private static final String PREF_KEY_APP_AUTO_START = "PREF_KEY_APP_AUTO_START";
    private static final String PREFS_ROLE_ID = "PREFS_ROLE_ID";
    private static final String PREFS_ROLE_NAME = "PREFS_ROLE_NAME";
    private static final String PREFS_SHIFT = "PREFS_SHIFT";
    private static final String PREFS_MESSAGE = "PREFS_MESSAGE";
    private static final String PREFS_POST_ = "PREFS_POST_";
    private static AppPreferences ciaoPreferences;
    String EXTRA_COLOR_POSITION = "extraColorPosition";
    private SharedPreferences mPreferences;
    private Editor mEditor;
    private Context mContext;
    private boolean ChatEmojiFlag = false;
    private TextView txvCounter;
    private String PREFS_EMAIL = "PREFS_EMAIL";
    private String PREFS_SESSION_TOKEN = "PREFS_SESSION_TOKEN";
    private String PREFS_WEDDING_SIDE = "PREFS_WEDDING_SIDE";
    private String PREFS_CREW_MANAGEMENT = "PREFS_CREW_MANAGEMENT";
    private String PREFS_DECK_NO = "PREFS_DECK_NO";
    private String PREFS_ONLINE_CHECKIN = "PREFS_ONLINE_CHECKIN";
    private String PREFS_ROOM_NO = "PREFS_ROOM_NO";
    private String PREFS_RESERVATION_ID = "PREFS_RESERVATION_ID";
    private String PREFS_SELECTED_TAB = "PREFS_SELECTED_TAB";
    private String PREFS_FIRST_TIME_VISIT = "PREFS_FIRST_TIME_VISIT";
    private String PREFS_CATEGORY = "PREFS_CATEGORY";
    private String PREFS_TYPE = "PREFS_TYPE";
    private String PREFS_TUTORIAL_READ = "PREFS_TUTORIAL_READ";
    private String PREFS_LOGGEDIN = "PREFS_LOGGEDIN";
    private String PREFS_EVENT_TYPES = "PREFS_EVENT_TYPES";
    private String PREFS_ATTEND_EVENT_TYPES = "PREFS_ATTEND_EVENT_TYPES";
    private String PREFS_CREATE_EVENT_TYPES = "PREFS_CREATE_EVENT_TYPES";

    private String PREF_ALL_ATTEND_EVENTS = "PREF_ALL_ATTEND_EVENTS";
    private String PREFS_DATE_OF_BIRTH = "PREFS_DATE_OF_BIRTH";
    private String PREFS_MOBILE = "PREFS_MOBILE";
    private String PREFS_GENDER = "PREFS_GENDER";
    private String PREFS_LOGIN_TYPE = "PREFS_LOGIN_TYPE";
    private String PREFS_FROM = "PREFS_FROM";
    private String PREFS_TO = "PREFS_TO";
    private String PREFS_FOLLOW = "PREFS_FOLLOW";
    private String PREFS_VIEW_LISTING = "PREFS_VIEW_LISTING";
    private String PREFS_GRID = "PREFS_GRID";
    private String PREFS_GPAYMENT_SUCCESS = "PREFS_GPAYMENT_SUCCESS";
    private String PREFS_EVENT_CREATED = "PREFS_EVENT_CREATED";
    private String PREFS_FOLLOWER_EVENT = "PREFS_FOLLOWER_EVENT";
    private String PREFS_FOLLOWING_EVENT = "PREFS_FOLLOWING_EVENT";
    private String PREFS_ATTENDING_COUNT = "PREFS_ATTENDING_COUNT";
    private String PREFS_UPCOMING_COUNT = "PREFS_UPCOMING_COUNT";
    private String PREFS_USER_NAME = "PREFS_USER_NAME";
    private String PREFS_DESCRIPTION = "PREFS_DESRIPTION";
    private String PREFS_PROFILE_PIC_TYPE = "PREFS_PROFILE_PIC_TYPE";
    private String PREFS_UNAME = "PREFS_UNAME";
    private String PREFS_LOGGED = "PREFS_LOGGED";
    private String PREFS_NOTIFICATION_COUNTS = "PREFS_NOTIFICATION_COUNTS";
    private String PREFS_TOTAL_EVENT_COUNTS = "PREFS_TOTAL_EVENT_COUNTS";
    private String PREF_REGISTERED_ON = "PREF_REGISTERED_ON";
    private String PREF_STATUS_NOTE = "PREF_STATUS_NOTE";
    private String PREF_SEARCH_HOSTING = "PREF_SEARCH_HOSTING";
    private String PREF_SEARCH_GOING = "PREF_SEARCH_GOING";
    private String PREFS_PIC_UPLOADED = "PREFS_PIC_UPLOADED";
    private String PREF_OPTION = "PREF_OPTION";
    private String PREF_EMAIL_VERIFIED = "PREF_EMAIL_VERIFIED";
    private String PREF_CUSTOM_PAY_STATUS = "PREF_CUSTOM_PAY_STATUS";

    private String PREF_CANCEL = "PREF_CANCEL";
    private String PREF_STATE = "PREF_STATE";
    private String PREF_FILTER_PRIVACY = "PREF_FILTER_PRIVACY";
    private String PREF_LIKE_NOTIFICATION = "PREF_LIKE_NOTIFICATION";
    private String PREF_FOLLOW_NOTIFICATION = "PREF_FOLLOW_NOTIFICATION";
    private String PREF_REFRESH_FEED = "PREF_REFRESH_FEED";
    private String PREF_NEW_EVENT_NOTIFICATION = "PREF_NEW_EVENT_NOTIFICATION";
    private String PREF_ATTENDING_NOTIFICATION = "PREF_ATTENDING_NOTIFICATION";
    private String PREF_PRIVATE_NOTIFICATION = "PREF_PRIVATE_NOTIFICATION";
    private String PREF_COMMENT_NOTIFICATION = "PREF_COMMENT_NOTIFICATION";
    private String PREF_NOTIFICATION_ON = "PREF_NOTIFICATION_ON";
    private String PREFS_BUCKET_URL = "PREFS_BUCKET_URL";
    private String PREF_SEARCH_GOING_ALL = "PREF_SEARCH_GOING_ALL";

    private String PREF_TOTAL_EVENTS = "PREF_TOTAL_EVENTS";
    private String PREF_CUSTOM_CHAT = "PREF_CUSTOM_CHAT";
    private String PREF_MAJORMINOR_STATE = "PREF_MAJORMINOR_STATE";
    private String PREF_APP_VERSION = "PREF_APP_VERSION";
    private String PREF_UPDATE_VERSION_MESSAGE = "PREF_UPDATE_VERSION_MESSAGE";
    private String PREF_EMAIL_STATUS = "PREF_EMAIL_STATUS";
    private String PREF_MYFB_USERID = "PREF_MYFB_USERID";
    private String PREFS_PAX_COUNT = "paxCount";
    private String PREFS_HOTEL_NAME = "hotel";
    private String PREFS_RESPONSE = "etd";
    private String PREFS_POST_COUNT = "counts";
    private String PREF_NINETEEN = "nineteen";
    private String PREFS_PUNCHING_DATE = "PREFS_PUNCHING_DATE";


    private AppPreferences(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static AppPreferences getInstance(Context context) {
        if (ciaoPreferences == null) {
            ciaoPreferences = new AppPreferences(context);
        }
        return ciaoPreferences;
    }

    public void removeKey(String key) {
        mEditor.remove(key).apply();
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
        ciaoPreferences = null;
    }

    public void setTxvCounter(TextView txvCounter) {
        this.txvCounter = txvCounter;
    }

    public String getUserId() {
        return mPreferences.getString(PREFS_USER_ID, null);
    }

    public void setBaseApi(String value) {
        mEditor.putString(PREFS_OPPONENT_USER_ID, value).apply();
    }


    public String getBaseApi() {
        return mPreferences.getString(PREFS_OPPONENT_USER_ID, "http://15.206.178.0");
    }

    public void setUserId(String value) {
        mEditor.putString(PREFS_USER_ID, value).apply();
    }


    public String getHeader() {
        return mPreferences.getString(PREFS_HEADER, null);
    }

    public void setHeader(String value) {
        mEditor.putString(PREFS_HEADER, value).apply();
    }

    public void setNotiCounter(int value) {
        mEditor.putInt(PREFS_NOTI_COUNT, value).apply();
        try {
            if (value < 1) {
                txvCounter.setText("");
                txvCounter.setVisibility(View.GONE);
                return;
            }
            txvCounter.setText(String.format("%s", value));
            txvCounter.setVisibility(View.VISIBLE);

        } catch (Exception e) {
        }
    }

    public String getEmail() {
        return mPreferences.getString(PREFS_EMAIL, "");
    }

    public void setEmail(String value) {
        mEditor.putString(PREFS_EMAIL, value).apply();
    }


    public String getPort() {
        return mPreferences.getString(PREFS_HOTEL_NAME, "8000");
    }

    public void setPort(String hotel) {
        mEditor.putString(PREFS_HOTEL_NAME, hotel).apply();
    }


    public String getTransport() {
        return mPreferences.getString(PREFS_RESPONSE, "");
    }

    public void setTransport(String etd) {
        mEditor.putString(PREFS_RESPONSE, etd).apply();
    }


    public boolean getProfilePicUpdated() {
        return mPreferences.getBoolean(PREF_PIC_UPDATED, false);
    }

    public void setProfilePicUpdated(boolean value) {
        mEditor.putBoolean(PREF_PIC_UPDATED, value);
    }

    public int getNotiCount() {
        return mPreferences.getInt(PREFS_NOTI_COUNT, 0);
    }

    public boolean isAppRestarted() {
        return ChatEmojiFlag;
    }

    public void setAppRestarted(boolean chatEmojiFlag) {
        ChatEmojiFlag = chatEmojiFlag;
    }

    public Boolean isDiscoverMe() {
        return mPreferences.getBoolean(PREFS_DISCOVER_ME, false);
    }

    public void setUpateState(UpdateState state) {
        mEditor.putString(AppConstants.PREFS_UPDATE_STATUS, state.name());
    }

    public UpdateState getUpdateState() {
        return UpdateState.valueOf(mPreferences.getString(AppConstants.PREFS_UPDATE_STATUS, UpdateState.NEED_TO_UPDATE.name()));
    }

    public void setDiscoverMe(boolean value) {
        mEditor.putBoolean(PREFS_DISCOVER_ME, value).apply();
    }

    public String getDeviceToken() {
        return mPreferences.getString(PREFS_DEVICE_TOKEN, null);
    }

    public void setDeviceToken(String regid) {
        mEditor.putString(PREFS_DEVICE_TOKEN, regid).apply();
    }

    public String getPhone() {
        String phone = mPreferences.getString(PREFS_PHONE_NUM, null);
//        if (!StringUtils.isNullOrEmpty(phone) && !phone.contains("+")) {
//            phone = String.format("%s%s", "+", phone.trim());
//        }
        return phone;
    }


    public void setPhone(String phone) {
        mEditor.putString(PREFS_PHONE_NUM, phone).apply();
    }


    public String getCountryCode() {
        String phone = mPreferences.getString(PREFS_COUNTRY_CODE, "");
        return phone;
    }

    public void setCountryCode(String code) {
        mEditor.putString(PREFS_COUNTRY_CODE, code).apply();
    }

    public String getMetaDataResponse() {
        String phone = mPreferences.getString(PREFS_META_RESPONSE, "");
        return phone;
    }

    public void setMetaDataResponse(String meta) {
        mEditor.putString(PREFS_META_RESPONSE, meta).apply();
    }


    public ROSTER_STATE getRosterState() {
        String state = mPreferences.getString(PREFS_ROSTER_STATE, ROSTER_STATE.NOT_SYNCED.name());
        return ROSTER_STATE.valueOf(state);
    }

    public void setRosterState(ROSTER_STATE state) {
        mEditor.putString(PREFS_ROSTER_STATE, state.name()).apply();
    }

    public AppState getAppState() {
        String state = mPreferences.getString(PREFS_APP_STATE, AppState.INSTALLED.name());
        return AppState.valueOf(state);
    }

    public void setAppState(AppState state) {
        mEditor.putString(PREFS_APP_STATE, state.name()).apply();
    }

    private LinkedHashSet<String> getStatusSet(String status) {
        LinkedHashSet<String> statusList = new LinkedHashSet<>();
        if (!TextUtils.isEmpty(status)) statusList.add(status);
        statusList.add(mContext.getString(R.string.status_default_ciaom));
        statusList.add(mContext.getString(R.string.status_available));
        statusList.add(mContext.getString(R.string.status_busy));
        statusList.add(mContext.getString(R.string.status_battery_about_to_die));
        statusList.add(mContext.getString(R.string.status_can_t_talk_vchat_only));
        statusList.add(mContext.getString(R.string.status_sleeping));
        statusList.add(mContext.getString(R.string.status_in_a_meeting));
        statusList.add(mContext.getString(R.string.status_urgent_calls_only));
        return statusList;
    }


    public String getCurrentStatus() {
        return getAllStatus().iterator().next();
    }

    public void setCurrentStatus(String status) {
        LinkedHashSet<String> allStatus = getStatusSet(status);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(bos);
            outputStream.writeObject(allStatus);
            String serializedStatusSet = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
            mEditor.putString(PREFS_ALL_STATUS, serializedStatusSet).apply();
        } catch (Exception e) {
            Log.e(TAG, "setCurrentStatus: ", e);
        }
    }

    @SuppressWarnings("unchecked")
    public Set<String> getAllStatus() {
        String serializedStatusSet = mPreferences.getString(PREFS_ALL_STATUS, null);
        if (serializedStatusSet == null) return getStatusSet(null);

        try {
            byte[] data = Base64.decode(serializedStatusSet, Base64.DEFAULT);
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream inputStream = new ObjectInputStream(bis);
            LinkedHashSet<String> allStatus = (LinkedHashSet<String>) inputStream.readObject();
            inputStream.close();
            return allStatus;
        } catch (Exception e) {
            Log.e(TAG, "getAllStatus: ", e);
        }
        return getStatusSet(null);
    }

    public int getKeyboardHeight() {
        return mPreferences.getInt(PREFS_KEYBOARD_HEIGHT, -1);
    }

    public void setKeyboardHeight(int keyboardHeight) {
        mEditor.putInt(PREFS_KEYBOARD_HEIGHT, keyboardHeight).apply();
    }

    public String getTeamLead() {
        return mPreferences.getString(PREFS_RINGTONE_TITLE, null);
    }

    public void setTeamLead(String selectedRingTone) {
        mEditor.putString(PREFS_RINGTONE_TITLE, selectedRingTone).apply();
    }

    public String getTeamLeadID() {
        return mPreferences.getString(PREFS_RINGTONE_URI, null);
    }

    public void setTeamLeadID(String selectedRingToneUri) {
        mEditor.putString(PREFS_RINGTONE_URI, selectedRingToneUri).apply();
    }

    public String getDepartment() {
        return mPreferences.getString(PREFS_RINGTONE_TITLE_GROUP, null);
    }

    public void setDepartment(String selectedRingToneGroup) {
        mEditor.putString(PREFS_RINGTONE_TITLE_GROUP, selectedRingToneGroup).apply();
    }

    public String getBranchId() {
        return mPreferences.getString(PREFS_BRANCH_ID, null);
    }

    public void setBranchId(String selectedRingToneGroup) {
        if(StringUtils.isNullOrEmpty(selectedRingToneGroup)){
            selectedRingToneGroup="";
        }
        mEditor.putString(PREFS_BRANCH_ID, selectedRingToneGroup).apply();
    }

    public String getBranchName() {
        return mPreferences.getString(PREFS_BRANCH_NAME, null);
    }

    public void setBranchName(String selectedRingToneGroup) {
        if(StringUtils.isNullOrEmpty(selectedRingToneGroup)){
            selectedRingToneGroup="";
        }
        mEditor.putString(PREFS_BRANCH_NAME, selectedRingToneGroup).apply();
    }

    public String getRingtoneUriGroup() {
        return mPreferences.getString(PREFS_RINGTONE_URI_GROUP, Settings.System.DEFAULT_NOTIFICATION_URI.toString());
    }

    public void setRingtoneUriGroup(String selectedRingToneUriGroup) {
        mEditor.putString(PREFS_RINGTONE_URI_GROUP, selectedRingToneUriGroup).apply();
    }

    public String getRingtoneTitleCall() {
        return mPreferences.getString(PREFS_RINGTONE_TITLE_CALL, "Default");
    }

    public void setRingtoneTitleCall(String selectedRingToneCall) {
        mEditor.putString(PREFS_RINGTONE_TITLE_CALL, selectedRingToneCall).apply();
    }


    public String getProgItineraryResponse() {
        return mPreferences.getString(PREFS_ITINERARY, "");
    }

    public void setProgItineraryResponse(String prog) {
        mEditor.putString(PREFS_ITINERARY, prog).apply();
    }

    public String getBreedResponse() {
        return mPreferences.getString(PREFS_BREED, "");
    }

    public void setBreedResponse(String breed) {
        mEditor.putString(PREFS_BREED, breed).apply();
    }

    public String getCategoryResponse() {
        return mPreferences.getString(PREFS_PET_CATEGORY, "");
    }

    public void setCategoryResponse(String category) {
        mEditor.putString(PREFS_PET_CATEGORY, category).apply();
    }

    public String getPetListResponse() {
        return mPreferences.getString(PREFS_PET_LIST, "");
    }

    public void setPetListResponse(String petListResponse) {
        mEditor.putString(PREFS_PET_LIST, petListResponse).apply();
    }


    public String getResortResponse() {
        return mPreferences.getString(PREFS_RESORT, "");
    }

    public void setResortResponse(String resort) {
        mEditor.putString(PREFS_RESORT, resort).apply();
    }


    public String getRingtoneUriCall() {
        return mPreferences.getString(PREFS_RINGTONE_URI_CALL, Settings.System.DEFAULT_NOTIFICATION_URI.toString());
    }

    public void setRingtoneUriCall(String selectedRingToneUriCall) {
        mEditor.putString(PREFS_RINGTONE_URI_CALL, selectedRingToneUriCall).apply();
    }

    public int getVibration() {
        return mPreferences.getInt(PREFS_VIBRATE, 1);
    }

    public void setVibration(int vibration) {
        mEditor.putInt(PREFS_VIBRATE, vibration).apply();
    }

    public int getVibrationGroup() {
        return mPreferences.getInt(PREFS_VIBRATE_GROUP, 1);
    }

    public void setVibrationGroup(int vibrationGroup) {
        mEditor.putInt(PREFS_VIBRATE_GROUP, vibrationGroup).apply();
    }

    public int getVibrationCall() {
        return mPreferences.getInt(PREFS_VIBRATE_CALL, 1);
    }

    public void setVibrationCall(int vibrationCall) {
        mEditor.putInt(PREFS_VIBRATE_CALL, vibrationCall).apply();
    }

    public boolean isShowChatMessagePreview() {
        return mPreferences.getBoolean(PREFS_SHOW_MESSAGE_PREVIEW, true);
    }

    public void setShowChatMessagePreview(boolean isChecked) {
        mEditor.putBoolean(PREFS_SHOW_MESSAGE_PREVIEW, isChecked).apply();
    }

    public boolean isAutoDownloadMedia() {
        return mPreferences.getBoolean(PREFS_AUTO_DOWNLOAD_MEDIA, true);
    }

    public void setAutoDownloadMedia(boolean isChecked) {
        mEditor.putBoolean(PREFS_AUTO_DOWNLOAD_MEDIA, isChecked).apply();
    }

    public PRIVACY getPrivacy() {
        String privacyName = mPreferences.getString(PREFS_PRIVACY_SELECTED_POSITION, PRIVACY.FRIENDS.name());
        return PRIVACY.valueOf(privacyName);
    }

    public void setPrivacy(PRIVACY privacy) {
        mEditor.putString(PREFS_PRIVACY_SELECTED_POSITION, privacy.name()).apply();
    }

    public boolean isShowLikeNotifications() {
        return mPreferences.getBoolean(PREFS_TIMELINE_SETTING_LIKES, false);
    }

    public void setLikeNotification(boolean isLike) {
        mEditor.putBoolean(PREFS_TIMELINE_SETTING_LIKES, isLike).apply();
    }


    public boolean getCommentNotification() {
        return mPreferences.getBoolean(PREFS_TIMELINE_SETTING_COMMENTS, false);
    }

    public void isShowCommentNotifications(boolean isComment) {
        mEditor.putBoolean(PREFS_TIMELINE_SETTING_COMMENTS, isComment).apply();
    }


    public boolean isShowTagNotification() {
        return mPreferences.getBoolean(PREFS_TIMELINE_SETTING_ALLOW_TAG, false);
    }

    public void setAllowTagNotification(boolean isTagNotification) {
        mEditor.putBoolean(PREFS_TIMELINE_SETTING_ALLOW_TAG, isTagNotification).apply();
    }

    public String getName() {
        return mPreferences.getString(PREFS_NAME, "");
    }

    public void setName(String name) {
        mEditor.putString(PREFS_NAME, name).apply();
    }

    public String getAddress() {
        return mPreferences.getString(PREFS_ADDRESS, "");
    }

    public void setAddress(String address) {
        mEditor.putString(PREFS_ADDRESS, address).apply();
    }

    public String getPetName() {
        return mPreferences.getString(PREFS_PET_NAME, "");
    }

    public void setPetName(String petname) {
        mEditor.putString(PREFS_PET_NAME, petname).apply();
    }

    public String getPetID() {
        return mPreferences.getString(PREFS_PET_ID, "");
    }

    public void setPetID(String petId) {
        mEditor.putString(PREFS_PET_ID, petId).apply();
    }

    public String getPetAge() {
        return mPreferences.getString(PREFS_PET_AGE, "");
    }

    public void setPetAge(String petage) {
        mEditor.putString(PREFS_PET_AGE, petage).apply();
    }

    //TODO
    public String getCategoryName() {
        return mPreferences.getString(PREFS_CAT_NAME, "");
    }

    public void setCategoryName(String petCat) {
        mEditor.putString(PREFS_CAT_NAME, petCat).apply();
    }

    public String getClinicName() {
        return mPreferences.getString(PREFS_OWNER_NAME, "");
    }

    public void setClinicName(String ownerName) {
        mEditor.putString(PREFS_OWNER_NAME, ownerName).apply();
    }

    public String getQualifications() {
        return mPreferences.getString(PREFS_QUALIFICATIONS, "");
    }

    public void setQualifications(String qualification) {
        mEditor.putString(PREFS_QUALIFICATIONS, qualification).apply();
    }

    public String getSpeciesSpecialisation() {
        return mPreferences.getString(PREFS_SPECIES_SPECIALISATION, "");
    }

    public void setSpeciesSpecialisation(String speciesSpecialisation) {
        mEditor.putString(PREFS_SPECIES_SPECIALISATION, speciesSpecialisation).apply();
    }

    public String getRegistrationNo() {
        return mPreferences.getString(PREFS_REG_NO, "");
    }

    public void setRegistrationNo(String registrationNo) {
        mEditor.putString(PREFS_REG_NO, registrationNo).apply();
    }

    public String getExperience() {
        return mPreferences.getString(PREFS_EXP, "");
    }

    public void setExperience(String experience) {
        mEditor.putString(PREFS_EXP, experience).apply();
    }

    public String getOnBoard() {
        return mPreferences.getString(PREFS_ON_BOARD, "Yes");
    }

    public void setOnBoard(String onBoard) {
        if(!StringUtils.isNullOrEmpty(onBoard))
            mEditor.putString(PREFS_ON_BOARD, onBoard).apply();
    }

    public String getServiceFor() {
        return mPreferences.getString(PREFS_SEVICE_FOR, "");
    }

    public void setServiceFor(String serviceFor) {
        mEditor.putString(PREFS_SEVICE_FOR, serviceFor).apply();
    }

    public String getSpecialities() {
        return mPreferences.getString(PREFS_SPECIALITY, "");
    }

    public void setSpecialities(String specialities) {
        mEditor.putString(PREFS_SPECIALITY, specialities).apply();
    }

    public String getCharges() {
        return mPreferences.getString(PREFS_CHARGES, "");
    }

    public void setCharges(String charges) {
        mEditor.putString(PREFS_CHARGES, charges).apply();
    }

    public String getGST() {
        return mPreferences.getString(PREFS_GST, "");
    }

    public void setGST(String gst) {
        mEditor.putString(PREFS_GST, gst).apply();
    }

    public String getPropertyType() {
        return mPreferences.getString(PREFS_PROPERTY_TYPE, "");
    }

    public void setPropertyType(String propertyType) {
        mEditor.putString(PREFS_PROPERTY_TYPE, propertyType).apply();
    }

    public String getStyle() {
        return mPreferences.getString(PREFS_STYLE, "");
    }

    public void setStyle(String style) {
        mEditor.putString(PREFS_STYLE, style).apply();
    }

    public String getStarRating() {
        return mPreferences.getString(PREFS_STAR_RATING, "");
    }

    public void setStarRating(String starRating) {
        mEditor.putString(PREFS_STAR_RATING, starRating).apply();
    }

    public String getVisitTiming() {
        return mPreferences.getString(PREFS_TIMING, "");
    }

    public void setVisitTiming(String timing) {
        mEditor.putString(PREFS_TIMING, timing).apply();
    }

    public String getRegion() {
        return mPreferences.getString(PREFS_REGION, "");
    }

    public void setRegion(String region) {
        if(!StringUtils.isNullOrEmpty(region))
            mEditor.putString(PREFS_REGION, region).apply();
    }

    public String getTelephone() {
        return mPreferences.getString(PREFS_TELEPHONE, "");
    }

    public void setTelephone(String telephone) {
        mEditor.putString(PREFS_TELEPHONE, telephone).apply();
    }

    public String getSocialProfile() {
        return mPreferences.getString(PREFS_SOCIAL_PROFILE, "");
    }

    public void setSocialProfile(String socialProfile) {
        mEditor.putString(PREFS_SOCIAL_PROFILE, socialProfile).apply();
    }

    public String getWeeklyOff() {
        return mPreferences.getString(PREFS_WEEKLY_OFF, "");
    }

    public void setWeeklyOff(String weeklyOff) {
        mEditor.putString(PREFS_WEEKLY_OFF, weeklyOff).apply();
    }

    public String getProductSupplies() {
        return mPreferences.getString(PREFS_PRODUCT_SUPPLIES, "");
    }

    public void setProductSupplies(String productSupplies) {
        mEditor.putString(PREFS_PRODUCT_SUPPLIES, productSupplies).apply();
    }

    public String getProductListing() {
        return mPreferences.getString(PREFS_PRODUCT_LISTING, "");
    }

    public void setProductListing(String productListing) {
        mEditor.putString(PREFS_PRODUCT_LISTING, productListing).apply();
    }

    public String getServicesOffered() {
        return mPreferences.getString(PREFS_SERTVICES_OFFERED, "");
    }

    public void setServicesOffered(String servicesOffered) {
        mEditor.putString(PREFS_SERTVICES_OFFERED, servicesOffered).apply();
    }
    //TODO
    public String getProfileImage() {
        return mPreferences.getString(PREFS_PROFILE_IMAGE, "");
    }

    public void setProfileImage(String petImage) {
        mEditor.putString(PREFS_PROFILE_IMAGE, petImage).apply();
    }

    public String getCiaoId() {
        return mPreferences.getString(PREFS_CIAO_ID, "");
    }

    public void setCiaoId(String value) {
        mEditor.putString(PREFS_CIAO_ID, value).apply();
    }


    public boolean getSignalTour() {
        return mPreferences.getBoolean(PREFS_SINGAL_TOUR, false);
    }

    public void setSignalTour(boolean value) {
        mEditor.putBoolean(PREFS_SINGAL_TOUR, value).apply();
    }

//    @Override
//    public String getCiaoPassword() {
//        return mPreferences.getString(PREFS_CIAO_PASSWORD, "");
//    }
//
//    public void setCiaoPassword(String value) {
//        mEditor.putString(PREFS_CIAO_PASSWORD, value).apply();
//    }

    @Override
    public Country getCountry() {
        String countryInfo = mPreferences.getString(PREFS_COUNTRY, null);
        if (TextUtils.isEmpty(countryInfo)) return null;
        return new Country(countryInfo);
    }

    public void setCountry(Country country) {
        mEditor.putString(PREFS_COUNTRY, country + "").apply();
    }

    public String getAvatarHash() {
        String avatarHash = mPreferences.getString(PREFS_AVATAR_HASH, null);
        if (avatarHash == null)
            return updateAvatarHash();
        return avatarHash;
    }

    public String setAvatarHash(String avatarHash) {
        mEditor.putString(PREFS_AVATAR_HASH, avatarHash).apply();
        return avatarHash;
    }


    public String getUpdateType() {
        return mPreferences.getString(PREFS_UPDATE_TYPE, null);
    }

    public void setUpdateType(String update) {
        mEditor.putString(PREFS_UPDATE_TYPE, update);
    }


    public String updateAvatarHash() {
        String avatarHash = String.valueOf(System.currentTimeMillis());
        return setAvatarHash(avatarHash);
    }

    public String getUserResource() {
        return mPreferences.getString(PREFS_XMPP_RESOURCE, null);
    }

    public void setUserResource(String value) {
        mEditor.putString(PREFS_XMPP_RESOURCE, value).apply();
    }

    public String getChatPassword() {
        return mPreferences.getString(PREFS_CHAT_PASSWORD, null);
    }

    public void setChatPassword(String password) {
        mEditor.putString(PREFS_CHAT_PASSWORD, password).apply();
    }

//    public SyncContactService.CONTACT_SYNC_STATE getContactSyncState() {
//        String state = mPreferences.getString(PREFS_CONTACT_SYNC_STATE, null);
//        if (state == null) return SyncContactService.CONTACT_SYNC_STATE.SYNC_NEEDED;
//        return SyncContactService.CONTACT_SYNC_STATE.valueOf(state);
//    }
//
//    public void setContactSyncState(SyncContactService.CONTACT_SYNC_STATE state) {
//        mEditor.putString(PREFS_CONTACT_SYNC_STATE, state.name()).apply();
//    }

//    ***************QB PREFERENCES**************

    public String getQbAppId() {
        return mPreferences.getString(QB_APP_ID, "");
    }

    public void setQbAppId(String qbAppId) {
        mEditor.putString(QB_APP_ID, qbAppId).apply();
    }


    public String getQbAuthSecret() {
        return mPreferences.getString(QB_AUTH_SECRET, "");
    }

    public void setQbAuthSecret(String qbAuthSecret) {
        mEditor.putString(QB_AUTH_SECRET, qbAuthSecret).apply();
    }

    public String getQbAccountKey() {
        return mPreferences.getString(QB_ACCOUNT_KEY, "");
    }

    public void setQbAccountKey(String qbAccountKey) {
        mEditor.putString(QB_ACCOUNT_KEY, qbAccountKey).apply();
    }


    public String getQbAuthKey() {
        return mPreferences.getString(QB_AUTH_KEY, "");
    }

    public void setQbAuthKey(String qbAuthKey) {
        mEditor.putString(QB_AUTH_KEY, qbAuthKey).apply();
    }

    public int getRadarDistance() {
        return mPreferences.getInt(PREFS_RADAR_DISTANCE, 50);
    }

    public void setRadarDistance(int distance) {
        mEditor.putInt(PREFS_RADAR_DISTANCE, distance).apply();
    }


    public String getFourSquareId() {
        return mPreferences.getString(PREFS_FOURSQUARE_ID, "");
    }

    public void setFourSquareId(String id) {
        mEditor.putString(PREFS_FOURSQUARE_ID, id).apply();
    }


    public String getFourSquareSecret() {
        return mPreferences.getString(PREFS_FOURSQUARE_SECRET, "");
    }

    public void setFourSquareSecret(String secret) {
        mEditor.putString(PREFS_FOURSQUARE_SECRET, secret).apply();
    }

    public <T> void storeSpiderOrder(List<T> spiders) {
        LinkedHashSet<String> order = new LinkedHashSet<>();
        Iterator<T> itr = spiders.iterator();
        while (itr.hasNext()) order.add(itr.next().toString());
        mEditor.putStringSet(PREFS_SPIDER_ORDER, order).apply();
    }

//    public List<SPIDER> getSpiders() {
//        Set<String> order = mPreferences.getStringSet(PREFS_SPIDER_ORDER, null);
//        if (order == null) return SPIDER.allAsList();
//        Iterator<String> itr = order.iterator();
//        ArrayList<SPIDER> spiders = new ArrayList<>();
//        while (itr.hasNext()) spiders.add(SPIDER.valueOf(itr.next()));
//        return spiders;
//    }


    public String isExist() {
        return mPreferences.getString(PREF_IS_EXIST, "0");
    }

    public void setIsExist(String isExist) {
        mEditor.putString(PREF_IS_EXIST, isExist).apply();
    }

    public String getSessioId() {
        return mPreferences.getString(PREF_SESSION_ID, "");
    }

    public void setSessioId(String sessioId) {
        mEditor.putString(PREF_SESSION_ID, sessioId).apply();
    }

    public boolean isBlockedListUpdated() {

        return mPreferences.getBoolean(PREFS_IS_BLOCK_UPDATED, false);
    }

    public void setIsBlockedListUpdated(boolean isBlockListUpdated) {
        mEditor.putBoolean(PREFS_IS_BLOCK_UPDATED, isBlockListUpdated);
    }

    public boolean isMatrixLoggedIn() {

        return mPreferences.getBoolean(PREFS_IS_MATRIX_LOGGED_IN, false);
    }

    public void setIsMatrixLoggedIn(boolean isMatrixLoggedIn) {
        mEditor.putBoolean(PREFS_IS_MATRIX_LOGGED_IN, isMatrixLoggedIn);
    }

    public boolean isMute() {
        return mPreferences.getBoolean(PREF_MUTE, false);
    }

    public void setMute(boolean value) {
        mEditor.putBoolean(PREF_MUTE, value).apply();
    }

//    public boolean isNineteen() {
//        return mPreferences.getBoolean(PREF_NINETEEN, false);
//    }
//
//    public void setNineteen(boolean nineteen) {
//        mEditor.putBoolean(PREF_NINETEEN, nineteen).apply();
//    }


    public String getNineteen() {
        return mPreferences.getString(PREF_NINETEEN, "");
    }

    public void setNineteen(String nineteen) {
        mEditor.putString(PREF_NINETEEN, nineteen);
    }

    public String getRoomNmber() {
        return mPreferences.getString(PREF_ROOM_NUMBER, null);
    }

    public void setRoomNmber(String roomnumber) {
        mEditor.putString(PREF_ROOM_NUMBER, roomnumber);
    }


    public String getDeviceId() {
        return mPreferences.getString(PREFS_DEVICE_ID, null);
    }

    public void setDeviceId(String value) {
        mEditor.putString(PREFS_DEVICE_ID, value);
    }


    public void setSelctedColorPosition(int position) {
        mEditor.putInt(EXTRA_COLOR_POSITION, position).apply();
    }

    public int getSelectedColorPosition() {
        return mPreferences.getInt(EXTRA_COLOR_POSITION, 0);
    }

    public String getLatestAppVersion() {
        return mPreferences.getString(PREFS_APP_VERSION, null);
    }

    public void setLatestAppVersion(String latestAppVersion) {
        mEditor.putString(PREFS_APP_VERSION, latestAppVersion).apply();
    }

    public long getAutoDownloadTime() {
        return mPreferences.getLong(PREF_AUTODOWNLOAD, System.currentTimeMillis());
    }

    public long setAutoDownloadTime(long avatarHash) {
        mEditor.putLong(PREF_AUTODOWNLOAD, avatarHash).apply();
        return avatarHash;
    }

    public long getAttendanceStartTime() {
        return mPreferences.getLong(PREFS_P_TYPE, 0);
    }

    public void setAttendanceId(Long pType) {
        mEditor.putLong(PREFS_P_TYPE, pType).apply();
    }

    public String getOtherEmpShift() {
        return mPreferences.getString(PREFS_PAX_COUNT, "IN");
    }

    public void setOtherEmpShift(String paxCount) {
        mEditor.putString(PREFS_PAX_COUNT, paxCount).apply();
    }

    public String getPunchingDate() {
        return mPreferences.getString(PREFS_PUNCHING_DATE, "");
    }

    public void setPunchingDate(String paxCount) {
        mEditor.putString(PREFS_PUNCHING_DATE, paxCount).apply();
    }


    public String getSessionToken() {
        return mPreferences.getString(PREFS_SESSION_TOKEN, "");
    }

    public void setSessionToken(String value) {
        mEditor.putString(PREFS_SESSION_TOKEN, value).apply();
    }



    public float getRadiusInMeter() {
        return Float.parseFloat(mPreferences.getString(PREFS_WEDDING_SIDE, "50"));
    }

    public void setRadiusInMeter(String value) {
        if(StringUtils.isNullOrEmpty(value)) return;
        mEditor.putString(PREFS_WEDDING_SIDE, value).apply();
    }


    public String getCrewManagement() {
        return mPreferences.getString(PREFS_CREW_MANAGEMENT, "");
    }

    public void setCrewManagement(String value) {
        mEditor.putString(PREFS_CREW_MANAGEMENT, value).apply();
    }

    public String getEmpCode() {
        return mPreferences.getString(PREFS_POST_COUNT, "");
    }

    public void setEmpCode(String counts) {
        mEditor.putString(PREFS_POST_COUNT, counts).apply();
    }

    public String getEmpCode_() {
        return mPreferences.getString(PREFS_POST_, "");
    }

    public void setEmpCode_(String counts) {
        mEditor.putString(PREFS_POST_, counts).apply();
    }


    public String getDeckNo() {
        return mPreferences.getString(PREFS_DECK_NO, "");
    }

    public void setDeckNo(String value) {
        mEditor.putString(PREFS_DECK_NO, value).apply();
    }


    public String getOnLineCheckIn() {
        return mPreferences.getString(PREFS_ONLINE_CHECKIN, "");
    }

    public void setOnLineCheckIn(String value) {
        mEditor.putString(PREFS_ONLINE_CHECKIN, value).apply();
    }

    public String getRoomNo() {
        return mPreferences.getString(PREFS_ROOM_NO, "");
    }

    public void setRoomNo(String value) {
        mEditor.putString(PREFS_ROOM_NO, value).apply();
    }

    public String getReservationId() {
        return mPreferences.getString(PREFS_RESERVATION_ID, "");
    }

//    public void setUserDocs(ArrayList<UserDocs> value) {
//        mEditor.put(PREFS_RESERVATION_ID, value).apply();
//    }
//
//
//    public String getUserDocs() {
//        return mPreferences.getString(PREFS_RESERVATION_ID, "");
//    }

    public void setReservationId(String value) {
        mEditor.putString(PREFS_RESERVATION_ID, value).apply();
    }

    public String getSelectedTab() {
        return mPreferences.getString(PREFS_SELECTED_TAB, "");
    }

    public void setSelectedTab(String value) {
        mEditor.putString(PREFS_SELECTED_TAB, value).apply();
    }

//    public boolean isFirstTime() {
//        return mPreferences.getBoolean(PREFS_FIRST_TIME_VISIT, true);
//    }
//
//    public void setFirstTime(Boolean value) {
//        mEditor.putBoolean(PREFS_FIRST_TIME_VISIT, value).apply();
//    }


    public String getCategory() {
        return mPreferences.getString(PREFS_CATEGORY, "Art,Music,Cooking,Fashion,Dance,Sports,Others,Gaming,Social,Education");
    }

    public void setCategory(String value) {
        mEditor.putString(PREFS_CATEGORY, value).apply();
    }

    public String getEventType() {
        return mPreferences.getString(PREFS_TYPE, "public");
    }

    public void setEventType(String value) {
        mEditor.putString(PREFS_TYPE, value).apply();
    }

    public String getFromDate() {
        return mPreferences.getString(PREFS_FROM, "");
    }

    public void setFromDate(String value) {
        mEditor.putString(PREFS_FROM, value).apply();
    }

    public String getToDate() {
        return mPreferences.getString(PREFS_TO, "");
    }

    public void setToDate(String value) {
        mEditor.putString(PREFS_TO, value).apply();
    }

    public boolean isTutorialRead() {
        return mPreferences.getBoolean(PREFS_TUTORIAL_READ, false);
    }

    public void setTutorialRead(Boolean value) {
        mEditor.putBoolean(PREFS_TUTORIAL_READ, value).apply();
    }

    public boolean isLoggedIn() {
        return mPreferences.getBoolean(PREFS_LOGGEDIN, false);
    }

    public void setLoggedIn(Boolean value) {
        mEditor.putBoolean(PREFS_LOGGEDIN, value).apply();
    }

    public String getDateOfBirth() {
        return mPreferences.getString(PREFS_DATE_OF_BIRTH, "");
    }

    public void setDateOfBirth(String value) {
        mEditor.putString(PREFS_DATE_OF_BIRTH, value).apply();
    }

    //    public String getMobile() {
//        return mPreferences.getString(PREFS_MOBILE, "");
//    }
//
//    public void setMobile(String value) {
//        mEditor.putString(PREFS_MOBILE, value).apply();
//    }
    public String getGender() {
        return mPreferences.getString(PREFS_GENDER, "");
    }

    public void setGender(String value) {
        mEditor.putString(PREFS_GENDER, value).apply();
    }


    public String getUserType() {
        return mPreferences.getString(PREFS_LOGIN_TYPE, "TL");
    }

    public void setUserType(String value) {
        mEditor.putString(PREFS_LOGIN_TYPE, value).apply();
    }




    public boolean isMyPost() {
        return mPreferences.getBoolean(PREFS_EVENT_TYPES, false);
    }

    public void setMyPost(Boolean value) {
        mEditor.putBoolean(PREFS_EVENT_TYPES, value).apply();
    }

    public boolean isAttendingEvent() {
        return mPreferences.getBoolean(PREFS_ATTEND_EVENT_TYPES, true);
    }

    public void setIsAttendingEvent(Boolean value) {
        mEditor.putBoolean(PREFS_ATTEND_EVENT_TYPES, value).apply();

    }


    public FilterHosting getHostingFilter() {
        String value = mPreferences.getString(PREFS_CREATE_EVENT_TYPES, FilterHosting.EVENT_ALL.name());
        return FilterHosting.valueOf(value);
    }

    public void setHostingFilter(FilterHosting value) {
        mEditor.putString(PREFS_CREATE_EVENT_TYPES, value.name()).apply();
    }


    public FilterAttending getAttendingFilter() {
        String value = mPreferences.getString(PREF_ALL_ATTEND_EVENTS, FilterAttending.EVENT_ALL.name());
        return FilterAttending.valueOf(value);
    }

    public void setAttendingFilter(FilterAttending value) {
        mEditor.putString(PREF_ALL_ATTEND_EVENTS, value.name()).apply();
    }

    public String getCategoryId() {
        return mPreferences.getString(PREFS_PRIVACY_TYPE, "0");
    }

    public void setCategoryId(String id) {
        mEditor.putString(PREFS_PRIVACY_TYPE, id).apply();
    }


    public boolean isGuest() {
        return mPreferences.getBoolean(PREFS_FOLLOW, false);
    }

    public void setGuest(Boolean value) {
        mEditor.putBoolean(PREFS_FOLLOW, value).apply();
    }

    public String getCreaterId() {
        return mPreferences.getString(PREFS_FOLLOW, null);
    }

    public void setCreaterId(String id) {
        mEditor.putString(PREFS_FOLLOW, id).apply();
    }

    public String getUserName() {
        return mPreferences.getString(PREFS_USER_NAME, null);
    }

    public void setUserName(String id) {
        mEditor.putString(PREFS_USER_NAME, id).apply();
    }

    public boolean isViewListing() {
        return mPreferences.getBoolean(PREFS_VIEW_LISTING, true);
    }

    public void setViewListing(Boolean value) {
        mEditor.putBoolean(PREFS_VIEW_LISTING, value).apply();
    }

    public boolean isGrid() {
        return mPreferences.getBoolean(PREFS_GRID, false);
    }

    public void setGrid(Boolean value) {
        mEditor.putBoolean(PREFS_GRID, value).apply();
    }


    public boolean isGridAttended() {
        return mPreferences.getBoolean(PREFS_GRID, false);
    }

    public void setGridAttended(Boolean value) {
        mEditor.putBoolean(PREFS_GRID, value).apply();
    }


    public boolean isPaymentSuccess() {
        return mPreferences.getBoolean(PREFS_GPAYMENT_SUCCESS, false);
    }

    public void setPaymentSuccess(Boolean value) {
        mEditor.putBoolean(PREFS_GPAYMENT_SUCCESS, value).apply();
    }

    public boolean isEventCreated() {
        return mPreferences.getBoolean(PREFS_EVENT_CREATED, false);
    }

    public void setEventCreated(Boolean value) {
        mEditor.putBoolean(PREFS_EVENT_CREATED, value).apply();
    }


    public void setDescription(String description) {
        mEditor.putString(PREFS_DESCRIPTION, description).apply();
    }

    public String getDescription() {
        return mPreferences.getString(PREFS_DESCRIPTION, "");
    }

    public String getProfilePicType() {
        return mPreferences.getString(PREFS_PROFILE_PIC_TYPE, Constants.Params.PROFILE_IMAGE);
    }

    public void setProfilePicType(String profilePicType) {
        mEditor.putString(PREFS_PROFILE_PIC_TYPE, profilePicType).apply();
    }


    public String getProfilePic() {
        return mPreferences.getString(PREFS_PROFILE_PIC, Constants.Params.PROFILE_IMAGE);
    }

    public void setProfilePic(String profilePicType) {
        mEditor.putString(PREFS_PROFILE_PIC, profilePicType).apply();
    }


    public void setUname(String uname) {
        mEditor.putString(PREFS_UNAME, uname).apply();
    }

    public String getUname() {
        return mPreferences.getString(PREFS_UNAME, "");
    }


    public void setRegisterdOn(String uname) {
        mEditor.putString(PREF_REGISTERED_ON, uname).apply();
    }

    public String getRegisteredOn() {
        return mPreferences.getString(PREF_REGISTERED_ON, "");
    }


    public void setStatusNote(String uname) {
        mEditor.putString(PREF_STATUS_NOTE, uname).apply();
    }

    public String getStatusNote() {
        return mPreferences.getString(PREF_STATUS_NOTE, "");
    }

    public void setEmailVerified(String uname) {
        mEditor.putString(PREF_EMAIL_VERIFIED, uname).apply();
    }

    public String getEmailVerified() {
        return mPreferences.getString(PREF_EMAIL_VERIFIED, "");
    }


    public void setCustomPayStatus(String uname) {
        mEditor.putString(PREF_CUSTOM_PAY_STATUS, uname).apply();
    }

    public String getCustomPayStatus() {
        return mPreferences.getString(PREF_CUSTOM_PAY_STATUS, "");
    }


    public void setOption(String uname) {
        mEditor.putString(PREF_OPTION, uname).apply();
    }

    public String getOption() {
        return mPreferences.getString(PREF_OPTION, "");
    }


    public void setMajorMinorState(String uname) {
        mEditor.putString(PREF_MAJORMINOR_STATE, uname).apply();
    }

    public String getMajorMinorState() {
        return mPreferences.getString(PREF_MAJORMINOR_STATE, "");
    }

    public void setAppVersionTime(String uname) {
        mEditor.putString(PREF_APP_VERSION, uname).apply();
    }

    public String getAppVersionTime() {
        return mPreferences.getString(PREF_APP_VERSION, "0");
    }

    public void setUpdateVersionMessage(String uname) {
        mEditor.putString(PREF_UPDATE_VERSION_MESSAGE, uname).apply();
    }

    public String getUpdateVersionMessage() {
        return mPreferences.getString(PREF_UPDATE_VERSION_MESSAGE, "");
    }


    public void setNotificationCount(int str) {
        Log.e("TAG", "Notification_SET=" + str);
        mEditor.putInt(PREFS_NOTIFICATION_COUNTS, str).apply();
    }

    public int getNotificationCount() {
        Log.e("TAG", "Notification_GET=" + mPreferences.getInt(PREFS_NOTIFICATION_COUNTS, 0));
        return mPreferences.getInt(PREFS_NOTIFICATION_COUNTS, 0);
    }


    public boolean isBySearch() {
        return mPreferences.getBoolean(PREF_SEARCH_HOSTING, false);
    }

    public void setBySearch(Boolean value) {
        mEditor.putBoolean(PREF_SEARCH_HOSTING, value).apply();
    }

    public boolean isBySearchAttending() {
        return mPreferences.getBoolean(PREF_SEARCH_GOING, false);
    }

    public void setBySearchAttending(Boolean value) {
        mEditor.putBoolean(PREF_SEARCH_GOING, value).apply();
    }


    public String getTotalEventCount() {
        return mPreferences.getString(PREF_TOTAL_EVENTS, "");
    }


    public void setTotalEventCount(String uname) {
        mEditor.putString(PREF_TOTAL_EVENTS, uname).apply();
    }


    public String getEmailStatus() {
        return mPreferences.getString(PREF_EMAIL_STATUS, "");
    }


    public void setEmailStatus(String uname) {
        mEditor.putString(PREF_EMAIL_STATUS, uname).apply();
    }

    public String getOtherUserId() {
        return mPreferences.getString(PREF_MYFB_USERID, "");
    }


    public void setOtherUserId(String uname) {
        mEditor.putString(PREF_MYFB_USERID, uname).apply();
    }


    public boolean isFromSpouse() {
        return mPreferences.getBoolean(PREF_SEARCH_GOING_ALL, false);
    }

    public void setFromSpouse(Boolean value) {
        mEditor.putBoolean(PREF_SEARCH_GOING_ALL, value).apply();
    }


    public boolean isByCancel() {
        return mPreferences.getBoolean(PREF_CANCEL, false);
    }

    public void setByCancel(Boolean value) {
        mEditor.putBoolean(PREF_CANCEL, value).apply();
    }

    public boolean isState() {
        return mPreferences.getBoolean(PREF_STATE, false);
    }

    public void setFilterType(String value) {
        mEditor.putString(PREF_FILTER_PRIVACY, value).apply();
    }


    public String getFilterType() {
        return mPreferences.getString(PREF_FILTER_PRIVACY, "public");
    }

    public void setState(Boolean value) {
        mEditor.putBoolean(PREF_STATE, value).apply();
    }

//
    public String getEmpProfileURL() {
        return mPreferences.getString(PREFS_BUCKET_URL, "");
    }

    public void setEmpProfileURL(String profilePicType) {
        mEditor.putString(PREFS_BUCKET_URL, profilePicType).apply();
    }

    //***************LIKE/UNLIKE********************
    public boolean isLikeNotifyEnable() {
        return mPreferences.getBoolean(PREF_LIKE_NOTIFICATION, true);
    }

    public void setLikeNotifyEnable(Boolean value) {
        mEditor.putBoolean(PREF_LIKE_NOTIFICATION, value).apply();
    }

    //    ******************NEW EVENT********************
    public boolean isNewEventNotifyEnable() {
        return mPreferences.getBoolean(PREF_NEW_EVENT_NOTIFICATION, true);
    }

    public void setNewEventNotifyEnable(Boolean value) {
        mEditor.putBoolean(PREF_NEW_EVENT_NOTIFICATION, value).apply();
    }

    //***************ATTENDING********************
    public boolean isAttendingNotifyEnable() {
        return mPreferences.getBoolean(PREF_ATTENDING_NOTIFICATION, true);
    }

    public void setAttendingNotifyEnable(Boolean value) {
        mEditor.putBoolean(PREF_ATTENDING_NOTIFICATION, value).apply();
    }

    //***************PRIVATE NOTIFY ********************
    public boolean isPrivateEventNotifyEnable() {
        return mPreferences.getBoolean(PREF_PRIVATE_NOTIFICATION, true);
    }

    public void setPrivateEventNotifyEnable(Boolean value) {
        mEditor.putBoolean(PREF_PRIVATE_NOTIFICATION, value).apply();
    }

    //***************COMMENT********************
    public boolean isCommentNotifyEnable() {
        return mPreferences.getBoolean(PREF_COMMENT_NOTIFICATION, true);
    }

    public void setCommentNotifyEnable(Boolean value) {
        mEditor.putBoolean(PREF_COMMENT_NOTIFICATION, value).apply();
    }

    //***************FOLLOW********************
    public boolean isFollowNotifyEnable() {
        return mPreferences.getBoolean(PREF_FOLLOW_NOTIFICATION, true);
    }

    public void setFollowNotifyEnable(Boolean value) {
        mEditor.putBoolean(PREF_FOLLOW_NOTIFICATION, value).apply();
    }

    //***************FOLLOW********************
    public boolean isNotifyOn() {
        return mPreferences.getBoolean(PREF_NOTIFICATION_ON, true);
    }

    public void setNotifyOn(Boolean value) {
        mEditor.putBoolean(PREF_NOTIFICATION_ON, value).apply();
    }

    public void setGuestConductStatus(String guestConductStatus) {
        mEditor.putString(PREFS_GUEST_CONDUCT, guestConductStatus).apply();
    }

    public String getGuestConductStatus() {
        return mPreferences.getString(PREFS_GUEST_CONDUCT, "");
    }

    public String getPassport() {
        return mPreferences.getString(PREF_PASSPORT, "");
    }

    public void setNickName(String guestConductStatus) {
        mEditor.putString(PREF_NICK_NAME, guestConductStatus).apply();
    }

    public void setPassport(String guestConductStatus) {
        mEditor.putString(PREF_PASSPORT, guestConductStatus).apply();
    }

    //***************FOLLOW********************
    public boolean getRefreshFeed() {
        return mPreferences.getBoolean(PREF_REFRESH_FEED, false);
    }

    public void setRefreshFeed(Boolean value) {
        mEditor.putBoolean(PREF_REFRESH_FEED, value).apply();
    }

    public String getNickName() {
        return mPreferences.getString(PREF_NICK_NAME, "");
    }

    public String getBannerResponse() {
        return mPreferences.getString(PREF_BANNER_RESPONSE, "");
    }

    public void setBannerResponse(String bannerResponse) {
        mEditor.putString(PREF_BANNER_RESPONSE, bannerResponse).apply();
    }

    public void setMarrigeDate(String marrigeDate) {
        mEditor.putString(PREF_MARRIGE_DATE, marrigeDate).apply();
    }

    public String getMarrigeDate() {
        return mPreferences.getString(PREF_MARRIGE_DATE, "0");
    }

    public void setMyPostCount(String myPostCount) {
        mEditor.putString(PREF_MY_POST_COUNT, myPostCount).apply();
    }

    public String getMyPostCount() {
        return mPreferences.getString(PREF_MY_POST_COUNT, "");
    }

    public void setBannerClick(String bannerClick) {
        mEditor.putString(PREF_BANNER_CLICK, bannerClick).apply();
    }

    public String getBannerClick() {
        return mPreferences.getString(PREF_BANNER_CLICK, "0");
    }

    public void setHomeImage(String homeImage) {
        mEditor.putString(PREF_HOME_IMAGE, homeImage).apply();
    }

    public String getHomeImage() {
        return mPreferences.getString(PREF_HOME_IMAGE, "");
    }

    public void setWeatherDetails(String weatherDetails) {
        mEditor.putString(PREFS_WEATHER_DETAILS, weatherDetails).apply();
    }

    public String getWeatherDetails() {
        return mPreferences.getString(PREFS_WEATHER_DETAILS, "");
    }

    public void setVenueDetails(String venueDetails) {
        mEditor.putString(PREFS_VENUE_DETAILS, venueDetails).apply();
    }

    public String getVenueDetails() {
        return mPreferences.getString(PREFS_VENUE_DETAILS, "");
    }

    public void setInOut(String presunsetDetails) {
        mEditor.putString(PREFS_PRESUNSET_DETAILS, presunsetDetails).apply();
    }

    public String getInOut() {
        return mPreferences.getString(PREFS_PRESUNSET_DETAILS, "");
    }

    public void setHotelDetails(String hotelDetails) {
        mEditor.putString(PREFS_HOTEL_DETAILS, hotelDetails).apply();
    }

    public String getHotelDetails() {
        return mPreferences.getString(PREFS_HOTEL_DETAILS, "");
    }

    public void setWeatherResponse(String weatherResponse) {
        mEditor.putString(PREFS_WEATHER_RESPONSE, weatherResponse).apply();
    }

    public String getWeatherResponse() {
        return mPreferences.getString(PREFS_WEATHER_RESPONSE, "");
    }

    public void setWarehouseCode(String weatherResponse) {
        mEditor.putString(PREFS_UPDATED_ON, weatherResponse).apply();
    }

    public String getWarehouseCode() {
        return mPreferences.getString(PREFS_UPDATED_ON, "0");
    }
    
    public Boolean getData() {
        return mPreferences.getBoolean(PREF_KEY_APP_AUTO_START, false);
    }

    public void setData(boolean b) {
        mEditor.putBoolean(PREF_KEY_APP_AUTO_START, b).apply();
        
    }

    public String getMobile() {
        return mPreferences.getString(PREFS_MOBILE, "");
    }

    public void setMobile(String weatherResponse) {
        mEditor.putString(PREFS_MOBILE, weatherResponse).apply();
    }


    public String getRoleId() {
        return mPreferences.getString(PREFS_ROLE_ID, "");
    }

    public void setRoleId(String weatherResponse) {
        mEditor.putString(PREFS_ROLE_ID, weatherResponse).apply();
    }

    public String getRoleName() {
        return mPreferences.getString(PREFS_ROLE_NAME, "");
    }

    public void setRoleName(String weatherResponse) {
        mEditor.putString(PREFS_ROLE_NAME, weatherResponse).apply();
    }
public String getShiftData() {
        return mPreferences.getString(PREFS_SHIFT, "");
    }

    public void setShiftData(String weatherResponse) {
        mEditor.putString(PREFS_SHIFT, weatherResponse).apply();
    }
    public String getMessage() {
        return mPreferences.getString(PREFS_MESSAGE, "");
    }

    public void setMessage(String messageId) {
        mEditor.putString(PREFS_MESSAGE, messageId).apply();

    }

    public enum FilterAttending {
        EVENT_ALL, EVENT_ATTENDING, EVENT_ATTENDED
    }

    public enum FilterHosting {
        EVENT_ALL, EVENT_HOSTING, EVENT_HOSTED
    }


    public double getStaticLatitude() {
        return Double.parseDouble(mPreferences.getString(PREFS_LAT, "0"));
    }

    public void setStaticLatitude(String value) {
        mEditor.putString(PREFS_LAT, value+"").apply();
    }

    public double getStaticLongitude() {
        return Double.parseDouble(mPreferences.getString(PREFS_LONG, "0"));
    }

    public void setStaticLongitude(String value) {
        mEditor.putString(PREFS_LONG, value+"").apply();
    }


    public double getCurrentLatitude() {
        return Double.parseDouble(mPreferences.getString(PREFS_CURRENT_LAT, "0"));
    }

    public void setCurrentLatitude(String value) {
        mEditor.putString(PREFS_CURRENT_LAT, value+"").apply();
    }

    public double getCurrentLongitude() {
        return Double.parseDouble(mPreferences.getString(PREFS_CURRENT_LONG, "0"));
    }

    public void setCurrentLongitude(String value) {
        mEditor.putString(PREFS_CURRENT_LONG, value+"").apply();
    }

}
