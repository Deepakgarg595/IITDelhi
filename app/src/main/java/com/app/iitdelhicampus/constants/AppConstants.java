package com.app.iitdelhicampus.constants;

import com.app.iitdelhicampus.model.Country;

public interface AppConstants {

    int REQUEST_CODE_PRIVACY = 10002;
    int REQUEST_CODE_TAGGED_SELECTION = 10003;
    int REQUEST_CODE_WRITE_STATUS = 10005;
    int REQUEST_CODE_LOCATION = 5000;
    int REQUEST_PICK_CONTACT = 5001;
    int REQUEST_CODE_DOODLE = 5002;
    int REQUEST_PICK_FILE = 5003;
    int REQUEST_MULTIPLE_IMAGE_CHOOSER = 5004;
    int REQUEST_ADD_CONTACT = 5005;
    int REQUEST_PICK_VIDEO = 1001;
    int REQUEST_CODE_CAMERA = 1122;
    int REQUEST_CODE_VIDEO=1016;
    int REQUEST_PICK_IMAGE = 1003;
    int REQUEST_CAMCORDER = 1004;
    int REQUEST_SELECT_PARTICIPANTS = 2365;
    int REQUEST_GROUP_ICON = 1005;
    int REQUEST_PICK_IMAGE_MULTIPLE = 21350;
    int REQUEST_SET_PROFILE_NAME = 1111;
    int REQUEST_CODE_NICKNAME = 2001;
    int REQUEST_CODE_STATUS = 2002;
    int REQUEST_CODE_STATUS_AND_NAME = 2003;
    int REQUST_CIAO_FRIEND = 2005;
    int REQUEST_SETTINGS = 2016;
    int REQUEST_SYNC_CONTACT_AFTER_PERMISSION = 2017;
    int REQUEST_VOIP_OUTGOING_CALL = 2018;
    int REQUEST_VOIP_INCOMING_CALL = 2019;
    int REQUEST_SEND_CIAO_ID_REQUEST = 2020;
    int REQUEST_RECORD_AUDIO = 2021;
    int REQUEST_OPEN_CAMERA = 2022;
    int REQUEST_ADD_CALL_PARICIPANTS = 2023;
    int REQUEST_CODE_SCAN_CAMERA = 2024;
    int REQUEST_CODE_SYSTEM_ALERT_PERMISSIONS = 2025;
    int REQUEST_PRIVACY_MEMBER = 2026;
    int REQUEST_UPDATE_FOLLOW = 2027;
    int REQUEST_UPDATE_NOTIFICATION = 2028;
    int REQUEST_PERMISSION = 21212;
    int REQUEST_CODE_CAMERA_FOR_CREATE_EVENT = 2029;

    /**
     * Constants to be used in bundles or intents
     */

    String EXTRA_CONTACT_FORCE_SYNC = "EXTRA_CONTACT_FORCE_SYNC";
    String EXTRA_VERIFY_CODE = "EXTRA_VERIFY_CODE";
    String EXTRA_FILE_PATH = "extra_file_path";
    String EXTRA_USER_STATUS = "extra_user_status";
    String EXTRA_MULTIPLE_IMAGES = "extra_multiple_images";
    String EXTRA_MAX_IMAGE_SELECT_COUNT = "extra_max_image_select_count";
    String EXTRA_SELECTED_PARTICIPANTS = "EXTRA_SELECTED_PARTICIPANTS";
    String EXTRA_GROUP_MEMBEERS = "GROUP_MEMBERS";
    String EXTRA_SELECTION_CHOICE_MODE = "extra_selection_choice_mode";
    String EXTRA_SELECTED_COUNTRY = "extra_selected_country";
    String EXTRA_CONTACT_SIZE = "contact_size";
    String EXTRA_POST_ID = "postId";
    String EXTRA_MEDIA_ID = "extraMediaID";
    String EXTRA_SELECTED_POSITION = "selectedPosition";
    String LOCAL_BROADCAST_UPDATE_UPLOAD_PROGRESS = "local_broadcast_update_upload_progress";
    String LOCAL_BROADCAST_UPDATE_UPLOAD_PROGRESS_TIMELINE = "local_broadcast_update_upload_progress__timeline";
    String EXTRA_ACTIVITY_DISPLAY_TYPE = "EXTRA_ACTIVITY_DISPLAY_TYPE";
    String EXTRA_DISPLAY_NAME = "EXTRA_DISPLAY_NAME";
    String EXTRA_CHAT_MESSAGE = "extra_chat_message";
    String EXTRA_ID = "_id";
    String IS_NEW = "is_new";
    String EXTRA_CHAT_MESSAGE_ID = "extra_chat_message_id";
    String EXTRA_CHAT_FILE_PATH = "extra_chat_file_path";
    String EXTRA_CHAT_LOCATION = "extra_chat_location";
    String EXTRA_CHAT_FILE_IS_VIDEO = "extra_chat_file_is_video";
    String EXTRA_FILE_MODE = "extra_file_mode";
    String EXTRA_USER_ID = "extra_chat_user_id";
    String EXTRA_CONTACT = "extra_contact";
    String EXTRA_DOODLE_IMAGE = "extra_doodle_image";
    String EXTRA_CHAT_FORWARD_MESSAGES = "extra_chat_forward_messages";
    String EXTRA_SELECTED_IMAGES_TO_SEND = "extra_selected_images_to_send";
    String EXTRA_IS_CONTACT_VIEW_MODE = "extra_is_contact_view_mode";
    String EXTRA_VIEW_SIGNALS = "extra_view_signals";
    String EXTRA_CHAT_IS_SECRET = "extra_chat_is_secret";
    String EXTRA_BROADCAST_PROGRESS_COUNT = "EXTRA_BROADCAST_PROGRESS_COUNT";
    String EXTRA_DOWNLOAD_STATUS = "extra_download_status";
    String EXTRA_CHAT_DOWNLOAD_PROGRESS_MAP = "extra_chat_download_progress_map";
    String RESEND_PASSWORD = "Resend Password";
    String EXTRA_UPLOAD_PROGRESS = "extra_upload_progress";
    String MSG_SPACING_RIGHT = "&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;";
    String MSG_SPACING_LEFT = "&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;";
    String MSG_SPACING_LARGE_SENT = "&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;";
    String MSG_SPACING_LARGE_RECEIVE = "&#160;&#160;&#160;&#160;&#160;&#160;&#160;";
    String EXTRA_MEDIA = "extra_media";
    String HANGUP_MSG_USER_CANCEL = "user hangup";
    String HANGUP_MSG_NOT_DEFINED = "not defined";

    String EXTRA_MATRIX_ID = "EXTRA_MATRIX_ID";
    String EXTRA_CALL_ID = "EXTRA_CALL_ID";
    String EXTRA_AUTO_ACCEPT = "EXTRA_AUTO_ACCEPT";
    String KEY_MIC_MUTE_STATUS = "KEY_MIC_MUTE_STATUS";
    String KEY_SPEAKER_STATUS = "KEY_SPEAKER_STATUS";
    String EXTRA_IS_FROM_MY_PROFILE = "isFromMyProfile";
    String MSG_SPACING_STATUS_RIGHT = "&#160;";
    String MSG_SPACING_STATUS_LEFT = "&#160;";
    float MSG_DEFAULT_FONT_SIZE = 0;
    String IS_LOCAL_GROUP = "IS_LOCAL_GROUP";
    String GROUP_SUBJECT = "GROUP_SUBJECT";
    String LOCAL_GROUP_MEMBER = "LOCAL_GROUP_MEMBER";


    /**
     * Constants to be used for messaging
     */
    String EXTRA_PHONE_NUMBER = "phoneNumber";
    int NOTIFICATION_ID_UPLOAD = 10000;
    int NOTIFICATION_ID_CHAT = 10001;
    /*******************
     * CALL CONSTANTS
     *************************/

    String QB_APP_ID = "qbAppId";
    String QB_AUTH_KEY = "qbAuthKey";

    /***************
     * API CONSTANTS
     ******/
    String QB_AUTH_SECRET = "qbAuthSecret";
    String QB_ACCOUNT_KEY = "qbAccountKey";
    String CONFERENCE_TYPE = "conference_type";
    String CHAT_USER_ID = "chatUserId";
    String START_CONVERSATION_REASON = "start_conversation_reason";
    String PREF_IS_EXIST = "isExist";
    String PREF_SESSION_ID = "sessionId";
    String HANGUP_MSG_HEADER_UI_CALL = "user hangup from header back arrow";
    String HANGUP_MSG_BACK_KEY = "user hangup from back key";
    String CONTACT_VIEW_UDATES = "contactViewUpdates";
    /***************************
     * CHAT CONSTANT
     *********************/

//    String CHAT_DOMAIN = BuildConfig.CHAT_DOMAIN;
    int PORT = 5222;
//    String GROUP_CHAT_DOMAIN = "conference." + CHAT_DOMAIN;
    /**
     * Local broadcast receivers used for chat states and messages
     */

    String LOCAL_BROADCAST_CONNECTIVITY_CHANGE = "local_broadcast_connectivity";
    String LOCAL_BROADCAST_CONTACT_SYNC_COMPLETE = "local_broadcast_contact_sync_complete";
    String LOCAL_BROADCAST_UPDATE_DOWNLOAD_STATUS = "loacal_broadcast_update_downlaod_sttaus";
    String LOCAL_BROADCAST_UPDATE_CONTACT_LIST = "loacal_broadcast_update_contact_list";
    String EXTRA_IS_FROM_LOGIN = "isFromMyAccounts";
    String EXTRA_ROOM_ID = "extra_room_id";
    /**
     * Request code for activity result
     */

    int GROUP_ADD_PARTICIPANT = 12456;
    /**
     * File type constants
     */
    String DEFAULT_THEME_ALL = "theme_6";
    String DEFAULT_THEME_SECRET = "chat_bg_default";
    /**
     * Preferences keys
     */
    String PREFS_SINGAL_TOUR = "pref_signal_tours";
    String PREFS_RADAR_DISTANCE = "PREFS_RADAR_DISTANCE";
    String PREFS_USER_ID = "userId";
    String PREFS_OPPONENT_USER_ID = "PREFS_OPPONENT_USER_ID";
    String PREFS_HEADER = "header";
    String PREFS_CIAO_ID = "ciaoid";
    String CIAO_DOAMIN = "ciaoim.com";
    String PREFS_CIAO_PASSWORD = "ciaoPassword";
    String PREFS_UPDATE_TYPE = "updateType";
    String PREFS_NOTI_COUNT = "notiCount";
    String PREFS_DISCOVER_ME = "discoverMe";
    String PREFS_COUNTRY = "PREFS_COUNTRY";
    String PREFS_PHONE_NUM = "phoneNumber";
    String PREFS_COUNTRY_CODE = "PREFS_COUNTRY_CODE";
    String PREFS_APP_STATE = "PREFS_APP_STATE";
    String PREFS_ALL_STATUS = "prefs_all_status";
    String PREFS_DEVICE_TOKEN = "prefs_device_token";
    String PREFS_KEYBOARD_HEIGHT = "prefs_keyboard_height";
    String PREFS_RINGTONE_TITLE = "prefs_ringtone_title";
    String PREFS_RINGTONE_URI = "prefs_ringtone_uri";
    String PREFS_RINGTONE_TITLE_GROUP = "prefs_ringtone_title_group";
    String PREFS_BRANCH_ID = "PREFS_BRANCH_ID";
    String PREFS_BRANCH_NAME = "PREFS_BRANCH_NAME";
    String PREFS_RINGTONE_URI_GROUP = "prefs_ringtone_uri_group";
    String PREFS_RINGTONE_TITLE_CALL = "prefs_ringtone_title_call";
    String PREFS_RINGTONE_URI_CALL = "prefs_ringtone_uri_call";
    String PREFS_VIBRATE = "prefs_vibrate";
    String PREFS_VIBRATE_GROUP = "prefs_vibrate_group";
    String PREFS_VIBRATE_CALL = "prefs_vibrate_call";
    String PREFS_SHOW_MESSAGE_PREVIEW = "prefs_show_message_preview";
    String PREFS_AUTO_DOWNLOAD_MEDIA = "prefs_auto_download_media";
    String PREFS_PRIVACY_SELECTED_POSITION = "privacySelectedPostion";
    String PREFS_TIMELINE_SETTING_LIKES = "timeline_likes";
    String PREFS_TIMELINE_SETTING_COMMENTS = "timeline_comments";
    String PREFS_TIMELINE_SETTING_ALLOW_TAG = "timeline_allow_tag";
    String PREFS_CHAT_PASSWORD = "prefs_chat_password";
    String PREFS_AVATAR_HASH = "PREFS_AVATAR_HASH";
    String PREFS_XMPP_RESOURCE = "PREFS_XMPP_RESOURCE";
    String PREFS_NAME = "PREFS_NAME";
    String PREFS_CONTACT_SYNC_STATE = "PREFS_CONTACT_SYNC_STATE";
    String PREFS_IS_BLOCK_UPDATED = "PREFS_CONTACT_IS_BLOCK_UPDATED";
    String PREFS_FOURSQUARE_ID = "fourSquareId";
    String PREFS_FOURSQUARE_SECRET = "fourSquareSecret";
    String PREFS_SPIDER_ORDER = "PREFS_SPIDER_ORDER";
    String PREFS_PROFILE_PIC_TIMESTAMP = "profilePicTimeStamp";
    String PREFS_FOLLOWERS = "";
    String PREFS_FOLLOWING = "";
    String PREFS_POST = "";
    String PREF_MUTE = "isMute";
    String EXTRA_IS_REQUEST_SENT = "isRequestSent";
    String PREFS_THEME = "theme_6";
    String PREFS_ROSTER_STATE = "roster_state";
    String PREFS_DEVICE_ID = "deviceId";
    String PREFS_APP_VERSION = "appVersion";
    String PREFS_P_TYPE = "pType";
    String PREF_AUTODOWNLOAD = "auto_download_time";
    String PREFS_IS_MATRIX_LOGGED_IN = "PREFS_IS_MATRIX_LOGGED_IN";
    String PREFS_PRIVACY_TYPE = "PREFS_PRIVACY_TYPE";

    /**
     * downloading constant
     **/
    String DEVICE_TYPE = "android";
    String BROADCAST_UPDATE_ROSTER_STATUS = "roster_status";
    String BROADCAST_ROSTER_STATUS = "rosterstatus";
    String EXTRA_VALUE = "ex_value";
    String EXTRA_DOWNLOADING_PERCENTAGE = "extra_dwonloading_percentage";
    String BROADCAST_UPDATE_PERCENTAGE = "update_percentage";
    String LOCAL_BROADCAST_USER_CALL_STATUS = "user_connected";
    String EXTRA_CALL_CONNECTED = "callConnected";
    String PREF_PIC_UPDATED = "picUpdated";


    String KEY_SUBSCRIPTION = "subscription";
    String KEY_SENDBY = "sendby";
    String KEY_ISCONTACT = "iscontact";
    String EXTRA_UPDATE_PTYPE = "updatePtype";
    String PREFS_UPDATE_STATUS = "updateStatus";
    String CIAOWEBRTC_DOMAIN = ":ciaortc.ciaoim.com";
    String STREAMING_URL="https://d15pv7010pvej0.cloudfront.net/profile/";

    /*CONTACTS constant*/
    int SYNC_DONE_FLASE = 0;
    int SYNC_DONE_TRUE = 1;
    int ADD_CONTACT = 0;
    int SYNC_CONTACT = 1;
    int DELETE_CONTACT = 2;
    String BROADCAST_LIKE_UNLIKE = "broadcastLikeUnlike";
    int REQUEST_CODE_CROP_VIDEO=10278;

    Country getCountry();


    /**
     * downloading constant
     **/
    enum AppState {
        INSTALLED,
        TUTORIAL_ACCEPTED,
        NUMBER_VERIFIED,
        PIC_SELECTED,
        PROFILE_UPDATED,
        CONTACT_SYNCED,
        GROUP_SYNCED,
//        OTP_SENT,
//        MOBILE_NUMBER

    }

    enum UpdateState {
        NEED_TO_UPDATE,
        UPDATED
    }

    enum ROSTER_STATE {
        NOT_SYNCED,
        SYNCED
    }

    enum PRIVACY {
        FRIENDS,
        ONLY_ME,
        TAGGED
    }

    interface PARAMS {
        String DEVICEID = "deviceId";
        String LATITUDE = "lat";
        String LONGITUDE = "long";
        String PHONE = "phone";
        String CODE = "code";
        String OS_VERSION = "osVersion";
        String body = "body";
        String DEVICE_TYPE = "deviceType";
        String ALLOW_ME_RADAR = "allowMeRadar";
        String SETTINGS = "settings";
        String USER_ID = "userId";
        String MIRROR = "mirror";
        String PAGE_NO = "pageNo";
        String LIGHT = "light";
        String APP_VERSION = "appVersion";
        String SWITCH = "switch";
    }

    interface QUERY_URL {
        String UPDATE_LOCATION = "users/v1/updateLocation";
        String GET_USER_FOR_RADAR = "users/v1/getUsersForRadar";
        String UPDATE_RADAR_SETTING = "users/v1/updateRadarSetting";
        String UPDATE_SETTINGS = "users/v1/updateSettings";
        String QR_CODE = "users/v1/scanQrCode";
        String DELETE_CIAO_ACCOUNT = "users/v1/deleteAccount";
        String RESEND_OTP = "login/v1/sendOtp";
        String VERIFY_OTP = "login/v1/verifyUser";
        String SEND_OTP = "login/v1/sendOtp";
        String FORGOT_PASSWORD = "login/v1/forgotPassword";
        String FORGOT_CIAOID = "login/v1/forgotCiaoId";
        String LOGIN = "login/v1/loginByCiaoId";
        String DELETE_ACCOUNT = "login/v1/deleteAccount";
        String CREATE_WEBRTC_ROOM = "create";
        String SUGGEST_FRIEND = "/users/v1/suggestFriendNew";//"users/v1/suggestFriend";
        String GET_APP_VERSION = "/login/v1/checkAppVersion";
        String CONFERENCE_ADD_USER_ROOM = "addusertoroom";
        String CONFERENCE_REMOVE_USER_ROOM = "kickme";
    }

    interface APP_PERMISSIONS {
        // Android M permission request code management
        public static final boolean PERMISSIONS_GRANTED = true;
        public static final boolean PERMISSIONS_DENIED = !PERMISSIONS_GRANTED;
        public static final int PERMISSION_CAMERA = 0x1;
        public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 0x1 << 1;
        public static final int PERMISSION_RECORD_AUDIO = 0x1 << 2;
        public static final int PERMISSION_READ_CONTACTS = 0x1 << 3;
        public static final int REQUEST_CODE_PERMISSION_AUDIO_IP_CALL = PERMISSION_RECORD_AUDIO;
        public static final int REQUEST_CODE_PERMISSION_VIDEO_IP_CALL = PERMISSION_CAMERA | PERMISSION_RECORD_AUDIO;
        public static final int REQUEST_CODE_PERMISSION_TAKE_PHOTO = PERMISSION_CAMERA | PERMISSION_WRITE_EXTERNAL_STORAGE;
        public static final int REQUEST_CODE_PERMISSION_MEMBERS_SEARCH = PERMISSION_READ_CONTACTS;
        public static final int REQUEST_CODE_PERMISSION_MEMBER_DETAILS = PERMISSION_READ_CONTACTS;
        public static final int REQUEST_CODE_PERMISSION_ROOM_DETAILS = PERMISSION_CAMERA;
        public static final int REQUEST_CODE_PERMISSION_HOME_ACTIVITY = PERMISSION_WRITE_EXTERNAL_STORAGE;
    }

    interface SUBSCRIPTION {
        String SEND = "S";
        String ACCEPT = "A";
        String DECLINE = "D";
        String CANCEL = "C";
        String SYNC = "sync";
    }


    interface CALL {
        String EVENT = "event";
        String EVENT_ACTION = "eventAction";
        String CALL = "call";
        String VIDEO = "video";
        String VIDEO_STOP_SHARING = "stopVideoSharing";
        String VIDEO_START_SHARING = "startVideoSharing";
        String CALL_PAUSE_CIAO_CALL = "pauseCiaoCall";
        String CALL_RESUME_CIAO_CALL = "resumeCiaoCall";
        String USER_NO_ANSWER = "userNoAnswer";
        String BUSY_CALL = "busyCall";
    }

    interface APIURLKeys {
        String API_URL = "apiUrl";
        String DEVICE_ID = "deviceId";
        String SEND_OTP = "send_opt";
        String VERIFY_USER = "verify_user";
    }


}