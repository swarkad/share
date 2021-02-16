package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_socket_object
{
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("host")
        @Expose
        private String host;
        @SerializedName("socket")
        @Expose
        private String socket;
        @SerializedName("voltage")
        @Expose
        private String voltage;
        @SerializedName("electricity_board")
        @Expose
        private String electricityBoard;
        @SerializedName("rate_structure")
        @Expose
        private Double rateStructure;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getSocket() {
            return socket;
        }

        public void setSocket(String socket) {
            this.socket = socket;
        }

        public String getVoltage() {
            return voltage;
        }

        public void setVoltage(String voltage) {
            this.voltage = voltage;
        }

        public String getElectricityBoard() {
            return electricityBoard;
        }

        public void setElectricityBoard(String electricityBoard) {
            this.electricityBoard = electricityBoard;
        }

        public Double getRateStructure() {
            return rateStructure;
        }

        public void setRateStructure(Double rateStructure) {
            this.rateStructure = rateStructure;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
}
