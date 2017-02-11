/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


totaltime = 0;

$(document).ready(function () {
    $('body').addClass('PopupMenu');
    if ($('#examtime') !== null) {
        totaltime = parseInt($('#examtime').text());
        timer();
    }
});


function timer() {
    if (totaltime < 0) {
        totaltime = 0;
    }
    $(document.getElementById("formPoll:timeLeft")).text(totaltime);
    totaltime--;
    setTimeout("timer()", 60000);
}