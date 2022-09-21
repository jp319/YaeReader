
public class settings {
    private String modelPath;
    private String capturePath;
    private String settingsPath;
    private String mangaOCRPath;
    private String translatorPath;
    private String translatorDataPath;
    public settings() {}
    public settings(String modelPath, String capturePath, String settingsPath, String mangaOCRPath,
                    String translatorPath, String translatorDataPath) {
        this.modelPath = modelPath;
        this.capturePath = capturePath;
        this.settingsPath = settingsPath;
        this.mangaOCRPath = mangaOCRPath;
        this.translatorPath = translatorPath;
        this.translatorDataPath = translatorDataPath;
    }
    public String getModelPath() {
        return modelPath;
    }
    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }
    public String getCapturePath() {
        return capturePath;
    }
    public void setCapturePath(String capturePath) {
        this.capturePath = capturePath;
    }
    public String getSettingsPath() {
        return settingsPath;
    }
    public void setSettingsPath(String settingsPath) {
        this.settingsPath = settingsPath;
    }
    public String getMangaOCRPath() {
        return mangaOCRPath;
    }
    public void setMangaOCRPath(String mangaOCRPath) {
        this.mangaOCRPath = mangaOCRPath;
    }
    public String getTranslatorPath() {
        return translatorPath;
    }
    public void setTranslatorPath(String translatorPath) {
        this.translatorPath = translatorPath;
    }
    public String getTranslatorDataPath() {
        return translatorDataPath;
    }
    public void setTranslatorDataPath(String translatorDataPath) {
        this.translatorDataPath = translatorDataPath;
    }
}
