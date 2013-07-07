/**
 * 
 */
package hu.elte.inf.fsz.webprog.amoeba;

/**
 * Az {@link Amoeba} implementációja egy vektorban tárolt táblával.
 * 
 * @author jupi
 * 
 */
public class VectorBoardAmoeba extends AbstractAmoeba {

    private static final int SIGNS_TO_WIN = 5;
    /**
     * A játéktábla tömb reprezentációja.<br>
     * A tömb sorfolytonosan tartalmazza a játéktábla mezőit.
     */
    private AmoebaSign[] board;

    /**
     * Az ősosztály konstruktora (továbbhívva).
     * 
     * @param width
     *            A tábla szélessége.
     * @param height
     *            A tábla magassága.
     */
    public VectorBoardAmoeba(final int width, final int height) {
        super(width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see hu.elte.inf.fsz.webprog.amoeba.Amoeba#boardAsString()
     */
    @Override
    public String boardAsString() {
        final StringBuilder sb = new StringBuilder((getWidth() + 1)
                * getHeight());
        for (int i = 0; i < board.length; ++i) {
            if (i % getWidth() == 0) {
                sb.append('\n');
            }
            sb.append(board[i].charValue());
        }
        return sb.toString();
    }

    /**
     * Ellenőrzi, hogy adott játékosnak kijött-e {@value #SIGNS_TO_WIN} számú
     * jele backslash irányban.
     * 
     * @param sign
     *            Egyik játékos jele (X vagy O).
     * @return Igaz, ha nyert.
     */
    private boolean checkBackSlash(final AmoebaSign sign) {
        return checkWinner(sign, getWidth() + 1);
    }

    /**
     * Ellenőrzi, hogy adott játékosnak kijött-e {@value #SIGNS_TO_WIN} számú
     * jele vízszintes irányban.
     * 
     * @param sign
     *            Egyik játékos jele (X vagy O).
     * @return Igaz, ha nyert.
     */
    private boolean checkHorizontal(final AmoebaSign sign) {
        return checkWinner(sign, 1);
    }

    /**
     * Ellenőrzi, hogy adott játékosnak kijött-e {@value #SIGNS_TO_WIN} számú
     * jele perjel irányban.
     * 
     * @param sign
     *            Egyik játékos jele (X vagy O).
     * @return Igaz, ha nyert.
     */
    private boolean checkSlash(final AmoebaSign sign) {
        return checkWinner(sign, getWidth() - 1);
    }

    /**
     * Ellenőrzi, hogy adott játékosnak kijött-e {@value #SIGNS_TO_WIN} számú
     * jele függőleges irányban.
     * 
     * @param sign
     *            Egyik játékos jele (X vagy O).
     * @return Igaz, ha nyert.
     */
    private boolean checkVertical(final AmoebaSign sign) {
        return checkWinner(sign, getWidth());
    }

    /**
     * Ellenőrzi, hogy adott jellel rendelkező játékos nyert-e.
     * 
     * @param sign
     *            Egyik játékos jele (X vagy O).
     * @return Igaz, ha nyert.
     */
    private boolean checkWinner(final AmoebaSign sign) {
        boolean result = checkHorizontal(sign);
        if (!result) {
            result = checkVertical(sign);
        }
        if (!result) {
            result = checkBackSlash(sign);
        }
        if (!result) {
            result = checkSlash(sign);
        }
        return result;
    }

    /**
     * Ellenőrzi, hogy adott játékosnak kijött-e {@value #SIGNS_TO_WIN} számú
     * jele a tábla vektorában, megadott lépésközzel.
     * 
     * @param sign
     *            Egyik játékos jele (X vagy O).
     * @param step
     *            Lépésköz a következő jelig, amit megvizsgálunk a tömbben, hogy
     *            azonos-e a megadot jellel.
     * @return Igaz, ha nyert.
     */
    private boolean checkWinner(final AmoebaSign sign, final int step) {
        if (sign == null || AmoebaSign.EMPTY.equals(sign) || step < 1) {
            throw new IllegalArgumentException();
        }
        int found = 0;
        boolean result = false;
        int i = 0;
        while (!result && i < board.length) {
            if (board[i].equals(sign)) {
                ++found;
            } else {
                found = 0;
            }
            result = found == SIGNS_TO_WIN;
            i = i + step;
        }
        return result;
    }

    @Override
    protected boolean checkWinnerO() {
        return checkWinner(AmoebaSign.O);
    }

    @Override
    protected boolean checkWinnerX() {
        return checkWinner(AmoebaSign.X);
    }

    /**
     * Tábla-koordinátákból a táblát ábrázoló vektor indexét számolja ki.
     * 
     * @param x
     *            X koordináta, 1-től kezdődően, balról jobbra nő.
     * @param y
     *            Y koordináta, 1-től kezdődően, fentről le nő.
     * @return A táblát ábrázoló vektor egy indexe.
     */
    private int coordinatesToIndex(final int x, final int y) {
        if (x < 1 || y < 1 || x > getWidth() || y > getHeight()) {
            throw new IllegalArgumentException();
        }
        return (y - 1) * getWidth() + x - 1;
    }

    @Override
    protected boolean free(final int x, final int y) {
        return AmoebaSign.EMPTY.equals(board[coordinatesToIndex(x, y)]);
    }

    @Override
    protected void putO(final int x, final int y) {
        board[coordinatesToIndex(x, y)] = AmoebaSign.O;
    }

    @Override
    protected void putX(final int x, final int y) {
        board[coordinatesToIndex(x, y)] = AmoebaSign.X;
    }

    @Override
    protected void resetBoard() {
        board = new AmoebaSign[getWidth() * getHeight()];
        for (int i = 0; i < board.length; ++i) {
            board[i] = AmoebaSign.EMPTY;
        }
    }
}
