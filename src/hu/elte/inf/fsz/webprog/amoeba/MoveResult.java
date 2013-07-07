package hu.elte.inf.fsz.webprog.amoeba;

/**
 * Az amőba játék egy lépésének az eredménye.
 * 
 * @author jupi
 * 
 */
public enum MoveResult {

    /**
     * Az X nyert.
     */
    WINNER_X("X nyert."),

    /**
     * A O nyert.
     */
    WINNER_O("O nyert."),

    /**
     * A tábla megtelt.
     */
    BOARD_FULL("A tábla megtelt."),

    /**
     * Hibás lépés.
     */
    WRONG_MOVE("Hibás lépés."),

    /**
     * Minden O.K.
     */
    OK("O.K.");

    /**
     * Az eredmény szöveges reprezentációja.
     */
    private String message;

    /**
     * Konstruktor a szöveg beállítására.
     * 
     * @param message
     *            A lépési eredmény szöveges reprezentációja.
     */
    private MoveResult(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
