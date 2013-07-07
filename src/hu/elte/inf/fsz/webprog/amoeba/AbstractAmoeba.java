package hu.elte.inf.fsz.webprog.amoeba;

import java.awt.Point;

/**
 * Absztrakt amőba bázisosztály. A különböző implementációk közös része. Az
 * egyes implementációk a játéktábla reprezentációjában különbözhetnek (pl.
 * vektoros, mátrixos vagy egyéb ábrázolás).
 * 
 * @author jupi
 * 
 */
public abstract class AbstractAmoeba implements Amoeba {

    /**
     * A tábla szélessége.
     */
    private int width;

    /**
     * A tábla magassága.
     */
    private int height;

    /**
     * Igaz, ha vége a játéknak, azaz egyik fél sem léphet többet.
     */
    private boolean gameOver;

    /**
     * A lépések száma.
     */
    private int moveCount;

    /**
     * Konstruktor. Új játékot kezdünk a paraméterek szerinti táblamérettel.
     * 
     * @param width
     *            A tábla szélessége.
     * @param height
     *            A tábla magassága.
     */
    public AbstractAmoeba(final int width, final int height) {
        if (width < 5 || height < 5 || width > 20 || height > 20) {
            throw new IllegalArgumentException(
                    "A lekisebb táblaméret 5x5-ös, a legnagyobb 20x20-as. "
                            + "Mind szélességnek, mind magasságnak 2 és 20 közötti "
                            + "számot adjon meg.");
        }
        newGame(width, height);
    }

    @Override
    public Point boardSize() {
        return new Point(width, height);
    }

    /**
     * Annak ellenőrzése, hogy betelt-e a tábla.<br>
     * Ez akkor következik be, ha a tábla (szélessége * magassága)-dik lépés
     * után vagyunk.
     * 
     * @return Igaz, ha a tábla betelt.
     */
    private boolean checkBoardFull() {
        return moveCount == width * height;
    }

    /**
     * Annak ellenőrzése, hogy O megnyerte-e a játékot.
     * 
     * @return Igaz, ha O nyert.
     */
    protected abstract boolean checkWinnerO();

    /**
     * Annak ellenőrzése, hogy X megnyerte-e a játékot.
     * 
     * @return Igaz, ha X nyert.
     */
    protected abstract boolean checkWinnerX();

    /**
     * Megmutatja, hogy a tábla adott mezője üres-e.
     * 
     * @param x
     *            A tábla X koordinátája, 1-től, balról jobbra nő.
     * @param y
     *            A tábla Y koordinátája, 1-től, fentről lefele nő.
     * @return Igaz, ha szabad.
     */
    protected abstract boolean free(int x, int y);

    protected int getHeight() {
        return height;
    }

    protected int getWidth() {
        return width;
    }

    @Override
    public void newGame(final int width, final int height) {
        moveCount = 1;
        gameOver = false;
        this.width = width;
        this.height = height;
        resetBoard();
    }

    /**
     * O elhelyezése a tábla (x,y) koordinátájú pontjában.
     * 
     * @param x
     *            A tábla X koordinátája, 1-től, balról jobbra nő.
     * @param y
     *            A tábla Y koordinátája, 1-től, fentről lefele nő.
     */
    protected abstract void putO(int x, int y);

    @Override
    public MoveResult putSign(final int x, final int y) {
        MoveResult result = null;
        if (x < 1 || y < 1 || x > width || y > height) {
            result = MoveResult.WRONG_MOVE;
        } else if (gameOver) {
            result = MoveResult.WRONG_MOVE;
        } else if (!free(x, y)) {
            result = MoveResult.WRONG_MOVE;
        } else if (xTurns()) {
            putX(x, y);
            ++moveCount;
            if (checkWinnerX()) {
                gameOver = true;
                result = MoveResult.WINNER_X;
            }
        } else {
            putO(x, y);
            ++moveCount;
            if (checkWinnerO()) {
                gameOver = true;
                result = MoveResult.WINNER_O;
            }
        }
        // Ha éppen akkor telt be a tábla, amikor valaki nyert, akkor megmarad a
        // nyerés, mint viszsatérési érték.
        if (result == null) {
            if (checkBoardFull()) {
                gameOver = true;
                result = MoveResult.BOARD_FULL;
            }
        }
        return result;
    }

    /**
     * X elhelyezése a tábla (x,y) koordinátájú pontjában.
     * 
     * @param x
     *            A tábla X koordinátája, 1-től, balról jobbra nő.
     * @param y
     *            A tábla Y koordinátája, 1-től, fentről lefele nő.
     */
    protected abstract void putX(int x, int y);

    /**
     * A tábla újralétrehozása a {@link #width} és a {@link #height} mezőkben
     * lévő értékek szerint.
     */
    protected abstract void resetBoard();

    /**
     * Igaz, ha X következik.
     */
    private boolean xTurns() {
        return moveCount % 2 == 1;
    }
}
