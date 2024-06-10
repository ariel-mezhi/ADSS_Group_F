package ServiceLayer;

public class Response {
    public String msg;
    public boolean isError;

    public Response(boolean error, String message){
        isError = error;
        msg = message;
    }

    @Override
    public String toString() {
        return (isError? "error: " + msg : msg);
    }
}
