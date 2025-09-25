package flexishowbuilder;

import java.util.List;

enum CSVExceptionType {
    NOFILE,
    INVALIDHEADER,
    INVALIDLINE,
    EMPTY,
    MISSINGIMAGES,
    UNKNOWN
}

public class CSVException extends Exception {
    private CSVExceptionType type;
    private int lineNumber;
    private String fileName;
    private List<String> missingImages;
    public CSVException(CSVExceptionType type, int line, String fileName, List<String> missingImages) {
        this.type = type;
        this.lineNumber = line;
        this.fileName = fileName;
        this.missingImages = missingImages;
    }

    @Override
    public String getMessage() {
        String message;
        switch (type) {
            case NOFILE:
                message = "CSVException: No CSV file selected.";
                break;
            case INVALIDHEADER:
                message = "CSVException: Invalid header found in CSV file " + fileName;
                break;
            case INVALIDLINE:
                message = "CSVException: Invalid line number " + (lineNumber+1) + " found in CSV file " + fileName;
                break;
            case EMPTY:
                message = "CSVException: No data found in CSV file " + fileName;
                break;
            case MISSINGIMAGES:
                message = "CSVException: Some images listed in CSV file " + fileName + " are missing:\n";
                for (String img : missingImages) {
                    message += img + "\n";
                }
                break;
            case UNKNOWN:
            default: // should never happen "but just in case".

                message = "CSVException: Unknown error with CSV file " + fileName;
        }   
        return message;
    }
    
    /*
     * Returns the type of CSV exception.
     * @return the type of CSV exception.
     */
    public CSVExceptionType getType() {
        return type;
    }
}
