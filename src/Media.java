public class Media implements Comparable<Media>{
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

    public String getTitle() {
        return title;
    }
    public int getYear(){
        return year;
    }

    public int getCopies(){return copies;}
    public void setCopies(int copies){
        this.copies=copies;
    }
    public String toPrint(){
        return (title+" ; "+year+" ; "+copies+"\n");
    }
    public int compareTo (Media a){
        return (this.getYear() -a.getYear());
    }
}
