//  Hello there!
//

$(document).ready(function () {
    $('#listRender')
        .datepick({
            monthsToShow: [3, 4],
            monthsToStep: 1,
            monthsOffset: function (date) {
                return date.getMonth();
            },
            showOtherMonths: false,
            changeMonth: true,
            onShow: $.datepick.showStatus,
            minDate: new Date(2011, 8 - 1, 23),
            maxDate: new Date(),
            onSelect: function (date) {
                var day = date[0].getDate() < 10 ? '0' + date[0].getDate() : date[0].getDate();
                var month = date[0].getMonth() + 1 < 10 ? '0' + (date[0].getMonth() + 1) : date[0].getMonth() + 1;
                var url = 'http://0xffff.net/logs/vnations@conference.jabber.ru/' + date[0].getFullYear() + '/' + month + '/' + day;
                window.location = url;
            }
        });

});


