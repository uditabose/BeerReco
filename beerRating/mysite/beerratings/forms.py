from django import forms

from .models import BeerRating

class SignUpForm (forms.ModelForm):
	class Meta:
		model = BeerRating
