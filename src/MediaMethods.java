import java.io.File;
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

    public static ArrayList<Media>searchMedia(Scanner input, ArrayList<Media> medias){
        ArrayList<Media> matches = new ArrayList<>();
        System.out.println("What would you like to search for? ");
        String searchString = input.nextLine();
        String[] keywords = searchString.split(",\\s+|\\.");
        for (Media m : medias) {
            for (int i = 0; i < keywords.length; i++) {
                if (UserInput.containsIgnoreCase(m.getTitle(),keywords[i])) {
                    matches.add(m);
                    break;
                } else if (m instanceof Book) {
                    if(UserInput.containsIgnoreCase(((Book) m).author,keywords[i])) {
                        matches.add(m);
                        break;
                    }
                }
                else if (m instanceof Newspaper){
                    if (UserInput.containsIgnoreCase(((Newspaper) m).publisher,keywords[i])) {
                        matches.add(m);
                        break;
                    }
                }
            }
        }

        if(matches.isEmpty()){
            System.out.println("No matches found");
        }
        else {
            System.out.println("Matches found:");
            int i = 1;
            for (Media m : matches) {
                //System.out.println(m);
                System.out.println(i +" "+ m);
                i++;
            }
        }
        return matches;
    }//end of searchMedias

    public static void borrowMedia(Scanner input, ArrayList<Media>medias) throws IOException{
        ArrayList<Media> matches = searchMedia(input, medias);

        if (matches.size()==1){

            if (matches.getFirst().getCopies()<1) {
                System.out.println("no available copies");

            } else {
                System.out.println("are you sure that you will borrow this media: " + matches.get(0).getTitle() + " ? yes / no");
                String answer = input.nextLine();

                //input.nextLine();

                if (UserInput.containsIgnoreCase("yes", answer)){
                    int Nb= matches.getFirst().getCopies();
                    int indexMedia= medias.indexOf(matches.getFirst());
                    System.out.println(medias.get(indexMedia));
                    matches.getFirst().setCopies(Nb-1);

                    medias.get(indexMedia).setCopies(Nb-1);
                    System.out.println("you have now "+ medias.get(indexMedia).getCopies() + " copies of this media: " + medias.get(indexMedia).getTitle());
                }
            }

        } else {
            /*int i=1;
            for (Media m: matches){
                System.out.println(i +" "+ m);
                i++;
            }*/
            System.out.println("enter the number of the media you want to borrow: ");
            int choice = input.nextInt();
            input.nextLine();
            int newNb=matches.get(choice-1).getCopies();
            if (newNb<1){
                System.out.println("no available copies");
            } else {
                matches.get(choice - 1).setCopies(newNb - 1);
                int indexMedia = medias.indexOf(matches.get(choice - 1));
                medias.get(indexMedia).setCopies(newNb - 1);
                System.out.println("you have now "+ medias.get(indexMedia).getCopies() + " copies of this media: " + medias.get(indexMedia).getTitle());
            }
        }
        FileHandler.writeToFile(medias);
    }//end of borrowMedia

}
