import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TranslatedWindow extends JFrame{
    private JPanel translatedPanel;
    private JTextField tfOrigText;
    private JTextField tfTransText;

    TranslatedWindow() throws IOException, FontFormatException {
        //Font Settings
        Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("NotoSans-Regular.ttf")));
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(font);
        font = new Font("NotoSans-Regular",Font.PLAIN,20);
        //JFrame Settings
        setTitle("DeepL Translation");
        setContentPane(translatedPanel);
        setUndecorated(true);
        setMinimumSize(new Dimension(870, 345));
        setSize(870, 345);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/yae_128px.png")));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //Text
        tfOrigText.setFont(font);
        tfTransText.setFont(font);
        tfOrigText.setText("愛されたいと思っています。");
        tfTransText.setText("I want to be loved.");
        tfOrigText.setEditable(false);
        tfTransText.setEditable(false);

    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, FontFormatException {
        String className = getLookAndFeelClassName("Windows");
        UIManager.setLookAndFeel(className);
        new TranslatedWindow();
    }
    //Set Look and Feel
    public static String getLookAndFeelClassName(String nameSnippet) {
        UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : plafs) {
            if (info.getName().contains(nameSnippet)) {
                return info.getClassName();
            }
        }
        return null;
    }
}
