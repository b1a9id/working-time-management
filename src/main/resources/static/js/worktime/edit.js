$(document).ready(function () {
	var workTypeChoices = $('.workType-choice');
	$(workTypeChoices).each(function (index, elem) {
		showItem(this);
	});
	$('.workType-choice').on('change', function () {
		showItem(this);
	});
});

function showItem(workTypeChoice) {
	var selected = $('option:selected', workTypeChoice).val();
	var tr = $(workTypeChoice).closest('tr');
	var children = $(tr).children();

	if (selected === 'LEGAL_VACATION' ||
		selected === 'FULL_PAID_VACATION' ||
		selected === 'FULL_PAID_VACATION_AFTER' ||
		selected === 'ABSENCE' ||
		selected === 'COMPENSATORY_VACATION' ||
		selected === 'SPECIAL_VACATION') {
		$(children).each(function (index, elem) {
			var showItem = $(elem).find('.show-item');
			if (showItem) {
				showItem.hide();
			}
		})
	} else {
		$(children).each(function (index, elem) {
			var showItem = $(elem).find('.show-item');
			if (showItem) {
				showItem.show();
			}
		})
	}
}
