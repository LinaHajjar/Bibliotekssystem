import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    public static void mainMenu(Scanner input, ArrayList<Media>medias)throws IOException {
        System.out.println("Here is the main menu: ");
        System.out.println("1\t See the full list of medias\n" +
                "2\t Add a media\n" +
                "3\t Edit a media\n" +
                "4\t Delete a media\n" +
                "5\t Search for a media\n" +
                "6\t Borrow a media\n" +
                "7\t Return to the main menu\n"+
                "8\t End the program.\n");
        int choice =UserInput.getIntInput("Choose an option from the main menu:\n", "wrong input, you should only use an integer between 1 and 8", 1, 8);

        switch (choice){
            case 1: // done
                System.out.println("you chose the option: see the full list of medias.");
                MediaMethods.sortMedia(input, medias);
                mainMenu(input,medias);
                break;
            case 2: // done
                System.out.println("you chose the option: add a media.");
                MediaMethods.addMedia(input, medias);
                mainMenu(input,medias);
                break;
            case 3:
                System.out.println("you chose the option: edit a media.");
                MediaMethods.editMedia(input,medias);
                mainMenu(input,medias);
                break;
            case 4:
                System.out.println("you chose the option: delete a media.");
                break;
            case 5:
                System.out.println("you chose the option: search for a media.");
                break;
            case 6: // done
                System.out.println("you chose the option: borrow a media.");
                MediaMethods.borrowMedia(input, medias);
                mainMenu(input,medias);
                break;
            case 7: // done
                System.out.println("returning to the main menu.");
                mainMenu(input,medias);
                break;
            case 8: //done
                System.out.println("you chose the option: end the program.");
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
