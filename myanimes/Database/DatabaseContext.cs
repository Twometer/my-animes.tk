using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using myanimes.Configuration;
using myanimes.Database.Entities.Animes;
using myanimes.Database.Entities.Profile;

namespace myanimes.Database
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
            user.HasAlternateKey(u => u.Name);
            user.Property(u => u.PasswordHash).IsRequired();

            var userFollowing = modelBuilder.Entity<UserFollowing>();
            userFollowing.HasKey(f => f.Id);

            var animeListItem = modelBuilder.Entity<AnimeListItem>();
            animeListItem.HasOne(i => i.User).WithMany(u => u.AnimeList).HasForeignKey(i => i.UserId);
            // animeListItem.HasOne(i => i.Anime);

            var anime = modelBuilder.Entity<Anime>();
            anime.HasKey(a => a.Id);
            anime.HasAlternateKey(a => a.Slug);
            anime.Property(a => a.CanonicalTitle).IsRequired();
            anime.HasMany(a => a.Titles).WithOne(t => t.Anime);
            anime.HasMany(a => a.Genres); // incomplete??

            var title = modelBuilder.Entity<AnimeTitle>();
            title.HasKey(t => t.Id);
            title.HasOne(t => t.Anime).WithMany(a => a.Titles).HasForeignKey(t => t.AnimeId);
        }

    }
}
