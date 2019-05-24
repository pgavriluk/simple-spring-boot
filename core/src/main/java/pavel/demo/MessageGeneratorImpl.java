package pavel.demo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MessageGeneratorImpl implements MessageGenerator {

    // constants
    public static final String MAIN_MESSAGE_KEY = "game.main.message";
    public static final String RESULT_WON_MESSAGE_KEY = "game.result.won.message";
    public static final String RESULT_LOST_MESSAGE_KEY = "game.result.lost.message";
    public static final String RESULT_INVALID_NUMBER_MESSAGE_KEY = "game.result.invalid.number.message";
    public static final String RESULT_FIRST_GUESS_MESSAGE_KEY = "game.result.first.guess.message";
    public static final String RESULT_DIRECTION_MESSAGE_KEY = "game.result.direction.message";
    public static final String DIRECTION_LOWER_KEY = "game.direction.lower";
    public static final String DIRECTION_HIGHER_KEY = "game.direction.higher";

    // fields
    private final Game game;
    private final MessageSource messageSource;

    // constructors
    public MessageGeneratorImpl(Game game, MessageSource messageSource) {
        this.game = game;
        this.messageSource = messageSource;
    }

    // init
    @PostConstruct
    public void init() {
        log.info("game = {}", game);
    }

    // public methods
    @Override
    public String getMainMessage() {
        return getMessage(MAIN_MESSAGE_KEY, game.getSmallest(), game.getBiggest());
    }

    @Override
    public String getResultMessage() {

        if(game.isGameWon()) {
            return getMessage(RESULT_WON_MESSAGE_KEY, game.getNumber());
        } else if(game.isGameLost()) {
            return getMessage(RESULT_LOST_MESSAGE_KEY, game.getNumber());
        } else if(!game.isValidNumberRange()) {
            return getMessage(RESULT_INVALID_NUMBER_MESSAGE_KEY);
        } else if(game.getRemainingGuesses() == game.getGuessCount()) {
            return getMessage(RESULT_FIRST_GUESS_MESSAGE_KEY);
        } else {
            String direction = getMessage(DIRECTION_LOWER_KEY);

            if(game.getGuess() < game.getNumber()) {
                direction = getMessage(DIRECTION_HIGHER_KEY);
            }

            return getMessage(RESULT_DIRECTION_MESSAGE_KEY, direction, game.getRemainingGuesses());
        }
    }

    // private methods
    private String getMessage(String code, Object... args){
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
