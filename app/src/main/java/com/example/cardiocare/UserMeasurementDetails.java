package com.example.cardiocare;

/**
 * Class representing user measurement details.
 * Contains information about a specific measurement, such as date, time, systolic value, diastolic value, heart rate, comment, and key.
 */
public class UserMeasurementDetails {
    String date, timne, systolic, dayastolic, heartrate, comment, dataid, key;

    /**
     * Constructor for UserMeasurementDetails.
     * Initializes the measurement details with the provided values.
     *
     * @param date       The date of the measurement.
     * @param timne      The time of the measurement.
     * @param systolic   The systolic value of the measurement.
     * @param dayastolic The diastolic value of the measurement.
     * @param heartrate  The heart rate value of the measurement.
     * @param comment    Any additional comment for the measurement.
     * @param key        The key associated with the measurement.
     */
    public UserMeasurementDetails(String date, String timne, String systolic, String dayastolic, String heartrate, String comment, String key) {
        this.date = date;
        this.timne = timne;
        this.systolic = systolic;
        this.dayastolic = dayastolic;
        this.heartrate = heartrate;
        this.comment = comment;
        this.key = key;
    }


    /**
     * Empty constructor for UserMeasurementDetails.
     * Required for Firebase database operations.
     */
    public UserMeasurementDetails() {
    }

    /**
     * Get the key associated with the measurement.
     *
     * @return The key of the measurement.
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the key associated with the measurement.
     *
     * @param key The key to set.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get the data ID of the measurement.
     *
     * @return The data ID of the measurement.
     */
    public String getDataid() {
        return dataid;
    }

    /**
     * Set the data ID of the measurement.
     *
     * @param dataid The data ID to set.
     */
    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    /**
     * Get the date of the measurement.
     *
     * @return The date of the measurement.
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of the measurement.
     *
     * @param date The date to set.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the time of the measurement.
     *
     * @return The time of the measurement.
     */
    public String getTimne() {
        return timne;
    }

    /**
     * Set the time of the measurement.
     *
     * @param timne The time to set.
     */
    public void setTimne(String timne) {
        this.timne = timne;
    }

    /**
     * Get the systolic value of the measurement.
     *
     * @return The systolic value of the measurement.
     */
    public String getSystolic() {
        return systolic;
    }

    /**
     * Set the systolic value of the measurement.
     *
     * @param systolic The systolic value to set.
     */
    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    /**
     * Get the diastolic value of the measurement.
     *
     * @return The diastolic value of the measurement.
     */
    public String getDayastolic() {
        return dayastolic;
    }

    /**
     * Set the diastolic value of the measurement.
     *
     * @param dayastolic The diastolic value to set.
     */
    public void setDayastolic(String dayastolic) {
        this.dayastolic = dayastolic;
    }

    /**
     * Get the heart rate value of the measurement.
     *
     * @return The heart rate value of the measurement.
     */
    public String getHeartrate() {
        return heartrate;
    }

    /**
     * Set the heart rate value of the measurement.
     *
     * @param heartrate The heart rate value to set.
     */
    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    /**
     * Get the additional comment for the measurement.
     *
     * @return The comment for the measurement.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the additional comment for the measurement.
     *
     * @param comment The comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
