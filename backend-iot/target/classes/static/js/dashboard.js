// ============================================
// DASHBOARD IoT RESIDENCIAL - LÓGICA PRINCIPAL
// ============================================

// Estado do dashboard
let dashboardState = {
    agua: { valor: 0, unidade: 'L/min', alerta: false },
    energia: { valor: 0, unidade: 'W', alerta: false },
    gas: { valor: 0, unidade: 'ppm', alerta: false },
    ultimaAtualizacao: null,
    alertas: [],
    modulos: []
};

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    console.log('🚀 Dashboard IoT Inicializado');
    
    // Carregar dados iniciais
    carregarDadosDashboard();
    
    // Carregar alertas
    carregarAlertas();
    
    // Carregar dicas
    carregarDicas();
    
    // Carregar módulos
    carregarModulos();
    
    // Conectar WebSocket
    conectarWebSocket();
    
    // Atualizar a cada 30 segundos (fallback)
    setInterval(() => {
        carregarDadosDashboard();
        carregarAlertas();
    }, 30000);
});

// Carregar dados do dashboard via REST API
async function carregarDadosDashboard() {
    try {
        const response = await fetch('/api/dashboard/dados');
        const data = await response.json();
        
        // Atualizar leituras
        if (data.ultimaAgua) {
            atualizarCard('agua', data.ultimaAgua.valor, data.ultimaAgua.alerta);
        }
        if (data.ultimaEnergia) {
            atualizarCard('energia', data.ultimaEnergia.valor, data.ultimaEnergia.alerta);
        }
        if (data.ultimoGas) {
            atualizarCard('gas', data.ultimoGas.valor, data.ultimoGas.alerta);
        }
        
        // Atualizar timestamp
        dashboardState.ultimaAtualizacao = new Date();
        document.getElementById('ultimaAtualizacao').textContent = 
            dashboardState.ultimaAtualizacao.toLocaleTimeString();
        
        // Atualizar gráficos se existirem
        if (window.chartsManager) {
            if (data.historicoAgua) window.chartsManager.atualizarGraficoAgua(data.historicoAgua);
            if (data.historicoEnergia) window.chartsManager.atualizarGraficoEnergia(data.historicoEnergia);
            if (data.historicoGas) window.chartsManager.atualizarGraficoGas(data.historicoGas);
        }
        
        console.log('✅ Dados do dashboard atualizados');
        
    } catch (error) {
        console.error('❌ Erro ao carregar dashboard:', error);
    }
}

// Atualizar card individual
function atualizarCard(tipo, valor, alerta) {
    const card = document.getElementById(`card-${tipo}`);
    if (!card) return;
    
    const valorElement = card.querySelector('.valor-principal');
    const alertaElement = card.querySelector('.alerta-indicador');
    
    if (valorElement) {
        valorElement.innerHTML = `${valor.toFixed(1)} <span class="unidade">${dashboardState[tipo].unidade}</span>`;
    }
    
    dashboardState[tipo].valor = valor;
    dashboardState[tipo].alerta = alerta;
    
    if (alertaElement) {
        if (alerta) {
            alertaElement.innerHTML = '⚠️ ALERTA';
            alertaElement.style.color = '#e74c3c';
            alertaElement.style.fontWeight = 'bold';
        } else {
            alertaElement.innerHTML = '✓ Normal';
            alertaElement.style.color = '#27ae60';
        }
    }
}

// Carregar alertas
async function carregarAlertas() {
    try {
        const response = await fetch('/api/dashboard/alertas');
        const data = await response.json();
        
        const container = document.getElementById('alertas-list');
        if (!container) return;
        
        if (data.alertas && data.alertas.length > 0) {
            container.innerHTML = data.alertas.map(alerta => `
                <div class="alerta-item ${alerta.severidade.toLowerCase()}">
                    <div class="alerta-mensagem">
                        <span class="alerta-severidade">[${alerta.severidade}]</span>
                        ${alerta.mensagem}
                        <small style="display: block; color: #666; font-size: 0.75rem;">
                            ${new Date(alerta.timestamp).toLocaleString()}
                        </small>
                    </div>
                    <button class="btn-resolver" onclick="resolverAlerta(${alerta.id})">
                        Resolver
                    </button>
                </div>
            `).join('');
        } else {
            container.innerHTML = '<div style="text-align: center; padding: 20px;">✅ Nenhum alerta ativo</div>';
        }
        
    } catch (error) {
        console.error('❌ Erro ao carregar alertas:', error);
    }
}

// Resolver alerta
async function resolverAlerta(id) {
    try {
        await fetch(`/api/dashboard/alertas/${id}/resolver`, { method: 'POST' });
        carregarAlertas();
        console.log(`✅ Alerta ${id} resolvido`);
    } catch (error) {
        console.error('❌ Erro ao resolver alerta:', error);
    }
}

// Carregar dicas de economia
async function carregarDicas() {
    try {
        const response = await fetch('/api/dicas');
        const dicas = await response.json();
        
        const container = document.getElementById('dicas-grid');
        if (!container) return;
        
        if (dicas && dicas.length > 0) {
            container.innerHTML = dicas.map(dica => `
                <div class="dica-card">
                    <h4>${dica.titulo}</h4>
                    <p>${dica.descricao}</p>
                    ${dica.economiaEstimada ? 
                        `<div class="economia-estimada">💰 Economia: ${dica.economiaEstimada}</div>` : ''}
                </div>
            `).join('');
        } else {
            container.innerHTML = '<div style="text-align: center; padding: 20px;">💡 Dicas em breve...</div>';
        }
        
    } catch (error) {
        console.error('❌ Erro ao carregar dicas:', error);
    }
}

// Carregar módulos
async function carregarModulos() {
    try {
        const response = await fetch('/api/modulos');
        const modulos = await response.json();
        
        const container = document.getElementById('modulos-grid');
        if (!container) return;
        
        dashboardState.modulos = modulos;
        
        container.innerHTML = modulos.map(modulo => `
            <div class="modulo-card ${modulo.ativo ? 'ativo' : 'inativo'}" 
                 onclick="toggleModulo('${modulo.nome}')">
                <span>${modulo.icone || '📦'}</span>
                <span>${modulo.nome}</span>
                <span>${modulo.ativo ? '✅' : '❌'}</span>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('❌ Erro ao carregar módulos:', error);
    }
}

// Alternar módulo
async function toggleModulo(nome) {
    try {
        const response = await fetch(`/api/modulos/${nome}/toggle`, { method: 'PUT' });
        const modulo = await response.json();
        
        console.log(`✅ Módulo ${nome} agora está ${modulo.ativo ? 'ativado' : 'desativado'}`);
        carregarModulos();
        
    } catch (error) {
        console.error('❌ Erro ao alternar módulo:', error);
    }
}

// Atualizar status da conexão
function atualizarStatus(conectado) {
    const statusElement = document.getElementById('status-conexao');
    if (statusElement) {
        if (conectado) {
            statusElement.innerHTML = '🟢 Online';
            statusElement.className = 'status-badge online';
        } else {
            statusElement.innerHTML = '🔴 Offline';
            statusElement.className = 'status-badge offline';
        }
    }
}