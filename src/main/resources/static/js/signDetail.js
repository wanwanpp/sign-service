var app = angular.module('myApp', ['ngAnimate', 'ui.bootstrap']);
app.controller('signDetailCtrl', function ($scope, $http) {

    $scope.getData = function () {
        var starts = $("#firstTime").val();
        var ends = $("#lastTime").val();
        $http({
            method: 'post',
            url: '/signDetail/getData',
            data: {starts: starts, ends: ends}
        }).success(function (signs) {
            $scope.signs = signs;
        });
    };

    $scope.buqian = function () {

        var date = $("#dateselect").val();
        var reason = $("#reason").val();
        $http({
            method: 'get',
            url: '/buqian/apply',
            params: {number: date, reason: reason}
        }).success(function (message) {
            document.getElementById("buqian").setAttribute("disabled", "disabled");
            alert(message);
        });
    };
//日期插件
    $scope.today = function () {
        $scope.dt = new Date();
        $scope.dt1 = new Date();
        $scope.dt2 = new Date();
    };
    $scope.today();

    $scope.clear = function () {
        $scope.dt = null;
        $scope.dt1 = null;
        $scope.dt2 = null;
    };

    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: true
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        startingDay: 1
    };

    $scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function () {
        $scope.popup1.opened = true;
    };

    $scope.open2 = function () {
        $scope.popup2.opened = true;
    };

    $scope.setDate = function (year, month, day) {
        $scope.dt = new Date(year, month, day);
        $scope.dt1 = new Date(year, month, day);
        $scope.dt2 = new Date(year, month, day);

    };
    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup1 = {
        opened: false
    };

    $scope.popup2 = {
        opened: false
    };

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 1);
    $scope.events = [
        {
            date: tomorrow,
            status: 'full'
        },
        {
            date: afterTomorrow,
            status: 'partially'
        }
    ];

    function getDayClass(data) {
        var date = data.date,
            mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);
            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }
        return '';
    }
});
