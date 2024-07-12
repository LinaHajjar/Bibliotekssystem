public class Book extends Media{
    String author;
    String isbn;
    public Book(){}

    public  Book(String title, int year, int copies, String author, String isbn){
        super(title, year, copies);
        this.author=author;
        this.isbn=isbn;
    }
}
