package cm.yowyob.auth.app.domain.util;


import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class SimpleCache<K, V> {


    private final int size;
    private final Map<K, V> eden;
    private final Map<K, V> longterm;

    public SimpleCache(int size) {
        this.size = size;
        this.eden = new ConcurrentHashMap<>(size);
        this.longterm = new WeakHashMap<>(size);
    }


    public V get(K k, Function<K, V> supplier) {

        V v = get(k);

        if (v == null){

            v = supplier.apply(k);

            if (v != null)
                put(k, v);
        }

        return v;
    }


    public V get(K k) {
        V v = this.eden.get(k);
        if (v == null) {
            synchronized(this.longterm) {
                v = this.longterm.get(k);
            }

            if (v != null) {
                this.eden.put(k, v);
            }
        }

        return v;
    }

    public void put(K k, V v) {
        if (this.eden.size() >= this.size) {
            synchronized(this.longterm) {
                this.longterm.putAll(this.eden);
            }

            this.eden.clear();
        }

        this.eden.put(k, v);
    }
}
