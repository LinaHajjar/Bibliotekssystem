public class Newspaper extends Media{
    String publisher;
    public Newspaper(){}
    public Newspaper(String title, int year, int copies,String publisher){
        super(title, year, copies);
        this.publisher=publisher;
    }

    public String toString(){
        return ("Newspaper:\n" +"Title: "+title+"\nDate of issue: "+ year+ "\nTotal copies: "+copies+"\nPublisher: "+publisher+"\n" );
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String toPrint(){
        return (title+" ; "+publisher+" ; "+year+" ; "+copies+"\n");
    }
}
