package lk.ijse.cmjd109.lostAndFoundSystem.util;

import java.time.LocalDate;
import java.util.UUID;

public class UtilityData {
    public static String generateItemId() {
        return "I-" + UUID.randomUUID();
    }

    public static String generateUserId() {
        return "U-" + UUID.randomUUID();
    }

    public static String generateRequestId() {
        return "R-" + UUID.randomUUID();
    }


    public static LocalDate generateTodayDate() {
        return LocalDate.now();
    }

}
