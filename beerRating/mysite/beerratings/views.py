from django.utils import simplejson
from django.http import HttpResponse
from django.contrib import messages
from .models import BeerRating

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
        
	#name = b.name
	beer1 = request.GET.get('beer1', '')
	beer2 = request.GET.get('beer2', '')
	beer3 = request.GET.get('beer3', '')
	#beer2 = b.beer2
	#beer3 = b.beer3

	to_json = {
        	"name": name,
        	"beer1": beer1,
		"beer2": beer2,
		"beer3": beer3
    	}
    	return HttpResponse(simplejson.dumps(to_json), mimetype='application/json')
