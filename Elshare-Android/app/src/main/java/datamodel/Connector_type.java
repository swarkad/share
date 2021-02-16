package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Connector_type
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

    public Connector_type withConnectorType(String connectorType) {
        this.connectorType = connectorType;
        return this;
    }

}
