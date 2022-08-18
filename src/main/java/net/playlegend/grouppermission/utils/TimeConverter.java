package net.playlegend.grouppermission.utils;

/**
 * This class represents a time converter
 * It converts input like 2h3m1s to milliseconds
 */
public class TimeConverter {
    
    private static TimeConverter instance;
    
    public static TimeConverter getInstance() {
        if (instance == null) {
            instance = new TimeConverter();
        }
        return instance;
    }

    /**
     * Converts a input time to milliseconds
     * Valid inputs:
     * d = days
     * h = hours
     * m = minutes
     * s = seconds
     */
    public long convertTime(String input) {
        long days = getIntBySeparator(input, "d");
        long hours = getIntBySeparator(input, "h");
        long minutes = getIntBySeparator(input, "m");
        long seconds = getIntBySeparator(input, "s");
        System.out.println("d:" + days + " h:" + hours + " m:" + minutes + " s:" + seconds);
        return seconds * 1000L + minutes * 10000L * 60L + hours * 1000L * 60L * 60L + days * 1000L * 60L * 60L * 24L;
    }

    private int getIntBySeparator(String input, String separator) {
        if (!input.contains(separator)) {
            return 0;
        }
        String lastNumberString = "";
        for (int i = 0; i < input.length(); i++) {
            try {
                int number = Integer.parseInt(input.toCharArray()[i] + "");
                lastNumberString += number;
            } catch (NumberFormatException e) {
                if ((input.toCharArray()[i] + "").equals(separator)) {
                    return Integer.parseInt(lastNumberString);
                }
                lastNumberString = "";
            }
        }
        return 0;
    }

}
