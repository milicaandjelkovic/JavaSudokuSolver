package tests;

import nl.elridge.sudoku.controller.ButtonController;
import nl.elridge.sudoku.model.Game;
import nl.elridge.sudoku.view.Sudoku;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.awt.event.ActionEvent;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class TestClass extends BaseTestClass{
    @InjectMocks
    private ButtonController controller;

    @Mock
    private Game game;

    private static Game games = new Game();
    private static BaseTestClass test = new BaseTestClass();


    @BeforeEach
    public void setupSudoku() {
        games.newGame();
    }

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
//Provera ActionEvent-a za NewGame
    @Test
    public void newGameTest(){

        ActionEvent event = mock(ActionEvent.class);
        controller = new ButtonController(game);

        when(event.getActionCommand()).thenReturn("New");
        doNothing().when(game).newGame();

        controller.actionPerformed(event);

        verify(game, times(1)).newGame();
    }
//Provera ActionEvent-a za CheckGame
    @Test
    public void checkGameTest(){
        ActionEvent event = mock(ActionEvent.class);
        controller = new ButtonController(game);

        when(event.getActionCommand()).thenReturn("Check");
        doNothing().when(game).checkGame();

        controller.actionPerformed(event);

        verify(game, times(1)).checkGame();
    }
//Provera ActionEvent-a za Help
    @Test
    public void setHelpTest(){
        ActionEvent event = mock(ActionEvent.class);
        controller = new ButtonController(game);

        when(event.getActionCommand()).thenReturn("Help on");
        doNothing().when(game).setHelp(true);

        controller.actionPerformed(event);

    }

    // Ova test metoda treba da proveri da li je game.game niz promenjen nakon game.newGame()
    @Test
    public void generateNewGameTest() {
        int[][] tmp = copyArr(test.generateGameArray(games));
        games.newGame();
        Assertions.assertFalse(Arrays.deepEquals(test.generateGameArray(games), tmp));
    }

    // Metoda za proveru help)n checkbox-a
    @Test
    public void helpOnCheckBoxTrueTest() {
        games.setHelp(true);
        Assertions.assertTrue(games.isHelp(), "Help is checked");
    }

    @Test
    public void helpOnCheckBoxFalseTest() {
        games.setHelp(false);
        Assertions.assertFalse(games.isHelp(), "Help is not checked!");
    }

    // Metoda koja proverava da li je selected number nakon postavljanja u zadato polje zapravo u tom polju
    @Test
    public void selectedNumberTest() {
        int x = 3;
        int y = 6;
        int n = 5;
        games.setSelectedNumber(n);
        games.setNumber(x, y, games.getSelectedNumber());
        Assertions.assertEquals(games.getSelectedNumber(), games.getNumber(x, y), "Number is not right!");
    }

    // Proverava da li je broj kandidat za to polje i da li to polje u nizu check nosi vrednost true
    // postavlja ga ako su ti uslovi ispunjeni i dokazuje da polje na koje je broj postavljen
    // ima vrednost true u nizu check[x][y]
    // korisceni su parametri ValueSource
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9 })
    public void isSelectedNumberCandidateValidTrue(int n) {
        // kreira boolean[][] check, gde vrednost true znaci da taj borj stoji na tom mestu
        games.checkGame();
        games.setSelectedNumber(n);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (games.isSelectedNumberCandidate(x, y) && games.isCheckValid(x, y)) {
                    games.setNumber(x, y, games.getSelectedNumber());
                    Assertions.assertTrue(games.isCheckValid(x, y), "The number is not right!");
                }
            }
        }
    }


    // starts the gui
    @Test
    public void testGui() {
        Sudoku.main(null);
    }




    @AfterEach
    public void endTestMessage() {
        System.out.println("Test Method Over");
    }
    @AfterAll
    public static void quitSudoku() {
        System.exit(0);
    }
}

