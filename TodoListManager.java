import java.util.*;
import java.io.*;

public class TodoListManager {
    public static final boolean EXTENSION_FLAG = true;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        
        List<String> todos = new ArrayList<>();
        List<String> completedTasks = new ArrayList<>();

        System.out.println("Welcome to your TODO List Manager!");

        if (EXTENSION_FLAG) {
            String choice = "";
            while (!choice.equalsIgnoreCase("Q")) {
                System.out.println("What would you like to do?");
                System.out.print("(A)dd TODO, (M)ark TODO as done," 
                    + " (L)oad TODOs, (S)ave TODOs, (Mu)ltiple Add, (Q)uit? ");
                
                choice = console.nextLine();

                if (choice.equalsIgnoreCase("A")) {
                    addItem(console, todos);
                    printTodos(todos);
                } else if (choice.equalsIgnoreCase("M")) {
                    markItemAsDone(console, todos, completedTasks);
                    printTodos(todos);
                } else if (choice.equalsIgnoreCase("L")) {
                    loadItems(console, todos);
                    printTodos(todos);
                } else if (choice.equalsIgnoreCase("S")) {
                    saveItems(console, todos);
                    printTodos(todos);
                } else if (choice.equalsIgnoreCase("Mu")) {
                    addMultipleItems(console, todos);
                    printTodos(todos);
                } else if (!choice.equalsIgnoreCase("Q")) {
                    System.out.println("Unknown input: " + choice);
                    printTodos(todos);
                }
            }

        } else {
            String choice = "";
            while (!choice.equalsIgnoreCase("Q")) {
                System.out.println("What would you like to do?");
                System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs," 
                    + " (S)ave TODOs, (Q)uit? ");
                choice = console.nextLine();

                if (choice.equalsIgnoreCase("A")) {
                    addItem(console, todos);
                    printTodos(todos);
                } else if (choice.equalsIgnoreCase("M")) {
                    markItemAsDone(console, todos, completedTasks);
                    printTodos(todos);
                } else if (choice.equalsIgnoreCase("L")) {
                    loadItems(console, todos);
                    printTodos(todos);
                } else if (choice.equalsIgnoreCase("S")) {
                    saveItems(console, todos);
                    printTodos(todos);
                } else if (!choice.equalsIgnoreCase("Q")) {
                    System.out.println("Unknown input: " + choice);
                    printTodos(todos);
                }
            }
        }
    }

    //Behavior:
    //  - prints the to do list for the user to see 
    //Parameters:
    //  - todos: an array list with the to do list items in it
    public static void printTodos(List<String> todos) {
        System.out.println("Today's TODOs:");
        if (todos.size() == 0) {
            System.out.println("  You have nothing to do yet today! Relax!");
        } else {
            for(int i = 0; i < todos.size(); i++) {
                System.out.println("  " + (i + 1) + ": " + todos.get(i));
            }
        }
    }

    //Behavior:
    //  - allows the user to add multiple tasks at once 
    //Exceptions:
    //  - assumes user will enter an int in response to the number of tasks they would
    //    like to add
    //Parameters:
    //  - console: a scanner that takes user input
    //  - todos: an array list with the to do list items in it
    public static void addMultipleItems(Scanner console, List<String> todos) {
        System.out.print("How many tasks would you like to add?: ");
        int numTasks = Integer.parseInt(console.nextLine());
        
        for (int i = 0; i < numTasks; i++) {
            System.out.print("Task " + (i + 1) + ": ");
            String multTask = console.nextLine();
            todos.add(multTask);
        }
    }

    //Behavior: 
    //  - adds one item to the to do list, position it is added is based on
    //    user input
    //Parameters:
    //  - console: a scanner that takes user input
    //  - todos: an array list with the to do list items in it
    public static void addItem(Scanner console, List<String> todos) {      
        System.out.print("What would you like to add? ");
        String task = console.nextLine();

        if (todos.size() == 0) { 
                todos.add(task);
        } else {
            System.out.print("Where in the list should it be (1-"
                + ((todos.size()) + 1) + ")? (Enter for end): ");
            String index = console.nextLine();
            if (index.equals("")) {
                todos.add(task);
            } else {
                int numIndex = Integer.parseInt(index) - 1;
                todos.add(numIndex, task);
            }
        }
    }

    //Behavior:
    //  - allows user to mark items as done from the to do list, removing them from
    //    the list. If the EXTENSION_FLAG = true, then it will also print a list of 
    //    the completed tasks.
    //Parameters:
    //  - console: a scanner that takes user input
    //  - todos: an array list with the to do list items in it
    //  - completedTasks: an array list with the tasks marked as complete in it
    public static void markItemAsDone(Scanner console, List<String> todos, 
                                        List<String> completedTasks) {
        if (EXTENSION_FLAG) {
            if (todos.size() == 0) {
                System.out.println("All done! Nothing left to mark as done!");
            } else {
                System.out.print("Which item did you complete (1-" + (todos.size()) + ")? ");
                int index = Integer.parseInt(console.nextLine()) - 1;
                String completedTask = todos.remove(index);
                completedTasks.add(completedTask);
                
                System.out.println("Completed TODOs:");
                if (completedTasks.size() == 0) {
                    System.out.println("  You haven't finished anything yet, get working!");
                } else {
                    for(int i = 0; i < completedTasks.size(); i++) {
                        System.out.println("  " + (i + 1) + ": " + completedTasks.get(i));
                    }
                }
            }

        } else {
            if (todos.size() == 0) {
                System.out.println("All done! Nothing left to mark as done!");
            } else {
                System.out.print("Which item did you complete (1-" + (todos.size()) + ")? ");
                int index = Integer.parseInt(console.nextLine()) - 1;
                String completedTask = todos.remove(index);
                completedTasks.add(completedTask);
            } 
        }

    }

    //Behavior:
    //  - Removes all the items in the to do list and replaces them with tasks 
    //    from a user-inputted file
    //Exceptions:
    //  - FileNotFoundException: allows for file to be used in method
    //Parameters:
    //  - console: a scanner that takes user input
    //  - todos: an array list with the to do list items in it
    public static void loadItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        File file = new File(fileName);
        Scanner fileScan = new Scanner(file);

        for (int i = todos.size() - 1; i >= 0; i--) {
            todos.remove(i);
        }

        while(fileScan.hasNextLine()) {
            String task = fileScan.nextLine();
            todos.add(task);
        }
    }

    //Behavior:
    //  - saves the to do list items to a file
    //Exceptions:
    //  - FileNotFoundException: allows for file to be used in method
    //Parameters:
    //  - console: a scanner that takes user input
    //  - todos: an array list with the to do list items in it
    public static void saveItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();

        File outFile = new File(fileName);
        PrintStream output = new PrintStream(outFile);

        if (todos.size() == 0) {
            output.println("You have nothing to do yet today! Relax!");
        } else {
            for(int i = 0; i < todos.size(); i++) {
                output.println(todos.get(i));
            }
        }
    }


}
