package fr.univartois.butinfo.ihm;

import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.*;
import javafx.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Axel Kleszewski
 *
 * @version 0.1.0
 */
public class MorseCodeController {
    /**
    * Partie FXML
     */

    @FXML
    private Label greenLight;

    @FXML
    private Label redLight;

    @FXML
    private Label morseLight;

    @FXML
    private TextField wordInput;

    @FXML
    private Button nextWordButton;

    @FXML
    void onWordInput(ActionEvent event) {
        if(checkAnswer()) {
            morseLight.setStyle("-fx-background-color: #4A4A00");
            greenLight.setStyle("-fx-background-color: #00A400");
            nextWordButton.setDisable(false);
            wordInput.setDisable(true);
            morseLightOnTimeline.stop();
            morseLightOffTimeline.stop();
            shortBeepPlayer.stop();
            longBeepPlayer.stop();
        }
        else {
            redLight.setStyle("-fx-background-color: #A40000");
            wordInput.setDisable(true);
            lightsTimeline.play();
        }
    }

    @FXML
    void onNextWordButton(ActionEvent event) {
        sequenceIndex = 0;
        greenLight.setStyle("-fx-background-color: #004A00");
        wordAnswer = wordList.get(randomWord.nextInt(0, wordList.size()));
        buildMorseCode.delete(0, buildMorseCode.length());
        nextWordButton.setDisable(true);
        wordInput.setDisable(false);
        wordInput.setText(null);
        //totalMorseLightLengthCalculation(translationLetterMorse());
        flashMorseLight(translationLetterMorse());
    }

    @FXML
    void initialize() {
        fillLetterList();
        fillWordList();
        lightsTimeline.setOnFinished(event -> {
            if(!checkAnswer()) {
                redLight.setStyle("-fx-background-color: #4A0000");
                wordInput.setDisable(false);
                wordInput.setText(null);
            }
        });
        morseLightOnTimeline.setOnFinished(event -> {
            morseLight.setStyle("-fx-background-color: #4A4A00");
            shortBeepPlayer.stop();
            longBeepPlayer.stop();
            morseLightOffTimeline.play();
        });
        morseLightOffTimeline.setOnFinished(event -> {
            if(sequenceIndex > translationLetterMorse().length()) {
                sequenceIndex = 0;
            }
            flashMorseLight(translationLetterMorse());
        });
    }

    /**
    * Partie Java
     */
    List<String> letterList = new ArrayList<>();

    List<String> wordList = new ArrayList<>();

    Random randomWord = new Random();

    String wordAnswer;

    String wordGuess;

    StringBuilder buildMorseCode = new StringBuilder();

    Timeline lightsTimeline = new Timeline(new KeyFrame(Duration.seconds(1)));

    KeyFrame morseLightOnKeyframe;

    KeyFrame morseLightOffKeyframe;

    Timeline morseLightOnTimeline = new Timeline();

    Timeline morseLightOffTimeline = new Timeline();

    int sequenceIndex;

    Media shortBeep = new Media(new File("sounds/shortBeep.wav").toURI().toString());

    Media longBeep = new Media(new File("sounds/longBeep.wav").toURI().toString());

    MediaPlayer shortBeepPlayer = new MediaPlayer(shortBeep);

    MediaPlayer longBeepPlayer = new MediaPlayer(longBeep);

    File words = new File("words/words.txt");

    Scanner scanner;
    {
        try {
            scanner = new Scanner(words);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void fillLetterList() {
        for(char i = 'A'; i <= 'Z'; i++) {
            letterList.add(String.valueOf(i));
        }
    }
    void fillWordList() {
        while(scanner.hasNextLine()) {
            wordList.add(scanner.nextLine());
        }
    }
    String translationLetterMorse() {
        String letter;
        for(int i = 0; i < wordAnswer.length(); i++) {
            letter = Character.toString(wordAnswer.charAt(i));
            switch(letter) {
                case "A":
                    buildMorseCode.append("._");
                    break;
                case "B":
                    buildMorseCode.append("_...");
                    break;
                case "C":
                    buildMorseCode.append("_._.");
                    break;
                case "D":
                    buildMorseCode.append("_..");
                    break;
                case "E":
                    buildMorseCode.append(".");
                    break;
                case "F":
                    buildMorseCode.append(".._.");
                    break;
                case "G":
                    buildMorseCode.append("__.");
                    break;
                case "H":
                    buildMorseCode.append("....");
                    break;
                case "I":
                    buildMorseCode.append("..");
                    break;
                case "J":
                    buildMorseCode.append(".___");
                    break;
                case "K":
                    buildMorseCode.append("_._");
                    break;
                case "L":
                    buildMorseCode.append("._..");
                    break;
                case "M":
                    buildMorseCode.append("__");
                    break;
                case "N":
                    buildMorseCode.append("_.");
                    break;
                case "O":
                    buildMorseCode.append("___");
                    break;
                case "P":
                    buildMorseCode.append(".__.");
                    break;
                case "Q":
                    buildMorseCode.append("__._");
                    break;
                case "R":
                    buildMorseCode.append("._.");
                    break;
                case "S":
                    buildMorseCode.append("...");
                    break;
                case "T":
                    buildMorseCode.append("_");
                    break;
                case "U":
                    buildMorseCode.append(".._");
                    break;
                case "V":
                    buildMorseCode.append("..._");
                    break;
                case "W":
                    buildMorseCode.append(".__");
                    break;
                case "X":
                    buildMorseCode.append("_.._");
                    break;
                case "Y":
                    buildMorseCode.append("_.__");
                    break;
                case "Z":
                    buildMorseCode.append("__..");
                    break;
            }
            buildMorseCode.append("/");
        }
        buildMorseCode.append("-");
        return buildMorseCode.toString();
    }

    void flashMorseLight(String morseSequence) {
        if(sequenceIndex < morseSequence.length()) {
            int offKeyframeValue = 0;
            morseLightOnTimeline.getKeyFrames().clear();
            morseLightOffTimeline.getKeyFrames().clear();
            String morseBit = String.valueOf(morseSequence.charAt(sequenceIndex));
            switch(morseBit) {
                case ".":
                    morseLightOnKeyframe = new KeyFrame(Duration.millis(200));
                    offKeyframeValue += 200;
                    morseLight.setStyle("-fx-background-color: #A4A400");
                    shortBeepPlayer.play();
                    break;
                case "_":
                    morseLightOnKeyframe = new KeyFrame(Duration.millis(600));
                    offKeyframeValue += 200;
                    morseLight.setStyle("-fx-background-color: #A4A400");
                    longBeepPlayer.play();
                    break;
                case "/":
                    offKeyframeValue += 400;
                    break;
                case "-":
                    offKeyframeValue += 800;
                    break;
            }
            sequenceIndex++;
            morseLightOffKeyframe = new KeyFrame(Duration.millis(offKeyframeValue));
            morseLightOnTimeline.getKeyFrames().add(morseLightOnKeyframe);
            morseLightOffTimeline.getKeyFrames().add(morseLightOffKeyframe);
            morseLightOnTimeline.play();
        }
    }

    boolean checkAnswer() {
        if(wordInput.getText() != null) {
            wordGuess = wordInput.getText();
            if(wordAnswer.length() != wordGuess.length()) {
                return false;
            }
            else {
                for(int i = 0; i < wordAnswer.length(); i++) {
                    if(!Character.toString(wordAnswer.charAt(i)).equals(Character.toString(wordGuess.charAt(i)))) {
                        return false;
                    }
                }
            }
            return true;
        }
        else {
            return  false;
        }
    }
}
