package userPack;

public class Pair<K, V,C> {
    private K key;
    private V value;
    private  C credit;

    public Pair(K key, V value, C credit) {
        this.key = key;
        this.value = value;
        this.credit = credit;
    }

    public K getKey() {
        return key;
    }

    public C getCredit() {
        return credit;
    }

    public V getValue() {
        return value;
    }
}
