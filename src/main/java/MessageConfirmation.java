import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class MessageConfirmation extends BasicJPanel {
    private static final String MESSAGE_START = "Enter a valid MessageWhatsapp and phone numbers split with ',' char,  ->";
    public static final int SIZE = 100;
    private boolean valid;
    private JTextField userTextPhoneNumber;
    private JTextField userTextMessage;
    private JLabel massageToUser;
    private List<String> validPhoneNumbers;
    private List<String> notValidPhoneNumbers;
    private String message;
    private Button button;

    public MessageConfirmation() {
        super(0, 0, Const.WINDOW_W, Const.WINDOW_H, "gallery_6154_large.jpg", "Whatsaap, Integrity check");
        valid = false;
        this.validPhoneNumbers = new LinkedList<>();
        this.notValidPhoneNumbers = new LinkedList<>();
        this.message = null;
        addButton();
        act();
    }

    public void act() {
        this.userTextPhoneNumber = addTextField("", SIZE * 4, SIZE * 3, SIZE * 3, SIZE / 2);
        this.userTextMessage = addTextFieldBelowAntherTextField(userTextPhoneNumber, null);
        this.massageToUser = addJLabel(MESSAGE_START, 0, userTextPhoneNumber.getY() - userTextPhoneNumber.getHeight(), Const.WINDOW_W, userTextPhoneNumber.getHeight(), 20, Color.white);
    }

    public void addButton() {
        this.button = new Button("Click here to check valid");
        this.button.setBounds(0, Const.WINDOW_H - 100, Const.BUTTON_W, 50);
        button.addActionListener(e -> {
            validInput();
            if (isValid()) {
                button.setVisible(false);
                massageToUser.setText("Your sent " + (notValidPhoneNumbers.size() + validPhoneNumbers.size()) + "" +
                        ", " + validPhoneNumbers.size() + " are valid, do you wont to sent the message to this list ?");
                Button confirm = new Button("Confirm");
                confirm.setBounds(0, Const.WINDOW_H - 100, Const.BUTTON_W / 2, 50);
                confirm.addActionListener(e1 -> {
                    confirm.setVisible(false);
                    ArrayList<MessageWhatsapp> messageWhatsappList = new ArrayList<>();
                    for (int i = 0; i < validPhoneNumbers.size(); i++) {
                        messageWhatsappList.add(new MessageWhatsapp(validPhoneNumbers.get(i), message));
                    }
                    Start mainDriver = new Start(messageWhatsappList);
                    MainStart mainStart = new MainStart(mainDriver);
                    (SwingUtilities.getAncestorOfClass(JFrame.class, this)).setVisible(false);
                    mainStart.setVisible(true);
                });
                Button cancel = new Button("cancel");
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
        }
    }

    public boolean isValid() {
        return this.valid;
    }
}
