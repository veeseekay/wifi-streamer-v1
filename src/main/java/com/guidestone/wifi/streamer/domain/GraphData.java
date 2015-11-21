package com.guidestone.wifi.streamer.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "cols",
        "rows"
})
public class GraphData {

    @JsonProperty("cols")
    private List<Col> cols = new ArrayList<Col>();
    @JsonProperty("rows")
    private List<Row> rows = new ArrayList<Row>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The cols
     */
    @JsonProperty("cols")
    public List<Col> getCols() {
        return cols;
    }

    /**
     *
     * @param cols
     * The cols
     */
    @JsonProperty("cols")
    public void setCols(List<Col> cols) {
        this.cols = cols;
    }

    /**
     *
     * @return
     * The rows
     */
    @JsonProperty("rows")
    public List<Row> getRows() {
        return rows;
    }

    /**
     *
     * @param rows
     * The rows
     */
    @JsonProperty("rows")
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}