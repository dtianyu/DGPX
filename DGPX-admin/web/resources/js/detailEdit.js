/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    var btnConfirmDetail = document.getElementById("formNew:btnSaveDetail");
    if (btnConfirmDetail !== undefined && btnConfirmDetail !== null) {
        btnConfirmDetail.disabled = true;
        $(btnConfirmDetail).addClass('ui-state-disabled');
    }
    var btnConfirmDetail = document.getElementById("formEdit:btnSaveDetail");
    if (btnConfirmDetail !== undefined && btnConfirmDetail !== null) {
        btnConfirmDetail.disabled = true;
        $(btnConfirmDetail).addClass('ui-state-disabled');
    }
    var btnConfirmDetail = document.getElementById("formEdit:tabView:btnSaveDetail");
    if (btnConfirmDetail !== undefined && btnConfirmDetail !== null) {
        btnConfirmDetail.disabled = true;
        $(btnConfirmDetail).addClass('ui-state-disabled');
    }
    var btnDeleteDetail = document.getElementById("formNew:btnDeleteDetail");
    if (btnDeleteDetail !== undefined && btnDeleteDetail !== null) {
        btnDeleteDetail.disabled = true;
        $(btnDeleteDetail).addClass('ui-state-disabled');
    }
    var btnDeleteDetail = document.getElementById("formEdit:btnDeleteDetail");
    if (btnDeleteDetail !== undefined && btnDeleteDetail !== null) {
        btnDeleteDetail.disabled = true;
        $(btnDeleteDetail).addClass('ui-state-disabled');
    }
    var btnDeleteDetail = document.getElementById("formEdit:tabView:btnDeleteDetail");
    if (btnDeleteDetail !== undefined && btnDeleteDetail !== null) {
        btnDeleteDetail.disabled = true;
        $(btnDeleteDetail).addClass('ui-state-disabled');
    }

});