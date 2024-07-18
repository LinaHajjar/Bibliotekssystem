import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MediaMethods {
    public static void sortMedia(Scanner input, ArrayList<Media> medias) throws IOException {
        String answer;
        int sortDecider = UserInput.getIntInput("How do you want the media sorted?:\n"+
                "1.\t Unsorted\n"+
                "2.\t Sorted by title\n"+
                "3.\t Sorted by year\n", "You've entered a non valid option, try again",1,3);
        switch(sortDecider)
        {
            case 1:
                for(Media m: medias){
                System.out.println(m);
                }
                System.out.println("Do you want another sorting? yes/no");
                answer=input.nextLine();
                if (UserInput.containsIgnoreCase("yes",answer)) {
                    sortMedia(input, medias);
                } else {
                    System.out.println("returning to the main menu:");
                    UI.mainMenu(input, medias);
                }
                break;
            case 2:
                Collections.sort(medias, new CompareTitle());
                for(Media m: medias){
                    System.out.println(m);
                }
                System.out.println("Do you want another sorting? yes/no");
                answer=input.nextLine();
                if (UserInput.containsIgnoreCase("yes",answer)) {
                    sortMedia(input, medias);
                } else {
                    System.out.println("returning to the main menu:");
                    UI.mainMenu(input, medias);
                }
                break;
            case 3:
                Collections.sort(medias);
                for(Media m: medias){
                    System.out.println(m);
                }
                answer=input.nextLine();
                if (UserInput.containsIgnoreCase("yes",answer)) {
                    sortMedia(input, medias);
                } else {
                    System.out.println("returning to the main menu:");
                    UI.mainMenu(input, medias);
                }
                break;
            default:
                System.out.println("You've entered a non valid option and will be shown the media unsorted");
        }// end switch
    }//end sortMedia


    public static void addMedia(Scanner input, ArrayList<Media>medias)throws IOException {
        int type= UserInput.getIntInput("what do you want to add?\n choose 1 for books, 2 for magazine, 3 for newspaper:\n", "wrong input. Choose only an integer between 1 and 3.\n",1,3);
        String title="";
        int year=0;
        int copies=0;
        Media media=null;
        switch (type){
            case 1: //books
                System.out.println("what is the name of the book? ");
                title+=input.nextLine();
                System.out.println("when was the book published? ");
                year+= input.nextInt();
                input.nextLine();
                System.out.println("how many copies of the book do you want to add? ");
                copies+= input.nextInt();
                input.nextLine();
                System.out.println("what is the name of the author");
                String author=input.nextLine();
                System.out.println("what is the ISBN:");
                String isbn= input.nextLine();
                media=new Book(title,year,copies,author,isbn);
                break;

            case 2: //magazine
                System.out.println("what is the title of the magasine: ");
                title = input.nextLine();
                System.out.println("when was the magasine issued");
                year = input.nextInt();
                input.nextLine();
                System.out.println("what is the issue number:");
                int issuenumber= input.nextInt();
                input.nextLine();
                media = new Magazine(title,year,copies,issuenumber);
                break;

            case 3: //newspaper
                System.out.println("what is the title of the newspaper: ");
                title = input.nextLine();
                System.out.println("when was the newspaper issued");
                year = input.nextInt();
                input.nextLine();
                System.out.println("what is the name of the publisher:");
                String publisher= input.nextLine();
                media = new Newspaper(title,year,copies,publisher);
                break;
            default:
                System.out.println("wrong input. Choose only an integer between 1 and 3.");
                break;
        }

        if(!checkIfExists(media,medias)) {
            medias.add(media);
            FileHandler.writeToFile(medias);
        }

        System.out.println("do you want to add more medias? yes / no");
        String answer= input.nextLine();
        if (UserInput.containsIgnoreCase("yes",answer)) {
            addMedia(input, medias);
        } else {
            System.out.println("saving the new media and returning to the main menu:");
            FileHandler.writeToFile(medias);
            UI.mainMenu(input, medias);
        }


    }// end of addMedia

    public static boolean checkIfExists(Media newMedia,ArrayList<Media>medias){
        boolean exists=false;
        int count = 0;
        for(Media m:medias){
            if(m.getTitle().equalsIgnoreCase(newMedia.getTitle()) && m.getYear()== newMedia.getYear()){
                count+=m.copies;
                m.setCopies(count+1);
                exists=true;
            }
        }
        return exists;
    }
}
