package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import datamodel.address_residential_show_list;
import datamodel.residential_show_list.ChargerType;
import datamodel.residential_show_list.amphere;
import datamodel.residential_show_list.charger_brand;
import datamodel.residential_show_list.connector_type;
import datamodel.residential_show_list.residential_data_object;
import datamodel.residential_show_list.residential_provider_object;

public class show_commertial {

    @SerializedName("data")
    @Expose
    private residential_data_object data;
    @SerializedName("provider")
    @Expose
    private residential_provider_object provider;
    @SerializedName("address")
    @Expose
    private address_residential_show_list address;
    @SerializedName("days_array")
    @Expose
    private List<String> daysArray = null;
    @SerializedName("time")
    @Expose
    private List<String> time = null;
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
    private Long singleDay;
    @SerializedName("slot_mon")
    @Expose
    private String slotMon;
    @SerializedName("slot_tues")
    @Expose
    private String slotTues;
    @SerializedName("slot_wed")
    @Expose
    private String slotWed;
    @SerializedName("slot_thus")
    @Expose
    private String slotThus;
    @SerializedName("slot_fri")
    @Expose
    private String slotFri;
    @SerializedName("slot_sat")
    @Expose
    private String slotSat;
    @SerializedName("slot_sun")
    @Expose
    private String slotSun;
    @SerializedName("profits")
    @Expose
    private Double profits;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("charger_type")
    @Expose
    private ChargerType charger_type;
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
    @SerializedName("voltage")
    @Expose
    private Object voltage;
    @SerializedName("amphere")
    @Expose
    private datamodel.residential_show_list.amphere amphere;
    @SerializedName("power_output")
    @Expose
    private Object powerOutput;
    @SerializedName("rate_structure")
    @Expose
    private Object rateStructure;

    public residential_data_object getData() {
        return data;
    }

    public void setData(residential_data_object data) {
        this.data = data;
    }

    public residential_provider_object getProvider() {
        return provider;
    }

    public void setProvider(residential_provider_object provider) {
        this.provider = provider;
    }

    public address_residential_show_list getAddress() {
        return address;
    }

    public void setAddress(address_residential_show_list address) {
        this.address = address;
    }

    public List<String> getDaysArray() {
        return daysArray;
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

    public Long getSingleDay() {
        return singleDay;
    }

    public void setSingleDay(Long singleDay) {
        this.singleDay = singleDay;
    }

    public String getSlotMon() {
        return slotMon;
    }

    public void setSlotMon(String slotMon) {
        this.slotMon = slotMon;
    }

    public String getSlotTues() {
        return slotTues;
    }

    public void setSlotTues(String slotTues) {
        this.slotTues = slotTues;
    }

    public String getSlotWed() {
        return slotWed;
    }

    public void setSlotWed(String slotWed) {
        this.slotWed = slotWed;
    }

    public String getSlotThus() {
        return slotThus;
    }

    public void setSlotThus(String slotThus) {
        this.slotThus = slotThus;
    }

    public String getSlotFri() {
        return slotFri;
    }

    public void setSlotFri(String slotFri) {
        this.slotFri = slotFri;
    }

    public String getSlotSat() {
        return slotSat;
    }

    public void setSlotSat(String slotSat) {
        this.slotSat = slotSat;
    }

    public String getSlotSun() {
        return slotSun;
    }

    public void setSlotSun(String slotSun) {
        this.slotSun = slotSun;
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

    public ChargerType getChargerType() {
        return charger_type;
    }

    public void setChargerType(ChargerType charger_type) {
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

    public Object getVoltage() {
        return voltage;
    }

    public void setVoltage(Object voltage) {
        this.voltage = voltage;
    }

    public Object getAmphere() {
        return amphere;
    }

    public void setAmphere(amphere amphere) {
        this.amphere = amphere;
    }

    public Object getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(String powerOutput) {
        this.powerOutput = powerOutput;
    }

    public Object getRateStructure() {
        return rateStructure;
    }

    public void setRateStructure(Object rateStructure) {
        this.rateStructure = rateStructure;
    }
}