package com.spring.boot;

import java.util.*;

/**
 * Priority Queue implemented as an ArrayList
 * */
public class PriorityQueue<T>{

    private List<T> queue = new ArrayList<T>();
    static HeapSort heapSort;
    Comparator comparator;

    public PriorityQueue() {
    }

    public PriorityQueue(int initialCapacity) {
        queue = new ArrayList<T>(initialCapacity);
    }

    public PriorityQueue(int initialCapacity, Comparator comparator) {
        this.queue = new ArrayList<T>(initialCapacity);
        this.comparator = comparator;
        heapSort = new HeapSort<>(this.comparator);
    }

    public PriorityQueue(ArrayList<T> array){
        heapSort.sort(array);
        queue = array;
    }

//    @Override
    public boolean add(T node){
        int length = queue.size();
//        ((ArrayList<T>)queue).ensureCapacity(length+1); //Increase capacity
        queue.add(length, node);// Insert node to end
        int nodeIndex = length;

        while(nodeIndex > 1 // We don't reach root of tree
                && // There' a Violation with Parent
//MAX HEAP                queue.get(nodeIndex).compareTo(queue.get(nodeIndex/2)) == 1){
                comparator.compare(queue.get(nodeIndex / 2), (queue.get(nodeIndex))) > 0){ //MIN HEAP

            int parentIndex = (int)Math.floor((nodeIndex+1)/2)-1;
            //Swap with Parent
            heapSort.swap(queue, nodeIndex, parentIndex);
            nodeIndex = parentIndex;
        }
        return true;
    }

    public T peek(){
        return queue.get(0); //Simply the top element of the QUEUE
    }

    public T poll(){
        T highestNode = queue.get(0); //Simply the top element of the QUEUE
        List<T> array = new ArrayList<T>();
        array.addAll(queue.subList(1, queue.size()));
        queue = array;
        heapSort.maxHeapify(queue, 0, queue.size() - 1);
        return highestNode;
    }

    public List<T> getQueue() {
        return queue;
    }

//    @Override
    public int size() {
        return queue.size();
    }

//    @Override
    public boolean isEmpty() {
        return queue.size() == 0;
    }

//    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

//    @Override
    public boolean remove(Object o) {
        //TODO: Improve this implementation. Currently it is order of nlogn
        int index = queue.indexOf(o);
        List newQueue = queue.subList(0,index);
        newQueue.addAll(index+1,queue.subList(index+1,queue.size()-1)) ;
        queue = newQueue;
//        heapSort.maxHeapify(queue, 0, queue.size() - 1);
        heapSort.sort(queue);
        return false;
    }

//    @Override
    public boolean addAll(Collection c) {
        return false;
    }

//    @Override
    public void clear() {

    }

//    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

//    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

//    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

//    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
