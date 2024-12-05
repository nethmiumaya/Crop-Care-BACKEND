package lk.ijse.main.util;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class Util {
    public static String createVehicleCode() {
        return UUID.randomUUID().toString();
    }

    public static String createEquipmentCode() {
        return UUID.randomUUID().toString();
    }

    public static String createStaffId() {
        return UUID.randomUUID().toString();
    }

    public static String createCropCode() {
        return UUID.randomUUID().toString();
    }

    public static String createFieldCode() {
        return UUID.randomUUID().toString();
    }

    public static String createMonitoryLogCode() {
        return UUID.randomUUID().toString();
    }

    public static String toBase64ProfilePic(byte[] cropImage) {
        return Base64.getEncoder().encodeToString(cropImage);
    }

    public static Point parsePoint(String str) {
        if (str == null || !str.matches("\\d+,\\d+")) {
            throw new IllegalArgumentException("Invalid format for point: " + str);
        }

        // Split the string by comma
        String[] parts = str.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());

        // Return a new Point with parsed x and y values
        return new Point(x, y);
    }


    public static Date parseDate(String logDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(logDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + logDate, e);
        }
    }
}
