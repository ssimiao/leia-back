package br.com.user;

public class Request<T> {
    private T request;

    public Request(T request) {
        this.request = request;
    }

    public Request() {
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }
}
