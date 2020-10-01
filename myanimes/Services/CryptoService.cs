using System.Security.Cryptography;
using System.Text;

namespace myanimes.Services
{
    public class CryptoService
    {
        private const int HashIterations = 1500;

        private const int SaltLength = 16;

        private const int KeyLength = 32;

        public byte[] RandomSalt()
        {
            var salt = new byte[SaltLength];

            using var random = new RNGCryptoServiceProvider();
            random.GetBytes(salt);

            return salt;
        }

        public byte[] Hash(string key, byte[] salt)
        {
            using var derive = new Rfc2898DeriveBytes(key, salt, HashIterations);
            return derive.GetBytes(KeyLength);
        }

        public bool HashMatches(string input, byte[] key, byte[] salt)
        {
            var inputHash = Hash(input, salt);

            for (int i = 0; i < KeyLength; i++)
                if (inputHash[i] != key[i])
                    return false;

            return true;            
        }

    }
}
