// ============================================
// WEBSOCKET - DADOS EM TEMPO REAL
// ============================================

let stompClient = null;

function conectarWebSocket() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function(frame) {
        console.log('✅ WebSocket conectado: ' + frame);
        atualizarStatus(true);
        
        // Inscrever para receber leituras em tempo real
        stompClient.subscribe('/topic/leituras', function(message) {
            const leitura = JSON.parse(message.body);
            console.log('📡 Dado em tempo real:', leitura);
            
            // Atualizar card correspondente
            if (leitura.tipo === 'agua') {
                atualizarCard('agua', leitura.valor, leitura.alerta);
            } else if (leitura.tipo === 'energia') {
                atualizarCard('energia', leitura.valor, leitura.alerta);
            } else if (leitura.tipo === 'gas') {
                atualizarCard('gas', leitura.valor, leitura.alerta);
            }
            
            // Atualizar timestamp
            document.getElementById('ultimaAtualizacao').textContent = 
                new Date().toLocaleTimeString();
        });
        
        // Inscrever para receber alertas em tempo real
        stompClient.subscribe('/topic/alertas', function(message) {
            const alerta = JSON.parse(message.body);
            console.log('🔔 Alerta em tempo real:', alerta);
            
            // Recarregar lista de alertas
            carregarAlertas();
            
            // Mostrar notificação (se suportado)
            if (Notification.permission === 'granted') {
                new Notification('🚨 IoT Alerta', { body: alerta.mensagem });
            }
        });
        
    }, function(error) {
        console.error('❌ Erro no WebSocket:', error);
        atualizarStatus(false);
        
        // Tentar reconectar após 5 segundos
        setTimeout(conectarWebSocket, 5000);
    });
}

// Solicitar permissão para notificações
if (Notification.permission === 'default') {
    Notification.requestPermission();
}