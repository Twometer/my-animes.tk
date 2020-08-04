package tk.myanimes.model;

public class AnimeStudioInfo {

    private long id;

    private String name;

    private AnimeStudioNamespace location;

    public AnimeStudioInfo(long id, String name, AnimeStudioNamespace location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public AnimeStudioInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimeStudioNamespace getLocation() {
        return location;
    }

    public void setLocation(AnimeStudioNamespace location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "AnimeStudioInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
