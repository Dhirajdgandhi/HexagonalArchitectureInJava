package com.spring.boot;

import Assignment1.Nodes.NodeBase;

import java.util.*;

/**
 * Priority Queue implemented as an ArrayList
 * */
public class PriorityQueue<T extends NodeBase> implements Collection {

    private List<T> queue = new ArrayList<T>();
    static HeapSort heapSort;

    public PriorityQueue() {
    }

    public PriorityQueue(int initialCapacity) {
        queue = new ArrayList<T>(initialCapacity);
    }

    public PriorityQueue(ArrayList<T> array){
        heapSort.sort(array);
        queue = array;
    }

    static
    {
        heapSort = new HeapSort<NodeBase>();
    }

    public void insert(T node){
        int length = queue.size();
        ((ArrayList<T>)queue).ensureCapacity(length+1); //Increase capacity
        queue.add(length, node);// Insert node to end
        int nodeIndex = length;

        while(nodeIndex > 1 // We don't reach root of tree
                && // There' a Violation with Parent
//MAX HEAP                queue.get(nodeIndex).compareTo(queue.get(nodeIndex/2)) == 1){
                queue.get(nodeIndex/2).compareTo(queue.get(nodeIndex)) == 1){ //MIN HEAP

            int parentIndex = (int)Math.floor((nodeIndex+1)/2)-1;
            //Swap with Parent
            heapSort.swap(queue, nodeIndex, parentIndex);
            nodeIndex = parentIndex;
        }
    }

    public T getHighestPriority(){
        return queue.get(0); //Simply the top element of the QUEUE
    }

    public T returnAndDeleteHighestPriority(){
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

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public boolean add(Object o) {
        insert((T) o);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
