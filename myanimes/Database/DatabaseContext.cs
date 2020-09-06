using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using myanimes.Configuration;
using myanimes.Database.Entities.Animes;
using myanimes.Database.Entities.Mappings;
using myanimes.Database.Entities.Profile;

namespace myanimes.Database
{
    public class DatabaseContext : DbContext
    {
        private readonly IOptions<DatabaseOptions> options;

        public DbSet<User> Users { get; set; }

        public DbSet<FollowerMapping> FollowerMappings { get; set; }

        public DbSet<AnimeListItem> AnimeListItems { get; set; }

        public DbSet<AnimeDbo> Animes { get; set; }

        public DbSet<GenreMapping> GenreMappings { get; set; }

        public DbSet<StudioMapping> StudioMappings { get; set; }

        public DbSet<Title> Titles { get; set; }

        public DbSet<Genre> Genres { get; set; }

        public DbSet<Studio> Studios { get; set; }

        public DbSet<Episode> Episodes { get; set; }

        public DbSet<Character> Characters { get; set; }

        public DbSet<StreamingLink> StreamingLinks { get; set; }


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
            var user = modelBuilder.Entity<User>();
            user.HasKey(u => u.Id);
            user.HasIndex(u => u.Name).IsUnique();
            user.Property(u => u.PasswordHash).IsRequired();
            user.Property(u => u.JoinDate).IsRequired();
            user.HasMany(u => u.AnimeList).WithOne(i => i.User).HasForeignKey(i => i.UserId);

            var followerMapping = modelBuilder.Entity<FollowerMapping>();
            followerMapping.HasKey(m => new { m.FollowerId, m.FollowingId });
            followerMapping.HasOne(m => m.Follower).WithMany(u => u.Following).HasForeignKey(m => m.FollowerId);
            followerMapping.HasOne(m => m.Following).WithMany(u => u.Followers).HasForeignKey(m => m.FollowingId);

            var animeListItem = modelBuilder.Entity<AnimeListItem>();
            animeListItem.HasKey(i => i.Id);
            animeListItem.HasOne(i => i.User).WithMany(u => u.AnimeList).HasForeignKey(i => i.UserId);
            animeListItem.Property(i => i.WatchState).IsRequired();
            animeListItem.Property(i => i.DateAdded).IsRequired();
            animeListItem.Property(i => i.DateModified).IsRequired();
            animeListItem.Property(i => i.DateWatched).IsRequired();

            var anime = modelBuilder.Entity<AnimeDbo>();
            anime.HasKey(a => a.Id);
            anime.HasIndex(a => a.Slug).IsUnique();
            anime.Property(a => a.CanonicalTitle).IsRequired();
            anime.HasMany(a => a.Titles).WithOne(t => t.Anime).HasForeignKey(t => t.AnimeId);
            anime.Property(a => a.Status).IsRequired();
            anime.Property(a => a.Type).IsRequired();
            anime.HasMany(a => a.Episodes).WithOne(e => e.Anime).HasForeignKey(e => e.AnimeId);
            anime.HasMany(a => a.Characters).WithOne(c => c.Anime).HasForeignKey(c => c.AnimeId);
            anime.HasMany(a => a.StreamingLinks).WithOne(l => l.Anime).HasForeignKey(l => l.AnimeId);
            anime.Property(a => a.EntryExpirationDate).IsRequired();

            var genreMapping = modelBuilder.Entity<GenreMapping>();
            genreMapping.HasKey(m => new { m.AnimeId, m.GenreId });
            genreMapping.HasOne(m => m.Anime).WithMany(a => a.Genres).HasForeignKey(m => m.AnimeId);
            genreMapping.HasOne(m => m.Genre).WithMany(g => g.GenreMappings).HasForeignKey(m => m.GenreId);

            var studioMapping = modelBuilder.Entity<StudioMapping>();
            studioMapping.HasKey(m => new { m.AnimeId, m.StudioId });
            studioMapping.HasOne(m => m.Anime).WithMany(a => a.Studios).HasForeignKey(m => m.AnimeId);
            studioMapping.HasOne(m => m.Studio).WithMany(s => s.StudioMappings).HasForeignKey(m => m.StudioId);

            var title = modelBuilder.Entity<Title>();
            title.HasKey(t => t.Id);
            title.Property(t => t.Language).IsRequired();
            title.Property(t => t.Text).IsRequired();

            var genre = modelBuilder.Entity<Genre>();
            genre.HasKey(g => g.Id);
            genre.HasIndex(g => g.Slug).IsUnique();
            genre.Property(g => g.Name).IsRequired();

            var studio = modelBuilder.Entity<Studio>();
            studio.HasKey(s => s.Id);
            studio.HasIndex(s => s.Slug).IsUnique();
            studio.Property(s => s.Name).IsRequired();

            var episode = modelBuilder.Entity<Episode>();
            episode.HasKey(e => e.Id);

            var character = modelBuilder.Entity<Character>();
            character.HasKey(c => c.Id);
            character.HasIndex(c => c.Slug).IsUnique();
            character.Property(c => c.Name).IsRequired();

            var streamingLink = modelBuilder.Entity<StreamingLink>();
            streamingLink.HasKey(l => l.Id);
            streamingLink.Property(l => l.Url).IsRequired();
            streamingLink.Property(l => l.SubbedLanguages).HasConversion(ValueConverters.SplitStringConverter);
            streamingLink.Property(l => l.DubbedLanguages).HasConversion(ValueConverters.SplitStringConverter);
        }

    }
}
