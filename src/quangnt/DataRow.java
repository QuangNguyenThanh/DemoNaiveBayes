package quangnt;

public class DataRow {

    String document;
    String content;
    String type;

    public DataRow(String document, String content, String type) {
        this.document = document;
        this.content = content;
        this.type = type;
    }

    public DataRow(String document, String content) {
        this.document = document;
        this.content = content;
    }
}
