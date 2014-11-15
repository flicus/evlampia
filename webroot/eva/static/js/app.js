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

    $('#pageSize2').popup({
        trigger: 'hover, hover',
        animation: 'growUp',
        animationDuration: 400,
        template: '<div class="-tooltip"><i class="-arrow -success-"></i><div class="js-content"></div></div>',
        content: 'Херня какая то',
        placement: 'bottom',
        onlyOne: true
    });

    var cookie = Cookie.get('eva');
    if (cookie) {
        $('.pageSize').text(cookie);
    }

    $('#pageSize').on('click', function () {
        var oldItem = $(this);
        var li = oldItem.parent();
        var value = oldItem.find('.pageSize').text();
        var edit = $('<div id="toRemove" class="-group -form _tiny -warning-" style="display: none"><input id="toSave" type="text" value="' + value + '"><a id="saveBtn" href="#" class="-btn"><i class="-icon-save"></i></a></div>');
        li.prepend(edit);
        oldItem.fadeOut('fast', function () {
            edit.fadeIn();
        });
        var toSave = edit.find('#toSave');
        var saveBtn = edit.find('#saveBtn');
        saveBtn.on('click', function () {
            var toRemove = $('#toRemove');
            var toSave = toRemove.find('#toSave').val();
            Cookie.set('eva', toSave, 356);
            toRemove.fadeOut();
            toRemove.remove();
            oldItem.find('.pageSize').text(toSave);
            oldItem.fadeIn();
        });
        toSave.keyup(function (event) {
            if (event.which == 13) {
                event.preventDefault();
                saveBtn.click();
            }
        });
        toSave.focus();
    });

    var searchButton = $('#searchBtn');
    searchButton.on('click', function () {
        var editBox = $('#toSearch');
        var pageSize = +$('.pageSize').text();
        searchButton.removeClass("-error-");
        searchButton.addClass("_loading_ _unclickable_");
        EvaSearch
            .search(editBox.val(), pageSize, 0)
            .then(
            function (data) {
                searchButton.removeClass("_loading_ _unclickable_");
                var resultList = $('#resultList');
                resultList.find('.listItem').remove();
                var hits = data.searchResult.hits;
                $('#hits').text(hits);
                var items = data.searchResult.items;
                $.each(items, function (index, item) {
                    var record = $('<li class="listItem"><div class="resultItem"><div><small class="nick">' + item.author + '</small><a target="_blank" href="' + item.url + '">link</a></div><div>' + item.message + '</div></div></li>');
                    resultList.append(record);
                });
                if (hits > pageSize) {
                    Pager.init(hits, pageSize);
                    $('.pagination').css("display", "block");
                } else {
                    $('.pagination').css("display", "none");
                }
            },
            function (data, status) {
                searchButton.removeClass("_loading_ _unclickable_");
                searchButton.addClass("-error-");
                $.notify('Ooops... Some error occurs during search request<br>' + data.statusText, {
                    header: "Error!",
                    theme: "error",
                    animation: 'easeOutElastic',
                    type: 5000
                });
            });
    });

    var editBox = $('#toSearch');
    editBox.keyup(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            searchButton.click();
        }
    });

    editBox.focus();

    $.notify('Как всегда, жду замечаний и предложений в беседке. Сам собой коммунизм не построится!', {
        header: "Привет",
        theme: "primary",
        animation: 'easeOutElastic',
        type: 5000
    });
    $.notify('А если кто то вдруг возьмет текущие html/css за основу и нарисует шаблончик, как, по его мнению это должно выглядеть - тот молодец и владелец пирожка', {
        header: "Все еще привет",
        theme: "primary",
        animation: 'easeOutElastic',
        type: 9000
    });

    aGet("/eva/api/getcloud", function (data) {
        if (data.result == "ok") {
            $('#cloud').jQCloud(data.tags, {
                afterCloudRender: function () {
                    $('#cloud > span').on('click', function () {
                        var editBox = $('#toSearch');
                        editBox.val($(this).text());
                        currentPage = 1;
                        firstPageNum = 1;
                        findLog(0);
                    });
                }
            });
        }
    });
});


EvaSearch = {
    search: function (toSearch, pageSize, start) {
        var d = $.Deferred();
        var data = JSON.stringify({toSearch: toSearch, count: pageSize, start: start});

        $.ajax({
            url: "/eva/api/search",
            type: "POST",
            dataType: "json",
            processData: false,
            contentType: "application/json; charset=UTF-8",
            data: data,
            success: function (json) {
                if (json.result != "ok") {
                    d.reject(json);
                } else {
                    d.resolve(json);
                }
            },
            error: function (xhr, status) {
                d.reject(xhr, status);
            }
        });

        return d;
    },
    test: function (toSearch, count, start) {
        var d = $.Deferred();
        var json = {toSearch: toSearch, count: count, start: start};
        setTimeout(function () {
            var res = {"searchResult": {"hits": 250, "items": [
                {"author": "IzvrÐ°t", "message": " test", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/02/19.html#t02:27:51.147", "score": 6.125736},
                {"author": "IzvrÐ°t", "message": " test", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/02/19.html#t02:30:23.10", "score": 6.125736},
                {"author": "izvrat", "message": " test", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/02/19.html#t02:34:30.489", "score": 6.125736},
                {"author": "tirvindi", "message": " http://mirtestoff.ru/test/sondy.php", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/02/15.html#t05:49:31.401", "score": 3.062868},
                {"author": "110001101", "message": " http://www.rb.ru/test/33/", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/04/13.html#t05:24:54.202", "score": 3.062868},
                {"author": "tirvindi", "message": " https://www.microsoft.com/rus/windows/test/ слепящий вин. тест проходить до конца", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/08/23.html#t17:24:05.802", "score": 1.9142926},
                {"author": "Орда", "message": " [21:21] tirvindi: http://mirtestoff.ru/test/sondy.phpтест который не видит во мне убийцу - бестолковый", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/02/15.html#t08:34:21.700", "score": 1.531434},
                {"author": "tirvindi", "message": " дословный перевод:перетаскивайте квадраты с цветами в каждой строчке так чтобы упорядочить их по цветупервый и последний цвета в каждой строке фиксированы. нажмите \"Score Test\", когда закончите", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/03/13.html#t04:52:27.524", "score": 1.3400048},
                {"author": "tirvindi", "message": " Существует специальная команда -- [ (левая квадратная скобка). Она является синонимом команды test, и является встроенной командой (т.е. более эффективной, в смысле производительности). Эта команда воспринимает свои аргументы как выражение сравнения или как файловую проверку и возвращает код завершения в соответствии с результатами проверки (0 -- истина, 1 -- ложь).", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/02/14.html#t07:26:22.597", "score": 0.9571463},
                {"author": "mildox", "message": " хз, вот еще часть копипасты \"Как сообщает ИТАР-ТАСС со ссылкой на BBC, разработанная ими программа, получившая название \"Евгений\", победила на состоявшемся в Англии международном научном конкурсе кибернетического интеллекта (Turing test marathon), не добрав всего лишь 0,8 процента для того, чтобы пройти знаменитый тест Тьюринга.\"", "url": "http://schors.zapto.org/logs/vnations@conference.jabber.ru/2012/08/23.html#t05:07:20.833", "score": 0.9571463}
            ]}, "result": "ok"};
            d.resolve(res);
        }, 3000);
        return d;
    }
}

function findLog(start) {
    var editBox = $('#toSearch');
    var pageSize = +$('.pageSize').text();
    EvaSearch
        .search(editBox.val(), pageSize, start)
        .then(
        function (data) {
            var resultList = $('#resultList');
            resultList.find('.listItem').remove();
            var hits = data.searchResult.hits;
            $('#hits').text(hits);
            var items = data.searchResult.items;
            $.each(items, function (index, item) {
                var record = $('<li class="listItem"><div class="resultItem"><div><small class="nick">' + item.author + '</small><a target="_blank" href="' + item.url + '">link</a></div><div>' + item.message + '</div></div></li>');
                resultList.append(record);
            });
            if (hits > pageSize) {
                $('.pagination').css("display", "block");
            } else {
                $('.pagination').css("display", "none");
            }
            Pager.update(hits, pageSize);
        },
        function (data, status) {
            $.notify('Ooops... Some error occurs during search request<br>' + data.statusText, {
                header: "Error!",
                theme: "error",
                animation: 'easeOutElastic',
                type: 5000
            });
        });
}


var currentPage = 1;
var firstPageNum = 1;

Pager = {
    init: function (hits, pageSize) {
        currentPage = 1;
        firstPageNum = 1;
        var pagesTotal = Math.floor(hits / pageSize);
        var rest = hits % pageSize;
        if (rest > 0) {
            pagesTotal++;
        }
        var pagesToShow = pagesTotal > 5 ? 5 : pagesTotal;
        var pager = $('.pagination > ul');
        pager.find('li').remove();
        if (pagesToShow < pagesTotal) {
            var prev = $('<li><a href="#">Prev</a></li>');
            pager.append(prev);
            prev.addClass('disabled');
        }
        for (var i = 1; i <= pagesToShow; i++) {
            var item = $('<li><a id="' + i + '" href="#">' + i + '</a></li>');
            if (i == 1) {
                item.addClass('active');
            }
            pager.append(item);
        }
        if (pagesToShow < pagesTotal) {
            pager.append($('<li><a href="#">Next</a></li>'));
        }
        pager.children().not('.disabled').on('click', function () {
            var clicked = $(this).find('a').text();
            if (clicked == 'Next') {
                currentPage++;
            } else {
                currentPage = +clicked;
            }
            findLog((currentPage - 1) * pageSize);
        });
    },
    update: function (hits, pageSize) {
        var pagesTotal = Math.floor(hits / pageSize);
        var rest = hits % pageSize;
        if (rest > 0) {
            pagesTotal++;
        }
        var pagesToShow = pagesTotal > 5 ? 5 : pagesTotal;
        var pager = $('.pagination > ul');
        pager.find('li').remove();

        var prev;
        var next;
        if (pagesToShow < pagesTotal) {
            prev = $('<li><a href="#">Prev</a></li>');
            if (currentPage == 1) {
                prev.addClass('disabled')
            }
            next = $('<li><a href="#">Next</a></li>');
            if (currentPage == pagesTotal) {
                next.addClass('disabled');
            }
        }

        if (prev) {
            pager.append(prev);
        }
        for (var i = 1; i <= pagesToShow; i++) {
            var id = firstPageNum + i - 1;
            var item = $('<li><a id="' + id + '" href="#">' + id + '</a></li>');
            if (id == currentPage) {
                item.addClass('active');
            }
            pager.append(item);
        }
        if (next) {
            pager.append(next);
        }
        pager.children().not('.disabled').on('click', function () {
            var clicked = $(this).find('a').text();
            if (clicked == 'Prev') {
                currentPage--;
                if (firstPageNum > currentPage) {
                    firstPageNum = currentPage;
                }
            } else if (clicked == 'Next') {
                currentPage++;
                if ((currentPage - 5) == firstPageNum) {
                    firstPageNum++;
                }
            } else {
                currentPage = +clicked;
            }
            findLog((currentPage - 1) * pageSize);
        });
    }
}

Cookie = {
    set: function (name, value, days) {
        var postfix;
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            postfix = "; expires=" + date.toGMTString(); // expiry_date
        } else postfix = "";
        document.cookie = name + "=" + value + postfix + "; path=/";
    },
    get: function (cookie_name) {
        var nameEQ = cookie_name + "=";
        if (document.cookie) {
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1, c.length);
                if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
            }
        }
        return null;
    },
    delete: function (cookie_name) {
        var cookie_date = new Date();
        cookie_date.setTime(cookie_date.getTime() - 1);
        document.cookie = cookie_name += "=; expires=" + cookie_date.toGMTString();
    }
}

function aGet(url, onSuccess) {
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: onSuccess,
        error: function (xhr, status) {
            alert(xhr);
        }
    });
}

function aPut(url, data, onSuccess) {
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        processData: false,
        contentType: "application/json; charset=UTF-8",
        data: data,
        success: onSuccess,
        error: function (xhr, status) {
            alert(xhr);
        }
    });
}
