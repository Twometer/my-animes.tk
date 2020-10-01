using myanimes.Database.Entities.Mappings;
using System.Collections.Generic;

namespace myanimes.Database.Entities.Animes
{
    public class Studio
    {
        public int Id { get; set; }

        public string Slug { get; set; }

        public string Name { get; set; }

        public IEnumerable<StudioMapping> StudioMappings { get; set; }
    }

}
