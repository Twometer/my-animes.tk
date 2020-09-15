using Microsoft.AspNetCore.Mvc;
using myanimes.Models;
using myanimes.Services;
using System.Threading.Tasks;

namespace myanimes.Controllers
{
    [Route("~/search/{query}/{action=Index}")]
    public class SearchController : Controller
    {
        private readonly KitsuService kitsu;

        public SearchController(KitsuService kitsu)
        {
            this.kitsu = kitsu;
        }

        public async Task<IActionResult> Index(string query)
        {
            var results = await kitsu.Search(query);
            return View(new SearchViewModel(query, results));
        }
    }
}
