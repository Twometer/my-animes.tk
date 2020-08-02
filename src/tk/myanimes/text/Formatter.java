package tk.myanimes.text;

import java.time.Duration;
import java.time.Instant;

public class Formatter {

    public String formatDate(Instant instant) {
        var duration = Duration.between(instant, Instant.now()).toSeconds();

        if (duration < 60) {
            return "a few seconds ago";
        } else if (duration < 60 * 15) {
            return "a few minutes ago";
        } else if (duration < 60 * 60) {
            return Math.round(duration / 60.0) + " minutes ago";
        } else if (duration < 60 * 60 * 24) {
            return Math.round(duration / 60.0 / 60.0) + " hours ago";
        } else if (duration < 60 * 60 * 24 * 30.5) {
            return Math.round(duration / 60.0 / 60.0 / 24.0) + " days ago";
        } else {
            return Math.round(duration / 60.0 / 60.0 / 24.0 / 30.5) + " months ago";
        }
    }

    public String formatDuration(int minutes) {
        if (minutes < 60) {
            return minutes + " minutes";
        } else if (minutes < 60 * 24) {
            return round(minutes / 60.0, 2) + " hours";
        } else {
            return round(minutes / 60.0 / 24.0, 2) + " days";
        }
    }

    public String formatScore(float score) {
        return String.valueOf(round(score, 1));
    }

    private double round(double d, int digits) {
        var exp = Math.pow(10, digits);
        return Math.round(d * exp) / exp;
    }

}
