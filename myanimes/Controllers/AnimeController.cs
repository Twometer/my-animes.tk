using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using myanimes.Services;

namespace myanimes.Controllers
{
    [Route("~/anime/{slug}/{action=Index}")]
    public class AnimeController : Controller
    {
        private readonly KitsuService kitsu;

        public AnimeController(KitsuService kitsu)
        {
            this.kitsu = kitsu;
        }

        public async Task<IActionResult> Index(string slug)
        {
            var anime = await kitsu.GetAnime(slug);
            return View(anime);
        }
    }
}
