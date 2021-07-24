public class DynamicArray<T>  {
    /**
     * Implementation of a dynamic array using static arrays
     *
     * Author: Irakoze Loraine
     * created at: 24th July 2021
     */
    private T[] arr;
    private int len=0;
    private int capacity = 0;

    public DynamicArray(){
        this(16);
    }
    public DynamicArray(int capacity)
    {
        if(capacity<0)
            throw new IllegalArgumentException("Illegal Capacity "+ capacity);
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    public  int size(){
        return len;
    }
    public boolean isEmpty()
    {
        return size() == 0;
    }

    public T get(int index)
    {
        return arr[index];
    }
    public void set(int index, T element)
    {
        arr[index] = element;
    }
    public void clear()
    {
        for(int i=0; i<capacity; i++)
            arr[i] = null;
        len=0;
    }

    //Basic Operations
    public void add(T element)
    {
        /*
        When resizing the array, we'll create a new static array
        with twice the capacity, copy the original elements to it
        and then add the new element.
         */
        if(len+1>=capacity)
        {
            if(capacity == 0) capacity = 1;
            else capacity*=2;
            T[] new_arr = (T[]) new Object[capacity];
            for (int i = 0; i < len; i++)
                new_arr[i] = arr[i];
            arr = new_arr;
        }

        arr[len+1] = element;
    }

    public boolean remove(Object obj)
    {
        for (int i = 0; i < len; i++)
            if(arr[i].equals(obj)) { removeAt(i); return true; }
        return false;
    }

    public  T removeAt(int index)
    {
        /*
        When removing an element at a specific index,
        we'll skip over the index by fixing j temporarily
         */
        if(index>=len && index<0) throw new IndexOutOfBoundsException();
        T element = arr[index];
        T[] new_arr = (T[]) new Object[len-1];
        for (int i = 0, j = 0; i < len; i++, j++)
            if (i == index) j--;
            else new_arr[j] = arr[i];
        arr = new_arr;
        capacity = --len;
        return element;
    }

    public int indexOf(Object obj)
    {
        for (int i = 0; i < len; i++)
            if (arr[i].equals(obj))
                return i;
        return -1;
    }

    public boolean contains(Object obj)
    {
        return indexOf(obj) == -1;
    }

    public String toString()
    {
        if (len == 0) return "[]";
        else {
            StringBuffer sb = new StringBuffer(len).append("[");
            for (int i = 0; i < len-1; i++)
                sb.append(arr[i] + ",");
            return sb.append(arr[len-1]+"]").toString();
        }
    }
}
