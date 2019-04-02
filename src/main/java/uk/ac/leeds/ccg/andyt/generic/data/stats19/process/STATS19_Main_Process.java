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

import java.io.File;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.io.STATS19_Files;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Object;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Data;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Casualty_Handler;

/**
 *
 * @author geoagdt
 */
public class STATS19_Main_Process extends STATS19_Object {

    // For convenience
    protected final STATS19_Data data;
    protected final STATS19_Files files;

    public STATS19_Main_Process(STATS19_Environment env) {
        super(env);
        data = env.data;
        files = env.files;
    }

    public static void main(String[] args) {
        STATS19_Main_Process p = new STATS19_Main_Process(
                new STATS19_Environment(new Generic_Environment()));
        // Main switches
        p.doJavaCodeGeneration = true;
        //p.doLoadDataIntoCaches = true; // rename/reuse just left here for convenience...
        p.run();
    }

    public void run() {
        String m = "run";
        env.logStartTag(m);
        if (doJavaCodeGeneration) {
            runJavaCodeGeneration();
        }

        File indir = files.getSTATS19InputDir();
        File generateddir = files.getGeneratedSTATS19Dir();
        File outdir = new File(generateddir, STATS19_Strings.s_Subsets);
        outdir.mkdirs();
        STATS19_Casualty_Handler ch = new STATS19_Casualty_Handler(env, indir);

        int chunkSize = 256; //1024; 512; 256;
        doDataProcessingStep1(indir, outdir, ch);
        env.logEndTag(m);
        env.ge.closeLog(env.logID);
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
        // Add code here!

    }

    boolean doJavaCodeGeneration = false;
    boolean doLoadDataIntoCaches = false;

}
