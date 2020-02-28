package footballbetting;

import javax.persistence.*;

@Entity
@Table(name = "result_predictions")
public class ResultPrediction extends BaseEntity {
    private Prediction prediction;

    public ResultPrediction() {
    }

    @Column(name = "prediction")
    @Enumerated(EnumType.ORDINAL)
    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }
}
