package Game;


import Map.MapGenerator;
import Coins.CoinsController;
import Coins.CoinsModel;
import Coins.CoinsView;
import Ghost.GhostController;
import Ghost.GhostModel;
import Ghost.GhostView;
import Lives.LivesController;
import Lives.LivesModel;
import Lives.LivesView;
import Map.MapModel;
import Map.MapView;
import Pacman.PacmanController;
import Pacman.PacmanModel;
import Pacman.PacmanView;
import Score.ScoreController;
import Score.ScoreModel;
import Score.ScoreView;

import javax.swing.*;

public class GameModel {

    private boolean killed = false;

    private GameView view;

    private int level;

    private GhostModel[] ghostModels;

    private GhostView[] ghostViews;

    private final ScoreModel scoreModel;

    private final ScoreView scoreView;

    private MapView mapView;

    private CoinsView coinsView;

    private PacmanView pacmanView;

    private final LivesView livesView;

    private PacmanModel pacmanModel;

    private CoinsModel coinsModel;

    private final LivesModel livesModel;

    private MapModel mapModel;

    private int height;

    private int width;

    private final int rows;

    private final int columns;

    private boolean resized;

    private final JLayeredPane layeredPane;

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    public MapView getMapView() {
        return mapView;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean getResized() {
        return resized;
    }

    public CoinsView getCoinsView() {
        return coinsView;
    }

    public PacmanView getPacmanView() {
        return pacmanView;
    }

    public LivesView getLivesView() {
        return livesView;
    }

    public ScoreView getScoreView() {
        return scoreView;
    }

    public LivesModel getLivesModel() {
        return livesModel;
    }

    public GhostModel[] getGhostModels() {
        return ghostModels;
    }

    public GhostView[] getGhostView() {
        return ghostViews;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public GameView getView() {
        return view;
    }

    public ScoreModel getScoreModel() {
        return scoreModel;
    }

    public PacmanModel getPacmanModel() {
        return pacmanModel;
    }

    public boolean getKilled() {
        return killed;
    }

    public void newLevel() {

        level++;

        if(view != null) view.setTitle("Game — Level " + level);

        for(GhostModel ghostModel : ghostModels) {
            ghostModel.cave();
        }

        scoreModel.start();
        coinsModel.resetCoins();
    }

    public void kill() {

        for (GhostModel ghost : ghostModels) ghost.kill();

        ghostViews = null;
        ghostModels = null;

        pacmanModel.kill();

        pacmanView = null;
        pacmanModel = null;

        mapModel = null;
        mapView = null;

        coinsView = null;
        coinsModel = null;

        killed = true;

        livesModel.interruptTimer();

        view.setFocusable(false);

        SwingUtilities.invokeLater(() -> new GameOver(this));

    }

    public void death() {

        for(GhostModel ghostModel : ghostModels) {
            ghostModel.getView().setVisible(false);
            ghostModel.cave();
        }

        if(livesModel.getLife() == 0) {
            kill();
        }

    }

    public void showGhosts() {
        for(int i = 0; ghostModels != null && i < ghostModels.length; i++) {
            ghostModels[i].getView().setVisible(true);
            scoreModel.start();
        }
    }

    public void refresh() {
        width = mapModel.getSide() * columns;
        height = mapModel.getSide() * rows;
        view.setSize(width, height + (55 + livesView.getHeight()));
        mapModel.refresh();
        pacmanModel.refresh(mapModel.getSide());
        coinsModel.setSide(mapModel.getSide());
        mapView.setBounds(0, 0, getWidth(), getHeight());
        pacmanView.setBounds(0, 0, getWidth(), getHeight());
        coinsView.setBounds(0, 0, getWidth(), getHeight());
        for (GhostView ghostView : ghostViews) ghostView.setBounds(0, 0, getWidth(), getHeight());
        for (GhostModel ghostModel : ghostModels) ghostModel.refresh(mapModel.getSide());
    }

    public GameModel(int rows, int columns) {

        level = 1;

        this.rows = rows;
        this.columns = columns;

        layeredPane = new JLayeredPane();

        mapModel = new MapModel(rows, columns);
        mapView = new MapView(mapModel);

        height = rows * mapModel.getSide();
        width = columns * mapModel.getSide();

        coinsModel = new CoinsModel(mapModel.getSide());
        coinsView = new CoinsView(coinsModel);
        CoinsController coinsController = new CoinsController(coinsModel, coinsView);

        scoreModel = new ScoreModel();
        scoreView = new ScoreView(scoreModel);
        ScoreController scoreController = new ScoreController(scoreModel, scoreView);

        pacmanModel = new PacmanModel(mapModel.getSide(), coinsController, this, scoreController);
        pacmanView = new PacmanView(pacmanModel);
        PacmanController pacmanController = new PacmanController(pacmanModel, pacmanView);

        livesModel = new LivesModel();
        livesView = new LivesView(livesModel);
        new LivesController(livesModel, livesView);

        ghostViews = new GhostView[MapGenerator.getLocation().length];
        ghostModels = new GhostModel[MapGenerator.getLocation().length];

        GhostModel.setID(0);

        for (int i = 0; i < ghostModels.length; i++) {
            ghostModels[i] = new GhostModel(mapModel.getSide(), pacmanController, coinsController);
            ghostViews[i] = new GhostView(ghostModels[i]);
            new GhostController(ghostModels[i], ghostViews[i]);
        }

    }

}
