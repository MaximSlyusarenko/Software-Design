import com.google.common.base.Preconditions;

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
    }

    public Optional<V> get(K key) {
        Node<K, V> node = keyToNode.get(key);
        if (node == null) {
            return Optional.empty();
        } else {
            becomeHead(node);
            return Optional.of(node.value);
        }
    }

    public void set(K key, V value) {
        if (keyToNode.size() == 0 || (keyToNode.size() == capacity && capacity == 1)) {
            head = new Node<>(key, value);
        } else if (keyToNode.size() == capacity) {
            Node<K, V> node = keyToNode.get(key);
            if (node == null) {
                keyToNode.remove(tail.key);
                tail.prev.next = null;
                insert(key, value);
            } else {
                becomeHead(node);
            }
        } else {
            insert(key, value);
        }
    }

    private void becomeHead(Node<K, V> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        head.prev = node;
        head = node;
    }

    private void insert(K key, V value) {
        Node<K, V> node = keyToNode.get(key);
        if (node == null) {
            node = new Node<>(key, value, null, head);
            head = node;
            keyToNode.put(key, node);
        } else {
            becomeHead(node);
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
    }
}
