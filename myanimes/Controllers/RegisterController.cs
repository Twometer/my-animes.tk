using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using myanimes.Database;
using myanimes.Database.Entities.Profile;
using myanimes.Models.Request;
using myanimes.Models.Response;
using myanimes.Services;
using System;
using System.Threading.Tasks;

namespace myanimes.Controllers
{
    [Route("~/register/{action=Index}")]
    public class RegisterController : Controller
    {
        private readonly DatabaseContext database;

        private readonly CryptoService crypto;

        public RegisterController(DatabaseContext database, CryptoService crypto)
        {
            this.database = database;
            this.crypto = crypto;
        }

        public IActionResult Index()
        {
            return View();
        }

        [HttpPost]
        [ActionName("Index")]
        public async Task<ActionResult<RegisterResponseModel>> IndexPost([FromBody] RegisterRequestModel request)
        {
            var trimmedUsername = request.Username.Trim();

            if (request.Password != request.PasswordConfirm)
            {
                return BadRequest(new RegisterResponseModel("Passwords do not match"));
            }

            if (request.Password.Length < 8)
            {
                return BadRequest(new RegisterResponseModel("Password must be at least 8 characters"));
            }

            if (await database.Users.AnyAsync(u => u.Name == trimmedUsername))
            {
                return BadRequest(new RegisterResponseModel("Username already taken"));
            }

            var salt = crypto.RandomSalt();
            var user = new User
            {
                Name = trimmedUsername,
                PasswordHash = crypto.Hash(request.Password, salt),
                PasswordSalt = salt,
                JoinDate = DateTime.Now
            };

            database.Users.Add(user);
            await database.SaveChangesAsync();

            return new RegisterResponseModel("ok");
        }



    }
}
