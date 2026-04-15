package histoire;

// Імпортуємо наших персонажів з сусіднього пакета
import personnages.Humain;
import personnages.Commercant;
import personnages.Yakuza;
import personnages.Ronin;

public class HistoireTP4 {
    public static void main(String[] args) {
        
        // Сценарій 1: Тестуємо звичайну людину
        Humain prof = new Humain("Prof", "kombucha", 54);
        prof.direBonjour();
        prof.acheter("boisson", 12);
        prof.boire();
        prof.acheter("jeu", 2);
        prof.acheter("kimono", 50);

        System.out.println("-------------------------");

        // Сценарій 2: Торговець і Ронін
        Commercant marco = new Commercant("Marco", 20);
        marco.direBonjour();
        marco.seFaireExtorquer();
        marco.recevoir(15);
        marco.boire();

        System.out.println("-------------------------");

        // Сценарій 3: Злий Якудза
        Yakuza yaku = new Yakuza("Yaku Le Noir", "whisky", 30, "Warsong");
        yaku.direBonjour();
        yaku.extorquer(marco);

        System.out.println("-------------------------");

        // Сценарій 4: Дуель Роніна і Якудзи
        Ronin roro = new Ronin("Roro", "shochu", 60);
        roro.direBonjour();
        roro.donner(marco);
        
        System.out.println("-------------------------");
        
        // Бій!
        roro.provoquer(yaku);
    }
}