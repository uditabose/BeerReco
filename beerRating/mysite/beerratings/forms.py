from django import forms

from .models import BeerRating
from .models import BeerComp

class SignUpForm (forms.ModelForm):
	class Meta:
		model = BeerRating
