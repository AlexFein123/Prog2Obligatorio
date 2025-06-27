package um.edu.uy.tads;

public class Pair <K, T>{
    private final K key;
    private final T value;

    public Pair(K key, T value){
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}

