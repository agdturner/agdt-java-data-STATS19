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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            p.doLoadData = false;//true;//false;
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
            // Count the number of accidents in each year
            Iterator<S_CollectionID> ite = env.data.data.keySet().iterator();
            while(ite.hasNext()) {
                S_CollectionID cid = ite.next();
                S_Collection c = env.data.data.get(cid);
                if (c == null) {
                    c = env.data.getCollection(cid);
                }
                env.env.log("Year=" + cid.id + ", N=" + c.data.size());                
            }
            // Count the number of fatalities in each year
            // Count the number of severe injuries in each year
            // Count the number of slight injuries in each year
            // Count the number of accidents involving 0, 1, 2, 3, 3+ cars in each year
            // Count the number of accidents involving death of cyclist in each year
        } catch (IOException ex) {
            Logger.getLogger(S_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadData() throws IOException {
        String m = "loadData";
        env.logStartTag(m);
        Path indir = files.getInputDir();
        Path gendir = files.getGeneratedDir();
        Path outdir = Paths.get(gendir.toString(), S_Strings.s_Subsets);
        Files.createDirectories(outdir);
        Data_ReadCSV reader = new Data_ReadCSV(env.de);
        int startYear = 2009;
        int endYear = 2018;
        int syntax = 1;
        data = new S_Data(env);
        long al = 0;
        long cl = 0;
        long vl = 0;
        for (int year = startYear; year <= endYear; year++) {
            S_CollectionID cid = new S_CollectionID(year);
            S_Collection c = new S_Collection(cid);
            env.data.data.put(cid, c);
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
                        env.data.aiid2cid.put(id, cid);
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
                        S_ID_long id = env.data.ai2aiid.get(cr.getAcc_Index());
                        S_Record r = c.data.get(id);
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
                        S_ID_long id = env.data.ai2aiid.get(vr.getAcc_Index());
                        S_Record r = c.data.get(id);
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
            env.data.cacheCollection(cid, c);
            env.data.clearCollection(cid);
        }
        env.logEndTag(m);
        env.env.closeLog(env.logID);
        env.swapData();
    }

    boolean doLoadData = false;
}
