// ============================================
// GERENCIADOR DE GRÁFICOS - CHART.JS
// ============================================

class ChartsManager {
    constructor() {
        this.graficoAgua = null;
        this.graficoEnergia = null;
        this.graficoGas = null;
        this.inicializarGraficos();
    }
    
    inicializarGraficos() {
        // Gráfico de Água
        const ctxAgua = document.getElementById('grafico-agua')?.getContext('2d');
        if (ctxAgua) {
            this.graficoAgua = new Chart(ctxAgua, {
                type: 'line',
                data: {
                    labels: [],
                    datasets: [{
                        label: 'Consumo de Água (L/min)',
                        data: [],
                        borderColor: '#3498db',
                        backgroundColor: 'rgba(52, 152, 219, 0.1)',
                        tension: 0.4,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: true,
                    plugins: {
                        legend: { position: 'top' },
                        tooltip: { mode: 'index', intersect: false }
                    },
                    scales: {
                        y: { beginAtZero: true, title: { display: true, text: 'Litros/minuto' } },
                        x: { title: { display: true, text: 'Horário' } }
                    }
                }
            });
        }
        
        // Gráfico de Energia
        const ctxEnergia = document.getElementById('grafico-energia')?.getContext('2d');
        if (ctxEnergia) {
            this.graficoEnergia = new Chart(ctxEnergia, {
                type: 'line',
                data: {
                    labels: [],
                    datasets: [{
                        label: 'Consumo de Energia (W)',
                        data: [],
                        borderColor: '#f39c12',
                        backgroundColor: 'rgba(243, 156, 18, 0.1)',
                        tension: 0.4,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: true,
                    plugins: {
                        legend: { position: 'top' },
                        tooltip: { mode: 'index', intersect: false }
                    },
                    scales: {
                        y: { beginAtZero: true, title: { display: true, text: 'Watts' } },
                        x: { title: { display: true, text: 'Horário' } }
                    }
                }
            });
        }
        
        // Gráfico de Gás
        const ctxGas = document.getElementById('grafico-gas')?.getContext('2d');
        if (ctxGas) {
            this.graficoGas = new Chart(ctxGas, {
                type: 'line',
                data: {
                    labels: [],
                    datasets: [{
                        label: 'Concentração de Gás (ppm)',
                        data: [],
                        borderColor: '#e74c3c',
                        backgroundColor: 'rgba(231, 76, 60, 0.1)',
                        tension: 0.4,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: true,
                    plugins: {
                        legend: { position: 'top' },
                        tooltip: { mode: 'index', intersect: false }
                    },
                    scales: {
                        y: { 
                            beginAtZero: true, 
                            title: { display: true, text: 'ppm' },
                            grid: { color: 'rgba(231, 76, 60, 0.1)' }
                        },
                        x: { title: { display: true, text: 'Horário' } }
                    }
                }
            });
        }
        
        console.log('📊 Gráficos inicializados');
    }
    
    atualizarGraficoAgua(dados) {
        if (!this.graficoAgua || !dados) return;
        
        const labels = dados.map(d => new Date(d.timestamp).toLocaleTimeString());
        const valores = dados.map(d => d.valor);
        
        this.graficoAgua.data.labels = labels.slice(-20);
        this.graficoAgua.data.datasets[0].data = valores.slice(-20);
        this.graficoAgua.update();
    }
    
    atualizarGraficoEnergia(dados) {
        if (!this.graficoEnergia || !dados) return;
        
        const labels = dados.map(d => new Date(d.timestamp).toLocaleTimeString());
        const valores = dados.map(d => d.valor);
        
        this.graficoEnergia.data.labels = labels.slice(-20);
        this.graficoEnergia.data.datasets[0].data = valores.slice(-20);
        this.graficoEnergia.update();
    }
    
    atualizarGraficoGas(dados) {
        if (!this.graficoGas || !dados) return;
        
        const labels = dados.map(d => new Date(d.timestamp).toLocaleTimeString());
        const valores = dados.map(d => d.valor);
        
        this.graficoGas.data.labels = labels.slice(-20);
        this.graficoGas.data.datasets[0].data = valores.slice(-20);
        this.graficoGas.update();
    }
}

// Inicializar gerenciador de gráficos quando a página carregar
document.addEventListener('DOMContentLoaded', () => {
    window.chartsManager = new ChartsManager();
});