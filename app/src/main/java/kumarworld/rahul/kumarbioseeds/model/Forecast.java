package kumarworld.rahul.kumarbioseeds.model;

import java.io.Serializable;

public class Forecast implements Serializable {

    private String mCode,mDate,mDay,mHigh,mLow,mText;

    public Forecast(String mCode, String mDate, String mDay, String mHigh, String mLow, String mText) {
        this.mCode = mCode;
        this.mDate = mDate;
        this.mDay = mDay;
        this.mHigh = mHigh;
        this.mLow = mLow;
        this.mText = mText;
    }


    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmDay() {
        return mDay;
    }

    public void setmDay(String mDay) {
        this.mDay = mDay;
    }

    public String getmHigh() {
        return mHigh;
    }

    public void setmHigh(String mHigh) {
        this.mHigh = mHigh;
    }

    public String getmLow() {
        return mLow;
    }

    public void setmLow(String mLow) {
        this.mLow = mLow;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }
}
