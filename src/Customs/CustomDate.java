package Customs;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDate {
    public static String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
