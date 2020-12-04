package gng2101_2020.group12.multireminder;

public class Helpers {

    public static String formatDate(int hours, int minutes) {
        String minutesString = (minutes < 9) ? ("0" + minutes) : (minutes + "");
        return hours + ":" + minutesString;
    }

    public static String formatDuration(long duration) {
        long seconds = (duration / 1000) % 60;
        long minutes = (duration / 60000) % (60);
        long hours = duration / (60 * 60000);
        String secondsString = (seconds < 9) ? ("0" + seconds) : (seconds + "");
        String minutesString = (minutes < 9) ? ("0" + minutes) : (minutes + "");
        return ((hours > 0) ? hours + ":" : "") + minutesString + ":" + secondsString;
    }

    public static long getMillisDuration(String duration) {
        long durationResult = 0;

        String[] parts = duration.split(":");
        for (int i = 0; i < parts.length; i++) {
            try {
                durationResult += Long.parseLong(parts[parts.length - 1 - i]) * 1000 * Math.pow(60, i);
            } catch (NumberFormatException e) {}
        }

        return durationResult;
    }
}
