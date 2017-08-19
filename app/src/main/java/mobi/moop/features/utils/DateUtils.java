package mobi.moop.features.utils;

import org.joda.time.Duration;

import java.util.Date;

/**
 * Created by murilo aires on 11/08/2017.
 */

public class DateUtils {
    public static String getDifference(Date start, Date end) {
        Duration dur = new Duration(end.getTime(), start.getTime());
        if (dur.getStandardDays() == 0) {
            if (dur.getStandardHours() == 0) {
                if (dur.getStandardMinutes() < 5) {
                    return "Agora mesmo";
                } else {
                    return "Há " + dur.getStandardMinutes() + " minutos";
                }
            } else if (dur.getStandardHours() == 1) {
                return "Há 1 hora";
            } else {
                return "Há " + dur.getStandardHours() + " horas";
            }
        } else if (dur.getStandardDays() == 1) {
            return "Há 1 dia";
        } else {
            return "Há " + dur.getStandardDays() + " dias";
        }
    }
}
