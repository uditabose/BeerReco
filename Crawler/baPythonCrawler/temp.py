f = open("filename.csv")
f2 = open("newfile.csv", "w")
for line in f:
  cols = line.split(",")
  cols[0] = cols[0][2:]
  cols[1] = cols[1][2:]
  cols[2] = cols[2][:cols[2].find("/")]
  cols[2] = int(cols[2])
  f2.write(",".join(cols))