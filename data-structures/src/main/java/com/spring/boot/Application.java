package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class Application {

	static Sort sort = new Sort();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		System.out.println("The Sorted Array is : " + sort.sortArray(getInput()));
	}

	static ArrayList<Integer> getInput(){
		System.out.println("Enter an array of numbers to be sorted");

		Scanner sc = new Scanner(System.in);
		String[] arr=sc.nextLine().split(" ");//take the input in string array separated by whitespaces" "
		ArrayList<Integer> A = new ArrayList(arr.length);

		for(int i=0;i<arr.length;i++)
			A.add(Integer.parseInt(arr[i]));//each array indices parsed to integer

		System.out.println("The input Array is : " + A);
		return A;
	}

}

