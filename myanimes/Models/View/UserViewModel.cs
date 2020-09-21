using myanimes.Database.Entities.Profile;

namespace myanimes.Models.View
{
    public class UserViewModel
    {
        public User User { get; }

        public UserViewModel(User user)
        {
            User = user;
        }
    }
}
