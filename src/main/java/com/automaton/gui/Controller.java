package com.automaton.gui;

import com.automaton.application.*;
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

    private int height = 20, width = 20, elementaryStepCounter = 0;
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

            // setting standard rules for Conway's Game of Life
            if(heightTextField.getText().equals("") && widthTextField.getText().equals("")) {
                height = 20;
                width = 20;
            } else {
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

        neighbourhoodChoiceBox.getItems().removeAll();

        switch (gameChoiceBox.getSelectionModel().getSelectedIndex()) {
            case 0: // Game of Life
                neighbourhoodChoiceBox.getItems().addAll("Moor", "Von Neumann");
                neighbourhoodChoiceBox.setDisable(false);
                neighbourhoodChoiceBox.getSelectionModel().selectFirst();
                ruleTextField.setDisable(false);
                ruleTextField.setPromptText("23/3");
                wrappingChoiceBox.setDisable(false);
                rangeTextField.setDisable(false);
                break;
            case 1: // Game of Life - QuadState
                neighbourhoodChoiceBox.getItems().addAll("Moor", "Von Neumann");
                neighbourhoodChoiceBox.setDisable(false);
                neighbourhoodChoiceBox.getSelectionModel().selectFirst();
                ruleTextField.setDisable(true);
                ruleTextField.setText("23/3");
                wrappingChoiceBox.setDisable(false);
                rangeTextField.setDisable(false);
                break;
            case 2: // Langton Ant
                neighbourhoodChoiceBox.getItems().addAll("Moor", "Von Neumann");
                neighbourhoodChoiceBox.getSelectionModel().select(1);
                neighbourhoodChoiceBox.setDisable(true);
                ruleTextField.setDisable(true);
                ruleTextField.setPromptText("---");
                wrappingChoiceBox.setDisable(false);
                rangeTextField.setDisable(true);
                rangeTextField.setPromptText("---");
                break;
            case 3: // Wireworld
                neighbourhoodChoiceBox.getItems().addAll("Moor", "Von Neumann");
                neighbourhoodChoiceBox.getSelectionModel().select(0);
                neighbourhoodChoiceBox.setDisable(true);
                ruleTextField.setDisable(true);
                ruleTextField.setPromptText("---");
                wrappingChoiceBox.setDisable(true);
                rangeTextField.setDisable(true);
                rangeTextField.setPromptText("---");
                break;
            case 4: // Elementary
                neighbourhoodChoiceBox.getItems().addAll("Elementary Neighbourhood");
                neighbourhoodChoiceBox.getSelectionModel().select(0);
                neighbourhoodChoiceBox.setDisable(true);
                rangeTextField.setDisable(true);
                rangeTextField.setPromptText("---");
                wrappingChoiceBox.setDisable(true);
                ruleTextField.setDisable(false);
                ruleTextField.setPromptText("30");
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

        if(ruleTextField.getText().equals("") && gameChoiceBox.getSelectionModel().isSelected(0))
            ruleTextField.setText("23/3");
        else if (ruleTextField.getText().equals("") && gameChoiceBox.getSelectionModel().isSelected(4))
            ruleTextField.setText("30");

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
                        false,
                        ruleTextField.getText()
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
                        true,
                        ruleTextField.getText()
                );
                break;
            case 2: // Langton Ant
                factory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD));

                currAutomaton = new LangtonAnt(
                        factory,
                        height,
                        width,
                        wrappingChoiceBox.getValue()
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
                while (cellIterator.hasNext()) {
                    gc.setFill(Color.DARKGREY);
                    Cell cell = cellIterator.next();
                    Coords2D coords2D = (Coords2D) cell.getCoords();
                    drawCell(coords2D.getX(), coords2D.getY());
                }

                break;
            case 4: //Elementary
                elementaryStepCounter=0;
                factory = new UniformStateFactory(BinaryState.DEAD);

                currAutomaton = new ElementaryAutomaton(
                        factory, width, Byte.parseByte(ruleTextField.getText())
                );


        }

        mainCanvas.setOnMouseClicked(mouseEvent -> {

            if(state==State.SIM) {
                double x = mouseEvent.getX(), y = mouseEvent.getY();
                
                int coordX = (int)(x/(cellWidth+offset)), coordY = (int)(y/(cellHeight+offset));

                Automaton.CellIterator cellIterator = currAutomaton.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if(cell.getCoords().equals(new Coords2D(coordX, coordY))) {
                        System.out.println("X: "+coordX+", Y: "+coordY);
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
                                drawCell(coordX, coordY);
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

                                drawCell(coordX, coordY);
                                break;
                            case 2: // Langton Ant
                                LangtonCell langtonCell = (LangtonCell) state;
                                BinaryState binaryState = langtonCell.getCellState();
                                boolean isThereAnAnt = langtonCell.getAntStates().size()>0;

                                LangtonCell newLangtonCell;
                                // alive - dark, dead - white
                                if(isThereAnAnt) {
                                    AntState antState = langtonCell.getAntStates().get(0);
                                    if(binaryState == BinaryState.ALIVE && antState == AntState.NORTH) {
                                        newLangtonCell = new LangtonCell(binaryState);
                                        newLangtonCell.getAntStates().add(AntState.EAST);
                                        cellIterator.setState(newLangtonCell);
                                        gc.setFill(Color.DARKGREY);
                                    } else if(binaryState == BinaryState.ALIVE && antState == AntState.EAST) {
                                        gc.setFill(Color.DARKGREY);
                                        newLangtonCell = new LangtonCell(binaryState);
                                        newLangtonCell.getAntStates().add(AntState.SOUTH);
                                        cellIterator.setState(newLangtonCell);
                                    } else if(binaryState == BinaryState.ALIVE && antState == AntState.SOUTH) {
                                        gc.setFill(Color.DARKGREY);
                                        newLangtonCell = new LangtonCell(binaryState);
                                        newLangtonCell.getAntStates().add(AntState.WEST);
                                        cellIterator.setState(newLangtonCell);
                                    } else if(binaryState == BinaryState.ALIVE && antState == AntState.WEST) {
                                        gc.setFill(Color.DARKGREY);
                                        newLangtonCell = new LangtonCell(binaryState);
                                        cellIterator.setState(newLangtonCell);
                                    } else if(binaryState == BinaryState.DEAD && antState == AntState.NORTH) {
                                        newLangtonCell = new LangtonCell(binaryState);
                                        newLangtonCell.getAntStates().add(AntState.EAST);
                                        cellIterator.setState(newLangtonCell);
                                        gc.setFill(Color.FLORALWHITE);
                                    } else if(binaryState == BinaryState.DEAD && antState == AntState.EAST) {
                                        newLangtonCell = new LangtonCell(binaryState);
                                        newLangtonCell.getAntStates().add(AntState.SOUTH);
                                        cellIterator.setState(newLangtonCell);
                                        gc.setFill(Color.FLORALWHITE);
                                    } else if(binaryState == BinaryState.DEAD && antState == AntState.SOUTH) {
                                        newLangtonCell = new LangtonCell(binaryState);
                                        newLangtonCell.getAntStates().add(AntState.WEST);
                                        cellIterator.setState(newLangtonCell);
                                        gc.setFill(Color.FLORALWHITE);
                                    } else  { // if(binaryState == BinaryState.DEAD && antState == AntState.WEST)
                                        newLangtonCell = new LangtonCell(BinaryState.ALIVE);
                                        newLangtonCell.getAntStates().add(AntState.NORTH);
                                        cellIterator.setState(newLangtonCell);
                                        gc.setFill(Color.DARKGREY);
                                    }
                                } else {
                                    if(binaryState == BinaryState.ALIVE) {
                                        newLangtonCell = new LangtonCell(BinaryState.DEAD);
                                        cellIterator.setState(newLangtonCell);
                                        gc.setFill(Color.FLORALWHITE);
                                    }
                                    else {
                                        //gc.setFill(Color.DARKGREY);
                                        newLangtonCell = new LangtonCell(binaryState);
                                        newLangtonCell.getAntStates().add(AntState.NORTH);
                                        cellIterator.setState(newLangtonCell);
                                        //binaryState = BinaryState.ALIVE;
                                        //gc.setFill(Color.DARKGREY);
                                        //langtonCell.getAntStates().add(AntState.NORTH);

                                    }
                                }

                                drawCell(coordX, coordY);
                                if(newLangtonCell.getAntStates().size()>0) {
                                    drawAnt(coordX, coordY, newLangtonCell.getAntStates().get(0));
                                }
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
                                drawCell(coordX, coordY);
                                break;
                        }

                    } else if (cell.getCoords().equals(new Coords1D(coordX))) {
                        CellState state = cell.getState();
                        System.out.println("state: "+state);
                        //Elementary
                        System.out.println("coordY: "+coordY);
                        if(coordY==0) {
                            //System.out.println("Clicked");
                            if (state == BinaryState.DEAD) {
                                cellIterator.setState(BinaryState.ALIVE);
                                gc.setFill(Color.CHOCOLATE);
                            } else {
                                cellIterator.setState(BinaryState.DEAD);
                                gc.setFill(Color.FLORALWHITE);
                            }
                            drawCell(coordX, coordY);
                        }
                    }

                }
            }
        });
    }

    @FXML
    public void backToParams(ActionEvent event) {
        state = State.PARAMS;
        toParamMenu();
        clearCanvas();

        // painting board i
        Automaton.CellIterator cellIterator = currAutomaton.cellIterator();

        for(int i=0; i<width; i++)
            for(int j=0; j<height; j++) {
                gc.setFill(Color.FLORALWHITE);
                drawCell(i,j);
            }
    }

    @FXML
    private void nextState(ActionEvent event) {
        ++elementaryStepCounter;
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

                    if(((LangtonCell)cell.getState()).getCellState()== BinaryState.DEAD)
                        gc.setFill(Color.FLORALWHITE);
                    else
                        gc.setFill(Color.DARKGREY);

                    drawCell(coords2D3.getX(), coords2D3.getY());

                    LangtonCell langtonCell = (LangtonCell) state;

                    for (AntState antState : langtonCell.getAntStates()) {
                        drawAnt(coords2D3.getX(), coords2D3.getY(), antState);
                    }

                    
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

                case 4: // Elementary
                    Coords1D coords1D = (Coords1D)cell.getCoords();
                    if(state==BinaryState.DEAD) {
                        gc.setFill(Color.FLORALWHITE);
                    } else  {
                        gc.setFill(Color.CHOCOLATE);
                    }
                    drawCell(coords1D.getX(), elementaryStepCounter);
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

    private void drawAnt(double startX, double startY, AntState antState) {
        double[] xtr = new double[3], ytr = new double[3];
        double baseX = offset + startX * (cellWidth+offset);
        double baseY = offset + startY * (cellHeight+offset);

        if (antState.equals(AntState.EAST)) {
            xtr[0] = baseX + offset;
            xtr[1] = baseX + offset;
            xtr[2] = baseX  + cellWidth;
            ytr[0] = baseY + offset;
            ytr[1] = baseY + cellHeight - offset;
            ytr[2] = baseY+ cellHeight/2;
        }
        else if (antState.equals(AntState.WEST)) {
            xtr[0] = baseX + cellWidth - offset;
            xtr[1] = baseX + cellWidth - offset;
            xtr[2] = baseX;
            ytr[0] = baseY + offset;
            ytr[1] = baseY + cellHeight - offset;
            ytr[2] = baseY+ cellHeight/2;
        }
        else if (antState.equals(AntState.NORTH)) {
            xtr[0] = baseX + offset;
            xtr[1] = baseX + cellWidth - offset;
            xtr[2] = baseX  + cellWidth/2;
            ytr[0] = baseY + cellHeight - offset;
            ytr[1] = baseY + cellHeight - offset;
            ytr[2] = baseY;
        }
        else if (antState.equals(AntState.SOUTH)) {
            xtr[0] = baseX + offset;
            xtr[1] = baseX + cellWidth - offset;
            xtr[2] = baseX + cellWidth/2;
            ytr[0] = baseY + offset;
            ytr[1] = baseY + offset;
            ytr[2] = baseY+ cellHeight;

        }
        gc.setFill(Color.MEDIUMVIOLETRED);
        gc.fillPolygon(xtr, ytr, xtr.length);
        gc.setFill(Color.FLORALWHITE);

        /*gc.fillRoundRect(offset+startX*(cellWidth+offset),
                offset+startY*(cellHeight+offset),
                cellWidth,
                cellHeight,
                cellWidth*3/5,
                cellHeight*3/5);*/
    }

    private void clearCanvas() {
        gc = mainCanvas.getGraphicsContext2D();
        // paint canvas with some deep dark terrifying colour
        gc.setFill(Color.BLANCHEDALMOND);
        gc.fillRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
        //mainCanvas.setStyle("-fx-background-color:red"); y u no work? :<
    }

}
