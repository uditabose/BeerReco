from django.db import models
from django.utils.encoding import smart_unicode


class BeerRating(models.Model):
	name = models.CharField(max_length = 120, null = False, blank = False)
        beer1 = models.CharField(max_length = 120, null = False, blank = False)
        beer2 = models.CharField(max_length = 120, null = False, blank = False)
        beer3 = models.CharField(max_length = 120, null = False, blank = False)

	def __unicode__(self):
		return smart_unicode(self.name)
