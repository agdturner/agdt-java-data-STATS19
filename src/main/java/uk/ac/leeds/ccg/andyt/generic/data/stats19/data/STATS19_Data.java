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
import java.util.HashMap;
import java.util.Iterator;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Object;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;

/**
 *
 * @author geoagdt
 */
public class STATS19_Data extends STATS19_Object {

    /**
     * The main STATS19 data store. Keys are Collection IDs.
     */
    public HashMap<Short, STATS19_Collection> data;

    /**
     * Looks up from a CASEW1 to the Collection ID.
     */
    public HashMap<Short, Short> CASEW1ToCID;

    public STATS19_Collection getCollection(short collectionID) {
        STATS19_Collection r;
        r = data.get(collectionID);
        if (r == null) {
            r = (STATS19_Collection) loadSubsetCollection(collectionID);
            data.put(collectionID, r);
        }
        return r;
    }

    public void clearCollection(short cID) {
        STATS19_Collection c;
        data.put(cID, null);
    }

    public STATS19_Data(STATS19_Environment se) {
        super(se);
        data = new HashMap<>();
        CASEW1ToCID = new HashMap<>();
    }

    public boolean clearSomeData() {
        Iterator<Short> ite;
        ite = data.keySet().iterator();
        short cID;
        while (ite.hasNext()) {
            cID = ite.next();
            STATS19_Collection c;
            c = data.get(cID);
            cacheSubsetCollection(cID, c);
            data.put(cID, null);
            return true;
        }
        return false;
    }

    public int clearAllData() {
        int r;
        r = 0;
        Iterator<Short> ite;
        ite = data.keySet().iterator();
        short cID;
        while (ite.hasNext()) {
            cID = ite.next();
            STATS19_Collection c;
            c = data.get(cID);
            cacheSubsetCollection(cID, c);
            data.put(cID, null);
            r++;
        }
        return r;
    }

    /**
     *
     * @param cID the value of collectionID
     * @param o the value of o
     */
    public void cacheSubsetCollection(short cID, Object o) {
        File f;
        f = new File(Env.files.getGeneratedSTATS19SubsetsDir(),
                "STATS19_" + cID + "." + STATS19_Strings.s_dat);
        cache(f, o);
    }

    /**
     *
     * @param cID the value of collectionID
     * @return
     */
    public Object loadSubsetCollection(short cID) {
        Object r;
        File f;
        f = new File(Env.files.getGeneratedSTATS19SubsetsDir(),
                "STATS19_" + cID + "." + STATS19_Strings.s_dat);
        r = load(f);
        return r;
    }

    /**
     *
     * @param f the File to load Object result from.
     * @return
     */
    protected Object load(File f) {
        Object r;
        STATS19_Environment.log1("<load File " + f + ">");
        r = Generic_IO.readObject(f);
        STATS19_Environment.log1("</load File " + f + ">");
        return r;
    }

    /**
     *
     * @param f the value of cf
     * @param o the value of o
     */
    protected void cache(File f, Object o) {
        STATS19_Environment.log1("<cache File " + f + ">");
        Generic_IO.writeObject(o, f);
        STATS19_Environment.log1("</cache File " + f + ">");
    }

}
