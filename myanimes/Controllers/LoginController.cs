﻿using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using myanimes.Database;
using myanimes.Models.Request;
using myanimes.Models.Response;
using myanimes.Services;
using System.Threading.Tasks;

namespace myanimes.Controllers
{
    [Route("~/login/{action=Index}")]
    public class LoginController : Controller
    {
        private readonly DatabaseContext database;

        private readonly CryptoService crypto;

        private readonly AuthenticationService auth;

        public LoginController(DatabaseContext database, CryptoService crypto, AuthenticationService auth)
        {
            this.database = database;
            this.crypto = crypto;
            this.auth = auth;
        }

        public IActionResult Index()
        {
            return View();
        }

        [HttpPost]
        [ActionName("Index")]
        public async Task<ActionResult<LoginResponseModel>> IndexPost([FromBody] LoginRequestModel request)
        {
            var user = await database.Users.SingleOrDefaultAsync(u => u.Name == request.Username);

            if (user == default || !crypto.HashMatches(request.Password, user.PasswordHash, user.PasswordSalt))
            {
                return BadRequest(new LoginResponseModel("Invalid username or password"));
            }

            await auth.SignInAsync(HttpContext, user);
         
            return new LoginResponseModel("ok");
        }

    }
}
