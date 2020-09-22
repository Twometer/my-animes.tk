using Microsoft.AspNetCore.Mvc;

namespace myanimes.Controllers
{
    [Route("~/profile/{action=Index}")]
    public class ProfileController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}
