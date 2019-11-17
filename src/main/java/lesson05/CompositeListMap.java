package lesson05;

import java.util.*;


/**
 * Коллекция для картотеки. Хранит ссылки на объекты в структурах List и Map для
 * быстрого поиска элементов картотеки по их id (за O(1)) и
 * по ключу элемента (в лучшем случае за O(1), в худшем - O(log(n)))
 * @param <V> значение
 * @param <K> ключ
 * @author TVYakimov
 */

public class CompositeListMap<K,V> {

    private final List<V> list = new ArrayList<>();
    private final Map<K, Set<V>> map = new HashMap<>();


    public int add(K key, V value) throws Exception {
        if (list.contains(value)) {
            throw new Exception("It exists");
        }
        list.add(value);
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            Set<V> set = new HashSet<>();
            set.add(value);
            map.put(key, set);
        }
        return list.size();
    }

    public V get(int index) {
        return list.get(index);
    }

    public Set<V> get(K key) {
        return map.get(key);
    }

    public List<V> getList() {
        return list;
    }


}
