package com.automaton.gui;

import com.automaton.application.Automaton;
import com.automaton.application.GameOfLife;
import com.automaton.cell.CellNeighbourhood;
import com.automaton.cell.MoorNeighbourhood;
import com.automaton.cell.VonNeumanNeighbourhood;
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

    private int height = 20, width = 20;


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
            height = Integer.parseInt(heightTextField.getText());
            width = Integer.parseInt(widthTextField.getText());
        } catch (NumberFormatException ex) {
            System.out.println("Don't try to break my program, please");
            return;
        }

        if(height<5) height=5;
        if(width<5) width=5;

        if(height>100) height=100;
        if(width>100) width=100;

        gc.setFill(Color.CHOCOLATE);

        int offset = 1;

        int rectHeight = (int)mainCanvas.getHeight()/height;
        int rectWidth = (int)mainCanvas.getWidth()/width;

        int cellHeight = rectHeight - 2*offset;
        int cellWidth = rectWidth - 2*offset;

        for(int j=0; j<height; j++)
            for (int i=0; i<width; i++) {
                gc.fillRoundRect((i*rectWidth)+offset,
                        (j*rectHeight)+offset,
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
        toGeneratingMap();
        clearCanvas();
    }

    @FXML
    public void acquireParamsAndOnToSimulation(ActionEvent event) {
        switch (gameChoiceBox.getSelectionModel().getSelectedIndex()) {
            case 0: //Game of Life
                CellNeighbourhood cellNeighbourhood;
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

                //TODO factory



                Automaton automaton = new GameOfLife(
                        cellNeighbourhood,
                        null,
                        height,
                        width,
                        false
                );
        }
    }



    private void clearCanvas() {
        gc = mainCanvas.getGraphicsContext2D();
        // paint canvas with some deep dark terrifying colour
        gc.setFill(Color.BLANCHEDALMOND);
        gc.fillRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
        //mainCanvas.setStyle("-fx-background-color:red"); y u no work? :<
    }

}
