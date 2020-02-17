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
package uk.ac.leeds.ccg.data.stats19.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import uk.ac.leeds.ccg.data.Data_Record;
import uk.ac.leeds.ccg.data.id.Data_RecordID;
import uk.ac.leeds.ccg.data.stats19.core.S_Environment;
import uk.ac.leeds.ccg.data.stats19.core.S_Object;
import uk.ac.leeds.ccg.data.stats19.data.accident.S_Accident_Record;
import uk.ac.leeds.ccg.data.stats19.data.casualty.S_Casualty_Record;
import uk.ac.leeds.ccg.data.stats19.data.id.S_ID_long;
import uk.ac.leeds.ccg.data.stats19.data.vehicle.S_Vehicle_Record;

/**
 * STATS19_Combined_Record
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class S_Record extends S_Object {

    public S_ID_long id;
    public S_Accident_Record aRec;
    public Set<S_Casualty_Record> cRecs;
    public Set<S_Vehicle_Record> vRecs;
    
    public S_Record(S_Environment e, S_ID_long i) {
        super(e);
        id = i;
        cRecs = new HashSet<>();
        vRecs = new HashSet<>();
    }

    
}
