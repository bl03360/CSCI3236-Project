package DefiniteFiniteAutomata;

import java.io.*;
import java.util.*;

public class DFA {

    private static HashMap<Character, Character> inner;
    private static HashMap<Character, HashMap<Character, Character>> outer = new HashMap<>();
    private static ArrayList<Character> alph = new ArrayList<>();
    private static ArrayList<Character> states = new ArrayList<>();
    private static ArrayList<Character> finalStates = new ArrayList<>();
    private static LinkedList<Character> transitions = new LinkedList<>();
    private static char startState;

    public static void main(String[] args) {

        fileReader();        
        createDfa();
        
        for (int i = 0; i < states.size(); i++) {
			System.out.println("At state " + states.get(i) + " " + outer.get(states.get(i)));
		}
        
        userInput();
       
    }
    
    private static void userInput() {
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter a string of the alphabet, " + alph.toString() + ", to generate a proper string of the dfa.");
        String input = scan.next();
 
        traverseDFA(input);
        
        
    }
    
    private static void traverseDFA(String input) {
        if(input.charAt(0) != startState) {
            improperString();
        }
        else if(!finalStates.contains(input.charAt(input.length()-1))) {
            improperString();
        }
        
        int a = 1;
        
        for(int i = 0;i < input.length();i++) {
        	char marker = input.charAt(a);
        	if(!outer.get(input.charAt(i)).containsValue(marker)) {
        		System.out.println("you didnt work");
        		improperString();
        	}
        	System.out.println("you worked");
        	a++;
        	if(a == input.length()) {
        		System.out.println("you worked finally");
        		System.exit(0);
        	}
        }                     
    }
    

    private static void fileReader() {
        try {
            File file = new File("DFA.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            line = line.replaceAll("[^a-zA-Z0-9]", "");
            
            int count = 0;

            while (line != null) {                
                while (count <= 3) {
                    
                    if (count == 0) {
                        for (int i = 0; i < line.length(); i++) {
                            alph.add(line.charAt(i));                        
                        }
                    } // reads alphabet from line 1 from file
                    else if (count == 1) {
                        for (int i = 0; i < line.length(); i++) {
                            states.add(line.charAt(i));                           
                        }
                    } // reads states from line 2 from file
                    else if (count == 2) {
                        startState = line.charAt(0);                      
                    } // reads start state from line 3 from file
                    else {
                        for (int i = 0; i < line.length(); i++) {
                            finalStates.add(line.charAt(i));                          
                        }
                    } // reads final states
                    line = br.readLine();
                    line = line.replaceAll("[^a-zA-Z0-9]", "");
                    count++;
                } // reads first 4 lines for the alphabet, states, start state, and final states
                
                line = line.replaceAll("[^a-zA-Z0-9]", "");
                
                for (int i = 0; i < line.length(); i++) {
                    transitions.add(line.charAt(i));                    
                } // reads transitions from file adding from rest of the lines
                
                line = br.readLine();
                
            } // while loop to read all lines of file

        } // try block
        catch (IOException error) {
            error.printStackTrace();
        } // catch block
    }// file reader method

    private static void createDfa() {
        for (int i = 0; i < states.size(); i++) {
            outer.put(states.get(i), createTransition(states.get(i)));
        }
    }

    private static HashMap<Character, Character> createTransition(char a) {
        inner = new HashMap<Character, Character>();
        
        while (transitions.peek() != null && a == transitions.peekFirst()) {
            transitions.removeFirst();
            inner.put(transitions.poll(), transitions.poll());
        }
        
        return inner;
    }

    private static void improperString() {
        System.out.println("The string you entered is not a proper language for this DFA");
        userInput();
    }

}