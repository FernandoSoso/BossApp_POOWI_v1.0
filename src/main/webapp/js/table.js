let offset = 0;
let itens = 0;

function inicializarPagina(entidade, tableTitleId, tableHeadId, tableBodyId, cols, colsNames, maxItens) {
    document.getElementById(tableTitleId).innerHTML = entidade;

    addCadastrarTableButtonEvent(entidade);
    atualizarSidebar(entidade);
    preencherSelect();
    inserirCabecalho(tableHeadId, colsNames);
    atualizarTabela(entidade, tableBodyId, cols, maxItens);

    document.getElementById(incrementId).addEventListener('click', function() {
        incrementarOffset(entidade,tableBodyId, cols, maxItens);
    });
    document.getElementById(decrementId).addEventListener('click', function() {
        decrementarOffset(entidade, tableBodyId, cols, maxItens);
    });
}

function incrementarOffset(entidade, tableId, cols, maxItens) {
    if (itens === maxItens) {
        offset += maxItens;
        atualizarTabela(entidade, tableId, cols);
    }
    else{
        alert("Não há mais itens para serem exibidos!");
    }
}

function decrementarOffset(entidade, tableId, cols, maxItens) {
    if (offset > 0) {
        offset -= maxItens;
        itens = 0;
        atualizarTabela(entidade, tableId, cols);
    }
    else{
        alert("Não há mais itens para serem exibidos!");
    }
}

function inserirCabecalho(headerId, cols){
    document.getElementById(headerId).innerHTML = '';

    let row = document.createElement('tr');
    let cell = document.createElement('th');
    row.appendChild(cell);

    cols.forEach(col => {
        cell = document.createElement('th');
        cell.appendChild(document.createTextNode(col.toUpperCase()));
        row.appendChild(cell);
    });

    let scrollbarSpacer = document.createElement('th');
    scrollbarSpacer.id = "scrollbarSpacer";
    scrollbarSpacer.style.width = "10px";
    scrollbarSpacer.style.padding = "5px";
    row.appendChild(scrollbarSpacer);

    document.getElementById(headerId).appendChild(row);
}

function atualizarTabela(entidade, tableId, cols) {
    fetch(entidade + '?async=true&offset=' + offset, {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            document.getElementById(tableId).innerHTML = '';

            if (data !== null) {
                if (data.length !== 0){
                    data.forEach(json => {
                        if (itens !== 15){
                            let row = document.createElement('tr');

                            row.appendChild(addShowDetailsButton(entidade,json['cod']));

                            cols.forEach(col => {
                                cell = document.createElement('td');
                                p = document.createElement('p');

                                if (json[col] === null || json[col] === undefined || json[col] === -1 || json[col] === ""){
                                    p.appendChild(document.createTextNode("-"));
                                }
                                else{
                                    p.appendChild(document.createTextNode(json[col]));
                                }
                                p.style.overflow = "overlay";
                                cell.appendChild(p);
                                row.appendChild(cell);
                            });
                            document.getElementById(tableId).appendChild(row);
                        }

                        itens++;
                    });
                }
                else{
                    let h3 = document.createElement('H3');
                    h3.textContent = "Não há itens cadastrados para serem exibidos!";
                    document.getElementById(tableId).appendChild(h3);
                }
                atualizarNavButtons();
            }

            itens--;
        })
        .catch(error => console.error('Erro:', error));
}

function atualizarNavButtons(){

    let incrementButton = document.getElementById('increment');
    let decrementButton = document.getElementById('decrement');

    if (itens%16 !== 0 || itens === 0){
        incrementButton.classList.add('nav-button-disabled');
        if (incrementButton.classList.contains('nav-button-active')){
            incrementButton.classList.remove('nav-button-active');
        }
    }
    else{
        incrementButton.classList.add('nav-button-active');
        if (incrementButton.classList.contains('nav-button-disabled')){
            incrementButton.classList.remove('nav-button-disabled');
        }
    }

    if (offset === 0){
        decrementButton.classList.add('nav-button-disabled');
        if (decrementButton.classList.contains('nav-button-active')){
            decrementButton.classList.remove('nav-button-active');
        }
    }
    else{
        decrementButton.classList.add('nav-button-active');
        if (decrementButton.classList.contains('nav-button-disabled')){
            decrementButton.classList.remove('nav-button-disabled');
        }
    }
}

function addCadastrarTableButtonEvent(entidade){
    document.getElementById('cadastrarTableButton').addEventListener('click', function() {
        document.getElementById('header-modal-title').innerHTML = 'CADASTRAR ' + entidade.toUpperCase();

        document.getElementById('persistModal').querySelectorAll('input, select, textarea').forEach(input => {
            input.value = '';
            input.classList.remove('input-filled');
            if (input.tagName === 'SELECT'){
                input.innerHTML = '';
            }
        });
    });
}

function addShowDetailsButton(entidade, rowCod){
    // Adiciona botão de visualização
    let cell = document.createElement('td');
    let button = document.createElement('a');
    let img = document.createElement('img');

    img.src = "img/show-details.png";

    button.appendChild(document.createTextNode("VISUALIZAR"));
    button.appendChild(img);
    button.classList.add('show-details-button');
    button.setAttribute("data-toggle", "modal");
    button.setAttribute("data-target", "#persistModal");
    button.setAttribute("data-row-cod", rowCod);

    button.addEventListener('click', addShowDetailsEvent);

    cell.appendChild(button);

    return cell;
}

function addShowDetailsEvent(event){
    document.getElementById('header-modal-title').innerHTML = 'VISUALIZAR ' + entidade.toUpperCase();

    fetch(entidade + '?async=true&codMotorista=' + event.currentTarget.getAttribute('data-row-cod'))
        .then(response => response.json())
        .then(dataUnique => {
            if (dataUnique !== null){
                dataUnique.forEach((json) => {
                    for (let key in json){
                        let element = document.getElementById(key);
                        element.innerHTML = "";

                        if ((typeof json[key]) === "object"){
                            let option = document.createElement('option');
                            option.selected = true;
                            option.value = json[key]['cod'];

                            if (key === "caminhao"){
                                option.innerHTML = json[key]['placa'];
                            }
                            else if (key === "motorista"){
                                option.innerHTML = json[key]['nome'];
                            }

                            element.appendChild(option)
                            element.classList.add('input-filled');
                        }
                        else{
                            element.value = json[key];
                        }

                        element.classList.add('input-filled');
                    }
                });
            }
            else{
                alert('Erro ao buscar dados');
            }
        });
}

function preencherSelect(){
    document.querySelectorAll('select').forEach(select => {
        if (select.id === 'caminhao' || select.id === 'motorista') {

            select.addEventListener('click', function () {
                let selectedOption = select.options[select.selectedIndex];
                select.innerHTML = '';

                let option = document.createElement('option');
                option.value = '';
                option.innerHTML = '';

                select.appendChild(option);

                fetch(select.id + '?async=true')
                    .then(response => response.json())
                    .then(data => {
                        data.forEach(json => {
                            option = document.createElement('option');
                            option.value = json['cod'];
                            if (select.id === 'caminhao') {
                                option.innerHTML = json['placa'];
                            } else if (select.id === 'motorista') {
                                option.innerHTML = json['nome'];
                            }

                            if (selectedOption !== undefined && selectedOption.value === option.value) {
                                option.selected = true;
                            }

                            select.appendChild(option);
                        });
                    })
                    .catch(error => console.error('Erro:', error));
            });
        }
    });
}
