/* first part of project1A
* Deque implemented by Linked List
 */
public class LinkedListDeque<T> {
    /**inner class node*/
    public class Node{
        private T item;
        private Node pre;
        private Node next;


        /** constructor for Node. */
        public  Node(T n, Node ppre, Node nnext){
            item = n;
            pre = ppre;
            next = nnext;

        }
        /** constructor for Node.(especially for sentinel node). */
        public  Node(Node ppre, Node nnext){
            pre = ppre;
            next = nnext;
        }

    }

    /** sentinel node */
    private Node sentinel;
    /** size of the deque*/
    private int size;

    /** constructor for deque*/
    public LinkedListDeque(){
        sentinel = new Node(null,null);
        /*在构造函数体内，创建了一个新的Node实例，并将其赋值给sentinel。
        这个Node实例的prev和next指针都被设置为null，表示它没有前驱和后继节点。
        */
        sentinel.pre = sentinel;
        /*这行代码将sentinel节点的pre（前驱）指针设置为自身。
        在链表中，pre指针通常指向当前节点的前一个节点，但由于sentinel是虚拟头节点，它没有前驱，所以设置为自身。
        */
        sentinel.next = sentinel;
        size = 0;

    }
    public  boolean isEmpty(){
        /*这行代码声明了一个名为isEmpty的公共方法，它返回一个布尔值（boolean）。
        这个方法用于检查链表队列是否为空*/
        return size == 0;
        /*通过比较一个名为size的变量与数字0。
        如果size等于0，说明队列中没有元素，因此队列是空的，方法返回true。
        如果size不等于0，说明队列中有元素，方法返回false。*/
    }
    public int size(){
        return size;
    }

    public void addFirst(T item){
        Node newList = new Node(item, sentinel, sentinel.next);
        /*创建一个新的节点（newList），这个节点包含要添加的元素item，
        并且它的pre（前一个节点）指向哨兵节点（sentinel）
        sentinel.next 是链表的第一个节点*/
        sentinel.next.pre = newList;
        /*这行代码将"当前"头节点（sentinel.next）的pre指针设置为新节点newList。*/
        sentinel.next = newList;
        /*更新哨兵节点的next指针，使其指向新节点-----哨兵节点的next总是链表的第一个节点*/
        size++;
    }
    public void addLast(T item){
        /*接受一个类型为T的参数item。
        T是泛型类型参数，表示队列中存储的元素类型*/
        Node newList = new Node(item, sentinel.pre, sentinel);
        /* 这个新节点的pre（前驱节点）被设置为哨兵节点sentinel的前一个节点（sentinel.pre），
        而next（后继节点）被设置为哨兵节点本身（sentinel）。
        这样做的目的是为了确保新节点在链表的末尾，因为哨兵节点是链表的虚拟头节点，它的pre指向链表的最后一个实际节点。*/
        sentinel.pre.next = newList;
        /*这行代码将哨兵节点的前一个节点（即链表的最后一个实际节点）的next指针指向新创建的节点newList。
        这样，链表的最后一个节点现在指向了新节点，从而将新节点连接到链表的末尾。*/
        sentinel.pre = newList;
        /*这行代码更新哨兵节点的pre指针，使其指向新节点newList。
        这样，哨兵节点的pre现在指向链表的最后一个实际节点，即新添加的节点。
        这确保了哨兵节点始终指向链表的最后一个节点，无论链表如何变化。*/
        size++;
    }
    public T removeFirst(){
        /*从链表中删除并返回第一个元素*/
        if(size == 0){
            return null;
        }
        T ret = sentinel.next.item;
        /*创建一个临时变量ret，用于存储被删除元素的数据。
        这里，sentinel.next指向链表的第一个实际元素（因为sentinel是哨兵节点，不存储任何数据），
        所以sentinel.next.item就是第一个元素的数据*/
        sentinel.next.next.pre = sentinel;
        /*更新哨兵节点的下一个节点(sentinel.next)的pre指针(“.next”是指当前节点）*/
        sentinel.next = sentinel.next.next;
        /*更新哨兵节点的next指针，哨兵节点的next指向第一个元素，现在第一个元素是sentinel.next.next*/
        size--;
        return ret;
    }
    public T removeLast(){
        if (size == 0){
            return null;
        }
        T ret = sentinel.pre.item;
        sentinel.pre.pre.next = sentinel;
        /* 更新哨兵节点上一个节点（sentinel.pre）的next指针，（“.pre”是指当前节点）*/
        sentinel.pre = sentinel.pre.pre;
        /*更新哨兵节点的pre指针，哨兵节点的pre指向最后一个元素，现在最后一个元素是sentinel.pre.pre*/
        size--;
        return ret;
    }
    public T get(int index){
        /*根据给定的索引index从链表中获取元素*/
        if(index >= size){
            return null;
        }
        Node ptr = sentinel;
        for (int i =0; i <= index; i++){
            ptr = ptr.next;
        }
        return ptr.item;
    }

    private T getRecursiveHelp(Node start, int index){
        if(index == 0){
            return start.item;
        }
        else{
            return getRecursiveHelp(start.next, index-1);
//            sentinel.next，即链表的第一个实际节点
        }
    }
    public T getRecursive(int index){
        if(index >= size){
            return null;
        }
        return getRecursiveHelp(sentinel.next, index);
    }

    public void printDeque(){
        Node ptr = sentinel.next;
//        创建一个Node类型的指针ptr，并将其初始化为哨兵节点的下一个节点，
//        即链表的第一个实际节点。
        while(ptr != sentinel){
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
//        当ptr指向哨兵节点时，循环结束，此时所有元素都已打印完毕
    }
//    递归方法getRecursive来获取指定索引的元素，这种方式在链表中不是很常见，
//    因为它可能会导致栈溢出，尤其是在链表很长的情况下。通常，我们会使用迭代方法来遍历链表








}
