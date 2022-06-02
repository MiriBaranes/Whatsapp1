public class ParserPhone {
    public static final String START_PHONE_NUMBER = "05";
    private String phoneNumber;
    private static final String VALID_MESSAGE = "Valid!";
    public ParserPhone(String phoneNumber) {
        this.phoneNumber=phoneNumber;
    }
    protected boolean checkInput() {
        boolean isProperPhoneNumber=false;
        if (phoneNumber.length()>=10) {
            String start = phoneNumber.substring(0, 2);
            if (start.equals(START_PHONE_NUMBER)) {
                if (phoneNumber.length() == 10) {
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
    public String getRequest (){
        String caseValid = VALID_MESSAGE;
        if (phoneNumber.length() == 0) {
            caseValid = "empty input";
        } else if (!checkInput()) {
            caseValid = "not valid! only 10 digits!";
        }
        return caseValid;
    }
}