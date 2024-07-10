let offset = 0;
let itens = 0;

function inicializarPagina(entidade, cols, colsNames, maxItens) {
    document.getElementById('page-title').innerHTML = entidade;

    addCadastrarTableButtonEvent(entidade);
    atualizarSidebar(entidade);
    fillSelects();
    inserirCabecalho('table-header', colsNames);
    atualizarTabela(entidade, 'table-body', cols, maxItens);

    document.getElementById(incrementId).addEventListener('click', function() {
        incrementarOffset(entidade,'table-body', cols, maxItens);
    });
    document.getElementById(decrementId).addEventListener('click', function() {
        decrementarOffset(entidade, 'table-body', cols, maxItens);
    });
}

function incrementarOffset(entidade, tableId, cols, maxItens) {
    if (itens !== maxItens) {
        alert("Não há mais itens para serem exibidos!");
        return;
    }
    offset += maxItens;
    atualizarTabela(entidade, tableId, cols, maxItens);
}

function decrementarOffset(entidade, tableId, cols, maxItens) {
    if (offset <= 0) {
        alert("Não há mais itens para serem exibidos!");
        return;
    }
    offset -= maxItens;
    itens = 0;
    atualizarTabela(entidade, tableId, cols, maxItens);
}

function inserirCabecalho(headerId, cols) {
    const header = document.getElementById(headerId);
    header.innerHTML = '';

    const row = document.createElement('tr');
    row.appendChild(document.createElement('th'));

    cols.forEach(col => {
        const cell = document.createElement('th');
        cell.textContent = col.toUpperCase();
        row.appendChild(cell);
    });

    const scrollbarSpacer = document.createElement('th');
    scrollbarSpacer.id = "scrollbarSpacer";
    scrollbarSpacer.style.cssText = "width: 10px; padding: 5px;";
    row.appendChild(scrollbarSpacer);

    header.appendChild(row);
}

function atualizarTabela(entidade, tableId, cols, maxItens) {
    fetch(entidade + '?async=true&offset=' + offset + '&operacao=selectAll&limit='+(maxItens+1))
        .then(response => response.json())
        .then(data => {
            document.getElementById(tableId).innerHTML = '';

            if (data !== null) {
                let auxItens = 0;
                if (data.length !== 0){
                    data.forEach(json => {
                        if (auxItens !== maxItens){
                            let row = document.createElement('tr');

                            row.appendChild(addShowDetailsButton(entidade,json['cod']));

                            cols.forEach(col => {
                                cell = document.createElement('td');
                                p = document.createElement('p');

                                if (!json[col]){
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

                        auxItens++;
                    });
                }
                else{
                    let h3 = document.createElement('H3');
                    h3.textContent = "Não há itens cadastrados para serem exibidos!";
                    document.getElementById(tableId).appendChild(h3);
                }

                atualizarNavButtons(auxItens);

                if (auxItens === (maxItens+1)){
                    itens = auxItens-1;
                }
                else {
                    itens = auxItens;
                }

            }
        })
        .catch(error => console.error('Erro:', error));
}

function atualizarNavButtons(auxItens) {
    const updateButtonState = (button, condition) => {
        button.classList.toggle('nav-button-disabled', condition);
        button.classList.toggle('nav-button-active', !condition);
    };

    updateButtonState(document.getElementById('increment'), auxItens % 16 !== 0 || auxItens === 0);
    updateButtonState(document.getElementById('decrement'), offset === 0);
}

function addCadastrarTableButtonEvent(entidade) {
    document.getElementById('cadastrarTableButton').addEventListener('click', () => {
        const modalTitle = document.getElementById('header-modal-title');
        const persistButton = document.getElementById('persistButton');
        const persistForm = document.getElementById('persistForm');
        const inputs = document.querySelectorAll('#persistModal input, #persistModal select, #persistModal textarea');

        modalTitle.innerHTML = `CADASTRAR ${entidade.toUpperCase()}`;
        persistButton.innerHTML = 'CADASTRAR';
        persistForm.href = entidade;
        inputs.forEach(input => {
            input.value = '';
            input.classList.remove('input-filled');
        });
        document.getElementById('operacao').value = 'insert';
    });
}

function addShowDetailsButton(entidade, rowCod) {
    const cell = document.createElement('td');
    const button = document.createElement('a');
    button.innerHTML = `VISUALIZAR <img src="img/show-details.png">`;
    button.className = 'show-details-button';
    button.dataset.toggle = "modal";
    button.dataset.target = "#showDetailsModal";
    button.dataset.rowCod = rowCod;
    button.addEventListener('click', addDetailsEvent);
    cell.appendChild(button);
    return cell;
}

function addDetailsEvent(event){
    const modalTitle = document.getElementById('showDetails-modal-title');
    modalTitle.innerHTML = `VISUALIZAR ${entidade.toUpperCase()}`;
    modalTitle.setAttribute('data-cod', event.currentTarget.getAttribute('data-row-cod'))

    fetch(`${entidade}?async=true&cod=${event.currentTarget.getAttribute('data-row-cod')}&operacao=selectUnique`)
        .then(response => response.json())
        .then(json => {
            if (json !== null){
                document.getElementById('showDetailsModal').querySelectorAll('td p').forEach(cell => {
                    cell.innerHTML = "-";
                    cell.classList.add('empty-table-cell');
                });
                for (let key in json) {
                    const addContent = (key, content) => {
                        if (cell) {
                            let cell = document.getElementById(`${key}-info-table`);
                            if (cell){
                                if (key === "estado") {
                                    cell.classList.add('success-badge');
                                }

                                if (content) {
                                    cell.innerHTML = content;
                                    cell.classList.toggle('empty-table-cell');
                                }
                            }
                        }
                    }

                    let content = json[key];

                    if ((typeof content) === "object") {
                        for (let subKey in content) {
                            addContent(subKey, content[subKey]);
                        }
                    }
                    else {
                        addContent(key, content);
                    }
                }
            }
            else{
                alert('Erro ao buscar dados');
            }
        });

    let deleteButton = document.getElementById('showDetailsDeleteButton');
    deleteButton.setAttribute('data-cod', event.currentTarget.getAttribute('data-row-cod'));
    deleteButton.addEventListener('click', addDeleteButtonEvent);

    let editButton = document.getElementById('showDetailsEditButton');
    editButton.setAttribute('data-cod', event.currentTarget.getAttribute('data-row-cod'));
    editButton.addEventListener('click', addEditButtonEvent);
}

function addEditButtonEvent(event) {
    const headerTitle = document.getElementById('header-modal-title');
    const persistButton = document.getElementById('persistButton');
    const persistForm = document.getElementById('persistForm');
    const operationInput = document.getElementById('operacao');
    const codInput = document.getElementById('cod');

    headerTitle.innerHTML = `EDITAR ${entidade.toUpperCase()}`;
    persistButton.innerHTML = 'EDITAR';
    persistForm.href = `${entidade}?async=true`;
    operationInput.value = 'update';
    codInput.value = event.currentTarget.getAttribute('data-cod');

    fetch(`${entidade}?async=true&cod=${event.currentTarget.getAttribute('data-cod')}&operacao=selectUnique`)
        .then(response => response.json())
        .then(json => {
            if (json) {
                Object.keys(json).forEach(key => {
                    const element = document.getElementById(key);
                    if (element) {
                        if (element.tagName === 'SELECT' && element.classList.contains('static')) {
                            element.value = json[key];
                        }
                        else {
                            element.innerHTML = '';

                            if (typeof json[key] === "object") {
                                const option = new Option(json[key]['nome'] || json[key]['placa'], json[key]['cod'], true, true);
                                element.add(option);
                            } else {
                                element.value = json[key];
                            }
                        }

                        element.classList.add('input-filled');
                    }
                });
            } else {
                alert('Erro ao buscar dados');
            }
        })
        .catch(error => console.error('Erro:', error));
}

function addDeleteButtonEvent(event) {
    document.getElementById('deleteButton').href = `${entidade}?async=false&cod=${event.currentTarget.getAttribute('data-cod')}&operacao=delete`;
}

function fillSelects() {
    document.querySelectorAll('select.dinamic').forEach(select => {
        if (select.id === 'caminhao' || select.id === 'motorista') {
            select.addEventListener('focus', () => {
                const selectedOptionValue = select.value;
                fetch(select.id + '?async=true&operacao=selectAll')
                    .then(response => response.json())
                    .then(data => {
                        select.innerHTML = '<option value=""></option>' + data.map(json => {
                            const value = json['cod'];
                            const text = select.id === 'caminhao' ? json['placa'] : json['nome'];
                            const selected = value === selectedOptionValue ? ' selected' : '';
                            return `<option value="${value}"${selected}>${text}</option>`;
                        }).join('');
                    })
                    .catch(error => console.error('Erro:', error));
            });
        }
    });
}
