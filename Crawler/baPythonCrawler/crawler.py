from lxml import html
import urllib2
from urllib2 import URLError
import pdb
import os

# header to emulate a browser
hdr = {'Accept-Language': 'en-US,en;q=0.8', 'Accept-Encoding': 'none', 'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', 
'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11', 
'Accept-Charset': 'ISO-8859-1,utf-8;q=0.7,*;q=0.3', 'Connection': 'keep-alive'}

baseurl = "http://www.beeradvocate.com"

# String Constants
beerlistFileName = "beerlinks.txt"
beerMetaDataFileName = "beerData.txt"
brewryMetaDataFileName = "brewryData.txt"
reviewDataFileName = "reviewData.txt"
logFileName = "log.txt"
beeridDataFileName = "beerIdData.txt"
brewryidDataFileName = "brewryIdData.txt"

# id constants
beerId = 0
brewryId = 0
reviewNum = 0
resetingReview = 0

# global objects
failedUrlList = []
beerLinkList = []
beerIdDict = {}
brewryIdDict = {}
beerMetaDataDict = {}
brewryMetaDataDict = {}

# files
beerMetaData = open(beerMetaDataFileName,"w")
beerIdData = open(beeridDataFileName,"w")
brewryIdData = open(brewryidDataFileName, "w")
brewryMetaData = open(brewryMetaDataFileName,"w")
beerReviewData = open(reviewDataFileName,"w")
logFile = open(logFileName,"w") 

def readBeerList():
  f = open(beerlistFileName,'r')
  for line in f:
    url = line[:line.find("?")]
    beerLinkList.append(url)

def crawlBeer():
  global resetingReview
  for url in beerLinkList:
    resetingReview = 0
    try:
      htmlcode = openAndGetHtml(url)
      if htmlcode is not None:
        doc = html.fromstring(htmlcode)
        beerName , brewryName = findBeerData(doc)
        crawlReviews(doc, beerName, brewryName)
    except:
      pass
  dictionaryDump()

def dictionaryDump():
  deleteMetaFiles()
  beerMetaData = open(beerMetaDataFileName,"w")
  beerIdData = open(beeridDataFileName,"w")
  brewryIdData = open(brewryidDataFileName, "w")
  brewryMetaData = open(brewryMetaDataFileName,"w")
  for key in beerMetaDataDict:
    beerMetaData.write("%s,%s\n" %(key, beerMetaDataDict[key]))
  beerMetaData.close()
  for key in brewryMetaDataDict:
    brewryMetaData.write("%s,%s\n" % (key, brewryMetaDataDict[key]))
  brewryMetaData.close()
  for key in beerIdDict:
    beerIdData.write("%s,%s\n" % (key, beerIdDict[key]))
  beerIdData.close()
  for key in brewryIdDict:
    brewryIdData.write("%s,%s\n" % (key, brewryIdDict[key]))
  brewryIdData.close()

def deleteMetaFiles():
  lst = [beerMetaDataFileName, beeridDataFileName, brewryidDataFileName, brewryidDataFileName]
  for filename in lst:
    if os.path.exists(filename):
      os.remove(filename)

def findBeerData(doc):
  global beerId
  global brewryId
  beerName = doc.cssselect(".titleBar > h1")
  beerName = beerName[0]
  beerName = beerName.text_content()
  beerName = beerName.split("-")
  brewryName = beerName[1].strip()
  beerName = beerName[0].strip()
  if beerName not in beerIdDict:
    beerId += 1
    beerIdDict[beerName] = beerId
    getBeerMetaData(doc, beerName, brewryName)
  if brewryName not in brewryIdDict:
    brewryId += 1
    brewryIdDict[brewryName] = brewryId
  return (beerName, brewryName)

def getBeerMetaData(doc, beerName, brewryName):
  dic = {}
  elem = doc.cssselect("#baContent>table>tr>td:nth-child(2)>table>tr:nth-child(2)>td")
  vals = elem[0].text_content().strip().split("|")
  dic["style"] = vals[1][vals[1].rfind("\n")+1:].strip()
  dic["ABV"] = vals[2][:vals[2].find("%")+1].strip()
  dic["brewry"] = brewryName
  beerMetaDataDict[beerName] = dic

def openAndGetHtml(url):
  try:
    req = urllib2.Request(url,headers = hdr)
    htmlcode = urllib2.urlopen(req)
  except URLError:
    print url+"\n"
    failedUrlList.append(url)
    return None
  return htmlcode.read()

def getNextPageLink(doc):
  elem = doc.cssselect("#baContent>div:nth-last-child(1)")
  if len(elem[0]) > 0:
    elem = elem[0].cssselect(":nth-last-child(1)")
  if len(elem) > 0 and elem[0].tag == "a":
    link = baseurl + elem[0].get("href")
    return link
  else:
    return None

def crawlReviews(doc, beerName, brewryName):
  link = ""
  while link is not None:
    reviews = doc.cssselect("#rating_fullview_container")
    for review in reviews:
      try:
        collectReviewData(review, beerName, brewryName)
      except:
        pass
    try:
      link = getNextPageLink(doc)
      if link is not None:
        doc = html.fromstring(openAndGetHtml(link))
    except:
      pass




def collectReviewData(review, beerName, brewryName):
  global reviewNum, resetingReview
  spanList = review.cssselect("span")
  score = spanList[0].text_content()
  rating = spanList[3].text_content()
  flag = not rating.find("|") == -1
  if flag:
    rating = rating.split("|")
    ratings = [r[r.find(":")+1:].strip() for r in rating]
  else:
    ratings = ["","","","",""]
  
  username = spanList[len(spanList)-1].text_content()
  username = username[:username.find(",")]
  # extract the text review
  textreview = review.text_content()
  temp = textreview.find("%")
  try:
    if temp is not -1:
      textreview = textreview[temp+1:]
      textreview = textreview[:textreview.find(username)]
      temp = textreview.find("|")
      if temp is not -1:
        ind = textreview.rfind(":")
        if ind is not -1:
          textreview[ind+1:]
  except:
    textreview = ""
    pass
  output = ""
  output = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n"%(str(beerIdDict[beerName]), str(brewryIdDict[brewryName]),score,ratings[0], ratings[1], ratings[2], ratings[3], ratings[4], username, textreview)
  beerReviewData.write(output)
  reviewNum += 1
  resetingReview += 1
  logFile.write("trc = %s, resetrc = %s, beerNum = %s , brewryNum = %s\n" % (str(reviewNum), str(resetingReview), str(beerId), str(brewryId)))
  if logFile.tell() > 5000000:
    logFile.truncate(0)
    logFile.seek(0)
    dictionaryDump()


def main():
  readBeerList()
  crawlBeer()

if __name__ == '__main__':
  main()
