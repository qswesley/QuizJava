
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Path arquivoPerguntas = Path.of("perguntas.txt");
        Path arquivoResultado = Path.of("resultado.txt");

        int acertos = 0;
        int erros = 0;

        try {

            // Leitura do arquivo utilizando Java NIO
            List<String> linhas = Files.readAllLines(arquivoPerguntas);

            // Adiciona uma linha vazia para processar a última pergunta
            linhas.add("");

            List<String> alternativas = new ArrayList<>();

            String pergunta = "";
            int respostaCorreta = -1;

            System.out.println("========================");
            System.out.println("     QUIZ DE PERGUNTAS");
            System.out.println("========================");

            for (String linha : linhas) {

                linha = linha.trim();

                if (linha.isEmpty()) {

                    if (!pergunta.isEmpty()) {

                        // Exibe a pergunta
                        System.out.println("\n" + pergunta);

                        // Exibe as alternativas
                        for (int i = 0; i < alternativas.size(); i++) {

                            System.out.println(
                                    (i + 1) + " - " + alternativas.get(i)
                            );
                        }

                        int resposta;

                        // Validação da entrada do jogador
                        while (true) {

                            System.out.print("Digite sua resposta: ");

                            if (scanner.hasNextInt()) {

                                resposta = scanner.nextInt();

                                if (resposta >= 1 &&
                                        resposta <= alternativas.size()) {
                                    break;
                                }

                            } else {
                                scanner.next();
                            }

                            System.out.println("Alternativa inválida!");
                        }

                        // Verifica se a resposta está correta
                        if (resposta == respostaCorreta) {

                            System.out.println("Resposta correta!");
                            acertos++;

                        } else {

                            System.out.println("Resposta errada!");

                            System.out.println("Resposta correta: " +
                                    alternativas.get(respostaCorreta - 1));

                            erros++;
                        }

                        // Limpa os dados para a próxima pergunta
                        pergunta = "";
                        alternativas.clear();
                        respostaCorreta = -1;
                    }

                } else if (pergunta.isEmpty()) {

                    // Identifica o enunciado da pergunta
                    pergunta = linha;

                } else {

                    // Identifica a alternativa correta
                    if (linha.startsWith("*")) {

                        respostaCorreta = alternativas.size() + 1;

                        // Remove o asterisco
                        linha = linha.substring(1).trim();
                    }

                    alternativas.add(linha);
                }
            }

            // Resultado final
            String resultado =
                    "===== RESULTADO FINAL =====\n" +
                            "Total de perguntas: " + (acertos + erros) + "\n" +
                            "Acertos: " + acertos + "\n" +
                            "Erros: " + erros + "\n";

            System.out.println("\n" + resultado);

            // Salva o resultado utilizando Java NIO
            Files.writeString(arquivoResultado, resultado);

            System.out.println("Resultado salvo em resultado.txt!");

        } catch (IOException e) {

            System.out.println("Erro ao manipular o arquivo.");
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
