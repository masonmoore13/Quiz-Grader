
/*
Date: 3/7/2022
Course: CSCI 3005
Description: PROGRAMMING ASSIGNMENT 1: History Grading
Mason Moore 30097089

 * A program used to grade the answers on a historical event exam. It will compare the results 
 * from the student, myanswers1.txt, compare them to the correct results in answers.txt, and output 
 * a score, for the number of events in the correct pattern, and then output said events that were right. 
 */
import java.util.*;
import java.io.*;

public class Patterns {

    private String[] answers = null;

    /*
     * Uses FileInputStream to read raw bytes of data into a newFile, which is then
     * read and interpreted by DataInputStream, to which is then read, line by line,
     * in BufferedReader. Reads each line into a string, parses the string, and
     * reads it into an array called answers. First line contains number of events in txt
     * file. Returns an error if file not found.
     * Note: I decided to practice with streams and buffered reader even though scanner would
     * do the job just fine, but to my knowledge, buffered reader is most useful for much larger
     * amounts of data or while using more threads. 
     * 
     * @Param filename the name of the text file to read
     */
    public Patterns(String filename) {
        try {
            FileInputStream newFile = new FileInputStream(filename);
            DataInputStream data_input = new DataInputStream(newFile);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
            String line;
            line = buffer.readLine();
            int size = Integer.parseInt(line);
            answers = new String[size];

            // loop starts at 1 to ignore first line
            for (int i = 0; i < size; i++) {
                line = buffer.readLine();
                answers[i] = line;
            }
            buffer.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /*
     * A method that accepts a file containing a student's answers and computes the
     * score, reading the file line by line like the method above. Processes the
     * converted string array into getLongest().
     * 
     * @Param filename the name of the text file to read
     * @Return the length (score) of the myAnswers array after it has been processed
     * by getLongest()
     */
    public int grade(String filename) {
        String[] myAnswers = null;
        try {
            FileInputStream newFile = new FileInputStream(filename);
            DataInputStream data_input = new DataInputStream(newFile);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
            String line;
            line = buffer.readLine();
            int size = Integer.parseInt(line);
            myAnswers = new String[size];

            for (int i = 0; i < size; i++) {
                line = buffer.readLine();
                myAnswers[i] = line;
            }

            buffer.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return getLongest(myAnswers).length;
    }

    /*
     * Reads each line of the student's answer again, processes the converted string
     * array with getLongest(). If the string processed after getLongest() is
     * greater than 0,
     * separates each event with a comma
     * 
     * @Param filename the name of the text file to read
     * @Return str the string of correct elements
     */
    public String pattern(String filename) {
        String[] myAnswers = null;
        try {
            FileInputStream newFile = new FileInputStream(filename);
            DataInputStream data_input = new DataInputStream(newFile);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
            String line;
            line = buffer.readLine();
            int size = Integer.parseInt(line);
            myAnswers = new String[size];

            for (int i = 0; i < size; i++) {
                line = buffer.readLine();
                myAnswers[i] = line;
            }
            buffer.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        /*Converts the string array returned as a string, separated by commas
        * Note: The instructions say that this method should return the events separated
        * by commas, but the test class splits each event by comma and places on a new line. 
        * In the test cases, it is showing the correct format - separated by commas. 
        */
        String str = "";
        for (String s : getLongest(myAnswers)) {
            if (str.length() > 0) {
                str += ",";
            }
            str += s;
        }
        return str;
    }

    /*
     * Using dynamic programming, initializes a matrix for the length of answers and
     * student's answers. Goes through both arrays to find matching strings.
     * 
     * @Param myAnswers the string array of answers that will be compared to correct
     * answers.
     * @Return lcs the longest common subsequence as a string array.
     */
    private String[] getLongest(String[] myAnswers) {
        int m = answers.length, n = myAnswers.length;
        int[][] matrix = new int[m + 1][n + 1];

        // Loop through both arrays to find matching strings
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // Set the first column and first row to 0's
                if (i == 0 || j == 0)
                    matrix[i][j] = 0;
                // If there is a match add the count to the value on the top left diagonal
                else if (answers[(i - 1)].equals(myAnswers[(j - 1)]))
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                // If they don't match, fill in with the highest value either to the left or
                // above this cell
                else
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]);
            }
        }

        // Get length of longest subsequence
        int len = matrix[m][n];
        // Array to hold longest subsequence
        String[] lcs = new String[len];

        // Bottom-up approach to get the longest subsequence
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (answers[(i - 1)].equals(myAnswers[(j - 1)])) {
                lcs[--len] = answers[(--i)];
                j--;
            } else if (matrix[i - 1][j] > matrix[i][j - 1])
                i--;
            else
                j--;
        }
        return lcs;
    }
}
