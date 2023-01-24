import java.util.ArrayList;

/**
 * Et register for å behandle vareobjekter.
 *
 * @author 10082
 */
public class ItemRegister
{
    private ArrayList<Item> items;


    /**
     * Konstruktør for ItemRegister-klassen. Denne brukes til å lage et ItemRegister-objekt.
     */
    public ItemRegister()
    {
        this.items = new ArrayList<Item>();
    }


    /**
     * Brukes til å registrere en ny vare i vareregisteret. Varen som blir lagt til i registeret
     * blir sendt inn som argument når brukeren bruker metoden. Metoden bruker checkItemNumber for
     * å sjekke om registeret allerede inneholder en vare med samme varenummer. Hvis det er
     * tilfellet blir ikke varen lagt til i registeret.
     *
     * @param item Vareobjekt - (Item)
     */
    public void registerItem(Item item)
    {
        if (!checkItemNumber(item.getItemNumber())) // hvis ikke en vare med samme varenr.
        {
            this.items.add(new Item(item));
        }
    }


    /**
     * Brukes til å fjerne en vare fra registeret ved å bruke varenummeret. Dersom registeret
     * er tomt, skal det bli "kastet" en IllegalStateException som forklarer for brukeren hva
     * som er galt. Hvis vareregisteret inneholder en vare med et varenummer som er likt det
     * som blir sendt inn som argument, vil varen bli fjernet fra registeret.
     *
     * @param itemNumber Varenummeret på varen som skal slettes - (String)
     */
    public void removeItem(String itemNumber)
    {
        if (getSize() == 0)
        {
            throw new IllegalStateException("Kan ikke fjerne varer når registeret er tomt.");
        }
        this.items.removeIf(item -> item.getItemNumber().equals(itemNumber));
    }


    /**
     * Endrer beskrivelsen til varen med det varenummeret som blir gitt som argument. Den nye
     * beskrivelsen blir også sendt inn som argument.
     *
     * @param itemNumber Varenummeret til den varen som skal oppdateres - (String)
     * @param newDescription Ny beskrivelse av varen                    - (String)
     */
    public void changeDescription(String itemNumber, String newDescription)
    {
        getItem(itemNumber).setDescription(newDescription);
    }


    /**
     * Bruker varenummeret som blir sendt inn som argument for å sjekke om en vare finnes i
     * vareregisteret. Dersom det er tilfellet returnerer metoden true. Hvis det ikke finnes en vare
     * med det gitte varenummeret returneres false.
     *
     * @param itemNumber Varenummeret som skal sjekkes - (String)
     * @return true / false                            - (boolean)
     */
    public boolean checkItemNumber(String itemNumber)
    {
        return this.items.stream().anyMatch(item -> item.getItemNumber().equals(itemNumber));
    }


    /**
     * Endrer prisen til en gitt vare til det brukeren sender inn som argument.
     *
     * @param itemNumber Varenummeret   - (String)
     * @param newPrice Ny pris til vare - (int)
     */
    public void changePrice(String itemNumber, int newPrice)
    {
        this.getItem(itemNumber).setPrice(newPrice);
    }


    /**
     * Bruker varenummeret til å bestemme hvilken vare det er som skal bli rabbatert. Dersom
     * argumentet som bestemmer rabatten er mindre enn eller lik 0, eller større enn 100
     * vil det bli sendt en IllegalArgumentException som forteller brukeren at det ikke er mulig.
     * Hvis argumentene er er godkjente vil rabatten bli lagt til, og prisen endret.
     *
     * @param itemNumber Varenummeret   - (int)
     * @param discount Avslag i prosent - (double)
     */
    public void applyDiscount(String itemNumber, double discount)
    {
        if (discount < 0 || discount > 100)
        {
            throw new IllegalArgumentException("Rabatten må være et tall fra og med 0 til 100.");
        }
        this.getItem(itemNumber).setDiscount(calculateDiscount(discount));
    }


    /**
     * Regner om prosent til prosentfaktor.
     *
     * @param discount Prosent - (double)
     * @return Prosentfaktor   - (double)
     */
    private double calculateDiscount(double discount)
    {
        return (100 - discount) / 100;
    }


    /**
     * Fjerner avslaget til en vare ved å sette prosentfaktoren til 1 (100% av ordinær pris).
     *
     * @param itemNumber Varenummeret til varen som skal få fjernet rabatten - (String)
     */
    public void removeDiscount(String itemNumber)
    {
        this.getItem(itemNumber).setDiscount(1);
    }


    /**
     * Endrer beholdningen til en vare ved å ta inn to argumenter. Det ene viser til hvilken vare
     * det skal bli gjort endringer på, og det andre argumentet viser hvor mye beholdningen til
     * varen skal øke.
     *
     * @param itemNumber Varenummeret brukes til å finne varen - (String)
     * @param newQuantity Hvor mye beholdningen skal endres    - (int)
     */
    public void increaseQuantity(String itemNumber, int newQuantity)
    {
        getItem(itemNumber).setQuantity(getQuantity(itemNumber) + newQuantity);
    }


    /**
     * Endrer beholdningen til en vare ved å ta inn to argumenter. Det ene viser til hvilken vare
     * det skal bli gjort endringer på, og det andre argumentet viser hvor mye beholdningen til
     * varen skal bli redusert.
     *
     * @param itemNumber Varenummeret til varen              - (String)
     * @param removeQuantity Hvor mye beholdningen reduseres - (int)
     */
    public void reduceQuantity(String itemNumber, int removeQuantity)
    {
        getItem(itemNumber).setQuantity(getQuantity(itemNumber) - removeQuantity);
    }


    /**
     * Søker etter en vare med samme varenummer som blir sendt inn som argument og returnerer varen
     * dersom den finner den.
     *
     * @param itemNumber Varenummer  - (String)
     * @return Vareobjekt eller null - (Item)
     */
    public Item getItem(String itemNumber)
    {
        return this.items.stream()
            .filter(item -> item.getItemNumber().equals(itemNumber))
            .findFirst()
            .orElse(null);
    }


    /**
     * Bruker varenummeret som argument for å returnere beholdningen dens.
     *
     * @param itemNumber Varenummeret til varen som skal sjekkes - (String)
     * @return Beholdning                                        - (int)
     */
    public int getQuantity(String itemNumber)
    {
        return getItem(itemNumber).getQuantity();
    }


    /**
     * Returnerer en dypkopi av registeret.
     *
     * @return Dypkopi av registeret - (ArrayList<> med Item objekter)
     */
    public ArrayList<Item> getItems()
    {
        ArrayList<Item> items = new ArrayList<Item>();

        for (Item item : this.items)
        {
            items.add(new Item(item));
        }
        return items;
    }


    /**
     * Henter ut de varene som har rabattert pris og returnerer de.
     *
     * @return Rabatterte varer i liste - (ArrayList)
     */
    public ArrayList<Item> getDiscountedItems()
    {
        ArrayList<Item> items = new ArrayList<Item>();

        for (Item item : this.items)
        {
            if (item.isHasDiscount())
            {
                items.add(new Item(item));
            }
        }
        return items;
    }


    /**
     * Returnerer størrelsen på registeret.
     *
     * @return størrelse på registeret - (int)
     */
    public int getSize()
    {
        return this.getItems().size();
    }


    /**
     * Formatterer en tekst som inneholder alle varene i registeret og returnerer denne.
     *
     * @return Ferdig formattert tekst - (String)
     */
    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder();
        for (Item item : this.items)
        {
            text.append(item.toString() + "\n");
        }
        return text.toString();
    }
}