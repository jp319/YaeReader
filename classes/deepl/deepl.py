# Working!!!!
import json
import os
import requests
import sys
import time

workingdirectory=os.getcwd()

url = "https://www2.deepl.com/jsonrpc"
text = sys.argv[1]
# text = "我輩は神である"
r = requests.post(
    url,
    json = {
        "jsonrpc":"2.0",
        "method": "LMT_handle_jobs",
        "params": {
            "jobs":[{
                "kind":"default",
                "raw_en_sentence": text,
                "raw_en_context_before":[],
                "raw_en_context_after":[],
                "preferred_num_beams":4,
                "quality":"fast"
            }],
            "lang":{
                "user_preferred_langs":["JA","EN"],
                "source_lang_user_selected":"auto",
                "target_lang":"EN"
            },
            "priority":-1,
            "commonJobParams":{},
            "timestamp": int(round(time.time() * 1000))
        },
        "id": 40890008
    }
)
print(r.json())
# Data to be written
dictionary = r.json()
# Serializing json
json_object = json.dumps(dictionary, indent=4)
# Writing to translation_data.json
with open("classes/deepl/translation_data.json", "w") as outfile:
    outfile.write(json_object)