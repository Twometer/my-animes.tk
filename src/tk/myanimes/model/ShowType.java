package tk.myanimes.model;

public enum ShowType {
    ONA("ONA"),
    OVA("OVA"),
    TV("TV"),
    Movie("movie"),
    Music("music"),
    Special("special");

    private final String id;

    ShowType(String id) {
        this.id = id;
    }

    public static ShowType parse(String id) {
        for (var type : values())
            if (type.id.equals(id))
                return type;
        return null;
    }
}
