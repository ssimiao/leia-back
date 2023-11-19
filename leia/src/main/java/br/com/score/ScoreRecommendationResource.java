package br.com.score;

import br.com.shared.google.GoogleBookData;
import br.com.shared.google.GoogleBooksClient;
import br.com.shared.exception.ResourceNotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Objects;

@Path("/v1/score")
public class ScoreRecommendationResource {

    @RestClient
    private GoogleBooksClient client;

    @POST
    public Response getScoreFromText(JsonData<String> text, @QueryParam("isbn") String isbn) {
        GoogleBookData googleBookData = client.buscaLivroPorISBN("isbn:".concat(isbn));

        int numeroLetras = contarLetras(text.getData());
        int numeroPalavras = contarPalavras(text.getData());
        int numeroSentencas = contarSentencas(text.getData());

        double indiceDeLeiturabilidadeAutomatizado = indiceDeLeiturabilidadeAutomatizado(numeroLetras, numeroPalavras, numeroSentencas);
        double indiceColeman = indiceColeman(numeroLetras, numeroPalavras, numeroSentencas);
        double indiceGulpease = indiceGulpease(numeroLetras, numeroPalavras, numeroSentencas);

        if (Objects.isNull(googleBookData.items))
            throw new ResourceNotFoundException("Livro não encontrado");

        BigDecimal averageRating = googleBookData.items.stream()
                .findFirst().orElseThrow()
                .getVolumeInfo().getAverageRating();
        return Response.ok(new ScoreResponse(indiceDeLeiturabilidadeAutomatizado, indiceColeman, indiceGulpease, averageRating))
                .build();
    }

    private double indiceDeLeiturabilidadeAutomatizado(int numeroLetras, int numeroPalavras, int numeroSentencas) {
        return 4.6 * ((double) numeroLetras/numeroPalavras) + 0.44 * ((double) numeroPalavras/numeroSentencas) - 20;
    }

    private double indiceColeman(int numeroLetras, int numeroPalavras, int numeroSentencas) {
        return 5.4 * ((double) numeroLetras/numeroPalavras) - 21 * ((double) numeroSentencas/numeroPalavras) - 14;
    }

    private double indiceGulpease(int numeroLetras, int numeroPalavras, int numeroSentencas) {
        return 89 + (((double) 300 * numeroSentencas - 10 * numeroLetras)/numeroPalavras);
    }

    private int contarSentencas(String texto) {
        texto = texto.replace("'", "");
        String[] sentencas = texto.split("[.:!?]");
        return sentencas.length;
    }

    private int contarPalavras(String texto) {
        String[] palavras = texto.split("\\s+");
        return palavras.length;
    }

    private int contarLetras(String texto) {
        texto = texto.replaceAll("[^a-zA-Z]", ""); // Remove caracteres não alfabéticos
        return texto.length();
    }

    private int contarSilabas(String texto) {

        return 0;
    }
}
