import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class decryptionFile {

    public static void start()
    {

        String text = "";
        String text2 = "";

        //  get input from external file, using exception handling
        try (
                Scanner input = new Scanner(new File("ciphertext.txt"));
        ) {

            while (input.hasNext()) {
                text = input.nextLine().toLowerCase();
                text2 = input.nextLine().toLowerCase();

            }
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, but we couldn't find a file to run this app");
        } catch (InputMismatchException e) {
            System.out.println("Sorry, we could not read a number from the file");
        }

        boolean quit = false;

        // Greeting and entry screen

        // this is the major while loop which houses the menu
        while (quit != true) {


            System.out.println("\n\n\n\n                   ----------     ---  The Code Breaker App™  ---     ----------");
            Scanner keyboard = new Scanner(System.in);
            String appEntry = "";
            int decipherChoice = 0;
            int menuChoice = 0;
            boolean sentinel = false;


            while (!(appEntry.equals("e")) && !(appEntry.equals("x"))) {
                System.out.println("\n\n                             Click E to enter    |    Click X to quit              ");


                appEntry = keyboard.nextLine();
                appEntry.toLowerCase();
            }

            // you will enter here if you choose to enter the app
            if (appEntry.equals("e")) {

                while (sentinel == false) {

                    decipherChoice = decryptionFile.handlePlainTextOption();

                    // if you choose to view the answer to the first question you will enter here
                    if (decipherChoice == 1) {
                        // firstQuestion() houses the algorithm for the known caesar cipher
                        System.out.println("This is the answer to the first question : " + decryptionFile.firstQuestionReDone(text));
                        // handleMenu() shows and handles, and does the exception handling for the deepest menu
                        menuChoice = decryptionFile.handleMenu();

                       if (menuChoice == 2) {
                            // will take you back to the start of the app
                            sentinel = true;
                        } else if(menuChoice == 3) {
                            // will quit the app and end the program
                            sentinel = true;
                            quit = true;
                        }
                       // If 1 is clicked, the loop will simply repeat and you are presented with the 2 options

                    }
                    // if you choose to view the answer to the second question you will enter here
                    else {
                        // secondQuestion() houses the algorithm which brute forces the unknown caesar cipher
                        System.out.println("\nThis is the answer to the second question : " + decryptionFile.secondQuestion(text2));
                        menuChoice = decryptionFile.handleMenu();

                        if (menuChoice == 2) {
                            sentinel = true;
                        } else if (menuChoice == 3) {
                            sentinel = true;
                            quit = true;
                        }
                        // If 1 is clicked, the loop will simply repeat and you are presented with the 2 options


                    }
                }

            }
            // if you choose not to enter at the start, you will be taken here, and out of the menu system.
            else {
                quit = true;
            }

        }
    }
    public static int linearSearch(String[] alphabetArr, String key)
    {
        // This method returns the index of a given letter, from alphabet array
        for(int i=0;i<alphabetArr.length;i++){
            if(alphabetArr[i].equals(key)){
                return i;
            }
        }
        return -1;
    }

    public static String firstQuestionReDone (String text)
    {
        // reuses code from secondQuestion(), for details, look at secondQuestion()
        char[] decryption = text.toCharArray();
        System.out.println("\n\nQ1 : ");

        String[] Alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        // This algorithm searches from a-z, 23 shift forward = 3 shifts back
        int shift = 23;
        String plainText = "";
        int forwardShift;
        int backwardShift;

        for (int i = 0; i < decryption.length; i++)
        {

            char valAtIndex = decryption[i];
            String indexStringValue = Character.toString(valAtIndex);
            int alphabetArrPosition = linearSearch(Alphabet, indexStringValue);
            forwardShift = shift;
            backwardShift = Alphabet.length - shift;
            String nextLetter = "";

            if(alphabetArrPosition >= backwardShift)
            {
                nextLetter = Alphabet[alphabetArrPosition - backwardShift];
                plainText = plainText + nextLetter;
            }
            else if (valAtIndex != ' ')
            {
                nextLetter = Alphabet[alphabetArrPosition + forwardShift];
                plainText = plainText + nextLetter;
            }
            else
            {
                plainText = plainText + " ";
            }
        }
        String result = "PLAIN TEXT : " + plainText;
        return result;
    }




    public static String secondQuestion (String text2)
    {
        // turns the ciphertext into a character array
        char[] decryption2 = text2.toCharArray();
        System.out.println("\n\nQ2 : ");

        String[] Alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        int counter = 1;
        int shift = 1;
        String plainText2 = "";
        int forwardShift;
        int backwardShift;
        String nextLetter = "";
        String result = "";

        // while loop which closes once the "done" keyword is found, or if the entire alphabet is iterated unsuccessfully
        while( !(plainText2.contains("done")) && counter < 26)
        {
            plainText2 = "";

            // This for loop processes the ciphertext
            for (int i = 0; i < decryption2.length; i++)
            {
                // here we are plucking out a letter from the ciphertext and turning it to a string
                char valAtIndex = decryption2[i];
                String indexStringValue = Character.toString(valAtIndex);

                // here we are finding the index position of the letter in the alphabet array
                int alphabetArrPosition = linearSearch(Alphabet, indexStringValue);

                // forward shift -> A->Z shifting  /  backward shift -> Z->A shifting
                forwardShift = shift;
                backwardShift = (Alphabet.length - 1) - shift;

                // if we're shifting a letter for example 20 times forward, but the letter is z
                // we instead are going to use backwardShift (26 - # of forward shift)
                //to stay within the confines of the alphabet array
                if(alphabetArrPosition >= backwardShift)
                {
                    nextLetter = Alphabet[alphabetArrPosition - backwardShift];
                    plainText2 = plainText2 + nextLetter;
                }
                // if the shifting will not cause a letter to go out of bounds in the alphabet array
                // and it is indeed a letter and not an empty space, it will be shifted A->Z
                else if (valAtIndex != ' ')
                {
                    nextLetter = Alphabet[alphabetArrPosition + (forwardShift + 1)];
                    plainText2 = plainText2 + nextLetter;
                }
                // This leaves blank spaces alone
                else
                {
                    plainText2 = plainText2 + " ";
                }
            }
            shift = shift + 1;
            counter++;
        }

        // if the whole array was iterated unsuccessfully, this error message will return
        if(counter == 26)
        {
            result = "\n\nSorry, we couldn't find what you were looking for :( ";
            return result;
        }
        // This is the successful return message
        else {
            result = "\n\n\nPLAIN TEXT : " + plainText2 +
                    "\nThe Key, if moving from A->Z :  " + counter +
                    "\nThe Key, if moving from Z->A: " + (Alphabet.length - counter) +
                    "\n";
            return result;
        }

    }

    public static int handleMenu ()
    {

        int menuChoice = 0;


        while(menuChoice != 1 && menuChoice!= 2 && menuChoice!= 3) {

            printMenuOptions();
            Scanner keyboard = new Scanner(System.in);
            try{menuChoice = keyboard.nextInt();}
            catch (InputMismatchException e) {
                System.out.println();
                keyboard.next();
            }

        }
        return menuChoice;
    }

    public static void printMenuOptions()
    {
        System.out.println("\nPlease enter 1 to return to options");
        System.out.println("Please enter 2 to return to start");
        System.out.println("Please enter 3 to quit the application\n");
    }

    public static void printPlainTextOptions()
    {
        System.out.println("\nPlease enter 1 to view how ciphertext with a key of 3 is cracked");
        System.out.println("Please enter 2 to view how ciphertext without a known key, was cracked using bruteforce\n");
    }

    public static int handlePlainTextOption()
    {
        int decipherChoice = 0;
        Scanner keyboard = new Scanner(System.in);

        while (decipherChoice != 1 && decipherChoice != 2) {
            printPlainTextOptions();
            try {
                decipherChoice = keyboard.nextInt();
            }
            catch(InputMismatchException e){
                System.out.println();
                keyboard.next();
            }
        }
        return decipherChoice;
    }



}
