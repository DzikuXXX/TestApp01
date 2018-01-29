package com.example.dzik.testapp01;

//database model class

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class EventDB {
    private int serverId;
    private String title;
    private String description;
    private String executionTime;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    EventDB(){
        this.serverId = 0;
        this.title = null;
        this.description = null;
        this.executionTime = null;
    }

    EventDB(JSONObject jsonObject) {
        try {
            this.serverId = jsonObject.getInt("id");
            this.title = jsonObject.getString("title");
            this.description = jsonObject.getString("description");
            this.executionTime = jsonObject.getString("execution_time");
        } catch (JSONException e){
            Log.wtf("json exception", e.toString());
            this.serverId = 0;
            this.title = null;
            this.description = null;
            this.executionTime = null;
        }
    }

    EventDB(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.serverId = jsonObject.getInt("id");
            this.title = jsonObject.getString("title");
            this.description = jsonObject.getString("description");
            this.executionTime = jsonObject.getString("execution_time");
        } catch (JSONException e){
            Log.wtf("jsonParser", "json exception");
            Log.wtf("jsonParser", e.toString());
            this.serverId = 0;
            this.title = null;
            this.description = null;
            this.executionTime = null;
        }
    }

    @Override
    public String toString(){
        return (String.valueOf(this.serverId) + " " + this.title + " " + this.description + " " + this.executionTime);
    }
}
