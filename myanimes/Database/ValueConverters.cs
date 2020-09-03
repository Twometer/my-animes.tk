using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using System.Collections.Generic;

namespace myanimes.Database
{
    public static class ValueConverters
    {
        public static ValueConverter SplitStringConverter { get; } = new ValueConverter<IEnumerable<string>, string>(
            v => string.Join(";", v), v => v.Split(new[] { ';' })
        );
    }
}
