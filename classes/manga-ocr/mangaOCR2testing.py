# To run Script use "py .\methods.mangaOCR.py"
import os
import pyperclip
import sys
from manga_ocr import MangaOcr

workingdirectory=os.getcwd()
print('Number of Arguments: ', len(sys.argv), 'arguments')
print('Argument List: ', str(sys.argv))
def run():
    modelPath='D:/Hacks/scanlation/mangaOCR/classes/model/manga-ocr-base'
    capturePath='D:/Hacks/scanlation/mangaOCR/classes/manga-ocr/test.png'
    mocr = MangaOcr( pretrained_model_name_or_path=modelPath, force_cpu=True)
    text = mocr(str(capturePath))

    print(text)
    pyperclip.copy(str(text))

run()

# mocr = MangaOcr( pretrained_model_name_or_path='D:/Hacks/scanlation/mangaOCR/classes/model/manga-ocr-base', force_cpu=True)
# text = mocr('D:/Hacks/scanlation/mangaOCR/classes/captured.png')


