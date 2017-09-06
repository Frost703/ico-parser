package com.deloitte.projects.ico.parser.tokenmarket;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

@Entity
@Table(name="TOKENMARKET")
public class TokenMarketIco {
    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    private String name;
    private String lead;
    private String symbol;
    @Column(length = 5000)
    private String concept;
    private String technology;
    @Column(length = 2000)
    private String team;
    @Column(length = 1000)
    private String links;
    private String domainScore;
    private String backlinksScore;
    private String commits;

    private Date openingDate;
    private Date closingDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getDomainScore() {
        return domainScore;
    }

    public void setDomainScore(String domainScore) {
        this.domainScore = domainScore;
    }

    public String getBacklinksScore() {
        return backlinksScore;
    }

    public void setBacklinksScore(String backlinksScore) {
        this.backlinksScore = backlinksScore;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = convertStringToDate(openingDate);
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = convertStringToDate(closingDate);
    }

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }

    private Date convertStringToDate(String string){
        int dotIndex = string.indexOf(".");
        int day = Integer.parseInt(string.substring(0, dotIndex));

        int lastSpace = string.lastIndexOf(" ");
        String month = string.substring(dotIndex + 2, lastSpace);
        Month jdbcMonth = Month.JANUARY;

        Month[] months = Month.values();
        for(Month m : months){
            if(m.toString().startsWith(month.trim().toUpperCase())) jdbcMonth = m;
        }

        int year = Integer.parseInt(string.substring(lastSpace + 1));
        return Date.valueOf(LocalDate.of(year, jdbcMonth, day));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TokenMarketIco{" +
                "name='" + name + '\'' +
                ", lead='" + lead + '\'' +
                ", symbol='" + symbol + '\'' +
                ", concept='" + concept + '\'' +
                ", technology='" + technology + '\'' +
                ", team='" + team + '\'' +
                ", links='" + links + '\'' +
                ", domainScore='" + domainScore + '\'' +
                ", backlinksScore='" + backlinksScore + '\'' +
                ", commits='" + commits + '\'' +
                ", openingDate=" + openingDate +
                ", closingDate=" + closingDate +
                '}';
    }
}
