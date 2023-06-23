package com.example.cardiocare;

public class UserMeasurementDetails {
    String date,timne,systolic,dayastolic,heartrate,comment,dataid,key;

    public UserMeasurementDetails(String date, String timne, String systolic, String dayastolic, String heartrate, String comment,String key) {
        this.date = date;
        this.timne = timne;
        this.systolic = systolic;
        this.dayastolic = dayastolic;
        this.heartrate = heartrate;
        this.comment = comment;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public UserMeasurementDetails() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimne() {
        return timne;
    }

    public void setTimne(String timne) {
        this.timne = timne;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDayastolic() {
        return dayastolic;
    }

    public void setDayastolic(String dayastolic) {
        this.dayastolic = dayastolic;
    }

    public String getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
