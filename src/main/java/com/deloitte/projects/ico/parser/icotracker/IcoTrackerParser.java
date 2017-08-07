package com.deloitte.projects.ico.parser.icotracker;

import com.deloitte.projects.ico.parser.IcoParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//parser for icotracker.net
public class IcoTrackerParser implements IcoParser {
    private static final String link = "https://icotracker.net";

    @Override
    public List<IcoTrackerIco> parse() {
        List<IcoTrackerIco> icos = new ArrayList<>(300);
        parseListOfIcos(icos);

        return icos;
    }

    private void parseListOfIcos(List<IcoTrackerIco> icos) {
        //parse icos from "Current"
        parseIcos(icos, link);

        //parse icos from "Upcoming"
        parseIcos(icos, link + "/upcoming");

        //parse icos from "Past"
        parseIcos(icos, link + "/past");
    }

    private void parseIcos(List<IcoTrackerIco> icos, String link) {
        try {
            Document site = Jsoup.connect(link).timeout(10*1000).get();
            Elements cards = site.select(".card-block");

            for (Element c : cards) {
                IcoTrackerIco ico = new IcoTrackerIco();

                parseIcoDataFromTop(c.select(".cp-top").first(), ico);
                parseIcoDataFromBody(c.select(".cp-body").first(), ico);
                parseIcoDataFromFooter(c.select(".cp-footer").first(), ico);

                icos.add(ico);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
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
        Elements description = footer.select(".cp-descr .row");
        for(Element e : description){
            String html = e.html();
            if(html.contains("UTC")) ico.setDate(e.select("span").first().text());
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
