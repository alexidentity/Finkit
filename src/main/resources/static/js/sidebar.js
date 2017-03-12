$(document).ready(function() {
	$("#btnToggleSidebar").click(function(event) {
    event.preventDefault();

    if ($(".sidebar").is(":visible") == true) {
    	$("#btnToggleSidebar > span").removeClass("glyphicon-chevron-right").addClass("glyphicon-chevron-left");
    	$(this).animate({right: "5px"}, "slow");
    } else {
    	$("#btnToggleSidebar > span").removeClass("glyphicon-chevron-left").addClass("glyphicon-chevron-right");
    	$(this).animate({right: "260px"}, "slow");
    }
    
    $(".sidebar").toggle("slide", { direction: "right" }, "slow");
  });
});