package br.com.character;


public class JsonValueName<T> {

    private T name;

    public JsonValueName() {
    }

    public JsonValueName(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }
}
