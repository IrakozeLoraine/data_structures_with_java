
public class Stack<T> {
    /**
     * Implementation of a stack using a doubly linked list
     * but only using the methods of a singly linked list
     *
     * Author: Irakoze Loraine
     * created at: 24th July 2021
     */

    private DoublyLinkedList<T> stack = new DoublyLinkedList<>();

    public Stack(){}
    public Stack(T firstElement) {
        push(firstElement);
    }

    //return the number of elements in the stack
    public int size()
    {
        return stack.size();
    }

    //Check if stack is empty
    public boolean isEmpty()
    {
        return size() == 0;
    }

    //Peek the top of the stack
    public T peek() {
        if (isEmpty())
            throw new RuntimeException("Stack is Empty");
        return stack.peekLast();
    }

    //Push an element to stack
    public void push(T element)
    {
        stack.addLast(element);
    }

    //Pop an element off the stack and throw underflow error when empty
    public T pop() {
        if(isEmpty())
            throw new RuntimeException("Stack is empty");
        return stack.removeLast();
    }
}
