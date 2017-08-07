package com.deloitte.projects.ico.parser.icotracker;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ICOTRACKER")
public class IcoTrackerIco implements Serializable{
    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    private String project;
    private String description;
    private String icoTrackerLink;
    private String launch;
    private String base;
    private String whitepaper;
    private String escrow;
    private String website;
    private String cpIco;
    private String date;
    private String details;

    private String ico_user;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcoTrackerLink() {
        return icoTrackerLink;
    }

    public void setIcoTrackerLink(String icoTrackerLink) {
        this.icoTrackerLink = icoTrackerLink;
    }

    public String getLaunch() {
        return launch;
    }

    public void setLaunch(String launch) {
        this.launch = launch;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getWhitepaper() {
        return whitepaper;
    }

    public void setWhitepaper(String whitepaper) {
        this.whitepaper = whitepaper;
    }

    public String getEscrow() {
        return escrow;
    }

    public void setEscrow(String escrow) {
        this.escrow = escrow;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIco_user() {
        return ico_user;
    }

    public void setIco_user(String ico_user) {
        this.ico_user = ico_user;
    }

    public String getCpIco() {
        return cpIco;
    }

    public void setCpIco(String cpIco) {
        this.cpIco = cpIco;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IcoTrackerIco{" +
                "project='" + project + '\'' +
                ", description='" + description + '\'' +
                ", icoTrackerLink='" + icoTrackerLink + '\'' +
                ", launch='" + launch + '\'' +
                ", base='" + base + '\'' +
                ", whitepaper='" + whitepaper + '\'' +
                ", escrow='" + escrow + '\'' +
                ", website='" + website + '\'' +
                ", ico_user='" + ico_user + '\'' +
                ", cpIco='" + cpIco + '\'' +
                ", date='" + date + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
