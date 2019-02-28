package kumarworld.rahul.kumarbioseeds.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SuccessStories implements Serializable {
    private String mName,mStory,mAddress,mCity,mCreated_at,mPin_code,mMobile_no;
    private ArrayList<String>mImages;

    public SuccessStories(String mName, String mStory, String mAddress, String mCity, String mCreated_at, String mPin_code, String mMobile_no, ArrayList<String> mImages) {
        this.mName = mName;
        this.mStory = mStory;
        this.mAddress = mAddress;
        this.mCity = mCity;
        this.mCreated_at = mCreated_at;
        this.mPin_code = mPin_code;
        this.mMobile_no = mMobile_no;
        this.mImages = mImages;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmStory() {
        return mStory;
    }

    public void setmStory(String mStory) {
        this.mStory = mStory;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmCreated_at() {
        return mCreated_at;
    }

    public void setmCreated_at(String mCreated_at) {
        this.mCreated_at = mCreated_at;
    }

    public String getmPin_code() {
        return mPin_code;
    }

    public void setmPin_code(String mPin_code) {
        this.mPin_code = mPin_code;
    }

    public String getmMobile_no() {
        return mMobile_no;
    }

    public void setmMobile_no(String mMobile_no) {
        this.mMobile_no = mMobile_no;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public void setmImages(ArrayList<String> mImages) {
        this.mImages = mImages;
    }
}
