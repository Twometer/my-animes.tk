using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using myanimes.Database;
using System.Security.Claims;
using System.Threading.Tasks;

namespace myanimes.Controllers
{
    [Authorize]
    [Route("~/profile/{action=Index}")]
    public class ProfileController : Controller
    {
        private readonly DatabaseContext database;

        public ProfileController(DatabaseContext database)
        {
            this.database = database;
        }

        public async Task<IActionResult> Index()
        {
            var username = HttpContext.User.FindFirstValue(ClaimTypes.Name);
            var user = await database.Users.SingleOrDefaultAsync(u => u.Name == username);

            return View();
        }
    }
}
