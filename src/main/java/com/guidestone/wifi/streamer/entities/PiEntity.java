package com.guidestone.wifi.streamer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "pi", schema = "", catalog = "wifistreamer")
public class PiEntity {
    private String macId;
    private Integer active;
    private Long downloads;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Timestamp lastChecked;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Timestamp addedOn;

    @Id
    @Column(name = "mac_id")
    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    @Basic
    @Column(name = "active")
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Basic
    @Column(name = "downloads")
    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    @Basic
    @Column(name = "last_checked")

    public Timestamp getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(Timestamp lastChecked) {
        this.lastChecked = lastChecked;
    }

    @Basic
    @Column(name = "added_on")
    public Timestamp getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Timestamp addedOn) {
        this.addedOn = addedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PiEntity piEntity = (PiEntity) o;

        if (macId != null ? !macId.equals(piEntity.macId) : piEntity.macId != null) return false;
        if (active != null ? !active.equals(piEntity.active) : piEntity.active != null) return false;
        if (downloads != null ? !downloads.equals(piEntity.downloads) : piEntity.downloads != null) return false;
        if (lastChecked != null ? !lastChecked.equals(piEntity.lastChecked) : piEntity.lastChecked != null)
            return false;
        if (addedOn != null ? !addedOn.equals(piEntity.addedOn) : piEntity.addedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = macId != null ? macId.hashCode() : 0;
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (downloads != null ? downloads.hashCode() : 0);
        result = 31 * result + (lastChecked != null ? lastChecked.hashCode() : 0);
        result = 31 * result + (addedOn != null ? addedOn.hashCode() : 0);
        return result;
    }
}
