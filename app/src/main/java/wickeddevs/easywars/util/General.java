package wickeddevs.easywars.util;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by hicke_000 on 7/27/2016.
 */
public class General {

    public static void formatWarTime(long startTime, FormatWarTimeCallback callback) {
        long elapsedTime = Calendar.getInstance().getTimeInMillis() - startTime;

        long hours = elapsedTime / 3600000;
        if (hours <= 47) {
            long remainingHours = hours % 24;
            long remainingMinutes = hours % 60;
            long remainingSeconds = hours % 3600;
            callback.remainingTime(remainingHours, remainingMinutes, remainingSeconds);
        } else {
            callback.remainingTime(0,0,0);
        }
    }

    public static String formatDateTime(long dateTime) {
        String[] monthArray = DateFormatSymbols.getInstance(Locale.getDefault()).getMonths();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        long todayStart = todayCalendar.getTimeInMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        String hour = calendar.get(Calendar.HOUR) == 0 ? "12" : calendar.get(Calendar.HOUR) + "";
        String minute = (calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE) + "");
        String am_pm = (calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
        String time = hour + ":" + minute + " " + am_pm;

        if (todayStart > dateTime) {
            String month = monthArray[calendar.get(Calendar.MONTH)];
            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            return month + " " + day + ", " + time;
        }
        return time;
    }

    public interface FormatWarTimeCallback {
        void remainingTime(long hours, long minutes, long seconds);
    }

}
