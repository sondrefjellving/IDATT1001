import java.util.Scanner;

/**
 * Denne klassen inneholder hjelpemetoder som verifiserer input.
 *
 * @author 10082
 */
public class Verifier
{
    private static Scanner scan = new Scanner(System.in);


    /**
     * Sjekker og verifiserer String variabler. Returnerer verdien dersom den fyller kravene.
     *
     * @param variableToCheck Variabelen som skal sjekkes                   - (String)
     * @param nameForMessage Navnet/ordet som skal brukes til feilmeldingen - (String)
     * @return Godkjent variabel                                            - (String)
     */
    public String verifyStringVariable(String variableToCheck, String nameForMessage)
    {
        if (variableToCheck == null || variableToCheck.isBlank())
        {
            throw new IllegalArgumentException(nameForMessage + " må inneholde tekst.");
        }
        return variableToCheck;
    }


    /**
     * Sjekker og verifiserer double variabler. Returnerer verdien dersom den fyller kravene.
     *
     * @param variableToCheck Variabelen som skal sjekkes                   - (double)
     * @param nameForMessage Navnet/ordet som skal brukes til feilmeldingen - (String)
     * @return Godkjent variabel                                            - (double)
     */
    public double verifyDoubleVariable(Double variableToCheck, String nameForMessage)
    {
        if (variableToCheck < 0)
        {
            throw new IllegalArgumentException(nameForMessage + " må være høyere enn 0.");
        }
        return variableToCheck;
    }


    /**
     * Sjekker og verifiserer int variabler. Returnerer verdien dersom den fyller kravene.
     *
     * @param variableToCheck Variabelen som skal sjekkes                   - (int)
     * @param nameForMessage Navnet/ordet som skal brukes til feilmeldingen - (String)
     * @return Godkjent variabel                                            - (int)
     */
    public int verifyIntVariable(int variableToCheck, String nameForMessage)
    {
        if (variableToCheck < 0)
        {
            throw new IllegalArgumentException(nameForMessage + " kan ikke være mindre enn 0.");
        }
        return variableToCheck;
    }


    /**
     * Sjekker om det varenummeret som blir tastet inn allerede eksisterer. Brukeren blir bedt om
     * å taste inn et nytt varenummer helt til den taster inn et varenummer som ikke er i
     * registeret.
     *
     * @param checkFor exists eller doesnt exist - (String)
     * @return Varenummer - (String)
     */
    public static String verifyItemNumber(String checkFor)
    {
        String itemNumber = validateStringInput("Varenummeret");

        switch (checkFor)
        {
            case "exists":
                while (ItemTerminal.ir.checkItemNumber(itemNumber))
                { // sjekker om det allerede finnes en vare med gitt varenummer
                    System.out.print("\nDet finnes allerede en vare med det varenummeret. "
                        + "Velg et nytt: ");
                    itemNumber = validateStringInput("Varenummeret");
                }
                break;
            case "doesnt exist":
                while (!ItemTerminal.ir.checkItemNumber(itemNumber))
                { // sjekker om det allerede finnes en vare med gitt varenummer
                    System.out.print("\nSkriv inn varenummeret til en av "
                        + "varene som har rabbatert pris: ");
                    itemNumber = validateStringInput("Varenummeret");
                }
                break;
            default: System.out.print("Sjekk koden");
        }
        return itemNumber;
    }


    /**
     * Sjekker om menyvalget brukeren taster inn er gyldig.
     *
     * @return Menyvalg - (int)
     */
    public static int validateMenuChoice()
    {
        while (!scan.hasNextInt()) // denne brukes for å sjekke om brukeren taster inn et tall
        {
            System.out.print("Tast inn et tall som representerer et av valgene ovenfor: ");
            scan.nextLine();
        }

        int menuChoice = scan.nextInt();
        scan.nextLine();
        return menuChoice;
    }


    /**
     * Sjekker om inputen til brukeren er gyldig. Hvis den ikke er gyldig, vil brukeren
     * få muligheten til å taste inn inputen på nytt. Når inputen brukeren taster inn er
     * gyldig, vil den bli returnert.
     *
     * @param variableBeingChecked Navn på den variabelen som skal sjekkes  - (String)
     * @return Brukerinput                                                  - (String)
     */
    public static String validateStringInput(String variableBeingChecked)
    {
        while (true)
        {
            String stringInput = scan.nextLine();
            if (stringInput.isBlank())
            {
                System.out.println(variableBeingChecked + " må inneholde tekst.");
                System.out.print("Prøv på nytt: ");
            } else
            {
                return stringInput;
            }
        }
    }


    /**
     * Sjekker om inputen til brukeren er gyldig. Sjekker først om verdien som blir
     * sendt inn er et tall, og deretter om tallet er større enn 0. Dersom en av
     * tilfellene ikke fyller kravene, blir brukeren få sjansen til å taste inn
     * inputen på nytt.
     *
     * @param variableBeingChecked Navn på den variabelen som skal sjekkes - (String)
     * @return Tallet som skal registreres                                 - (double)
     */
    public static double validateNumberInput(String variableBeingChecked)
    {
        while (true)
        {
            double numberInput = (scan.hasNextDouble()) ? scan.nextDouble() : -404;
            scan.nextLine();
            if (numberInput < 0)
            {
                System.out.println("\n" + variableBeingChecked
                    + " må være et tall større enn eller lik 0.");
                System.out.print("Prøv på nytt: ");
            } else
            {
                return numberInput;
            }
        }
    }


    /**
     * Sjekker om det brukeren ønsker å redusere med, er gyldig.
     *
     * @param itemNumber Varenummeret til varen som skal reduseres - (String)
     * @return Reduksjonen                                         - (int)
     */
    public static int validateReductionInput(String itemNumber)
    {
        while (true)
        {
            int numberInput = (scan.hasNextInt()) ? scan.nextInt() : -404;
            scan.nextLine();
            if (numberInput <= 0 || numberInput > ItemTerminal.ir.getItem(itemNumber).getQuantity())
            {
                System.out.println("\nReduksjonen "
                    + " må være et tall større enn 0 og kan ikke være større enn beholdningen.");
                System.out.print("Prøv på nytt: ");
            } else
            {
                return numberInput;
            }
        }
    }


    /**
     * Sjekker om inputen til brukeren er gyldig. Sjekker først om verdien brukeren taster inn er
     * et heltall.Hvis det er et heltall, sjekker den om tallet er en verdi fra og med 1, til og
     * med 4. Dersom det stemmer vil verdien bli lagret og returnert. Hvis ikke, vil brukeren få
     * mulighet til å taste inn verdien på nytt.
     *
     * @return Kategorinummeret som skal brukes for å velge en kategori fra kategorilista - (int)
     */
    public static int validateCategoryPick()
    {
        while (true)
        {
            int categoryNumber = (scan.hasNextInt()) ? scan.nextInt() : - 404;
            scan.nextLine();
            if (categoryNumber < 1 || categoryNumber > Category.values().length)
            {
                System.out.println("Det må skrives inn et tall fra og med 1, til og med "
                    + Category.values().length + ".");
                System.out.print("Prøv på nytt: ");
            } else
            {
                return (categoryNumber - 1); // -1, fordi indeks starter på 0
            }
        }
    }


    /**
     * Sjekker om rabatten brukeren skriver inn er gyldig. Dersom den ikke er gyldig, får brukeren
     * beskjed om å prøve på nytt. Dersom den er gyldig vil den aksepterte verdien bli returnert.
     * Rabatten må være et tall mellom 0 og 100.
     *
     * @return Rabatt - (double)
     */
    public static double validateDiscount()
    {
        while (true)
        {
            double discount = (scan.hasNextDouble()) ? scan.nextDouble() : -404;
            scan.nextLine();
            if (discount < 0 || discount >= 100)
            {
                System.out.print("\nRabatten kan ikke settes til "
                    + "mindre enn 0 eller større enn eller lik 100.");
                System.out.print("\nPrøv på nytt: ");
            } else
            {
                return discount;
            }
        }
    }
}
