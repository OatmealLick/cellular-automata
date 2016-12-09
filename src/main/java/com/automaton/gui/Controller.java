package com.automaton.gui;

import com.automaton.application.Automaton;
import com.automaton.application.GameOfLife;
import com.automaton.application.LangtonAnt;
import com.automaton.application.WireWorld;
import com.automaton.cell.*;
import com.automaton.cell.Cell;
import com.automaton.states.*;
import com.automaton.states.CellState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Controller {

    @FXML
    private Canvas mainCanvas;

    private GraphicsContext gc;

    @FXML
    private ChoiceBox<String> gameChoiceBox;

    @FXML
    private TextField widthTextField;

    @FXML
    private VBox mapGenBox;

    @FXML
    private VBox typeParamBox;

    @FXML
    private VBox simBox;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField rangeTextField;

    @FXML
    private ChoiceBox<String> neighbourhoodChoiceBox;

    @FXML
    private TextField ruleTextField;

    /*@FXML
    private ChoiceBox<Boolean> quadstateChoiceBox;*/

    @FXML
    private ChoiceBox<Boolean> wrappingChoiceBox;

    private enum State {MAP_GEN, PARAMS, SIM}

    private State state;

    private int height = 20, width = 20;
    private double cellHeight, cellWidth, offset;

    private Automaton currAutomaton;

    private void toParamMenu() {
        mapGenBox.setManaged(false);
        mapGenBox.setVisible(false);
        typeParamBox.setVisible(true);
        typeParamBox.setManaged(true);
        simBox.setVisible(false);
        simBox.setManaged(false);
    }

    private void toGeneratingMap() {
        //TODO stop

        mapGenBox.setVisible(true);
        mapGenBox.setManaged(true);
        typeParamBox.setVisible(false);
        typeParamBox.setManaged(false);
        simBox.setVisible(false);
        simBox.setManaged(false);

    }

    private void toSimMenu() {

        mapGenBox.setVisible(false);
        mapGenBox.setManaged(false);
        typeParamBox.setVisible(false);
        typeParamBox.setManaged(false);
        simBox.setVisible(true);
        simBox.setManaged(true);

    }

    @FXML
    private void initialize() {
        state = State.MAP_GEN;
        //initialize choice box with game types
        gameChoiceBox.getItems().addAll(
                "Game of Life and Death",
                "Game of QuadLife and QuadDeath",
                "Horrifying Langton Ant of Horror",
                "PEC",
                "Amoebas"
        );


        gameChoiceBox.getSelectionModel().selectFirst();

        neighbourhoodChoiceBox.getItems().addAll("Moor", "Von Neumann");
        wrappingChoiceBox.getItems().addAll(true, false);
        wrappingChoiceBox.getSelectionModel().selectFirst();
        //quadstateChoiceBox.getItems().addAll(true, false);
        rangeTextField.setPromptText("1");

        // disable them, so they don't occupe space
        typeParamBox.setManaged(false);
        simBox.setManaged(false);

        /*//height slider
        heightSlider.setMin(5);
        heightSlider.setMax(100);
        heightSlider.setShowTickLabels(true);
        heightSlider.setShowTickMarks(true);
*/

        clearCanvas();
    }

    @FXML
    private void generateMapAndProceedToParamMenu(ActionEvent event) {
        clearCanvas();

        try {
            // TODO throw out when tests are over - start
            if(heightTextField.getText().equals("") && widthTextField.getText().equals("")) {
                height = 20;
                width = 20;
            } else { // TODO end
                height = Integer.parseInt(heightTextField.getText());
                width = Integer.parseInt(widthTextField.getText());
            }
        } catch (NumberFormatException ex) {
            System.out.println("Don't try to break my program, please");
            return;
        }

        state = State.PARAMS;

        if(height<5) height=5;
        if(width<5) width=5;

        if(height>100) height=100;
        if(width>100) width=100;

        gc.setFill(Color.FLORALWHITE);

        offset = 2;

        cellHeight = (mainCanvas.getHeight() - (height+1)*offset)/height;
        cellWidth = (mainCanvas.getWidth() - (width+1)*offset)/width;

        for(int j=0; j<height; j++)
            for (int i=0; i<width; i++) {
                gc.fillRoundRect(offset + i*(cellWidth+offset),
                        offset + j*(cellHeight+offset),
                        cellWidth,
                        cellHeight,
                        cellWidth*3/5,
                        cellHeight*3/5);
            }

        toParamMenu();

        //neighbourhoodChoiceBox.getItems().removeAll();

        switch (gameChoiceBox.getSelectionModel().getSelectedIndex()) {
            case 0: // Game of Life
                neighbourhoodChoiceBox.setDisable(false);
                neighbourhoodChoiceBox.getSelectionModel().selectFirst();
                ruleTextField.setDisable(false);
                ruleTextField.setPromptText("np. \"23/3\"");
                //quadstateChoiceBox.setDisable(true);
                wrappingChoiceBox.setDisable(false);
                rangeTextField.setDisable(false);
                break;
            case 1: // Game of Life - QuadState
                neighbourhoodChoiceBox.setDisable(false);
                neighbourhoodChoiceBox.getSelectionModel().selectFirst();
                ruleTextField.setDisable(true);
                ruleTextField.setText("23/3");
                //quadstateChoiceBox.setDisable(false);
                wrappingChoiceBox.setDisable(false);
                rangeTextField.setDisable(false);
                break;
            case 2: // Langton Ant
                neighbourhoodChoiceBox.getSelectionModel().select(1);
                neighbourhoodChoiceBox.setDisable(true);
                ruleTextField.setDisable(true);
                ruleTextField.setPromptText("---");
                //quadstateChoiceBox.setDisable(true);
                wrappingChoiceBox.setDisable(false);
                rangeTextField.setDisable(true);
                rangeTextField.setPromptText("---");
                break;
            case 3: // Wireworld
                neighbourhoodChoiceBox.getSelectionModel().select(0);
                neighbourhoodChoiceBox.setDisable(true);
                ruleTextField.setDisable(true);
                ruleTextField.setPromptText("---");
                //quadstateChoiceBox.setDisable(true);
                wrappingChoiceBox.setDisable(true);
                rangeTextField.setDisable(true);
                rangeTextField.setPromptText("---");
                break;
            case 4:
                break;
        }

    }

    @FXML
    private void backToMapGen(ActionEvent event) {
        state = State.MAP_GEN;
        toGeneratingMap();
        clearCanvas();
    }

    @FXML
    public void acquireParamsAndOnToSimulation(ActionEvent event) {
        state = State.SIM;
        toSimMenu();
        CellNeighbourhood cellNeighbourhood;
        CellStateFactory factory;
        if(rangeTextField.getText().equals(""))
            rangeTextField.setText(1+"");
        switch (gameChoiceBox.getSelectionModel().getSelectedIndex()) {
            case 0: //Game of Life

                if(neighbourhoodChoiceBox.getSelectionModel().isSelected(0))
                    cellNeighbourhood = new MoorNeighbourhood(
                            width,
                            height,
                            Integer.parseInt(rangeTextField.getText()),
                            wrappingChoiceBox.getValue()
                    );
                else
                    cellNeighbourhood = new VonNeumanNeighbourhood(
                            width,
                            height,
                            Integer.parseInt(rangeTextField.getText()),
                            wrappingChoiceBox.getValue()
                    );

                factory = new UniformStateFactory(BinaryState.DEAD);

                currAutomaton = new GameOfLife(
                        cellNeighbourhood,
                        factory,
                        height,
                        width,
                        false
                );
                break;
            case 1: // Game of Life - QuadYOLO
                if(neighbourhoodChoiceBox.getSelectionModel().isSelected(0))
                    cellNeighbourhood = new MoorNeighbourhood(
                            width,
                            height,
                            Integer.parseInt(rangeTextField.getText()),
                            wrappingChoiceBox.getValue()
                    );
                else
                    cellNeighbourhood = new VonNeumanNeighbourhood(
                            width,
                            height,
                            Integer.parseInt(rangeTextField.getText()),
                            wrappingChoiceBox.getValue()
                    );

                factory = new UniformStateFactory(QuadState.DEAD);

                currAutomaton = new GameOfLife(
                        cellNeighbourhood,
                        factory,
                        height,
                        width,
                        true
                );
                break;
            case 2: // Langton Ant
                factory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD));

                currAutomaton = new LangtonAnt(
                        factory,
                        height,
                        width,
                        true
                );
                break;
            case 3: // Wireworld 
                factory = new UniformStateFactory(WireElectronState.VOID);
                
                currAutomaton = new WireWorld(
                        factory,
                        height,
                        width
                );

                Automaton.CellIterator cellIterator = currAutomaton.cellIterator();
                System.out.println(gameChoiceBox.getSelectionModel().getSelectedIndex());
                while (cellIterator.hasNext()) {
                    gc.setFill(Color.DARKGREY);
                    Cell cell = cellIterator.next();
                    Coords2D coords2D = (Coords2D) cell.getCoords();
                    drawCell(coords2D.getX(), coords2D.getY());
                }

                break;


        }

        mainCanvas.setOnMouseClicked(mouseEvent -> {

            System.out.println(mouseEvent.getX());
            if(state==State.SIM) {
                double x = mouseEvent.getX(), y = mouseEvent.getY();
                
                int coordX = (int)(x/(cellWidth+offset)), coordY = (int)(y/(cellHeight+offset));

                Automaton.CellIterator cellIterator = currAutomaton.cellIterator();
                System.out.println(gameChoiceBox.getSelectionModel().getSelectedIndex());
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if(cell.getCoords().equals(new Coords2D(coordX, coordY))) {
                        CellState state = cell.getState();
                        switch (gameChoiceBox.getSelectionModel().getSelectedIndex()) {
                            case 0: //Game of Life
                                if (state == BinaryState.DEAD) {
                                    cellIterator.setState(BinaryState.ALIVE);
                                    gc.setFill(Color.CHOCOLATE);
                                } else {
                                    cellIterator.setState(BinaryState.DEAD);
                                    gc.setFill(Color.FLORALWHITE);
                                }
                                break;
                            case 1: //Game of Life - Quadruple
                                if (state == QuadState.DEAD) {
                                    gc.setFill(Color.INDIANRED);
                                    cellIterator.setState(QuadState.RED);
                                } else if (state == QuadState.RED) {
                                    gc.setFill(Color.CADETBLUE);
                                    cellIterator.setState(QuadState.BLUE);
                                } else if (state == QuadState.BLUE) {
                                    gc.setFill(Color.DARKOLIVEGREEN);
                                    cellIterator.setState(QuadState.GREEN);
                                } else if (state == QuadState.GREEN) {
                                    gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                                    cellIterator.setState(QuadState.YELLOW);
                                } else if (state == QuadState.YELLOW) {
                                    gc.setFill(Color.FLORALWHITE);
                                    cellIterator.setState(QuadState.DEAD);
                                }

                                break;
                            case 2: // Langton Ant
                                break;
                            case 3: // Wireworld
                                if (state == WireElectronState.VOID) {
                                    gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                                    cellIterator.setState(WireElectronState.WIRE);
                                } else if (state == WireElectronState.WIRE) {
                                    gc.setFill(Color.INDIANRED);
                                    cellIterator.setState(WireElectronState.ELECTRON_HEAD);
                                } else if (state == WireElectronState.ELECTRON_HEAD) {
                                    gc.setFill(Color.CADETBLUE);
                                    cellIterator.setState(WireElectronState.ELECTRON_TAIL);
                                } else if (state == WireElectronState.ELECTRON_TAIL) {
                                    gc.setFill(Color.DARKGREY);
                                    cellIterator.setState(WireElectronState.VOID);
                                }
                                break;

                        }
                        drawCell(coordX, coordY);
                    }

                }
            }
        });
    }

    //TODO BACK BUTTON WITH DRAWING CANVAS AGAIN

    @FXML
    private void nextState(ActionEvent event) {
        currAutomaton = currAutomaton.nextState();
        Automaton.CellIterator cellIterator = currAutomaton.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            CellState state = cell.getState();
            switch (gameChoiceBox.getSelectionModel().getSelectedIndex()) {
                case 0: //Game of Life
                    Coords2D coords2D1 = (Coords2D)cell.getCoords();
                    if(state==BinaryState.DEAD) {
                        gc.setFill(Color.FLORALWHITE);
                    } else  {
                        gc.setFill(Color.CHOCOLATE);
                    }
                    drawCell(coords2D1.getX(), coords2D1.getY());
                    break;
                case 1: //Game of Life - Quadralife
                    Coords2D coords2D2 = (Coords2D)cell.getCoords();
                    if(state == QuadState.DEAD) {
                        gc.setFill(Color.FLORALWHITE);
                    } else if(state == QuadState.RED) {
                        gc.setFill(Color.INDIANRED);
                    } else if(state == QuadState.BLUE) {
                        gc.setFill(Color.CADETBLUE);
                    } else if(state == QuadState.GREEN) {
                        gc.setFill(Color.DARKOLIVEGREEN);
                    } else if(state == QuadState.YELLOW) {
                        gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                    }
                    drawCell(coords2D2.getX(), coords2D2.getY());
                    break;
                case 2: // Langton Ant
                    Coords2D coords2D3 = (Coords2D)cell.getCoords();
                    break;
                case 3: // Wireworld
                    Coords2D coords2D4 = (Coords2D)cell.getCoords();
                    if (state == WireElectronState.VOID) {
                        gc.setFill(Color.DARKGREY);
                    } else if (state == WireElectronState.WIRE) {
                        gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                    } else if (state == WireElectronState.ELECTRON_HEAD) {
                        gc.setFill(Color.INDIANRED);
                    } else if (state == WireElectronState.ELECTRON_TAIL) {
                        gc.setFill(Color.CADETBLUE);
                    }
                    drawCell(coords2D4.getX(), coords2D4.getY());
                    break;
            }

        }

    }

    private void drawCell(double startX, double startY) {
        gc.fillRoundRect(offset+startX*(cellWidth+offset),
                offset+startY*(cellHeight+offset),
                cellWidth,
                cellHeight,
                cellWidth*3/5,
                cellHeight*3/5);
    }

    private void clearCanvas() {
        gc = mainCanvas.getGraphicsContext2D();
        // paint canvas with some deep dark terrifying colour
        gc.setFill(Color.BLANCHEDALMOND);
        gc.fillRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
        //mainCanvas.setStyle("-fx-background-color:red"); y u no work? :<
    }

}
