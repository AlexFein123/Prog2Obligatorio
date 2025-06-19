package um.edu.uy.tads;

public class NodoHash<K,T> {

    private K key;
    private boolean deleted;
    private T value;


    public NodoHash(K key, T value) {
        this.key = key;
        this.value = value;
        this.deleted = false;
    }

    public K getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equalToReturn = false;
        if (obj instanceof NodoHash) {
            equalToReturn = this.key.equals(((NodoHash) obj).getKey());
        }
        return equalToReturn;
    }
}
