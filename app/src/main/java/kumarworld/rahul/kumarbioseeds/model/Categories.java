package kumarworld.rahul.kumarbioseeds.model;

import java.io.Serializable;

public class Categories implements Serializable {
    String mCatID,mCatName,mCatImage;

    public Categories(String mCatID, String mCatName, String mCatImage) {
        this.mCatID = mCatID;
        this.mCatName = mCatName;
        this.mCatImage = mCatImage;
    }

    public String getmCatID() {
        return mCatID;
    }

    public void setmCatID(String mCatID) {
        this.mCatID = mCatID;
    }

    public String getmCatName() {
        return mCatName;
    }

    public void setmCatName(String mCatName) {
        this.mCatName = mCatName;
    }

    public String getmCatImage() {
        return mCatImage;
    }

    public void setmCatImage(String mCatImage) {
        this.mCatImage = mCatImage;
    }
}
