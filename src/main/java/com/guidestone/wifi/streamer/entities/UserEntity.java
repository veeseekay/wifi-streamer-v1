package com.guidestone.wifi.streamer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "user", schema = "", catalog = "wifistreamer")
public class UserEntity {
    private double id;
    private String userName;
    private String passwd;
    private String type;
    private String age;
    private Long appUsageDuration;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Timestamp created;


    @Id
    @Column(name = "id")
    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "passwd")
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Basic
    @Column(name = "age")
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "usage_duration")
    public Long getAppUsageDuration() {
        return appUsageDuration;
    }

    public void setAppUsageDuration(Long appUsageDuration) {
        this.appUsageDuration = appUsageDuration;
    }

    @Basic
    @Column(name = "created_on")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (passwd != null ? !passwd.equals(that.passwd) : that.passwd != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(id);
        result = (int) (temp ^ (temp >>> 32));

        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", type='" + type + '\'' +
                ", age='" + age + '\'' +
                ", appUsageDuration=" + appUsageDuration +
                ", created=" + created +
                '}';
    }
}
