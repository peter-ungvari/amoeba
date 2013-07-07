package hu.elte.inf.fsz.webprog.amoeba;

import java.awt.Point;

/**
 * Az amőba játék szolgáltatásait leíró interfész.
 * 
 * @author jupi
 * 
 */
public interface Amoeba {

    /**
     * A tábla tartalmának szöveges reprezentációja.
     * 
     * @return A tábla, mint {@link String}.
     */
    String boardAsString();

    /**
     * A tábla mérete. A {@link Point} X koordinátája a szélességet, a Y
     * kooridnátája magasságot tartalmazza.
     * 
     * @return Táblaméret, mint {@link Point}.
     */
    Point boardSize();

    /**
     * Új játék. <br>
     * A táblát iniciaálizálja a megadott szélességgel és magassággal és
     * beállítja, hogy X játékos következik.
     * 
     * @param width
     *            A tábla szélessége.
     * @param height
     *            A tábla magassága.
     */
    void newGame(int width, int height);

    /**
     * Lépés az amőba szabályai szerint. <br>
     * Az soron következő játékos a tábla (x,y) pozícióját jelöli meg. A
     * számozás a tábla bal felső sarkában kezdődik (1,1)-gyel.
     * 
     * @param x
     *            X koordináta, 1-től kezdődően, balról jobbra nő.
     * @param y
     *            Y koordináta, 1-től kezdődően, fentről le nő.
     * @return A lépés eredménye.
     */
    MoveResult putSign(int x, int y);
}
