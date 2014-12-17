from django.contrib import admin

from .models import BeerComp
from .models import BeerRating

class BeerRatingAdmin(admin.ModelAdmin):
	class Meta:
		model = BeerRating

admin.site.register(BeerRating, BeerRatingAdmin)
