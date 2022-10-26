#ler texto em json
#extrair dados
#salvar em json

from email.quoprimime import quote
import json
from pprint import pprint

dataset = json.load(open("info.json")) 

movies_set = []
quotes = []
movies_data = []

#type,language,source,quote,author
for movie in dataset:
    source = movie["source"].strip()
    if source not in movies_set:
        movies_set.append(source)
        movies_data.append({"source":source,"type":movie["type"]})
    quotes.append({"show":{"id":movies_set.index(source)+288},"quote":movie["quote"], "author":movie["author"]})



json.dump(movies_data, open("movies.json","w"),indent=6)
json.dump(quotes, open("quotes.json","w"),indent=6)