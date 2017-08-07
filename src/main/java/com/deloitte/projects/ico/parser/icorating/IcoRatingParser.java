package com.deloitte.projects.ico.parser.icorating;


import com.deloitte.projects.ico.parser.IcoParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//parser for http://icorating.com/
public class IcoRatingParser implements IcoParser {
    private static final String link = "http://icorating.com";

    public List<IcoRatingIco> parse(){
        List<String> links = parseListOfIcoLinks();
        List<IcoRatingIco> icos = parseListOfIcoFromLinks(links);

        return icos;
    }

    //get all ICO links from the website
    private List<String> parseListOfIcoLinks(){
        List<String> icos = new ArrayList<String>(300);
        try {
            Document site = Jsoup.connect(link).timeout(10*1000).get();


            Elements links = site.select(".ico-projects-table tr");
            for(Element e : links){
                String ico = e.attr("data-href");
                if(ico != null && ico.length() > 1){
                    icos.add(ico);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return icos;
    }

    //get ico objects from links
    private List<IcoRatingIco> parseListOfIcoFromLinks(List<String> links){
        List<IcoRatingIco> icos = new ArrayList<>(300);
        for(String website : links){
            try{
                Document site = Jsoup.connect(link+website).timeout(10*1000).get();
                IcoRatingIco ico = new IcoRatingIco();

                setValuesFromContent(ico, site.select(".ico-card-content").first());
                setValuesFromProjectDetails(ico, site.select("#tab-1").first());
                setValuesFromIcoDetails(ico, site.select("#tab-2").first());
                setValuesFromTech(ico, site.select("#tab-3").first());

                //empty on website yet
                setValuesFromTeam(ico, site.select("#tab-4").first());

                icos.add(ico);
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return icos;
    }

    private void setValuesFromContent(IcoRatingIco ico, Element overview){
        Elements rows = overview.select("div.ico-card-table .ico-card-table__tr");

        for(Element r : rows){
            String title = r.select(".ico-card-table__td").first().text();
            String value = r.select(".ico-card-table__td").last().text();

            if(title.contains("Hype")) ico.setHypeScore(value);
            else if(title.contains("Risk")) ico.setRiskScore(value);
            else if(title.contains("Invest")) ico.setInvestScore(value);
            else if(title.contains("Category")) ico.setIco_category(value);
            else if(title.contains("Description")) ico.setDescription(value);
            else if(title.contains("Founded")) ico.setFounded(value);
            else if(title.contains("Website")) ico.setWebsite(value);
        }

        Element reportLink = overview.select(".ico-card-report a").first();
        if(reportLink != null) {
            ico.setReportLink(reportLink.attr("href"));
        }

        Element rating = overview.select(".ico-card-score__status").first();
        if(rating != null){
            ico.setRating(rating.text());
        }
    }

    private void setValuesFromProjectDetails(IcoRatingIco ico, Element overview){
        Elements rows = overview.select(".ico-card-table__tr");

        for(Element r : rows){
            String title = r.select(".ico-card-table__td").first().text();
            String value = r.select(".ico-card-table__td").last().text();

            if(title.contains("Features")) ico.setFeatures(value);
            else if(title.contains("Similar")) ico.setSimilarProjects(value);
        }
    }

    private void setValuesFromIcoDetails(IcoRatingIco ico, Element overview){
        Elements rows = overview.select(".ico-card-table__tr");

        for(Element r : rows){
            String title = r.select(".ico-card-table__td").first().text();
            String value = r.select(".ico-card-table__td").last().text();

            if(title.contains("date")) {
                if(value.contains(" ")) {
                    String[] date = value.split(" ");
                    if(date[0].contains(".")) {
                        String startDate = date[0];
                        int firstDot = startDate.indexOf('.');
                        int lastDot = startDate.lastIndexOf('.');

                        int day = Integer.parseInt(startDate.substring(0, firstDot));
                        int month = Integer.parseInt(startDate.substring(firstDot + 1, lastDot));
                        int year = Integer.parseInt(startDate.substring(lastDot + 1, startDate.length()));
                        LocalDate start = LocalDate.of(year, month, day);
                        ico.setStartDate(start.toEpochDay());
                    }

                    if(date.length > 2) {
                        if(date[2].contains(".")) {
                            String endDate = date[2];
                            int firstDot = endDate.indexOf('.');
                            int lastDot = endDate.lastIndexOf('.');

                            int day = Integer.parseInt(endDate.substring(0, firstDot));
                            int month = Integer.parseInt(endDate.substring(firstDot + 1, lastDot));
                            int year = Integer.parseInt(endDate.substring(lastDot + 1, endDate.length()));
                            LocalDate end = LocalDate.of(year, month, day);
                            ico.setEndDate(end.toEpochDay());
                        }
                    }
                }

                ico.setIcoDate(value);
            }
            else if(title.contains("distribution")) ico.setTokensDistribution(value);
            else if(title.contains("Sales")) ico.setTokenSales(value);
            else if(title.contains("Bounty")) ico.setBountyCamping(value);
            else if(title.contains("Escrow")) ico.setEscrow(value);
            else if(title.contains("Accepts")) ico.setAccepts(value);
            else if(title.contains("Dividends")) ico.setDividends(value);
        }
    }

    private void setValuesFromTech(IcoRatingIco ico, Element overview){
        Elements rows = overview.select(".ico-card-table__tr");

        for(Element r : rows){
            String title = r.select(".ico-card-table__td").first().html();
            String value = r.select(".ico-card-table__td").last().html();

            if(title.contains("details")) ico.setTechnicalDetails(value);
            else if(title.contains("developer")) ico.setProofOfDeveloper(value);
            else if(title.contains("source")) ico.setTheSourceCode(value);
            else if(title.contains("Bounty")) ico.setBountyCamping(value);
            else if(title.contains("Escrow")) ico.setEscrow(value);
            else if(title.contains("Accepts")) ico.setAccepts(value);
            else if(title.contains("Dividends")) ico.setDividends(value);
        }
    }

    //nothing here yet. This chapter of the website is empty
    private void setValuesFromTeam(IcoRatingIco ico, Element overview){
    }



}
