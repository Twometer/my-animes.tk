package tk.myanimes.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class DbUser {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String location;

    @DatabaseField
    private String biography;

    @DatabaseField
    private String profilePicture;

    @DatabaseField
    private long favoriteAnimeId;

    @DatabaseField
    private byte[] passwordHash;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public long getFavoriteAnimeId() {
        return favoriteAnimeId;
    }

    public void setFavoriteAnimeId(long favoriteAnimeId) {
        this.favoriteAnimeId = favoriteAnimeId;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }
}
