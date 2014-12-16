import json
import os
import urllib

def cleandata():
	city_file_path = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/BeerReco/Crawler/DataDump/RateBeer/Archive/City"
	brewer_dir_path = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/BeerReco/Crawler/DataDump/RateBeer/Archive/"
	beer_dir_path = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/BeerReco/Crawler/DataDump/RateBeer/Archive/"
	review_dir_path = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/BeerReco/Crawler/DataDump/RateBeer/Archive/"
	dump_dir_path = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/BeerReco/Crawler/DataDump/RateBeer/Archive/"

	by_city(city_file_path, brewer_dir_path, beer_dir_path, review_dir_path, dump_dir_path)

def by_city(city_file_path, brewer_dir_path, beer_dir_path, review_dir_path, dump_dir_path):
	city_json_obj = json.load(open(city_file_path))
	if (type(city_json_obj) is dict):
		dump_by_brewer_file = open(dump_dir_path + "By_Brewer", "a")
		dump_by_beer_file = open(dump_dir_path + "By_Beer", "a")
		dump_by_review_file = open(dump_dir_path + "By_Review", "a")
		for city, city_brewery_link in city_json_obj.items():
			print city, ":", city_brewery_link
			print "--------------------------------------\n"
			by_brewery(city, city_brewery_link, brewer_dir_path, beer_dir_path, review_dir_path, dump_by_brewer_file, dump_by_beer_file, dump_by_review_file)

		dump_by_brewer_file.close()
		dump_by_beer_file.close()
		dump_by_review_file.close()



def by_brewery(city, city_brewery_link, brewer_dir_path, beer_dir_path, review_dir_path, dump_by_brewer_file, dump_by_beer_file, dump_by_review_file):
	if(os.path.exists(brewer_dir_path + "Brewery-" + city)) :
		breawery_json_obj = json.load(open(brewer_dir_path + "Brewery-" + city))
		if (type(breawery_json_obj) is dict) :
			for brewery_name, brewery_link in breawery_json_obj.items():
				link_splits = brewery_link.split("/")
				brewery_id = "-1"
				if (link_splits[-1] == "#"):
					continue
				else: 
					brewery_id = link_splits[-2] if (link_splits[-1] == '') else link_splits[-1]
					print brewery_id, ":", brewery_name
					by_beer(city, brewery_id, brewery_name, beer_dir_path, review_dir_path, dump_by_brewer_file, dump_by_beer_file, dump_by_review_file)

def by_beer(city, brewery_id, brewery_name, beer_dir_path, review_dir_path, dump_by_brewer_file, dump_by_beer_file, dump_by_review_file):
	encoded_brewery_name = urllib.urlencode({"":brewery_name.encode("UTF-8", "ignore")})[1:]
	print encoded_brewery_name
	if(os.path.exists(beer_dir_path + "Beer-" + encoded_brewery_name)) :
		beer_json_obj = json.load(open(beer_dir_path + "Beer-" + encoded_brewery_name))
		if (type(beer_json_obj) is dict):
			for beer_name, beer_link in beer_json_obj.items():
				link_splits = beer_link.spli("/")
				beer_id = link_splits[-2] if (link_splits[-1] == '') else link_splits[-1]
				dump_by_brewer_file.write("RB-" + brewery_id + ", " + beer_name + ", " + city + "\n")
				dump_by_beer_file.write("RB-" + beer_id+ ", " + beer_name + "\n")
				by_review(city, brewery_id, brewery_name, beer_id, beer_name, review_dir_path, dump_by_review_file)


def by_review(city, brewery_id, brewery_name, beer_id, beer_name, review_dir_path, dump_by_review_file):
	encoded_beer_name = urllib.urlencode({"":beer_name.encode("UTF-8", "ignore")})[1:]
	print encoded_beer_name
	if(os.path.exists(review_dir_path + "Review-" + encoded_beer_name)) :
		review_json_obj = json.load(open(review_dir_path + "Review-" + encoded_beer_name))
		if (type(review_json_obj) is dict):
			reviewers = review_json_obj["reviewer"]
			if (type(reviewers) is list):
				for review in reviewers:
					dump_by_brewer_file.write("RB-" + beer_id + "," + brewery_id + ", " 
						+ review.get("avgRatings", "") + ", "
						+ review.get("appearance") + ", "
						+ review.get("aroma") + ", "
						+ review.get("taste") + ", "
						+ review.get("palate") + ", "
						+ review.get("overall") + ", "
						+ review.get("userName") + ", "
						+ review.get("review") + ", "
						+ city + "\n")




cleandata()
