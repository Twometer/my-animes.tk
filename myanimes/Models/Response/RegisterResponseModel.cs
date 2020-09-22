namespace myanimes.Models.Response
{
    public class RegisterResponseModel
    {
        public RegisterResponseModel(string message)
        {
            Message = message;
        }

        public string Message { get; set; }

    }
}
