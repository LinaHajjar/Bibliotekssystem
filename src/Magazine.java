public class Magazine extends Media{
    int issueNumber;
    public Magazine(){}
    public Magazine(String title, int year, int copies, int issueNumber){
        super(title, year, copies);
        this.issueNumber=issueNumber;
    }

    public String toString(){
        return ("Magazine:\n" +"Title: "+title+"\nDate of issue: "+ year+ "\nTotal copies: "+copies+"\nIssue number: "+issueNumber+"\n" );
    }
}
