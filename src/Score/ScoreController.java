package Score;

public class ScoreController {

    ScoreModel model;

    ScoreView view;

    public ScoreController(ScoreModel model, ScoreView view) {
        this.model = model;
        this.view = view;
    }

    public ScoreModel getModel() {
        return model;
    }
}
