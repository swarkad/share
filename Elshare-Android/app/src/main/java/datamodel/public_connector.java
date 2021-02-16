package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_connector
{
    @SerializedName("connector_type")
    @Expose
    private String connectorType;

    public String getConnectorTypePublic() {
        return connectorType;
    }

    public void setConnectorTypePublic(String connectorType) {
        this.connectorType = connectorType;
    }

    public public_connector withConnectorTypePublic(String connectorType) {
        this.connectorType = connectorType;
        return this;
    }
}
