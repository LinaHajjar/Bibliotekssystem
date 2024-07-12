public class Newspaper extends Media{
    String publisher;
    public Newspaper(){}
    public Newspaper(String title, int year, int copies,String publisher){
        super(title, year, copies);
        this.publisher=publisher;

    }
}
