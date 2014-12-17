import json

inp = open("brewryIdData.txt", "r")

op = open("brewryidname.txt","w")

iddict = json.loads(inp.readline())

for key in iddict:
  datastr = unicode("%s^%s\n"%(key, iddict[key]))
  op.write(datastr.encode("UTF-8"))
inp.close()
op.close()