//  Hello there!
//

$(document).ready(function () {
    $('#listRender').datepick({monthsToShow: [3, 4], monthsToStep:12, monthsOffset:function(date){return date.getMonth();}, showOtherMonths: false, changeMonth: true, onShow: $.datepick.showStatus});

});


