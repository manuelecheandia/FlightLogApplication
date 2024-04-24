/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flightlog.websocket;

import com.flightlog.model.FlightLogEntry;
import com.google.gson.Gson;
import jakarta.websocket.Decoder;
import jakarta.websocket.DecodeException;
import jakarta.websocket.EndpointConfig;

/**
 *
 * @author manue
 */
public class FlightLogDecoder implements Decoder.Text<FlightLogEntry> {

    private Gson gson = new Gson();

    @Override
    public FlightLogEntry decode(String inputString) throws DecodeException {
        return gson.fromJson(inputString, FlightLogEntry.class);
    }

    @Override
    public boolean willDecode(String inputString) {
        return (inputString != null);
    }

    @Override
    public void init(EndpointConfig ec) {
        
    }

    @Override
    public void destroy() {
        
    }
}