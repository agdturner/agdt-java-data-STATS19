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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Object;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.io.STATS19_Files;
import uk.ac.leeds.ccg.andyt.generic.util.Generic_Collections;

/**
 *
 * @author geoagdt
 */
public abstract class STATS19_Handler extends STATS19_Object {

    public transient STATS19_Files Files;
    public transient STATS19_Strings Strings;
    protected String TYPE;
    protected File INDIR;

    public STATS19_Handler(STATS19_Environment e) {
        super(e);
        Files = e.Files;
        Strings = e.Strings;
    }

    public File getInputFile(byte wave) {
        File f;
        String filename;
        filename = "was_wave_" + wave + "_" + TYPE + "_eul_final";
        if (wave < 4) {
            filename += "_v2";
        }
        filename += ".tab";
        f = new File(INDIR, filename);
        return f;
    }

    protected Object load(byte wave, File f) {
        Object r;
        String m;
        m = "load " + getString0(wave, f);
        STATS19_Environment.log1("<" + m + ">");
        r = Generic_IO.readObject(f);
        STATS19_Environment.log1("</" + m + ">");
        return r;
    }

    protected String getString0(byte wave, File f) {
        return "wave " + wave + " " + TYPE + " STATS19 file " + f;
    }

    protected void cache(byte wave, File f, Object o) {
        String m;
        m = "store " + getString0(wave, f);
        STATS19_Environment.log1("<" + m + ">");
        Generic_IO.writeObject(o, f);
        STATS19_Environment.log1("</" + m + ">");
    }

    public void cacheSubset(byte wave, Object o) {
        File f = new File(Files.getGeneratedSTATS19SubsetsDir(),
                TYPE + wave + "." + Strings.S_dat);
        cache(wave, f, o);
    }

    public void cacheSubsetLookups(byte wave, TreeMap<Short, HashSet<Short>> m0,
            TreeMap<Short, Short> m1) {
        File f;
        f = new File(Files.getGeneratedSTATS19SubsetsDir(),
                TYPE + wave + "To" + (wave + 1) + "." + Strings.S_dat);
        cache(wave, f, m0);
        f = new File(Files.getGeneratedSTATS19SubsetsDir(),
                TYPE + (wave + 1) + "To" + wave + "." + Strings.S_dat);
        cache(wave, f, m1);
    }

    public void cacheSubsetCollection(short cID, byte wave, Object o) {
        File f;
        f = new File(Files.getGeneratedSTATS19SubsetsDir(),
                getString1(wave, cID) + "." + Strings.S_dat);
        STATS19_Handler.this.cache(wave, f, o);
    }

    protected String getString1(byte wave, short cID) {
        return TYPE + wave + "_" + cID;
    }

    public Object[] loadSubsetLookups(byte wave) {
        Object[] r;
        r = new Object[2];
        TreeMap<Short, HashSet<Short>> m0;
        TreeMap<Short, Short> m1;
        File f;
        f = new File(Files.getGeneratedSTATS19SubsetsDir(),
                TYPE + wave + "To" + (wave + 1) + "." + Strings.S_dat);
        m0 = (TreeMap<Short, HashSet<Short>>) Generic_IO.readObject(f);
        r[0] = m0;
        cache(wave, f, m0);
        f = new File(Files.getGeneratedSTATS19SubsetsDir(),
                TYPE + (wave + 1) + "To" + wave + "." + Strings.S_dat);
        m1 = (TreeMap<Short, Short>) Generic_IO.readObject(f);
        r[1] = m1;
        return r;
    }

    public Object loadSubsetCollection(short cID, byte wave) {
        Object r;
        File f;
        f = new File(Files.getGeneratedSTATS19SubsetsDir(),
                getString1(wave, cID) + "." + Strings.S_dat);
        r = load(wave, f);
        return r;
    }
}
