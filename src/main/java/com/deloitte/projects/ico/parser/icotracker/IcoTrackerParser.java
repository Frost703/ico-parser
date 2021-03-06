package com.deloitte.projects.ico.parser.icotracker;

import com.deloitte.projects.ico.parser.IcoParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//parser for icotracker.net
public class IcoTrackerParser implements IcoParser<IcoTrackerIco> {
    private static final String link = "https://icotracker.net";

    @Override
    public List<IcoTrackerIco> parse() {
        List<IcoTrackerIco> icos = new ArrayList<>(300);
        parseListOfIcos(icos);

        return icos;
    }

    private void parseListOfIcos(List<IcoTrackerIco> icos) {
        //parse icos from "Current"
        parseIcos(icos, link, "current");

        //parse icos from "Upcoming"
        parseIcos(icos, link, "upcoming");

        //parse icos from "Past"
        parseIcos(icos, link, "past");
    }

    private void parseIcos(List<IcoTrackerIco> icos, String link, String status) {
        try {
            Document site = Jsoup.connect(link+"/"+status).timeout(30*1000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();

            System.out.println("Parsing " + link+"/"+status);

            Elements cards = site.select(".card-block");

            for (Element c : cards) {
                IcoTrackerIco ico = new IcoTrackerIco();

                ico.setStatus(status);

                parseIcoDataFromTop(c.select(".cp-top").first(), ico);
                parseIcoDataFromBody(c.select(".cp-body").first(), ico);
                parseIcoDataFromFooter(c.select(".cp-body .cp-info").first(), ico);

                icos.add(ico);
            }
        } catch (IOException ioe) {
            System.out.println("Read timeout at IcoTrackerParser subsite. Trying again...");
            parseIcos(icos, link, status);
        }
    }

    private void parseIcoDataFromTop(Element top, IcoTrackerIco ico) {
        Element project = top.select(".cp-prj h2 a").first();
        if(project != null) {
            ico.setProject(project.text());
            ico.setIcoTrackerLink(link+project.attr("href"));
        }

        Element description = top.select(".cp-prj-descr div").first();
        if(description != null) {
            ico.setDescription(description.text());
        }
    }

    private void parseIcoDataFromBody(Element body, IcoTrackerIco ico) {
        Element row = body.select(".cp-info-i .row").first();

        Elements what = row.select(".cp-what .cp-line");
        for(Element e : what){
            String html = e.html();
            if(html.contains("Launch")) ico.setLaunch(getSpanValueFromElement(e));
            else if(html.contains("Base")) ico.setBase(getSpanValueFromElement(e));
            else if(html.contains("Whitepaper")) ico.setWhitepaper(e.select("a").attr("href"));
            else if(html.contains("Escrow")) ico.setEscrow(e.select("a").attr("href"));
        }

        Elements who = row.select(".cp-who .cp-line");
        for(Element e : who){
            String html = e.html();
            if(html.contains("cp-social")) continue;
            else if(html.contains("</i>")) ico.setIco_user(e.select("span").text());
            else if(html.contains("td-u")) ico.setWebsite(e.select("a").attr("href"));
        }

        Element cpIco = body.select(".cp-info-i .cp-ico span.text-black").first();
        if(cpIco != null) ico.setCpIco(cpIco.text());
    }

    private void parseIcoDataFromFooter(Element footer, IcoTrackerIco ico) {
        Elements description = footer.select("div.cp-row-sm.row").not("div.cp-info-i div.cp-row-sm.row");
        for(Element e : description){
            String html = e.html();
            if(html.contains("UTC")) {
                String date = e.select("span").first().text();
                ico.setIcoDate(date);

                String[] splitted = date.split(" ");
                if(splitted.length > 2){
                    String start = splitted[0];
                    String end = splitted[splitted.length - 1];

                    Date startDate = Date.valueOf(LocalDate.parse(start, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    Date endDate = Date.valueOf(LocalDate.parse(end, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                    ico.setStartDate(startDate);
                    ico.setEndDate(endDate);
                }
            }
            else if(html.contains("Bonus")) {
                Element scum = e.select("h3").first();
                if(scum != null) ico.setDetails(scum.text());
                else {
                    Element details = e.select(".cp-line").first();
                    if(details != null) ico.setDetails(details.text());
                }
            }
        }
    }

    private String getSpanValueFromElement(Element e){
        return e.select("span").first().text();
    }
}
