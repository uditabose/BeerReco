import json

inp = open("beerData.txt", "r")
inp2 = open("beerIdData.txt", "r")
inp3 = open("brewryIdData.txt", "r")

metadict = json.loads(inp.readline())
iddatadict = json.loads(inp2.readline())
brewiddatadict = json.loads(inp3.readline())

op = open("beermetadata.txt","w")
#header = unicode("id, style, brewry, abv")

for key in metadict:
  id = iddatadict[key]
  data = metadict[key]
  if data["ABV"].find("%") is not -1:
    data["ABV"] = data["ABV"][:-1]
  else:
    print "%s\n"%data["ABV"]
  if data["brewry"] not in brewiddatadict:
    print "data not found %s" % data["brewry"]
  datastr = unicode("%s^%s^%s^%s\n" %(str(id), data["style"], brewiddatadict[data["brewry"]], data["ABV"]))
  op.write(datastr.encode("UTF-8"))

inp.close()
inp2.close()
inp3.close()
op.close()

