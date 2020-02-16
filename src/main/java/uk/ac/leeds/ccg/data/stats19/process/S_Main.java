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
import uk.ac.leeds.ccg.data.stats19.data.id.S_CollectionID;
import uk.ac.leeds.ccg.data.stats19.data.id.S_ID_long;
import uk.ac.leeds.ccg.data.stats19.data.id.S_RecordID;
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
            //p.doLoadDataIntoCaches = true; // rename/reuse just left here for convenience...
            p.run();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void run() {
        try {
            String m = "run";
            env.logStartTag(m);
            Path indir = files.getInputDir();
            Path gendir = files.getGeneratedDir();
            Path outdir = Paths.get(gendir.toString(), S_Strings.s_Subsets);
            Files.createDirectories(outdir);
            int chunkSize = 256; //1024; 512; 256;
            Data_ReadCSV reader = new Data_ReadCSV(env.de);
            int startYear = 2009;
            int endYear = 2018;
            int nYears = endYear - startYear + 1;
            int syntax = 1;
            data = new S_Data(env);
            long l = 0;
            S_Collection c = new S_Collection(new S_CollectionID(0));
            for (int year = startYear; year <= endYear; year++) {
                Path aP = Paths.get(indir.toString(), "Accidents_"
                        + Integer.toString(year) + ".csv");
                long lf = 0;
                try (BufferedReader br = Generic_IO.getBufferedReader(aP)) {
                    reader.setStreamTokenizer(br, syntax);
                    String line = reader.readLine();    // Skip header...
                    env.log(line);                      // ... but log it.
                    line = reader.readLine();
                    while (line != null) {
                        S_RecordID i = new S_RecordID(l);
                        try {
                            S_Accident_Record a = new S_Accident_Record(i, line);
                            S_ID_long id = new S_ID_long(l);
                            S_Record r = new S_Record(env, id);
                            r.aRec = a;
                            if (l % 10000 == 0) {
                                env.env.log("loaded accident record " + l);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace(System.err);
                            env.env.log(ex.getMessage() + " loading accident record " + l);
                        }
                        line = reader.readLine();
                        l++;
                        lf ++;
                    }
                }
            }
            env.logEndTag(m);
            env.env.closeLog(env.logID);
        } catch (IOException ex) {
            Logger.getLogger(S_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
