package com.deloitte.projects.ico.parser.icorating;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ICORATING")
public class IcoRatingIco implements Serializable{
    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    private String hypeScore = "";
    private String riskScore = "";
    private String investScore = "";
    private String ico_category = "";
    @Column(length = 800)
    private String description = "";
    private String founded = "";
    private String website = "";
    @Column(length = 1500)
    private String features = "";
    private String similarProjects = "";
    private String icoDate = "";
    @Column(length = 2500)
    private String tokensDistribution = "";
    @Column(length = 2000)
    private String tokenSales = "";
    @Column(length = 3500)
    private String bountyCamping = "";
    @Column(length = 1200)
    private String escrow = "";
    private String accepts = "";
    @Column(length = 5000)
    private String technicalDetails = "";
    private String proofOfDeveloper = "";
    @Column(length = 800)
    private String dividends = "";
    @Column(length = 600)
    private String theSourceCode = "";
    private String rating = "";
    private String reportLink = "";
    private long startDate;
    private long endDate;

    public String getHypeScore() {
        return hypeScore;
    }

    public IcoRatingIco setHypeScore(String hypeScore) {
        this.hypeScore = hypeScore;
        return this;
    }

    public String getRiskScore() {
        return riskScore;
    }

    public IcoRatingIco setRiskScore(String riskScore) {
        this.riskScore = riskScore;
        return this;
    }

    public String getInvestScore() {
        return investScore;
    }

    public IcoRatingIco setInvestScore(String investScore) {
        this.investScore = investScore;
        return this;
    }

    public String getIco_category() {
        return ico_category;
    }

    public IcoRatingIco setIco_category(String ico_category) {
        this.ico_category = ico_category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IcoRatingIco setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getFounded() {
        return founded;
    }

    public IcoRatingIco setFounded(String founded) {
        this.founded = founded;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public IcoRatingIco setWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getFeatures() {
        return features;
    }

    public IcoRatingIco setFeatures(String features) {
        this.features = features;
        return this;
    }

    public String getSimilarProjects() {
        return similarProjects;
    }

    public IcoRatingIco setSimilarProjects(String similarProjects) {
        this.similarProjects = similarProjects;
        return this;
    }

    public String getIcoDate() {
        return icoDate;
    }

    public IcoRatingIco setIcoDate(String icoDate) {
        this.icoDate = icoDate;
        return this;
    }

    public String getTokensDistribution() {
        return tokensDistribution;
    }

    public IcoRatingIco setTokensDistribution(String tokensDistribution) {
        this.tokensDistribution = tokensDistribution;
        return this;
    }

    public String getTokenSales() {
        return tokenSales;
    }

    public IcoRatingIco setTokenSales(String tokenSales) {
        this.tokenSales = tokenSales;
        return this;
    }

    public String getBountyCamping() {
        return bountyCamping;
    }

    public IcoRatingIco setBountyCamping(String bountyCamping) {
        this.bountyCamping = bountyCamping;
        return this;
    }

    public String getEscrow() {
        return escrow;
    }

    public IcoRatingIco setEscrow(String escrow) {
        this.escrow = escrow;
        return this;
    }

    public String getAccepts() {
        return accepts;
    }

    public IcoRatingIco setAccepts(String accepts) {
        this.accepts = accepts;
        return this;
    }

    public String getProofOfDeveloper() {
        return proofOfDeveloper;
    }

    public IcoRatingIco setProofOfDeveloper(String proofOfDeveloper) {
        this.proofOfDeveloper = proofOfDeveloper;
        return this;
    }

    public String getTechnicalDetails() {
        return technicalDetails;
    }

    public IcoRatingIco setTechnicalDetails(String technicalDetails) {
        this.technicalDetails = technicalDetails;
        return this;
    }

    public String getDividends() {
        return dividends;
    }

    public IcoRatingIco setDividends(String dividends) {
        this.dividends = dividends;
        return this;
    }

    public String getTheSourceCode() {
        return theSourceCode;
    }

    public IcoRatingIco setTheSourceCode(String theSourceCode) {
        this.theSourceCode = theSourceCode;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public IcoRatingIco setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getReportLink() {
        return reportLink;
    }

    public IcoRatingIco setReportLink(String reportLink) {
        this.reportLink = reportLink;
        return this;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IcoRatingIco{" +
                "hypeScore='" + hypeScore.length() + '\'' +
                ", riskScore='" + riskScore.length() + '\'' +
                ", investScore='" + investScore.length() + '\'' +
                ", ico_category='" + ico_category.length() + '\'' +
                ", description='" + description.length() + '\'' +
                ", founded='" + founded.length() + '\'' +
                ", website='" + website.length() + '\'' +
                ", features='" + features.length() + '\'' +
                ", similarProjects='" + similarProjects.length() + '\'' +
                ", icoDate='" + icoDate.length() + '\'' +
                ", tokensDistribution='" + tokensDistribution.length() + '\'' +
                ", tokenSales='" + tokenSales.length() + '\'' +
                ", bountyCamping='" + bountyCamping.length() + '\'' +
                ", escrow='" + escrow.length() + '\'' +
                ", accepts='" + accepts.length() + '\'' +
                ", technicalDetails='" + technicalDetails.length() + '\'' +
                ", proofOfDeveloper='" + proofOfDeveloper.length() + '\'' +
                ", dividends='" + dividends.length() + '\'' +
                ", theSourceCode='" + theSourceCode.length() + '\'' +
                ", rating='" + rating.length() + '\'' +
                ", reportLink='" + reportLink.length() + '\'' +
                '}';
    }
}
