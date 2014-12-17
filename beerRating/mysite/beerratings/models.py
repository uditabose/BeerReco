# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Remove `managed = False` lines for those models you wish to give write DB access
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin.py sqlcustom [appname]'
# into your database.
from __future__ import unicode_literals

from django.db import models

class Beer(models.Model):
    beer_id = models.BigIntegerField(db_column='BEER_ID', primary_key=True) # Field name made lowercase.
    beer_name = models.CharField(db_column='BEER_NAME', max_length=1024) # Field name made lowercase.
    class Meta:
        managed = False
        db_table = 'Beer'

class BeerComp(models.Model):
    cmp_seq = models.BigIntegerField(db_column='CMP_SEQ') # Field name made lowercase.
    rev_beer = models.CharField(db_column='REV_BEER', max_length=256, blank=True) # Field name made lowercase.
    cmp_beer = models.CharField(db_column='CMP_BEER', max_length=256, blank=True) # Field name made lowercase.
    dist = models.FloatField(db_column='DIST', blank=True, null=True) # Field name made lowercase.
    beer_key = models.IntegerField(primary_key=True)
    class Meta:
        managed = False
        db_table = 'Beer_Comp'

class BaBeerIndex(models.Model):
    beer_id1 = models.IntegerField(blank=True, null=True)
    beer_id2 = models.IntegerField(blank=True, null=True)
    value = models.DecimalField(max_digits=10, decimal_places=2, blank=True, null=True)
    beer_sim_seq = models.IntegerField(primary_key=True)
    class Meta:
        managed = False
        db_table = 'ba_beer_index'

class BaBeerMetadata(models.Model):
    beer_id = models.IntegerField(blank=True, null=True)
    style = models.CharField(max_length=100, blank=True)
    brewery_id = models.IntegerField(blank=True, null=True)
    abv = models.DecimalField(max_digits=10, decimal_places=2, blank=True, null=True)
    ba_beer_meta_seq = models.IntegerField(primary_key=True)
    class Meta:
        managed = False
        db_table = 'ba_beer_metadata'

class BaBeeridName(models.Model):
    beer_name = models.CharField(max_length=100, blank=True)
    beer_id = models.IntegerField(blank=True, null=True)
    ba_brewery_seq = models.IntegerField(primary_key=True)
    class Meta:
        managed = False
        db_table = 'ba_beerid_name'

class BaBrewryidName(models.Model):
    brewry_name = models.CharField(max_length=100, blank=True)
    brewry_id = models.IntegerField(blank=True, null=True)
    ba_brewery_seq = models.IntegerField(primary_key=True)
    class Meta:
        managed = False
        db_table = 'ba_brewryid_name'

class BeerRating(models.Model):
        name = models.CharField(max_length = 120, null = False, blank = False)
        beer1 = models.CharField(max_length = 120, null = False, blank = False)
        beer2 = models.CharField(max_length = 120, null = False, blank = False)
        beer3 = models.CharField(max_length = 120, null = False, blank = False)

        def __unicode__(self):
                return smart_unicode(self.name)
