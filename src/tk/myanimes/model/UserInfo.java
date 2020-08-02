package tk.myanimes.model;

public class UserInfo {

    private long id;

    private String name;

    private String location;

    private String biography;

    private String profilePicture;

    private AnimeInfo favoriteAnime;

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

    public AnimeInfo getFavoriteAnime() {
        return favoriteAnime;
    }

    public void setFavoriteAnime(AnimeInfo favoriteAnime) {
        this.favoriteAnime = favoriteAnime;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

}
