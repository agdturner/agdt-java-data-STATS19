/*
 * Copyright 2020 Andy Turner, University of Leeds.
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

package uk.ac.leeds.ccg.data.stats19.data.vehicle;

import uk.ac.leeds.ccg.data.stats19.data.id.S_RecordID;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class S_Vehicle_Record_2014Plus extends S_Vehicle_Record {
    
    protected Byte Age_of_Driver;
    
    public S_Vehicle_Record_2014Plus(S_RecordID i, String line) throws Exception {
        super(i);
        String[] s = line.split(",");
        initAcc_Index(s[0]);
        initVehicle_Reference(s[1]);
        initVehicle_Type(s[2]);
        initTowing_and_Articulation(s[3]);
        initVehicle_Manoeuvre(s[4]);
        initVehicle_Location_Restricted_Lane(s[5]);
        initJunction_Location(s[6]);
        initSkidding_and_Overturning(s[7]);
        initHit_Object_in_Carriageway(s[8]);
        initVehicle_Leaving_Carriageway(s[9]);
        initHit_Object_off_Carriageway(s[10]);
        initFirst_Point_of_Impact(s[11]);
        initWas_Vehicle_Left_Hand_Drive(s[12]);
        initJourney_Purpose_of_Driver(s[13]);
        initSex_of_Driver(s[14]);
        initAge_of_Driver(s[15]);
        initAge_Band_of_Driver(s[16]);
        initEngine_Capacity_CC(s[17]);
        initPropulsion_Code(s[18]);
        initAge_of_Vehicle(s[19]);
        initDriver_IMD_Decile(s[20]);
        initDriver_Home_Area_Type(s[21]);
    }
    
    protected final void initAge_of_Driver(String s) {
        if (!s.trim().isEmpty()) {
            Age_of_Driver = Byte.parseByte(s);
        } else {
            Age_of_Driver = null;
        }
    }
    
    public Byte getAge_of_Driver() {
        return Age_of_Driver;
    }
}
