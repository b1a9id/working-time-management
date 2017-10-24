$(document).ready(function () {
	$('#accordion-btn').on('click', function () {
		$('#accordion-block').stop(true, true).slideToggle();
	});
});

$(function () {
	$('[data-toggle="tooltip"]').tooltip();

	$('.date-time-picker').datepicker({
		dateFormat: 'yy/mm/dd',
		inline: true,
		lang: 'ja'
	});
});

