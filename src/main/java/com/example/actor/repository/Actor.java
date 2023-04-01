package com.example.actor.repository;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "actor")
public class Actor {

  @Id
  @Column(name="id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  @Column(name="name", nullable=false)
  private String name;
  @Column(name="height")
  private Integer height;
  @Column(name="blood")
  private String blood;
  @Temporal(TemporalType.DATE)
  @Column(name="birthday")
  private Date birthday;
  @Column(name="birthplace_id")
  private Integer birthplaceId;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="update_at")
  private Date updateAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="birthplace_id", insertable = false, updatable = false )
  private Prefecture pref;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Integer getHeight() {
    return height;
  }
  public void setHeight(Integer height) {
    this.height = height;
  }
  public String getBlood() {
    return blood;
  }
  public void setBlood(String blood) {
    this.blood = blood;
  }
  public Date getBirthday() {
    return birthday;
  }
  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
  public Integer getBirthplaceId() {
    return birthplaceId;
  }
  public void setBirthplaceId(Integer birthplaceId) {
    this.birthplaceId = birthplaceId;
  }
  public Date getUpdateAt() {
    return updateAt;
  }
  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }

  public Prefecture getPref() {
    return pref;
  }
  public void setPref(Prefecture pref) {
    this.pref = pref;
  }

  @Override
  public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
  }

}
