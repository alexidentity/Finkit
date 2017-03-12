$(document).ready(function() {
	$(".navbar-topics").hide();

  $("#btnTopics").click(function(event) {
    event.preventDefault();
    $(".navbar-topics").toggle("slide", { direction: "left" }, 250);
    $("#topicNavbarSearch").focus();
  });

	$(".searchable-container").searchable({
		searchField: '#topicNavbarSearch',
		selector: 'li > a',
		childSelector: '.topicName',

		show: function(element) {
			element.slideDown(100);
		},
		hide: function(element) {
			element.slideUp(100);
		}
	})
});
