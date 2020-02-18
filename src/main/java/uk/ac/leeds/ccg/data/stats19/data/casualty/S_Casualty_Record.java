/*
 * Source code generated by uk.ac.leeds.ccg.data.stats19.process.STATS19_JavaCodeGenerator
 */
package uk.ac.leeds.ccg.data.stats19.data.casualty;

import uk.ac.leeds.ccg.data.Data_Record;
import uk.ac.leeds.ccg.data.stats19.data.id.S_RecordID;

public class S_Casualty_Record extends Data_Record {

    private static final long serialVersionUID = 1L;

    protected String Acc_Index;
    protected Short Vehicle_Reference;
    protected Short Casualty_Reference;
    protected Byte Casualty_Class;
    protected Byte Sex_of_Casualty;
    protected Byte Age_Band_of_Casualty;
    protected Byte Casualty_Severity;
    protected Byte Pedestrian_Location;
    protected Byte Pedestrian_Movement;
    protected Byte Car_Passenger;
    protected Byte Bus_or_Coach_Passenger;
    protected Byte Pedestrian_Road_Maintenance_Worker;
    protected Byte Casualty_Type;
    protected Byte Casualty_Home_Area_Type;

    public S_Casualty_Record(S_RecordID i, String line) throws Exception {
        super(i);
        String[] s = line.split(",");
        initAcc_Index(s[0]);
        initVehicle_Reference(s[1]);
        initCasualty_Reference(s[2]);
        initCasualty_Class(s[3]);
        initSex_of_Casualty(s[4]);
        initAge_Band_of_Casualty(s[5]);
        initCasualty_Severity(s[6]);
        initPedestrian_Location(s[7]);
        initPedestrian_Movement(s[8]);
        initCar_Passenger(s[9]);
        initBus_or_Coach_Passenger(s[10]);
        initPedestrian_Road_Maintenance_Worker(s[11]);
        initCasualty_Type(s[12]);
        initCasualty_Home_Area_Type(s[13]);
    }

    @Override
    public S_RecordID getId() {
        return (S_RecordID) id;
    }

    protected final void initAcc_Index(String s) {
        if (!s.trim().isEmpty()) {
            Acc_Index = s;
        } else {
            Acc_Index = null;
        }
    }

    protected final void initVehicle_Reference(String s) {
        if (!s.trim().isEmpty()) {
            Vehicle_Reference = Short.parseShort(s);
        } else {
            Vehicle_Reference = null;
        }
    }

    protected final void initCasualty_Reference(String s) {
        if (!s.trim().isEmpty()) {
            Casualty_Reference = Short.parseShort(s);
        } else {
            Casualty_Reference = null;
        }
    }

    protected final void initCasualty_Class(String s) {
        if (!s.trim().isEmpty()) {
            Casualty_Class = Byte.parseByte(s);
        } else {
            Casualty_Class = null;
        }
    }

    protected final void initSex_of_Casualty(String s) {
        if (!s.trim().isEmpty()) {
            Sex_of_Casualty = Byte.parseByte(s);
        } else {
            Sex_of_Casualty = null;
        }
    }

    protected final void initAge_Band_of_Casualty(String s) {
        if (!s.trim().isEmpty()) {
            Age_Band_of_Casualty = Byte.parseByte(s);
        } else {
            Age_Band_of_Casualty = null;
        }
    }

    protected final void initCasualty_Severity(String s) {
        if (!s.trim().isEmpty()) {
            Casualty_Severity = Byte.parseByte(s);
        } else {
            Casualty_Severity = null;
        }
    }

    protected final void initPedestrian_Location(String s) {
        if (!s.trim().isEmpty()) {
            Pedestrian_Location = Byte.parseByte(s);
        } else {
            Pedestrian_Location = null;
        }
    }

    protected final void initPedestrian_Movement(String s) {
        if (!s.trim().isEmpty()) {
            Pedestrian_Movement = Byte.parseByte(s);
        } else {
            Pedestrian_Movement = null;
        }
    }

    protected final void initCar_Passenger(String s) {
        if (!s.trim().isEmpty()) {
            Car_Passenger = Byte.parseByte(s);
        } else {
            Car_Passenger = null;
        }
    }

    protected final void initBus_or_Coach_Passenger(String s) {
        if (!s.trim().isEmpty()) {
            Bus_or_Coach_Passenger = Byte.parseByte(s);
        } else {
            Bus_or_Coach_Passenger = null;
        }
    }

    protected final void initPedestrian_Road_Maintenance_Worker(String s) {
        if (!s.trim().isEmpty()) {
            Pedestrian_Road_Maintenance_Worker = Byte.parseByte(s);
        } else {
            Pedestrian_Road_Maintenance_Worker = null;
        }
    }

    protected final void initCasualty_Type(String s) {
        if (!s.trim().isEmpty()) {
            Casualty_Type = Byte.parseByte(s);
        } else {
            Casualty_Type = null;
        }
    }

    protected final void initCasualty_Home_Area_Type(String s) {
        if (!s.trim().isEmpty()) {
            Casualty_Home_Area_Type = Byte.parseByte(s);
        } else {
            Casualty_Home_Area_Type = null;
        }
    }

    public String getAcc_Index() {
        return Acc_Index;
    }

    public Short getVehicle_Reference() {
        return Vehicle_Reference;
    }

    public Short getCasualty_Reference() {
        return Casualty_Reference;
    }

    public Byte getCasualty_Class() {
        return Casualty_Class;
    }

    public Byte getSex_of_Casualty() {
        return Sex_of_Casualty;
    }

    public Byte getAge_Band_of_Casualty() {
        return Age_Band_of_Casualty;
    }

    public Byte getCasualty_Severity() {
        return Casualty_Severity;
    }

    public Byte getPedestrian_Location() {
        return Pedestrian_Location;
    }

    public Byte getPedestrian_Movement() {
        return Pedestrian_Movement;
    }

    public Byte getCar_Passenger() {
        return Car_Passenger;
    }

    public Byte getBus_or_Coach_Passenger() {
        return Bus_or_Coach_Passenger;
    }

    public Byte getPedestrian_Road_Maintenance_Worker() {
        return Pedestrian_Road_Maintenance_Worker;
    }

    public Byte getCasualty_Type() {
        return Casualty_Type;
    }

    public Byte getCasualty_Home_Area_Type() {
        return Casualty_Home_Area_Type;
    }
}