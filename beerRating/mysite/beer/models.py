import datetime

from django.db import models
from django.utils import timezone


class Beer(models.Model):
    question = models.CharField(max_length=200)
    pub_date = models.DateTimeField('date published')
    def __unicode__(self):  
       	return self.question

    def was_published_recently(self):
       	return self.pub_date >= timezone.now() - datetime.timedelta(days=1)

class BeerChoice(models.Model):
    poll = models.ForeignKey(Beer)

    userName  = models.CharField(max_length=200)

    beer1 = models.CharField(max_length=200)

    beer2 = models.CharField(max_length=200)

    beer3 = models.CharField(max_length=200)

    def __unicode__(self):  
        return self.username
	return self.beer1
	return self.beer2
	return self.beer3


