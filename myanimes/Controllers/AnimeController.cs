using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using myanimes.Models.View;
using myanimes.Services;

namespace myanimes.Controllers
{
    [Route("~/anime/{slug}/{action=Index}")]
    public class AnimeController : Controller
    {
        private readonly CacheService cache;

        public AnimeController(CacheService cache)
        {
            this.cache = cache;
        }

        public async Task<IActionResult> Index(string slug)
        {
            var anime = await cache.GetAnime(slug);
            return View(new AnimeViewModel(anime));
        }
    }
}
