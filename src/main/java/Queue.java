public class Queue <T>{
    /**
     * Implementation of a queue using a doubly linked list
     *
     * Author: Irakoze Loraine, mukezwa@gmail.com
     */

    private DoublyLinkedList<T> queue = new DoublyLinkedList<>();

    public Queue(){}
    public Queue(T firstElement) {
        offer(firstElement);
    }

    //return the number of elements in the queue
    public int size()
    {
        return queue.size();
    }

    //Check if queue is empty
    public boolean isEmpty()
    {
        return size() == 0;
    }

    //Peek the front of the queue and throw underflow error when empty
    public T peek() {
        if (isEmpty())
            throw new RuntimeException("Queue is Empty");
        return queue.peekFirst();
    }

    //Add an element to rear(back of the queue)
    public void offer(T element)
    {
        queue.addLast(element);
    }

    //Poll an element from the front of the queue and throw underflow error when empty
    public T poll() {
        if(isEmpty())
            throw new RuntimeException("Queue is empty");
        return queue.removeFirst();
    }

    //Traverse through the queue
    public void traverse()
    {
        queue.traverse();
    }
}
