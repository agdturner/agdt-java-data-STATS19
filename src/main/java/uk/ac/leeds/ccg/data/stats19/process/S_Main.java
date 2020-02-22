/*
 * Copyright 2018 Andy Turner, CCG, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.data.stats19.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Iterator;
import java.util.TreeMap;
import uk.ac.leeds.ccg.data.core.Data_Environment;
import uk.ac.leeds.ccg.data.format.Data_ReadCSV;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.data.stats19.core.S_Environment;
import uk.ac.leeds.ccg.data.stats19.io.S_Files;
import uk.ac.leeds.ccg.data.stats19.core.S_Object;
import uk.ac.leeds.ccg.data.stats19.core.S_Strings;
import uk.ac.leeds.ccg.data.stats19.data.S_Collection;
import uk.ac.leeds.ccg.data.stats19.data.S_Data;
import uk.ac.leeds.ccg.data.stats19.data.S_Record;
import uk.ac.leeds.ccg.data.stats19.data.accident.S_Accident_Record;
import uk.ac.leeds.ccg.data.stats19.data.casualty.S_Casualty_Record;
import uk.ac.leeds.ccg.data.stats19.data.id.S_CollectionID;
import uk.ac.leeds.ccg.data.stats19.data.id.S_ID_long;
import uk.ac.leeds.ccg.data.stats19.data.id.S_RecordID;
import uk.ac.leeds.ccg.data.stats19.data.vehicle.S_Vehicle_Record;
import uk.ac.leeds.ccg.data.stats19.data.vehicle.S_Vehicle_Record_2014Plus;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_IO;
import uk.ac.leeds.ccg.generic.util.Generic_Collections;

/**
 * S_Main
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class S_Main extends S_Object {

    private static final long serialVersionUID = 1L;

    // For convenience
    protected S_Data data;
    protected final S_Files files;

    public S_Main(S_Environment env) {
        super(env);
        data = env.data;
        files = env.files;
    }

    public static void main(String[] args) {
        try {
            Path dataDir = Paths.get(System.getProperty("user.home"),
                    S_Strings.s_data);
            S_Environment e = new S_Environment(
                    new Data_Environment(new Generic_Environment(
                            new Generic_Defaults(dataDir))));
            S_Main p = new S_Main(e);
            // Main switches
            //p.doLoadData = true;
            p.doAccidentsByYearAndMonth = true;
            p.doDeathsByYearAndMonth = true;
            p.run();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void run() throws ClassNotFoundException {
        try {
            if (doLoadData) {
                loadData();
            } else {
                env.data.env = env;
            }
            env.env.log("env.data.ai2aiid.size() " + env.data.ai2aiid.size());
            env.env.log("env.data.aiid2cid.size() " + env.data.aiid2cid.size());
            if (doAccidentsByYearAndMonth) {
                String name = "Accident";
                /**
                 * Table and plot the number of accidents per Month for each
                 * Year.
                 */
                Path nymdp = files.getNymd(name);
                TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> nymd;
                if (Files.exists(nymdp)) {
                    nymd = (TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>>) Generic_IO.readObject(nymdp);
                } else {
                    nymd = new TreeMap<>();
                    Iterator<S_CollectionID> ite = env.data.data.keySet().iterator();
                    while (ite.hasNext()) {
                        S_CollectionID cid = ite.next();
                        LocalDate date = env.data.cid2date.get(cid);
                        Integer year = date.getYear();
                        TreeMap<Integer, TreeMap<Integer, Integer>> nmd;
                        if (nymd.containsKey(year)) {
                            nmd = nymd.get(year);
                        } else {
                            nmd = new TreeMap<>();
                            nymd.put(year, nmd);
                        }
                        Integer month = date.getMonthValue();
                        TreeMap<Integer, Integer> nd;
                        if (nmd.containsKey(month)) {
                            nd = nmd.get(month);
                        } else {
                            nd = new TreeMap<>();
                            nmd.put(month, nd);
                        }
                        Integer day = date.getDayOfMonth();
                        S_Collection c = env.data.data.get(cid);
                        if (c == null) {
                            c = env.data.getCollection(cid);
                        }
                        int n = c.data.size();
                        Generic_Collections.addToCount(nd, day, n);
                        env.env.log("date=" + date.toString() + ", n=" + n);
                    }
                    Generic_IO.writeObject(nymd, nymdp);
                }
                printTable(name, nymd);
            }
            if (doDeathsByYearAndMonth) {
                String name = "Death";
                /**
                 * Table and plot the number of fatalities per Month for each
                 * Year.
                 */
                Path nymdp = files.getNymd(name);
                TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> nymd;
                if (Files.exists(nymdp)) {
                    nymd = (TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>>) Generic_IO.readObject(nymdp);
                } else {
                    nymd = new TreeMap<>();
                    Iterator<S_CollectionID> ite = env.data.data.keySet().iterator();
                    while (ite.hasNext()) {
                        S_CollectionID cid = ite.next();
                        LocalDate date = env.data.cid2date.get(cid);
                        Integer year = date.getYear();
                        TreeMap<Integer, TreeMap<Integer, Integer>> nmd;
                        if (nymd.containsKey(year)) {
                            nmd = nymd.get(year);
                        } else {
                            nmd = new TreeMap<>();
                            nymd.put(year, nmd);
                        }
                        Integer month = date.getMonthValue();
                        TreeMap<Integer, Integer> nd;
                        if (nmd.containsKey(month)) {
                            nd = nmd.get(month);
                        } else {
                            nd = new TreeMap<>();
                            nmd.put(month, nd);
                        }
                        Integer day = date.getDayOfMonth();
                        S_Collection c = env.data.data.get(cid);
                        if (c == null) {
                            c = env.data.getCollection(cid);
                        }
                        Iterator<S_ID_long> ite4 = c.data.keySet().iterator();
                        int n = 0;
                        while(ite.hasNext()) {
                            S_Record sr = c.data.get(ite4.next());
                            if (sr.aRec.getAccident_Severity() == 1) {
                                Iterator<S_Casualty_Record> ite5 = sr.cRecs.iterator();
                                while (ite5.hasNext()) {
                                    if (ite5.next().getCasualty_Severity() == 1) {
                                        n +=1;
                                    }
                                }                        
                            }
                        }
                        Generic_Collections.addToCount(nd, day, n);
                        env.env.log("date=" + date.toString() + ", n=" + n);
                    }
                    Generic_IO.writeObject(nymd, nymdp);
                }
                printTable(name, nymd);
            }
            // Count the number of severe injuries in each year
            // Count the number of slight injuries in each year
            // Count the number of accidents involving 0, 1, 2, 3, 3+ cars in each year
            // Count the number of accidents involving death of cyclist in each year
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Loads source csv data files into collections. Each collections is a
     * collection for a day.
     *
     * @throws IOException
     */
    public void loadData() throws IOException, Exception {
        String m = "loadData";
        env.logStartTag(m);
        Path indir = files.getInputDir();
        Path gendir = files.getGeneratedDir();
        Path outdir = Paths.get(gendir.toString(), S_Strings.s_Subsets);
        Files.createDirectories(outdir);
        Data_ReadCSV reader = new Data_ReadCSV(env.de);
        int startYear = 2009;
        int endYear = 2018;
        int syntax = 5;
        data = new S_Data(env);
        long al = 0;
        long cl = 0;
        long vl = 0;
        for (int year = startYear; year <= endYear; year++) {
            // Accidents
            Path aP = Paths.get(indir.toString(), "Accidents_"
                    + Integer.toString(year) + ".csv");
            env.env.log("Reading " + aP.toString());
            long lf = 0;
            try (BufferedReader br = Generic_IO.getBufferedReader(aP)) {
                reader.setStreamTokenizer(br, syntax);
                String line = reader.readLine();    // Skip header...
                env.log(line);                      // ... but log it.
                line = reader.readLine();
                while (line != null) {
                    S_RecordID i = new S_RecordID(al);
                    try {
                        S_Accident_Record ar = new S_Accident_Record(i, line);
                        S_ID_long id = new S_ID_long(al);
                        env.data.ai2aiid.put(ar.getAccident_Index(), id);
                        env.data.aiid2ai.put(id, ar.getAccident_Index());
                        LocalDate date = ar.getDate();
                        S_Collection c = env.data.getCollectionAddIfNecessary(date);
                        env.data.aiid2cid.put(id, c.id);
                        S_Record r = new S_Record(env, id);
                        r.aRec = ar;
                        c.data.put(id, r);
                        if (lf % 10000 == 0) {
                            env.env.log("loaded accident record " + lf);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                        env.env.log(ex.getMessage() + " loading accident record " + lf);
                    }
                    line = reader.readLine();
                    if (al % 100000 == 0) {
                        env.env.log("loaded " + al);
                    }
                    al++;
                    lf++;
                }
            }
            // Casualties
            Path cP = Paths.get(indir.toString(), "Casualties_"
                    + Integer.toString(year) + ".csv");
            env.env.log("Reading " + cP.toString());
            lf = 0;
            try (BufferedReader br = Generic_IO.getBufferedReader(cP)) {
                reader.setStreamTokenizer(br, syntax);
                String line = reader.readLine();    // Skip header...
                env.log(line);                      // ... but log it.
                line = reader.readLine();
                while (line != null) {
                    S_RecordID i = new S_RecordID(cl);
                    try {
                        S_Casualty_Record cr = new S_Casualty_Record(i, line);
                        S_ID_long aiid = env.data.ai2aiid.get(cr.getAcc_Index());
                        S_CollectionID cid = env.data.aiid2cid.get(aiid);
                        S_Record r = env.data.data.get(cid).data.get(aiid);
                        r.cRecs.add(cr);
                        if (lf % 10000 == 0) {
                            env.env.log("loaded casualty record " + lf);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                        env.env.log(ex.getMessage() + " loading casualty record " + lf);
                    }
                    line = reader.readLine();
                    if (cl % 100000 == 0) {
                        env.env.log("loaded " + cl);
                    }
                    cl++;
                    lf++;
                }
            }
            // Vehicles
            Path vP = Paths.get(indir.toString(), "Vehicles_"
                    + Integer.toString(year) + ".csv");
            env.env.log("Reading " + vP.toString());
            lf = 0;
            try (BufferedReader br = Generic_IO.getBufferedReader(vP)) {
                reader.setStreamTokenizer(br, syntax);
                String line = reader.readLine();    // Skip header...
                env.log(line);                      // ... but log it.
                line = reader.readLine();
                while (line != null) {
                    S_RecordID i = new S_RecordID(vl);
                    try {
                        S_Vehicle_Record vr;
                        if (year < 2014) {
                            vr = new S_Vehicle_Record(i, line);
                        } else {
                            vr = new S_Vehicle_Record_2014Plus(i, line);
                        }
                        S_ID_long aiid = env.data.ai2aiid.get(vr.getAcc_Index());
                        S_CollectionID cid = env.data.aiid2cid.get(aiid);
                        S_Record r = env.data.data.get(cid).data.get(aiid);
                        r.vRecs.add(vr);
                        if (lf % 10000 == 0) {
                            env.env.log("loaded vehicle record " + lf);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                        env.env.log(ex.getMessage() + " loading vehicle record " + lf);
                    }
                    line = reader.readLine();
                    if (vl % 100000 == 0) {
                        env.env.log("loaded " + vl);
                    }
                    vl++;
                    lf++;
                }
            }
            env.data.swapCollections();
        }
        env.logEndTag(m);
        env.env.closeLog(env.logID);
        env.swapData();
    }

    public void printTable(String name, TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> nymd) {
        env.env.log("");
        env.env.log(name + " count (by year and month)");
        env.env.log("Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec");
        Iterator<Integer> ite2 = nymd.keySet().iterator();
        while (ite2.hasNext()) {
            int year = ite2.next();
            String s = "" + year;
            TreeMap<Integer, TreeMap<Integer, Integer>> nmd = nymd.get(year);
            Iterator<Integer> ite3 = nmd.keySet().iterator();
            while (ite3.hasNext()) {
                int month = ite3.next();
                TreeMap<Integer, Integer> nd = nmd.get(month);
                long count = 0;
                Iterator<Integer> ite4 = nd.keySet().iterator();
                while (ite4.hasNext()) {
                    count += nd.get(ite4.next());
                }
                s += "," + count;
            }
            env.env.log(s);
        }
        env.env.log("");
        env.env.log(name + "s per day (by year and month)");
        env.env.log("Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec");
        ite2 = nymd.keySet().iterator();
        while (ite2.hasNext()) {
            int year = ite2.next();
            String s = "" + year;
            TreeMap<Integer, TreeMap<Integer, Integer>> nmd = nymd.get(year);
            Iterator<Integer> ite3 = nmd.keySet().iterator();
            while (ite3.hasNext()) {
                int month = ite3.next();
                TreeMap<Integer, Integer> nd = nmd.get(month);
                double count = 0;
                Iterator<Integer> ite4 = nd.keySet().iterator();
                while (ite4.hasNext()) {
                    count += nd.get(ite4.next());
                }
                YearMonth ym = YearMonth.of(year, month);
                double v = count / (double) ym.lengthOfMonth();
                s += "," + BigDecimal.valueOf(v).setScale(2,
                        RoundingMode.HALF_UP).toPlainString();
            }
            env.env.log(s);
        }
    }

    boolean doLoadData = false;
    boolean doAccidentsByYearAndMonth = false;
    boolean doDeathsByYearAndMonth = false;

}
