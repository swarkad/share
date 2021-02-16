package datamodel;


import android.app.Notification;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import Booking_history_pojo.HostBookingHistoryPojo;
import Booking_history_pojo.hostParticularBookingDetail;
import commertial_pojo.commertial_charger_amphere;
import commertial_pojo.commertial_socket_amphere;
import datamodel.residential_show_list.show_residential;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import public_pojo.public_charger_amphere;
import public_pojo.public_socket_amphere;
import residential_pojo.residential_charger_amphere;
import residential_pojo.residential_socket_amphere;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @FormUrlEncoded
    @POST("register")
    Call<Register> createUser(
            @Query("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<Register> userLogin(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/add_driver_listing/{id}")
    Call<ResponseBody> driver_add(
            @Path("id") int id,
            @Query("make") String make,
            @Query("model") String model,
            @Query("year") String year,
            @Query("plate") String licence,
            @Query("battery_size") String bat_size,
            @Query("charger_size") String charger_size,
            @Field("type") String type);

    @FormUrlEncoded
    @POST("auth/edit_driver_listing/{id}/{vehicle_id}")
    Call<ResponseBody> driverEdit(
            @Path("id") int id,
            @Path("vehicle_id") int vehicleId,
            @Query("make") String make,
            @Query("model") String model,
            @Query("year") String year,
            @Query("plate") String licence,
            @Query("battery_size") String bat_size,
            @Query("charger_size") String charger_size,
            @Field("type") String type);

    @FormUrlEncoded
    @POST("auth/listing/public/{id}")
    Call<ResponseBody> add_public_list2(
            @Path("id") int id,
            @Query("city") String city,
            @Query("address1_public") String address1,
            @Query("address2_public") String address2,
            @Query("landmark") String landmark,
            @Query("state") String state,
            @Query("country") String country,
            @Query("voltage") String voltage,
            @Query("amphere") String amphere_p,
            @Query("profit") String profit,
            @Query("rate_public") String rate,
            @Query("pin") int pin,
            @Query("lat") float lat,
            @Query("lng") float lng,
            @Query("selected") int selected,
            @Query("checked") int checked,
            @Query("electricity_board") String board,
            @Query("charger_type") String charger_type,
            @Query("connector_type_public") String connector,
            @Query("charger_brand_public") String brand,
            @Query("model_public") String model,
            @Query("power_output") String power,
            @Query("custom_brand_public") ArrayList<String> custm_brand,
            @Query("custom_model_public") ArrayList<String> custum_model,
            @Query("socket_public") String socket_public,
            @Query("custom_connector_type_public") ArrayList<String> custom_connector_type_public,
            @Query("custom_voltage_public") ArrayList<String> custom_voltage_public,
            @Query("custom_power_output_public") ArrayList<String> custom_power_output_public,
            @Query("selected_days[]") ArrayList<String> selected_days,
            @Query("selected_day[]") ArrayList<String> selected_day,
            @Query("start_time[]") ArrayList<String> start_time,
            @Query("end_time[]") ArrayList<String> end_time,
            @Query("start_time_mon[]") ArrayList<String> start_time_mon,
            @Query("end_time_mon[]") ArrayList<String> end_time_mon,
            @Query("start_time_tues[]") ArrayList<String> start_time_tues,
            @Query("end_time_tues[]") ArrayList<String> end_time_tues,
            @Query("start_time_wed[]") ArrayList<String> start_time_wed,
            @Query("end_time_wed[]") ArrayList<String> end_time_wed,
            @Query("start_time_thus[]") ArrayList<String> start_time_thus,
            @Query("end_time_thus[]") ArrayList<String> end_time_thus,
            @Query("start_time_fri[]") ArrayList<String> start_time_fri,
            @Query("end_time_fri[]") ArrayList<String> end_time_fri,
            @Query("start_time_sat[]") ArrayList<String> start_time_sat,
            @Query("end_time_sat[]") ArrayList<String> end_time_sat,
            @Query("start_time_sun[]") ArrayList<String> start_time_sun,
            @Query("end_time_sun[]") ArrayList<String> end_time_sun,
            @Query("start_time_all") String[] start_time_all_public,
            @Query("end_time_all") String[] end_time_all_public,
            @Query("add_day") String add_day,
            @Query("add_day_mon") String add_day_mon,
            @Query("add_day_tues") String add_day_tues,
            @Query("add_day_wed") String add_day_wed,
            @Query("add_day_thus") String add_day_thus,
            @Query("add_day_fri") String add_day_fri,
            @Query("add_day_sat") String add_day_sat,
            @Field("add_day_sun") String add_day_sun,
            @Query("public_info") String public_info,
            @Query("private_info") String private_info);

    @FormUrlEncoded
    @POST("auth/listing/residential/{id}")
    Call<ResponseBody> add_residential_list2(
            @Path("id") int id,
            @Query("address1_residential") String address1,
            @Query("address2_residential") String address2,
            @Query("landmark") String landmark,
            @Query("city") String city,
            @Query("state") String state,
            @Query("country") String country,
            @Query("lat") float lat,
            @Query("lng") float lng,
            @Query("pin") int pin,
            @Query("electricity_board") String board,
            @Query("rate_residential") String rate,
            @Query("profit") String profit,
            @Query("selected") int selected,
            @Query("charger_type") String charger_type,
            @Query("connector_type_residential") String connector,
            @Query("charger_brand_residential") String brand,
            @Query("voltage") String voltage,
            @Query("amphere") String amphere,
            @Query("checked") int checked,
            @Query("model_residential") String model,
            @Query("power_output") String power,
            @Query("custom_brand_residential") ArrayList<String> custm_brand,
            @Query("custom_model_residential") ArrayList<String> custum_model,
            @Query("socket_residential") String socket,
            @Query("custom_connector_type_residential") ArrayList<String> custom_connector_type,
            @Query("custom_voltage_residential") ArrayList<String> custom_voltage,
            @Query("custom_power_output_residential") ArrayList<String> custom_power_output,
            @Query("selected_days[]") ArrayList<String> selected_days,
            @Query("selected_day[]") ArrayList<String> selected_day,
            @Query("start_time[]") ArrayList<String> start_time,
            @Query("end_time[]") ArrayList<String> end_time,
            @Query("start_time_mon[]") ArrayList<String> start_time_mon,
            @Query("end_time_mon[]") ArrayList<String> end_time_mon,
            @Query("start_time_tues[]") ArrayList<String> start_time_tues,
            @Query("end_time_tues[]") ArrayList<String> end_time_tues,
            @Query("start_time_wed[]") ArrayList<String> start_time_wed,
            @Query("end_time_wed[]") ArrayList<String> end_time_wed,
            @Query("start_time_thus[]") ArrayList<String> start_time_thus,
            @Query("end_time_thus[]") ArrayList<String> end_time_thus,
            @Query("start_time_fri[]") ArrayList<String> start_time_fri,
            @Query("end_time_fri[]") ArrayList<String> end_time_fri,
            @Query("start_time_sat[]") ArrayList<String> start_time_sat,
            @Query("end_time_sat[]") ArrayList<String> end_time_sat,
            @Query("start_time_sun[]") ArrayList<String> start_time_sun,
            @Query("end_time_sun[]") ArrayList<String> end_time_sun,
            @Query("start_time_all") String[] start_time_all_commertial,
            @Query("end_time_all") String[] end_time_all_comertial,
            @Query("add_day") String add_day,
            @Query("add_day_mon") String add_day_mon,
            @Query("add_day_tues") String add_day_tues,
            @Query("add_day_wed") String add_day_wed,
            @Query("add_day_thus") String add_day_thus,
            @Query("add_day_fri") String add_day_fri,
            @Query("add_day_sat") String add_day_sat,
            @Field("add_day_sun") String add_day_sun);

    @FormUrlEncoded
    @POST("auth/listing/commercial/{id}")
    Call<ResponseBody> add_commercial_list2(
            @Path("id") int id,
            @Query("address1_commercial") String address1,
            @Query("address2_commercial") String address2,
            @Query("landmark") String landmark,
            @Query("city") String city,
            @Query("state") String state,
            @Query("country") String country,
            @Query("lat") float lat,
            @Query("lng") float lng,
            @Query("pin") int pin,
            @Query("electricity_board") String board,
            @Query("rate_commercial") String rate,
            @Query("profit") String profit,
            @Query("selected") int selected,
            @Query("charger_type") String charger_type,
            @Query("connector_type_commercial") String connector,
            @Query("charger_brand_commercial") String brand,
            @Query("voltage") String voltage,
            @Query("amphere") String amphere_c,
            @Query("checked") int checked,
            @Query("model_commercial") String model,
            @Query("power_output") String power,
            @Query("custom_brand_commercial") ArrayList<String> custm_brand,
            @Query("custom_model_commercial") ArrayList<String> custum_model,
            @Query("socket_commercial") String socket,
            @Query("custom_connector_type_commercial") ArrayList<String> custom_connector_type,
            @Query("custom_voltage_commercial") ArrayList<String> custom_voltage,
            @Query("custom_power_output_commercial") ArrayList<String> custom_power_output,
            @Query("selected_days[]") ArrayList<String> selected_days,
            @Query("selected_day[]") ArrayList<String> selected_day,
            @Query("start_time[]") ArrayList<String> start_time,
            @Query("end_time[]") ArrayList<String> end_time,
            @Query("start_time_mon[]") ArrayList<String> start_time_mon,
            @Query("end_time_mon[]") ArrayList<String> end_time_mon,
            @Query("start_time_tues[]") ArrayList<String> start_time_tues,
            @Query("end_time_tues[]") ArrayList<String> end_time_tues,
            @Query("start_time_wed[]") ArrayList<String> start_time_wed,
            @Query("end_time_wed[]") ArrayList<String> end_time_wed,
            @Query("start_time_thus[]") ArrayList<String> start_time_thus,
            @Query("end_time_thus[]") ArrayList<String> end_time_thus,
            @Query("start_time_fri[]") ArrayList<String> start_time_fri,
            @Query("end_time_fri[]") ArrayList<String> end_time_fri,
            @Query("start_time_sat[]") ArrayList<String> start_time_sat,
            @Query("end_time_sat[]") ArrayList<String> end_time_sat,
            @Query("start_time_sun[]") ArrayList<String> start_time_sun,
            @Query("end_time_sun[]") ArrayList<String> end_time_sun,
            @Query("start_time_all") String[] start_time_all_commertial,
            @Query("end_time_all") String[] end_time_all_comertial,
            @Query("add_day") String add_day,
            @Query("add_day_mon") String add_day_mon,
            @Query("add_day_tues") String add_day_tues,
            @Query("add_day_wed") String add_day_wed,
            @Query("add_day_thus") String add_day_thus,
            @Query("add_day_fri") String add_day_fri,
            @Query("add_day_sat") String add_day_sat,
            @Field("add_day_sun") String add_day_sun);


    //  Driver flow
    @GET("car_make/list/")
    Call<List<Make>> createMake(@Query("type") String type);

    @GET("car_model/list")
    Call<List<Model>> getModelData(@Query("make") String make);

    @GET("battery_size/list")
    Call<battery_size> getBatterySize(@Query("make") String make, @Query("model") String model);


    //Residential flow

    @GET("charger_brand_residential/list")
    Call<List<Charegr_brand>> getBrand();

    @GET("charger_model_residential/list/")
    Call<List<charger_model>> getModel(@Query("charger_brand_residential") String brand);

    @GET("charger_connector_type_residential/list/")
    Call<List<Connector_type>> getConnector();

    @GET("charger_connector_type_residential/list/")
    Call<List<Connector_type>> getConnector(@Query("charger_model") String model,
                                            @Query("charger_brand") String brand,
                                            @Query("board") String electricityBoard);

    @GET("charger_electricity_board_residential/list/")
    Call<List<residential_board>> getResidentiallBoard(@Query("board") String board);

    @GET("charger_rate_structure_residential/list/")
    Call<List<residetial_rate>> getResidentialRate(@Query("board") String board);

    @GET("charger_power_output_residential/list/")
    Call<List<residential_power_output>> getPowerOutputRes(@Query("charger_brand") String charger_brand, @Query("charger_model") String charger_model);

    @GET("charger_power_output_residential/list/")
    Call<List<residential_power_output>> getPowerOutputRes(@Query("charger_brand") String charger_brand,
                                                           @Query("charger_model") String charger_model,
                                                           @Query("board") String board);

    @GET("charger_voltage_residential/list/")
    Call<List<residrntial_voltage>> getVoltageRes(@Query("charger_brand") String charger_brand, @Query("charger_model") String charger_model);

    @GET("charger_voltage_residential/list/")
    Call<List<residrntial_voltage>> getVoltageRes(@Query("charger_brand") String charger_brand,
                                                  @Query("charger_model") String charger_model,
                                                  @Query("board") String board);

    @GET("charger_amp_residential/list/")
    Call<List<residential_charger_amphere>> getChargerAmphereRes(@Query("charger_brand") String charger_brand, @Query("charger_model") String charger_model);

    @GET("charger_amp_residential/list/")
    Call<List<residential_charger_amphere>> getChargerAmphereRes(@Query("charger_brand") String charger_brand,
                                                                 @Query("charger_model") String charger_model,
                                                                 @Query("board") String board);


    @GET("socket_residential/list/")
    Call<List<Socket>> getSocket();

    @GET("socket_electricity_board_residential/list/")
    Call<List<residential_board_socket>> getElectricityBoardSocketRes(@Query("board") String board);

    @GET("socket_rate_structure_residential/list/")
    Call<List<residential_rate_socket>> getRateStructureSocketRes(@Query("board") String board);

    @GET("socket_voltage_residential/list/")
    Call<List<residential_voltage_socket>> getVoltageSocketRes(@Query("socket") String socket);

    @GET("socket_voltage_residential/list/")
    Call<List<residential_voltage_socket>> getVoltageSocketRes(@Query("socket") String socket, @Query("board") String electricityBoard);

    @GET("socket_amphere_residential/list/")
    Call<List<residential_socket_amphere>> getSocketAmphereRes(@Query("socket") String socket);

    @GET("socket_amphere_residential/list/")
    Call<List<residential_socket_amphere>> getSocketAmphereRes(@Query("socket") String socket, @Query("board") String electricityBoard);

    //    Commertial flow
    @GET("charger_brand_commercial/list/")
    Call<List<commertial_charger_type>> getChargerBrand();

    @GET("charger_model_commercial/list/")
    Call<List<commertial_charger_model>> getChargerModel(@Query("charger_brand_commercial") String brand);

    @GET("charger_connector_type_commercial/list/")
    Call<List<commertial_connector>> getConnectorType();

    @GET("charger_connector_type_commercial/list/")
    Call<List<commertial_connector>> getConnectorType(@Query("charger_model") String model, @Query("charger_brand") String brand,
                                                      @Query("board") String electricityBoard);

    @GET("charger_rate_structure_commercial/list/")
    Call<List<commertial_rate>> getRateStructure(@Query("board") String board);

    @GET("charger_electricity_board_commercial/list/")
    Call<List<commertial_board>> getElectricityBoardCommertial(@Query("board") String board);

    @GET("charger_power_output_commercial/list/")
    Call<List<commertial_power_output>> getPowerOutputCommertial(@Query("charger_model") String model, @Query("charger_brand") String brand);

    @GET("charger_power_output_commercial/list/")
    Call<List<commertial_power_output>> getPowerOutputCommertial(@Query("charger_model") String model,
                                                                 @Query("charger_brand") String brand,
                                                                 @Query("board") String electricityBoard);

    @GET("charger_voltage_commercial/list/")
    Call<List<commertial_voltage>> getVoltageComSocketCommertial(@Query("charger_model") String model, @Query("charger_brand") String brand);

    @GET("charger_voltage_commercial/list/")
    Call<List<commertial_voltage>> getVoltageComSocketCommertial(@Query("charger_model") String model, @Query("charger_brand") String brand,
                                                                 @Query("board") String electricityBoard);

    @GET("charger_amp_commercial/list/")
    Call<List<commertial_charger_amphere>> getChargerAmphereCom(@Query("charger_brand") String charger_brand, @Query("charger_model") String charger_model);

    @GET("charger_amp_commercial/list/")
    Call<List<commertial_charger_amphere>> getChargerAmphereCom(@Query("charger_brand") String charger_brand,
                                                                @Query("charger_model") String charger_model,
                                                                @Query("board") String electricityBoard);


    @GET("socket_commercial/list/")
    Call<List<Socket_commertial>> getSocketCommertial();

    @GET("socket_electricity_board_commercial/list/")
    Call<List<commertial_board_socket>> getElectricityBoardCommertialSocket(@Query("board") String board);

    @GET("socket_rate_structure_commercial/list/")
    Call<List<commertial_rate_socket>> getRateStructureCommertialSocket(@Query("board") String board);

    @GET("socket_voltage_commercial/list/")
    Call<List<commertial_voltage_socket>> getVoltageComSocket(@Query("socket") String socket);

    @GET("socket_voltage_commercial/list/")
    Call<List<commertial_voltage_socket>> getVoltageComSocket(@Query("socket") String socket, @Query("board") String board);

    @GET("socket_amphere_commercial/list/")
    Call<List<commertial_socket_amphere>> getSocketAmphereCom(@Query("socket") String socket);

    @GET("socket_amphere_commercial/list/")
    Call<List<commertial_socket_amphere>> getSocketAmphereCom(@Query("socket") String socket, @Query("board") String board);

//    Public

    @GET("charger_brand_public/list/")
    Call<List<public_charegr_brand>> getChargerBrandPublic();

    @GET("charger_model_public/list/")
    Call<List<public_charger_model>> getChargerModelPublic(@Query("charger_brand_public") String brand);

    @GET("charger_connector_type_public/list/")
    Call<List<public_connector>> getConnectorTypePublic();

    @GET("charger_connector_type_public/list/")
    Call<List<public_connector>> getConnectorTypePublic(@Query("charger_model") String model, @Query("charger_brand") String brand,
                                                        @Query("board") String electricityBoard);

    @GET("charger_rate_structure_public/list/")
    Call<List<public_rate>> getRateStructurePublic(@Query("board") String board);

    @GET("charger_electricity_board_public/list/")
    Call<List<public_board>> getElectricityBoardPublic(@Query("board") String board);

    @GET("charger_power_output_public/list/")
    Call<List<public_power_output>> getPowerOutputPublic(@Query("charger_model") String model, @Query("charger_brand") String brand);

    @GET("charger_power_output_public/list/")
    Call<List<public_power_output>> getPowerOutputPublic(@Query("charger_model") String model,
                                                         @Query("charger_brand") String brand,
                                                         @Query("board") String electricityBoard);

    @GET("charger_voltage_public/list/")
    Call<List<public_voltage>> getVoltagePublic(@Query("charger_model") String model, @Query("charger_brand") String brand);

    @GET("charger_voltage_public/list/")
    Call<List<public_voltage>> getVoltagePublic(@Query("charger_model") String model, @Query("charger_brand") String brand,
                                                @Query("board") String electricityBoard);


    @GET("charger_amp_public/list/")
    Call<List<public_charger_amphere>> getChargerAmpherePublic(@Query("charger_brand") String charger_brand, @Query("charger_model") String charger_model);

    @GET("charger_amp_public/list/")
    Call<List<public_charger_amphere>> getChargerAmpherePublic(@Query("charger_brand") String charger_brand,
                                                               @Query("charger_model") String charger_model,
                                                               @Query("board") String electricityBoard);

    @GET("socket_public/list/")
    Call<List<Socket_public>> getSocketPublic();

    @GET("socket_electricity_board_public/list/")
    Call<List<public_board_socket>> getElectricityBoardSocketPublic(@Query("board") String board);

    @GET("socket_rate_structure_public/list/")
    Call<List<public_rate_socket>> getRateStructureSocketPublic(@Query("board") String board);

    @GET("socket_voltage_public/list/")
    Call<List<public_voltage_socket>> getVoltageSocketPublic(@Query("socket") String socket);

    @GET("socket_voltage_public/list/")
    Call<List<public_voltage_socket>> getVoltageSocketPublic(@Query("socket") String socket, @Query("board") String electricityBoard);

    @GET("socket_amphere_public/list/")
    Call<List<public_socket_amphere>> getSocketAmpherePublic(@Query("socket") String socket);

    @GET("socket_amphere_public/list/")
    Call<List<public_socket_amphere>> getSocketAmpherePublic(@Query("socket") String socket, @Query("board") String electricityBoard);


//    Show_listing

    @GET("auth/residential_listing/{id}")
    Call<List<ResidentialListing>> getResidentialListing(@Path("id") int id);

    @GET("auth/public_listing/{id}")
    Call<List<PublicListing>> getPublicListing(@Path("id") int id);

    @GET("auth/commercial_listing/{id}")
    Call<List<CommertialListing>> getCommercialListing(@Path("id") int id);

    //  Delete Listing
    @DELETE("auth/residential_listing_delete{id}")
    Call<List<ResidentialListing>> deleteResidentialListing(@Path("id") int id);

    @DELETE("auth/commercial_listing_delete{id}")
    Call<List<CommertialListing>> deleteCommertialListing(@Path("id") int id);

    @DELETE("auth/public_listing_delete{id}")
    Call<List<PublicListing>> deletePublicListing(@Path("id") int id);

    //    Show added list with listing id---view as public
    @GET("auth/show_listing_residential/{id}")
    Call<show_residential> getListResidential(@Path("id") int id);

    @GET("auth/show_listing/{id}")
    Call<show_commertial> getListCommertial(@Path("id") int id);

    @GET("auth/show_listing_public/{id}")
    Call<show_public> getListPublic(@Path("id") int id);

    @GET("json?")
    Call<JsonObject> getWholeAddress(@Query("key") String api_key, @Query("placeid") String place_id);

    @GET("json?")
    Call<JsonObject> getPlaceId(@Query("key") String api_key, @Query("input") String searchText);

    //     Edit Listing
    @FormUrlEncoded
    @POST("auth/edit_list_residential/{id}/{userid}")
    Call<ResponseBody> edit_residential_listing_method(
            @Path("id") Long id,
            @Path("userid") int userid,
            @Query("address1_residential") String address1,
            @Query("address2_residential") String address2,
            @Query("landmark") String landmark,
            @Query("city") String city,
            @Query("state") String state,
            @Query("country") String country,
            @Query("lat") float lat,
            @Query("lng") float lng,
            @Query("pin") Long pin,
            @Query("profit") String profit,
            @Query("selected") int selected,
            @Query("checked") int checked,
            @Query("charger_type") String charger_type,
            @Query("connector_type_residential") String connector,
            @Query("charger_brand_residential") String brand,
            @Query("model_residential") String model,
            @Query("voltage") String voltage,
            @Query("amphere") String amphere,
            @Query("power_output") String power,
            @Query("electricity_board") String board,
            @Query("rate_residential") String rate,
            @Query("socket_residential") String socket,
            @Query("selected_days[]") ArrayList<String> selected_days,
            @Query("start_time[]") ArrayList<String> start_time,
            @Query("end_time[]") ArrayList<String> end_time,
            @Query("selected_day[]") ArrayList<String> selected_day,
            @Query("start_time_mon[]") ArrayList<String> start_time_mon,
            @Query("end_time_mon[]") ArrayList<String> end_time_mon,
            @Query("start_time_tues[]") ArrayList<String> start_time_tues,
            @Query("end_time_tues[]") ArrayList<String> end_time_tues,
            @Query("start_time_wed[]") ArrayList<String> start_time_wed,
            @Query("end_time_wed[]") ArrayList<String> end_time_wed,
            @Query("start_time_thus[]") ArrayList<String> start_time_thus,
            @Query("end_time_thus[]") ArrayList<String> end_time_thus,
            @Query("start_time_fri[]") ArrayList<String> start_time_fri,
            @Query("end_time_fri[]") ArrayList<String> end_time_fri,
            @Query("start_time_sat[]") ArrayList<String> start_time_sat,
            @Query("end_time_sat[]") ArrayList<String> end_time_sat,
            @Query("start_time_sun[]") ArrayList<String> start_time_sun,
            @Query("end_time_sun[]") ArrayList<String> end_time_sun,
            @Query("add_day") String add_day,
            @Query("add_day_mon") String add_day_mon,
            @Query("add_day_tues") String add_day_tues,
            @Query("add_day_wed") String add_day_wed,
            @Query("add_day_thus") String add_day_thus,
            @Query("add_day_fri") String add_day_fri,
            @Query("add_day_sat") String add_day_sat,
            @Field("add_day_sun") String add_day_sun);

///Edit commertial listing

    @FormUrlEncoded
    @POST("auth/edit_list/{id}/{userId}")
    Call<ResponseBody> editCommercialListing(
            @Path("id") Long id,
            @Path("userId") int userId,
            @Query("address1_commercial") String address1,
            @Query("address2_commercial") String address2,
            @Query("landmark") String landmark,
            @Query("city") String city,
            @Query("state") String state,
            @Query("country") String country,
            @Query("lat") float lat,
            @Query("lng") float lng,
            @Query("pin") Long pin,
            @Query("profit") String profit,
            @Query("selected") int selected,
            @Query("checked") int checked,
            @Query("charger_type") String charger_type,
            @Query("connector_type_commercial") String connector,
            @Query("charger_brand_commercial") String brand,
            @Query("model_commercial") String model,
            @Query("voltage") String voltage,
            @Query("amphere") String amphere,
            @Query("power_output") String power,
            @Query("electricity_board") String board,
            @Query("rate_commercial") String rate,
            @Query("socket_commercial") String socket,
            @Query("selected_days[]") ArrayList<String> selected_days,
            @Query("start_time[]") ArrayList<String> start_time,
            @Query("end_time[]") ArrayList<String> end_time,
            @Query("selected_day[]") ArrayList<String> selected_day,
            @Query("start_time_mon[]") ArrayList<String> start_time_mon,
            @Query("end_time_mon[]") ArrayList<String> end_time_mon,
            @Query("start_time_tues[]") ArrayList<String> start_time_tues,
            @Query("end_time_tues[]") ArrayList<String> end_time_tues,
            @Query("start_time_wed[]") ArrayList<String> start_time_wed,
            @Query("end_time_wed[]") ArrayList<String> end_time_wed,
            @Query("start_time_thus[]") ArrayList<String> start_time_thus,
            @Query("end_time_thus[]") ArrayList<String> end_time_thus,
            @Query("start_time_fri[]") ArrayList<String> start_time_fri,
            @Query("end_time_fri[]") ArrayList<String> end_time_fri,
            @Query("start_time_sat[]") ArrayList<String> start_time_sat,
            @Query("end_time_sat[]") ArrayList<String> end_time_sat,
            @Query("start_time_sun[]") ArrayList<String> start_time_sun,
            @Query("end_time_sun[]") ArrayList<String> end_time_sun,
            @Query("add_day") String add_day,
            @Query("add_day_mon") String add_day_mon,
            @Query("add_day_tues") String add_day_tues,
            @Query("add_day_wed") String add_day_wed,
            @Query("add_day_thus") String add_day_thus,
            @Query("add_day_fri") String add_day_fri,
            @Query("add_day_sat") String add_day_sat,
            @Field("add_day_sun") String add_day_sun);

    @FormUrlEncoded
    @POST("auth/edit_list_public/{id}/{userId}")
    Call<ResponseBody> edit_public_list(
            @Path("id") Long id,
            @Path("userId") int userId,
            @Query("address1_public") String address1,
            @Query("address2_public") String address2,
            @Query("landmark") String landmark,
            @Query("city") String city,
            @Query("state") String state,
            @Query("country") String country,
            @Query("lat") float lat,
            @Query("lng") float lng,
            @Query("pin") Long pin,
            @Query("profit") String profit,
            @Query("selected") int selected,
            @Query("checked") int checked,
            @Query("charger_type") String charger_type,
            @Query("connector_type_public") String connector,
            @Query("charger_brand_public") String brand,
            @Query("model_public") String model,
            @Query("voltage") String voltage,
            @Query("amphere") String amphere,
            @Query("power_output") String power,
            @Query("electricity_board") String board,
            @Query("rate_public") String rate,
            @Query("socket_public") String socket,
            @Query("selected_days[]") ArrayList<String> selected_days,
            @Query("start_time[]") ArrayList<String> start_time,
            @Query("end_time[]") ArrayList<String> end_time,
            @Query("selected_day[]") ArrayList<String> selected_day,
            @Query("start_time_mon[]") ArrayList<String> start_time_mon,
            @Query("end_time_mon[]") ArrayList<String> end_time_mon,
            @Query("start_time_tues[]") ArrayList<String> start_time_tues,
            @Query("end_time_tues[]") ArrayList<String> end_time_tues,
            @Query("start_time_wed[]") ArrayList<String> start_time_wed,
            @Query("end_time_wed[]") ArrayList<String> end_time_wed,
            @Query("start_time_thus[]") ArrayList<String> start_time_thus,
            @Query("end_time_thus[]") ArrayList<String> end_time_thus,
            @Query("start_time_fri[]") ArrayList<String> start_time_fri,
            @Query("end_time_fri[]") ArrayList<String> end_time_fri,
            @Query("start_time_sat[]") ArrayList<String> start_time_sat,
            @Query("end_time_sat[]") ArrayList<String> end_time_sat,
            @Query("start_time_sun[]") ArrayList<String> start_time_sun,
            @Query("end_time_sun[]") ArrayList<String> end_time_sun,
            @Query("add_day") String add_day,
            @Query("add_day_mon") String add_day_mon,
            @Query("add_day_tues") String add_day_tues,
            @Query("add_day_wed") String add_day_wed,
            @Query("add_day_thus") String add_day_thus,
            @Query("add_day_fri") String add_day_fri,
            @Query("add_day_sat") String add_day_sat,
            @Field("add_day_sun") String add_day_sun);

    @GET("driver/account/{id}")
    Call<List<AllVehicle>> getAllVehicleOfParticularDriver(@Path("id") int id);

    @GET("init_vehicles/{id}")
    Call<List<AllVehicle>> initAllVehicleOfParticularDriver(@Path("id") int id);

    // https://elshare.in/api/calculate_time?start_day=2020-12-16&start_time=10:00&end_time=11:00&end_day=2020-12-16
    @GET("calculate_time")
    Call<Integer> getCalculateTime(@Query("start_day") String start_day, @Query("start_time") String start_time,
                                            @Query("end_day") String end_day, @Query("end_time") String end_time);

    @GET("auth/init/driver/{id}")
    Call<DriverDetail> getDriverDetail(@Path("id") int id);

    @DELETE("delete_vehicle/{id}")
    Call<ResponseBody> deleteVehicle(@Path("id") int id);

    @GET("show_details/{id}")
    Call<ShowVehicleDetail> getVehicleDetail(@Path("id") int id);

//    My Profile

    @Multipart
    @POST("update_profile/{id}")
    Call<MyProfilePojo> uploadMyProfile(
            @Path("id") String userId,
            @Part("name") RequestBody user_name,
            @Part("mobile") RequestBody mobile,
            @Part("picture\"; filename=\"myProfile.jpg\" ") RequestBody image_file,
            @Part("id_picture\"; filename=\"myId.jpg\" ") RequestBody id_file);

    @Multipart
    @POST("update_profile/{id}")
    Call<MyProfilePojo> uploadMyProfileWithoutImages(
            @Path("id") String userId,
            @Part("name") RequestBody user_name,
            @Part("mobile") RequestBody mobile
    );
    @Multipart
    @POST("update_profile/{id}")
    Call<MyProfilePojo> uploadMyProfileWithoutProfile(
            @Path("id") String userId,
            @Part("name") RequestBody user_name,
            @Part("mobile") RequestBody mobile,
            @Part("id_picture\"; filename=\"myId.jpg\" ") RequestBody id_file
    );
    @Multipart
    @POST("update_profile/{id}")
    Call<MyProfilePojo> uploadMyProfileWithoutIdProof(
            @Path("id") String userId,
            @Part("name") RequestBody user_name,
            @Part("mobile") RequestBody mobile,
            @Part("picture\"; filename=\"myProfile.jpg\" ") RequestBody image_file
    );

    @GET("auth/init/driver/{id}")
    Call<MyProfileUser> fetchMyProfile(
            @Path("id") String userId);

    @GET("assets/img/uploads/picture/{name}")
    Call<ResponseBody> fetchProfilePicture(
            @Path("name") String userProfilePictureName);

    @GET("assets/img/uploads/id_picture/{name}")
    Call<ResponseBody> fetchProfileId(
            @Path("name") String userIdName);

    @Multipart
    @POST("auth/charger/data")
    Call<List<MarkerData>> getMarkerData(
            @Part("price_range_data[]") RequestBody price_range_start,
            @Part("price_range_data[]") RequestBody price_range_last,
            @Part("placeName") RequestBody placeName,
            @Part("selected_socket_data[]") RequestBody selected_socket_data,
            @Part("selected_timeslots[]") RequestBody selected_timeslot,
            @Part("selected_avai_day[]") RequestBody selected_available_day,
            @Part("selected_charger_data[]") RequestBody selected_charger_data
    );

    @GET("user/{id}")
    Call<UserId> getUserData(
            @Path("id") String user_id
    );

    @GET("connecter_type")
    Call<List<Connector_type>> getConnectorMapFilter();

    @GET("socket_type")
    Call<List<Socket>> getSocketMapFilter();


    @GET("show_availability/{id}/{host}")
    Call<ShowAvailabilityPojo> getAvailability(
            @Path("id") String id,
            @Path("host") String host_table);


    //Deactivation of account

    @DELETE("delete_driver/account/{id}")
    Call<ResponseBody> deleteDriver(@Path("id") String user_id);
    @DELETE("delete_host/account/{id}")
    Call<ResponseBody> deleteHost(@Path("id") String user_id);
    @DELETE("delete_account/{id}")
    Call<ResponseBody> deleteBoth(@Path("id") String user_id);

//    Booking History Host

    @GET("show_all_bookings_host/{user_id}")
    Call<HostBookingHistoryPojo> getBookingHistoryHostNext(@Path("user_id") String user_id,@Query("page") Integer page_int);

    @GET("show_booking/{booking_id}")
    Call<hostParticularBookingDetail> getBookingHistoryHostParticularBooking(@Path("booking_id") String booking_id);

    @GET("approve_booking/{booking_id}")
    Call<ResponseBody> hostBookingApprove(@Path("booking_id") String booking_id);

    @GET("decline_booking/{booking_id}")
    Call<ResponseBody> hostBookingDecline(@Path("booking_id") String booking_id);

    @DELETE("delete_booking/{booking_id}")
    Call<ResponseBody> deleteBookingHost(@Path("booking_id") String booking_id);

//    Notification Host

    @GET("get_notifications_host_all/{user_id}")
    Call<NotificationHostPOJO> getHostNotification(@Path("user_id") String user_id);

    @GET("get_unread_notifications_host/{user_id}")
    Call<ResponseBody> getHostNotificationCountHost(@Path("user_id") String user_id);

    @GET("mark_read_host/{notification_id}")
    Call<ResponseBody> markHostNotificationRead(@Path("notification_id") String notification_id);
    @GET("get_notifications_host/{user_id}")
    Call<NotificationHostPOJO> getHostNotificationCount(@Path("user_id") String user_id);

    @GET("get_payments/{user_id}")
    Call<List<DriverPaymentHistoryPojo>> paymentHistoryDriver(@Path("user_id") String user_id);


//Driver Notification
    @GET("get_notifications/{user_id}")
    Call<NotificationHostPOJO> getDriverNotificationCount(@Path("user_id") String user_id);

    @GET("get_notifications_all/{user_id}")
    Call<NotificationHostPOJO> getDriverNotification(@Path("user_id") String user_id);

    @GET("mark_read/{notification_id}")
    Call<ResponseBody> markDriverNotificationRead(@Path("notification_id") String notification_id);

    @GET("show_all_bookings/{user_id}")
    Call<HostBookingHistoryPojo> getBookingHistoryDriverNext(@Path("user_id") String user_id,@Query("page") Integer page_int);

    @GET("show_pending_bookings/{user_id}")
    Call<HostBookingHistoryPojo> getBookingHistoryDriverPendingNext(@Path("user_id") String user_id,@Query("page") Integer page_int);

    @GET("cancel_booking_host/{booking_id}")
    Call<ResponseBody> bookingCancle(@Path("booking_id") String booking_id);

    @POST("auth/reset_password")
    Call<ResponseBody> forgotPassword(@Query("email") String email);

    @Multipart
    @POST("add_booking")
    Call<AddBooking> addBooking(
            @Part("amount") RequestBody amount,
            @Part("start_day") RequestBody start_day,
            @Part("end_day") RequestBody end_day,
            @Part("start_time") RequestBody start_time,
            @Part("end_time") RequestBody end_time,
            @Part("userId") RequestBody userId,
            @Part("host_id") RequestBody host_id,
            @Part("tablename") RequestBody tablename,
            @Part("minute") RequestBody minute,
            @Part("unit") RequestBody unit);

    // https://elshare.in/api/check_booked_slots?start_day=2020-12-29&end_day=2020-12-29&start_time=15:09&end_time=16:08&host_id=7&tablename=residentials
    @POST("check_booked_slots")
    Call<Integer> checkHostAvailability(@Query("start_day") String start_day, @Query("start_time") String start_time,
                                   @Query("end_day") String end_day, @Query("end_time") String end_time,
                                        @Query("host_id") String host_id, @Query("tablename") String tablename);

    @Multipart
    @POST("payment/{user_id}")
    Call<BookingConfirmation> bookingCheckout(
            @Path("user_id") String user_id,
            @Part("booking_id") RequestBody booking_id,
            @Part("razorpay_signature") RequestBody razorpay_signature,
            @Part("razorpay_payment_id") RequestBody razorpay_payment_id,
            @Part("razorpay_order_id")  RequestBody razorpay_order_id
    );
}


