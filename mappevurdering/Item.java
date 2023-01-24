/**
 * Representerer en vare.
 *
 * @author 10082
 */

public class Item
{
    private Verifier verifier = new Verifier();
    private final String itemNumber;
    private String description;
    private final String brand;
    private final String color;
    private final Category category;
    private final double weight;
    private final double length;
    private final double height;
    private double discount;
    private int price;
    private int discountedPrice;
    private int quantity;
    private boolean hasDiscount;


    /**
     * Lager et vareobjekt.
     *
     *  @param itemNumber - Varenummeret til varen
     *  @param description - Beskrivelsen på varen
     *  @param brand - Produsenten til varen
     *  @param color - Fargen til varen
     *  @param weight - Vekta til varen i kilogram
     *  @param length - Lengden til varen
     *  @param height - Høyden til varen
     *  @param price - Prisen til varen
     *  @param quantity - Antall varer av denne typen på lager
     *  @param categoryNumber - Brukes til å bestemme type vare v.h.a Category enumeratoren
     */
    public Item(String itemNumber, String description, String brand, String color,
                double weight, double length, double height,
                int price, int quantity, int categoryNumber)
    {
        this.itemNumber = verifier.verifyStringVariable(itemNumber, "Varenummeret");
        this.description = verifier.verifyStringVariable(description, "Beskrivelsen");
        this.brand = verifier.verifyStringVariable(brand, "Merket/produsenten");
        this.color = verifier.verifyStringVariable(color, "Fargen");
        this.weight = verifier.verifyDoubleVariable(weight, "Vekten");
        this.length = verifier.verifyDoubleVariable(length, "Lengden");
        this.height = verifier.verifyDoubleVariable(height, "Høyden");
        this.price = verifier.verifyIntVariable(price, "Prisen");
        this.quantity = verifier.verifyIntVariable(quantity, "Beholdningen");
        this.category = Category.values()[categoryNumber];
    }


    /**
     * Kopikonstruktør. Denne konstruktøren brukes for å lage et objekt med like verdier,
     * men med en ny referanse.
     *
     * @param source (Item)
     */
    public Item(Item source)
    {
        this.itemNumber = source.itemNumber;
        this.description = source.description;
        this.brand = source.brand;
        this.color = source.color;
        this.weight = source.weight;
        this.length = source.length;
        this.height = source.height;
        this.price = source.price;
        this.quantity = source.quantity;
        this.category = source.category;
    }


    /**
     * Denne vil informere brukeren om en vare har rabbatert pris. Vil kun utløses når hasDiscount
     * er true.
     *
     * @return Informasjon om prisen til varen - (String)
     */
    public String informOfDiscount()
    {
        return (hasDiscount) ? "(Rabattert)" : "";
    }


    /**
     * Henter varenummeret til varen.
     *
     * @return Varenummer - (String)
     */
    public String getItemNumber()
    {
        return itemNumber;
    }


    /**
     * Henter beskrivelsen av varen.
     *
     * @return Beskrivelse - (String)
     */
    public String getDescription()
    {
        return description;
    }


    /**
     * Henter merket på varen.
     *
     * @return Varemerke - (String)
     */
    public String getBrand()
    {
        return brand;
    }


    /**
     * Henter fargen på varen.
     *
     * @return Farge - (String)
     */
    public String getColor()
    {
        return color;
    }


    /**
     * Henter kategorien på varen.
     *
     * @return Varekategori - (String)
     */
    public Category getCategory()
    {
        return category;
    }


    /**
     * Henter vekta på varen i kilogram.
     *
     * @return Vekt i kg - (double)
     */
    public double getWeight()
    {
        return weight;
    }


    /**
     * Henter lengden på varen i meter.
     *
     * @return Lengde - (double)
     */
    public double getLength()
    {
        return length;
    }


    /**
     * Henter høyden på varen i meter.
     *
     * @return Høyde - (double)
     */
    public double getHeight()
    {
        return height;
    }


    /**
     * Henter avslaget på varen i prosentfaktor, og returnerer dette.
     *
     * @return Avslaget i prosentfaktor - (double)
     */
    public double getDiscount()
    {
        return discount;
    }


    /**
     * Henter den rabatterte prisen på varen og returnerer denne.
     *
     * @return Rabattert pris - (int)
     */
    public int getDiscountedPrice()
    {
        return discountedPrice;
    }


    /**
     * Henter prisen på varen.
     *
     * @return Pris - (int)
     */
    public int getPrice()
    {
        return (hasDiscount) ? discountedPrice : price;
    }


    /**
     * Henter beholdningen på varen.
     *
     * @return Beholdning - (int)
     */
    public int getQuantity()
    {
        return quantity;
    }


    /**
     * Henter tilstanden til hasDiscount og returnerer den.
     *
     * @return Sant / usant avhengig av om varen er på rabatt eller ikke - (boolean)
     */
    public boolean isHasDiscount()
    {
        return hasDiscount;
    }


    /**
     * Setter beholdningen til verdien gitt som argument.
     *
     * @param quantity Beholdning - (int)
     */
    public void setQuantity(int quantity)
    {
        this.quantity = verifier.verifyIntVariable(quantity, "Beholdningen");
    }


    /**
     * Setter prisen til verdien gitt som argument.
     *
     * @param price Ny pris - (int)
     */
    public void setPrice(int price)
    {
        this.price = verifier.verifyIntVariable(price, "Prisen");
    }


    /**
     * Setter beskrivelsen av varen til verdien gitt som argument.
     *
     * @param newDescription Ny beskrivelse - (String)
     */
    public void setDescription(String newDescription)
    {
        this.description = verifier.verifyStringVariable(newDescription, "Varebeskrivelsen");
    }


    /**
     * Setter rabatten til varen til verdien gitt som argument.
     *
     * @param newDiscount Rabatt den skal settes til - (double)
     */
    public void setDiscount(double newDiscount)
    {
        if (newDiscount < 0 || newDiscount > 1)
        {
            throw new IllegalArgumentException("Rabatten kan kun settes mellom 0 og 100%.");
        }
        this.discount = newDiscount; // setter ny rabatt
        this.discountedPrice = (int) (this.getPrice() * discount); // regner ut rabattert pris
        this.hasDiscount = this.discount != 1; // settes til true/false ettersom varen er rabattert
    }


    /**
     * ToString - Lager en ferdig formattert tekst som printer ut alle
     * objektvariablene til et objekt på en bestemt måte.
     *
     * @return (String)
     */
    @Override
    public String toString()
    {
        return "Varenummer: " + this.getItemNumber() + "\n"
               + "Beskrivelse: " + this.getDescription() + "\n"
               + "Produsent: " + this.getBrand() + "\n"
               + "Kategori: " + this.getCategory() + "\n"
               + "Farge: " + this.getColor() + "\n"
               + "Lengde: " + this.getLength() + " meter\n"
               + "Høyde: " + this.getHeight() + " meter\n"
               + "Vekt: " + this.getWeight() + " kilogram\n"
               + "Pris: " + this.getPrice() + " nok " + informOfDiscount() + "\n"
               + "Beholdning: " + this.getQuantity() + " stk\n";
    }
}
