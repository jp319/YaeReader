import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Translator {

    final String baseUrl = "https://www.deepl.com/";
    static String sourceLanguage = "translator#ja/";
    static String translationLanguage = "en/";

    public void translateText(String text) {
        try {
            textLinkCompatible(text);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(baseUrl + sourceLanguage + translationLanguage + text));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String textLinkCompatible(String text) {
        text = text.replace(" ","%20");
        return text;
    }

}
