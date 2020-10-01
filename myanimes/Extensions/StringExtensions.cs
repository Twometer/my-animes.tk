using System;
using System.Net;
using System.Text.RegularExpressions;

namespace myanimes.Extensions
{
    public static class StringExtensions
    {
        public static string StripHtml(this string str)
        {
            return WebUtility.HtmlDecode(Regex.Replace(str, "<.*?>", string.Empty)).Trim();
        }
    }
}
