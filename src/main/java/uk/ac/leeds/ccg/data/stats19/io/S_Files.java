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
package uk.ac.leeds.ccg.data.stats19.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.data.io.Data_Files;
import uk.ac.leeds.ccg.data.stats19.core.S_Strings;

/**
 * S_Files
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class S_Files extends Data_Files {

    private static final long serialVersionUID = 1L;

    /**
     * @param dir What {@link #dir} is set using.
     * @throws java.io.IOException If encountered.
     */
    public S_Files(Path dir) throws IOException {
        super(dir);
    }

    public Path getGeneratedSubsetsDir() throws IOException {
        Path r = Paths.get(getGeneratedDir().toString(), S_Strings.s_Subsets);
        Files.createDirectories(r);
        return r;
    }

    /**
     * @return A path to a file for storing the accident to accident id lookup.
     * @throws IOException If encountered.
     */
    public Path getAi2aiid() throws IOException {
        return Paths.get(getGeneratedDir().toString(), "ai2aiid.dat");
    }

    /**
     * @return A path to a file for storing the accident id to accident lookup.
     * @throws IOException If encountered.
     */
    public Path getAiid2ai() throws IOException {
        return Paths.get(getGeneratedDir().toString(), "aiid2ai.dat");
    }

    /**
     * @return A path to a file for storing the accident id to collection id
     * lookup.
     * @throws IOException If encountered.
     */
    public Path getAiid2cid() throws IOException {
        return Paths.get(getGeneratedDir().toString(), "aiid2cid.dat");
    }

    /**
     * @param subsetType A name for subsetType directory.
     * @param name0 The subset name.
     * @param name Filename prefix (the type of output).
     * @return A path for a file for storing the number of accidents in each
     * year, month, day.
     */
    public Path getNymd(String subsetType, String name0, String name) throws IOException {
        if (subsetType.isBlank()) {
            return Paths.get(getGeneratedDir().toString(), "SummaryCounts",
                    name0, name + "_nymd.dat");
        } else {
            return Paths.get(getGeneratedDir().toString(), "SummaryCounts",
                    subsetType, name0, name + "_nymd.dat");
        }
    }
}
