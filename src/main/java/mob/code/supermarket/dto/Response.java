package mob.code.supermarket.dto;

public class Response<T> {
    private T data;
    private String error;

    public static <T> Response<T> of(T data) {
        return new Response<>(data, null);
    }

    public Response(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(null, message);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
