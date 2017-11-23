//import the array class just to test the code 
import java.util.Arrays;
public class ExamGrading{
  
  public static void main (String [] args){
    char [][] responses = {{'C', 'A', 'B', 'B', 'C', 'A'}, {'A', 'A', 'B', 'B', 'B', 'B'},{'C', 'B', 'A', 'B', 'C', 'A'}, {'A', 'B', 'A', 'B', 'B', 'B'}};
    char [] solutions = {'C', 'A', 'B', 'B', 'C', 'C'};
    double [] grades = (gradeAllStudents(responses, solutions));
   System.out.println(Arrays.toString(grades));
    int[][]cheating = findSimilarAnswers(1,responses,solutions);
    System.out.println(Arrays.deepToString(cheating));
  }
  
  //method gradeAllStudents
  public static double [] gradeAllStudents(char [][] responses, char [] solutions){
    //first create the variable that will be returned at the end of the method
    //it will have the length of the 2D array (number of students)
    double [] grades = new double [responses.length];
    //for loop to go through each student
    for(int i = 0; i<responses.length; i++){
      //first check if the number of responses match the number of solutions
      if(responses[i].length != solutions.length){
        //throw the exception
        throw new IllegalArgumentException ("The student whose number of answers didn't match the number of solutions is at index " + i +  ". The student's number of responses is " + responses[i].length + " and the exam's number of questions is " + solutions.length + ".");
      }else{
        //call the helper method to grade 1 student and store the grade in the array of grades
        grades [i] = gradeStudent(responses[i],solutions); 
      }
    }
    return grades;
  }
  
  //helper method to grade a single student
  public static double gradeStudent(char [] responses, char [] solutions){
    //grade that will be returned
    double grade = 0.0;
    //the responses that the student got right
    int correctAnswers = 0;
    for(int i = 0; i<solutions.length; i++){
      if(responses[i]==solutions[i]){
        //if the answer is same as the solution 
        //increment the number of correct answers
        correctAnswers++;
      }
    }
    // calculate the grade (#correct/#total questions)*100   
    grade = ((double)(correctAnswers)/(solutions.length)*100);  
    return grade;
  }
  
  //method numWrongSimilar
  public static int numWrongSimilar(char [] student1, char [] student2, char [] solutions){
    //variable that will be returned as the number of same wrong answers
    int numberWrong = 0;
    for(int i =0; i<student1.length; i++){
      //if the answers is the same as the other student answer but different to the correct answer
      if((student1[i]==student2[i]) && (student1[i] != solutions[i])){
        //add one to the number of same wrong answers
        numberWrong++;
      }
    }
    return numberWrong;
  }
  
  //method NumMatches
  public static int numMatches(char [][] allResponses, char [] solutions, int index, int threshold){
    //the number of students that match the student at the index 
    int matches = 0;
    for(int i=0; i<allResponses.length; i++){
      //if the index of the char array of students is the same as "index" means we are comparing the same student
      //so continue and skip the comparison
      if(i==index){
        continue;
      }
      //if the number of similar wrong answers of the comparison is greater than or equal to the threshold
      if((numWrongSimilar(allResponses[index],allResponses[i],solutions))>=threshold){
        //add one to the matches
        matches++;
      }
    }
    return matches;
  }
  
  //method findSimilarAnswers
  public static int[][] findSimilarAnswers(int threshold, char[][] allResponses, char[] solutions){
    //the length of the fnal 2D array is the number of students
    int[][] cheating = new int[allResponses.length][];
    for(int i = 0; i<allResponses.length; i++){
      //we need a variable that will be the elements of the sub array to be the length of the num of matches the student has 
      int [] matches = new int [numMatches(allResponses,solutions,i,threshold)];
      //j is the counter for the students the main student (i) will be compared with
      //we need an index for the elements of the sub array
        int index = 0;
      for(int j = 0; j<allResponses.length; j++){
        //we don't want to compare the student with himself
        if (j==i){
          continue;
        }
        //if the similar wrong answers are greater than the threshold with the student we are comparing with ( i with j)
        if ((numWrongSimilar(allResponses[i],allResponses[j],solutions))>=threshold){
          //put the student in the sub array 
          matches[index] = j; 
          index++;
        }
      }
      //update the array in the index of that student with the matches he had
      cheating[i]= matches;
    }    
    return cheating;
  }Git