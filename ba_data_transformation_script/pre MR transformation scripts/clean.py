filename = "reviewData.txt"

inpfile = open(filename, "r")
outfile = open("processedReviews.txt","w")
def main():
  for line in inpfile:
    lst = line.split(",")
    if len(lst) < 10:
      print str(len(lst)) +" : " +line
    lst = lst[0:9]
    outfile.write("%s\n"%(",".join(lst)))
  outfile.close()

main()