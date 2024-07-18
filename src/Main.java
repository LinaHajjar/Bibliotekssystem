import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input =new Scanner(System.in);
        ArrayList<Media> medias = FileHandler.readFromFile();

        /*for(Media m: medias){
            System.out.println(m);
        }*/
        UI.mainMenu(input, medias);
    }
}
