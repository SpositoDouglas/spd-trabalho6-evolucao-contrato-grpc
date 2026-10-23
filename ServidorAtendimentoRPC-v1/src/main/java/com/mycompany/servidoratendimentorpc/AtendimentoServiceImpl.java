package com.mycompany.servidoratendimentorpc;

import com.mycompany.atendimentorpc.AtendimentoServiceGrpc;
import com.mycompany.atendimentorpc.PedidoSenha;
import com.mycompany.atendimentorpc.Senha;
import com.mycompany.atendimentorpc.TipoAtendimento;
import io.grpc.stub.StreamObserver;

public class AtendimentoServiceImpl
        extends AtendimentoServiceGrpc.AtendimentoServiceImplBase {

    private int proximoNumero = 1;

    private synchronized int gerarNumero() {
        return proximoNumero++;
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
        observador.onNext(resposta);
        observador.onCompleted();
    }
}
