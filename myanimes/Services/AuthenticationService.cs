using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Http;
using myanimes.Database.Entities.Profile;
using System.Security.Claims;
using System.Threading.Tasks;

namespace myanimes.Services
{
    public class AuthenticationService
    {
        public const string Scheme = "X-AnimeAuth";

        public Task SignOutAsync(HttpContext context)
        {
            return context.SignOutAsync(Scheme);
        }

        public async Task SignInAsync(HttpContext httpContext, User user)
        {
            var identity = new ClaimsIdentity(Scheme, ClaimTypes.Name, ClaimTypes.Role);
            identity.AddClaim(new Claim(ClaimTypes.Name, user.Name));
            identity.AddClaim(new Claim(ClaimTypes.Role, "User"));

            var principal = new ClaimsPrincipal(identity);

            var properties = new AuthenticationProperties
            {
                IsPersistent = true
            };

            await httpContext.SignInAsync(Scheme, principal, properties);
        }

    }
}
