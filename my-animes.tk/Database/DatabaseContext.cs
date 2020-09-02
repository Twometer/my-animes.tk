using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using my_animes.tk.Configuration;
using my_animes.tk.Database.Entities.Animes;
using my_animes.tk.Database.Entities.Profile;

namespace my_animes.tk.Database
{
    public class DatabaseContext : DbContext
    {
        private readonly IOptions<DatabaseOptions> options;

        public DbSet<User> Users { get; set; }

        public DbSet<UserFollowing> Followings { get; set; }

        public DbSet<AnimeListItem> AnimeListItems { get; set; }

        public DbSet<Anime> Animes { get; set; }

        public DbSet<AnimeTitle> AnimeTitles { get; set; }

        public DbSet<AnimeGenre> AnimeGenres { get; set; }

        public DbSet<AnimeStudio> AnimeStudios { get; set; }

        public DbSet<AnimeEpisode> AnimeEpisodes { get; set; }

        public DbSet<AnimeCharacter> AnimeCharacters { get; set; }

        public DbSet<AnimeStreamingLink> AnimeStreamingLinks { get; set; }


        public DatabaseContext(IOptions<DatabaseOptions> options)
        {
            this.options = options;
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            base.OnConfiguring(optionsBuilder);
            optionsBuilder.UseMySql(options.Value.ConnectionString);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // TODO fully build model!
            var user = modelBuilder.Entity<User>();
            user.HasKey(u => u.Id);
            user.Property(u => u.Name).IsRequired();
            user.Property(u => u.PasswordHash).IsRequired();

            var anime = modelBuilder.Entity<Anime>();
            anime.HasKey(a => a.Id);
            anime.HasIndex(a => a.Slug).IsUnique();
            anime.Property(a => a.CanonicalTitle).IsRequired();


            var title = modelBuilder.Entity<AnimeTitle>();
            title.HasKey(t => t.Id);
            title.HasOne(t => t.Anime).WithMany(a => a.Titles).HasForeignKey(t => t.AnimeId);
        }

    }
}
