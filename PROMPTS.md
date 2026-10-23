# PROMPTS.md — Trabalho 6: Evolução do contrato (a Central muda, o totem não)

Registro dos prompts usados com o assistente de IA (Claude Code - Opus 5.5 Esforço Alto) e do que foi respondido e feito em cada um.

- **Projeto:** Central de Senhas em gRPC/Java (gRPC, protobuf, Maven, Apache NetBeans 31, JDK 25)
- **Pasta:** `Documentos/Trabalho gRPC`
- **Projetos:** `ServidorAtendimentoRPC` (Central v2), `ClienteAtendimentoRPC` (totem v2), `ServidorAtendimentoRPC-v1` e `ClienteAtendimentoRPC-v1` (versões antigas, congeladas)

---

## Prompt 1 — Pedido inicial do trabalho (com a Parte 1)

**Prompt (resumo):** sou estudante de Sistemas Distribuídos e quero acrescentar o trabalho 6 ao projeto gRPC da forma mais simples possível, no estilo da aula, sem bibliotecas extras. Faça uma parte por vez, explique cada linha e espere "ok" antes da próxima. O prompt trazia:
- o código atual (`atendimento.proto`, `AtendimentoServiceImpl`, `ServidorAtendimentoRPC`, `TelaTotem`);
- o slide da professora;
- as regras: só unário, `BlockingStub`, `synchronized`, `withDeadlineAfter`, `StatusRuntimeException` e Swing; não editar o `initComponents()`/`.form`; nunca mudar número ou tipo de campo existente; estado só em memória com `synchronized`; não refatorar; comentários em português;
- as 6 partes;
- o formato de cada resposta (arquivo, código, explicação, como conferir, resumo).

<details>
<summary>Prompt completo</summary>

~~~~text
Sou estudante de Sistemas Distribuídos. Tenho este projeto gRPC em Java (código abaixo). Quero acrescentar o trabalho 6 — "Evolução do contrato: a Central muda, o totem não" — da forma MAIS SIMPLES possível, no mesmo estilo, sem bibliotecas extras. Faça uma parte por vez e explique cada linha.

## Ambiente
- Java + Maven + Apache NetBeans. gRPC e protobuf (já estão no pom.xml).
- Nesta pasta há dois projetos: ServidorAtendimentoRPC (a Central, pacote com.mycompany.servidoratendimentorpc) e ClienteAtendimentoRPC (o totem, pacote com.mycompany.clienteatendimentorpc). As classes geradas ficam em com.mycompany.atendimentorpc.
- Leia os arquivos dos dois projetos antes de propor qualquer mudança.
- TelaTotem é um JFrame Form do NetBeans: NÃO edite o método initComponents() nem o arquivo TelaTotem.form. Quando precisar de componente novo, me diga o que arrastar da Palette, o Variable Name e o text; eu faço no Design. Você escreve só o código fora do bloco gerado.
- Componentes que já existem na tela: cbxLocal, tfNome, rbNormal, rbPrioritario (ButtonGroup grupoTipo), btnPegarSenha, lbSenha, lblMensagem, lblStatus.

## Código atual (modelo da aula — mantenha este estilo)
(atendimento.proto, AtendimentoServiceImpl.java, ServidorAtendimentoRPC.java e TelaTotem.java — construtor e botão)

## O que o trabalho 6 pede
O que acrescentar:
- Campo tempoEstimado na Senha
- Métodos CancelarSenha e ChamarProxima, cada um com seu deadline
- Guardar a Central antiga para comparar
Como testar:
- Central nova + totem antigo → funciona
- Totem novo + Central antiga → UNIMPLEMENTED
- Prazo curto em ChamarProxima → só ele falha
- Renumerar um campo → dados errados; reserved evita
O que explicar:
- Contrato antes do código
- O papel dos números dos campos
- O que nunca se faz com o serviço em uso
- Atualizar a Central sem obrigar todos os totens

## Regras
- Use só o que já está no material: chamadas unárias, BlockingStub, synchronized, withDeadlineAfter, StatusRuntimeException, Swing. Nada de Spring, banco de dados, Lombok, SwingWorker ou bibliotecas novas. Não altere o pom.xml (só o artifactId das cópias da Parte 1).
- No .proto: nunca mude o número nem o tipo de um campo que já existe; campos novos ganham números novos; rpc novos entram depois do PegarSenha. Prefira mensagens próprias e mínimas (ex.: message Vazio {}).
- Estado da Central só em memória, protegido com synchronized, igual ao gerarNumero().
- Não refatore o que já funciona; só acrescente.
- Comentários no código e explicações em português.
- Se o mvn estiver instalado, pode rodar "mvn -q clean package" no projeto para conferir que compila. Não tente abrir a interface gráfica.

## Partes (faça UMA, pare e espere eu dizer "ok" antes da próxima)
Parte 1 — Guardar as versões antigas (cópias -v1, mudando só o artifactId).
Parte 2 — Contrato v2 (tempo_estimado, CancelarSenha, ChamarProxima).
Parte 3 — Central nova (fila synchronized, tempo estimado, cancelar, chamar com atraso).
Parte 4 — Totem novo (tempo em lblMensagem, último código, dois botões, deadlines 5/3/1 s).
Parte 5 — Roteiro dos 4 testes do slide.
Parte 6 — Material da apresentação com as páginas oficiais para conferir.

## Formato de cada resposta
1. Qual arquivo, de qual projeto, e onde colar.
2. O código da parte.
3. Explicação linha por linha em português.
4. Como conferir no NetBeans (Clean and Build, Run, o que deve aparecer no Output).
5. Um resumo de 2–3 linhas para eu colar no PROMPTS.md.

Comece pela Parte 1. Texto aperfeiçoado com IA.
~~~~

</details>

**Resposta:**
- O assistente leu a pasta antes de mudar qualquer coisa. Analisou o `TelaTotem.java` e o `TelaTotem.form` e procurou os dois projetos Maven (`ServidorAtendimentoRPC` e `ClienteAtendimentoRPC`), que ainda não estavam na pasta. O totem também estava sem o código gRPC (o botão estava com `// TODO`), e o `mvn` não estava instalado.
- Não criou cópias de projetos inexistentes.
- Depois que os dois projetos Maven foram montados, o assistente analisou os dois junto com o `TelaTotem` antes de cada parte: `pom.xml`, `atendimento.proto`, `ServidorAtendimentoRPC.java`, `AtendimentoServiceImpl.java`, `TelaTotem.java` e `TelaTotem.form`.
- Explicou a Parte 1 completa para ser feita depois: comandos de cópia, troca do `artifactId`, por que as cópias v1 são necessárias e como conferir no NetBeans.
- Pediu que os projetos fossem colocados na pasta.

---

## Prompt 2 — "Os códigos foram feitos corretamente de forma simples e com o que foi descrito nos trabalhos?"

**Resposta:** o assistente conferiu em vez de responder de memória.
- **Build:** os dois projetos compilam com `mvn clean package` e a Central sobe.
- **JDK:** o NetBeans e o Maven embutido usam o JDK 25, compatível com o `maven.compiler.release 25` do pom.
- **Formulário:** `TelaTotem.form`, `initComponents()` e o bloco de variáveis estão idênticos ao original.
- **Comparação com o material da aula:** o pom é o da seção 10 do PDF; `.proto` idêntico nos dois projetos; `ServerBuilder`; `ImplBase` com `onNext`/`onCompleted`; `usePlaintext`, `newBlockingStub`, deadline de 5 s e `StatusRuntimeException`; `synchronized` no estado compartilhado.
- **Diferenças em relação ao PDF, todas vindas do modelo do aluno:**
  - sem classe `ClienteProduto` separada;
  - canal aberto no construtor, sem botões Conectar/Desconectar;
  - `addShutdownHook(canal::shutdown)`, que não aparece no material e pode ser removido.
- **Limite conhecido:** a chamada bloqueante roda na thread do Swing (o PDF cita SwingWorker, que ficou fora por regra).

---

## Prompt 3 — "ok" → Parte 1: guardar as versões antigas

**Resposta:**
- Copiou `ServidorAtendimentoRPC` → `ServidorAtendimentoRPC-v1` e `ClienteAtendimentoRPC` → `ClienteAtendimentoRPC-v1` e apagou o `target/` das cópias.
- Trocou **só** o `<artifactId>` do pom de cada cópia (linha 9). O `diff` confirmou que nada mais mudou.
- As duas cópias compilaram e geraram `...-v1-1.0-SNAPSHOT.jar`.
- **Por que guardar:** as classes são geradas do `.proto` no build. Depois da Parte 2, só as v1 guardam o contrato antigo compilado. Elas permitem os testes "Central nova + totem antigo" e "totem novo + Central antiga → UNIMPLEMENTED".
- **Aviso:** o NetBeans mostra o `<name>` (que não mudou), então original e cópia aparecem com o mesmo nome no painel. A troca do `<name>` foi oferecida, mas não aplicada.

> **Resumo:** copiei ServidorAtendimentoRPC e ClienteAtendimentoRPC para `-v1`, mudando só o `artifactId` no pom. As cópias ficam congeladas com o contrato antigo (v1), para testar "Central nova + totem antigo" e "totem novo + Central antiga → UNIMPLEMENTED".

---

## Prompt 4— "ok" → Parte 2: contrato v2 (`.proto` dos projetos novos)

**Resposta:** o `src/main/proto/atendimento.proto` dos dois projetos novos recebeu, de forma idêntica nos dois:

```proto
service AtendimentoService {
    rpc PegarSenha (PedidoSenha) returns (Senha);
    // v2: rpc novos entram depois dos que já existem
    rpc CancelarSenha (PedidoCancelamento) returns (ResultadoCancelamento);
    rpc ChamarProxima (Vazio) returns (Senha);
}
message Senha {
    int32 numero = 1;
    string codigo = 2;
    string mensagem = 3;
    int32 tempo_estimado = 4; // v2: minutos de espera; número novo
}
// v2: mensagens novas
message Vazio {}
message PedidoCancelamento {
    string codigo = 1;
}
message ResultadoCancelamento {
    bool cancelada = 1;
    string mensagem = 2;
}
```

- **Explicação linha por linha:**
  - `ChamarProxima` reaproveita `Senha` como resposta, como o slide 22 reaproveita mensagens.
  - `Vazio` existe porque todo rpc precisa de uma mensagem de entrada.
  - `tempo_estimado` vira `getTempoEstimado()` em Java.
- **Números dos campos:** são a identidade do campo nos bytes; o código gerado confirma `TEMPO_ESTIMADO_FIELD_NUMBER = 4`.
- **Por que não quebra o totem antigo:** ele ignora o campo 4 e não conhece os rpc novos.
- **Gerado depois do Clean and Build:**
  - `Vazio`, `PedidoCancelamento` e `ResultadoCancelamento` (com os `...OrBuilder`);
  - `get/set/clearTempoEstimado` na `Senha`;
  - `cancelarSenha`/`chamarProxima` na `ImplBase` (respondendo `UNIMPLEMENTED` por padrão) e no `BlockingStub`.
- Os dois projetos compilaram sem mudar nenhuma linha de Java.

> **Resumo:** contrato v2 no `.proto` dos projetos novos: `tempo_estimado = 4` na `Senha`, rpc `CancelarSenha` e `ChamarProxima` depois de `PegarSenha`, e as mensagens mínimas `Vazio`, `PedidoCancelamento` e `ResultadoCancelamento` (`ChamarProxima` reaproveita `Senha`). Nenhum campo existente mudou de número ou tipo, então o totem v1 ignora o campo 4 e continua funcionando.

---

## Prompt 5 — "ok" → Parte 3: Central nova (`AtendimentoServiceImpl`)

**Resposta:** só acréscimos na `AtendimentoServiceImpl` de `ServidorAtendimentoRPC`.
- **Constantes:**
  - `MINUTOS_POR_PESSOA = 3`;
  - `ATRASO_CHAMAR_MS = 2000`.
- **Estado:** `LinkedList<Senha> fila`, mexida só em métodos `synchronized`:
  - `entrarNaFila(senha)`: tempo = `fila.size() × 3`; usa `toBuilder()` e adiciona no fim;
  - `sairDaFila(codigo)`: `removeIf` pelo código; devolve `true` ou `false`;
  - `tirarPrimeira()`: `poll()`; devolve `null` se a fila estiver vazia.
- **`pegarSenha`:** ganhou 2 linhas, `entrarNaFila` e um `println` do tempo estimado.
- **`cancelarSenha`:** responde `ResultadoCancelamento` com `cancelada` e a mensagem.
- **`chamarProxima`:** `Thread.sleep(2000)` e tira a primeira da fila; com a fila vazia, devolve uma `Senha` com a mensagem "Fila vazia".
- **Simplificações:** fila por ordem de chegada (prioritário não fura a fila); a primeira pessoa recebe 0 min.
- **Teste com cliente de linha de comando** (fora do projeto):
  - tempos 0, 3 e 6;
  - cancelar uma vez e de novo → "cancelada" / "não está na fila";
  - ChamarProxima com prazo de 1 s → `DEADLINE_EXCEEDED`;
  - fila vazia → mensagem de aviso.
- **Achado:** depois do `DEADLINE_EXCEEDED`, a Central continuou e tirou a senha da fila (slide 18).

> **Resumo:** Central nova guarda as senhas numa `LinkedList` em memória, mexida só em métodos `synchronized`. `pegarSenha` preenche `tempo_estimado` = pessoas à frente × 3 min. `cancelarSenha` remove pelo código e diz se conseguiu. `chamarProxima` espera 2 s (atraso artificial) e tira a primeira da fila, ou avisa "fila vazia".

---

## Prompt 6 — "ok" → Parte 4: totem novo (`TelaTotem`)

**Resposta:**
- **Escrito pelo assistente, fora do bloco gerado:**
  - imports de `PedidoCancelamento`, `ResultadoCancelamento` e `Vazio`;
  - constantes `PRAZO_PEGAR_SEG = 5`, `PRAZO_CANCELAR_SEG = 3` e `PRAZO_CHAMAR_SEG = 1` (menor que os 2 s da Central, de propósito);
  - atributo `ultimoCodigo`;
  - no PEGAR SENHA: deadline pela constante, guarda o código e mostra "Tempo estimado: X min" em `lblMensagem`.
- **Feito pelo aluno no Design:** dois `JButton` no `painelFundo`:
  - `btnCancelarSenha`, com o texto "Cancelar senha";
  - `btnChamarProxima`, com o texto "Chamar próxima";
  - dois cliques em cada um para criar o evento.
- **Corpo dos eventos (colado pelo aluno):**
  - **Cancelar:** valida o `ultimoCodigo`, chama `cancelarSenha` com prazo de 3 s, mostra a mensagem e limpa a senha se cancelou.
  - **Chamar próxima:** chama `chamarProxima` com prazo de 1 s e mostra "Chamando senha X" ou a mensagem de fila vazia.
  - **Os dois:** mesmo `catch (StatusRuntimeException)` mostrando o código do status.
- O código dos eventos foi compilado antes numa cópia descartável. Depois o assistente conferiu o arquivo real (botões, eventos e `.form`): `BUILD SUCCESS`.

> **Resumo:** totem novo mostra o `tempo_estimado` em `lblMensagem`, guarda `ultimoCodigo` e ganha os botões Cancelar senha e Chamar próxima (criados no Design). Cada chamada tem seu deadline em constante: Pegar 5 s, Cancelar 3 s e Chamar 1 s (menor que os 2 s da Central, de propósito). O tratamento de `StatusRuntimeException` mostra o código do status.

---

## Prompt 7 — "ok" → Parte 5: roteiro dos 4 testes do slide

**Resposta:** os 4 testes foram rodados de verdade, com clientes de linha de comando e cópias descartáveis, e o roteiro traz os resultados observados.

1. **Central nova + totem v1 → funciona.**
   - O totem v1 mostra N-001 e N-002 normalmente.
   - O Output da Central mostra "tempo estimado: 3 min".
   - O totem v1 guardou o campo 4 como campo desconhecido (`getUnknownFields()` = `{4=…}`).
   - Com tempo 0 o campo nem é enviado, porque o proto3 não envia valores padrão.
2. **Totem novo + Central v1.**
   - Pegar funciona, mostrando "Tempo estimado: 0 min" (valor padrão).
   - Cancelar e Chamar dão `UNIMPLEMENTED` na hora (2 ms).
3. **Prazo de 1 s em ChamarProxima.**
   - Só ele dá `DEADLINE_EXCEEDED` (1004 ms); Pegar e Cancelar continuam funcionando.
   - A Central termina o trabalho e tira a senha da fila mesmo assim.
4. **Renumerar e `reserved`.**
   - Trocar `codigo = 2` e `mensagem = 3` só na Central: o totem mostra "Ana, sua senha é N-001 (totem: Biblioteca)" no lugar da senha, sem nenhum erro.
   - Com `reserved 4; reserved "tempo_estimado";` o `protoc` recusa as duas reutilizações:
     - `Field "sala" uses reserved number 4.`
     - `Field name "tempo_estimado" is reserved.`
   - **Desfazer:** voltar o `.proto` da Central (ou usar History → Revert no NetBeans), voltar `PRAZO_CHAMAR_SEG = 1` e fazer Clean and Build nos dois projetos novos.

> **Resumo:** roteiro dos 4 testes, todos conferidos:
> 1. Central nova + totem v1 funciona (o v1 guarda o campo 4 como desconhecido).
> 2. Totem novo + Central v1: Pegar funciona com tempo 0 (valor padrão); Cancelar e Chamar dão UNIMPLEMENTED.
> 3. Prazo de 1 s contra atraso de 2 s: só ChamarProxima dá DEADLINE_EXCEEDED, e a Central continua e tira a senha da fila.
> 4. Trocar os números de codigo/mensagem só na Central faz o totem mostrar dados trocados sem erro; `reserved 4; reserved "tempo_estimado";` faz o protoc recusar reutilizar o número e o nome.

---

## Prompt 8 — "ok" → Parte 6: material da apresentação

**Resposta:**
- **Conferência antes de escrever:** o assistente viu que o `.proto` da Central e o do totem diferiam só em espaços (mesmo contrato) e que os prazos estavam de volta (5/3/1 s).
- **Seções oficiais conferidas nas páginas:**
  - no guia do proto3 não existe a seção "Reserved Fields": o assunto está em "Deleting Fields" › "Reserved Field Numbers" / "Reserved Field Names";
  - a tabela "Method not found at server → UNIMPLEMENTED" está em `grpc/doc/statuscodes.md`, não em grpc.io/status-codes.
- **Um parágrafo (paráfrase, sem citações) para cada item do "O que explicar", com a evidência do trabalho e a seção onde confirmar:**
  - **A) Contrato antes do código:** proto3 "Defining Services" e "Generating Your Classes".
  - **B) Números dos campos:** "Assigning Field Numbers" › "Consequences of Reusing Field Numbers" e "Default Field Values".
  - **C) O que nunca se faz:**
    - "Deleting Fields" › "Reserved Field Numbers" / "Reserved Field Names";
    - "Updating A Message Type" › "Binary Wire-unsafe Changes".
  - **D) Atualizar a Central sem obrigar os totens:**
    - "Updating A Message Type" › "Binary Wire-safe Changes";
    - "Unknown Fields" › "Retaining Unknown Fields";
    - grpc.io/docs/guides/deadlines ("Deadlines on the Client" e "Deadlines on the Server");
    - grpc.io/docs/guides/status-codes ("The full list of status codes");
    - github.com/grpc/grpc/blob/master/doc/statuscodes.md.

> **Resumo:** material da apresentação com um parágrafo para cada item: contrato antes do código, números dos campos, o que nunca se faz com o serviço em uso, atualizar a Central sem obrigar os totens. Cada um aponta a seção oficial para conferir: proto3 "Assigning Field Numbers", "Deleting Fields"/"Reserved Field Numbers"/"Reserved Field Names", "Updating A Message Type", "Unknown Fields", "Default Field Values"; grpc.io deadlines; grpc.io status-codes; e a tabela em grpc/doc/statuscodes.md.

---
## Prompt 9 — "ok, monte o documento"

**Resposta:** o material foi montado num documento compartilhável (Claude Docs), "Trabalho 6 — Evolução do contrato gRPC":
- contexto (o que a v2 acrescentou e os quatro projetos);
- os itens A–D, cada um com evidência e "onde confirmar";
- a tabela dos 4 testes com os resultados observados;
- a tabela de fontes com links clicáveis e as seções exatas.

---
## Prompt 10 — "Atualize o documento e referencie com links e trechos de cada parte utilizada, como se fosse uma citação de TCC"

**Resposta:**
- O assistente baixou as páginas oficiais e extraiu os trechos **literais** de cada seção usada:
  - Protocol Buffers: Language Guide (proto 3);
  - gRPC: Deadlines, Status Codes e Basics tutorial (Java);
  - grpc/grpc: `doc/statuscodes.md`.
- No documento, cada item (A–D) ganhou uma "Fundamentação" com citações diretas no padrão ABNT NBR 10520:2023: autor e ano, por exemplo (GOOGLE, [2026]) e (GRPC AUTHORS, 2025), mais o link direto para a seção.
- A lista de fontes virou "Referências" no padrão ABNT NBR 6023:2018. As datas vieram do "Last modified" de cada página; o guia proto3 ficou como [2026], data provável. As duas páginas de 2024 viraram 2024a e 2024b.

---

## Prompt 11 — "Remonte o documento e coloque somente a tradução direta, sem a citação original"

**Resposta:**
- O assistente reescreveu a fundamentação das seções A–D só com as traduções, cada uma indicada como citação traduzida: (AUTOR, ano, tradução nossa), mais o link da seção.
- Citações com mais de três linhas ficaram em bloco recuado.
- Conferiu que não sobrou trecho em inglês nem parágrafo duplicado.

**Decisão do grupo:** usar só a tradução no documento. Quem quiser o original abre o link de cada seção.

---

## Prompt 12 — "O meu não é o único trabalho; modifique os nomes das variáveis para ficar com o padrão dos slides e compatível com os demais trabalhos, tanto na v1 quanto na nova"

**Resposta:**
- O assistente comparou os nomes dos 4 projetos com os slides da Aula 10. Os nomes da Central e do totem dos slides 13–21 já eram iguais.
**Decisões do grupo e o que foi feito:**
- **`grupoTipo` → `bgTipo`** (nos dois totens): feito pelo aluno no Design (Navigator › Change Variable Name), para não editar o código gerado. O assistente conferiu depois que os dois totens compilam.
- **`tempo_estimado` → `tempoEstimado`** no `.proto` da Central e do totem novos. O número 4 não mudou, então a troca não quebra a compatibilidade, e o Java continua com `getTempoEstimado()`. Só a constante gerada passou a se chamar `TEMPOESTIMADO_FIELD_NUMBER`. O v1 não tem esse campo.
- **Classe principal `ClienteAtendimentoRPC`** criada nos dois totens (um `main` que chama `TelaTotem.main(args)`), com o `exec.mainClass` do pom apontando para ela.
- Os 4 projetos foram recompilados e os testes repetidos, com o mesmo comportamento: totem v1 ignora o campo 4; `UNIMPLEMENTED` contra a Central v1; `DEADLINE_EXCEEDED` só no Chamar próxima. O teste do `reserved` foi refeito com o nome novo (`Field name "tempoEstimado" is reserved.`), e o documento foi atualizado.

**Por quê:** seguir o padrão definido nos slides, para ficar compatível com os trabalhos dos outros grupos.

