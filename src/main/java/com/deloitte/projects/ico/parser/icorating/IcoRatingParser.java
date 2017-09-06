package com.deloitte.projects.ico.parser.icorating;


import com.deloitte.projects.ico.parser.IcoParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//parser for http://icorating.com/
public class IcoRatingParser implements IcoParser<IcoRatingIco> {
    private static final String link = "http://icorating.com";

    private List<String> ongoing;
    private List<String> past;
    private List<String> upcoming;
    private List<String> scum;

    public List<IcoRatingIco> parse(){
        parseListOfIcoLinks();
        List<IcoRatingIco> icos = parseListOfIcoFromLinks(ongoing, "current");
        icos.addAll(parseListOfIcoFromLinks(upcoming, "upcoming"));
        icos.addAll(parseListOfIcoFromLinks(past, "past"));
        icos.addAll(parseListOfIcoFromLinks(scum, "scum"));

        return icos;
    }

    //get all ICO links from the website
    private void parseListOfIcoLinks(){
        try {
            Document site = Jsoup.connect(link).timeout(30*1000).get();

            System.out.println("Parsing " + link);
            //get ongoing links
            ongoing = getListOfLinksFromElements(site.select("div[data-idx=0]"));
            past = getListOfLinksFromElements(site.select("div[data-idx=2]"));
            upcoming = getListOfLinksFromElements(site.select("div[data-idx=1]"));
            scum = getListOfLinksFromElements(site.select("div[data-idx=3]"));
        } catch (IOException e) {
            System.out.println("Read timeout at IcoRatingParser site. Trying again...");
            parseListOfIcoLinks();
        }

    }

    private List<String> getListOfLinksFromElements(Elements elem){
        ArrayList<String> list = new ArrayList<>(100);

        Elements links = elem.select(".ico-projects-table tr");
        for(Element e : links){
            String ico = e.attr("data-href");
            if(ico != null && ico.length() > 1){
                list.add(ico);
            }
        }

        return list;
    }

    //get ico objects from links
    private List<IcoRatingIco> parseListOfIcoFromLinks(List<String> links, String status){
        List<IcoRatingIco> icos = new ArrayList<>(300);
        for(String website : links){
            try{
                Document site = Jsoup.connect(link+website).timeout(30*1000).get();
                System.out.println("Parsing " + link+website);

                IcoRatingIco ico = new IcoRatingIco();
                ico.setStatus(status);

                String project = site.select("div.ico-name-title").first().text();
                ico.setProject(project);

                setValuesFromContent(ico, site.select(".ico-card-content").first());
                setValuesFromProjectDetails(ico, site.select("#tab-1").first());
                setValuesFromIcoDetails(ico, site.select("#tab-2").first());
                setValuesFromTech(ico, site.select("#tab-3").first());

                //empty on website yet
                setValuesFromTeam(ico, site.select("#tab-4").first());

                icos.add(ico);
            } catch(IOException ioe) {
                System.out.println("Read timeout at IcoRatingParser subsite. Trying again...");
                parseListOfIcoFromLinks(links, status);
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
            else if(title.contains("Social")){
                Elements links = r.select(".ico-card-table__td").last().select("a");
                StringBuilder builder = new StringBuilder();
                for(Element link : links){
                    builder.append(link.attr("href")).append("   ,   ");
                }

                ico.setLinks(builder.toString());
            }
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
                    if(date[0].contains("T") && date[0].contains(":")){
                        ico.setStartDate(getISODate(date[0]));
                    } else if(date[0].contains(".")) {
                        ico.setStartDate(getDate(date[0]));
                    }

                    if(date.length > 2) {
                        if(date[2].contains("T") && date[0].contains(":")){
                            ico.setEndDate(getISODate(date[2]));
                        } else if(date[2].contains(".")) {
                            ico.setEndDate(getDate(date[2]));
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

    private Date getDate(String startDate){
        int firstDot = startDate.indexOf('.');
        int lastDot = startDate.lastIndexOf('.');

        int day = Integer.parseInt(startDate.substring(0, firstDot));
        int month = Integer.parseInt(startDate.substring(firstDot + 1, lastDot));
        int year = Integer.parseInt(startDate.substring(lastDot + 1, startDate.length()));

        return Date.valueOf(LocalDate.of(year, month, day));
    }

    private Date getISODate(String startDate){
        String date = startDate.substring(0, startDate.indexOf("T"));
        int firstDot = date.indexOf('-');
        int lastDot = date.lastIndexOf('-');

        int year = Integer.parseInt(date.substring(0, firstDot));
        int month = Integer.parseInt(date.substring(firstDot + 1, lastDot));
        int day = Integer.parseInt(date.substring(lastDot + 1, date.length()));

        return Date.valueOf(LocalDate.of(year, month, day));
    }

}
