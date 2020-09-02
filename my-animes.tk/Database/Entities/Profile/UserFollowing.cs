namespace my_animes.tk.Database.Entities.Profile
{
    public class UserFollowing
    {
        public int FollowerId { get; set; }

        public User Follower { get; set; }

        public int FollowingId { get; set; }

        public User Following { get; set; }
    }
}
