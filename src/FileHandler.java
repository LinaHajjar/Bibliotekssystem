import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    public static ArrayList<Media> readFromFile() throws FileNotFoundException {
        ArrayList<Media> listOfMedias = new ArrayList<>();
        Scanner scan = new Scanner(new File("src/Media"));

        //reading the books:

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.equals("Magazine Section")) {
                break;
            }
            Scanner lineScan = new Scanner(line);
            String title = "";
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                title += lineScan.next() + " ";
            }
            lineScan.next(); //consume

            String author = "";
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                author += lineScan.next() + " ";
            }
            lineScan.next();

            int year = 0;
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                year += lineScan.nextInt();
            }
            lineScan.next();

            int copies = 0;
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                copies += lineScan.nextInt();
            }
            lineScan.next();


            String ISBN ="";
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                ISBN = lineScan.nextLine().trim();
            }

            Book newBook = new Book(title, year, copies, author, ISBN);
            listOfMedias.add(newBook);
        }

        //reading the magazines
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.equals("Newspaper Section")) {
                break;
            }
            Scanner lineScan = new Scanner(line);
            String title = "";
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                title += lineScan.next() + " ";
            }
            lineScan.next();

            int year = 0;
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                year += lineScan.nextInt();
            }
            lineScan.next();

            int copies = 0;
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                copies += lineScan.nextInt();
            }
            lineScan.next();

            int issueNumber = 0;
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                issueNumber += lineScan.nextInt();
            }
            Magazine newMagazine = new Magazine(title.trim(), year, copies, issueNumber);
            listOfMedias.add(newMagazine);
        }

        //reading the newspapers
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.equals("End of file")) {
                break;
            }
            Scanner lineScan = new Scanner(line);
            String title = "";
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                title += lineScan.next() + " ";
            }
            lineScan.next();

            String publisher = "";
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                publisher += lineScan.next() + " ";
            }
            lineScan.next();

            int year = 0;
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                year += lineScan.nextInt();
            }
            lineScan.next();

            int copies = 0;
            while (lineScan.hasNext() && !lineScan.hasNext(";")) {
                copies += lineScan.nextInt();
            }
            Newspaper newNewspaper = new Newspaper(title.trim(), year, copies, publisher.trim());
            listOfMedias.add(newNewspaper);
        }
        return listOfMedias;

    }//end of readFromFile

}
