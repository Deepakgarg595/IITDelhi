package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LoginModel implements Parcelable {
    private boolean status;
    private String userId;
    private String loginKey;
    private String updatedOn;
    private String requestId;
    private String sessionToken;
    private String email;
    private String firstName;
    private String gender;
    private String uname;
    private String mobile;
    private String isProfileUpdated;
    private String fullNumber;
    private int isExist;
    private String dob;
    private String countryCode;
    private String statusNote;
    private String description;
    private String address;
    private String city;
    private String state;
    private String pinCode;

    private String currentPage;
    private String nextPage;
    //    private String 19th;
//    private String 20th;
//    private String 21st;
    private String isLogin;
    private String roomType;
    private String extraBed;
    private String isCall;
    private String marrigeDate;
    private String checkOut;
    private String remarks;
    private String loginType;

    private String message;
    private String adminType;
    private String registeredOn;
    private String adminNotify;

    private String categoryName;
    private String ownerName;
    private String qualifications;
    private String speciesSpecialisation;
    private String registrationNo;
    private String experience;
    private String onBoard;
    private String serviceFor;
    private String specialities;
    private String charges;
    private String gst;
    private String PropertyType;
    private String style;
    private String starRating;
//    private String timings;
    private String region;
    private String telephone;
    private String socialProfile;
    private String weeklyOff;
    private String productSupplies;
    private String productListing;
    private String servicesOffered;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    private String categoryId;


//    private ArrayList<GuestFilterModel> guestFilters;
    private ArrayList<AllUserDetailModel> allUserDetail;
    private ArrayList<AllUserDetailModel> allUsers;
    private ArrayList<AllUserDetailModel> allGroup;

    protected LoginModel(Parcel in) {
        status = in.readByte() != 0;
        userId = in.readString();
        loginKey = in.readString();
        updatedOn = in.readString();
        requestId = in.readString();
        sessionToken = in.readString();
        email = in.readString();
        firstName = in.readString();
        gender = in.readString();
        uname = in.readString();
        mobile = in.readString();
        isProfileUpdated = in.readString();
        fullNumber = in.readString();
        isExist = in.readInt();
        dob = in.readString();
        countryCode = in.readString();
        statusNote = in.readString();
        description = in.readString();
        address = in.readString();
        city = in.readString();
        state = in.readString();
        pinCode = in.readString();
        currentPage = in.readString();
        nextPage = in.readString();
        isLogin = in.readString();
        roomType = in.readString();
        extraBed = in.readString();
        isCall = in.readString();
        marrigeDate = in.readString();
        checkOut = in.readString();
        remarks = in.readString();
        loginType = in.readString();
        message = in.readString();
        adminType = in.readString();
        registeredOn = in.readString();
        adminNotify = in.readString();
        categoryName = in.readString();
        ownerName = in.readString();
        qualifications = in.readString();
        speciesSpecialisation = in.readString();
        registrationNo = in.readString();
        experience = in.readString();
        onBoard = in.readString();
        serviceFor = in.readString();
        specialities = in.readString();
        charges = in.readString();
        gst = in.readString();
        PropertyType = in.readString();
        style = in.readString();
        starRating = in.readString();
//        timings = in.readString();
        region = in.readString();
        telephone = in.readString();
        socialProfile = in.readString();
        weeklyOff = in.readString();
        productSupplies = in.readString();
        productListing = in.readString();
        servicesOffered = in.readString();
        categoryId=in.readString();
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsProfileUpdated() {
        return isProfileUpdated;
    }

    public void setIsProfileUpdated(String isProfileUpdated) {
        this.isProfileUpdated = isProfileUpdated;
    }

    public String getFullNumber() {
        return fullNumber;
    }

    public void setFullNumber(String fullNumber) {
        this.fullNumber = fullNumber;
    }

    public int getIsExist() {
        return isExist;
    }

    public void setIsExist(int isExist) {
        this.isExist = isExist;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStatusNote() {
        return statusNote;
    }

    public void setStatusNote(String statusNote) {
        this.statusNote = statusNote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getExtraBed() {
        return extraBed;
    }

    public void setExtraBed(String extraBed) {
        this.extraBed = extraBed;
    }

    public String getIsCall() {
        return isCall;
    }

    public void setIsCall(String isCall) {
        this.isCall = isCall;
    }

    public String getMarrigeDate() {
        return marrigeDate;
    }

    public void setMarrigeDate(String marrigeDate) {
        this.marrigeDate = marrigeDate;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getAdminNotify() {
        return adminNotify;
    }

    public void setAdminNotify(String adminNotify) {
        this.adminNotify = adminNotify;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getSpeciesSpecialisation() {
        return speciesSpecialisation;
    }

    public void setSpeciesSpecialisation(String speciesSpecialisation) {
        this.speciesSpecialisation = speciesSpecialisation;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getOnBoard() {
        return onBoard;
    }

    public void setOnBoard(String onBoard) {
        this.onBoard = onBoard;
    }

    public String getServiceFor() {
        return serviceFor;
    }

    public void setServiceFor(String serviceFor) {
        this.serviceFor = serviceFor;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public void setPropertyType(String propertyType) {
        PropertyType = propertyType;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

//    public String getTimings() {
//        return timings;
//    }
//
//    public void setTimings(String timings) {
//        this.timings = timings;
//    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSocialProfile() {
        return socialProfile;
    }

    public void setSocialProfile(String socialProfile) {
        this.socialProfile = socialProfile;
    }

    public String getWeeklyOff() {
        return weeklyOff;
    }

    public void setWeeklyOff(String weeklyOff) {
        this.weeklyOff = weeklyOff;
    }

    public String getProductSupplies() {
        return productSupplies;
    }

    public void setProductSupplies(String productSupplies) {
        this.productSupplies = productSupplies;
    }

    public String getProductListing() {
        return productListing;
    }

    public void setProductListing(String productListing) {
        this.productListing = productListing;
    }

    public String getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(String servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    public ArrayList<AllUserDetailModel> getAllUserDetail() {
        return allUserDetail;
    }

    public void setAllUserDetail(ArrayList<AllUserDetailModel> allUserDetail) {
        this.allUserDetail = allUserDetail;
    }

    public ArrayList<AllUserDetailModel> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(ArrayList<AllUserDetailModel> allUsers) {
        this.allUsers = allUsers;
    }

    public ArrayList<AllUserDetailModel> getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(ArrayList<AllUserDetailModel> allGroup) {
        this.allGroup = allGroup;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(userId);
        parcel.writeString(loginKey);
        parcel.writeString(updatedOn);
        parcel.writeString(requestId);
        parcel.writeString(sessionToken);
        parcel.writeString(email);
        parcel.writeString(firstName);
        parcel.writeString(gender);
        parcel.writeString(uname);
        parcel.writeString(mobile);
        parcel.writeString(isProfileUpdated);
        parcel.writeString(fullNumber);
        parcel.writeInt(isExist);
        parcel.writeString(dob);
        parcel.writeString(countryCode);
        parcel.writeString(statusNote);
        parcel.writeString(description);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(pinCode);
        parcel.writeString(currentPage);
        parcel.writeString(nextPage);
        parcel.writeString(isLogin);
        parcel.writeString(roomType);
        parcel.writeString(extraBed);
        parcel.writeString(isCall);
        parcel.writeString(marrigeDate);
        parcel.writeString(checkOut);
        parcel.writeString(remarks);
        parcel.writeString(loginType);
        parcel.writeString(message);
        parcel.writeString(adminType);
        parcel.writeString(registeredOn);
        parcel.writeString(adminNotify);
        parcel.writeString(categoryName);
        parcel.writeString(ownerName);
        parcel.writeString(qualifications);
        parcel.writeString(speciesSpecialisation);
        parcel.writeString(registrationNo);
        parcel.writeString(experience);
        parcel.writeString(onBoard);
        parcel.writeString(serviceFor);
        parcel.writeString(specialities);
        parcel.writeString(charges);
        parcel.writeString(gst);
        parcel.writeString(PropertyType);
        parcel.writeString(style);
        parcel.writeString(starRating);
//        parcel.writeString(timings);
        parcel.writeString(region);
        parcel.writeString(telephone);
        parcel.writeString(socialProfile);
        parcel.writeString(weeklyOff);
        parcel.writeString(productSupplies);
        parcel.writeString(productListing);
        parcel.writeString(servicesOffered);
        parcel.writeString(categoryId);
    }
}

