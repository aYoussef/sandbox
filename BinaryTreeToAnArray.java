import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
  public static Cell[] arr;
  public static void main(String[] args) {
    arr = new Cell[9];
    BinaryTreeNode root = new BinaryTreeNode(1);
    root.left = new BinaryTreeNode(2);
    root.right = new BinaryTreeNode(3);
    root.left.left = new BinaryTreeNode(4);
    root.left.right = new BinaryTreeNode(5);
    root.right.left = new BinaryTreeNode(6);
    root.right.right = new BinaryTreeNode(7);
    root.left.left.left = new BinaryTreeNode(8);
    root.left.left.right = new BinaryTreeNode(9);
    convert(root);
    for(int i = 0; i < arr.length; i++){
      System.out.print("(" + arr[i].data + ", " + arr[i].child + ")");
    }
  }
  
  public static void convert(BinaryTreeNode root){
    if(root == null){
      return;
    }
    Queue<BinaryTreeNode> queue = new LinkedList<BinaryTreeNode>();
    queue.add(root);
    int index = 0;
    while(!queue.isEmpty()){
      BinaryTreeNode node = queue.remove();
      Cell cell = new Cell(node.data);
      arr[index] = cell;
      index++;
      if(node.left != null){
        cell.child = index + queue.size();
        queue.add(node.left);
        queue.add(node.right);
      }
    } 
  }
  
  static class BinaryTreeNode{
    int data;
    BinaryTreeNode left;
    BinaryTreeNode right;
    BinaryTreeNode(int data){
      this.data = data;
    }
  }
  
  static class Cell{
    int data;
    int child = -1;
    
    Cell(int data){
      this.data = data;
    }
  }
}
