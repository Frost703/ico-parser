package com.deloitte.projects.ico.parser.tokenmarket;

import com.deloitte.projects.ico.parser.IcoParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//parser for tokenmarket.net/blockchain/all-assets
public class TokenMarketParser implements IcoParser<TokenMarketIco> {
    private final static String link = "https://tokenmarket.net/blockchain/all-assets";

    @Override
    public List<TokenMarketIco> parse() throws IOException {
        return parseListOfIcos();
    }

    private List<TokenMarketIco> parseListOfIcos() throws IOException{
        ArrayList<TokenMarketIco> icos = new ArrayList<>(200);
        try {
            Document website = Jsoup.connect(link)
                                    .timeout(30*1000)
                                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                                    .referrer("http://www.google.com")
                                    .get();
            System.out.println("Parsing " + link);

            Elements rows = website.select("table.table-assets tr");
            for(Element r : rows){
                String link = r.select("a.logo-link").attr("href");
                if(link != null && link.length() > 0){
                    icos.add(getIcoFromLink(link.trim()));
                }
            }

        } catch (IOException e) {
            System.out.println("Read timeout at TokenMarketParser subsite. Trying again...");
            parseListOfIcos();
        }

        return icos;
    }

    private TokenMarketIco getIcoFromLink(String href) throws IOException {
        TokenMarketIco ico = new TokenMarketIco();
        try{
            Document site = Jsoup.connect(href)
                    .timeout(30*1000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            System.out.println("Pasring "+href);

            ico.setName(site.select("div.col-md-6 h1").text());
            ico.setLead(site.select("p.lead").text());

            Elements content = site.select("div.row");
            Elements overviewRows = null;
            for(Element c : content){
                if(c.select("h2").text().contains("Overview"))
                    overviewRows = c.select("div.col-md-6").first().select("table.table-asset-data tr");
            }

            if(overviewRows == null)
                overviewRows = content.select("div.col-md-6 tr");

            for(Element e : overviewRows) {
                if (e.select("th").html().contains("Symbol"))
                    ico.setSymbol(e.select("td").text());

                else if (e.select("th").html().contains("opening")) {
                    Element temp = e.select("td p").first();
                    if(temp != null)
                        ico.setOpeningDate(temp.text());
                }

                else if(e.select("th").html().contains("closing")) {
                    Element temp = e.select("td p").first();
                    if(temp != null)
                        ico.setClosingDate(temp.text());
                }

                else if(e.select("th").html().contains("Concept"))
                    ico.setConcept(e.select("td").text());
            }

            ico.setTechnology(overviewRows.last().select("th").text());

            Elements teamRows = site.select("div.table-responsive").first()
                                        .select("td p");
            StringBuilder teamMembers = new StringBuilder();

            for(Element p : teamRows){
                teamMembers.append(p.text() + System.lineSeparator());
            }
            ico.setTeam(teamMembers.toString());

            Element content2 = content.select("div.col-md-6").last();
            Elements links = content2.select("div.table-responsive tr a");
            StringBuilder urls = new StringBuilder();
            for(Element l : links){
                urls.append(l.attr("href") + System.lineSeparator());
            }

            ico.setLinks(urls.toString());

            Elements scores = content2.select("table.asset-list-github tr");
            for(Element s : scores){
                if(s.html().contains("Domain"))
                    ico.setDomainScore(s.select("td").text());

                else if(s.html().contains("Backlinks"))
                    ico.setBacklinksScore(s.select("td").text());

                else if(s.html().contains("Commits"))
                    ico.setCommits(s.select("td").text());
            }
        } catch (IOException ioe){
            System.out.println("Read timeout at TokenMarketParser subsite. Trying again...");
            getIcoFromLink(href);
        }

        return ico;
    }
}
