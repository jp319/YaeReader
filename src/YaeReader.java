import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class YaeReader extends JFrame{
    private JTextField tfCapturePath;
    private JTextField tfModelPath;
    private JButton chooseCaptureDirButton;
    private JButton chooseModelDirButton;
    private JButton saveButton;
    private JPanel YaePanel;
    //Paths
    private String modelPath;
    private String capturePath;
    public static String settingsPath = "classes/settings/settings.xml";
    private String mangaOCR_PAth;
    //private final String mangaOCR_PAth = "D:\\Hacks\\scanlation\\mangaOCR\\classes\\manga-ocr\\mangaOCR2.py";
    private String translatorPath;
    private String translatorDataPath;
    public YaeReader() {
        //Check settings if Exist
        createIfNoExistSettings();
        //Set JFrame Properties
        setTitle("Yae Reader Settings");
        setContentPane(YaePanel);
        setMinimumSize(new Dimension(470, 325));
        setSize(470, 325);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/yae_128px.png")));
        tfCapturePath.setEditable(false);
        tfModelPath.setEditable(false);
        //Clean Paths
        capturePath = tfCapturePath.getText().replace("\\","/");
        modelPath = tfModelPath.getText().replace("\\","/");

        //Buttons
        //Choose where to save captures
        chooseCaptureDirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.setDialogTitle("Save Captures to");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //
                // disable the "All files" option.
                //
                chooser.setAcceptAllFileFilterUsed(false);
                //
                if (chooser.showOpenDialog(YaeReader.this) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): "
                            +  chooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : "
                            +  chooser.getSelectedFile());
                    capturePath = String.valueOf(chooser.getSelectedFile());
                    capturePath = capturePath.replace("\\", "/");
                    String optimizedCapturePath = capturePath+"/captured.png";
                    tfCapturePath.setText(optimizedCapturePath);
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });
        //Choose where to load Model
        chooseModelDirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.setDialogTitle("Load Models From");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //
                // disable the "All files" option.
                //
                chooser.setAcceptAllFileFilterUsed(false);
                //
                if (chooser.showOpenDialog(YaeReader.this) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): "
                            +  chooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : "
                            +  chooser.getSelectedFile());
                    modelPath = String.valueOf(chooser.getSelectedFile());
                    modelPath = modelPath.replace("\\", "/");
                    tfModelPath.setText(modelPath);
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });
        //Save DIR
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO Use save Function
                saveSettings(
                        modelPath.replace("\\","/"),
                        capturePath.replace("\\","/"),
                        settingsPath.replace("\\","/"),
                        mangaOCR_PAth.replace("\\","/"),
                        translatorPath.replace("\\","/"),
                        translatorDataPath.replace("\\","/")
                );
            }
        });

        //System Tray Settings
        if (SystemTray.isSupported()) {
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        }

        SystemTray systemTray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/yae_16px.png")));
        trayIcon.setToolTip("Yae Reader");
        PopupMenu popMenu = new PopupMenu();
        //Menu
        //Snip Screen Menu
        MenuItem snipScreen = new MenuItem("Snip Screen");
        //Function moved to bottom in order for notification to show up

        //Show Settings Menu
        MenuItem showSettings = new MenuItem("Settings");
        showSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
            }
        });
        //Exit
        MenuItem exit = new MenuItem("Exit Yae Reader");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Add PopMenu
        popMenu.add(snipScreen);
        popMenu.add(showSettings);
        popMenu.addSeparator();
        popMenu.add(exit);
        //Set Tray Icon
        trayIcon.setPopupMenu(popMenu);
        //Add Tray Icon
        try {
            systemTray.add(trayIcon);
        } catch (AWTException el) {
            el.printStackTrace();
        }
        //Snip screen function
        snipScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Capture Method Here
                String cleanCapturePath = tfCapturePath.getText().replace("\\","/");
                String cleanModelPath = tfModelPath.getText().replace("\\","/");
                String cleanOCRPath = mangaOCR_PAth.replace("\\","/");
                String cleanTranslatorPath = translatorPath.replace("\\", "/");
                String cleanTranslatorDataPath = translatorDataPath.replace("\\", "/");
                //Get Extracted Text
                Capture capt = new Capture(cleanCapturePath,cleanModelPath,cleanOCRPath,
                                            cleanTranslatorPath,cleanTranslatorDataPath);
                //Translate
//                Translator translate = new Translator();
//                translate.translateText(capt.getExtractedText());
                //Translate Python Method
                //Show translated window
                //Code here
                //TODO Show Tray Icon Message Extracting Text
                trayIcon.displayMessage("Translated","Extracted Text Translated", TrayIcon.MessageType.INFO);
            }
        });
        //TODO Make a translucent window for viewing translations
    }

    //Settings Methods
    //TODO Fix LoadSettings
    //FIXME Make LoadSettings Read XML
    //FIXME update The error came from XML file not in the code
    //FIXME Fixed XML File working fine
    //public static String settingsPath = "D:/Hacks/scanlation/mangaOCR/classes/settings/settings.xml";
    public void loadSettings() {
        try {
            FileInputStream fis = new FileInputStream(new File(settingsPath));
            XMLDecoder decoder = new XMLDecoder(fis);

            settings settings = (settings) decoder.readObject();
            decoder.close();
            fis.close();

            tfModelPath.setText(settings.getModelPath());
            tfCapturePath.setText(settings.getCapturePath());
            settingsPath = settings.getSettingsPath();
            mangaOCR_PAth = settings.getMangaOCRPath();
            translatorPath = settings.getTranslatorPath();
            translatorDataPath = settings.getTranslatorDataPath();

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void saveSettings(String modelPath, String capturePath, String settingsPath,
                             String mangaOCR_PAth, String translatorPath, String translatorDataPath) {
        settings saveSettings = new settings(modelPath, capturePath,
                settingsPath,mangaOCR_PAth,translatorPath,translatorDataPath);
        doSave(saveSettings);
        loadSettings();
    }
    private void doSave(settings saveSettings) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(settingsPath));
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(saveSettings);
            encoder.close();
            fos.close();
        } catch (IOException ec) {
            ec.printStackTrace();
        }
    }
    public void createIfNoExistSettings() {
        //Old condition
        File settingsFile = new File(settingsPath);
//        if (settingsFile.exists())
        //Check settings if exist
        if (settingsFile.isFile()) {
            System.out.println("File already exist");
            loadSettings();
        } else {
//            String modPath = "D:/Hacks/scanlation/mangaOCR/classes/model/manga-ocr-base";
//            String capPath = "D:/Hacks/scanlation/mangaOCR/classes/captured.png";
            String modPath = "classes/model/manga-ocr-base";
            String capPath = "classes/captured.png";
            String setPath = "classes/settings/settings.xml";
            String transPath = "classes/deepl/deepl.py";
            String transDataPath = "classes/deepl/translation_data.json";
            String ocrPath = "classes/manga-ocr/mangaOCR2.py";
            settings settings = new settings(modPath, capPath, setPath, ocrPath, transPath, transDataPath);
            doSave(settings);
            saveSettings(modPath,capPath, setPath,ocrPath,transPath,transDataPath);
            System.out.println("Created new file");
        }
    }
    //Run
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String className = getLookAndFeelClassName("Windows");
        UIManager.setLookAndFeel(className);
        new YaeReader();
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
