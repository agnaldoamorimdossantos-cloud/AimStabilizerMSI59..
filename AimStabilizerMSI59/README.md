# Aim Stabilizer — MSI App Player 5.9

Protótipo de painel Android para organizar configurações e perfis de teste para Free Fire.

## O que faz
- Sliders de suavização desejada, sensibilidade de referência e DPI de referência.
- Perfis rápidos: Preciso, Equilibrado e Suave.
- Salva as preferências localmente no aplicativo.

## Limitação importante
Este app **não modifica nem estabiliza diretamente a mira do Free Fire**. Um app Android comum não consegue controlar o mouse usado por outro processo do emulador. Os valores são referências para testar e ajustar manualmente as configurações do emulador/jogo.

## Como abrir e compilar
1. Instale o Android Studio em um computador.
2. Abra a pasta `AimStabilizerMSI59` como projeto.
3. Aguarde a sincronização do Gradle.
4. Se necessário, instale o Android SDK 35 pelo SDK Manager.
5. Use **Build > Build APK(s)**.
6. O APK de depuração normalmente fica em `app/build/outputs/apk/debug/app-debug.apk`.

Este projeto não inclui scripts para alterar a memória do jogo, injetar código ou contornar proteções.

## Compilar na nuvem pelo GitHub Actions (sem Android Studio)
1. Crie uma conta no GitHub e um repositório novo.
2. Envie todos os arquivos e pastas deste projeto para o repositório, incluindo a pasta oculta `.github/workflows`.
3. Abra a aba **Actions** do repositório.
4. Se a compilação não iniciar automaticamente, escolha **Build Aim Stabilizer APK** e clique em **Run workflow**.
5. Quando terminar, abra a execução e baixe o artefato **AimStabilizer-debug-apk** na seção **Artifacts**.
6. Extraia o ZIP do artefato; dentro estará `app-debug.apk`.

O GitHub compila o projeto; você ainda precisa criar o repositório e enviar os arquivos. Não publique senhas, chaves ou dados pessoais no repositório.
