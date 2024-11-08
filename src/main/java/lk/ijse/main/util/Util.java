package lk.ijse.main.util;

import java.util.Base64;
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

    public static String createCropCode(){
        return UUID.randomUUID().toString();
    }

    public static String toBase64ProfilePic(byte [] cropImage){
        return Base64.getEncoder().encodeToString(cropImage);
    }
}
