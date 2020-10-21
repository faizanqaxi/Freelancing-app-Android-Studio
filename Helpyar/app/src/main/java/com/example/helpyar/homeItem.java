package com.example.helpyar;

public class homeItem {

    String jobId;
    String title;
    String amount;
    String posted_date;
    String jobStatus;
    String jobPostedByUserId;
    String jobPostedByUserName;


    public homeItem(String jobId, String title, String amount, String posted_date, String jobStatus, String jobPostedByUserId, String jobPostedByUserName) {
        this.jobId = jobId;
        this.title = title;
        this.amount = amount;
        this.posted_date = posted_date;
        this.jobStatus = jobStatus;
        this.jobPostedByUserId = jobPostedByUserId;
        this.jobPostedByUserName = jobPostedByUserName;
    }


    public String getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public String getAmount() {
        return amount;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public String getJobPostedByUserId() {
        return jobPostedByUserId;
    }

    public String getJobPostedByUserName() {
        return jobPostedByUserName;
    }

}
