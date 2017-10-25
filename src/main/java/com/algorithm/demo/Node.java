package com.algorithm.demo;

/**
 * Created by szc on 17/9/13.
 */
public class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }

    public static void main(String[] args) {
        Node head = null;
        Node tail = null;

        for(int i=0; i < 5 ; i++){
            Node node = new Node(i);
            if(head == null){
                head = node;
                tail = node;
            } else{
                tail.next = node;
                tail = node;
            }
        }

        while (head != null){
            System.out.println(head.data);
            head = head.next;
        }
    }
}
