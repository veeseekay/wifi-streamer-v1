package com.guidestone.wifi.streamer.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "c"
})
public class Row {

    private static final Logger LOG = LoggerFactory.getLogger(Row.class);

    @JsonProperty("c")
    private List<C> c = new ArrayList<C>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The c
     */
    @JsonProperty("c")
    public List<C> getC() {
        return c;
    }

    /**
     *
     * @param c
     * The c
     */
    @JsonProperty("c")
    public void setC(List<C> c) {
        this.c = c;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder row = new StringBuilder("{\"c\":[");
        for (C cObject : c) {
            row.append(cObject.toString()).append(",");
        }
        if (row.length() > 0 && row.charAt(row.length()-1)==',') {
            row = new StringBuilder(row.substring(0, row.length()-1));
        }
        row.append("]}");
        //LOG.info("Row is {}", row.toString());
        return row.toString();
    }
}