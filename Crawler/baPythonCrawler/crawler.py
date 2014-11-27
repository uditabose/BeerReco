from lxml import html
import urllib2

# header to emulate a browser
hdr = {'Accept-Language': 'en-US,en;q=0.8', 'Accept-Encoding': 'none', 'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', 
'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11', 
'Accept-Charset': 'ISO-8859-1,utf-8;q=0.7,*;q=0.3', 'Connection': 'keep-alive'}

baseurl = "http://www.beeradvocate.com"

# String Constants
beerlistFileName = "beerlinks.txt"
beerMetaDataFileName = "beerData.txt"
brewryMetaDataFileName = "brewryData.txt"

# id constants
beerId = 0
brewryId = 0

# global objects
failedUrlList = []
beerLinkList = []
beerIdDict = {}
brewryIdDict = {}
beerMetaDataDict = {}
brewryMetaDataDict = {}

# files
beerMetaData = open(beerMetaDataFileName,"w")
brewryMetaData = open(brewryMetaDataFileName,"w")

def readBeerList():
  f = open(beerlistFileName,'r')
  for line in f:
    url = line[:line.find("?")]
    beerLinkList.append(url)

def crawlBeer():
  for url in beerLinkList:
    htmlcode = openAndGetHtml(url)
    if htmlcode is not None:
      doc = html.fromstring(htmlcode)
      findBeerData(doc)
      crawlReviews(doc)

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

def getBeerMetaData(doc, beerName, brewryName):
  dic = {}
  elem = doc.cssselect("#baContent>table>tr>td:nth-child(2)>table>tr:nth-child(2)>td")
  vals = elem[0].text_content().strip().split("|")
  dic["style"] = vals[1][vals[1].rfind("\n")+1:].strip()
  dic["ABV"] = vals[2][:vals[2].find("%")+1].strip()
  dic["brewry"] = brewryName
  beerMetaDataDict[beerName] = dic
  print beerMetaDataDict

def openAndGetHtml(url):
  try:
    req = urllib2.Request(url,headers = hdr)
    htmlcode = urllib2.urlopen(req)
  except URLError:
    failedUrlList.append(url)
    return None
  return htmlcode.read()

def getNextPageLink(doc):
  elem = doc.cssselect("#baContent>div:nth-last-child(1)>:nth-last-child(1)")
  if elem[0].tag == "a":
    print "getting next page"
    link = baseurl + elem[0].get("href")
    return link
  else:
    return None

def crawlReviews(doc):
  link = getNextPageLink(doc)
  if link is not None:
    reviews = doc.cssselect("#rating_fullview_container")
    for review in reviews:
      collectReviewData(review)



def main():
  readBeerList()
  crawlBeer()

if __name__ == '__main__':
  main()
