public class Magazine extends Media{
    int issueNumber;
    public Magazine(){}
    public Magazine(String title, int year, int copies, int issueNumber){
        super(title, year, copies);
        this.issueNumber=issueNumber;
    }

}
