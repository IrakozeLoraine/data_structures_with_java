public class DoublyLinkedList<K> {
    /**
     * Implementation of a doubly linked list
     *
     * Author: Irakoze Loraine
     * created at: 24th July 2021
     */
    private int size = 0;

    private class Node <K>
    {
        K element;
        Node <K> prev, next;
        public Node(K element, Node<K> prev, Node<K> next)
        {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
    private Node <K> head = null;
    private Node <K> tail = null;

    public void clear()
    {
        Node<K> temp = head;
        while (temp != null){
            Node<K> next = temp.next;
            temp.prev = temp.next = null;
            temp.element = null;
            temp = next;
        }
        head = tail = temp = null;
        size = 0;
    }
    public int size()
    {
        return  size;
    }
    public boolean isEmpty()
    {
        return size() == 0;
    }

    //Add element to the tail
    public  void addLast(K element)
    {
        if(isEmpty()) head = tail = new Node<>(element, null, null);
        else{
            tail.next = new Node<>(element, tail, null);
            tail = tail.next;
        }
        size++;
    }

    //Add element to the head
    public void addFirst(K element)
    {
        if(isEmpty()) head = tail = new Node<>(element, null, null);
        else{
            head.prev = new Node<>(element, null, head);
            head = head.prev;
        }
        size++;
    }

    //Get the value of the head
    public K peekFirst()
    {
        if(isEmpty()) throw new RuntimeException("List is empty");
        return head.element;
    }

    //Get the value of the tail
    public K peekLast()
    {
        if(isEmpty()) throw new RuntimeException("List is empty");
        return tail.element;
    }

    //Remove element at the head
    public K removeFirst()
    {
        if (isEmpty()) throw new RuntimeException("List is empty");
        K element = head.element;
        head = head.next;
        --size;

        if (isEmpty()) tail = null;
        else head.prev = null;
        return element;
    }

    //Remove element at the tail
    public K removeLast()
    {
        if (isEmpty()) throw new RuntimeException("List is empty");
        K element = tail.element;
        tail = tail.prev;
        --size;

        if (isEmpty()) head = null;
        else tail.next = null;
        return element;
    }
    //Remove node from the linked list
    private K remove(Node<K> node)
    {
        if(node.prev == null) return removeFirst();
        if(node.next == null) return removeLast();

        node.next.prev = node.prev;
        node.prev.next = node.next;

        K element = node.element;
        node.element = null;
        node.prev = node.next = null;
        --size;
        return element;
    }

    //Remove node at a specific index
    public K removeAt(int index)
    {
        if(index<0 || index>=size) throw new IllegalArgumentException();

        int i;
        Node<K> temp;

        //Search from the head
        if(index < size/2)
            for (i = 0, temp = head; i != index; i++)
                temp = temp.next;
        //Search from the back of the list
        else
            for (i = size-1, temp = tail;i !=index ; i--)
                temp = temp.prev;
        return remove(temp);
    }

    //Remove a specific element from the linked list
    public boolean remove(Object obj)
    {
        Node<K> temp;
        if(obj == null)
        {
            for(temp = head; temp != null; temp = temp.next)
                if(temp.element == null)
                    remove(temp);
                    return true;
        }
        else{
            for(temp = head; temp != null; temp = temp.next)
                if(obj.equals(temp.element)){
                    remove(temp);
                    return true;
                }
        }
        return false;
    }

    //find the index of a specific element in the linked list
    public int indexOf(Object obj)
    {
        int index = 0;
        Node<K> temp;
        if(obj == null){
            for(temp = head; temp != null; temp = temp.next)
                index++;
                if(temp.element == null)
                    return index;
        }
        else{
            for(temp = head; temp != null; temp = temp.next)
                index++;
                if(obj.equals(temp.element))
                    return index;
        }
        return -1;
    }

    //check if an element is within the linked list
    public boolean contains(Object obj)
    {
        return indexOf(obj) != -1;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<K> temp = head;
        while (temp != null)
        {
            sb.append(temp.element).append(", ");
            temp = temp.next;
        }
        sb.append("]");
        return sb.toString();
    }

    //Traverse through the linked list
    public void traverse() {
        while (head.next != null) {
            System.out.println(head.element+" >> ");
        }
    }
}
