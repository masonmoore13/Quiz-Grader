import java.util.*;
/**
   Program to test the basic functionality of the Patterns class.
   
*/
public class PatternsTest
{
 public static void main(String[] args) 
   {
      Patterns test1 = new Patterns("answersA.txt");
      System.out.println("The score is: " + test1.grade("myanswers1.txt"));
      String [] events = test1.pattern("myanswers1.txt").split(",");
      System.out.println("The events in the longest matching pattern are: ");
      for (String s: events)
         System.out.println(s);
   }
}
