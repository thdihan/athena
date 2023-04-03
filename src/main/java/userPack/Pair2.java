package userPack;

public class Pair2<K, V> {
    private K key;
    private V value;


    public Pair2(K key, V value) {
        this.key = key;
        this.value = value;

    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
