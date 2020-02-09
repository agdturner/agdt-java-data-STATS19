/*
 * Copyright 2018 geoagdt.
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.data.core.Data_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.data.stats19.core.STATS19_Environment;
import uk.ac.leeds.ccg.data.stats19.io.STATS19_Files;
import uk.ac.leeds.ccg.data.stats19.core.STATS19_Object;
import uk.ac.leeds.ccg.data.stats19.core.STATS19_Strings;
import uk.ac.leeds.ccg.data.stats19.data.STATS19_Data;
import uk.ac.leeds.ccg.data.stats19.data.STATS19_Casualty_Handler;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;

/**
 * STATS19_Main
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class STATS19_Main extends STATS19_Object {

    // For convenience
    protected final STATS19_Data data;
    protected final STATS19_Files files;

    public STATS19_Main(STATS19_Environment env) {
        super(env);
        data = env.data;
        files = env.files;
    }

    public static void main(String[] args) {
        try {
            Path dataDir = Paths.get(System.getProperty("user.home"),
                    STATS19_Strings.s_data, STATS19_Strings.s_data,
                    STATS19_Strings.s_STATS19);
            STATS19_Environment e = new STATS19_Environment(
                    new Data_Environment(new Generic_Environment(
                    new Generic_Defaults(dataDir))));
            STATS19_Main p = new STATS19_Main(e);
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
            Path indir = files.getSTATS19InputDir();
            Path generateddir = files.getGeneratedSTATS19Dir();
            Path outdir = Paths.get(generateddir.toString(), STATS19_Strings.s_Subsets);
            Files.createDirectories(outdir);
            STATS19_Casualty_Handler ch = new STATS19_Casualty_Handler(env, indir);
            
            int chunkSize = 256; //1024; 512; 256;
            doDataProcessingStep1(indir, outdir, ch);
            env.logEndTag(m);
            env.env.closeLog(env.logID);
        } catch (IOException ex) {
            Logger.getLogger(STATS19_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Read input data and create subsets. Organise for person records that each
     * subset is split into separate files one for each collection. The
     * collections will be merged one by one in doDataProcessingStep2.
     *
     * @param indir
     * @param outdir
     * @param hholdHandler
     */
    public void doDataProcessingStep1(Path indir, Path outdir,
            STATS19_Casualty_Handler hholdHandler) {
        // Add code here!

    }

    boolean doLoadDataIntoCaches = false;

}
