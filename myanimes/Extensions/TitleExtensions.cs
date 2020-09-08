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

        private static string GetTitle(this AnimeBase anime, string language)
        {
            foreach (var title in anime.Titles)
                if (title.Language == language)
                    return title.Text;
            return null;
        }

        public static string GetEnglishTitle(this AnimeBase anime)
        {
            foreach (var title in anime.Titles)
                if (title.Language == GenericEnglish || title.Language == EnUs)
                    return title.Text;
            return anime.CanonicalTitle;
        }

        public static string GetJapaneseTitle(this AnimeBase anime)
        {
            var jpTitle = anime.GetTitle(JaJp);
            if (jpTitle != null)
                return jpTitle;

            foreach (var title in anime.Titles)
                if (title.Language.EndsWith(GenericJapanese))
                    return title.Text;
            return anime.CanonicalTitle;
        }

        public static string GetAlternateTitle(this AnimeBase anime)
        {
            if (anime.CanonicalTitle != anime.GetEnglishTitle())
                return anime.CanonicalTitle;

            var enJpTitle = anime.GetTitle(EnJp);
            if (enJpTitle != null)
                return enJpTitle;

            return anime.Titles.First().Text;
        }

    }
}
