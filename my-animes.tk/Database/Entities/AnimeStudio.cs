using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace my_animes.tk.Database.Entities
{
    public class AnimeStudio
    {
        public int Id { get; set; }

        public int Key { get; set; }

        public KeyNamespace KeyNamespace { get; set; }

        public string Name { get; set; }
    }

    public enum KeyNamespace
    {
        Studio,
        Company
    }
}
