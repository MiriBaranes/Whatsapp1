import javax.swing.*;
import java.awt.*;

public class BasicJPanel extends JPanel {
    private ImageIcon backGround;
    private JLabel title;
    public BasicJPanel(int x, int y, int w, int h, Color color,String title){
        this.setBounds(x,y,w,h);
        this.setBackground(color);
        this.backGround=null;
        init();
    }
    public BasicJPanel(int x, int y, int w, int h,String fieldName,String title){
        this.setBounds(x,y,w,h);
        this.title=addJLabel(title,0,0,this.getWidth(),Const.SIZE,Const.SIZE/2,Color.blue.brighter());
        this.title.setOpaque(true);
        this.backGround=new ImageIcon(fieldName);
        init();
    }
    public BasicJPanel(int x, int y, int w, int h,String fieldName){
        this.setBounds(x,y,w,h);
        this.backGround=new ImageIcon(fieldName);
        init();
    }
    public JTextField addJTextField(String title, int y) {
        JLabel jLabel = addJLabel(title, 0, y, Const.BUTTON_W, Const.BUTTON_H, 15, Color.black);
        jLabel.setForeground(Color.green.darker());
        return addTextField("", jLabel.getX() + jLabel.getWidth() + 1, y, Const.BUTTON_W, Const.BUTTON_H);
    }
    public JTextField addJTextFieldWithTitleBlowAnotherTextField(JTextField textField,String title, int y) {
        JLabel jLabel = addJLabel(title, 0, y, Const.BUTTON_W, Const.BUTTON_H, 15, Color.black);
       jLabel.setForeground(Color.green.darker());
        return addTextFieldBelowAntherTextField(textField);
    }
    public void init(){
        this.setLayout(null);
        this.setDoubleBuffered(true);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.backGround!=null) {
            this.backGround.paintIcon(this, g, 0, 0);
        }
    }

    public JLabel addJLabel(String title, int x, int y, int w, int h, int size,Color color){
        JLabel jLabel=new JLabel(title,SwingConstants.CENTER);
        jLabel.setFont(new Font("ariel",Font.BOLD,size));
        jLabel.setForeground(color);
        jLabel.setBounds(x,y,w,h);
        this.add(jLabel);
        return jLabel;
    }
    public void setBackGround(String path){
        if (this.backGround!=null){
            backGround=new ImageIcon(path);
            repaint();
        }
    }
    public JLabel addLabelBelowAntherLabel(JLabel other,String string,int size){
        return addJLabel(string,other.getX(),other.getY()+other.getHeight(),other.getWidth(),other.getHeight(),size,other.getForeground());
    }
    public JLabel addLabelNextAntherLabel(JLabel other,String string,int size){
        return addJLabel(string,other.getX()+other.getWidth(),other.getY(),other.getWidth(),other.getHeight(),size,other
                .getForeground());
    }
    public JTextField addTextField(String title, int x, int y, int w,int h){
        JTextField text=new JTextField(title,SwingConstants.CENTER);
        text.setBounds(x,y,w,h);
        this.add(text);
        return text;
    }
    public JTextField addTextFieldBelowAntherTextField(JTextField other){
        return addTextField(null,other.getX(),other.getY()+other.getHeight(),other.getWidth(),other.getHeight());
    }
    public JTextField addTextFieldNextAntherTextField(TextField other,String string){
        return addTextField(string,other.getX()+other.getWidth(),other.getY(),other.getWidth(),other.getHeight());
    }




}
