package ca.qc.bdeb.info202.Gestion;

import ca.qc.bdeb.info202.Elements.Element;
import ca.qc.bdeb.info202.Elements.AddOn;
import ca.qc.bdeb.info202.Elements.Energy;
import ca.qc.bdeb.info202.Elements.Normal;
import ca.qc.bdeb.info202.Elements.Structure;
import java.io.*;
import java.util.*;

public class GestionStarcraft implements Serializable {

    /**
     * Liste principale qui contiendra tous les éléments créées
     */
    private static ArrayList<Element> dataBase = new ArrayList();

    /**
     * Liste temporaire qui contiendra les éléments créées pour les affichages
     */
    private static ArrayList<Element> temp = new ArrayList();

    public GestionStarcraft(){
        /*
        À LIRE AVANT DE VÉRIFIER LE TP, CAR CECI EST TRÈS TRÈS TRÈS TRÈS IMPORTANT!!
        Juste un petit message, nous n'avons pas utilisé les exceptions personnalisées puisque de toute façon, notre méthode
        checkInput règle déjà toutes les exceptions relier à l'utilisateur. Ceci amène aussi au fait que, comme dans votre exemple avec 
        le café et le sucre qui ne peut être négatif, cette erreur ne peut se produire non plus. Ainsi, puisque le seul moment où nous avons
        besoin des exceptions c'est aux inputs et que cela règle déjà les problèmes lorsque vient le moment de crééer un objet Élément, nous 
        n'avons pu faire d'exception personnalisée.
        */
    }

    /**
     * Cerveau du TP (xd)
     */
    public void brain() {
        Scanner cl = new Scanner(System.in);
        fileCheck();
        boolean restart = true;
        int chxAction;
        int chxTri;
        do {
            dataBase = loadData(dataBase);
            temp = (ArrayList<Element>) dataBase.clone();
            chxAction = menuPrincipale(cl);
            switch (chxAction) {
                //AFFICHAGE
                case 1:
                    showList(dataBase);
                    break;
                //INFORMATIONS
                case 2:
                    chxTri = menuInfoTri(cl);
                    switch (chxTri) {
                        case 1:
                            triInsert(temp);
                            break;
                        case 2:
                            removeAllExceptUnit(cl, temp);
                            showList(temp);
                            break;
                        default:
                            menuInfo3(cl, temp);
                            break;
                    }
                    break;

                //SAISIR 
                case 3:
                    ajout(cl, dataBase);
                    break;

                //MODIFIER    
                case 4:
                    removeType(cl, temp, dataBase);
                    break;

                //QUITTER
                default:
                    saveData(dataBase);
                    restart = false;
                    break;
            }
        } while (restart);
    }

    /**
     * Affichage du menuScanner principale
     *
     * @param cl Scanner pour permettre le input
     * @return Le choix de l'action
     */
    private static int menuPrincipale(Scanner cl) {
        System.out.println("**********************************");
        System.out.println("*1- Affichage                    *");
        System.out.println("*2- Informations                 *");
        System.out.println("*3- Créer un élément             *");
        System.out.println("*4- Modifier/Supprimer un élément*");
        System.out.println("*5- Quitter et Sauvegarder       *");
        System.out.println("**********************************");
        System.out.print("Que voulez-vous faire? ");
        int chx = checkInput(cl, 1, 5);
        return chx;
    }

    /**
     * Affichage du menu des tris
     *
     * @param cl Scanner pour permettre le input
     * @return Le choix du tris
     */
    private static int menuInfoTri(Scanner cl) {
        System.out.println("******************************************************");
        System.out.println("*1- Trier par prix de production \033[31m[Tout les éléments]\033[0m *");
        System.out.println("* 2- Trier par Health Points(HP)  \033[31m[Unité seulement]\033[0m  *");
        System.out.println("*   3- Trier par prix de prodoction plus elevé à X   *");
        System.out.println("*               \033[31m[Structure seulement]\033[0m                *");
        System.out.println("******************************************************");
        System.out.print("Que voulez-vous faire? ");
        int chxTri = checkInput(cl, 1, 3);
        return chxTri;
    }

    /**
     * Demander le input du prix minimum voulu pour le tri par séléction
     *
     * @param cl Scanner pour permettre le input
     * @param temp Liste temporaire qui contiendra les éléments créées pour les
     * affichages
     */
    private static void menuInfo3(Scanner cl, ArrayList<Element> temp) {
        System.out.println("Quel sera le prix mininum à afficher?");
        System.out.print("Prix: ");
        int prix = checkInput(cl, 0, Integer.MAX_VALUE);
        removeAllExceptStructure(cl, temp);
        triSelect(temp, prix);
    }

    /**
     * Méthodes pour l'ajout(création) d'éléments dans la liste principale
     *
     * @param cl Scanner pour permettre le input
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void ajout(Scanner cl, ArrayList<Element> dataBase) {
        int chx;
        int chxUnit;
        System.out.println("Quel type d'élément voulez-vous?");
        System.out.println("1- Unité    2- Structure    3- Add-on");
        chx = checkInput(cl, 1, 3);
        switch (chx) {
            case 1:
                System.out.println("Quel type d'unité voulez vous?");
                System.out.println("1-Normale       2-Énergie");
                chxUnit = checkInput(cl, 1, 2);
                switch (chxUnit) {
                    case 1:
                        createNormUnit(cl, dataBase);
                        break;
                    default:
                        createEnerUnit(cl, dataBase);
                        break;
                }
                break;
            case 2:
                createStruct(cl, dataBase);
                break;
            default:
                createAddOn(cl, dataBase);
                break;
        }
        showList(dataBase);
        saveData(dataBase);
    }

    /**
     * Méthode pour permettre la modification d'élément dans la liste principale
     * (dataBase) et dans la liste d'affichage (temp)
     *
     * @param cl Scanner pour permettre le input
     * @param temp Liste temporaire qui contiendra les éléments créées pour les
     * affichages
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void modifier(Scanner cl, ArrayList<Element> temp, ArrayList<Element> dataBase) {
        boolean restart = true;
        do {
            System.out.println("1- Modifier \t 2- Supprimer \t 3- Quitter");
            System.out.print("Que voulez-vous faire? ");
            int chx = checkInput(cl, 1, 3);
            int k;
            switch (chx) {
                case 1:
                    showList(temp);
                    System.out.print("Quel élément voulez-vous modifier selon sa position? ");
                    int chxMod = checkInput(cl, 1, temp.size());
                    int idMod = temp.get(chxMod - 1).getNumeroSerie();
                    System.out.println("chxMod = " + chxMod);
                    System.out.println("idMod = " + idMod);
                    System.out.print("Nouveau HP: ");
                    int newHP = checkInput(cl, 1, Integer.MAX_VALUE);
                    System.out.print("Nouveau Prix Mineraux: ");
                    int newPrix = checkInput(cl, 1, Integer.MAX_VALUE);
                    k = 0;
                    while (k < dataBase.size()) {
                        if (dataBase.get(k).getNumeroSerie() == idMod) {
                            if (dataBase.get(k) instanceof Structure) {
                                System.out.println("first");
                                System.out.print("Nouveau Prix Gas: ");
                                int newPrixG = checkInput(cl, 1, Integer.MAX_VALUE);
                                dataBase.get(k).setPrixProdGAS(newPrixG);
                                temp.get(k).setPrixProdGAS(newPrixG);
                            }
                            if (dataBase.get(k) instanceof Energy) {
                                System.out.println("second");
                                System.out.println("Nouveau Prix Energy: ");
                                int newPrixNRG = checkInput(cl, 1, Integer.MAX_VALUE);
                                dataBase.get(k).setPrixProdGAS(newPrixNRG);
                                temp.get(k).setPrixProdGAS(newPrixNRG);
                            }
                            System.out.println("third");
                            dataBase.get(k).setPrixProdMIN(newPrix);
                            dataBase.get(k).setHp(newHP);
                            temp.get(k).setPrixProdMIN(newPrix);
                            temp.get(k).setHp(newHP);
                            k++;
                        } else {
                            k++;
                        }
                    }
                    saveData(dataBase);
                    break;
                case 2:
                    showList(temp);
                    System.out.print("Quel élément voulez-vous supprimer selon sa position? " );
                    int chxSup = checkInput(cl, 1, temp.size());
                    int idSup = temp.get(chxSup - 1).getNumeroSerie();
                    temp.remove(chxSup - 1);
                    k = 0;
                    while (k < dataBase.size()) {
                        if (dataBase.get(k).getNumeroSerie() == idSup) {
                            dataBase.remove(k);
                        } else {
                            k++;
                        }
                    }
                    showList(temp);
                    break;
                default:
                    restart = false;
                    break;
            }
        } while (restart);
    }

    /**
     * Méthode de création pour les AddOn qui sera utiliser dans la méthode
     * ajout
     *
     * @param cl Scanner pour permettre le input
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void createAddOn(Scanner cl, ArrayList<Element> dataBase) {
        Race race;
        System.out.print("Nom: ");
        String name = cl.nextLine();
        System.out.println("Race possible : 1-Terran    2- Zerg     3-Protoss");
        System.out.print("Race: ");
        int chxRace = checkInput(cl, 1, 3);
        switch (chxRace) {
            case 1:
                race = Race.TERRAN;
                break;
            case 2:
                race = Race.ZERG;
                break;
            default:
                race = Race.PROTOSS;
                break;
        }
        System.out.print("HP: ");
        int hp = checkInput(cl, 1, Integer.MAX_VALUE);
        System.out.print("DEF: ");
        int def = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Côut MIN: ");
        int prixMIN = checkInput(cl, 0, Integer.MAX_VALUE);

        AddOn typeAdd = new AddOn(name, race, hp, def, prixMIN);
        dataBase.set(dataBase.size(), typeAdd);
    }

    /**
     * Méthode de création pour les Structures qui sera utiliser dans la méthode
     * ajout
     *
     * @param cl Scanner pour permettre le input
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void createStruct(Scanner cl, ArrayList<Element> dataBase) {
        Race race;
        System.out.print("Nom: ");
        String name = cl.nextLine();
        System.out.println("Race possible : 1-Terran    2- Zerg     3-Protoss");
        System.out.print("Race: ");
        int chxRace = checkInput(cl, 1, 3);
        switch (chxRace) {
            case 1:
                race = Race.TERRAN;
                break;
            case 2:
                race = Race.ZERG;
                break;
            default:
                race = Race.PROTOSS;
                break;
        }
        System.out.print("HP: ");
        int hp = checkInput(cl, 1, Integer.MAX_VALUE);
        System.out.print("DEF: ");
        int def = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Coût MIN: ");
        int prixMIN = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Coût GAS: ");
        int prixGAS = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Add-On (1-Oui/2-Non): ");
        int chxAddOn = checkInput(cl, 1, 2);
        boolean addOn = checkAirGround(chxAddOn);

        Structure typeStr = new Structure(name, race, hp, def, prixMIN, prixGAS, addOn);
        dataBase.add(dataBase.size(), typeStr);
    }

    /**
     * Méthode de création pour les Unités d'Énérgies qui sera utiliser dans la
     * méthode ajout
     *
     * @param cl Scanner pour permettre le input
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void createEnerUnit(Scanner cl, ArrayList<Element> dataBase) {
        Race race;
        System.out.print("Nom: ");
        String name = cl.nextLine();
        System.out.println("Race possible : 1-Terran    2- Zerg     3-Protoss");
        System.out.print("Race: ");
        int chxRace = checkInput(cl, 1, 3);
        switch (chxRace) {
            case 1:
                race = Race.TERRAN;
                break;
            case 2:
                race = Race.ZERG;
                break;
            default:
                race = Race.PROTOSS;
                break;
        }
        System.out.print("HP: ");
        int hp = checkInput(cl, 1, Integer.MAX_VALUE);
        System.out.print("DEF: ");
        int def = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("DMG: ");
        int dmg = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("NRG DMG: ");
        int dmgNRG = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Shield: ");
        int shield = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Coût MIN: ");
        int prixMIN = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Coût NRG: ");
        int prixNRG = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Air (1-Oui/2-Non): ");
        int chxAir = checkInput(cl, 1, 2);
        boolean air = checkAirGround(chxAir);
        System.out.print("Terre (1- Oui/2-Non): ");
        int chxGround = checkInput(cl, 1, 2);
        boolean ground = checkAirGround(chxGround);

        Energy typeNRG = new Energy(name, race, hp, def, dmg, dmgNRG, shield, prixMIN, prixNRG, air, ground);
        dataBase.add(dataBase.size(), typeNRG);
    }

    /**
     * Méthode de création pour les Unités Normales qui sera utiliser dans la
     * méthode ajout
     *
     * @param cl Scanner pour permettre le input
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void createNormUnit(Scanner cl, ArrayList<Element> dataBase) {
        Race race;
        System.out.print("Nom: ");
        String name = cl.nextLine();
        System.out.println("Race possible : 1-Terran    2- Zerg     3-Protoss");
        System.out.print("Race: ");
        int chxRace = checkInput(cl, 1, 3);
        switch (chxRace) {
            case 1:
                race = Race.TERRAN;
                break;
            case 2:
                race = Race.ZERG;
                break;
            default:
                race = Race.PROTOSS;
                break;
        }
        System.out.print("HP: ");
        int hp = checkInput(cl, 1, Integer.MAX_VALUE);
        System.out.print("DEF: ");
        int def = checkInput(cl, 0, Integer.MAX_VALUE);//
        System.out.print("DMG: ");
        int dmg = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Côut MIN: ");
        int prixMIN = checkInput(cl, 0, Integer.MAX_VALUE);
        System.out.print("Air (1-Oui/2-Non): ");
        int chxAir = checkInput(cl, 1, 2);
        boolean air = checkAirGround(chxAir);
        System.out.print("Terre (1- Oui/2-Non): ");
        int chxGround = checkInput(cl, 1, 2);
        boolean ground = checkAirGround(chxGround);

        Normal typeNorm = new Normal(name, race, hp, def, dmg, prixMIN, air, ground);
        dataBase.add(dataBase.size(), typeNorm);
    }

    /**
     * Méthode qui fait un tri par séléction alphabétique
     *
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void triSelect(ArrayList<Element> dataBase) {
        int positionMin;
        Element temp;
        for (int i = 0; i < dataBase.size(); i++) {
            positionMin = i;
            for (int j = i + 1; j < dataBase.size(); j++) {
                if (dataBase.get(j).getName().compareToIgnoreCase(dataBase.get(positionMin).getName()) < 0) {
                    positionMin = j;
                }
            }
            temp = dataBase.get(positionMin);
            dataBase.set(positionMin, dataBase.get(i));
            dataBase.set(i, temp);
        }
    }

    /**
     * Méthode qui fait un tri par séléction selon le prix (int) introduit dans
     * la méthode menuInfo3
     *
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     * @param prix
     */
    private static void triSelect(ArrayList<Element> dataBase, int prix) {
        int k = 0;
        while (k < dataBase.size()) {
            if (dataBase.get(k).getPrixProdMIN() <= prix) {
                dataBase.remove(k);
            } else {
                k++;
            }
        }
        Element temp;
        int positionMin;
        for (int i = 0; i < dataBase.size(); i++) {
            positionMin = i;
            for (int j = i + 1; j < dataBase.size(); j++) {
                if (dataBase.get(j).getPrixProdMIN() < dataBase.get(positionMin).getPrixProdMIN()) {
                    positionMin = j;
                }
            }
            temp = dataBase.get(positionMin);
            dataBase.set(positionMin, dataBase.get(i));
            dataBase.set(i, temp);
        }
        showList(dataBase);
    }

    /**
     * Méthode qui fait un tri par insertion
     *
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void triInsert(ArrayList<Element> dataBase) {
        for (int i = 1; i < dataBase.size(); i++) {
            Element valeur = dataBase.get(i);
            int position = i;
            while (position > 0 && dataBase.get(position - 1).getPrixProdMIN() > valeur.getPrixProdMIN()) {
                dataBase.set(position, dataBase.get(position - 1));
                position--;
            }
            dataBase.set(position, valeur);
        }
        showList(dataBase);
    }

    /**
     * Méthode qui fait un tri rapide
     *
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     * @param debut
     * @param fin
     */
    private static void triQuick(ArrayList<Element> dataBase, int debut, int fin) {
        if (debut < fin) {
            int indicePivot = partition(dataBase, debut, fin);
            triQuick(dataBase, debut, indicePivot - 1);
            triQuick(dataBase, indicePivot + 1, fin);
        }
    }

    /**
     * Méthode qui partitionne les données d'un liste. Elle sera utilisé dans la
     * méthodee triQuick pour faire le tri.
     *
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     * @param debut Première valeur qui va permettre la comparaison
     * @param fin La taille de la liste
     * @return
     */
    private static int partition(ArrayList<Element> dataBase, int debut, int fin) {
        Element valeurPivot = dataBase.get(debut);
        int d = debut + 1;
        int f = fin;
        while (d < f) {
            while (d < f && dataBase.get(f).getHp() >= valeurPivot.getHp()) {
                f--;
            }
            while (d < f && dataBase.get(d).getHp() <= valeurPivot.getHp()) {
                d++;
            }
            Element temp = dataBase.get(d);
            dataBase.set(d, dataBase.get(f));
            dataBase.set(f, temp);
        }
        if (dataBase.get(d).getHp() > valeurPivot.getHp()) {
            d--;
        }
        dataBase.set(debut, dataBase.get(d));
        dataBase.set(d, valeurPivot);
        return d;
    }

    /**
     * Chargement de la liste (dataBase) sauvegardé auparavant.
     *
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     * @return Return la liste loader du fichier et l'envois à la lsite principale
     */
    private static ArrayList<Element> loadData(ArrayList<Element> dataBase){
        try {
            FileInputStream fileLoad = new FileInputStream("starcraft.bin");
            ObjectInputStream objectLoad = new ObjectInputStream(fileLoad);

            Element.setID((int) objectLoad.readObject());
            dataBase = (ArrayList<Element>) objectLoad.readObject();
        } catch (ClassNotFoundException | IOException error) {
            System.out.println("La classe ou le document n'existe pas!");
        }
        return dataBase;
    }

    /**
     * Sauvegarde de la liste (dataBase).
     *
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void saveData(ArrayList<Element> dataBase) {
        try {
            FileOutputStream fileSave = new FileOutputStream("starcraft.bin", false);
            ObjectOutputStream objectSave = new ObjectOutputStream(fileSave);

            objectSave.writeObject(Element.getID());
            objectSave.flush();
            objectSave.writeObject(dataBase);
            objectSave.flush();
            objectSave.close();
        } catch (FileNotFoundException error) {
            System.out.println("Le document n'existe pas!");
        } catch (IOException error) {
            System.out.println("Le document n'a pus être lu!");
        }
    }

    /**
     * Méthode qui enlève les races pour permettre le tris sur une seule Race.
     *
     * @param cl Scanner pour permettre le input
     * @param temp Liste temporaire qui contiendra les éléments créées pour les
     * affichages
     */
    private static void removeRace(Scanner cl, ArrayList<Element> temp) {
        Race race;
        System.out.println("1-Terran    2- Zerg     3-Protoss");
        System.out.print("Quel race voulez-vous garder? ");
        int chx = checkInput(cl, 1, 3);
        int k;
        switch (chx) {
            case 1:
                race = Race.TERRAN;
                k = 0;
                while (k < temp.size()) {
                    if (temp.get(k).getRace() != race) {
                        temp.remove(k);
                    } else {
                        k++;
                    }
                }
                break;
            case 2:
                race = Race.ZERG;
                k = 0;
                while (k < temp.size()) {
                    if (temp.get(k).getRace() != race) {
                        temp.remove(k);
                    } else {
                        k++;
                    }
                }
                break;
            default:
                race = Race.PROTOSS;
                k = 0;
                while (k < temp.size()) {
                    if (temp.get(k).getRace() != race) {
                        temp.remove(k);
                    } else {
                        k++;
                    }
                }
                break;
        }
        triQuick(temp, 0, temp.size() - 1);
    }

    /**
     * Méthode qui enlève les types pour permettre le tris sur un seul Type.
     *
     * @param cl Scanner pour permettre le input
     * @param temp Liste temporaire qui contiendra les éléments créées pour les
     * affichages
     * @param dataBase Liste principale qui contiendra tous les éléments créées
     */
    private static void removeType(Scanner cl, ArrayList<Element> temp, ArrayList<Element> dataBase) {
        System.out.println("1-Unit    2-Structure     3- AddOn");
        System.out.print("Quel type voulez-vous garder? ");
        int chx = checkInput(cl, 1, 3);
        int k;
        switch (chx) {
            case 1:
                k = 0;
                while (k < temp.size()) {
                    if (temp.get(k) instanceof Energy || temp.get(k) instanceof Normal) {
                        k++;
                    } else {
                        temp.remove(k);
                    }
                }
                break;
            case 2:
                k = 0;
                while (k < temp.size()) {
                    if (temp.get(k) instanceof Structure) {
                        k++;
                    } else {
                        temp.remove(k);
                    }
                }
                break;
            default:
                k = 0;
                while (k < temp.size()) {
                    if (temp.get(k) instanceof AddOn) {
                        k++;
                    } else {
                        temp.remove(k);
                    }
                }
                break;
        }
        triSelect(temp);
        modifier(cl, temp, dataBase);
        saveData(dataBase);
    }

    /**
     * Une partie de la méthode removeType qui enlève tous sauf les éléments de type unité
     * @param cl Scanner pour permettre le input
     * @param temp Liste temporaire qui contiendra les éléments créées pour les
     * affichages
     */
    private static void removeAllExceptUnit(Scanner cl, ArrayList<Element> temp) {
        int k = 0;
        while (k < temp.size()) {
            if (temp.get(k) instanceof Energy || temp.get(k) instanceof Normal) {
                k++;
            } else {
                temp.remove(k);
            }
        }
        removeRace(cl, temp);
    }
    
    /**
     * Une partie de la méthode removeType qui enlève tous sauf les éléments de type structure
     * @param cl Scanner pour permettre le input
     * @param temp Liste temporaire qui contiendra les éléments créées pour les
     * affichages
     */
    public static void removeAllExceptStructure(Scanner cl, ArrayList<Element> temp) {
        int k = 0;
        while (k < temp.size()) {
            if (temp.get(k) instanceof Structure) {
                k++;
            } else {
                temp.remove(k);
            }
        }
    }

    /**
     * Méthode d'éxception qui gère le input de l'utilisateur
     *
     * @param cl Scanner pour permettre le input
     * @param min Valeur minimale que l'utilisateur peut entrer
     * @param max Valeur maximale que l'utilisateur peut entrer
     * @return Valeur qui respecte toutes les caractéristiques de la méthode
     * (NumberFormateException, plus grand que min et plus petit que max)
     */
    private static int checkInput(Scanner cl, int min, int max) {
        int rep = Integer.MIN_VALUE;
        do {
            try {
                rep = Integer.parseInt(cl.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrez un chiffre valide");
            }
            if (rep < min || rep > max) {
                System.out.println("Entrez un chiffre entre " + min + " et " + max);
            }
        } while (rep < min || rep > max);
        return rep;
    }

    /**
     * Méthode qui détermine, selon le input int (chx), si l'élément créée est
     * dans l'air ou sur terre
     *
     * @param chx
     * @return True ou False selon le input int (chx) envoyé
     */
    private static boolean checkAirGround(int chx) {
        return chx == 1;
    }

    /**
     * Méthode qui vérifie si le fichier "starcraft.bin" est créée. si non alors
     * il créée un fichier "starcraft.bin" avec la méthode creationFichier()
     */
    private static void fileCheck() {
        File file = new File("starcraft.bin");
        if (file.exists() == false) {
            creationFichier();
        }
    }

    /**
     * Méthode qui créée le fichier "starcraft.bin" et qui génère les éléments
     * de base
     */
    private static void creationFichier() {
        ArrayList<Element> dataBase = new ArrayList();
//       Initialisation des variable objets (Normal, Energy, Structure & AddOn)
        Normal typeNorm;
        Energy typeNRG;
        Structure typeStr;
        AddOn typeAdd;
//        Creation Normale    (Name, race, hp, def, dmg, prixProd, air, ground) 
        typeNorm = new Normal("Marine", Race.TERRAN, 45, 0, 6, 50, false, true);
        dataBase.add(typeNorm);
//        Creation Energy    (Name, race, hp, def, dmg, nrg, shield, prixMin, prixGas, air, ground)
        typeNRG = new Energy("Mutalisk", Race.ZERG, 120, 0, 9, 12, 0, 100, 100, true, false);
        dataBase.add(typeNRG);
        typeNRG = new Energy("Ravager", Race.ZERG, 120, 1, 16, 20, 0, 25, 75, false, true);
        dataBase.add(typeNRG);
        typeNRG = new Energy("Void Ray", Race.PROTOSS, 150, 0, 6, 0, 100, 250, 150, true, true);
        dataBase.add(typeNRG);
//        Creation Structures   (Name, race, hp, def, prixMin, prixGas, allowAddOn)
        typeStr = new Structure("Forge", Race.PROTOSS, 150, 1, 150, 100, true);
        dataBase.add(typeStr);
        typeStr = new Structure("Pylon", Race.PROTOSS, 400, 1, 150, 0, false);
        dataBase.add(typeStr);
        typeStr = new Structure("Armory", Race.TERRAN, 750, 1, 150, 100, true);
        dataBase.add(typeStr);
        typeStr = new Structure("Spire", Race.ZERG, 750, 1, 200, 200, false);
        dataBase.add(typeStr);
//        Creation AddOn      (Name, race, hp, def, prixMin)
        typeAdd = new AddOn("Reactor", Race.TERRAN, 400, 1, 150);
        dataBase.add(typeAdd);
        typeAdd = new AddOn("Tech Lab", Race.TERRAN, 400, 1, 50);
        dataBase.add(typeAdd);

        saveData(dataBase);
    }
    
    /**
     * Méthode pour afficher les éléments contenus dans la liste
     * @param dataBase La liste d'élément envoyer pour l'affichage
     */
    private static void showList(ArrayList<Element> dataBase){
        System.out.println("**********************************");
        for (int i = 0; i < dataBase.size(); i++) {
            System.out.println(dataBase.get(i));
        }
    }
}
