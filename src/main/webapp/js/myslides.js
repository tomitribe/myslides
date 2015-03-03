(function () {
    'use strict';

    angular.module('myslides', [ 'ngRoute', 'ngSanitize' ])
        .config(['$routeProvider', '$locationProvider', function ($routeProvider) {
            $routeProvider
                .when('/login',             { templateUrl: 'partial/login.html',    controller: 'LoginController' })
                .when('/slides',            { templateUrl: 'partial/slides.html',   controller: 'SlidesController' })
                .when('/slide/new',         { templateUrl: 'partial/new.html',      controller: 'NewSlideController' })
                .when('/slide/admin/:id',   { templateUrl: 'partial/admin.html',      controller: 'AdminController' })
                .when('/slide/:id',         { templateUrl: 'partial/empty.html',    controller: 'SlideController' })
                .otherwise({ redirectTo: '/slides' });
        }])

        .controller('LoginController', ['$rootScope', '$scope', '$http', '$location', function ($rootScope, $scope, $http, $location) {
            $scope.doLogin = function () {
                $rootScope.username = $scope.login.username;
                $http.defaults.headers.common.Authorization = 'Basic ' + btoa($scope.login.username + ':' + $scope.login.password);
                $location.path('/slides');
            };
        }])
        .controller('SlidesController', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
            $http.get('api/slide/user/' + $rootScope.username)
                .success(function (data) {
                    $scope.slides = data;
                });
        }])
        .controller('NewSlideController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
            $scope.save = function () {
                $http.post('api/slide/user/new', $scope.slide)
                    .success(function () {
                        $location.path('/slides');
                    });
            };
        }])
        .controller('AdminController', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
            $scope.id = $routeParams;
            $scope.actions = {
                next: function () {
                    $http.head('api/slideshow/actions/next/' + $routeParams.id);
                },
                previous: function () {
                    $http.head('api/slideshow/actions/previous/' + $routeParams.id);
                }
            };
        }])
        .controller('SlideController', ['$scope', '$http', '$routeParams', '$window', function ($scope, $http, $routeParams, $window) {
            $http.get('api/slide/' + $routeParams.id)
                .success(function (data) {
                    var jsPlugins = [ // TODO: configurable
                        'webjars/reveal.js/3.0.0/lib/js/classList.js',
                        'webjars/reveal.js/3.0.0/js/reveal.js',
                        'webjars/reveal.js/3.0.0/plugin/zoom-js/zoom.js',
                        'webjars/reveal.js/3.0.0/plugin/notes/notes.js',
                        'webjars/reveal.js/3.0.0/plugin/highlight/highlight.js',
                        'js/asciidoctor/asciidoctor-all.js',
                        'js/asciidoctor/asciidoctor.reveal.js'
                    ];
                    var css = [ // TODO: configurable
                        'webjars/reveal.js/3.0.0/css/reveal.css'                        
                    ];
                    var revealConfig = { // TODO: configurable
                        controls: true, progress: true, history: true, center: true, transition: "slide"
                    };

                    var slides = data;
                    var wsUri = document.location.host + document.location.pathname + (document.location.pathname == '/' ? '' : '/') + 'slideshow/' + $routeParams.id;
                    var iframe = document.createElement("iframe");
                    iframe.id = 'internal-iframe';
                    iframe.frameBorder = '0';
                    iframe.style.width = '100%';
                    iframe.style.height = jQuery(window).height() + 'px';
                    var page = '<!doctype html>' +
                        '<html lang="en">' +
                        '<head>' +
                        '<meta charset="utf-8">' +
                        '<title>' + slides.name + '</title>' +
                        '<link rel="stylesheet" href="webjars/reveal.js/3.0.0/css/reveal.css">' +
                        '<!--[if lt IE 9]>' +
                        '<script src="webjars/reveal.js/3.0.0/lib/js/html5shiv.js"></script>' +
                        '<![endif]-->' +
                        '<body>' +
                        '<div class="reveal">' +
                        '<div class="slides">' +
                        slides.content +
                        '</div>' +
                        '</div>';
                    for (var j = 0; j < jsPlugins.length; j++) {
                        page = page + '<script src="' + jsPlugins[j] + '"></script>';
                    }
                    for (var c = 0; c < css.length; c++) {
                        page = page + '<link rel="stylesheet" href="' + css[c] + '"></link>';
                    }
                    page = page +
                    '<script>' +
                    'Reveal.initialize(' + JSON.stringify(revealConfig) + ');' +
                    'var socket = new WebSocket("' + wsUri + '");' +
                    'document.getElementById("internal-iframe").onclose = function () {' +
                    '  socket.close();' +
                    '};' +
                    'socket.onmessage = function (e) {' +
                    '  var cmd = JSON.parse(e.data);' +
                    '  if(!cmd) return;' +
                    '  if (cmd.action == "NEXT") {' +
                    '    Reveal.next();' +
                    '  } else if (cmd.action == "PREVIOUS") {' +
                    '    Reveal.previous();' +
                    '  }' +
                    '};' +
                    '' +
                    '</script>' +
                    '<!-- TODO: add websocket handling -->' +
                    '</body>' +
                    '</html>';

                    angular.element($window).bind('resize', function() {
                        iframe.style.height = jQuery(window).height() + 'px';
                    });

                    document.body.appendChild(iframe);
                    iframe.contentWindow.document.open();
                    iframe.contentWindow.document.write(page);
                    iframe.contentWindow.document.close();
                    iframe.focus();
                });
        }])

        .run(['$rootScope', '$location', '$http', function($rootScope, $location, $http) {
            document.execCommand("ClearAuthenticationCache");
            $rootScope.$on('$locationChangeSuccess', function() {
                if (!$http.defaults.headers.common.Authorization) {
                    $location.path('/login')
                }
            });
        }]);
})();
