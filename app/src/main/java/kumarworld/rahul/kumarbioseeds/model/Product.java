package kumarworld.rahul.kumarbioseeds.model;

import java.io.Serializable;

public class Product implements Serializable {
    String mProID,mProName,mProImage,mProCultivation;


    public Product(String mProID, String mProName, String mProImage, String mProCultivation) {
        this.mProID = mProID;
        this.mProName = mProName;
        this.mProImage = mProImage;
        this.mProCultivation = mProCultivation;
    }

    public String getmProID() {
        return mProID;
    }

    public void setmProID(String mProID) {
        this.mProID = mProID;
    }

    public String getmProName() {
        return mProName;
    }

    public void setmProName(String mProName) {
        this.mProName = mProName;
    }

    public String getmProImage() {
        return mProImage;
    }

    public void setmProImage(String mProImage) {
        this.mProImage = mProImage;
    }

    public String getmProCultivation() {
        return mProCultivation;
    }

    public void setmProCultivation(String mProCultivation) {
        this.mProCultivation = mProCultivation;
    }
}
