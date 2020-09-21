using Microsoft.AspNetCore.Mvc;

namespace myanimes.Controllers
{
    [Route("~/register/{action=Index}")]
    public class RegisterController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}
