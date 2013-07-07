/**
 * 
 */
package hu.elte.inf.fsz.webprog.amoeba;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Parancssoros amőba alkalmazás, amivel a standard inputon keresztül
 * játszhatunk vagy egy fájl alapján játszik a program.
 * 
 * @author jupi
 * 
 */
public class LocalAmoeba {

    /**
     * {@link Amoeba}.
     */
    private final Amoeba amoeba;

    /**
     * Bemeneti adatfolyam a {@link #processInput()}-hoz.
     */
    private final InputStream is;

    /**
     * Használati útmutató.
     */
    private static final String USAGE = "Használata: \n\t"
            + LocalAmoeba.class.getSimpleName()
            + " <tábla_szélesség> <tábla_magasság> [fájlnév]";

    /**
     * Prancssori argumentumok feldolgozása, az osztály példányosítása, majd
     * beérkező adatok feldolgozása.
     * 
     * @param args
     *            Argumentumok tömbje.
     */
    public static void main(final String[] args) {
        final LocalAmoeba localAmoeba;
        if (args.length == 2) {
            try {
                final int width = Integer.parseInt(args[0]);
                final int height = Integer.parseInt(args[1]);
                localAmoeba = new LocalAmoeba(width, height,
                        System.in);
                localAmoeba.processInput();
            } catch (final NumberFormatException e) {
                System.out.println(USAGE);
            }
        } else if (args.length == 3) {
            InputStream is = null;
            try {
                final int width = Integer.parseInt(args[0]);
                final int height = Integer.parseInt(args[1]);
                final File inputFile = new File(args[2]);
                if (inputFile.canRead()) {
                    is = new FileInputStream(inputFile);
                    localAmoeba = new LocalAmoeba(width,
                            height, is);
                    localAmoeba.processInput();
                }
            } catch (final NumberFormatException e) {
                System.out.println(USAGE);
            } catch (final IOException e) {
                System.out.println(USAGE);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (final IOException e) {
                    // Szépen lenyeljük (very bad practice :)
                }
            }
        } else {
            System.out.println(USAGE);
        }
    }

    /**
     * Konstruktor. Feladata példányosítani egy {@link Amoeba} implementációt.
     * Ezen kívül beállítja a bemenő adatfolyamot.
     * 
     * @param width
     *            Az amőba tábla szélessége.
     * @param height
     *            Az amőba tábla magassága.
     * @param is
     *            Bemenő sorok adatfolyama.
     */
    public LocalAmoeba(final int width, final int height, final InputStream is) {
        amoeba = new VectorBoardAmoeba(width, height);
        this.is = is;
    }

    /**
     * A bemeneti sorok feldolgozása.
     */
    private void processInput() {
        final Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine().trim();
            if ("kiir".equals(line)) {
                System.out.println(amoeba.boardAsString());
            } else {
                final String[] tokens = line.split(" ");
                if (tokens.length == 3) {
                    if ("lep".equals(tokens[0])) {
                        try {
                            final int x = Integer.parseInt(tokens[1]);
                            final int y = Integer.parseInt(tokens[2]);
                            final MoveResult moveResult = amoeba.putSign(x, y);
                            if (moveResult != null) {
                                switch (moveResult) {
                                case WRONG_MOVE:
                                    System.out.println("Hibás lépés.");
                                    break;
                                case BOARD_FULL:
                                    System.out.println("A tábla megtelt.");
                                    break;
                                case WINNER_X:
                                    System.out.println("X nyert.");
                                    break;
                                case WINNER_O:
                                    System.out.println("O nyert.");
                                    break;
                                }
                            }
                        } catch (final NumberFormatException e) {
                            // Ilyekor csak megyünk szépen tovább...
                        }
                    }
                }
            }
        }
        scanner.close();
    }

}
