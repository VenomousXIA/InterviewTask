package ejadaxmlparsertask;

/**
 *
 * @author sasuk
 */
public class XmlParser {

    private static XmlParser parser = null;

    private XmlParser() {
    }

    /**
     * If an instance of XmlParser was instantiated before return it otherwise
     * create a new instance (Singleton).
     *
     * @return XmlParser used to parse and validate the student data.
     */
    public static XmlParser getInstance() {
        if (parser == null) {
            parser = new XmlParser();
        }
        return parser;
    }

    /**
     * Parse the attribute given by removing trailing white spaces and XML tags
     * from the data provided.
     *
     * @param attribute
     * @return parsed data from XML to readable string.
     */
    public String parse(String attribute) {
        attribute = attribute.trim();
        int index = attribute.indexOf('>', 0);

        if (index != -1) {
            attribute = attribute.substring(index + 1, attribute.length()); // remove openning tag

            index = attribute.lastIndexOf('<');

            if (index != -1) {
                attribute = attribute.substring(0, index); // remove closing tag
            } else {
                return "";
            }
        } else {
            return "";
        }

        return attribute;
    }

    /**
     * Validates the student data.
     *
     * @param student Student to be validated not.
     * @return returns validation state of the student.
     */
    public boolean isValidStudent(Student student) {
        String ename = student.getEname();
        String aname = student.getAname();
        String email = student.getEmail();
        String phone = student.getPhone();
        String address = student.getAddress();

        if (!isValidEnglishName(ename)
                || !isValidArabicName(aname)
                || !isValidGmailAddress(email)
                || !isValidPhoneNumber(phone)
                || !isValidStreetAddress(address)) {
            student.setValid(false);
            return false;
        }

        student.setValid(true);
        return true;
    }

    /**
     * Gets the validation message of either success or failure of validation of
     * the student showing errors in validation (if any)
     *
     * @param student student to be inspected for validation.
     * @return validation message of the student validation status.
     */
    public String getValidationMessage(Student student) {
        String message;
        int id = student.getId();

        if (student.getValid()) {
            message = String.format("Student %d: valid:true, all validations have been passed", id);
        } else {
            String ename = student.getEname();
            String aname = student.getAname();
            String email = student.getEmail();
            String phone = student.getPhone();
            String address = student.getAddress();
            int errorsCount = 0;
            String errors = "";

            if (!isValidEnglishName(ename)) {
                errors += "English name";
                errorsCount++;
            }
            if (!isValidArabicName(aname)) {
                errors += errorsCount > 0 ? ", Arabic name" : "Arabic name";
                errorsCount++;
            }
            if (!isValidGmailAddress(email)) {
                errors += errorsCount > 0 ? ", email" : "email";
                errorsCount++;
            }
            if (!isValidPhoneNumber(phone)) {
                errors += errorsCount > 0 ? ", phone" : "phone";
                errorsCount++;
            }
            if (!isValidStreetAddress(address)) {
                errors += errorsCount > 0 ? ", address" : "address";
                errorsCount++;
            }

            message = errorsCount < 2
                    ? String.format("Student %d: valid: false, the student object contains invalid fields; ", id)
                    : String.format("Student %d: valid: false, > the student object contains invalid fields; ", id);
            message += errors;
        }
        return message;
    }

    private boolean isValidEnglishName(String attribute) {
        attribute = attribute.trim();
        if (attribute.isEmpty()) {
            return false;
        }

        for (Character ch : attribute.toCharArray()) {
            if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) && !Character.isWhitespace(ch)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidArabicName(String attribute) {
        attribute = attribute.trim();
        if (attribute.isEmpty()) {
            return false;
        }

        for (Character ch : attribute.toCharArray()) {
            if (Character.UnicodeBlock.of(ch) != Character.UnicodeBlock.ARABIC && !Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidGmailAddress(String attribute) {
        attribute = attribute.trim();
        if (attribute.isEmpty()) {
            return false;
        }

        // check if it has a domain
        int domainStartIndex = attribute.indexOf('@');

        if (domainStartIndex == -1) {
            return false;
        }

        // check if domain is correct
        String domain = attribute.substring(domainStartIndex, attribute.length());

        if (!domain.equals("@gmail.com")) {
            return false;
        }

        // check recipent naming using gmail rules
        // (obtained by actually trying to make a new account)
        String recipient = attribute.substring(0, domainStartIndex);

        // 1. must start with letter or number
        Character beginChar = recipient.charAt(0);
        Character endChar = recipient.charAt(recipient.length() - 1);

        if ((!Character.isLetter(beginChar) && !Character.isDigit(beginChar))
                || (!Character.isLetter(endChar) && !Character.isDigit(endChar))) {
            return false;
        }

        // 2. contain only letters or numbers or period
        // 3. no consecutive periods
        for (int i = 1; i < recipient.length() - 1; i++) {
            Character ch = recipient.charAt(i);

            if (!Character.isLetter(ch) && !Character.isDigit(ch)) {
                if (ch != '.' || recipient.charAt(i + 1) == '.') {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValidPhoneNumber(String attribute) {
        attribute = attribute.trim();
        if (attribute.isEmpty()) {
            return false;
        }

        // check if the phone number is a valid egyptian number
        // check if phone number is fully provided 11 in length without '+2'
        // or 13 with the '+2' code
        if (attribute.length() < 11 || attribute.length() > 13) {
            return false;
        }

        // check if the first character is not '+' or digit
        Character ch = attribute.charAt(0);
        if (ch != '+' && !Character.isDigit(ch)) {
            return false;
        }

        // check if the phone number contains anything other than digits
        for (int i = 1; i < attribute.length(); i++) {
            if (!Character.isDigit(attribute.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidStreetAddress(String attribute) {
        attribute = attribute.trim();
        if (attribute.isEmpty()) {
            return false;
        }

        // check if address contain any special characters
        for (Character ch : attribute.toCharArray()) {
            if (!Character.isLetter(ch) && !Character.isDigit(ch) && !Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }
}
