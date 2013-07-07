/**
 * 
 */
package hu.elte.inf.fsz.webprog.amoeba;

/**
 * Az amőba játék jeleit (az üres mezőt is beleértve) tartalmazó felsorolás
 * típus.
 * 
 * @author jupi
 * 
 */
public enum AmoebaSign {

    /**
     * X jel.
     */
    X('x'),

    /**
     * O jel.
     */
    O('o'),

    /**
     * Üres mező.
     */
    EMPTY('.');

    /**
     * Az amőba egy jelének char reprezentációja.
     */
    private char sign;

    /**
     * Konstruktor a karakter jelölés beállítására.
     * 
     * @param sign
     *            Az amőba egy jelének char reprezentációja.
     */
    private AmoebaSign(final char sign) {
        this.sign = sign;
    }

    /**
     * Visszaadja a jel karater értékét.
     * 
     * @return Az amőba egy jelének char reprezentációja.
     */
    public char charValue() {
        return sign;
    }

    @Override
    public String toString() {
        return String.valueOf(sign);
    }
}
