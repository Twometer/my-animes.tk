package tk.myanimes.model;

public enum WatchState {
    Watching("watching"),
    Watched("watched"),
    Paused("paused"),
    Cancelled("cancelled"),
    Queued("queued");

    private final String id;

    WatchState(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
