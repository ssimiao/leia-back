package br.com.score;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RegisterForReflection
public class ScoreResponse {

    private double indiceLeiturabilidadeAutomatizado;

    private double indiceColeman;

    private double indiceGulpease;

    private BigDecimal googleRating;

    private List<Integer> idadeARI;

    private double score = 100;

    private Map<Integer, String> rangeAgeARI = new HashMap<>();

    public ScoreResponse(double indiceLeiturabilidadeAutomatizado, double indiceColeman, double indiceGulpease, BigDecimal googleRating) {
        this.indiceLeiturabilidadeAutomatizado = indiceLeiturabilidadeAutomatizado;
        this.indiceColeman = indiceColeman;
        this.indiceGulpease = indiceGulpease;
        this.googleRating = googleRating;
        populateRangeARI();
        int indice = (int) Math.round(indiceLeiturabilidadeAutomatizado);
        if (indice > 14) {
            indice = 14;
            this.indiceLeiturabilidadeAutomatizado = 14;
        } else if (indice < 1) {
            indice = 1;
            this.indiceLeiturabilidadeAutomatizado = 1;
        }

        String[] idades  = rangeAgeARI.get(indice).split(",");
        this.idadeARI = Arrays.stream(idades).map(Integer::valueOf).toList();
        validaIndiceARI();
    }

    private void populateRangeARI() {
        rangeAgeARI.put(1, "5,6");
        rangeAgeARI.put(2, "6,7");
        rangeAgeARI.put(3, "7,8");
        rangeAgeARI.put(4, "8,9");
        rangeAgeARI.put(5, "9,10");
        rangeAgeARI.put(6, "10,11");
        rangeAgeARI.put(7, "11,12");
        rangeAgeARI.put(8, "12,13");
        rangeAgeARI.put(9, "13,14");
        rangeAgeARI.put(10, "14,15");
        rangeAgeARI.put(11, "15,16");
        rangeAgeARI.put(12, "16,17");
        rangeAgeARI.put(13, "17,18");
        rangeAgeARI.put(14, "18,22");
    }

    public double getScore() {
        return score;
    }

    public BigDecimal getGoogleRating() {
        return googleRating;
    }

    public double getIndiceLeiturabilidadeAutomatizado() {
        return indiceLeiturabilidadeAutomatizado;
    }

    public void setIndiceLeiturabilidadeAutomatizado(double indiceLeiturabilidadeAutomatizado) {
        this.indiceLeiturabilidadeAutomatizado = indiceLeiturabilidadeAutomatizado;
    }

    public double getIndiceColeman() {
        return indiceColeman;
    }

    public void setIndiceColeman(double indiceColeman) {
        this.indiceColeman = indiceColeman;
    }

    public double getIndiceGulpease() {
        return indiceGulpease;
    }

    public void setIndiceGulpease(double indiceGulpease) {
        this.indiceGulpease = indiceGulpease;
    }

    public List<Integer> getIdadeARI() {
        return idadeARI;
    }

    public String getCuradoriaAutomatizada() {
        if (score > 90)
            return "Recomendado";
        else if (score < 50)
            return "Não Recomendado";

        return "Analise Curador";
    }

    public void validaIndiceARI() {
        double diferencaResultados = 7 - indiceLeiturabilidadeAutomatizado;
        if (Math.signum(diferencaResultados) == -1.0) {
            diferencaResultados = Math.abs(diferencaResultados) * 5;
            this.score = this.score - diferencaResultados;
        }
    }

    public void validaIndiceGulpease() {
        if (indiceGulpease > 60 && indiceGulpease < 80) {
            this.score = this.score - 25;
        } else if (indiceGulpease < 40) {
            this.score -= 50;
        }
    }
}
