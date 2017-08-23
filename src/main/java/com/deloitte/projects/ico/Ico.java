package com.deloitte.projects.ico;


import com.deloitte.projects.ico.dao.DBController;
import com.deloitte.projects.ico.parser.icorating.IcoRatingIco;
import com.deloitte.projects.ico.parser.icorating.IcoRatingParser;
import com.deloitte.projects.ico.parser.icotracker.IcoTrackerIco;
import com.deloitte.projects.ico.parser.icotracker.IcoTrackerParser;
import com.deloitte.projects.ico.parser.tokenmarket.TokenMarketIco;
import com.deloitte.projects.ico.parser.tokenmarket.TokenMarketParser;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

//Developed to get fast results. Project is hard to test and need refactoring
public class Ico {
    public static void main(String[] args) throws IOException{
        //   https://jsoup.org/cookbook/extracting-data/selector-syntax
        LocalTime start = LocalTime.now();

        //239 items. ~15 seconds
        List<IcoRatingIco> icorating = new IcoRatingParser().parse();
        int ratingTime = LocalTime.now().toSecondOfDay();
        System.out.println("icorating.com parsed in " + (ratingTime - start.toSecondOfDay()) + " seconds");

        //229 items.
        List<IcoTrackerIco> icotracking = new IcoTrackerParser().parse();
        int trackerTime = LocalTime.now().toSecondOfDay();
        System.out.println("icotracking.net parsed in " + (trackerTime - ratingTime) + "seconds");

        //302 items.
        List<TokenMarketIco> tokenmarket = new TokenMarketParser().parse();
        int marketTime = LocalTime.now().toSecondOfDay();
        System.out.println("tokenmarket.net parsed in " + (marketTime - trackerTime) + "seconds");

        System.out.println("The total time of parsing - " + ((LocalTime.now().toSecondOfDay() - start.toSecondOfDay())) + " seconds");

        LocalTime dbstart = LocalTime.now();
        icorating.forEach(DBController::save);
        icotracking.forEach(DBController::save);
        tokenmarket.forEach(DBController::save);

        System.out.println("DB operations took " + ((LocalTime.now().toSecondOfDay() - dbstart.toSecondOfDay())) + " seconds");
    }
}
