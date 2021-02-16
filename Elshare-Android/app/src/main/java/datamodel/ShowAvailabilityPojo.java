package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import datamodel.residential_show_list.charger_brand;
import datamodel.residential_show_list.charger_model;
import datamodel.residential_show_list.connector_type;

public class ShowAvailabilityPojo
{
    @SerializedName("data")
    @Expose
    private ShowAvailabilityData data;
    @SerializedName("provider")
    @Expose
    private ShowAvailabilityProvider provider;
    @SerializedName("time")
    @Expose
    private List<String> time = null;
    @SerializedName("days_array")
    @Expose
    private List<String> daysArray = null;
    @SerializedName("start_time")
    @Expose
    private List<String> startTime = null;
    @SerializedName("end_time")
    @Expose
    private List<String> endTime = null;
    @SerializedName("start_time_mon")
    @Expose
    private List<String> startTimeMon = null;
    @SerializedName("end_time_mon")
    @Expose
    private List<String> endTimeMon = null;
    @SerializedName("start_time_tues")
    @Expose
    private List<String> startTimeTues = null;
    @SerializedName("end_time_tues")
    @Expose
    private List<String> endTimeTues = null;
    @SerializedName("start_time_wed")
    @Expose
    private List<String> startTimeWed = null;
    @SerializedName("end_time_wed")
    @Expose
    private List<String> endTimeWed = null;
    @SerializedName("start_time_thus")
    @Expose
    private List<String> startTimeThus = null;
    @SerializedName("end_time_thus")
    @Expose
    private List<String> endTimeThus = null;
    @SerializedName("start_time_fri")
    @Expose
    private List<String> startTimeFri = null;
    @SerializedName("end_time_fri")
    @Expose
    private List<String> endTimeFri = null;
    @SerializedName("start_time_sat")
    @Expose
    private List<String> startTimeSat = null;
    @SerializedName("end_time_sat")
    @Expose
    private List<String> endTimeSat = null;
    @SerializedName("start_time_sun")
    @Expose
    private List<String> startTimeSun = null;
    @SerializedName("end_time_sun")
    @Expose
    private List<String> endTimeSun = null;
    @SerializedName("single_day")
    @Expose
    private String singleDay;


    @SerializedName("profits")
    @Expose
    private Double profits;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("charger_type")
    @Expose
    private String charger_type;
    @SerializedName("charger_brand")
    @Expose
    private charger_brand chargerBrand;
    @SerializedName("connector_type")
    @Expose
    private connector_type connectorType;
    @SerializedName("charger_model")
    @Expose
    private charger_model chargerModel;
    @SerializedName("socket")
    @Expose
    private Object socket;


    public ShowAvailabilityData getData() {
        return data;
    }

    public void setData(ShowAvailabilityData data) {
        this.data = data;
    }


    public ShowAvailabilityProvider getProvider() {
        return provider;
    }

    public void setProvider(ShowAvailabilityProvider provider) {
        this.provider = provider;
    }

    public List<String> getDaysArray() {
        return  daysArray;
    }

    public void setDaysArray(List<String> daysArray) {
        this.daysArray = daysArray;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<String> getStartTime() {
        return startTime;
    }

    public void setStartTime(List<String> startTime) {
        this.startTime = startTime;
    }

    public List<String> getEndTime() {
        return endTime;
    }

    public void setEndTime(List<String> endTime) {
        this.endTime = endTime;
    }

    public List<String> getStartTimeMon() {
        return startTimeMon;
    }

    public void setStartTimeMon(List<String> startTimeMon) {
        this.startTimeMon = startTimeMon;
    }

    public List<String> getEndTimeMon() {
        return endTimeMon;
    }

    public void setEndTimeMon(List<String> endTimeMon) {
        this.endTimeMon = endTimeMon;
    }

    public List<String> getStartTimeTues() {
        return startTimeTues;
    }

    public void setStartTimeTues(List<String> startTimeTues) {
        this.startTimeTues = startTimeTues;
    }

    public List<String> getEndTimeTues() {
        return endTimeTues;
    }

    public void setEndTimeTues(List<String> endTimeTues) {
        this.endTimeTues = endTimeTues;
    }

    public List<String> getStartTimeWed() {
        return startTimeWed;
    }

    public void setStartTimeWed(List<String> startTimeWed) {
        this.startTimeWed = startTimeWed;
    }

    public List<String> getEndTimeWed() {
        return endTimeWed;
    }

    public void setEndTimeWed(List<String> endTimeWed) {
        this.endTimeWed = endTimeWed;
    }

    public List<String> getStartTimeThus() {
        return startTimeThus;
    }

    public void setStartTimeThus(List<String> startTimeThus) {
        this.startTimeThus = startTimeThus;
    }

    public List<String> getEndTimeThus() {
        return endTimeThus;
    }

    public void setEndTimeThus(List<String> endTimeThus) {
        this.endTimeThus = endTimeThus;
    }

    public List<String> getStartTimeFri() {
        return startTimeFri;
    }

    public void setStartTimeFri(List<String> startTimeFri) {
        this.startTimeFri = startTimeFri;
    }

    public List<String> getEndTimeFri() {
        return endTimeFri;
    }

    public void setEndTimeFri(List<String> endTimeFri) {
        this.endTimeFri = endTimeFri;
    }

    public List<String> getStartTimeSat() {
        return startTimeSat;
    }

    public void setStartTimeSat(List<String> startTimeSat) {
        this.startTimeSat = startTimeSat;
    }

    public List<String> getEndTimeSat() {
        return endTimeSat;
    }

    public void setEndTimeSat(List<String> endTimeSat) {
        this.endTimeSat = endTimeSat;
    }

    public List<String> getStartTimeSun() {
        return startTimeSun;
    }

    public void setStartTimeSun(List<String> startTimeSun) {
        this.startTimeSun = startTimeSun;
    }

    public List<String> getEndTimeSun() {
        return endTimeSun;
    }

    public void setEndTimeSun(List<String> endTimeSun) {
        this.endTimeSun = endTimeSun;
    }

    public String getSingleDay() {
        return singleDay;
    }

    public void setSingleDay(String singleDay) {
        this.singleDay = singleDay;
    }



    public Double getProfits() {
        return profits;
    }

    public void setProfits(Double profits) {
        this.profits = profits;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getChargerType() {
        return charger_type;
    }

    public void setChargerType(String charger_type) {
        this.charger_type = charger_type;
    }

    public charger_brand getChargerBrand() {
        return chargerBrand;
    }

    public void setChargerBrand(charger_brand chargerBrand) {
        this.chargerBrand = chargerBrand;
    }

    public connector_type getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(connector_type connectorType) {
        this.connectorType = connectorType;
    }

    public charger_model getChargerModel() {
        return chargerModel;
    }

    public void setChargerModel(charger_model chargerModel) {
        this.chargerModel = chargerModel;
    }

    public Object getSocket() {
        return socket;
    }

    public void setSocket(Object socket) {
        this.socket = socket;
    }

}
