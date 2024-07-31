public class Book extends Media{
    String author;
    String isbn;
    public Book(){}

    public  Book(String title, int year, int copies, String author, String isbn){
        super(title, year, copies);
        this.author=author;
        this.isbn=isbn;
    }

    public String toString(){
        return ("Book:\n" +"Title: "+title+"\nDate of issue: "+ year+ "\nTotal copies: "+copies+"\nAuthor: "+author+"\nISBN: "+isbn+"\n" );
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String toPrint(){
        return (title+" ; "+author+" ; "+year+" ; "+copies+" ; "+isbn+"\n");
    }

    public String getAuthor(){return author;}
    public String getIsbn(){return isbn;}

}

