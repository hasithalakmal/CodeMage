'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('add_boundary', function ($scope) {
  // This example requires the Drawing library. Include the libraries=drawing
  // parameter when you first load the API. For example:
  // <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC_qWRq_TMvCsvVGEsYAAPM6yrQ7JOz2uQ&libraries=drawing">

  function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: -34.397, lng: 150.644},
      zoom: 8
    });

    var drawingManager = new google.maps.drawing.DrawingManager({
      // drawingMode: google.maps.drawing.OverlayType.MARKER,
      drawingControl: true,
      drawingControlOptions: {
        position: google.maps.ControlPosition.TOP_CENTER,
        drawingModes: [ 'polygon']
      },
      markerOptions: {icon: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'},
      circleOptions: {
        fillColor: '#ffff00',
        fillOpacity: 1,
        strokeWeight: 5,
        clickable: false,
        editable: true,
        zIndex: 1
      }
    });
    drawingManager.setMap(map);

    google.maps.event.addListener(drawingManager, 'polygoncomplete', function (polygon) {
      var coordinates = (polygon.getPath().getArray());

      console.log(coordinates);
      //Display Coordinates below map
      var len = polygon.getPath().getLength();
      var htmlStr = [];

      var val ="";
      var longVal= [];
      var latVal= [];
      for (var i = 0; i < len; i++) {
        val =  polygon.getPath().getAt(i).toUrlValue(10);
        var str_array = [];
        var str_array = val.split(',');
        longVal[i] = parseFloat(str_array[0]);
        latVal[i] = parseFloat(str_array[1]);
      }
      console.log(longVal);
      console.log(latVal);
    });

  };

});

