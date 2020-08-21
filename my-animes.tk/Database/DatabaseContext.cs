using Microsoft.EntityFrameworkCore;
using my_animes.tk.Database.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace my_animes.tk.Database
{
    public class DatabaseContext : DbContext
    {

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            base.OnConfiguring(optionsBuilder);

            optionsBuilder.UseMySql(""); // TODO: configurable connection string
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

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
