package com.mycompany.servidoratendimentorpc;

import com.mycompany.atendimentorpc.AtendimentoServiceGrpc;
import com.mycompany.atendimentorpc.PedidoCancelamento;
import com.mycompany.atendimentorpc.PedidoSenha;
import com.mycompany.atendimentorpc.ResultadoCancelamento;
import com.mycompany.atendimentorpc.Senha;
import com.mycompany.atendimentorpc.TipoAtendimento;
import com.mycompany.atendimentorpc.Vazio;
import io.grpc.stub.StreamObserver;
import java.util.LinkedList;

public class AtendimentoServiceImpl
        extends AtendimentoServiceGrpc.AtendimentoServiceImplBase {

    // v2: regra do tempo estimado e atraso artificial para testar o deadline
    private static final int MINUTOS_POR_PESSOA = 3;
    private static final long ATRASO_CHAMAR_MS = 2000;

    private int proximoNumero = 1;
    // v2: senhas emitidas e ainda não chamadas, em ordem de chegada
    private final LinkedList<Senha> fila = new LinkedList<>();

    private synchronized int gerarNumero() {
        return proximoNumero++;
    }

    // v2: calcula o tempo pelas pessoas à frente e coloca a senha no fim da fila
    private synchronized Senha entrarNaFila(Senha senha) {
        Senha comTempo = senha.toBuilder()
                .setTempoEstimado(fila.size() * MINUTOS_POR_PESSOA).build();
        fila.add(comTempo);
        return comTempo;
    }

    // v2: true se a senha estava na fila e foi removida
    private synchronized boolean sairDaFila(String codigo) {
        return fila.removeIf(s -> s.getCodigo().equals(codigo));
    }

    // v2: primeira da fila, ou null se a fila estiver vazia
    private synchronized Senha tirarPrimeira() {
        return fila.poll();
    }

    @Override
    public void pegarSenha(PedidoSenha req, StreamObserver<Senha> observador) {
        int numero = gerarNumero();
        String prefixo = req.getTipo() == TipoAtendimento.PRIORITARIO ? "P" : "N";
        String codigo = String.format("%s-%03d", prefixo, numero);
        String mensagem = req.getNome() + ", sua senha é " + codigo
                + " (totem: " + req.getLocal() + ")";
        System.out.println("Senha " + codigo + " para " + req.getNome()
                + " no totem " + req.getLocal());
        Senha resposta = Senha.newBuilder()
                .setNumero(numero).setCodigo(codigo).setMensagem(mensagem).build();
        resposta = entrarNaFila(resposta); // v2
        System.out.println("  tempo estimado: " + resposta.getTempoEstimado() + " min"); // v2
        observador.onNext(resposta);
        observador.onCompleted();
    }

    // v2
    @Override
    public void cancelarSenha(PedidoCancelamento req,
            StreamObserver<ResultadoCancelamento> observador) {
        boolean cancelada = sairDaFila(req.getCodigo());
        String mensagem = cancelada
                ? "Senha " + req.getCodigo() + " cancelada."
                : "Senha " + req.getCodigo() + " não está na fila.";
        System.out.println(mensagem);
        ResultadoCancelamento resposta = ResultadoCancelamento.newBuilder()
                .setCancelada(cancelada).setMensagem(mensagem).build();
        observador.onNext(resposta);
        observador.onCompleted();
    }

    // v2
    @Override
    public void chamarProxima(Vazio req, StreamObserver<Senha> observador) {
        try {
            Thread.sleep(ATRASO_CHAMAR_MS); // atraso artificial (teste do deadline)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Senha proxima = tirarPrimeira();
        Senha resposta;
        if (proxima == null) {
            resposta = Senha.newBuilder()
                    .setMensagem("Fila vazia: nenhuma senha para chamar.").build();
            System.out.println("Chamar próxima: fila vazia");
        } else {
            resposta = proxima;
            System.out.println("Chamando senha " + proxima.getCodigo());
        }
        observador.onNext(resposta);
        observador.onCompleted();
    }
}
