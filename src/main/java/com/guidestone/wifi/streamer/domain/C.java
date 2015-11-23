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
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "v",
        "f"
})
public class C {

    private static final Logger LOG = LoggerFactory.getLogger(C.class);

    @JsonProperty("v")
    private String v;
    @JsonProperty("f")
    private Object f;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The v
     */
    @JsonProperty("v")
    public String getV() {
        return v;
    }

    /**
     *
     * @param v
     * The v
     */
    @JsonProperty("v")
    public void setV(String v) {
        this.v = v;
    }

    /**
     *
     * @return
     * The f
     */
    @JsonProperty("f")
    public Object getF() {
        return f;
    }

    /**
     *
     * @param f
     * The f
     */
    @JsonProperty("f")
    public void setF(Object f) {
        this.f = f;
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
        String c = null;
        try {
            int value = Integer.parseInt(v);
            c = "{\"v\":" + v + ",\"f\":null}";
        } catch (NumberFormatException e) {
            c = "{\"v\":\"" + v + "\",\"f\":null}";
        }
        LOG.info("C is {}", c);
        return c;
    }
}