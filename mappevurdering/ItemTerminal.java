import java.util.ArrayList;
import java.util.Scanner;

/**
 * Klient for vareregisteret.
 *
 * @author 10082
 */
public class ItemTerminal
{
    public static ItemRegister ir = new ItemRegister();
    private static Scanner scan = new Scanner(System.in);

    // MAIN MENU
    private static final int SHOW_ITEMS_MENU = 1;
    private static final int UPDATE_ITEMS_MENU = 2;
    private static final int REGISTER_ITEM = 3;
    private static final int REMOVE_ITEM = 4;
    private static final int TURN_OFF_REGISTER = 5;

    // SHOW ITEMS MENU
    private static final int SHOW_ALL_ITEMS = 1;
    private static final int SHOW_ITEM_NUMBERS_AND_DESCRIPTIONS = 2;
    private static final int FIND_ITEM_WITH_ITEM_NUMBER = 3;
    private static final int BACK_TO_MAIN_MENU = 9;

    // UPDATE ITEMS MENU
    private static final int INCREASE_QUANTITY = 1;
    private static final int REDUCE_QUANTITY = 2;
    private static final int APPLY_DISCOUNT = 3;
    private static final int REMOVE_DISCOUNT = 4;
    private static final int UPDATE_PRICE = 5;
    private static final int CHANGE_DESCRIPTION = 6;


    /**
     * Main metoden for klienten. Her skrives koden som skal kjøres i klienten.
     *
     * @param args argumenter - (String[])
     */
    public static void main(String[] args)
    {
        loadItems();
        mainMenu();
        scan.close();
    }


    /**
     * Laster opp noen verdier som blir lagt inn i vareregisteret.
     */
    public static void loadItems()
    {
        Item[] items = {
            new Item("111", "Laminat med mørk og matt finish",
                "Trevareeksperten", "brun", 4, 2.35, 0.01, 499, 0, 0),
            new Item("222", "Vinduer med myk og grønt skinn",
                "Vindueksperten", "Gjennomsiktig/grønt", 20, 1.5, 1.0, 20000, 5, 1),
            new Item("333", "Dør med hvit matt finish",
                "Døreksperten", "Hvit", 35, 0.8, 2.0, 40000, 4, 2),
            new Item("444", "Impregnerte planker av eik",
                "Trevareeksperten", "Grå", 5, 3.0, 0.04, 199, 100, 3),
            new Item("555", "Dør med svart matt finish",
                "Døreksperten", "Svart", 35, 0.8, 2.0, 34999, 8, 2)
        };
        for (Item item : items)
        {
            ir.registerItem(item);
        }
    }

    /*
    ======================================== MENYER ===========================================
     */


    /**
     * Hovedmenyen til programmet.
     */
    public static void mainMenu()
    {
        while (true)
        {
            System.out.print("\n============== HOVEDMENY SMARTHUS AS =============\n");
            System.out.print("1) SØK PÅ VARE/VIS BEHOLDNING\n");
            System.out.print("2) OPPDATER VARER\n");
            System.out.print("3) REGISTRER NY VARE\n");
            System.out.print("4) FJERN VARE\n");
            System.out.print("5) AVSLUTT PROGRAM\n\n");
            System.out.print("VALG (1-5): ");

            int menuChoice = Verifier.validateMenuChoice();

            switch (menuChoice)
            {
                case SHOW_ITEMS_MENU -> showItemsMenu();
                case UPDATE_ITEMS_MENU -> updateItemsMenu();
                case REGISTER_ITEM -> addItemToRegister();
                case REMOVE_ITEM -> removeItemFromRegister();
                case TURN_OFF_REGISTER -> System.exit(0); // avslutter programmet
                // dersom brukeren taster inn tall som ikke blir representert av et valg.
                default -> System.out.print("Forespørsel avvist.");
            }
        }
    }


    /**
     * Delmeny som viser hvilke visningsalternativer for lageret brukeren har.
     */
    public static void showItemsMenu()
    {
        while (ir.getSize() != 0) // kommer ikke inn i menyen hvis registeret er tomt
        {
            System.out.print("\n================== LAGEROVERSIKT =================\n");
            System.out.print("1) SE ALLE VARENE PÅ LAGERET\n");
            System.out.print("2) SE VARENUMMER OG BESKRIVELSE\n");
            System.out.print("3) SØK PÅ VARENUMMER\n");
            System.out.print("9) TILBAKE TIL HOVEDMENY\n\n");
            System.out.print("VALG: ");

            int menuChoice = Verifier.validateMenuChoice();

            switch (menuChoice)
            {
                case SHOW_ALL_ITEMS -> printItemsInRegister();
                case SHOW_ITEM_NUMBERS_AND_DESCRIPTIONS ->
                    printItemNumberAndDescription(ir.getItems());
                case FIND_ITEM_WITH_ITEM_NUMBER -> findItemUsingItemNumber();
                case BACK_TO_MAIN_MENU -> mainMenu();
                // dersom brukeren taster inn tall som ikke blir representert av et valg.
                default -> System.out.print("Forespørsel avvist.");
            }
        }
        System.out.print("\nIngen varer i registeret.\n");
    }


    /**
     * Delmeny som viser hvilke endringer brukeren kan gjøre på varene i registeret.
     */
    public static void updateItemsMenu()
    {
        while (ir.getSize() != 0)
        {
            System.out.print("\n============= ENDRE VERDIER TIL VARE =============\n");
            System.out.print("1) ØK BEHOLDNING TIL VARE\n");
            System.out.print("2) REDUSER BEHOLDNING TIL VARE\n");
            System.out.print("3) LAG RABATT PÅ VARE\n");
            System.out.print("4) FJERN RABATT PÅ VARE\n");
            System.out.print("5) OPPDATER PRIS TIL VARE\n");
            System.out.print("6) ENDRE BESKRIVELSE TIL VARE\n");
            System.out.print("9) TILBAKE TIL HOVEDMENY\n\n");

            int menuChoice = Verifier.validateMenuChoice();

            switch (menuChoice)
            {
                case INCREASE_QUANTITY -> increaseQuantity();
                case REDUCE_QUANTITY -> reduceQuantity();
                case APPLY_DISCOUNT -> applyDiscount();
                case REMOVE_DISCOUNT -> removeDiscount();
                case UPDATE_PRICE -> updatePrice();
                case CHANGE_DESCRIPTION -> changeDescription();
                case BACK_TO_MAIN_MENU -> mainMenu();
                // dersom brukeren taster inn tall som ikke blir representert av et valg.
                default -> System.out.print("Forespørsel avvist.");
            }
        }
        System.out.print("\nIngen varer igjen i registeret å oppdatere verdiene til.\n");
    }


    /*
    ================================== VISNINGSALTERNATIVER =====================================
     */


    /**
     * Printer ut alle varene på lageret.
     */
    public static void printItemsInRegister()
    {
        System.out.print("\n_________________ LAGERBEHOLDNING ________________\n\n");
        System.out.print(ir.toString());
    }


    /**
     * Hjelpemetode for findItemUsingItemNumber-metoden. Denne printer ut
     * varenummeret og beskrivelsen til all varene som er registrert i registeret.
     *
     * @param items Den lista som skal printes - (ArrayList)
     */
    public static void printItemNumberAndDescription(ArrayList<Item> items)
    {
        System.out.println("\n           * Varenummer og beskrivelse *          ");
        for (int i = 0; i < items.size(); i++)
        {
            System.out.print("#" + (i + 1) + ": " + items.get(i).getItemNumber()
                + " - " + items.get(i).getDescription() + "\n");
        }
    }


    /**
     * Søker etter en vare ved å bruke varenummeret. Dersom en vare blir funnet blir den printet ut,
     * hvis ikke blir det printet ut en beskjed om at varen ikke ble funnet.
     */
    public static void findItemUsingItemNumber()
    {
        printItemNumberAndDescription(ir.getItems());
        System.out.print("\nSkriv inn et varenummer for å hente mer informasjon om den varen: ");
        String itemNumber = scan.nextLine();

        if (ir.getItem(itemNumber) != null)
        {
            System.out.print("\n" + ir.getItem(itemNumber).toString());
        } else
        {
            System.out.print("\nFant ingen vare med det varenummeret.\n");
        }
    }


    /*
    ======================================= ENDRINGER =========================================
     */


    /**
     * Øker beholdningen til en vare.
     */
    public static void increaseQuantity()
    {
        System.out.println("\n____________ ØK BEHOLDNINGEN TIL VARE ____________");
        printItemNumberAndDescription(ir.getItems());
        System.out.print("\nTast inn varenummeret til varen du ønsker å øke beholdningen til: ");
        String itemNumber = Verifier.validateStringInput("Varenummeret");
        if (ir.getItem(itemNumber) != null)
        {
            System.out.print("\nHva ønsker du å øke beholdningen med? ");
            int increaseBy = (int) Verifier.validateNumberInput("Økningen");
            System.out.println("\nBeholdning før: " + ir.getItem(itemNumber).getQuantity());
            ir.increaseQuantity(itemNumber, increaseBy);
            System.out.println("Beholdning etter: " + ir.getItem(itemNumber).getQuantity() + "\n");
        } else
        {
            System.out.print("\nFant ingen vare med det varenummeret.\n");
        }
    }


    /**
     * Reduserer beholdningen til en vare.
     */
    public static void reduceQuantity()
    {
        System.out.println("\n__________ REDUSER BEHOLDNINGEN TIL VARE _________");
        printItemNumberAndDescription(ir.getItems());
        System.out.print("\nVarenummeret til varen du ønsker å redusere beholdningen til: ");
        final String itemNumber = Verifier.validateStringInput("Varenummeret");

        if (ir.getItem(itemNumber) == null) // kjøres hvis varen ikke eksisterer
        {
            System.out.print("Fant ingen vare med dette varenummeret.\n");
        } else if (ir.getItem(itemNumber).getQuantity() == 0) // sjekker om beholdningen er 0.
        {
            System.out.print("\nBeholdningen er allerede 0. Umulig å redusere ytterligere.\n");
        } else
        {
            checkInputThenReduce(itemNumber);
        }
    }


    /**
     * Sjekker at det brukeren taster inn er mulig å redusere med. Fungerer som en hjelpemetode
     * for reduceQuantity-metoden rett ovenfor.
     *
     * @param itemNumber Varenummeret til varen som skal redusere beholdningen - (String)
     */
    public static void checkInputThenReduce(String itemNumber)
    {
        int currentQuantity = ir.getItem(itemNumber).getQuantity();
        System.out.print("\nNåværende beholdning: " + currentQuantity);
        System.out.print("\nHva ønsker du å redusere den med? ");

        int reduceBy = Verifier.validateReductionInput(itemNumber);
        ir.reduceQuantity(itemNumber, reduceBy);
        System.out.println("Ny beholdning: " + ir.getItem(itemNumber).getQuantity());
    }


    /**
     * Legger til en rabatt. Dersom verdien brukeren sender inn er over 100 eller under 0, vil den
     * bli spurt om å prøve på nytt.
     */
    public static void applyDiscount()
    {
        System.out.println("\n_________________ ENDRE RABATT _________________");
        printItemNumberAndDescription(ir.getItems());
        System.out.print("\nTast inn varenummeret til den varen du ønsker å endre rabatten til: ");
        String itemNumber = Verifier.validateStringInput("Varenummeret");
        if (ir.getItem(itemNumber) != null)
        {
            System.out.print("\nNåværende pris: " + ir.getItem(itemNumber).getPrice() + " nok\n");
            System.out.print("Hva skal rabatten settes til? (%) ");
            ir.applyDiscount(itemNumber, Verifier.validateDiscount());
            System.out.print("Ny pris: " + ir.getItem(itemNumber).getPrice() + " nok\n");
        } else
        {
            System.out.print("\nFant ingen vare med det varenummeret.\n");
        }
    }


    /**
     * Fjerner avslaget på en vare. Sjekker først om det finnes rabbaterte varer i registeret
     * som det er mulig å fjerne. Hvis ikke vil funksjonen sende beskjed om at det ikke er
     * rabatterte varer i registeret.
     */
    public static void removeDiscount()
    {
        ArrayList<Item> discountedItems = ir.getDiscountedItems();
        if (discountedItems.size() != 0)
        {
            System.out.print("\n_________________ FJERN RABATT _________________\n");
            printItemNumberAndDescription(discountedItems);
            System.out.print("Tast inn varenummeret til varen du ønsker å fjerne rabatten til: ");
            String itemNumber = Verifier.verifyItemNumber("doesnt exist");
            ir.removeDiscount(itemNumber);
        } else
        {
            System.out.print("Det er foreløpig ingen rabatterte varer i registeret.\n");
        }
    }


    /**
     * Endrer/oppdaterer prisen på en vare. Dersom prisen brukeren taster inn er mindre enn 0, vil
     * brukeren få beskjed om å prøve på nytt.
     */
    public static void updatePrice()
    {
        System.out.println("\n__________________ ENDRE PRIS ____________________");
        printItemNumberAndDescription(ir.getItems());
        System.out.print("\nTast inn varenummeret til den varen du ønsker å endre prisen til: ");
        String itemNumber = Verifier.validateStringInput("Varenummeret");
        if (ir.getItem(itemNumber) != null)
        {
            System.out.print("\nNåværende pris: " + ir.getItem(itemNumber).getPrice() + " nok");
            System.out.print("\nHva skal prisen settes til? ");
            int updatedPrice = (int) Verifier.validateNumberInput("Prisen");
            ir.changePrice(itemNumber, updatedPrice);
            System.out.print("\nNy pris: " + updatedPrice + " nok\n");
        } else
        {
            System.out.print("Fant ingen vare med det varenummeret.\n");
        }
    }


    /**
     * Endrer beskrivelsen til en vare.
     */
    public static void changeDescription()
    {
        System.out.println("\n________________ ENDRE BESKRIVELSE _______________");
        printItemNumberAndDescription(ir.getItems());
        System.out.print("\nVarenummeret til den varen du ønsker å endre beskrivelsen til: ");
        String itemNumber = Verifier.validateStringInput("Varenummeret");
        if (ir.getItem(itemNumber) != null)
        {
            System.out.print("\nNy beskrivelse: ");
            ir.changeDescription(itemNumber, Verifier.validateStringInput("Beskrivelsen"));
            System.out.print("\nBeskrivelsen er oppdatert.\n");
        } else
        {
            System.out.print("Fant ingen vare med det varenummeret.\n");
        }
    }


    /**
     * Registrerer en ny vare til registeret.
     */
    public static void addItemToRegister()
    {
        System.out.println("________________ REGISTRER NY VARE _______________");
        System.out.print("Varenummer: ");
        final String itemNumber = Verifier.verifyItemNumber("exists");

        System.out.print("Varebeskrivelse: ");
        final String itemDescription = Verifier.validateStringInput("Varebeskrivelsen");

        System.out.print("Produsent/merke på vare: ");
        final String brand = Verifier.validateStringInput("Merke/produsenten");

        System.out.print("Farge på vare: ");
        final String color = Verifier.validateStringInput("Fargen");

        System.out.print("Vekt (kilogram): ");
        final double weight = Verifier.validateNumberInput("Vekten");

        System.out.print("Lengde på vare (m): ");
        final double length = Verifier.validateNumberInput("Lengden");

        System.out.print("Høyde på vare (m): ");
        final double height = Verifier.validateNumberInput("Høyden");

        System.out.print("Pris på vare: ");
        final int price = (int) Verifier.validateNumberInput("Prisen");

        System.out.print("Beholdning på vare: ");
        final int quantity = (int) Verifier.validateNumberInput("Beholdningen");

        System.out.println("Skriv inn tall for kategori:");
        for (int i = 0; i < Category.values().length; i++)
        {
            System.out.print((i + 1) + ") " + Category.values()[i] + "\n"); // verdiene i enum
        }
        int categoryNumber = Verifier.validateCategoryPick();

        ir.registerItem(new Item(itemNumber, itemDescription, brand, color, weight, length, height,
                        price, quantity, categoryNumber));
        System.out.print("\n_________________ VARE REGISTRERT ________________\n\n");
    }


    /**
     * Fjerner en vare fra registeret ved å bruke varenummer.
     */
    public static void removeItemFromRegister()
    {
        if (ir.getSize() == 0)
        {
            System.out.print("\nKan ikke fjerne varer. Registeret er tomt.\n");
        } else
        {
            System.out.println("\n___________________ FJERN VARE ___________________");
            printItemNumberAndDescription(ir.getItems());
            System.out.print("\nTast inn varenummeret til den varen du ønsker å fjerne: ");
            String itemNumber = Verifier.validateStringInput("Varenummeret");
            if (ir.getItem(itemNumber) != null)
            {
                ir.removeItem(itemNumber);
                System.out.print("\nVare " + itemNumber + " er fjernet fra registeret.\n");
            } else
            {
                System.out.print("Fant ingen vare med det varenummeret.\n");
            }
        }
    }
}