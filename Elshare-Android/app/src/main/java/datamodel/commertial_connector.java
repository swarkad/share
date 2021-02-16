package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_connector
{
    @SerializedName("connector_type")
    @Expose
    private String connectorType;

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public commertial_connector withConnectorType(String connectorType) {
        this.connectorType = connectorType;
        return this;
    }
}
