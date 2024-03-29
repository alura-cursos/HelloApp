
![Jetpack Compose armazenamento de dados internos](https://user-images.githubusercontent.com/35709152/224780761-6773fbfc-8c1a-4354-a937-e44710f81452.png)

# HelloApp


HelloApp é um aplicativo que permite salvar contatos com número de telefone, data de aniversário, foto de perfil e outras informações pessoais.

## :hammer: Funcionalidades do projeto
Feito em Jetpack Compose, o HelloApp utiliza muitas das técnicas mais atuais do desenvolvimento Android. 
Ele é dividido em dois fluxos principais, um de login que permite salvar informações de um usuário e depois as utiliza para autenticação:

<img src="https://user-images.githubusercontent.com/35709152/207883587-c2572b28-c198-484a-bd7f-7a6afab5932b.gif" alt = "helloApp gif fluxo login" width="300">

ㅤ
ㅤ

O segundo fluxo permite: Inserir contatos através de um formulário; Visualizar a lista de contatos inseridos; Editar e excluir contatos:

<img src="https://user-images.githubusercontent.com/35709152/207889521-69038e3b-0e26-4604-807d-3db81433e35c.gif" alt = "helloApp gif fluxo contatos" width="300">


## 🏠 Arquitetura
* Navigation
* Room Database / DataStore
* Kotlin Coroutines e Flow
* ViewModel com StateFlow
* Hilt (injeção de dependência)

## ✔️ Outras técnicas e tecnologias utilizadas
* Kotlin
* Jetpack Compose
* Compose BOM
* [Coil][coil]
* [LocalDate][localdate] do Java 8+, compatível com versões abaixo do Android 8 graças ao [desugaring support][jdk8desugar]


## 📂 Acesso ao projeto
- Versão inicial: Veja o [código fonte][codigo-inicial] ou [baixe o projeto][download-inicial]
- Versão final: Veja o [código fonte][codigo-final] ou [baixe o projeto][download-final]

## 🛠️ Abrir e rodar o projeto
Após baixar o projeto, você pode abri-lo com o Android Studio. Para isso, na tela de launcher clique em:

“Open” (ou alguma opção similar), procure o local onde o projeto está e o selecione (caso o projeto seja baixado via zip, é necessário extraí-lo antes de procurá-lo). Por fim, clique em “OK” o Android Studio deve executar algumas tasks do Gradle para configurar o projeto, aguarde até finalizar. Ao finalizar as tasks, você pode executar o App 🏆

## 🎯 Desafios
- Campo de busca na tela inicial - [Veja a solução][desafio-1]
<img src="https://user-images.githubusercontent.com/35709152/207896507-07cbd79a-8135-4264-8848-4a58ec6ab6bb.gif" alt = "helloApp gif desafio 1" width="200">
ㅤ
ㅤ

- Confirmar antes de deslogar - [Veja a solução][desafio-2]
<img src="https://user-images.githubusercontent.com/35709152/208907927-2c8014b4-e0f1-44c0-997e-f764529ce5d5.gif" alt = "helloApp gif desafio confirmar deslogar" width="200">



[localdate]: https://developer.android.com/reference/java/time/LocalDate
[jdk8desugar]: https://developer.android.com/studio/write/java8-support#library-desugaring
[coil]: https://coil-kt.github.io/coil/
[codigo-inicial]: https://github.com/alura-cursos/HelloApp/commits/projeto-inicial
[download-inicial]: https://github.com/alura-cursos/HelloApp/archive/refs/heads/projeto-inicial.zip
[codigo-final]: https://github.com/alura-cursos/HelloApp/commits/aula-6
[download-final]: https://github.com/alura-cursos/HelloApp/archive/refs/heads/aula-6.zip
[desafio-1]: https://github.com/alura-cursos/HelloApp/commit/5a76d9fcb18513b6c1696a58150f73cda1c13a0d
[desafio-2]: https://github.com/alura-cursos/HelloApp/commit/73d7bca228e2c508750cf3f82a11f46a03d0e4d9
