import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinarySearchTree <T extends Comparable<T>> {
    /**
     * Implementation of a Binary Search Tree(BST)
     *
     * Author: Irakoze Loraine, mukezwa@gmail.com
     */

    private class Node {
        T element;
        Node left, right;
        public Node (Node left, Node right, T element) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }
    private int totalNodes = 0;
    private Node root = null;

    public int size() {
        return totalNodes;
    }
    public boolean isEmpty() {
        return size() == 0;
    }

    //Add an element to this binary tree
    public boolean add(T element) {
        //check if element already exists
        if (contains(element)) return false;
        else {
            root = add(root, element);
            totalNodes++;
            return true;
        }
    }

    //Remove an element to this binary tree
    public boolean remove(T element) {
        //check if element already exists
        if (contains(element)) return false;
        else {
            root = remove(root, element);
            totalNodes--;
            return true;
        }
    }

    //Check if elements exist in the binary tree
    public  boolean contains(T element) {
        return contains(root, element);
    }

    //Calculate the height of the tree
    public int height() {
        return height(root);
    }

    //Traverse through the binary tree
    public void traverse(TreeTraversalEnum order) {
        switch (order) {
            case PRE_ORDER:
                preorder(root);
                break;
            case IN_ORDER:
                inorder(root);
                break;
            case POST_ORDER:
                postorder(root);
                break;
            case LEVEL_ORDER:
                break;
            default:
                System.out.println("Unknown Input");
        }
    }

    //Add the element at the corresponding node
    private Node add(Node node, T element) {
        if (node == null)
            node = new Node(null, null, element);
        else {
            if (element.compareTo(node.element) < 0)
                node.left = add(node.left, element);
            else
                node.right = add(node.right, element);
        }
        return node;
    }

    //Remove the element at the corresponding node
    private Node remove(Node node, T element) {
        if (node == null) return null;

        int cmp = element.compareTo(node.element);

        if (cmp < 0)
            node.left = remove(node.left, element);
        else if (cmp > 0)
            node.right = remove(node.right, element);
        //If we find the node we wish to remove
        else {
            if (node.left == null) {
                Node right = node.right;
                node.element = null;
                node = null;

                return  right;
            }
            else if (node.right == null) {
                Node left = node.left;
                node.element = null;
                node = null;

                return left;
            }
            //if the node to remove has two descendants
            else {
                //find the largest node in the left subtree
                /*
                    Node tmp = findLargestLeft(node.left);
                    //copy the data to the node
                    node.element = tmp.element;
                    //remove the node we've found
                    node.left = remove(node.left, tmp.element);
                  */
                //find the smallest node in the right subtree
                Node tmp = findSmallestRight(node.right);
                //copy the data to the node
                node.element = tmp.element;
                //remove the node we've found
                node.right = remove(node.right, tmp.element);
            }
        }
        return node;
    }

    //Find the largest left node
    private Node findLargestLeft(Node node) {
        Node current = node;
        while (current.right != null)
            current = current.right;
        return current;
    }

    //Find the smallest right node
    private Node findSmallestRight(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    //Check if elements exist in any node of the binary tree
    private boolean contains(Node node, T element) {
        if (node == null) return false;

        int cmp = element.compareTo(node.element);

        if (cmp < 0) return contains(node.left, element);
        else if (cmp > 0) return contains(node.right, element);
        else return true;
    }

    //Calculate the height per every node in the tree
    private int height(Node node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right))+1;
    }

    //Pre-Order Traversal using recursive functions
    private void preorder(Node node) {
        if (node == null) return;
        System.out.println(node.element);
        preorder(node.left);
        preorder(node.right);
    }

    //In-Order Traversal using recursive functions
    private void inorder(Node node) {
        if (node == null) return;
        inorder(node.left);
        System.out.println(node.element);
        inorder(node.right);
    }

    //Post-Order Traversal using recursive functions
    private void postorder(Node node) {
        if (node == null) return;
        postorder(node.left);
        postorder(node.right);
        System.out.println(node.element);
    }

    //Pre-Order Traversal using iterator
    private Iterator<T> preOrderTraversal() {
        int expectedNodeCount = totalNodes;
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            Node temp = root;
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != totalNodes) throw new ConcurrentModificationException();
                return root!= null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != totalNodes) throw new ConcurrentModificationException();

                while (temp != null && temp.left != null){
                    stack.push(temp.left);
                    temp = temp.left;
                }
                Node node = stack.pop();

                if (node.right != null){
                    stack.push(node.right);
                    temp = node.right;
                }
                return node.element;
            }
        };
    }

    //In-Order Traversal using iterator
    private Iterator<T> inOrderTraversal() {
        int expectedNodeCount = totalNodes;
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            Node temp = root;
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != totalNodes) throw new ConcurrentModificationException();
                return root!= null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != totalNodes) throw new ConcurrentModificationException();

                while (temp != null && temp.left != null){
                    stack.push(temp.left);
                    temp = temp.left;
                }
                Node node = stack.pop();

                if (node.right != null){
                    stack.push(node.right);
                    temp = node.right;
                }
                return node.element;
            }
        };
    }

    //Pre-Order Traversal using iterator
    private Iterator<T> postOrderTraversal() {
        int expectedNodeCount = totalNodes;
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            Node temp = root;
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != totalNodes) throw new ConcurrentModificationException();
                return root!= null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != totalNodes) throw new ConcurrentModificationException();

                while (temp != null && temp.left != null){
                    stack.push(temp.left);
                    temp = temp.left;
                }
                Node node = stack.pop();

                if (node.right != null){
                    stack.push(node.right);
                    temp = node.right;
                }
                return node.element;
            }
        };
    }

    //Level-Order Traversal uses iterator
    private Iterator<T> levelOrderTraversal() {
        int expectedNodeCount = totalNodes;
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            Node temp = root;
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != totalNodes) throw new ConcurrentModificationException();
                return root!= null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != totalNodes) throw new ConcurrentModificationException();

                while (temp != null && temp.left != null){
                    stack.push(temp.left);
                    temp = temp.left;
                }
                Node node = stack.pop();

                if (node.right != null){
                    stack.push(node.right);
                    temp = node.right;
                }
                return node.element;
            }
        };
    }
}
