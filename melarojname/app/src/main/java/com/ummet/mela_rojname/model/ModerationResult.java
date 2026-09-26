// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public class ModerationResult {
    @SerializedName("approved")
    private boolean approved;

    @SerializedName("reason")
    private String reason;

    @SerializedName("confidenceScore")
    private double confidenceScore;

    public ModerationResult() {}

    public ModerationResult(boolean approved, String reason, double confidenceScore) {
        this.approved = approved;
        this.reason = reason;
        this.confidenceScore = confidenceScore;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
}
