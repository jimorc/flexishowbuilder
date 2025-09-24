package flexishowbuilder;

import java.util.List;

enum CSVExceptionType {
    NOHEADER,
    INVALIDHEADER,
    EMPTY,
    MISSINGIMAGES,
    UNKNOWN
}

public class CSVException extends Exception {
    private CSVExceptionType type;
    private String fileName;
    private List<String> missingImages;
    public CSVException(CSVExceptionType type, String fileName, List<String> missingImages) {
        this.type = type;
        this.fileName = fileName;
        this.missingImages = missingImages;
    }

    @Override
    public String getMessage() {
        String message;
        switch (type) {
            case NOHEADER:
                message = "CSVException: No header found in CSV file " + fileName;
                break;
            case INVALIDHEADER:
                message = "CSVException: Invalid header found in CSV file " + fileName;
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
    
}
