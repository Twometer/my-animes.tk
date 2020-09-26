using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using myanimes.Configuration;
using myanimes.Database;
using myanimes.Services;
using System;

namespace myanimes
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllersWithViews();

            services.AddOptions<DatabaseOptions>().Bind(Configuration.GetSection("Database"));

            services.AddRouting();
            services.AddAuthentication(AuthenticationService.Scheme)
                .AddCookie(AuthenticationService.Scheme, options =>
            {
                options.LoginPath = "/login";
                options.AccessDeniedPath = "/login";
                options.ExpireTimeSpan = TimeSpan.FromDays(30);
            });

            services.AddDbContext<DatabaseContext>();
            services.AddSingleton<KitsuService>();
            services.AddSingleton<CryptoService>();
            services.AddSingleton<AuthenticationService>();
            services.AddScoped<CacheService>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
            }
            app.UseStaticFiles();
            app.UseRouting();
            app.UseAuthentication();
            app.UseAuthorization();
            app.UseEndpoints(endpoints => endpoints.MapControllers());

            using var scope = app.ApplicationServices.CreateScope();
            using var context = scope.ServiceProvider.GetRequiredService<DatabaseContext>();
            //context.Database.EnsureDeleted();
            context.Database.EnsureCreated();
        }
    }
}
