/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    $('#listRender').mouseleave(function () {
        $('#listRender').css({left: -10000});
    });

    $(".header").mouseenter(function (event) {
        var calendar = $('#listRender');
        var left = event.pageX - calendar.width() / 2 > 0 ? event.pageX - calendar.width() / 2 : 0;
        calendar.css({
            left: left + 'px'
        });
    });

});


