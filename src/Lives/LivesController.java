package Lives;

public class LivesController {

    private final LivesModel model;

    public LivesController(LivesModel model, LivesView view) {
        this.model = model;
    }

    public LivesModel getModel() {
        return model;
    }
}
