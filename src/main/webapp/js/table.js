let offset = 0;
let itens = 0;

function inicializarTabela(entidade, tableTitleId, tableHeadId, tableBodyId, cols, maxItens) {
    document.getElementById(tableTitleId).innerHTML = entidade;

    inserirCabecalho(tableHeadId, cols);
    atualizarTabela(entidade, tableBodyId, cols, maxItens);

    document.getElementById(incrementId).addEventListener('click', function() {
        incrementarOffset(entidade,tableBodyId, cols, maxItens);
    });
    document.getElementById(decrementId).addEventListener('click', function() {
        decrementarOffset(entidade, tableBodyId, cols, maxItens);
    });
}

function incrementarOffset(url, tableId, cols, maxItens) {
    if (itens === maxItens) {
        offset += maxItens;
        atualizarTabela(url, tableId, cols);
    }
    else{
        alert("Não há mais itens para serem exibidos!");
    }
}

function decrementarOffset(url, tableId, cols, maxItens) {
    if (offset > 0) {
        offset -= maxItens;
        itens = 0;
        atualizarTabela(url, tableId, cols);
    }
    else{
        alert("Não há mais itens para serem exibidos!");
    }
}

function inserirCabecalho(id, cols){
    document.getElementById(id).innerHTML = '';

    let row = document.createElement('tr');

    cols.forEach(col => {
        let cell = document.createElement('th');
        cell.appendChild(document.createTextNode(col.toUpperCase()));
        row.appendChild(cell);
    });

    document.getElementById(id).appendChild(row);
}

function atualizarTabela(url, tableId, cols) {
    fetch(url + '?async=true&offset=' + offset, {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            document.getElementById(tableId).innerHTML = '';

            if (data !== null) {
                if (data.length !== 0){
                    data.forEach(json => {
                        itens++;
                        let row = document.createElement('tr');

                        cols.forEach(col => {
                            let cell = document.createElement('td');
                            let p = document.createElement('p');

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
                    });
                }
                else{
                    let h3 = document.createElement('H3');
                    h3.textContent = "Não há itens cadastrados para serem exibidos!";
                    document.getElementById(tableId).appendChild(h3);
                }
            }

        })
        .catch(error => console.error('Erro:', error));
}