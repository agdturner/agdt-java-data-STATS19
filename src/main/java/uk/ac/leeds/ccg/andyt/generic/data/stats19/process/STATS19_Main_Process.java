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
package uk.ac.leeds.ccg.andyt.generic.data.stats19.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.io.STATS19_Files;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Object;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Collection;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Combined_Record;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Data;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Casualty_Handler;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Accident_Handler;

/**
 *
 * @author geoagdt
 */
public class STATS19_Main_Process extends STATS19_Object {

    // For convenience
    protected final STATS19_Data data;
    protected final STATS19_Strings Strings;
    protected final STATS19_Files Files;


    public STATS19_Main_Process(STATS19_Environment env) {
        super(env);
        data = env.data;
        Strings = env.Strings;
        Files = env.Files;
    }

    public static void main(String[] args) {
        Generic_Environment ge = new Generic_Environment();
        STATS19_Main_Process p;
        STATS19_Environment env;
        env = new STATS19_Environment(ge);
        p = new STATS19_Main_Process(env);
        // Main switches
        //p.doJavaCodeGeneration = true;
        p.doLoadDataIntoCaches = true; // rename/reuse just left here for convenience...
        p.run();
    }

    public void run() {
        Env.logF0 = new File(Files.getOutputDataDir(), "log0.txt");
        STATS19_Environment.logPW0 = Generic_IO.getPrintWriter(Env.logF0, false); // Overwrite log file.

        if (doJavaCodeGeneration) {
            runJavaCodeGeneration();
        }

        File indir;
        File outdir;
        File generateddir;
        STATS19_Casualty_Handler hholdHandler;

        indir = Files.getSTATS19InputDir();
        generateddir = Files.getGeneratedSTATS19Dir();
        outdir = new File(generateddir, "Subsets");
        outdir.mkdirs();
        hholdHandler = new STATS19_Casualty_Handler(Env, indir);

        int chunkSize;
        chunkSize = 256; //1024; 512; 256;
        doDataProcessingStep1(indir, outdir, hholdHandler);
        STATS19_Environment.logPW.close();
    }

    /**
     * Method for running JavaCodeGeneration
     */
    public void runJavaCodeGeneration() {
        String[] args;
        args = null;
        STATS19_JavaCodeGenerator.main(args);
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
    public void doDataProcessingStep1(File indir, File outdir,
            STATS19_Casualty_Handler hholdHandler) {
        Env.initlog(1);
        // Add code here!
        STATS19_Environment.logPW.close();
    }

    boolean doJavaCodeGeneration = false;
    boolean doLoadDataIntoCaches = false;

}
