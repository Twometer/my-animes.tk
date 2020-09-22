namespace myanimes.Models.Response
{
    public class LoginResponseModel
    {
        public string Message { get; set; }

        public LoginResponseModel(string message)
        {
            Message = message;
        }
    }
}
