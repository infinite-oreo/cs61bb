public class ArrayDeque<T> {
    private  T[] array;
    private int size;
    private int length;
    private int front;// 队列前端的索引
    private int last;

    /** constructor for ArrayDeque */
    public ArrayDeque(){
        array = (T[]) new Object[8];// 初始化数组，大小为8
        size = 0;// 初始化元素数量为0
        length = 8;
        front = 4;// 初始化前端索引为4，这里可能是为了后续的逻辑处理
        last = 4;
    }
    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    // 辅助方法，用于获取索引减1的结果
    private int minusOne(int index){
        if(index == 0){
            return length - 1;
        }
        return index - 1;
    }

    // 辅助方法，用于获取索引加1的结果，并处理数组越界
    private int plusOne(int index, int module){
        /*这行代码的目的是确保索引值始终在合法范围内，即 [0, module-1]。
        取模运算 % 会返回除法的余数，因此当 index 大于等于 module 时，index 将被限制在 [0, module-1] 的范围内。
        * 通过使用取模运算，可以防止索引超出数组的实际长度，保证在数组中的循环移动时不会越界。
        这是一种常见的技巧，尤其是在环形数据结构的实现中。
        * */
        index %= module;
        if(index == module - 1){
            return 0;
        }
        return index + 1;
    }

    // 扩容方法
    private void grow(){
//   创建新的数组，大小是原来的两倍
        T[] newArray = (T[]) new Object[length * 2];
        // 将旧数组的元素复制到新数组
        int ptr1 = front;
        int ptr2 = length;
        while (ptr1 != last){
            newArray[ptr2] = array[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length * 2);
        }
        // 更新数组引用、前端和后端索引以及数组长度
        front = length;
        last = ptr2;
        array = newArray;
        length *= 2;
    }
    // 缩容方法
    private void shrink(){
        // 创建新的数组，大小是原来的一半
        T[] newArray = (T[]) new Object[length/2];
        // 将旧数组的元素复制到新数组
        int ptr1 = front;
        int ptr2 = length / 4;
        while (ptr1 != last){
            newArray[ptr2] = array[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length/2);
        }
        // 更新前端索引、后端索引以及数组长度
        front = length/4;
        last = ptr2;
        array = newArray;
        length /= 2;
    }
    // 在队列前端添加元素
    public void addFirst(T item){
        if(size == length-1){
            grow();// 如果队列已满，先扩容
        }
        front = minusOne(front);// 前端索引向前移动
        array[front] = item;// 在前端添加元素
        size++;
    }

    public void addLast(T item){
        if(size == length -1){
            grow();
        }
        array[last] = item;// 在后端添加元素
        last = plusOne(last,length);// 后端索引向后移动
        size++;
    }

    public T removeFirst(){
        if (length >= 16 && length / size >= 4){
            shrink();// 如果数组过大，先缩容
        }
        if(size == 0){
            return null;
        }
        T ret = array[front];// 获取前端元素
        front = plusOne(front, length);// 前端索引向前移动
        size--;
        return ret;// 返回被移除的元素
    }

    public  T removeLast(){
        if (length >= 16 && length / size >= 4){
            shrink();// 如果数组过大，先缩容
        }
        if(size == 0){
            return null;
        }
        last = minusOne(last);
        size--;
        return array[last];
    }

    public T get(int index){
        if(index >= size){
            return null;
        }
        int ptr = front;
        for (int i = 0; i< index; i++){
            ptr = plusOne(ptr, length);
        }
        return array[ptr];
    }

    public void printDeque(){
        int ptr = front;
        while(ptr != last){
            System.out.print(array[ptr]+ " ");
            ptr = plusOne(ptr, length);
        }
    }


}
