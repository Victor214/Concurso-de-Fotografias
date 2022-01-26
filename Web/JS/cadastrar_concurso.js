/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// Limit File Size for Logo
$(function(){
    var fileInput = $('.upload-file');
    var maxSize = fileInput.data('max-size');
    $('.upload-form').submit(function(e){
        if(fileInput.get(0).files.length){
            var fileSize = fileInput.get(0).files[0].size; // in bytes
            if(fileSize>maxSize){
                alert('Tamanho do arquivo é maior que o permitido.');
                return false;
            }
        }else{
            alert('Um arquivo deve ser escolhido.');
            return false;
        }
        
    });
});


// Date Picker JS
$(function () {
    $('#datepicker-start').datetimepicker();
    $('#datepicker-end').datetimepicker({
        useCurrent: false
    });
    $("#datepicker-start").on("change.datetimepicker", function (e) {
        $('#datepicker-end').datetimepicker('minDate', e.date);
    });
    $("#datepicker-end").on("change.datetimepicker", function (e) {
        $('#datepicker-start').datetimepicker('maxDate', e.date);
    });
});


// JQuery Slider
  $( function() {
    $( "#slider-range" ).slider({
      range: true,
      min: 1,
      max: 6,
      values: [ 1, 3 ],
      slide: function( event, ui ) {
          if (ui.values[ 0 ] == ui.values[ 1 ]) {
            $( "#qtdFotos" ).val("Exatamente " + ui.values[ 1 ] + " fotos" );
          } else {
             $( "#qtdFotos" ).val( ui.values[ 0 ] + " - " + ui.values[ 1 ] + " fotos" ); 
          }
          $( "#minFotos" ).val(ui.values[ 0 ]);
          $( "#maxFotos" ).val(ui.values[ 1 ]); 
      }
    });
    if ($( "#slider-range" ).slider( "values", 0 ) == $( "#slider-range" ).slider( "values", 1 )) {
        $( "#qtdFotos" ).val( "Exatamente " + $( "#slider-range" ).slider( "values", 1 ) + " fotos" ); 
    } else {
        $( "#qtdFotos" ).val( $( "#slider-range" ).slider( "values", 0 ) +
          " - " + $( "#slider-range" ).slider( "values", 1 ) + " fotos" );
    }
    
    $( "#minFotos" ).val($( "#slider-range" ).slider( "values", 0 ));
    $( "#maxFotos" ).val($( "#slider-range" ).slider( "values", 1 )); 
  } );