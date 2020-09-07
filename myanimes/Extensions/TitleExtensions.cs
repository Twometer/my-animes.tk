using myanimes.Models;
using System.Linq;

namespace myanimes.Extensions
{
    public static class TitleExtensions
    {
        private const string GenericEnglish = "en";
        private const string GenericJapanese = "jp";
        private const string EnUs = "en_us";
        private const string EnJp = "en_jp";
        private const string JaJp = "ja_jp";

        public static string GetEnglishTitle(this AnimeBase anime)
        {
            foreach (var title in anime.Titles)
                if (title.Language == GenericEnglish || title.Language == EnUs)
                    return title.Text;
            return anime.CanonicalTitle;
        }

        public static string GetJapaneseTitle(this AnimeBase anime)
        {
            foreach (var title in anime.Titles)
                if (title.Language == JaJp || title.Language.EndsWith(GenericJapanese))
                    return title.Text;
            return anime.CanonicalTitle;
        }

        public static string GetAlternateTitle(this AnimeBase anime)
        {
            if (anime.CanonicalTitle != anime.GetEnglishTitle())
                return anime.CanonicalTitle;

            foreach (var title in anime.Titles)
                if (title.Language == EnJp)
                    return title.Text;

            return anime.Titles.First().Text;
        }

    }
}
