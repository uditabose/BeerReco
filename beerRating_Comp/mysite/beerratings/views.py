from django.utils import simplejson
from django.http import HttpResponse
from django.contrib import messages
from .models import Beer
from .models import BeerComp

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

	compList1 = BeerComp.objects.filter(rev_beer=beer1, 'cmp_beer')[:10]
	compList2 = BeerComp.objects.filter(rev_beer=beer2, 'cmp_beer')[:10]
	compList3 = BeerComp.objects.filter(rev_beer=beer3, 'cmp_beer')[:10]
	theList = list(compList1) + list(compList2) + list(compList3)
	data = serializers.serialize('json', theList)
	return HttpResponse(data, mimetype='application/json')