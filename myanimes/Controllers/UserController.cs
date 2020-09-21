using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using myanimes.Database;
using myanimes.Models.View;
using System.Linq;
using System.Threading.Tasks;

namespace myanimes.Controllers
{
    [Route("~/user/{username}/{action=Index}")]
    public class UserController : Controller
    {
        private readonly DatabaseContext database;

        public UserController(DatabaseContext database)
        {
            this.database = database;
        }

        public async Task<IActionResult> Index(string username)
        {
            var user = await database.Users.Where(u => u.Name.Equals(username, System.StringComparison.OrdinalIgnoreCase)).SingleOrDefaultAsync();

            if (user == default)
                return NotFound();

            return View(new UserViewModel(user));
        }

    }
}
