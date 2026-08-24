# DIO Spring Boot - Final Project 05: Spring AI (budgeting)

## Introduction

This final module applies Spring AI in a budgeting API while preserving the same layered architecture used across the track.

The goal is to integrate AI capabilities without bypassing domain and use case boundaries.

## Code Context

The project processes voice commands to create and query financial transactions.

Primary flow:

1. Client uploads an audio file.
2. Audio is transcribed into text.
3. The model selects an application tool/use case.
4. The use case persists or queries transaction data.
5. The final response is converted to audio.

## Project Structure

- `src/main/java/dio/budgeting/domain`
  - Domain model and repository contract.
- `src/main/java/dio/budgeting/application`
  - Use cases used by both REST and AI tool calling.
- `src/main/java/dio/budgeting/infrastructure`
  - HTTP adapters, JPA adapters, and integration glue.

## Module-Specific Topics

### Speech-to-text

- Uses `TranscriptionModel` for audio transcription.
- Model settings are configured in `application.properties`.

### Tool calling

- `ChatClient` registers use-case tools.
- `@Tool` methods expose business capabilities to the model.

### Text-to-speech

- `TextToSpeechModel` produces MP3 output from final text.
- AI endpoint returns generated audio.

## Spring AI Documentation

- Spring AI Reference: https://docs.spring.io/spring-ai/reference/index.html
- ChatModel API: https://docs.spring.io/spring-ai/reference/api/chatmodel.html
- ChatClient API: https://docs.spring.io/spring-ai/reference/api/chatclient.html
- Tools API: https://docs.spring.io/spring-ai/reference/api/tools.html
- Audio Transcriptions API: https://docs.spring.io/spring-ai/reference/api/audio/transcriptions.html
- Audio Speech API: https://docs.spring.io/spring-ai/reference/api/audio/speech.html

## Shared Architecture References

Common architecture concepts are documented in the root README:

- [DDD layers](../README.md#ddd-layered-architecture)
- [Class vs record](../README.md#java-class-vs-java-record-in-domain-modeling)
- [Strong typed identifiers](../README.md#strong-typed-identifiers)
- [Repository pattern](../README.md#repository-pattern)
- [Use cases and Clean Architecture](../README.md#use-cases-and-clean-architecture)
- [Docker Compose support](../README.md#docker-compose-support-in-development)

## How to Run

Set your OpenAI API key:

```bash
export OPENAI_API_KEY="your_api_key_here"
```

Run the application and tests:

```bash
./gradlew bootRun
./gradlew test
```

## Entrega do Desafio

### O que o projeto faz

Esta é uma API de orçamento que recebe comandos de voz sobre transações financeiras. O áudio é transcrito, a IA identifica a intenção, executa uma ferramenta da aplicação para criar ou consultar transações e gera uma resposta em áudio.

### Como executar a aplicação

1. Configure a variável de ambiente `OPENAI_API_KEY` com uma chave da OpenAI.
2. Inicie o banco de dados com Docker Compose, se ele não for iniciado automaticamente pelo Spring Boot:

   ```bash
   docker compose up -d
   ```

3. Execute a aplicação:

   ```bash
   ./gradlew bootRun
   ```

No PowerShell, configure a chave com:

```powershell
$env:OPENAI_API_KEY="sua_chave_aqui"
```

### Melhoria implementada

Foi adicionada validação no fluxo de persistência de transações. Como as informações podem ser extraídas de uma fala por IA, a aplicação rejeita descrição nula ou vazia, valor zero ou negativo e categoria nula antes de salvar os dados.

Os valores são armazenados em centavos e convertidos para reais na resposta da API usando `BigDecimal`, evitando imprecisões de `double` em valores monetários.

### Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring AI e OpenAI
- Spring Data JPA
- MySQL
- Gradle
- JUnit 5

### Como testar o fluxo principal

Os testes unitários da validação não exigem chave da OpenAI nem banco de dados:

```bash
./gradlew test --tests dio.budgeting.application.PersistTransactionUseCaseTest
```

Eles verificam que uma transação inválida não é persistida quando possui descrição nula ou vazia, valor zero ou negativo, ou categoria nula.

Com uma chave da OpenAI configurada, envie um arquivo de áudio para `POST /transactions/ai`. Por exemplo, um áudio com "gastei 80 reais no mercado" deve gerar uma transação com o valor `8000` em centavos, retornado como `80.00` reais.

### Aprendizados

Neste desafio, aprendi a integrar recursos de IA a uma aplicação Spring Boot, incluindo transcrição de áudio, Tool Calling e síntese de voz. Também pratiquei a separação entre domínio, casos de uso e infraestrutura, além da importância de validar dados produzidos por IA antes de persistir uma operação financeira.

## Notes

- Educational final project focused on AI plus architectural discipline.
- External provider integration tests may require active credentials.
