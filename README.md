# PierreSabre
1. Humain.java (Оновити)
Ми додаємо масив пам'яті (до 30 людей) та логіку знайомства.

Java
package personnages;

public class Humain {
    private String nom;
    private String boissonFavorite;
    private int argent;
    
    // Нові атрибути для TP5: Пам'ять
    protected int nbConnaissance = 0;
    protected Humain[] memoire = new Humain[30];

    public Humain(String nom, String boissonFavorite, int argent) {
        this.nom = nom;
        this.boissonFavorite = boissonFavorite;
        this.argent = argent;
    }

    public String getNom() { return nom; }
    public int getArgent() { return argent; }

    protected void parler(String texte) {
        System.out.println("(" + nom + ") - " + texte);
    }

    public void direBonjour() {
        parler("Bonjour ! Je m'appelle " + nom + " et j'aime boire du " + boissonFavorite + ".");
    }

    public void boire() {
        parler("Mmmm, un bon verre de " + boissonFavorite + " ! GLOUPS !");
    }

    public void acheter(String bien, int prix) {
        if (argent >= prix) {
            parler("J'ai " + argent + " sous en poche. Je vais pouvoir m'offrir un(e) " + bien + " à " + prix + " sous.");
            perdreArgent(prix);
        } else {
            parler("Je n'ai plus que " + argent + " sous en poche. Je ne peux même pas m'offrir un(e) " + bien + " à " + prix + " sous.");
        }
    }

    protected void gagnerArgent(int gain) { argent += gain; }
    protected void perdreArgent(int perte) { argent -= perte; }

    // --- НОВІ МЕТОДИ ДЛЯ TP5 ---

    public void faireConnaissanceAvec(Humain autreHumain) {
        direBonjour();
        autreHumain.repondre(this);
        memoriser(autreHumain);
    }

    private void repondre(Humain humain) {
        direBonjour();
        memoriser(humain);
    }

    protected void memoriser(Humain humain) {
        if (nbConnaissance < memoire.length) {
            memoire[nbConnaissance] = humain;
            nbConnaissance++;
        } else {
            // Якщо пам'ять повна, зсуваємо всіх вліво (забуваємо найстарішого)
            for (int i = 0; i < memoire.length - 1; i++) {
                memoire[i] = memoire[i+1];
            }
            memoire[memoire.length - 1] = humain;
        }
    }

    public void listerConnaissance() {
        String res = "Je connais beaucoup de monde dont : ";
        for (int i = 0; i < nbConnaissance; i++) {
            res += memoire[i].getNom() + (i < nbConnaissance - 1 ? ", " : "");
        }
        parler(res);
    }
}
2. Yakuza.java (Оновити)
Якудза тепер повинен перевизначити метод direBonjour(), щоб називати свій клан.

Java
package personnages;

public class Yakuza extends Humain {
    private String clan;
    private int reputation = 0;

    public Yakuza(String nom, String boissonFavorite, int argent, String clan) {
        super(nom, boissonFavorite, argent);
        this.clan = clan;
    }

    public int getReputation() { return reputation; }

    // НОВЕ ДЛЯ TP5: Перевизначення привітання
    @Override
    public void direBonjour() {
        super.direBonjour();
        parler("Mon clan est celui de " + clan + ".");
    }

    public void extorquer(Commercant victime) {
        parler("Tiens, tiens, ne serait-ce pas un faible marchand qui passe par là ?");
        parler(victime.getNom() + ", si tu tiens à la vie donne moi ta bourse !");
        int argentVole = victime.seFaireExtorquer();
        gagnerArgent(argentVole);
        reputation++;
        parler("J'ai piqué les " + argentVole + " sous de " + victime.getNom() + ", ce qui me fait " + getArgent() + " sous dans ma poche. Hi ! Hi !");
    }

    public int perdre() {
        int argentPerdu = getArgent();
        perdreArgent(argentPerdu);
        reputation--;
        parler("J'ai perdu mon duel et mes " + argentPerdu + " sous, snif... J'ai déshonoré le clan de " + clan + ".");
        return argentPerdu;
    }

    public void gagner(int gain) {
        gagnerArgent(gain);
        reputation++;
        parler("Ce ronin pensait vraiment battre " + getNom() + " du clan de " + clan + " ? Je l'ai dépouillé de ses " + gain + " sous.");
    }
}
(Файли Commercant.java та Ronin.java залишаються без змін з TP4).

Частина 2: Нові класи (Створити нові файли)
3. Samourai.java (Створити новий)
Самурай — це крутіший Ронін, який служить лорду і може пити будь-що.

Java
package personnages;

public class Samourai extends Ronin {
    private String seigneur;

    public Samourai(String seigneur, String nom, String boissonFavorite, int argent) {
        super(nom, boissonFavorite, argent);
        this.seigneur = seigneur;
    }

    @Override
    public void direBonjour() {
        super.direBonjour();
        parler("Je suis fier de servir le seigneur " + seigneur + ".");
    }

    // Перевантаження методу: Самурай може пити не лише улюблений напій
    public void boire(String boisson) {
        parler("Qu'est-ce que je vais choisir comme boisson ? Tiens je vais prendre du " + boisson + ".");
    }
}
4. Traitre.java (Створити новий)
Злий самурай, який грабує своїх і має шкалу зради.

Java
package personnages;
import java.util.Random;

public class Traitre extends Samourai {
    private int niveauTraitrise = 0;

    public Traitre(String seigneur, String nom, String boissonFavorite, int argent) {
        super(seigneur, nom, boissonFavorite, argent);
    }

    @Override
    public void direBonjour() {
        super.direBonjour();
        parler("Mais je suis un traître et mon niveau de traîtrise est : " + niveauTraitrise + ". Chut !");
    }

    public void ranconner(Commercant commercant) {
        if (niveauTraitrise < 3) {
            int argentRanconner = commercant.getArgent() * 2 / 10;
            commercant.perdreArgent(argentRanconner);
            gagnerArgent(argentRanconner);
            niveauTraitrise++;
            parler("Si tu veux ma protection contre les Yakuzas, il va falloir payer ! Donne-moi " + argentRanconner + " sous ou gare à toi !");
            commercant.parler("Tout de suite grand " + getNom() + ".");
        } else {
            parler("Mince je ne peux plus rançonner personne sinon un samouraï risque de me démasquer !");
        }
    }

    public void faireLeGentil() {
        if (nbConnaissance < 1) {
            parler("Je ne peux faire ami ami avec personne car je ne connais personne ! Snif.");
        } else {
            Random rand = new Random();
            int indexAmi = rand.nextInt(nbConnaissance);
            Humain ami = memoire[indexAmi];
            int don = getArgent() / 20;
            
            parler("Il faut absolument remonter ma cote de confiance. Je vais faire ami ami avec " + ami.getNom() + ".");
            parler("Bonjour l'ami ! Je voudrais vous aider en vous donnant " + don + " sous.");
            ami.gagnerArgent(don);
            perdreArgent(don);
            ami.parler("Merci " + getNom() + ". Vous êtes quelqu'un de bien.");
            
            if (niveauTraitrise > 1) {
                niveauTraitrise--;
            }
        }
    }
}
5. GrandMere.java (Створити новий)
Пліткарка з поганою пам'яттю, але нюхом на зрадників.

Java
package personnages;
import java.util.Random;

public class GrandMere extends Humain {
    
    // Внутрішній перелік професій
    private enum TypeHumain {
        Commercant, Ronin, Samourai, Yakuza, habitant
    }

    public GrandMere(String nom, int argent) {
        super(nom, "tisane", argent);
    }

    // Перевизначаємо пам'ять: бабуся пам'ятає лише 5 людей!
    @Override
    protected void memoriser(Humain humain) {
        if (nbConnaissance < 5) {
            memoire[nbConnaissance] = humain;
            nbConnaissance++;
        } else {
            parler("Oh ma tête ! Je ne peux plus retenir le nom d'une personne supplémentaire !");
        }
    }

    private String humainHasard() {
        TypeHumain[] types = TypeHumain.values();
        Random rand = new Random();
        return types[rand.nextInt(types.length)].name();
    }

    public void ragoter() {
        for (int i = 0; i < nbConnaissance; i++) {
            Humain connaissance = memoire[i];
            
            // Бабуся миттєво "вираховує" зрадників!
            if (connaissance instanceof Traitre) {
                parler("Je sais que " + connaissance.getNom() + " est un traître. Petit chenapan !");
            } else {
                parler("Je crois que " + connaissance.getNom() + " est un " + humainHasard());
            }
        }
    }
}
Частина 3: Фінальний тест (Створити в пакеті histoire)
6. HistoireTP5.java (Новий головний клас для запуску)
Створи цей файл у пакеті histoire. Він повністю відтворить сценарій, описаний у методичці.

Java
package histoire;

import personnages.*;

public class HistoireTP5 {
    public static void main(String[] args) {
        
        // 1. Тестуємо пам'ять
        Commercant marco = new Commercant("Marco", 20);
        Commercant chonin = new Commercant("Chonin", 40);
        Commercant kumi = new Commercant("Kumi", 10);
        Yakuza yaku = new Yakuza("Yaku Le Noir", "whisky", 30, "Warsong");
        Ronin roro = new Ronin("Roro", "shochu", 60);

        marco.faireConnaissanceAvec(roro);
        marco.faireConnaissanceAvec(yaku);
        marco.faireConnaissanceAvec(chonin);
        marco.faireConnaissanceAvec(kumi);

        marco.listerConnaissance();
        roro.listerConnaissance();
        yaku.listerConnaissance();

        System.out.println("-------------------------");

        // 2. Тестуємо Самурая
        Samourai akimoto = new Samourai("Miyamoto", "Akimoto", "saké", 80);
        akimoto.faireConnaissanceAvec(marco);
        akimoto.boire("thé");

        System.out.println("-------------------------");

        // 3. Тестуємо Зрадника
        Traitre masako = new Traitre("Miyamoto", "Masako", "whisky", 100);
        masako.faireLeGentil();
        masako.ranconner(kumi);
        masako.ranconner(chonin);
        masako.ranconner(marco);
        akimoto.faireConnaissanceAvec(masako);
        yaku.faireConnaissanceAvec(masako);
        masako.faireLeGentil();
        masako.faireConnaissanceAvec(roro);

        System.out.println("-------------------------");

        // 4. Тестуємо Бабусю
        GrandMere grandMere = new GrandMere("Grand-Mère", 10);
        grandMere.faireConnaissanceAvec(akimoto);
        grandMere.faireConnaissanceAvec(yaku);
        grandMere.faireConnaissanceAvec(masako);
        grandMere.faireConnaissanceAvec(kumi);
        grandMere.faireConnaissanceAvec(marco);
        grandMere.faireConnaissanceAvec(chonin);
        grandMere.ragoter();
    }
}
