using Microsoft.AspNetCore.Mvc;
using myanimes.Models.Request;
using myanimes.Models.Response;
using myanimes.Models.View;
using myanimes.Services;
using System.Threading.Tasks;

namespace myanimes.Controllers
{
    [Route("~/search/{action=Index}")]
    public class SearchController : Controller
    {
        private readonly KitsuService kitsu;

        public SearchController(KitsuService kitsu)
        {
            this.kitsu = kitsu;
        }

        public async Task<IActionResult> Index([FromQuery] string q)
        {
            var results = await kitsu.Search(q);
            return View(new SearchViewModel(q, results));
        }

        [HttpPost]
        [ActionName("Index")]
        public async Task<ActionResult<SearchResponseModel>> IndexApi([FromBody] SearchRequestModel request)
        {
            var results = await kitsu.Search(request.Query);
            return new SearchResponseModel(results);
        }
    }
}
