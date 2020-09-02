using System.ComponentModel.DataAnnotations;

namespace myanimes.Configuration
{
    public class DatabaseOptions
    {
        [Required] public string ConnectionString { get; set; }
    }
}
