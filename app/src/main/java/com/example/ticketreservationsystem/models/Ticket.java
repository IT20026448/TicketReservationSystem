package com.example.ticketreservationsystem.models;

public class Ticket {
    String id;
    String trainName;
    String time;
    String startLocation;
    String departureLocation;
    String createdTime;
    String lastUpdatedTime;
    int status;
    int numberOfReservations;
    String assigne;

    public Ticket(String id, String trainName, String time, String startLocation, String departureLocation, String createdTime, String lastUpdatedTime, int status, int numberOfReservations, String assigne) {
        this.id = id;
        this.trainName = trainName;
        this.time = time;
        this.startLocation = startLocation;
        this.departureLocation = departureLocation;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.status = status;
        this.numberOfReservations = numberOfReservations;
        this.assigne = assigne;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumberOfReservations() {
        return numberOfReservations;
    }

    public void setNumberOfReservations(int numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }
}
