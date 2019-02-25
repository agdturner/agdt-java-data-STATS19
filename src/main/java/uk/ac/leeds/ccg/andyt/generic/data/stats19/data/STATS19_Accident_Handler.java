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
package uk.ac.leeds.ccg.andyt.generic.data.stats19.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Environment;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;

/**
 *
 * @author geoagdt
 */
public class STATS19_Accident_Handler extends STATS19_Handler {

    public STATS19_Accident_Handler(STATS19_Environment e, File indir) {
        super(e);
        TYPE = STATS19_Strings.s_person;
        INDIR = indir;
    }

    protected File getFile(File dir, byte wave) {
        File f;
        f = new File(dir, STATS19_Strings.s_person + wave + ".dat");
        return f;
    }

    /**
     * Initialise cIDs, cPWs and cFs.
     *
     * @param cFs
     * @param cPWs
     * @param nOC Number of collections.
     * @param wave
     * @param outdir
     */
    protected void initialiseFilesAndPrintWriters(
            TreeMap<Short, File> cFs, HashMap<Short, PrintWriter> cPWs,
            int nOC, byte wave, File outdir) {
        for (short cID = 0; cID < nOC; cID++) {
            File f;
            f = new File(outdir, STATS19_Strings.s_person + wave + "_" + cID + ".tab");
            cPWs.put(cID, Generic_IO.getPrintWriter(f, false));
            cFs.put(cID, f);
        }
    }

    /**
     * Reads the header from br and writes this out to the
     * collectionIDPrintWriters.
     *
     * @param br
     * @param CPWs
     */
    protected void addHeader(
            BufferedReader br,
            HashMap<Short, PrintWriter> CPWs) {
        try {
            String header;
            header = br.readLine();
            CPWs.keySet().stream()                    .forEach(collectionID -> {
                        PrintWriter pw;
                        pw = CPWs.get(collectionID);
                        pw.println(header);
                    });
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            Logger.getLogger(STATS19_Accident_Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close the PrintWriters.
     *
     * @param cPWs
     */
    protected void closePrintWriters(HashMap<Short, PrintWriter> cPWs) {
        cPWs.keySet().stream()                .forEach(collectionID -> {
                    PrintWriter pw;
                    pw = cPWs.get(collectionID);
                    pw.close();
                });
    }

    /**
     *
     * @param c
     * @param numberOfCollections
     * @return (int) Math.ceil(c.size()/ (double) numberOfCollections)
     */
    protected int getCSize(Collection c, int numberOfCollections) {
        int r;
        int n;
        n = c.size();
        r = (int) Math.ceil((double) n / (double) numberOfCollections);
        return r;
    }

    /**
     * Loads subsets from a cache in generated data.
     *
     * @param nwaves
     * @return an Object[] r with size 5. r[] is a HashMap with keys that are
     * Integer CASEW1Each element is an Object[] ...
     */
    public Object[] loadSubsets(byte nwaves) {
        Object[] r;
        r = new Object[nwaves];
        for (byte wave = 1; wave <= nwaves; wave++) {
            // Load Waves 1 to 5 inclusive.
            r[wave] = loadSubset(wave);
        }
        return r;
    }

    public Object[] loadSubset(byte wave) {
        Object[] r;
        File dir;
        dir = Files.getGeneratedSTATS19Dir();
        dir = new File(dir, STATS19_Strings.s_Subsets);
        File f;
        f = new File(dir, TYPE + wave + "." + STATS19_Strings.s_dat);
        if (f.exists()) {
            r = (Object[]) Generic_IO.readObject(f);
        } else {
            r = null;
        }
        return r;
    }
}
