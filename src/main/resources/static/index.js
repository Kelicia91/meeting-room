$('.reservation .date-picker input').value = today();
$('.reservation .date-picker .btn').addEventListener("click", onClickLoadReservation);
$('.reservation .add-form .btn').addEventListener("click", onClickAddReservation);

loadMeetingRooms(onLoadMeetingRooms, onLoadFailed);
loadReservation(onLoadReservation, onLoadFailed);


function onClickLoadReservation(event) {
    event.preventDefault();
    loadReservation(onLoadReservation, onLoadFailed);
}

function onClickAddReservation(event) {
    event.preventDefault();
    addReservation(onLoadFailed);
}

function addReservation(fail) {
    const data = {
        username : $_value('.reservation .add-form .user input'),
        meetingRoomId : getMeetingRoomId(),
        date : getDate(),
        startTime : $_value('.reservation .add-form .time-picker .start-time'),
        endTime : $_value('.reservation .add-form .time-picker .end-time'),
        repeatPerWeek : $_value('.reservation .add-form .repeat input')
    };

    fetch('/api/reservations', {
        method: 'post',
        headers: new Headers({
            "content-type": "application/json",
        }),
        body: JSON.stringify(data)
    }).then(response => {
        if (!response.ok) {
            fail('잠시후 다시 시도해주세요');
        }
        loadReservation(onLoadReservation, onLoadFailed);
    }).catch(error => {
        fail(error);
    });
}

function loadReservation(success, fail) {
    const date = getDate();
    fetch(`/api/reservations?date=${date}`)
    .then(response => {
         if (!response.ok) {
             fail('잠시후 다시 시도해주세요');
         }
         return response.json();
     }).then(data => {
         success(data);
     }).catch(error => {
         fail(error);
     });
}

function onLoadReservation(data) {
    const table = $('.reservation .list');
    table.innerHTML = '';
    table.insertAdjacentHTML('afterbegin', templateReservations(data));
}

function loadMeetingRooms(success, fail) {
    fetch('/api/meeting-rooms')
    .then(response => {
        if (!response.ok) {
            fail('잠시후 다시 시도해주세요');
        }
        return response.json();
    }).then(data => {
        success(data);
    }).catch(error => {
        fail(error);
    });
}

function onLoadMeetingRooms(data) {
    const select = $('.reservation .add-form .meeting-room .select');
    select.innerHTML = '';
    select.insertAdjacentHTML('afterbegin', templateMeetingRooms(data));
    select.selectedIndex = 0;

    const table = $('.reservation table');
    table.innerHTML = '';
    table.insertAdjacentHTML('afterbegin', templateTableHeaderRow(data));
}

function onLoadFailed(error) {
    console.error(error); // tmp
}


/*** getter ***/
function getDate() {
    return $_value('.reservation .date-picker input');
}

function getMeetingRoomId() {
    const select = $('.reservation .add-form .meeting-room .select');
    return select[select.selectedIndex].value;
}


/*** Templates ***/
function templateMeetingRooms(data) {
    return `${data && data.map(templateMeetingRoom).join('')}`;
}

function templateMeetingRoom({id, name}) {
    return `<option value="${id}">${name}</option>`;
}

function templateTableHeaderRow(data) {
    return `<tr>${data && data.map(templateTableHeader).join('')}</tr>`;
}

function templateTableHeader({id, name}) {
    return `<th>${name}</th>`;
}

function templateReservations(data) {
    return `${data && data.map(templateReservation).join('')}`;
}

function templateReservation(data) {
    const json = JSON.stringify(data);
    return `<p>${json}</p>`;
}


/*** Utility ***/
function today() {
    const today = new Date();

    const month = today.getUTCMonth() + 1;
    const day = today.getUTCDate() + 1;
    const year = today.getUTCFullYear();

    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }

    return year + "-" + month + "-" + day;
}

function $(selector) {
    return document.querySelector(selector);
}

function $_value(selector) {
    return $(selector).value;
}
