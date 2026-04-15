package personnages;

public class Commercant extends Humain {

    public Commercant(String nom, int argent) {
        // super викликає конструктор батьківського класу (Humain)
        super(nom, "thé", argent);
    }

    public int seFaireExtorquer() {
        int sommePerdue = getArgent();
        perdreArgent(sommePerdue);
        parler("J'ai tout perdu ! Le monde est trop injuste...");
        return sommePerdue;
    }

    public void recevoir(int argent) {
        gagnerArgent(argent);
        parler(argent + " sous ! Je te remercie généreux donateur !");
    }
}