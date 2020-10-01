using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using myanimes.Services;
using System.Threading.Tasks;

namespace myanimes.Controllers
{
    [Authorize]
    [Route("~/logout/{action=Index}")]
    public class LogoutController : Controller
    {
        private AuthenticationService auth;

        public LogoutController(AuthenticationService auth)
        {
            this.auth = auth;
        }

        public async Task<IActionResult> Index()
        {
            await auth.SignOutAsync(HttpContext);
            return RedirectToPage("login");
        }
    }
}
