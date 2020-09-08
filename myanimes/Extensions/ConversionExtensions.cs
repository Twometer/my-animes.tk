using Newtonsoft.Json.Linq;
using System;
using System.Linq;

namespace myanimes.Extensions
{
    public static class ConversionExtensions
    {
        public static T ToEnum<T>(this string str)
        {
            return (T)Enum.Parse(typeof(T), str, true);
        }

        public static T ValueOrDefault<T>(this JToken token, string key)
        {
            if (token == null || token.Type == JTokenType.Null)
                return default;

            var childToken = token[key];
            if (childToken == null || childToken.Type == JTokenType.Null)
                return default;

            return childToken.Value<T>();
        }
    }
}
