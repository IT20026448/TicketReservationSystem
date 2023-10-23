package com.example.ticketreservationsystem.models;

// Create model to save reservation details in database
public class Reservation {
    // private Reservation class attributes
    private String id;
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String trainId;
    private String trainName;
    private String trainTime;
    private String trainStartLocation;
    private String trainDepartureLocation;
    private String travelAgentId;
    private String travelAgentName;
    private int type;

    public Reservation(String id, String userId, String userName, String userEmail, String userPhone, String trainId, String trainName, String trainTime, String trainStartLocation, String trainDepartureLocation, String travelAgentId, String travelAgentName, int type) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.trainId = trainId;
        this.trainName = trainName;
        this.trainTime = trainTime;
        this.trainStartLocation = trainStartLocation;
        this.trainDepartureLocation = trainDepartureLocation;
        this.travelAgentId = travelAgentId;
        this.travelAgentName = travelAgentName;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(String trainTime) {
        this.trainTime = trainTime;
    }

    public String getTrainStartLocation() {
        return trainStartLocation;
    }

    public void setTrainStartLocation(String trainStartLocation) {
        this.trainStartLocation = trainStartLocation;
    }

    public String getTrainDepartureLocation() {
        return trainDepartureLocation;
    }

    public void setTrainDepartureLocation(String trainDepartureLocation) {
        this.trainDepartureLocation = trainDepartureLocation;
    }

    public String getTravelAgentId() {
        return travelAgentId;
    }

    public void setTravelAgentId(String travelAgentId) {
        this.travelAgentId = travelAgentId;
    }

    public String getTravelAgentName() {
        return travelAgentName;
    }

    public void setTravelAgentName(String travelAgentName) {
        this.travelAgentName = travelAgentName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
