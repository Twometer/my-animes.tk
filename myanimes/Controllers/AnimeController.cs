using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using myanimes.Database;
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
            return View(anime);
        }
    }
}
