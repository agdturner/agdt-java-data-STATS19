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
import java.io.Serializable;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
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

    public Generic_Environment ge;
    public int logID;
    public final STATS19_Casualty_Handler ch;
    public STATS19_Data data;
    public STATS19_Files files;
    
    public transient static final String EOL = System.getProperty("line.separator");
    
    public STATS19_Environment(Generic_Environment ge) {
        //Memory_Threshold = 3000000000L;
        files = new STATS19_Files(ge.getFiles().getDataDir());
        File f  = files.getEnvDataFile();
        if (f.exists()) {
            loadData();
        } else {
            data = new STATS19_Data(this);
        }
        logID = ge.initLog(STATS19_Strings.s_STATS19);
        ch = new STATS19_Casualty_Handler(this, files.getInputDataDir());
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

    /**
     * 
     * @param hoome handleOutOfMemoryError
     * @return 
     */
    @Override
    public boolean swapDataAny(boolean hoome) {
        try {
            boolean r = swapDataAny();
            checkAndMaybeFreeMemory();
            return r;
        } catch (OutOfMemoryError e) {
            if (hoome) {
                clearMemoryReserve();
                boolean r = swapDataAny(HOOMEF);
                initMemoryReserve();
                return r;
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
        boolean r = clearSomeData();
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
        return data.clearAllData();
    }
    
    public void cacheData() {
        File f = files.getEnvDataFile();
        String m = "cacheData to " + f;
        logStartTag(m);
        Generic_IO.writeObject(data, f);
        logEndTag(m);
    }

    public final void loadData() {
        File f = files.getEnvDataFile();
        String m = "loadData from " + f;
        logStartTag(m);
        data = (STATS19_Data) Generic_IO.readObject(f);
        logEndTag(m);
    }

    /**
     * For convenience.
     * {@link Generic_Environment#logStartTag(java.lang.String, int)}
     *
     * @param s The tag name.
     */
    public final void logStartTag(String s) {
        ge.logStartTag(s, logID);
    }

    /**
     * For convenience. {@link Generic_Environment#log(java.lang.String, int)}
     *
     * @param s The message to be logged.
     */
    public void log(String s) {
        ge.log(s, logID);
    }

    /**
     * For convenience.
     * {@link Generic_Environment#logEndTag(java.lang.String, int)}
     *
     * @param s The tag name.
     */
    public final void logEndTag(String s) {
        ge.logEndTag(s, logID);
    }
}
