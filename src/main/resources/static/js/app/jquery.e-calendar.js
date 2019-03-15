/**
 * @license e-Calendar v0.9.3
 * (c) 2014-2016 - Jhonis de Souza
 * License: GNU
 */

(function ($) {
	
	

	    var eCalendar = function (options, object) {
        // Initializing global variables
        var adDay = new Date().getDate();
        var adMonth = new Date().getMonth();
        var adYear = new Date().getFullYear();
        var dDay = adDay;
        var dMonth = adMonth;
        var dYear = adYear;
        var instance = object;

        var settings = $.extend({}, $.fn.eCalendar.defaults, options);
       

        function lpad(value, length, pad) {
            if (typeof pad == 'undefined') {
                pad = '0';
            }
            var p;
            for (var i = 0; i < length; i++) {
                p += pad;
            }
            return (p + value).slice(-length);
        }

        var mouseOver = function () {
            $(this).addClass('c-nav-btn-over');
        };
        var mouseLeave = function () {
            $(this).removeClass('c-nav-btn-over');
        };
        var mouseOverEvent = function () {
            $(this).addClass('c-event-over');
            var d = $(this).attr('data-event-day');
            $('div.c-event-item[data-event-day="' + d + '"]').addClass('c-event-over');
        };
        var mouseLeaveEvent = function () {
            $(this).removeClass('c-event-over')
            var d = $(this).attr('data-event-day');
            $('div.c-event-item[data-event-day="' + d + '"]').removeClass('c-event-over');
        };
        var mouseOverItem = function () {
            $(this).addClass('c-event-over');
            var d = $(this).attr('data-event-day');
            $('div.c-event[data-event-day="' + d + '"]').addClass('c-event-over');
        };
        var mouseLeaveItem = function () {
            $(this).removeClass('c-event-over')
            var d = $(this).attr('data-event-day');
            $('div.c-event[data-event-day="' + d + '"]').removeClass('c-event-over');
        };
        var nextMonth = function () {
        	if ($.urlParam('type') != 'email'){   
        		//Allow change of month if NOT EMAIL LINK   
	            if (dMonth < 11) {
	                dMonth++;
	            } else {
	                dMonth = 0;
	                dYear++;
	            }
	//            $('#acc').val('');
	//            $('#proj').val('');
	//            $('#track').val(''); 
	//            $('#shift').val('');
	            print();
	            if($('#proj option:selected').val() !=="" && $('#track option:selected').val() !==""){
	            	GetShiftPlanMonth($('#proj option:selected').val(), $('#track option:selected').val() );
	            	EmployeeCounter();
	            	GetStatus($('#proj option:selected').val());
	            	setTimeout(function() {
	            		statusBtnTrack($('#track option:selected').val());
	            	},100);
	            } else  if($('#proj option:selected').val() !=="" && $('#track option:selected').val() ==""){
	            	GetShiftPlanMonthByProject($('#proj option:selected').val());
	            	EmployeeCounter();
	            	GetStatus($('#proj option:selected').val());
	            	setTimeout(function() { 
	            		statusBtnProj();
	            	},100);
	            }
        	}
            
        };
        var previousMonth = function () {
        	if ($.urlParam('type') != 'email'){   

	            if (dMonth > 0) {
	                dMonth--;
	            } else {
	                dMonth = 11;
	                dYear--;
	            }
	//            $('#acc').val('');
	//            $('#proj').val('');
	//            $('#track').val('');
	//            $('#shift').val('');
	            print();
	            if($('#proj option:selected').val() !=="" && $('#track option:selected').val() !==""){
	            	GetShiftPlanMonth($('#proj option:selected').val(), $('#track option:selected').val() );
	            	EmployeeCounter();
	            	 GetStatus($('#proj option:selected').val());
	            	 setTimeout(function() {
		            		statusBtnTrack($('#track option:selected').val());
		            	},100);
	            } else  if($('#proj option:selected').val() !=="" && $('#track option:selected').val() ==""){
	            	GetShiftPlanMonthByProject($('#proj option:selected').val());
	            	EmployeeCounter();
	            	 GetStatus($('#proj option:selected').val());
	            	 setTimeout(function() { 
		            		statusBtnProj();
		            	},100); 
	            }
        	}
           
        };

        
     
        
        function loadEvents() {
            if (typeof settings.url != 'undefined' && settings.url != '') {
                $.ajax({url: settings.url,
                    async: false,
                    success: function (result) {
                        settings.events = result;
                    }
                });
            }
        }


         function print() {
            //loadEvents();

            var dWeekDayOfMonthStart = new Date(dYear, dMonth, 1).getDay() - settings.firstDayOfWeek;
            if (dWeekDayOfMonthStart < 0) {
                dWeekDayOfMonthStart = 6 - ((dWeekDayOfMonthStart + 1) * -1);
            }
            var dLastDayOfMonth = new Date(dYear, dMonth + 1, 0).getDate();
            var dLastDayOfPreviousMonth = new Date(dYear, dMonth + 1, 0).getDate() - dWeekDayOfMonthStart + 1;

            var cBody = $('<div/>');
            var cMonthTable = $('<table/>');
            cMonthTable.attr('id','tableMonth')
            cMonthTable.addClass("table table-responsive");
			var cMonthtr = $('<tr/>');
//			var cShiftLegendtr = $('<tr/>').attr('id','legend');
//			var cShiftLegenddiv = $('<div/>').addClass('circle');
            //Commented by Akshata
          //  var cEvents = $('<div/>').addClass('c-event-grid');
          //  var cEventsBody = $('<div/>').addClass('c-event-body');
            //cEvents.append($('<div/>').addClass('c-event-title c-pad-top').html(settings.eventTitle));
          //  cEvents.append(cEventsBody);
          //Commented by Akshata
            var cNext = $('<td/>').addClass('c-next c-grid-title c-pad-top');
            var cMonth = $('<td/>').addClass('c-month c-grid-title c-pad-top');
            
        
        
            var cPrevious = $('<td/>').addClass('c-previous c-grid-title c-pad-top');
            cPrevious.html(settings.textArrows.previous);
           
            cMonth.html(settings.months[dMonth] + ' ' + dYear);
            cMonth.attr('data-monthId', dMonth + 1);
            cMonth.attr('data-year', dYear); 
            cMonth.attr('data-monthname', settings.months[dMonth]); 
            cNext.html(settings.textArrows.next);
//            $('.submit').attr('data-monthName',settings.months[dMonth]);
//            $('.submit').attr('data-year',dYear);
//            $('.submit').attr('data-monthId',dMonth + 1);
//            
            cPrevious.on('mouseover', mouseOver).on('mouseleave', mouseLeave).on('click',previousMonth);
            cNext.on('mouseover', mouseOver).on('mouseleave', mouseLeave).on('click',nextMonth);

            cMonthtr.append(cPrevious);
            cMonthtr.append(cMonth);
            
            cMonthtr.append(cNext); 
            cMonthTable.append(cMonthtr);
//            cShiftLegendtr.append(cShiftLegenddiv);
//            cMonthTable.append(cShiftLegendtr);
            cBody.append(cMonthTable);

           
            var dayOfWeek = settings.firstDayOfWeek;
            var cWeekDayTable = $("<table/>");
            cWeekDayTable.attr('id','tableDate')
            cWeekDayTable.addClass("calenderTable");
            cWeekDayTable.addClass("table table-responsive table-bordered");
            var cWeekDaytr = $("<tr/>");
            for (var i = 0; i < 7; i++) {
                if (dayOfWeek > 6) {
                    dayOfWeek = 0;
                }

            var cWeekDay =  $('<td/>').addClass('c-week-day c-pad-top');
//console.log(dayOfWeek);

                cWeekDay.html(settings.weekDays[dayOfWeek]);
                cWeekDaytr.append(cWeekDay);
                cWeekDayTable.append(cWeekDaytr);
                //cBody.append(cWeekDayTable);
                dayOfWeek++;
            }
            var day = 1;
            var dayOfNextMonth = 1;
             var cDaytr = $('<tr/>');
            for (var i = 0; i < 42; i++) {
                if (i==7 || i==14 || i == 21 || i==28){
                    var cDaytr = $('<tr/>');
                }


                var cDay = $('<td/>');
                var cSkeletonTable = $("<table/>");
                var cSkeletontr = $('<tr/>');
                var cSkeletontd = $('<td/>');
                 var cInnerDiv = $('<div/>');
                 cInnerDiv.addClass('parentDiv');
                
                 //Append Different DIV's for different Shifts
                 var ShiftArray = ['1S','2S','RS','NS','W','L','PH','H','C','OS'];
                 for(var m = 0 ;m<ShiftArray.length ; m++){
                	  var ShiftDiv = $('<div/>');
                	  ShiftDiv.addClass(''+ShiftArray[m]+' empEvent');
                	  cInnerDiv.append(ShiftDiv);
                 }

                cSkeletontd.append(cInnerDiv);
                cSkeletontr.append(cSkeletontd);
                cSkeletonTable.append(cSkeletontr);
                // var cEventDay = $('<div/>');
                // cEventDay.addClass('e-event-drop');
                if (i < dWeekDayOfMonthStart) {
                    cDay.addClass('c-day-previous-month c-pad-top');
                    cDay.html(dLastDayOfPreviousMonth++);
                } else if (day <= dLastDayOfMonth) {
                    cDay.addClass('c-day c-pad-top c-drop');
                    //Code to make element Draggable
                    cDay.droppable({ 
                      accept:".w-emp",
                      tolerance: "pointer",
                    //  revert:"true",
                      drop: function(event,ui) {
                    	  
                    	   
                    	  var shift = ui.draggable.attr('data-shift');
                    	  var emp_id = ui.draggable.attr('data-empid');
                          //var droppable = $(this).children().find('div.parentDiv');
                          var droppable = $(this).children().find('div.'+shift);
                        //console.log($(this).find('.parentDiv').attr('id'));
                          //Clone and drag
                          console.log($(this).find("div[data-empid="+emp_id+"]"));    
                          
                          console.log($(this).find("div[data-empid="+emp_id+"]").hasClass('ui-draggable-dragging'));
                          if (($(this).find("div[data-empid="+emp_id+"]").length == 0)){
                        	
                        		console.log('allow ');
                        	  cloned = ui.helper.clone();
                                
                                cloned.css({'width':'','height':'','position': 'static', 'top': '', 'left': ''});
                               
                                 setDraggable(cloned, false);
                                 $(droppable).append(cloned);
                                 cloned.addClass('inCalendar');
								 cloned.addClass('popoverThis');
								 cloned.removeClass('outCalendar');
								  ui.helper.remove();
								  cloned.css('display','');
								 // cloned.css('position','static');
                                  

                                //
                                // droppable.append(cloned);
                                // ui.helper.hide();



                          //----Without Clone
                            // var droppable = ($(this).find('td'));
                          //  var draggable = $(ui.draggable);
                            // draggable.appendTo(droppable);
                            // $(draggable).removeClass('w-emp').addClass('cal');
                               //----Without Clone


                          //  -----Clone
                            // var clone = draggable.clone();
                            // clone.appendTo(droppable);
                            // clone.draggable();
                            //
                            //
                            // clone.addClass('cal');
                          // clone.appendTo(droppable).draggable();
                              //	IfTrackCovered($(this));
                                 AttachTrack();
                                  //AttachTrack().done(populateSelectedTrck());
                                  CheckCovered();
                                  EmployeeCounter();
                                  CheckTrack(cloned);  
                                  checkHeight($(this).find('.parentDiv').height());
                                  console.log($(this).find('.w-emp').length);    
                          }
                          else{
                        	  var html = '<span class="label error-label label-danger">Multiple addition of Employee not allowed for the same day!!</span>';
                        	  $(this).append(html);
                        	  $(".error-label").delay(3000).fadeOut("slow");
                          }
                      }
                       
                    
                    });  

                     // setDraggable($(".div-event-drop"), true);
                    // cDay.attr('ondrop', 'drop(event)');
                    // cDay.attr('ondragover', 'allowDrop(event)');
                    //ondrop="drop(event)" ondragover="allowDrop(event)"
                    if (day == dDay && adMonth == dMonth && adYear == dYear) {
                        cDay.addClass('c-today');
                    }
                    for (var j = 0; j < settings.events.length; j++) {
                        var d = settings.events[j].datetime;
                        if (d.getDate() == day && d.getMonth() == dMonth && d.getFullYear() == dYear) {
                            cDay.addClass('c-event').attr('data-event-day', d.getDate());
                            cDay.on('mouseover', mouseOverEvent).on('mouseleave', mouseLeaveEvent);
                        } 	
                    }       
                  //  console.log(cSkeletonTable);    

                  var d= day++;
                  var cdayhtml = '<span class="glyphicon glyphicon-one-fine-dot fail" style="display:none" data-toggle="tooltip"></span>';
                  cdayhtml += '<span>'+d+'</span>';
				  cDay.html(cdayhtml);
				   cDay.attr('data-date',d); 
                  
                  cInnerDiv.attr('id', d);    
                  

//Thrash Functionality
                //   cThrash.addClass('deleteArea glyphicon glyphicon-trash');
                //   cThrash.droppable({
                //     accept: '.cal',
                //     drop: function(event, ui) {
                //   $(ui.helper).remove(); //destroy clone
                //   $(ui.draggable).remove(); //remove from list
                //
                //
                //     }
                // });
                //   cDay.append(cThrash);
//End of Thrash Functionality
                  cDay.append(cSkeletonTable);
                    // cDay.append(cEventDay);
                    //console.log(day);
                } else {
                    cDay.addClass('c-day-next-month c-pad-top');
                    cDay.html(dayOfNextMonth++);
                }
                cDaytr.append(cDay)
                 cWeekDayTable.append(cDaytr);

                cBody.append(cWeekDayTable);
            }
                      
            var eventList = $('<div/>').addClass('c-event-list');
            for (var i = 0; i < settings.events.length; i++) {
                var d = settings.events[i].datetime;
                if (d.getMonth() == dMonth && d.getFullYear() == dYear) {
                    var date = lpad(d.getDate(), 2) + '/' + lpad(d.getMonth() + 1, 2) + ' ' + lpad(d.getHours(), 2) + ':' + lpad(d.getMinutes(), 2);
                    var item = $('<div/>').addClass('c-event-item');
                    var title = $('<div/>').addClass('title').html(date + '  ' + settings.events[i].title + '<br/>');
                    var description = $('<div/>').addClass('description').html(settings.events[i].description + '<br/>');
                    item.attr('data-event-day', d.getDate());
                    item.on('mouseover', mouseOverItem).on('mouseleave', mouseLeaveItem);
                    item.append(title).append(description);

                    // Add the url to the description if is set
                    if( settings.events[i].url !== undefined )
                    {
                        /**
                         * If the setting url_blank is set and is true, the target of the url
                         * will be "_blank"
                         */
                        type_url = settings.events[i].url_blank !== undefined &&
                                   settings.events[i].url_blank === true ?
                                   '_blank':'';
                        description.wrap( '<a href="'+ settings.events[i].url +'" target="'+type_url+'" ></a>' );
                    }

                    eventList.append(item);
                }
            }
            $(instance).addClass('calendar');
            //Commented and added by Akshata
            // cEventsBody.append(eventList);
            //$(instance).html(cBody).append(cEvents);
            $(instance).html(cBody);
              //End of Commented and added by Akshata
            cMonth.attr('data-noOfDays', d);
        }

       // return $.when(print()).done(GetShiftPlanMonth());
         print();
     
         
    }

    $.fn.eCalendar = function (oInit) {
        return this.each(function () {
            return eCalendar(oInit, $(this));

        });
    };
    
    
       
    
function getColor(selectedshift){
  var shift_class='';
  switch(selectedshift) {
    case 'W':
          shift_class = 'w-shift';
          break;
    case '1S':
          shift_class = 'second-shift';
          break;
    case 'NS':
            shift_class = 'night-shift';
          break;
  }
  return shift_class;
}
    // plugin defaults
    // $.fn.eCalendar.defaults = {
    //     weekDays: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
    //     months: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
    //     textArrows: {previous: '<', next: '>'},
    //     eventTitle: 'Eventos',
    //     url: '',
    //     events: [
    //         {title: 'Evento de Abertura', description: 'Abertura das Olimpíadas Rio 2016', datetime: new Date(2016, new Date().getMonth(), 12, 17)},
    //         {title: 'Tênis de Mesa', description: 'BRA x ARG - Semifinal', datetime: new Date(2016, new Date().getMonth(), 23, 16)},
    //         {title: 'Ginástica Olímpica', description: 'Classificatórias de equipes', datetime: new Date(2016, new Date().getMonth(), 31, 16)}
    //     ],
    //     firstDayOfWeek: 0
    // };

	function checkHeight(length){
		if (length > 118 && length< 140){
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('height','15rem');
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('max-height','15rem')
		}
		
		if (length >= 140 && length <160){
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('height','17rem');
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('max-height','17rem')
		} 
		
		if (length >= 160 && length <180){
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('height','19rem');
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('max-height','19rem')
		} 

		if (length >= 180 && length <200){
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('height','21rem');
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('max-height','21rem')
		} 
		
		if (length >= 200 && length <220){ 
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('height','23rem');
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('max-height','23rem');
		} 
		if (length >= 220 && length <240){  
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('height','25rem');
			$('.c-day, .c-day-previous-month, .c-day-next-month').css('max-height','25rem');
		} 
		
//		if (length >20 && length < 25) {
//		
//		$('.c-day, .c-day-previous-month, .c-day-previous-month').css('height','15rem');
//		$('.c-day, .c-day-previous-month, .c-day-previous-month').css('max-height','15rem')
//		
//	}
//	if (length >=25 && length < 29) {
//		
//		$('.c-day, .c-day-previous-month, .c-day-previous-month').css('height','17rem');
//		$('.c-day, .c-day-previous-month, .c-day-previous-month').css('max-height','17rem')
//		
//	}
//if (length >=29 && length < 33) {
//		
//		$('.c-day, .c-day-previous-month, .c-day-previous-month').css('height','19rem');
//		$('.c-day, .c-day-previous-month, .c-day-previous-month').css('max-height','19rem')
//		
//	}

	}

}(jQuery));





