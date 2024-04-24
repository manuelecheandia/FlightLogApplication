/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flightlog.websocket;

import com.flightlog.model.FlightLogEntry;
import com.google.gson.Gson;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class FlightLogEncoder implements Encoder.Text<FlightLogEntry> {
    
    private static Gson gson = new Gson();

    @Override
    public String encode(FlightLogEntry message) throws EncodeException {
        return gson.toJson(message);
    }
    
    @Override
    public void init(EndpointConfig ec) {
    }
    
    @Override
    public void destroy() {
    }

    // Add a static method to get the Gson instance
    public static Gson getGson() {
        return gson;
    }
}