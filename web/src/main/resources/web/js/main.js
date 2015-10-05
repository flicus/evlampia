/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
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

window.log = function (text) {
    console.log(text);
};

var app = angular.module('webchat', ['ngRoute']);

app.factory('Notification', function () {

    var settings = {
        template: '<div class="notify-container"></div>',
        templateNotify: '<div class="notify closer"></div>',
        templateNotifyHeader: '<div class="notify-header"></div>',
        templateNotifyContent: '<div class="notify-content"></div>',
        templateCloser: '<div class="notify-closeAll">close all <i class="fa fa-remove"></i></div>'
    };

    var notifications = [],
        _id = 0,
        container, closer;

    function close(notification) {
        container.find('#notification' + notification.id).animate({opacity: 0}, function () {
            $(this).slideUp(function () {
                $(this).remove()
            })
        });
        remove(notification) && notifications.length <= 1 && closer.fadeOut();
    }

    function show(notification) {

        notification.element.on('click', function (event) {
            $(event.currentTarget).hasClass('closer') && close(notification);
        });

        notifications.push(notification);
        container.append(notification.element);
//        setTimeout(function () {
//            notification.element.addClass('-mx-start');
//        }, 1);

        notifications.length > 1 && closer.fadeIn();

        if (typeof notification.timeoutInMs === 'number') {
            timerClose(notification);

            notification.element.on('mouseenter', function (event) {
                clearTimeout(notification.timer);
            });

            notification.element.on('mouseleave', function (event) {
                timerClose(notification);
            });
        }
    }

    function timerClose(notification) {
        notification.timer = setTimeout(function () {
            close(notification);
        }, notification.timeoutInMs);
    }

    function remove(notification) {
        var i = 0;
        while (notifications[i].id !== notification.id && i < notifications.length) i++;

        if (notifications[i].id === notification.id) {
            notifications.splice(i, 1);
        }
        return true;
    }

    return {
        //timeoutInMs could be omitted to show notification without timeout for hiding it
        notify: function (header, message, timeoutInMs) {
            if (container === undefined) {
                container = $(settings.template);
                $('body').append(container);
                closer = $(settings.templateCloser);
                closer.on('click', function (event) {
                    var i = 0;
                    while (notifications.length !== 0) {
                        clearTimeout(notifications[i].timer);
                        close(notifications[i]);
                    }
                });
                container.append(closer);
            }

            var notification = {};
            notification.element = $(settings.templateNotify);
            notification.id = _id++;
            notification.element.attr('id', 'notification' + notification.id);
            notification.element.css('z-index', 10 * notification.id);
            notification.header = $(settings.templateNotifyHeader).append(header);
            notification.timeoutInMs = timeoutInMs;
            notification.content = $(settings.templateNotifyContent).append(message);
            notification.element.append(notification.header !== undefined && notification.header, notification.content);
            show(notification);
        }

    }

});

app.factory('Helper', function () {
    var _storage = {};
    var data = {
        storage: _storage
    };
    return data;
});

app.directive('keyHandler', function () {
    return {
        restrict: 'A',
        scope: {
            onEnter: '&',
            onTab: '&'
        },
        link: function postLink(scope, element, attrs) {
            //app.log('init onEnter');
            element.keyup(function (event) {
                //app.log('keyup');
                //app.log(event);
                if (event.which == 13) {
                    scope.onEnter();
                }
                if (event.which == 9) {
                    scope.onTab();
                }
            });
        }
    }
});

app.filter('jabberUser', function () {
    return function (userName) {
        return parseUserName(userName);
    };
});

app.config(function ($routeProvider) {

    log("on config");

    $routeProvider
        .when('/login', {
            templateUrl: 'login.html',
            controller: 'LoginCtrl'
        })
        .when('/chat', {
            templateUrl: 'chat.html',
            controller: 'ChatCtrl'
        })
        .otherwise('/login');
});

app.controller('MainCtrl', function ($scope, Helper, $location) {
    $scope.name = "MainCtrl";
    log('main ctrl start');
    var name = Helper.storage.name;
    if (name) {
        $location.path('/chat');
    } else {
        $location.path('/login');
    }
});

app.controller('ChatCtrl', function ($scope, Helper, $location) {
    $scope.name = "ChatCtrl";
    log('chat ctrl start');
    $scope.ws = new WebSocket('ws://localhost:8080/ws/api');
    $scope.ctx = {};
    $scope.ctx.messages = [];
    $scope.ctx.toSay = "";
    $scope.ctx.name = Helper.storage.name;

    $scope.ws.onopen = function (data) {
        log('web socket opened');
        log(data);
        $scope.ws.send(JSON.stringify({'name': $scope.ctx.name, 'command': 1}));    //connect
    };

    $scope.ws.onclose = function (data) {
        log('web socket closed');
        log(data);
        $location.path('/login');
    };

    $scope.ws.onmessage = function (msg) {
        log('web socket on message');
        log(msg.data);
        var json = JSON.parse(msg.data);
        switch (json.command) {
            case 1:
                if (json.result == 102) {
                    $location.path('/login');
                } else {
                    $scope.ws.send(JSON.stringify({'name': $scope.ctx.name, 'command': 4}));    //get participants
                }
                break;
            case 2:
                $scope.$apply(function () {
                    $scope.ctx.messages.push({'from': parseUserName(json.from), 'body': json.body});
                });
                break;
            case 4:
                $scope.$apply(function () {
                    $scope.ctx.users = json.users;
                });
                break;
            case 6: //presence changed
                $scope.ws.send(JSON.stringify({'name': $scope.ctx.name, 'command': 4}));    //get participants
                break;
            default :
        }
    };

    $scope.send = function () {
        $scope.ws.send(JSON.stringify({'name': $scope.ctx.name, 'command': 2, 'message': $scope.ctx.toSay}));
        $scope.$apply(function () {
            $scope.ctx.toSay = "";
        });
    };

    $scope.addLastUser = function () {
        var lastMessage = ctx.messages[ctx.messages.length];
        $scope.$apply(function () {
            $scope.ctx.toSay = parseUserName(lastMessage.from) + ": " + $scope.ctx.toSay;
        });
    }

});

app.controller('LoginCtrl', function ($scope, $location, Helper) {
    $scope.name = "LoginCtrl";
    $scope.ctx = {name: undefined};
    log("login ctrl start");

    $scope.login = function () {
        Helper.storage['name'] = $scope.ctx.name;
        $location.path('/chat')
    }

});

function parseUserName(username) {
    if (username && username.indexOf("/")) {
        return username.substr(username.indexOf("/") + 1);
    } else {
        return username;
    }
}

function soundClick() {
    var audio = new Audio();
    audio.src = 'click.mp3';
    audio.autoplay = true;
}
