from django.db import models

class Poll(models.Model):
    question = models.CharField(max_length=200)
    pub_date = models.DateTimeField('date published')

class Choice(models.Model):
    poll = models.ForeignKey(Poll)
    
    userName  = models.CharField(max_length=200)

    beer1 = models.CharField(max_length=200)
     
    beer2 = models.CharField(max_length=200)
    
    beer3 = models.CharField(max_length=200)
    

