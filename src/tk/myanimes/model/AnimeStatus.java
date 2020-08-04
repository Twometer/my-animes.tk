package tk.myanimes.model;

public enum AnimeStatus {
    Current("current"),
    Finished("finished"),
    Tba("tba"),
    Unreleased("unreleased"),
    Upcoming("upcoming");

    private final String id;

    AnimeStatus(String id) {
        this.id = id;
    }

    public static AnimeStatus parse(String id) {
        for (var status : values())
            if (status.id.equals(id))
                return status;
        return null;
    }
}
