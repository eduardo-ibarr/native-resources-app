# Phone Native Resources App

## Descrição

Este projeto é um aplicativo nativo para Android desenvolvido em Kotlin, utilizando **Jetpack Compose**. Ele permite que o usuário preencha um formulário, capture uma foto usando a câmera do celular e salve os dados no banco de dados SQLite. Além disso, o aplicativo exibe a localização atual do usuário utilizando o GPS do dispositivo.

### Funcionalidades

- **Formulário**: O usuário pode inserir seu nome, e-mail e um comentário.
- **Captura de Foto**: O usuário pode tirar uma foto e visualizá-la diretamente na interface do aplicativo.
- **Localização**: O aplicativo captura e exibe a localização atual do usuário (latitude e longitude).
- **Banco de Dados**: Os dados do formulário (nome, e-mail, comentário e caminho da imagem) são armazenados em um banco de dados SQLite.

## Requisitos

- **Kotlin** 1.9+
- **Jetpack Compose** 1.5.1+
- **Android Studio** Flamingo ou mais recente
- Dispositivo ou emulador com **API 24+** (Android 7.0 Nougat ou superior)

## Permissões Necessárias

Certifique-se de que as seguintes permissões estão configuradas no arquivo \`AndroidManifest.xml\` para o funcionamento adequado do aplicativo:

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## Configuração do Projeto

1. **Clonar o Repositório**

   Clone este repositório para sua máquina local usando o comando:

   ```bash
   git clone https://github.com/usuario/phonenativeresources.git
   ```

2. **Abrir no Android Studio**

   Abra o Android Studio, selecione "Open" e escolha o diretório onde o projeto foi clonado.

3. **Configurar Dependências**

   Verifique se as dependências do \`build.gradle.kts\` estão corretamente sincronizadas:

   ```kotlin
   implementation("androidx.compose.ui:ui:1.5.1")
   implementation("androidx.activity:activity-compose:1.6.0")
   implementation("com.google.android.gms:play-services-location:18.0.0")
   implementation("io.coil-kt:coil-compose:1.3.2")
   ```

4. **Executar o Projeto**

   Após a sincronização, conecte um dispositivo ou emulador Android e clique em "Run" para executar o aplicativo.

## Estrutura do Projeto

- **MainActivity.kt**: A \`MainActivity\` é responsável por configurar o conteúdo principal do aplicativo.
- **FormScreen.kt**: Contém a tela principal do aplicativo, onde o formulário é exibido, a foto pode ser capturada, e os dados são salvos no banco de dados.
- **FormDatabaseHelper.kt**: Responsável por gerenciar o banco de dados SQLite, permitindo salvar e recuperar os dados do formulário.

## Banco de Dados

O aplicativo utiliza o **SQLite** para armazenar os dados do formulário. O banco de dados contém as seguintes colunas:

- \`name\`: Nome do usuário
- \`email\`: E-mail do usuário
- \`comment\`: Comentário fornecido pelo usuário
- \`imagePath\`: Caminho da imagem capturada e salva no dispositivo

## Como Verificar os Dados Salvos

1. **Salvar os Dados**: Após preencher o formulário e tirar uma foto, clique no botão "Salvar Dados".
2. **Ver os Dados Salvos**: Clique no botão "Ver Dados Salvos" para exibir todos os registros armazenados no banco de dados SQLite.

## Arquivo \`file_paths.xml\`

Este arquivo é necessário para que o \`FileProvider\` funcione corretamente ao capturar a imagem. Está localizado em \`res/xml/file_paths.xml\`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="my_images" path="Android/data/com.uri.phone_native_resources/files/Pictures/" />
</paths>
```

## Contribuições

Se você deseja contribuir com o projeto, siga as etapas abaixo:

1. **Faça um Fork** do repositório.
2. Crie uma **branch** com sua feature ou correção: \`git checkout -b minha-feature\`.
3. **Commit** suas mudanças: \`git commit -m 'Minha nova feature'\`.
4. Envie para a branch original: \`git push origin minha-feature\`.
5. Abra um **Pull Request** para o repositório principal.

## Licença

Este projeto é licenciado sob os termos da [MIT License](LICENSE).

---

**Desenvolvido por Eduardo Ibarr de Paula**