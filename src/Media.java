public class Media {
    String title;
    int year; //udgivelsesår
    int copies;

    public Media(){}
    public Media(String title, int year, int copies){
        this.title=title;
        this.year=year;
        this.copies=copies;
    }

    public String toString(){
        return ("Title: " + title + "\nyear of isuue: " +year+"\nTotal copies: " + copies+"\n");
    }
}
