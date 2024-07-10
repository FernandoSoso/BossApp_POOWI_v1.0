document.addEventListener('input', function(event) {
    event.target.classList.toggle('input-filled', event.target.value !== '');

});

document.addEventListener('focusout', function(event){
    event.target.classList.toggle('input-filled', event.target.value !== '');
});