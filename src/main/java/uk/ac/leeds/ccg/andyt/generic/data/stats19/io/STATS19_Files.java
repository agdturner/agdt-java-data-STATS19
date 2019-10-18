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
import uk.ac.leeds.ccg.andyt.data.io.Data_Files;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;

/**
 *
 * @author geoagdt
 */
public class STATS19_Files extends Data_Files {

    /**
     *
     * @param dir
     */
    public STATS19_Files(File dir) {
        super(dir);
    }

    public File getSTATS19InputDir() {
        return new File(getInputDir(), STATS19_Strings.s_STATS19);
    }

    public File getGeneratedSTATS19Dir() {
        File r = new File(getGeneratedDir(), STATS19_Strings.s_STATS19);
        r.mkdirs();
        return r;
    }
    
    public File getGeneratedSTATS19SubsetsDir() {
        File r = new File(getGeneratedSTATS19Dir(), STATS19_Strings.s_Subsets);
        r.mkdirs();
        return r;
    }
}
