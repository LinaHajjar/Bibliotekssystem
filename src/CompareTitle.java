import java.util.Comparator;
    public class CompareTitle implements Comparator<Media> {
        public int compare (Media a, Media b){
            return (a.getTitle().compareToIgnoreCase(b.getTitle()));
        }
    }
