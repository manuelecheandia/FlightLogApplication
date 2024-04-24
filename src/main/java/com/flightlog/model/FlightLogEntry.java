/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flightlog.model;

/**
 *
 * @author manue
 */
public class FlightLogEntry {
    
    private String pilotName;
    private String date; 
    private String aircraft;
    private double hours;
    private String notes;

    // Constructor
    public FlightLogEntry(String pilotName, String date, String aircraft, double hours, String notes) {
        this.pilotName = pilotName;
        this.date = date;
        this.aircraft = aircraft;
        this.hours = hours;
        this.notes = notes;
    }

    // Getter and Setter methods
    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // To-string method for debugging
    @Override
    public String toString() {
        return "FlightLogEntry{" +
                "pilotName='" + pilotName + '\'' +
                ", date='" + date + '\'' +
                ", aircraft='" + aircraft + '\'' +
                ", hours=" + hours +
                ", notes='" + notes + '\'' +
                '}';
    }
    
}
