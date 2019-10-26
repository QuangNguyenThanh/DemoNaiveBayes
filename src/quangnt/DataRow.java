package quangnt;

/**
 * @author QuangNT
 * The Class DataRow.
 */
public class DataRow {

    /** The document. */
    String document;
    
    /** The content. */
    String content;
    
    /** The type. */
    String type;

    /**
     * Instantiates a new data row.
     *
     * @param document the document
     * @param content the content
     * @param type the type
     */
    public DataRow(String document, String content, String type) {
        this.document = document;
        this.content = content;
        this.type = type;
    }

    /**
     * Instantiates a new data row.
     *
     * @param document the document
     * @param content the content
     */
    public DataRow(String document, String content) {
        this.document = document;
        this.content = content;
    }
}
