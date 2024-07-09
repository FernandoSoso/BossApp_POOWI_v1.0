document.addEventListener('input', function(event) {
    if (event.target.value !== '') {
        event.target.classList.add('input-filled');
    } else {
        event.target.classList.remove('input-filled');
    }
});

document.addEventListener('focusout', function(event){
    if (!(event.target.value === '')){
        event.target.classList.add('input-filled');
    }
    else{
        event.target.classList.remove('input-filled');
    }
});