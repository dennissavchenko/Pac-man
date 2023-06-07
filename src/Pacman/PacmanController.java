package Pacman;

public class PacmanController {

    private final PacmanModel model;

    public PacmanController(PacmanModel model, PacmanView view) {

        this.model = model;
        model.setView(view);

    }

    public PacmanModel getModel() {
        return model;
    }
}
