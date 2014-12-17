from django.utils import simplejson
from django.core import serializers
from django.http import HttpResponse
from django.contrib import messages
from .models import *

from django.shortcuts import render, render_to_response, RequestContext

from .forms import SignUpForm

def home(request):
	
	form = SignUpForm(request.POST or None)
	if form.is_valid():
		save_it = form.save(commit=False)
		save_it.save()
	
	return render_to_response("BeerRating.html", 
				  locals(), 
				   context_instance=RequestContext(request))

#fav beer
def fav_beer(request):

    	messages.success(request, 'Thank you for using this system! Below are the suggestive beers! cheers.....!')
	name = request.GET.get('name', '')
	beer1 = request.GET.get('beer1', '')
	beer2 = request.GET.get('beer2', '')
	beer3 = request.GET.get('beer3', '')

	compList1 = BaBeerIndex.objects.filter(beer_id1=beer1)[:10]
	compList2 = BaBeerIndex.objects.filter(beer_id1=beer2)[:10]
	compList3 = BaBeerIndex.objects.filter(beer_id1=beer3)[:10]

	theList = get_compared_beer(compList1) + get_compared_beer(compList2) + get_compared_beer(compList3)
	print theList
	return HttpResponse(simplejson.dumps(theList), mimetype='application/json')


def get_compared_beer(compList):
	json_list = []
	for b in compList:
		print "collecting data"
		bmeta =  BaBeerMetadata.objects.get(beer_id=b.beer_id2)
	        beer = BaBeeridName.objects.get(beer_id=b.beer_id2)
	        brew = BaBrewryidName.objects.get(brewry_id=bmeta.brewery_id)
	    	print beer.beer_name, "-", bmeta.style, "-", brew.brewry_name
                toJson = { 
			"beer_name":beer.beer_name,
			"brewery":brew.brewry_name,
			"style":bmeta.style
		}
		json_list.append(toJson)
	return json_list
		

def beer_name(request):
	beer_part_name = request.GET.get('q', '')
	beer_list = BaBeeridName.objects.filter(beer_name__contains=beer_part_name)
	# print beer_list	
	json_list = []
	for beer in beer_list:
		toJson = {
			"name":beer.beer_name,
			"id":beer.beer_id
		}
		json_list.append(toJson)
	return HttpResponse(simplejson.dumps(json_list), mimetype='application/json')
