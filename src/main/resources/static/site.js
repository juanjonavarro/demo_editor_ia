
function obtenerContexto(elemento) {
        var texto = elemento.textContent;
        var textoPrevio1 = textoPrevio(elemento, 2);
        var textoPrevio2 = textoPrevio(elemento, 1);
        var textoSiguiente1 = textoSiguiente(elemento, 1);
        var textoSiguiente2 = textoSiguiente(elemento, 2);

        return {
            texto: texto,
            textoPrevio1: textoPrevio1,
            textoPrevio2: textoPrevio2,
            textoSiguiente1: textoSiguiente1,
            textoSiguiente2: textoSiguiente2
        };
}
function enter() {
    var div = document.createElement('div');
    div.className = 'iabuttons';

    div.appendChild(creaBoton('/ampliar', 'bi-arrows-angle-expand', 'ia_ampliar'));
    div.appendChild(creaBoton('/resumir', 'bi-arrows-angle-contract', 'ia_resumir'));
    div.appendChild(creaBoton('/puntos',  'bi-list-ul', 'ia_puntos'));
    div.appendChild(creaBoton('/emojis',  'bi-emoji-smile', 'ia_emoji'));
    div.appendChild(creaBoton('/ingles',  'bi-globe-americas', 'ia_emoji'));


    this.insertBefore(div, this.firstChild);
}

function creaBoton(url, texto, clase) {
    var boton = document.createElement('a');
    var icon = document.createElement('i');
    icon.className = 'bi ' + texto;
    boton.appendChild(icon);
    boton.className = clase;
    boton.addEventListener('click', function() {
        var parent = this.parentNode.parentNode;
        parent.querySelector('.iabuttons').remove();
        var contexto = obtenerContexto(parent);
        console.log(contexto);
        enviarLLM(parent, contexto, url);
    });
    return boton;
}

function exit() {
    var button = this.querySelector('.iabuttons');
    if (button) {
        button.remove();
    }
}

function enviarLLM(elemento, contexto, url) {
    // Llamamos a la API
    // con el texto y el contexto
    elemento.className = "flash";
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(contexto)
    })
    .then(response => response.json())
    .then(data => {
        var template = document.createElement('template');
        template.innerHTML = data.texto;
        console.log(data.texto);
        console.log(template.content.childNodes);
        console.log(template.content.childNodes.length);
        while (template.content.childNodes.length > 0) {
            console.log(template.content.childNodes[0]);
            elemento.parentNode.insertBefore(template.content.childNodes[0], elemento);
        }
        elemento.remove();
        putButtonsOnParagraphs();
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}


function textoPrevio(elemento, n) {
    if (n == 0) {
        return elemento?.textContent;
    }
    if (!elemento.previousSibling) {
        return "";
    }
    if (elemento.previousSibling?.nodeType == 3) {
        return textoPrevio(elemento.previousSibling, n);
    } else {
        return textoPrevio(elemento.previousSibling, n - 1);
    }
    return null;
}

function textoSiguiente(elemento, n) {
    if (n == 0) {
        return elemento?.textContent;
    }
    if (!elemento.nextSibling) {
        return "";
    }
    if (elemento.nextSibling?.nodeType == 3) {
        return textoSiguiente(elemento.nextSibling, n);
    } else {
        return textoSiguiente(elemento.nextSibling, n - 1);
    }
    return null;
}

function putButtonsOnParagraphs() {
    console.log('putButtonsOnParagraphs');
    var buttons = document.querySelectorAll('.iabuttons');
    for (var j = 0; j < buttons.length; j++) {
        buttons[j].remove();
    }
    var paragraphs = document.querySelectorAll('#contenido > *');
    for (var i = 0; i < paragraphs.length; i++) {
        paragraphs[i].removeEventListener('mouseenter', enter);
        paragraphs[i].addEventListener('mouseenter', enter);

        paragraphs[i].removeEventListener('mouseleave', exit);
        paragraphs[i].addEventListener('mouseleave', exit)
    }
    var contenido = document.querySelectorAll('#contenido');
    for (var i = 0; i < contenido.length; i++) {
        contenido[i].addEventListener('mouseleave', function() {
            console.log('mouseleave');
            var buttons = this.querySelectorAll('.iabuttons');
            for (var j = 0; j < buttons.length; j++) {
                buttons[j].remove();
            }
        });
        contenido[i].addEventListener('input', putButtonsOnParagraphs);

    }
}

putButtonsOnParagraphs();