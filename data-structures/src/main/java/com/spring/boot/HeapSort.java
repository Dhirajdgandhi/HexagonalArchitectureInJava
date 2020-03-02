package com.spring.boot;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class HeapSort<T>{

    Logger LOG = Logger.getLogger(HeapSort.class.getName());
    Comparator comparator;

    public HeapSort() {
        this.comparator = Comparator.naturalOrder();
    }

    public HeapSort(Comparator comparator) {
        this.comparator = comparator;
    }


    public void sort(List<T> array){
        int length = array.size();

        buildHeap(array, length);
        System.out.println("Maximum Element from the 1st round is :" + array.get(0).toString());
        swap(array, 0, length-1);

        for(int i=1;i<length;i++){
            maxHeapify(array, 0, length-i);
            System.out.println("Maximum Element from the round is :" + array.get(0).toString());
            swap(array, 0, length-i-1);
        }

    }

    public void sort(List<T> array, Comparator<T> comparator){
        this.comparator = comparator;
        sort(array);
    }

    void maxHeapify(List<T> array, int rootNodeIndex,int length){
        int leftNodeIndex = 2*rootNodeIndex+1;
        int rightNodeIndex = leftNodeIndex+1;
        int maxNodeIndex=rootNodeIndex;

        if (leftNodeIndex < length // If it doesn't go out of bound
                && // Left Node has greater Value than Parent Node
                comparator.compare(array.get(leftNodeIndex), array.get(rootNodeIndex)) == -1) {
            maxNodeIndex = leftNodeIndex;
        }

        if (rightNodeIndex < length // If it doesn't go out of bound
                && // Right Node has greater Value than Parent Node
                comparator.compare(array.get(rightNodeIndex), array.get(maxNodeIndex)) == -1) {
            maxNodeIndex = rightNodeIndex;
        }

        if (maxNodeIndex != rootNodeIndex){ // If there's a violation
            //swap with the one that is violating
            swap(array, maxNodeIndex, rootNodeIndex);

            // Since we have disturbed bottom trees, call recursively to fix them
            maxHeapify(array, maxNodeIndex, length);
        }

    }

    void buildHeap(List<T> array, int length){
        for(int nodeIndex=length/2; nodeIndex>=0; nodeIndex--){
            maxHeapify(array, nodeIndex, length);
        }
    }

    void swap(List<T> array, int i, int j){
        T tNode = array.get(i);
        array.set(i, array.get(j));
        array.set(j, tNode);
    }
}
