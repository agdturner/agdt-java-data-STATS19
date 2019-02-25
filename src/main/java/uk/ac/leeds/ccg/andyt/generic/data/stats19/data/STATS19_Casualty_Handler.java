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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Environment;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.util.Generic_Collections;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;

/**
 *
 * @author geoagdt
 */
public class STATS19_Casualty_Handler extends STATS19_Handler {

    public STATS19_Casualty_Handler(STATS19_Environment e, File indir) {
        super(e);
        TYPE = STATS19_Strings.s_casualty;
        INDIR = indir;
    }

    /**
     * Loads casualty records.
     */
    public Object[] load() {
        Object[] r;
        r = new Object[0];
        return r;
    }

    /**
     * Loads all hhold STATS19 from a cache in generated data if it exists and from
     * the source input data otherwise. It requires more than 4GB, but less than
     * 7GB of memory to hold all the data in memory.
     *
     * @param wave
     * @return an Object[] r with size 5. Each element is an Object[] containing
     * the data from loading each wave...
     */
    public Object[] loadAll(byte wave) {
        Object[] r;
        r = new Object[0];
        
        return r;
    }

    /**
     *
     * @param wave
     * @return
     */
    protected File getFile(byte wave) {
        File r;
        File dir;
        dir = Files.getGeneratedSTATS19Dir();
        dir = new File(dir, STATS19_Strings.s_Subsets);
        r = new File(dir, TYPE + wave + "." + STATS19_Strings.s_dat);
        return r;
    }

}
