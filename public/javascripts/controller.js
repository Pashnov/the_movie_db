var myApp = angular.module('myApp', ['ngRoute']);

myApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'assets/templates/home.html',
            controller:'MainCtrl'
        })
        .when('/about', {
            templateUrl: 'assets/templates/about.html',
            controller:'AboutCtrl'
        })
        .when('/contact', {
            templateUrl: 'assets/templates/contact.html',
            controller:'ContactCtrl'
        })
        .when('/movies/:movieId', {
            templateUrl: 'assets/templates/movie-detail.html',
            controller:'MovieDetailCtrl'
        })
        .when('/favorite', {
            templateUrl: 'assets/templates/favorite.html',
            controller:'FavoriteCtrl'
        })
        .when('/favorite/list/:favoriteId', {
            templateUrl: 'assets/templates/favorite-list.html',
            controller:'FavoriteListCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
}]);

myApp.controller('MainCtrl', ['$scope', '$http', '$location' ,function ($scope, $http, $location) {

    var url = 'top?size=2000000000';
    $http.get(url).then(function (resp, status, headers, config) {
        console.log(url, ':', resp);
        $scope.topMovies = resp.data;
    });


    $scope.title = "m phones";
    $scope.phones = [
        {'name': 'Nexes S', 'snippet': 'some s', 'status':false , 'priority':2},
        {'name': 'MOto XoOM', 'snippet': 'moto x', 'status':true, 'priority':1},
        {'name': 'Moto S', 'snippet': 'some s', 'status':true, 'priority':3}
    ];

    $scope.today = new Date();

    $scope.doneAndFilter = function (phoneItem) {
      return phoneItem.name && phoneItem.priority > 1 && phoneItem.status == true;
    };

    $scope.sortField = undefined;
    $scope.reverse = false;
    $scope.sort = function (fieldName) {
        if($scope.sortField === fieldName){
            $scope.reverse = !$scope.reverse;
        } else {
            $scope.sortField = fieldName;
            $scope.reverse = false;
        }
    };

    $scope.isSortUp = function (fieldName) {
        return $scope.sortField === fieldName && !$scope.reverse;
    };
    $scope.isSortDown = function (fieldName) {
        return $scope.sortField === fieldName && $scope.reverse;
    };

}]);

myApp.controller('AboutCtrl', ['$scope', '$http', '$location' ,function ($scope, $http, $location) {

}]);

myApp.controller('ContactCtrl', ['$scope', '$http', '$location' ,function ($scope, $http, $location) {

}]);

myApp.controller('FavoriteCtrl', ['$scope', '$http', '$location', '$route' ,function ($scope, $http, $location, $route) {
    var url = 'favorite';
    $http.get(url).then(function (resp) {
        console.log(url, ',', resp);
        $scope.favorites = resp.data;
    });

    $scope.createFavorite = function (name) {
        var url = 'createFavorite'+'?name='+name;
        $http.get(url).then(function (data) {
            console.log(url, ':', data);
            $route.reload();
        });
    }

}]);

myApp.controller('FavoriteListCtrl', ['$scope', '$http', '$location','$routeParams' ,function ($scope, $http, $location, $routeParams) {
    $scope.favoriteId = $routeParams.favoriteId;
    var url = 'favoriteList/' + $routeParams.favoriteId;
    $http.get(url).then(function (resp) {
        console.log(url, ',', resp);
        if(resp.status == 500){
            alert("You cannot remove favorite movie from foreign list")
        }
        $scope.favorite = resp.data;
    }, function (data) {
        alert("You cannot remove favorite movie from foreign list")
    });

    $scope.removeFavorite = function (favoriteId, movieId) {
        var url = 'removeFavoriteMovie';
        var data = {'favoriteId': favoriteId,'movieId': movieId};
        $http.post(url, data).then(function (resp) {
            console.log(url, ':', resp);
            angular.forEach($scope.favorite.movies, function(movie, index) {
                if (movie.id == movieId) {
                    $scope.favorite.movies.splice(index, 1);
                    return;
                }
            });
            // $scope.addedToFavorite = resp.data;
        });
    }
}]);

myApp.controller('MovieDetailCtrl', ['$scope', '$http', '$location', '$routeParams', '$route', function ($scope, $http, $location, $routeParams, $route) {
    $scope.movieId = $routeParams.movieId;
    $scope.favariteList = undefined;
    var url = 'detail/' + $routeParams.movieId;

    $http.get(url).then(function (resp) {
        console.log(url, ':', resp);
        $scope.movie = resp.data;
    });

    $scope.showFavoriteList = function () {
        if(!$scope.favariteList) {
            var url = 'showFavoriteList';
            $http.get(url).then(function (resp) {
                console.log(url, ':', resp);
                $scope.favariteList = resp.data;
                // $scope.addedToFavorite = resp.data;
            });
        }
    };

    $scope.addToFavorite = function (favoriteId, movieId) {
        var url = 'addToFavorite';
        var data = {'favoriteId':favoriteId,'movieId': movieId};
        $http.post(url, data).then(function (resp) {
            console.log(url, ':', resp);
            $scope.favariteList = undefined;
            // $scope.addedToFavorite = resp.data;
        });
    };


}]);

