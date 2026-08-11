function alternarTema() {
    document.body.classList.toggle('tema-escuro');

    const escuro = document.body.classList.contains('tema-escuro');
    localStorage.setItem('tema', escuro ? 'escuro' : 'claro');

    document.getElementById('botao-tema').textContent = escuro ? '☀️' : '🌙';
}

document.addEventListener('DOMContentLoaded', function () {
    const temaSalvo = localStorage.getItem('tema');
    if (temaSalvo === 'escuro') {
        document.body.classList.add('tema-escuro');
        document.getElementById('botao-tema').textContent = '☀️';
    }
});