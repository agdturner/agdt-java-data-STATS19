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
package uk.ac.leeds.ccg.andyt.generic.data.stats19.core;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
//import uk.ac.leeds.ccg.andyt.data.postcode.Generic_UKPostcode_Handler;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Data;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.data.STATS19_Casualty_Handler;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.io.STATS19_Files;

/**
 *
 * @author geoagdt
 */
public class STATS19_Environment extends STATS19_OutOfMemoryErrorHandler
        implements Serializable {

    public final STATS19_Casualty_Handler hh;
    
    public static transient PrintWriter logPW;
    public static transient PrintWriter logPW0;

    public static void log(String s) {
        logPW.println(s);
        System.out.println(s);
    }

    public static void logEnd(String s) {
        s = "</" + s + ">";
        logPW.println(s);
        System.out.println(s);
    }

    public static void log1(String s) {
        System.out.println(s);
    }

    public static void logStart(String s) {
        s = "<" + s + ">";
        logPW.println(s);
        System.out.println(s);
    }

    public static void log0(String s) {
        logPW.println(s);
    }

    public transient Generic_Environment ge;
    public transient STATS19_Strings Strings;
    public transient STATS19_Files Files;
    
    /**
     * Data.
     */
    public STATS19_Data data;

    public transient static final String EOL = System.getProperty("line.separator");
    public File logF0;
    // For logging.
    File logF;

    public STATS19_Environment(Generic_Environment ge) {
        //Memory_Threshold = 3000000000L;
        Strings = new STATS19_Strings();
        Files = new STATS19_Files(Strings, ge.getFiles().getDataDir());
        File f;
        f = Files.getEnvDataFile();
        if (f.exists()) {
            loadData();
        } else {
            data = new STATS19_Data(this);
        }
        initlog(1);
        hh = new STATS19_Casualty_Handler(this, Files.getInputDataDir());
    }

    /**
     * A method to try to ensure there is enough memory to continue.
     *
     * @return
     */
    @Override
    public boolean checkAndMaybeFreeMemory() {
        System.gc();
        while (getTotalFreeMemory() < Memory_Threshold) {
//            int clear = clearAllData();
//            if (clear == 0) {
//                return false;
//            }
            if (!swapDataAny()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean swapDataAny(boolean handleOutOfMemoryError) {
        try {
            boolean result = swapDataAny();
            checkAndMaybeFreeMemory();
            return result;
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                boolean result = swapDataAny(HOOMEF);
                initMemoryReserve();
                return result;
            } else {
                throw e;
            }
        }
    }

    /**
     * Currently this just tries to swap WaAS data.
     *
     * @return
     */
    @Override
    public boolean swapDataAny() {
        boolean r;
        r = clearSomeData();
        if (r) {
            return r;
        } else {
            System.out.println("No WaAS data to clear. Do some coding to try "
                    + "to arrange to clear something else if needs be. If the "
                    + "program fails then try providing more memory...");
            return r;
        }
    }

    public boolean clearSomeData() {
        return data.clearSomeData();
    }

    public int clearAllData() {
        int r;
        r = data.clearAllData();
        return r;
    }
    
    public void cacheData() {
        File f;
        f = Files.getEnvDataFile();
        System.out.println("<cache data>");
        Generic_IO.writeObject(data, f);
        System.out.println("</cache data>");
    }

    public final void loadData() {
        File f;
        f = Files.getEnvDataFile();
        System.out.println("<load data>");
        data = (STATS19_Data) Generic_IO.readObject(f);
        System.out.println("<load data>");
    }

    public final void initlog(int i) {
        logF = new File(Files.getOutputDataDir(), "log" + i + ".txt");
        logPW = Generic_IO.getPrintWriter(logF, true); // Append to log file.
    }
}
