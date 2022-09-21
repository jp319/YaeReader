import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Capture {

    private static String capturedImage;
    private static BufferedImage croppedImage;
    private static BufferedImage image;
    private static Rectangle cropSelection;
    private static String modPath;
    private static String OCRPath;
    private static String translatorPath;
    private static String translatedDataPath;
    private String extractedText;
    private String translatedText;
    public Capture(String imagePath, String modelPath, String mangaOCRPath, String translatorPath,
                   String translatedDataPath) {

        //Set paths
        capturedImage = imagePath;
        modPath = modelPath;
        OCRPath = mangaOCRPath;

        //Settings JFrame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setIconImage(transparentIcon());

        final BufferedImage original = screenshot();
        final BufferedImage copy = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        final JLabel screenLabel = new JLabel(new ImageIcon(copy));
        frame.add(screenLabel);
        repaint(original, copy);
        screenLabel.repaint();

        screenLabel.addMouseMotionListener(new MouseMotionAdapter() {
            Point start	= new Point();
            @Override
            public void	mouseMoved(MouseEvent me) {
                start = me.getPoint();
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                repaint(original, copy);
                screenLabel.repaint();
            }
            @Override
            public void	mouseDragged(MouseEvent	me) {
                Point end	= me.getPoint();
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                cropSelection = new Rectangle(start, new Dimension(end.x - start.x, end.y - start.y));
                repaint(original, copy);
                screenLabel.repaint();
            }
        });

        screenLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                if(cropSelection == null) {
                    return;
                }
                croppedImage = cropImage(original, cropSelection);
                try {
                    // Implement this further
                    File file = new File(capturedImage);

                    ImageIO.write(croppedImage, "png", file);
                } catch(final Exception ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
                cropSelection = null;
            }
        });

        //TODO Pass to mangaOCR
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setExtractedText(ExtractingText(modPath,capturedImage,OCRPath));
//                Translator translate = new Translator();
//                translate.translateText(extractedText);
                try {
                    setTranslatedText(TranslatorPythonTest.runTanslator(extractedText, translatorPath, translatedDataPath));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    new TranslatedWindow(getExtractedText(),getTranslatedText());
                } catch (IOException | FontFormatException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Raw Text : " + getExtractedText());
                System.out.println("Translate: " + getTranslatedText());
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
    public String ExtractingText(String mPath, String cPath, String ocrPath) {
        RunOCR runOCR = new RunOCR();
        try {
            extractedText = runOCR.runOCR(mPath,cPath,ocrPath);
            System.out.println("Extracted Text : " + extractedText);
        } catch (IOException | UnsupportedFlavorException ex) {
            throw new RuntimeException(ex);
        }
        return extractedText;
    }
    public String getExtractedText(){
        return extractedText;
    }
    public String getTranslatedText() {
        return translatedText;
    }
    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }
    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    private void repaint(BufferedImage	original, BufferedImage copy) {
        Graphics2D(original, copy, cropSelection);
    }
    static void Graphics2D(BufferedImage original, BufferedImage copy, Rectangle cropSelection) {
        Graphics2D g = copy.createGraphics();
        g.drawImage(original, 0, 0, null);
        g.setColor(Color.GRAY);
        if (cropSelection == null)return;
        else {
            g.draw(cropSelection);
            g.setColor(new Color(25, 25, 25, 50));
            g.fill(cropSelection);
            g.dispose();
        }
    }
    private BufferedImage screenshot() {
        try {
            Robot bot = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            image = bot.createScreenCapture(new Rectangle(screenSize));
        } catch(AWTException e) {
            e.printStackTrace();
        }
        return image;
    }
    private BufferedImage cropImage(BufferedImage source, Rectangle rectangle) {
        return source.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    private BufferedImage transparentIcon() {
        BufferedImage singlePixelImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Color transparent = new Color(0, 0, 0, 0);
        singlePixelImage.setRGB(0, 0, transparent.getRGB());
        return singlePixelImage;
    }
}