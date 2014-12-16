from django.conf.urls import patterns, include, url

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    url(r'^$', 'beerratings.views.home', name='home'),

    #fav_beer
    url(r'^fav_beer', 'beerratings.views.fav_beer', name='fav_beer'),	

    # url(r'^blog/', include('blog.urls')),
    url(r'^beer/', include('beer.urls')),

    url(r'^admin/', include(admin.site.urls)),
)
