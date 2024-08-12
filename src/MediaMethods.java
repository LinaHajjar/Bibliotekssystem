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

    public static void editMedia(Scanner input,ArrayList <Media>medias)throws IOException{
        System.out.println("here is the list of medias, enter the number of the associated media you want to borrow:");
        int count =1;
        for (Media m: medias){
            System.out.println(count+" "+m);
            count++;
        }
        int choice= input.nextInt();
        input.nextLine();
        Media media = medias.get(choice-1);

        if(media instanceof Book){
            Book mediaBook = (Book) media;
            editBook(input, mediaBook);
        }
        else if(media instanceof Magazine){
            Magazine mediaMagazine = (Magazine) media;
            editMagazine(input, mediaMagazine);
        }
        else if(media instanceof Newspaper){
            Newspaper mediaNewspaper = (Newspaper) media;
            editNewspaper(input, mediaNewspaper);
        }
        else{
            System.out.println("Something went wrong, media of unknown type");
        }
        FileHandler.writeToFile(medias);
    }

    public static void editBook(Scanner input, Book mediaBook){
        String answer;
        System.out.println(mediaBook);
        System.out.println("What would you like to change?");

        int choice=UserInput.getIntInput("1.\t Title: "+mediaBook.getTitle()+"\n"+
                "2.\t Author: "+mediaBook.getAuthor()+"\n"+
                "3.\t Year: "+mediaBook.getYear()+"\n"+
                "4.\t ISBN: "+mediaBook.getIsbn()+"\n", "you have entered a wrong number, just integers between 1 and 4 are allowed", 1, 4);

        switch(choice){
            case 1:
                System.out.println("Enter the new title");
                String s= input.nextLine();
                mediaBook.setTitle(s);
                System.out.println("the title is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editBook(input, mediaBook);
                }
                break;
            case 2:
                System.out.println("Enter the new author");
                String a= input.nextLine();
                mediaBook.setAuthor(a);
                System.out.println("the author is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editBook(input, mediaBook);
                }
                break;
            case 3:
                System.out.println("Enter the updated year");
                int y= input.nextInt();
                input.nextLine();
                mediaBook.setYear(y);
                System.out.println("the year is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editBook(input, mediaBook);
                }
                break;
            case 4:
                System.out.println("Enter the updated ISBN");
                String isbn= input.nextLine();
                mediaBook.setIsbn(isbn);
                System.out.println("the year is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editBook(input, mediaBook);
                }
                break;
            default:
                System.out.println("Invalid option, returning you the menu");
        }
    } // end of editBook
    public static void editMagazine(Scanner input, Magazine mediaMagazine){
        String answer;
        System.out.println(mediaMagazine);
        System.out.println("What would you like to change?");
        int choice = UserInput.getIntInput("1.\t Title: "+mediaMagazine.getTitle()+"\n"+
                "2.\t Year: "+mediaMagazine.getYear()+"\n"+
                "3.\t Issuenumber: "+mediaMagazine.getIssueNumber()+"\n","you have entered a wrong number, just integers between 1 and 3", 1, 3);

        switch(choice){
            case 1:
                System.out.println("Enter the new title");
                String s= input.nextLine();
                mediaMagazine.setTitle(s);
                System.out.println("the title is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editMagazine(input, mediaMagazine);
                }
                break;
            case 2:
                System.out.println("Enter the updated year");
                int y= input.nextInt();
                mediaMagazine.setYear(y);
                input.nextLine();
                System.out.println("the year is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editMagazine(input, mediaMagazine);
                }
                break;
            case 3:
                System.out.println("Enter the updated issuenumber");
                int issuenumber= input.nextInt();
                mediaMagazine.setIssueNumber(issuenumber);
                System.out.println("the issuenumber is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editMagazine(input, mediaMagazine);
                }
                break;
            default:
                System.out.println("Invalid option, returning you the menu");
        }
    } // end of editMagazine

    public static void editNewspaper(Scanner input, Newspaper mediaNewspaper){
        System.out.println(mediaNewspaper);
        String answer;
        System.out.println("What would you like to change?");
        int choice = UserInput.getIntInput("1.\t Title: "+mediaNewspaper.getTitle()+"\n"+
                "2.\t Year: "+mediaNewspaper.getYear()+"\n"+
                "3.\t Publisher: "+mediaNewspaper.getPublisher()+"\n", "you have entered a wrong number, just integers between 1 and 3 are accepted", 1, 3);
        switch(choice){
            case 1:
                System.out.println("Enter the new title");
                String s= input.nextLine();
                mediaNewspaper.setTitle(s);
                System.out.println("the title is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editNewspaper(input, mediaNewspaper);
                }
                break;
            case 2:
                System.out.println("Enter the updated year");
                int y= input.nextInt();
                mediaNewspaper.setYear(y);
                input.nextLine();
                System.out.println("the year is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editNewspaper(input, mediaNewspaper);
                }
                break;
            case 3:
                System.out.println("Enter the updated publisher");
                String publisher= input.nextLine();
                mediaNewspaper.setPublisher(publisher);
                System.out.println("the publisher is updated successfully");

                System.out.println("Do you want to change another option? yes/no");
                answer = input.nextLine();
                if (UserInput.containsIgnoreCase("yes", answer)){
                    editNewspaper(input, mediaNewspaper);
                }
                break;
            default:
                System.out.println("Invalid option, returning you the menu");
        }
    } // end of editNewspaper

    public static void deleteMedia(Scanner input, ArrayList<Media> medias)throws IOException{
        System.out.println("Here is the list of your medias: ");
        for (Media m: medias){
            System.out.println(m);
        }

        System.out.println("what is the title of the media you want to remove?");
        String mediaDelete= input.nextLine();

        ArrayList<Media> deleteMedia=new ArrayList<>();
        for (Media m: medias){
            if (UserInput.containsIgnoreCase(m.getTitle(),mediaDelete)){
                deleteMedia.add(m);          }
        }

        if(deleteMedia.isEmpty()){
            System.out.println("no matches found");
            return;
        }

        int i =0;
        System.out.println("Here are the matching medias: ");
        for (Media s: deleteMedia){
            System.out.println((i+1) + ": " +s.getTitle());
            i++;
        }

        if (deleteMedia.size()==1){
            System.out.println("Are you sure you want to delete the media? yes/ no");
            String answer= input.nextLine();
            if (UserInput.containsIgnoreCase("yes", answer)){
                medias.remove(deleteMedia.get(0));
                System.out.println("The media is successfully removed");
                System.out.println();
            }else {
                System.out.println("Returning to main menu:");
                UI.mainMenu(input, medias);
            }
        }else{
            System.out.println("choose the number of the media you want to delete:");
            int number=input.nextInt();
            input.nextLine();
            if (number >= 1 && number <= deleteMedia.size()) {
                medias.remove(deleteMedia.get(number - 1));
                System.out.println("The media is successfully removed");
                System.out.println();
            } else {
                System.out.println("Invalid choice.");
            }
        }

        System.out.println("Here is the list after you deleted the media:");
        for (Media m : medias) {
            System.out.println(m);
        }
        FileHandler.writeToFile(medias);

    }// end of deleteMedia
}
