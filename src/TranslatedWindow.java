import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.Objects;
public class TranslatedWindow extends JFrame{
    private JPanel translatedPanel;
    private JTextField tfOrigText;
    private JTextField tfTransText;
    private JLabel lbClose;
    int pX, pY;
    TranslatedWindow(String origText, String transText) throws IOException, FontFormatException {
        //Font Settings
        Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("NotoSans-Regular.ttf")));
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(font);
        font = new Font("NotoSans-Regular",Font.PLAIN,15);
        //JFrame Settings
        setTitle("DeepL Translation");
        setContentPane(translatedPanel);
        setUndecorated(true);
        setMinimumSize(new Dimension(460, 235));
        setSize(460, 235);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/yae_128px.png")));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //Text
        tfOrigText.setFont(font);
        tfTransText.setFont(font);
//        tfOrigText.setText("愛されたいと思っています。");
//        tfTransText.setText("I want to be loved.");
        tfOrigText.setText(origText);
        tfTransText.setText(transText);
        tfOrigText.setEditable(false);
        tfTransText.setEditable(false);
        //lbClose to Close with change icon
        lbClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons8_cancel_50px_1.png")));
                lbClose.setIcon(icon);
            }
        });
        lbClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons8_cancel_50px_1.png")));
                lbClose.setIcon(icon);
            }
        });
        lbClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons8_cancel_50px.png")));
                lbClose.setIcon(icon);
            }
        });
        lbClose.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                dispose();
            }
        });
        //Draggable
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                // Get x,y and store them
                pX = me.getX();
                pY = me.getY();
            }
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,
                        getLocation().y + me.getY() - pY);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,
                        getLocation().y + me.getY() - pY);
            }
        });
    }

    //Testing purposes
//    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, FontFormatException {
//        String className = getLookAndFeelClassName("Windows");
//        UIManager.setLookAndFeel(className);
//        new TranslatedWindow();
//    }
//    //Set Look and Feel
//    public static String getLookAndFeelClassName(String nameSnippet) {
//        UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
//        for (UIManager.LookAndFeelInfo info : plafs) {
//            if (info.getName().contains(nameSnippet)) {
//                return info.getClassName();
//            }
//        }
//        return null;
//    }
}
