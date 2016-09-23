import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Maxim Slyusarenko
 * @since 23.09.16
 */
public class LRUCache<K, V> {

    private final int capacity;
    private Map<K, Node<K, V>> keyToNode;
    private Node<K, V> head;
    private Node<K, V> tail;

    public LRUCache(int capacity) {
        Preconditions.checkArgument(capacity > 0, "Capacity must be greater then zero");
        this.capacity = capacity;
        this.keyToNode = new HashMap<>();
    }

    public Optional<V> get(K key) {
        assert keyToNode.size() <= capacity : "Map size is greater than capacity";
        Node<K, V> node = keyToNode.get(key);
        Optional<V> result;
        if (node == null) {
            result = Optional.empty();
        } else {
            becomeHead(node);
            result = Optional.of(node.value);
        }
        assert key == head.key : "Key didn't become head after get";
        return result;
    }

    public void set(K key, V value) {
        assert keyToNode.size() <= capacity : "Map size is greater than capacity";
        Node<K, V> headBefore = new Node<K, V>(head);
        Node<K, V> tailBefore = new Node<K, V>(tail);
        boolean inserted = false;
        if (keyToNode.size() == 0 || (keyToNode.size() == capacity && capacity == 1)) {
            head = new Node<>(key, value);
        } else if (keyToNode.size() == capacity) {
            Node<K, V> node = keyToNode.get(key);
            if (node == null) {
                keyToNode.remove(tail.key);
                tail.prev.next = null;
                tail = tail.prev;
                inserted = insert(key, value);
            } else {
                becomeHead(node);
            }
        } else {
            inserted = insert(key, value);
        }
        assert head.key.equals(key) : "Head key not equals to inserted key";
        assert head.value.equals(value) : "Head value not equals to inserted value";
        if (capacity == 1) {
            assert tail == null : "Capacity is one, tail is not null";
        }
        if (inserted) {
            assert tailBefore.key != tail.key && tailBefore.value != tail.value : "The map was full, but tail hasn't changed";
        }
        if (headBefore.key == key) {
            assert head == headBefore : "Head has changed, but mustn't";
        } else {
            assert head.key != headBefore.key : "Head hasn't change but must";
        }
    }

    private void becomeHead(Node<K, V> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node == tail) {
            tail = tail.prev;
        }
        head.prev = node;
        head = node;
    }

    private boolean insert(K key, V value) {
        Node<K, V> node = keyToNode.get(key);
        if (node == null) {
            node = new Node<>(key, value, null, head);
            if (tail == null) {
                tail = head;
            }
            head = node;
            keyToNode.put(key, node);
            return true;
        } else {
            becomeHead(node);
            return false;
        }
    }

    private class Node<NK, NV> {
        private NK key;
        private NV value;
        private Node<NK, NV> prev;
        private Node<NK, NV> next;

        private Node(NK key, NV value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }

        private Node(NK key, NV value, Node<NK, NV> prev, Node<NK, NV> next) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        private Node(Node<NK, NV> node) {
            this.key = node.key;
            this.value = node.value;
            this.prev = node.prev;
            this.next = node.next;
        }
    }
}
