public class ParserPhone {
    public static final String START_PHONE_NUMBER = "05";
    public static final int PHONE_LENGTH = 10;
    public static final int PHONE_STARTER_LENGTH = 2;
    private final String phoneNumber;
    private static final String VALID_MESSAGE = "Valid!";

    public ParserPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    protected boolean checkInput() {
        boolean isProperPhoneNumber = false;
        if (phoneNumber.length() >= PHONE_LENGTH) {
            String start = phoneNumber.substring(0, PHONE_STARTER_LENGTH);
            if (start.equals(START_PHONE_NUMBER)) {
                if (phoneNumber.length() == PHONE_LENGTH) {
                    isProperPhoneNumber = true;
                    for (int i = 0; i < phoneNumber.length(); i++) {
                        if (!Character.isDigit(phoneNumber.charAt(i))) {
                            isProperPhoneNumber = false;
                            break;
                        }
                    }
                }
            }
        }
        return isProperPhoneNumber;
    }

    public String getRequest() {
        String caseValid = VALID_MESSAGE;
        if (phoneNumber.length() == 0) {
            caseValid = "Empty input";
        } else if (!checkInput()) {
            caseValid = "Invalid input! Only 10 digits!";
        }
        return caseValid;
    }
}