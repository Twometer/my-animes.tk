package tk.myanimes.text;

import tk.myanimes.model.AnimeInfo;
import tk.myanimes.model.AnimeStudioInfo;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

public class Formatter {

    public String formatPlurals(int val, String singular, String plural) {
        return val + " " + (val == 1 ? singular : plural);
    }

    public String formatPlurals(float val, String singular, String plural) {
        return val + " " + (val == 1 ? singular : plural);
    }

    public String formatPlurals(double val, String singular, String plural) {
        return val + " " + (val == 1 ? singular : plural);
    }

    public String formatStrings(Collection<String> list) {
        return String.join(", ", list);
    }

    public String formatStudios(Collection<AnimeStudioInfo> list) {
        return list.stream().map(AnimeStudioInfo::getName).collect(Collectors.joining(", "));
    }

    public String formatDateSystem(Instant instant) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC).format(instant);
    }

    public String formatDate(Instant instant) {
        var duration = Duration.between(instant, Instant.now()).toSeconds();

        if (duration < 0) {
            return "in the future";
        } else if (duration < 60) {
            return "a few seconds ago";
        } else if (duration < 60 * 15) {
            return "a few minutes ago";
        } else if (duration < 60 * 60) {
            return formatPlurals((int) Math.round(duration / 60.0), "minute", "minutes") + "ago";
        } else if (duration < 60 * 60 * 24) {
            return formatPlurals((int) Math.round(duration / 60.0 / 60.0), "hour", "hours") + "ago";
        } else if (duration < 60 * 60 * 24 * 30.5) {
            return formatPlurals((int) Math.round(duration / 60.0 / 60.0 / 24.0), "day", "days") + " ago";
        } else {
            return formatPlurals((int) Math.round(duration / 60.0 / 60.0 / 24.0 / 30.5), "month", "months") + " ago";
        }
    }

    public String formatAnimeLength(AnimeInfo animeInfo) {
        if (animeInfo.getEpisodeCount() == 1)
            return animeInfo.getTotalLength() + " minutes";
        else
            return animeInfo.getEpisodeCount() + " episodes";
    }

    public String formatDuration(int minutes) {
        if (minutes < 60) {
            return formatPlurals(minutes, "minute", "minutes");
        } else if (minutes < 60 * 24) {
            return formatPlurals(round(minutes / 60.0, 2), "hour", "hours");
        } else {
            return formatPlurals(round(minutes / 60.0 / 24.0, 2), "day", "days");
        }
    }

    public String formatScore(float score) {
        return round(score, 1) + "/5.0";
    }

    private double round(double d, int digits) {
        var exp = Math.pow(10, digits);
        return Math.round(d * exp) / exp;
    }

}
