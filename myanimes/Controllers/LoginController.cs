using Microsoft.AspNetCore.Mvc;

namespace myanimes.Controllers
{
    [Route("~/login/{action=Index}")]
    public class LoginController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}
