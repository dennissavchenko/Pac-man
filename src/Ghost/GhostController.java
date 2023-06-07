package Ghost;

public class GhostController {
    public GhostController(GhostModel model, GhostView view) {
        model.setView(view);
    }
}
