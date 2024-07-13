import java.util.Scanner;

public class UI {
    public static void mainMenu(Scanner input) {
        System.out.println("Here is the main menu: ");
        System.out.println("1\t to see the full list of medias" +
                "2\t to add a media\n" +
                "3\t to edit a media\n" +
                "4\t to delete a media\n" +
                "5\t to search for a media\n" +
                "6\t to borrow a media\n" +
                "7\t to return to the main menu\n"+
                "8\t to end the program: ");
        UserInput.getIntInput("choose an option from the main menu: ", "wrong input, you should only use an integer between 1 and 8", 1, 8);
        int choice = input.nextInt();

        switch (choice){
            case 1:
                System.out.println("you chose the option: see the full list of medias.");
                break;
            case 2:
                System.out.println("you chose the option: add a media.");
                break;
            case 3:
                System.out.println("you chose the option: edit a media.");
                break;
            case 4:
                System.out.println("you chose the option: delete a media.");
                break;
            case 5:
                System.out.println("you chose the option: search for a media.");
                break;
            case 6:
                System.out.println("you chose the option: borrow a media.");
                break;
            case 7:
                System.out.println("returning to the main menu.");
                mainMenu(input);
                break;
            case 8:
                System.out.println("you chose the option: end the program.");
                System.exit(0);
                break;
            default:
                break;
        }

    }
}
