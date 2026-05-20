// URL base da sua API Spring Boot rodando na branch main
const API_URL = 'http://localhost:8080/api/mesas';

// Captura os elementos do HTML para podermos interagir com eles
const gridMesas = document.getElementById('gridMesas');
const formMesa = document.getElementById('formMesa');

// ==========================================
// 1. FUNÇÃO QUE BUSCA AS MESAS NO JAVA (GET)
// ==========================================
async function buscarMesas() {
    try {
        // Faz a requisição HTTP GET para o MesaController do Java
        const resposta = await fetch(API_URL);
        
        // Converte a lista de mesas vinda do banco H2 para JSON
        const mesas = await resposta.json();
        
        //Chama a função para desenhar essas mesas na tela
        renderizarMesas(mesas);
    } catch (erro) {
        console.error("Erro ao conectar com a API Java:", erro);
        gridMesas.innerHTML = `
            <p class="erro">
                🛡️ Não foi possível conectar ao servidor Java.<br>
                Certifique-se de que a API está rodando no terminal com './mvnw spring-boot:run'!
            </p>`;
    }
}

// ==========================================
// 2. FUNÇÃO QUE RENDERIZA OS CARDS NA TELA
// ==========================================
function renderizarMesas(mesas) {
    gridMesas.innerHTML = ''; // Limpa o grid para não duplicar dados

    // Se o banco de dados estiver vazio, exibe uma mensagem amigável
    if (mesas.length === 0) {
        gridMesas.innerHTML = `
            <div class="aviso-vazio">
                <h3>Nenhuma taverna ativa por enquanto...</h3>
                <p>Preencha o formulário acima para abrir sua primeira mesa de jogo!</p>
            </div>`;
        return;
    }

    // Se houver mesas, cria um bloco visual (card) para cada uma delas
    mesas.forEach(mesa => {
        const card = document.createElement('div');
        card.className = 'card-mesa';
        card.innerHTML = `
            <h3>📜 ${mesa.nome}</h3>
            <p><strong>ID da Mesa:</strong> #<span>${mesa.id}</span></p>
            <p><strong>Sistema:</strong> <span>${mesa.sistema}</span></p>
            <button class="btn-ver" onclick="alert('Lobby da mesa ${mesa.nome} em desenvolvimento!')">Entrar no Lobby 🎲</button>
        `;
        gridMesas.appendChild(card);
    });
}

// ==========================================
// 3. FUNÇÃO QUE ENVIA UMA NOVA MESA (POST)
// ==========================================
formMesa.addEventListener('submit', async (e) => {
    e.preventDefault(); // Impede a página de recarregar ao clicar no botão

    // Pega os valores digitados nos campos de texto
    const nome = document.getElementById('nomeMesa').value;
    const sistema = document.getElementById('sistemaMesa').value;

    // Monta o objeto exatamente no formato que a classe Mesa espera no Java
    const novaMesa = { nome, sistema };

    try {
        // Envia os dados via POST para o MesaController do Java
        const resposta = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(novaMesa)
        });

        if (resposta.ok) {
            formMesa.reset(); // Limpa os campos do formulário se der certo
            buscarMesas();    // Recarrega a listagem para mostrar o card novo na hora
        } else {
            const erroApi = await resposta.json();
            alert(`Falha da API (Erro ${resposta.status}): ${erroApi.message || 'Dados inválidos!'}`);
        }
    } catch (erro) {
        alert("Erro de conexão ao tentar salvar a mesa!");
    }
});

// ==========================================
// INICIALIZAÇÃO
// ==========================================
// Executa a busca automática assim que o arquivo HTML abre no navegador
buscarMesas();