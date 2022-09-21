import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
public class TranslatorPythonTest {
    public static void main(String[] args) throws IOException {
        String rawText = "愛されたいと思っています。";
        String transPath = "D:/Hacks/scanlation/mangaOCR/classes/deepl/deepl.py";
        String transDataPath = "D:/Hacks/scanlation/mangaOCR/classes/deepl/translation_data.json";

        String transText = runTanslator(rawText,transPath,transDataPath);
        System.out.println("Translated Text : " + transText);
    }
    public static String runTanslator(String textToBeTranslated, String translatorPath, String translatedDataPath) throws IOException {
        String translatedText;

        try {
            ProcessBuilder builder = new ProcessBuilder("python",
                    translatorPath,
                    textToBeTranslated);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String lines = null;
            while ((lines=reader.readLine())!=null) {
                System.out.println("lines : " + lines);
            }
            while ((lines=readers.readLine())!=null) {
                System.out.println("Error lines : " + lines);
            }
            //After this translation is finished
        } catch (Exception e) {
            e.printStackTrace();
        }
        translatedText = jsonToJava(translatedDataPath);
        return translatedText;
    }
    public static String jsonToJava(String translatedDataPath) throws IOException {
        //String to return
        String numberOneTranslatedText = null;
        JSONObject numberOneTransText;
        //Parse JSON
        String strJson = getJSONFromFile(translatedDataPath);
        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(strJson);
            JSONObject mainJsonObject = (JSONObject) object;
            /*** Result ***/
            JSONObject result = (JSONObject) mainJsonObject.get("result");
            /*** Translations ***/
            JSONArray jsonArrayTranslations = (JSONArray) result.get("translations");
            //Loop inside Array
            for (int i = 0; i < jsonArrayTranslations.size(); i++) {
                JSONObject translations = (JSONObject) jsonArrayTranslations.get(i);
                JSONArray translatedTextArray = (JSONArray) translations.get("beams");
                String quality = (String) translations.get("quality");
                System.out.println("Quality : " + quality);
                for (int j = 0; j < translatedTextArray.size(); j++) {
                    JSONObject translatedTexts = (JSONObject) translatedTextArray.get(j);
                    System.out.println("    Translation #" + (j+1));
                    Long num_symbols = (Long) translatedTexts.get("num_symbols");
                    System.out.println("        Number of symbols : " + num_symbols);
                    String postprocessed_sentence = (String) translatedTexts.get("postprocessed_sentence");
                    System.out.println("        Translated text   : " + postprocessed_sentence);
                    //Get specific translation which is Translation 1 or 0
                    numberOneTransText = (JSONObject) translatedTextArray.get(0);
                    numberOneTranslatedText = (String) numberOneTransText.get("postprocessed_sentence");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return numberOneTranslatedText;
    }
    public static String getJSONFromFile (String jsonPath) {
        //JSON parser
        String jsonText = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonPath));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonText += line + "\n";
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText;
    }
}
