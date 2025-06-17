package um.edu.uy.tads;

import um.edu.uy.interfaces.MyTree;

public class Tree<K, T> implements MyTree<K, T> {

    private Node<K, T> root;

    @Override
    public void insert(K key, T data, K parentKey) {
        if (root == null) {
            root = new Node<>(key, data);
        } else {
            insertRecursive(root, key, data, parentKey);
        }
    }

    private boolean insertRecursive(Node<K, T> current, K key, T data, K parentKey) {
        if (current == null) return false;

        if (current.key.equals(parentKey)) {
            if (current.leftChild == null) {
                current.leftChild = new Node<>(key, data);
                return true;
            } else if (current.rightChild == null) {
                current.rightChild = new Node<>(key, data);
                return true;
            } else {
                System.out.println("El nodo padre ya tiene dos hijos.");
                return false;
            }
        }

        return insertRecursive(current.leftChild, key, data, parentKey)
                || insertRecursive(current.rightChild, key, data, parentKey);
    }

    @Override
    public T find(K key) {
        return findRecursive(root, key);
    }

    private T findRecursive(Node<K, T> current, K key) {
        if (current == null) return null;
        if (current.key.equals(key)) return current.data;

        T leftResult = findRecursive(current.leftChild, key);
        if (leftResult != null) return leftResult;

        return findRecursive(current.rightChild, key);
    }

    @Override
    public void delete(K key) {
        root = deleteRecursive(root, key);
    }

    private Node<K, T> deleteRecursive(Node<K, T> current, K key) {
        if (current == null) return null;

        if (current.key.equals(key)) {
            if (current.leftChild == null && current.rightChild == null) {
                return null;
            }
            if (current.leftChild == null) return current.rightChild;
            if (current.rightChild == null) return current.leftChild;

            Node<K, T> successor = getLeftmost(current.rightChild);
            current.key = successor.key;
            current.data = successor.data;
            current.rightChild = deleteRecursive(current.rightChild, successor.key);
            return current;
        }

        current.leftChild = deleteRecursive(current.leftChild, key);
        current.rightChild = deleteRecursive(current.rightChild, key);
        return current;
    }

    private Node<K, T> getLeftmost(Node<K, T> node) {
        while (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }

    public int size() {
        return sizeRecursive(root);
    }

    private int sizeRecursive(Node<K, T> current) {
        if (current == null) return 0;
        return 1 + sizeRecursive(current.leftChild) + sizeRecursive(current.rightChild);
    }

    public int countLeaf() {
        return countLeafRecursive(root);
    }

    private int countLeafRecursive(Node<K, T> current) {
        if (current == null) return 0;
        if (current.leftChild == null && current.rightChild == null) return 1;
        return countLeafRecursive(current.leftChild) + countLeafRecursive(current.rightChild);
    }

    public int countCompleteElements() {
        return countCompleteElementsRecursive(root);
    }

    private int countCompleteElementsRecursive(Node<K, T> current) {
        if (current == null) return 0;
        int count = 0;
        if (current.leftChild != null && current.rightChild != null) {
            count = 1;
        }
        return count + countCompleteElementsRecursive(current.leftChild)
                + countCompleteElementsRecursive(current.rightChild);
    }

    public ListaCircularDoble inOrder() {
        ListaCircularDoble result = new ListaCircularDoble();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(Node<K, T> current, ListaCircularDoble result) {
        if (current != null) {
            inOrderRecursive(current.leftChild, result);
            result.addLast(current.key);
            inOrderRecursive(current.rightChild, result);
        }
    }

    public ListaCircularDoble preOrder() {
        ListaCircularDoble result = new ListaCircularDoble();
        preOrderRecursive(root, result);
        return result;
    }

    private void preOrderRecursive(Node<K, T> current, ListaCircularDoble result) {
        if (current != null) {
            result.addLast(current.key);
            preOrderRecursive(current.leftChild, result);
            preOrderRecursive(current.rightChild, result);
        }
    }

    public ListaCircularDoble postOrder() {
        ListaCircularDoble result = new ListaCircularDoble();
        postOrderRecursive(root, result);
        return result;
    }

    private void postOrderRecursive(Node<K, T> current, ListaCircularDoble result) {
        if (current != null) {
            postOrderRecursive(current.leftChild, result);
            postOrderRecursive(current.rightChild, result);
            result.addLast(current.key);
        }
    }

    public ListaCircularDoble levelOrder() {
        ListaCircularDoble result = new ListaCircularDoble();
        ListaCircularDoble queue = new ListaCircularDoble();

        if (root == null) return result;

        queue.addLast(root);

        while (!queue.isEmpty()) {
            Node<K, T> current = (Node<K, T>) queue.removeFirst();
            result.addLast(current.key);

            if (current.leftChild != null) {
                queue.addLast(current.leftChild);
            }
            if (current.rightChild != null) {
                queue.addLast(current.rightChild);
            }
        }

        return result;
    }

    public void loadPostFijaExpression(String sPostFija) {
        ListaCircularDoble stack = new ListaCircularDoble();

        for (char ch : sPostFija.toCharArray()) {
            String value = String.valueOf(ch);

            if (Character.isLetterOrDigit(ch)) {
                Node<K, T> operand = new Node<>((K) value, (T) value);
                stack.addLast(operand);
            } else {
                Node<K, T> right = (Node<K, T>) stack.removeLast();
                Node<K, T> left = (Node<K, T>) stack.removeLast();
                Node<K, T> operator = new Node<>((K) value, (T) value);
                operator.leftChild = left;
                operator.rightChild = right;
                stack.addLast(operator);
            }
        }

        root = (Node<K, T>) stack.removeLast();
    }
}
