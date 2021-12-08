package com.app.iitdelhicampus.constants;


import com.app.iitdelhicampus.application.BaseApplication;
import com.app.iitdelhicampus.preference.AppPreferences;

/**
 * Created by pawan on 29/3/17.
 */

public interface Constants {

    int REQUEST_IMAGE_CAMERA = 1002;
    int REQUEST_IMAGE_GALLERY = 1003;
    public static final String DateSaveFormat = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    int CROP_IMAGE = 1004;
    int REQUEST_VIDEO = 1005;
    String EXTRA_SELECTED_COUNTRY = "extra_selected_country";
    int REQUEST_PICK_IMAGE_MULTIPLE = 1007;
    int REQUEST_PICK_IMAGE = 1008;
    int REQUEST_MULTIPLE_IMAGE_CHOOSER = 5004;
    int REQUEST_CATEGORY = 1010;
    int REQUEST_EDIT_GROUP = 1011;
    int GPS_REQUEST = 1;
    int REQUEST_COMMENT_COUNT = 1012;
    int REQUEST_PARTICIPANTS_COUNT = 2013;

    int REQUEST_CODE_IN_SELF = 101;
    int REQUEST_CODE_OUT_SELF = 102;
    int REQUEST_CODE_IN_OUT_OTHERS = 103;
    int REQUEST_CODE_OUT_OTHERS = 102;
    int REQUEST_COMMENT_COUNT_REQUESTED = 1023;
    int REQUEST_TOTAL_COUNT = 1033;
    int REQUEST_TOTAL_COUNT_FOLLOW = 1044;
    int REQUEST_FOR_DATA_ZOOMED = 1013;
    int REQUEST_IS_PROFILE_UPDATED = 10903;
    int REQUEST_CODE = 1090;
    int REQUEST_CODE_LOCAL_REMINDER = 10019;
    int REQUEST_QR_CODE = 1211;
   String COLLECTION_ATTENDANCE="mark_attendance";


    //    String BASE_URL = "http://whatsaround.approutes.com/";
//    String BASE_URL ="http://whatsarounddev.approutes.com/";  https://admin.seearound.co/
//    String BASE_URL = BuildConfig.FLAVOR.equalsIgnoreCase("development")?"https://apidev.seearound.co/":"https://api.seearound.co/";
//    String BASE_URL = "https://apidev.seearound.co/";

    //    String BASE_URL = ""/*Nlh.i().baseUrl()*/; /*"https://api.seearound.co/"; */// production    //*Nlh.i().baseUrl()*/;
//    String MEDIA_URL = ""/*Nlh.i().mediaUrl()*/;/*"https://d3169i19d5uage.cloudfront.net/"*/
    // video Url // also in eventDetailAdapter and utility


    String BASE_URL = AppPreferences.getInstance(BaseApplication.getInstance()).getBaseApi() + ":" + AppPreferences.getInstance(BaseApplication.getInstance()).getPort();
    //            "http://15.206.178.0:8000/api/v1/users/verifyUser?mobile=";
    String MEDIA_URL = "https://astrostar.s3-eu-west-1.amazonaws.com/";
    String WEB_URL = "https://fullypetweb.approutes.com/";
    String CHECK_USER = BASE_URL + "/api/v1/users/verifyUser?mobile=";
    String SAVE_TOKEN = BASE_URL + "/api/v1/users/saveDeviceToken";
    String GETALL_CLIENTLIST = BASE_URL + "/api/v1/client/getAll";
    String SAVE_QR_TAG=BASE_URL+"/api/v1/client/tagQr";
    String GET_CLIENT_BY_QR_CODE=BASE_URL+"/api/v1/client/getClientByQrcode?qrCode=";
    String SAVE_INCIDENT_REPORT=BASE_URL+"/api/v1/report/saveIncidentReport";
    String SAVE_SITE_OBSERVATION=BASE_URL+"/api/v1/report/saveSiteObservation";
    String REPORT_BY_USER_ID=BASE_URL+"/api/v1/report/incidentReportByUserId?userId=";
    String SITE_OBSERVATION_BY_USER_ID=BASE_URL+"/api/v1/report/siteObservationByUserId?userId=";

    String MESSAGE="https://os.orionwach.com/api/sosmsg.ashx?command=getmessage";
    String MAP="https://orionwach.com/api/wmap.json";
//    String LOG_COMPLAIN="https://orionwach.com/api/jsonapi.ashx?command=logcomplain";
    String UPDATE_ATTENDANCE="https://os.orionwach.com/api/punch.ashx?command=punchlog";
    String SOS="https://os.orionwach.com/api/sosmsg.ashx?command=sosmessage";
    String UPDATE_PROFILE="https://os.orionwach.com/api/employee.ashx?command=update_profile";
    String OFFLINE_DATA="https://orionwach.com/api/jsonapi.ashx?command=offlinesync";
    String FETCH_EMPLOYEE="https://orionwach.com/api/jsonapi.ashx?command=logemployee";
    String USER_LOGIN="https://os.orionwach.com/api/login.ashx?command=loginuser_latlog";
//    String USER_LOGIN="https://orionwach.com/api/jsonapi.ashx?command=userlogin";
    String USER_LOGIN_MANAGER="https://orionwach.com/app/jsonapi.ashx?command=managerlogin";
    String WAREHOUSE_LIST="https://orionwach.com/app/jsonapi.ashx?command=logwarehouselist";
    String SHIFT_DATA="https://orionwach.com/app/jsonapi.ashx?command=employeeshift";
    String MUSTER_DATA="https://orionwach.com/app/jsonapi.ashx?command=employeemuster";
    String UPDATE_SHIFT_DATA="https://orionwach.com/app/jsonapi.ashx?command=updateemployeeshift";
    String SHIFT_MASTER="https://orionwach.com/app/jsonapi.ashx?command=logshiftlist";
    String UPDATE_DEVICE_DETAIL="https://os.orionwach.com/api/log.ashx?command=logdevicestatus";

    String GKEE = "fPlWhVpoUMKQ9g4No0Cda/k2A66QG5P9jCTmHOgALgOBgQJ7aCHkoDN1qZjG0uli\n";

    String DEVICE_TYPE_ANDROID = "android";
    String EXTRA_DATA = "extraData";
    String EXTRA_EMP_CODE = "EXTRA_EMP_CODE";
    String NEED_TO_EDIT = "NEED_TO_EDIT";
    String COMMENT_DATA = "commentData";
    String EXTRA_EVENT_DATA = "extraCurrentEventData";
    String SCANNED_DATA_LIST = "ScannedTicketData";
    String EXTRA_DATA_LIKE_COUNT = "EXTRA_DATA_LIKE_COUNT";

    String TICKET_ID = "ticketId";
    String TICKET_DETAIL = "ticketDetail";
    String EXTRA_MAX_IMAGE_SELECT_COUNT = "EXTRA_MAX_IMAGE_SELECT_COUNT";
    String EXTRA_MULTIPLE_IMAGES = "EXTRA_MULTIPLE_IMAGES";
    String EXTRA_SELECTED_IMAGES_TO_SEND = "EXTRA_SELECTED_IMAGES_TO_SEND";
    String EXTRA_CREATER_ID = "createrId";
//        String BITBUCKET_URL = "https://s3-eu-west-1.amazonaws.com/whatsaround-images/";

    String MEDIA_URL_DEFAULT = MEDIA_URL + "defaultMedia/";
    String MEDIA_URL_DEFAULT_THUMBNAIL = MEDIA_URL + "large";
    String CHANGE = "change";
    String TOTAL = "total";
    String CLOUDNET_URL = "https://d3tjw7dbwkjiu9.cloudfront.net/";
    String BROADCAST_GAME_DATA = "BROADCAST_GAME_DATA";
    String BROADCAST_GAME_ACCEPT_REJECT = "BROADCAST_GAME_ACCEPT_REJECT";

//    String LOCAL_BROADCAST_CONNECTIVITY_CHANGE = "local_broadcast_connectivity";



    public enum NOTIFICATION_TYPE {
        appointment, none
    }

    interface Params {
        //TODO wirte your params here

        String BASEURL = "baseurl";
        String USER_ID = "userId";
        String URL = "url";
        String TOKEN = "token";
        String DEVICE_TYPE = "deviceType";
        String DEVICE_ID = "deviceId";
        String REQUEST_ID = "requestId";
        String SESSION_TOCKEN = "sessionToken";
        String CATEGORY_NAME = "categoryName";
        String OWNER_NAME = "ownerName";
        String SPECIES_SPECIALISATION = "speciesSpecialisation";
        String REGISTRATION_NO = "registrationNo";
        String EXPERIENCE = "experience";
        String ON_BOARD = "onBoard";
        String SERVICE_FOR = "serviceFor";
        String SPECIALITIES = "specialities";
        String CHARGES = "charges";
        String GST = "gst";
        String PROPERTY_TYPE = "PropertyType";
        String STYLE = "style";
        String STAR_RATING = "starRating";
        String TIMING = "timings";
        String REGION = "region";
        String TELEPHONE = "telephone";
        String MOBILE = "mobile";
        String SOCIAL_PROFILE = "socialProfile";
        String WEEKLY_OFF = "weeklyOff";
        String PRODUCT_SUPPLIES = "productSupplies";
        String PRODUCT_LISTING = "productListing";
        String SERVICES_OFFERED = "servicesOffered";
        String QUALIFICATIONS = "qualifications";
        String NOTIFICATION_ID = "notificationId";
        String ACCEPT_REJECT = "acceptReject";

        String MEDIA_URL = "mediaUrl";
        String TYPE = "type";
        String LATITUDE_LOCATION = "latitude";
        String LONGITUDE_LOCATION = "longitude";
        String NAME_LOCATION = "name";
        String APP_TYPE = "appType";
        String PHONE = "phone";
        String LOGIN_TYPE = "loginType";
        String CODE = "code";
        String GENDER = "gender";
        String EMAIL = "email";
        String DOB = "dob";
        String COUNTRY_CODE = "countryCode";
        String SOCIAL_ID = "socialId";

        String PROFILE_TYPE = "profileType";
        String PROFILE_IMAGE = "image";
        String PROFILE_VIDEO = "video";
        String EXTRA_IS_VIDEO = "extra_is_video";
        String UNAME = "uname";
        String TOTALEVENTS = "totalevents";
        String EVENT_CANCEL_DATE = "cancelDate";
        String EXTRA_SEARCH_TEXT = "EXTRA_SEARCH_TEXT";
        String NAME = "name";
        String IMAGE = "image";
        String ADDRESS = "address";
        String LAT = "lat";
        String LNG = "lng";
        String EVENT_PAGE_NO = "eventPageNo";
        String USER_PAGE_NO = "userPageNo";
        String ACTION = "action";
        String ORGANIZER_USER_ID = "organizerUserId";
        String RATING = "rating";
        String REGISTERED_ON = "registeredOn";
        String STATUS_NOTE = "statusNote";
        String NEARBY = "nearBy";
        String WITHDRAW_AMOUNT = "withdrawAmount";
        String PAYMENT_MODE = "paymentMode";
        String SWIFT = "swift";
        String IBAN = "iban";
        String IFSC = "ifsc";
        String PAYPAL_EMAIL = "payPalEmail";
        String REQUEST_DATE = "requestDate";
        String TEXT_DESCRIPTION = "textDescription";
        String DOC_TYPE = "docType";
        String PROFILE_CHECK = "profileCheck";
        String OPTION = "option";
        String OTHER_USERID = "otherUserId";
        String MEDIA_ID = "mediaIds";
        String MIN_AGE = "minAge";
        String ACCEPT_REQUEST = "eventPermission";
        String ALLOW_CHAT = "chatAllowed";
        String PARTICIPANT_ID = "parcipateUserId";
        String IS_ATTENDING = "isAttending";
        String IS_BY_CANCEL = "byCancel";
        String PAYMENT_TYPE = "paymentType";
        String PAYMENT_DESCRIPTION = "paymentDescription";
        String PAYMENT_LINK = "paymentUrl";
        String REQUEST_DATA = "requestData";
        String MESSAGE = "message";
        String NOTIFICATION_FRAGMENT = "notificationFragment";
        String USER_TAG_FRIEND = "userTagList";

        String ROOM_NO = "roomNo";

        String PLAYER_1 = "playerOne";
        String PLAYER_2 = "playerTwo";
        String GAME_ID = "gameId";
        String PHOTO_BOOTH = "isPhotoBooth";

        String PET_CATEGORY = "petCategory";
        String PET_NAME = "petName";
        String PET_GENDER = "petGender";
        String PET_BREED = "petBreed";
        String PET_AGE = "petAge";
        String PET_CATEGORY_ID = "petCategoryId";
        String PROFILE_PIC = "profilePic";
    }

    interface Services {
        //TODO wirte services name here
        //RiYal api
        String SEND_OTP = BASE_URL + "login/v1/sendOtp";
        String VERIFY_USER = BASE_URL + "login/v1/verifyUser";
        String LOGOUT_PET = BASE_URL + "events/v1/logout";
        String GET_META_DATA = BASE_URL + "events/v1/getMetaData";
        String NOTIFICATION_LIST = BASE_URL + "users/v1/getNotification";
        String VET_PROFILE = BASE_URL + "users/v1/updateAllProfile";
        String ACCEPT_REJECT = BASE_URL + "users/v1/acceptRejectNotification";
        String UPDATE_PROFILE_PIC = BASE_URL + "users/v1/updateProfilePic";


    }

    interface RequestType {
        //RiYal
        int SEND_OTP = 101;
        int VET_PROFILE = 102;
        int VERIFY_USER = 107;
        int NOTIFICATION_LIST = 125;
        int GET_META_DATA = 198;
        int LOGOUT_PET = 215;
        int ACCEPT_REJECT = 105;


        int UPDATE_PROFILE_PIC = 216;
        int GETALL_CLIENTLIST = 218;
        int SAVE_QR_TAG=219;
        int GET_CLIENT_BY_QR_CODE=220;
        int SAVE_INCIDENT_REPORT=221;
        int SAVE_SITE_OBSERVATION=222;
        int REPORT_BY_USER_ID=223;
        int SITE_OBSERVATION_BY_USER_ID = 224;
        int UPDATE_ATTENDANCE=225;
        int FETCH_EMPLOYEE=226;
        int USER_LOGIN=227;
        int UPDATE_DEVICE_DETAIL=228;
        int LOG_COMPLAIN=229;
        int OFFLINE_DATA=230;


        int UPDATE_ATTENDANCE_LOGOUT = 231;
        int UPDATE_AUTO_OUT_IN12HOURS = 232;
        int USER_LOGIN_MANAGER = 233;
        int WAREHOUSE_LIST = 234;
        int SHIFT_MASTER = 235;
        int SHIFT_DATA = 236;
        int UPDATE_SHIFT_DATA = 237;
        int MUSTER_DATA = 238;
        int MESSAGE = 239;
        int SOS = 240;
        int MAP = 241;
        int UPDATE_PROFILE = 242;
    }

    interface EventCategory {
        String CATEGORY_ART = "Art";
        String CATEGORY_MUSIC = "Music";
        String CATAGORY_COOKING = "Cooking";
        String CATAGORY_DANCE = "Dance";
        String CATAGORY_FASHION = "Fashion";
        String CATAGORY_SPORTS = "Sports";
        String CATAGORY_OTHERS = "Others";
        String CATAGORY_GAMING = "Gaming";
        String CATAGORY_SOCIAL = "Social";
        String CATAGORY_EDUCATION = "Education";
    }


    interface MediaType {
        String PIC = "1";
    }

    interface LoginType {
        String FB = "fb";

    }

    interface Location {
        int LOCATION = 1006;
    }






    interface AttendingState {
        String NO_ATTENDING = "noAttending";
        String ATTENDING = "Attending";
        String REQUESTED = "Requested";
        String REJECTED = "Rejected";
        String ACCEPTED = "Accepted";
        String NONE = "none";
    }

    interface TransactionType {
        String FREE = "free";
        String PAID_FOR_EVENT = "payment";
        String REFUND = "refund";
        String REFUND_FREE = "refundFree";
        String WITHDRAW = "withDraw";
        String PAYMENT_RECEIVED = "totalEventAmount";
        String WITHDRAW_APPROVED = "withDrawApproved";

    }

    interface DeteteEventStates {
        String ACTIVE = "Active";
        String DELETE_BY_ADMIN = "deletedByAdmin";
        String DELETE_BY_CREATER = "deletedByCreator";
    }

    interface waitingStatus {
        String AVAILABLE = "available";
        String WAITING = "waiting";
    }

    interface PaymentType {
        String PAYPAL = "PP";
        String GOOGLE = "GP";
    }

    interface SelectedTab {
        String HOME_PARENT = "homeParent";
        String HOME_CHILD = "homeChild";
        String HOME_DOCUMENT = "homeDOCUMENT";
        String PROFILE = "profile";
    }


    interface CurrentTab {
        String HOST_TAB = "HostTab";
        String HOST_ALL_TAB = "HostAllTab";
        String HOSTING_TAB = "Hosting";
        String HOSTED_TAB = "Hosted";

        String GO_TAB = "GoTab";
        String GOING_ALL_TAB = "GoingAllTab";
        String GOING_TAB = "GoingTab";
        String GONE_TAB = "GoneTab";

        String REQUESTED_TAB = "RequestedTab";


        String FOLLOWER_TAB = "FollowerTab";
        String FOLLOWING_TAB = "FollowingTab";
        String FEEDBACK_TAB = "FeedbackTab";


        String FACEBOOK = "facebook";
        String CONTACTS = "contacts";

    }


}