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

/* http://keith-wood.name/datepick.html
 Russian localisation for jQuery Datepicker.
 Written by Andrew Stromnov (stromnov@gmail.com). */
(function ($) {
    $.datepick.regional['ru'] = {
        monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
            'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
        monthNamesShort: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн',
            'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'],
        dayNames: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'],
        dayNamesShort: ['вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт'],
        dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
        dateFormat: 'dd.mm.yyyy', firstDay: 1,
        renderer: $.datepick.defaultRenderer,
        prevText: '&#x3c;Пред', prevStatus: '',
        prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
        nextText: 'След&#x3e;', nextStatus: '',
        nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
        currentText: 'Сегодня', currentStatus: '',
        todayText: 'Сегодня', todayStatus: '',
        clearText: 'Очистить', clearStatus: '',
        closeText: 'Закрыть', closeStatus: '',
        yearStatus: '', monthStatus: '',
        weekText: 'Не', weekStatus: '',
        dayStatus: 'D, M d', defaultStatus: '',
        isRTL: false
    };
    $.datepick.setDefaults($.datepick.regional['ru']);
})(jQuery);
