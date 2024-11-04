package lk.ijse.main.util;

import java.util.UUID;

public class Util {
    public  static String createVehicleCode(){
        return UUID.randomUUID().toString();
    }
    public  static String createEquipmentCode(){
        return UUID.randomUUID().toString();
    }

    public  static String createStaffId(){
        return UUID.randomUUID().toString();
    }
}
