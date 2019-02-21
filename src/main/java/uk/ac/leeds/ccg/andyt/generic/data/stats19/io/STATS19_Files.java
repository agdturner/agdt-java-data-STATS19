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
package uk.ac.leeds.ccg.andyt.generic.data.stats19.io;

import java.io.File;
import java.io.Serializable;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Files;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;

/**
 *
 * @author geoagdt
 */
public class STATS19_Files extends Generic_Files implements Serializable {

    /**
     *
     * @param s
     */
    public STATS19_Files(STATS19_Strings s) {
        super(s);
    }

    /**
     *
     * @param s
     * @param dataDir
     */
    public STATS19_Files(STATS19_Strings s, File dataDir) {
        super(s, dataDir);
    }

    @Override
    public STATS19_Strings getStrings(){
        return (STATS19_Strings) strings;
    }

    public File getSTATS19InputDir() {
        File r;
        r = new File(getInputDataDir(), "STATS19");
        return r;
    }

    public File getGeneratedSTATS19Dir() {
        File dir;
        dir = getGeneratedDataDir();
        File f;
        f = new File(dir, "STATS19");
        f.mkdirs();
        return f;
    }
    
    public File getGeneratedSTATS19SubsetsDir() {
        File dir;
        dir = getGeneratedSTATS19Dir();
        File f;
        f = new File(dir, "Subsets");
        f.mkdirs();
        return f;
    }

    public File getEnvDataFile() {
        return new File(getGeneratedDataDir(), "Env.dat");
    }
}
