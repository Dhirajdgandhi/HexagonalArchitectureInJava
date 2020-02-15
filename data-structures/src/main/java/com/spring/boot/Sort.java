package com.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;

@Component
// T is the data type of array elements to be sorted
public class Sort<T> {

    @Autowired
    HeapSort<T> heapSort;

    public ArrayList<T> sortArray(ArrayList<T> A, Comparator<T> comparator){
        heapSort.sort(A, comparator);
        return A;
    }
}
