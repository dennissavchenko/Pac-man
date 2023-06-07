package Coins;

public class CoinsController {

    private CoinsView view;

    private CoinsModel model;

    public CoinsController(CoinsModel model, CoinsView view) {
        this.model = model;
        this.view = view;
    }

    public CoinsModel getModel() {
        return model;
    }

}
