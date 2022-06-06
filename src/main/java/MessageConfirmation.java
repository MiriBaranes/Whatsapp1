import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessageConfirmation extends BasicJPanel {
    private static final String MESSAGE_START = "Enter a valid Message WhatsApp and phone numbers split with ',' char,  ->";
    public static final int SIZE = 100;
    private static final String PHOTO_PATH="gallery_6154_large.jpg";
    private static final String SYSTEM_MESSAGE="Whatsaap, Integrity check";
    private boolean valid;
    private JTextField userTextPhoneNumber;
    private JTextField userTextMessage;
    private JLabel massageToUser;
    private final List<String> validPhoneNumbers;
    private final List<String> notValidPhoneNumbers;
    private String message;
    private Button button;

    public MessageConfirmation() {
        super(0, 0, Const.WINDOW_W, Const.WINDOW_H, PHOTO_PATH, SYSTEM_MESSAGE);
        valid = false;
        this.validPhoneNumbers = new LinkedList<>();
        this.notValidPhoneNumbers = new LinkedList<>();
        this.message = null;
        addButton();
        act();
    }

    public void act() {
        this.userTextPhoneNumber=addJTextField("Enter a phone numbers, split with','-----> ",SIZE*3);
        this.userTextMessage=addJTextFieldWithTitleBlowAnotherTextField(userTextPhoneNumber,"Enter a message to send---->",userTextPhoneNumber.getY()+userTextPhoneNumber.getHeight());
        this.massageToUser=addJLabel(MESSAGE_START,0,userTextPhoneNumber.getY()-userTextPhoneNumber.getHeight(),Const.WINDOW_W, userTextPhoneNumber.getHeight(), 20, Color.white);
//        this.userTextPhoneNumber = addTextField("", SIZE * 4, SIZE * 3, SIZE * 3, SIZE / 2);
//        this.userTextMessage = addTextFieldBelowAntherTextField(userTextPhoneNumber, null);
//        this.massageToUser = addJLabel(MESSAGE_START, 0, userTextPhoneNumber.getY() - userTextPhoneNumber.getHeight(), Const.WINDOW_W, userTextPhoneNumber.getHeight(), 20, Color.white);
    }

    public void addButton() {
        this.button = new Button("Click here to check valid");
        this.button.setBounds(0, Const.WINDOW_H - Const.SIZE, Const.BUTTON_W, Const.SIZE/2);
        button.addActionListener(e -> {
            validInput();
            if (isValid()) {
                button.setVisible(false);
                massageToUser.setText("Your sent " + (notValidPhoneNumbers.size() + validPhoneNumbers.size()) + "" +
                        " Phone numbers, " + validPhoneNumbers.size() + " are valid, do you wont to sent the message to this list ?");
                Button confirm = new Button("Confirm");
                confirm.setBounds(0, Const.WINDOW_H - 100, Const.BUTTON_W / 2, 50);
                confirm.addActionListener(e1 -> {
                    confirm.setVisible(false);
                    ArrayList<MessageWhatsapp> messageWhatsappList = new ArrayList<>();
                    for (String validPhoneNumber : validPhoneNumbers) {
                        messageWhatsappList.add(new MessageWhatsapp(validPhoneNumber, message));
                    }
                    StartSystemDriver mainDriver = new StartSystemDriver(messageWhatsappList);
                    MainStart mainStart = new MainStart(mainDriver);
                    (SwingUtilities.getAncestorOfClass(JFrame.class, this)).setVisible(false);
                    mainStart.setVisible(true);
                });
                Button cancel = new Button("Cancel");
                cancel.setBounds(confirm.getWidth(), Const.WINDOW_H - 100, Const.BUTTON_W / 2, 50);
                cancel.addActionListener(e1 -> {
                    button.setVisible(true);
                    cancel.setVisible(false);
                    confirm.setVisible(false);
                    this.validPhoneNumbers.clear();
                    this.notValidPhoneNumbers.clear();
                });
                this.add(cancel);
                this.add(confirm);
            }

        });
        this.add(button);
    }

    public void validInput() {
        if (userTextMessage.getText().equals("")) {
            massageToUser.setText("Empty input");
        } else {
            message = userTextMessage.getText();
        }
        String[] messages = userTextPhoneNumber.getText().split(",");
        for (String phone : messages) {
            if (new ParserPhone(phone).checkInput()) {
                validPhoneNumbers.add(phone);
            } else notValidPhoneNumbers.add(phone);
        }
        if (validPhoneNumbers.size() > 0 && message != null) {
            this.valid = true;
        }else{
            this.validPhoneNumbers.clear();
            this.notValidPhoneNumbers.clear();
        }
    }

    public boolean isValid() {
        return this.valid;
    }
}
