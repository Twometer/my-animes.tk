using System.ComponentModel.DataAnnotations;

namespace my_animes.tk.Configuration
{
    public class DatabaseOptions
    {
        [Required] public string ConnectionString { get; set; }
    }
}
