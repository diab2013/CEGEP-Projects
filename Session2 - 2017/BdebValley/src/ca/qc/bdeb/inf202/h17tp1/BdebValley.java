package ca.qc.bdeb.inf202.h17tp1;

import java.util.*;

public class BdebValley {

//Gros main, car pourquoi pas .... 
    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        Ferme farm = new Ferme();
        boolean stop = false;
        int rep = 0;
        do {
            System.out.println("------------------");
            System.out.println("|     JOUR " + farm.getNombreJour() + "\t |");
            System.out.println("------------------");
            farm.getPhoto();
            if (farm.getActionsRestantesAujourdhui() == 0) {
                menu(farm);
                rep = verifierInput(rep, clavier, 1, 7);
                while (rep < 6 || rep > 7) {
                    System.out.println("Vous ne pouvez choisir ce choix car vous n'avez atteint votre nombre d'actions maximum de la journée ... ¯\\_(ツ)_/¯");
                    rep = verifierInput(rep, clavier, 6, 7);
                }
                switch (rep) {
                    case 6:
                        farm.prochaineJournee();
                        break;
                    case 7:
                        stop = true;
                        ending();
                        break;
                }
            } else {
                if(farm.getActionsRestantesAujourdhui() == 1){
                    rep = 0;
                }
                menu(farm);
                rep = verifierInput(rep, clavier, 1, 7);
                switch (rep) {
                    case 1:
                        int semence = 1;
                        double montantTotal = 0;
                        System.out.println("Vous ne pouvez acheter que 6 semences seulement!");
                        System.out.println("---------------------------------------");
                        System.out.println("|   \033[31mVous avez " + farm.getEncaisse() + "$ dans vos poches\033[0m  |");
                        System.out.println("---------------------------------------");
                        menuSemences();
                        while (farm.getSemencesAchetees() < farm.getSEMENCES_MAX_PAR_JOUR() && semence <= 5) {
                            montantTotal += acheter(farm, semence);
                            semence++;
                        }
                        if (farm.getSemencesAchetees() == farm.getSEMENCES_MAX_PAR_JOUR()) {
                            System.out.println("Vous avez atteint le nombre limites de semences.");
                        }
                        System.out.println("--------------------------------------------------");
                        System.out.println("|   \033[31mVos achats ont coutés " + montantTotal + "$ pour la journée.\033[0m  |");
                        System.out.println("--------------------------------------------------");
                        farm.setNbrActions(farm.getActionsRestantesAujourdhui() - 1);
                        break;
                    case 2:
                        if (farm.getStockTotal() > 0) {
                            int planter = 1;
                            menuSemer(farm);
                            while (planter <= 5) {
                                semer(planter, farm, clavier);
                                planter++;
                            }
                        } else {
                            System.out.println("Vous n'avez acune semence ...... ¯\\_(ツ)_/¯");
                            System.out.println("Vous venez de perdre une action .... (ಥ﹏ಥ)");
                        }
                        farm.setNbrActions(farm.getActionsRestantesAujourdhui() - 1);
                        break;
                    case 3:
                        farm.arroser();
                        System.out.println("Vous avez arroser votre ferme. ☜(⌒▽⌒)☞");
                        farm.setNbrActions(farm.getActionsRestantesAujourdhui() - 1);
                        break;
                    case 4:
                        farm.fertiliser();
                        System.out.println("Vous avez fertiliser votre ferme. ☜(⌒▽⌒)☞");
                        System.out.println("Humidité moyenne " + farm.getHumiditeMoyenne() + " par parcelle");
                        farm.setNbrActions(farm.getActionsRestantesAujourdhui() - 1);
                        break;
                    case 5:
                        farm.recolter();
                        statistiques(farm);
                        farm.setNbrActions(farm.getActionsRestantesAujourdhui() - 1);
                        break;
                    case 6:
                        farm.prochaineJournee();
                        break;
                    case 7:
                        stop = true;
                        ending();
                        break;
                }
            }
        } while (stop == false);
    }

//Menu d'affichage, pour l'organisation
    public static void statistiques(Ferme farm) {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$   TIME TO MAKE SOME MONEY!! ☜(⌒▽⌒)☞" + "  $");
        System.out.println("$       Vous avez vendu " + farm.getNombreLegumeVendus() + " légumes" + "\t  $");
        System.out.println("$     Vous avez fait " + farm.recolter() + "$, gros gars!!!" + "   $");
        System.out.println("$   Vous avez une moyenne de " + farm.getGainMoyen() + "$/jour    $");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }

    public static void menuSemences() {
        System.out.println("***********************************");
        System.out.println("* Tomate " + "\t" + "2,00$" + "\t" + "3,50$" + "\t" + "5 *");
        System.out.println("* Concombre " + "\t" + "1,00$" + "\t" + "2,50$" + "\t" + "6 *");
        System.out.println("* Salade " + "\t" + "1,50$" + "\t" + "4,50$" + "\t" + "8 *");
        System.out.println("* Patate " + "\t" + "2,50$" + "\t" + "3,50$" + "\t" + "4 *");
        System.out.println("* Betterave " + "\t" + "2,00$" + "\t" + "4,00$" + "\t" + "10*");
        System.out.println("***********************************");
    }

    public static void menuSemer(Ferme farm) {
        System.out.println("--------------------------------------");
        System.out.println("Voici vos stocks de semences actuels.");
        System.out.println("Stock de Tomates: " + farm.getStockSemencesTomate() + " semences");
        System.out.println("Stock de Concombre: " + farm.getStockSemencesConcombre() + " semences");
        System.out.println("Stock de Salade: " + farm.getStockSemencesSalade() + " semences");
        System.out.println("Stock de Patate: " + farm.getStockSemencesPatate() + " semences");
        System.out.println("Stock de Betterave: " + farm.getStockSemencesBetterave() + " semences");
        System.out.println("--------------------------------------");
    }

    public static void menu(Ferme farm) {
        if (farm.peutEffectuerActionAujourdhui() == false) {
            System.out.println("-------------------------------------------------------");
            System.out.println("Faîtes un choix parmis les suivants (" + farm.getActionsRestantesAujourdhui() + "/2)");
            System.out.println("\033[31m1- Aller acheter des semences \033[0m");
            System.out.println("\033[31m2- Semer des semences \033[0m");
            System.out.println("\033[31m3- Arroser le jardin \033[0m");
            System.out.println("\033[31m4- Fertiliser le jadin (2$) \033[0m");
            System.out.println("\033[31m5- Ceuillir et vendre les semences \033[0m");
            System.out.println("\033[32m6- Aller dormir (passe une journée) \033[0m");
            System.out.println("\033[32m7- Quitter le jeu (BYE!!!) \033[0m");
            System.out.println("-------------------------------------------------------");
        } else {
            System.out.println("-------------------------------------------------------");
            System.out.println("Faîtes un choix parmis les suivants (" + farm.getActionsRestantesAujourdhui() + "/2)");
            System.out.println("\033[32m1- Aller acheter des semences \033[0m");
            System.out.println("\033[32m2- Semer des semences \033[0m");
            System.out.println("\033[32m3- Arroser le jardin \033[0m");
            System.out.println("\033[32m4- Fertiliser le jadin (2$) \033[0m");
            System.out.println("\033[32m5- Ceuillir et vendre les semences \033[0m");
            System.out.println("\033[32m6- Aller dormir (passe une journée) \033[0m");
            System.out.println("\033[32m7- Quitter le jeu (BYE!!!) \033[0m");
            System.out.println("-------------------------------------------------------");
        }
    }

//Méthodes pour acheter et semer avec swag
    public static double acheter(Ferme farm, int semence) {
        Scanner achat = new Scanner(System.in);
        double montant = 0;
        int choix = 0;
        switch (semence) {
            case 1:
                System.out.println("Combien de tomate voulez-vous?");
                choix = verifierInput(choix, achat, 0, 6);
                while ((farm.getSemencesAchetees() + choix) > farm.getSEMENCES_MAX_PAR_JOUR()) {
                    System.out.println("Vous allez dépasser la limite de semeces.");
                    choix = verifierInput(choix, achat, 0, 6);
                }
                montant = farm.acheterSemences(Semences.TOMATE, choix);
                farm.setSemencesAchetees(farm.getSemencesAchetees() + choix);
                System.out.println("Vous avez acheter " + choix + " " + Semences.TOMATE + " pour " + montant + "$.");
                break;
            case 2:
                System.out.println("Combien de concombre voulez-vous?");
                choix = verifierInput(choix, achat, 0, 6);
                while ((farm.getSemencesAchetees() + choix) > farm.getSEMENCES_MAX_PAR_JOUR()) {
                    System.out.println("Vous allez dépasser la limite de semeces.");
                    choix = verifierInput(choix, achat, 0, 6);
                }
                montant = farm.acheterSemences(Semences.CONCOMBRE, choix);
                farm.setSemencesAchetees(farm.getSemencesAchetees() + choix);
                System.out.println("Vous avez acheter " + choix + " " + Semences.CONCOMBRE + " pour " + montant + "$.");
                break;
            case 3:
                System.out.println("Combien de salade voulez-vous?");
                choix = verifierInput(choix, achat, 0, 6);
                while ((farm.getSemencesAchetees() + choix) > farm.getSEMENCES_MAX_PAR_JOUR()) {
                    System.out.println("Vous allez dépasser la limite de semeces.");
                    choix = verifierInput(choix, achat, 0, 6);
                }
                montant = farm.acheterSemences(Semences.SALADE, choix);
                farm.setSemencesAchetees(farm.getSemencesAchetees() + choix);
                System.out.println("Vous avez acheter " + choix + " " + Semences.SALADE + " pour " + montant + "$.");
                break;
            case 4:
                System.out.println("Combien de patate voulez-vous?");
                choix = verifierInput(choix, achat, 0, 6);
                while ((farm.getSemencesAchetees() + choix) > farm.getSEMENCES_MAX_PAR_JOUR()) {
                    System.out.println("Vous allez dépasser la limite de semeces.");
                    choix = verifierInput(choix, achat, 0, 6);
                }
                montant = farm.acheterSemences(Semences.PATATE, choix);
                farm.setSemencesAchetees(farm.getSemencesAchetees() + choix);
                System.out.println("Vous avez acheter " + choix + " " + Semences.PATATE + " pour " + montant + "$.");
                break;
            case 5:
                System.out.println("Combien de betterave voulez-vous?");
                choix = verifierInput(choix, achat, 0, 6);
                while ((farm.getSemencesAchetees() + choix) > farm.getSEMENCES_MAX_PAR_JOUR()) {
                    System.out.println("Vous allez dépasser la limite de semeces.");
                    choix = verifierInput(choix, achat, 0, 6);
                }
                montant = farm.acheterSemences(Semences.BETTERAVE, choix);
                farm.setSemencesAchetees(farm.getSemencesAchetees() + choix);
                System.out.println("Vous avez acheter " + choix + " " + Semences.BETTERAVE + " pour " + montant + "$.");
                break;
        }
        return montant;
    }

    public static void semer(int planter, Ferme farm, Scanner clavier) {
        Scanner semer = new Scanner(System.in);
        int choix = -1;
        switch (planter) {
            case 1:
                if (farm.getStockSemencesTomate() > 0) {
                    System.out.println("Combien de tomates voulez vous planter?");
                    choix = verifierInput(choix, semer, 0, farm.getStockSemencesTomate());
                    while (choix > farm.getStockSemencesTomate()) {
                        System.out.println("Vous n'avez pas assez de semences");
                        choix = verifierInput(choix, semer, 0, farm.getStockSemencesTomate());
                    }
                    farm.planter(Semences.TOMATE, choix);
                } else {
                    System.out.println("Vous n'avez acune semence de type " + Semences.TOMATE);
                }
                break;
            case 2:
                if (farm.getStockSemencesConcombre() > 0) {
                    System.out.println("Combien de concombres voulez vous planter?");
                    choix = verifierInput(choix, semer, 0, farm.getStockSemencesConcombre());
                    while (choix > farm.getStockSemencesConcombre()) {
                        System.out.println("Vous n'avez pas assez de semences");
                        choix = verifierInput(choix, semer, 0, farm.getStockSemencesConcombre());
                    }
                    farm.planter(Semences.CONCOMBRE, choix);
                } else {
                    System.out.println("Vous n'avez acune semence de type " + Semences.CONCOMBRE);
                }
                break;
            case 3:
                if (farm.getStockSemencesSalade() > 0) {
                    System.out.println("Combien de salades voulez vous planter?");
                    choix = verifierInput(choix, semer, 0, farm.getStockSemencesSalade());
                    while (choix > farm.getStockSemencesSalade()) {
                        System.out.println("Vous n'avez pas assez de semences");
                        choix = verifierInput(choix, semer, 0, farm.getStockSemencesSalade());
                    }
                    farm.planter(Semences.SALADE, choix);
                } else {
                    System.out.println("Vous n'avez acune semence de type " + Semences.SALADE);
                }
                break;
            case 4:
                if (farm.getStockSemencesPatate() > 0) {
                    System.out.println("Combien de patates voulez vous planter?");
                    choix = verifierInput(choix, semer, 0, farm.getStockSemencesPatate());
                    while (choix > farm.getStockSemencesPatate()) {
                        System.out.println("Vous n'avez pas assez de semences");
                        choix = verifierInput(choix, semer, 0, farm.getStockSemencesPatate());
                    }
                    farm.planter(Semences.PATATE, choix);
                } else {
                    System.out.println("Vous n'avez acune semence de type " + Semences.PATATE);
                }
                break;
            case 5:
                if (farm.getStockSemencesBetterave() > 0) {
                    System.out.println("Combien de betteraves voulez vous planter?");
                    choix = verifierInput(choix, semer, 0, farm.getStockSemencesBetterave());
                    while (choix > farm.getStockSemencesBetterave()) {
                        System.out.println("Vous n'avez pas assez de semences");
                        choix = verifierInput(choix, semer, 0, farm.getStockSemencesBetterave());
                    }
                    farm.planter(Semences.BETTERAVE, choix);
                } else {
                    System.out.println("Vous n'avez acune semence de type " + Semences.BETTERAVE);
                }
                break;
        }
    }

//Méthode pour vérifier le input du user 
    public static int verifierInput(int rep, Scanner clavier, int min, int max) {
        do {
            try {
                rep = Integer.parseInt(clavier.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrez un chiffre valide");
            }
            if (rep < min || rep > max) {
                System.out.println("Entre un chiffre entre " + min + " et " + max);
            }
        } while (rep < min || rep > max);
        return rep;
    }

//Méthode troll pour bien finir :D
    public static void ending() {
        System.out.println("FAITES UN ZOOM OUT");
        System.out.println("                                                                                                                                            #           \n"
                + "                                                                                       ++                                                   +#          \n"
                + "                                                                                     +'++                                                   '+          \n"
                + "                                                                                     ;:;+                                                  #+'          \n"
                + "                                                                                    ++:+;+;::                                             #+#+#         \n"
                + "                                                                                   +,,;+:;:;;                                             ++#++#        \n"
                + "        +:+#                                                                       +;::;;:::;                                            #'###'#        \n"
                + "        #'++#        ##                                                            ;;:::;:;:;                                           ##+##++#        \n"
                + "        ##+#++     ++++#                                                           ;;;:::::'+                                           #+##++#         \n"
                + "         #+##+#   #++++                                                            +'::::::+                                            #+###+#         \n"
                + "          ++###   #+++#                                                              +';+'+                                              ##+##          \n"
                + "          #+###    ###                                                              +,::,+                                               #++#           \n"
                + "    #+'+##  ##+ +:,,,,,,,,,,  ::,,,,,,,,,,,,,,+     +:,,:+        ++':++++++        +:,,,+            ::::,',,,,,,,,,,  +:,,+         ++++         +,,,:\n"
                + "  ##+#+++#    ++,,::::::::,:  ':''::''''::::::+     ':::,+        '+::+':,,:+     +++'::,+++++++     ;':. ,':::::::::,  +:::+         :,,,         +::,:\n"
                + "#+'+####+### +,::::::::::,,: ;'::::'::::':::::+    +:: :,+       ;'+. ,+:::::,    +:+';.,+':::,,'+   ;':':,':::::::::,  +'::,+       ;:: ,        +:::,+\n"
                + "####''+#+#+# +:::::,,::::::, ;++:::':: :''::::+    +::.:,+       ;'+;.,+::::,:+   +:+'':,+::::::,'   ;':::,'::::::::,:  ;+::,+       ;:'.,+       +:::,+\n"
                + "    #+'+###++'::::++++++++++ ;++++++:'::+++++'+   ++::::+'       ;++::,++++:::,:  +:+'::,+':::':::+  ;':::,+++++++++++  ;+:::,       ++:::'+      ::::+ \n"
                + "     #+#####+:::::+++;;;;;;; ;;;;;;;::::++++++'   +':::,+'       ;;+::,+;;++:::,  +++'::,+++'::'::+  ;''::,+;;;;;;;;;;  ;;:::,+     +++::::+     +::::+ \n"
                + "       ###  +::':,;;               ;:::'+;;;;;    +::::,':+       ;+:',+ ;;+'::,  ;;+'::,';;;+:::::+ ;+:::,+             ;+:::+     +:'+::,'     +:::,  \n"
                + "            +':::,                 ;::::+         +:::,:':+       ;+:+,+  ;+:::,  ;;+'::,';;;;::::,+ ;+:::,+             ;+:':'     +:'+::,:    ;+:::,  \n"
                + "            +':::,                 ;::::+         ':::,+:::+      ;+::,+  +''::,    +'::,'   ;+'::,+ ;':::,+              +'::,+    ::'':':,    +:::,+  \n"
                + "            +''::,'                ;::::+        ;:::::+:::+      ;+::,+''':::::    +'::,'    ;+::,' ;':::,+              ;+:','   ;:::':::,+   +::::+  \n"
                + "            ;+::::,:++++           ;':::+        +::::+'::,'      ;+';,+::'':::+    +'::,'    ;+:::, ;':::,+              ;+:::,   +:::'':::+   ::::+   \n"
                + "            ;+::::::,,:'++         ;::::+        +'::,+'::,:      ;+'',+::::::+     +'::,'    ;+::,, ;':::,+++++++++      ;;:::,  ;+:::''::::+ ;::::+   \n"
                + "            ;;'::::':'':,,+        ;::::+       +':::,;;:::,+     ;+::,+'''++;      +'::,'    ;+:::, ;':;.,'::::::,+       ;':::+ +::::+;+::,+ +:::,    \n"
                + "             ;+':::::::'::+        ;:::'+      +++':::;;':::+     ;;::,+++++;;      +'::,'    ;+:::, ;':':,'::::::,+       ;+:::+ +::::+;+::,:;+:::,    \n"
                + "              ;;;++'::::':'+       ;:::'+     +:::,,,,,,,,:''+++   ;::,+':::+       +'::,'    ;+:::, ;':::,':::'':'+        +':::+':::+ ;;:::,+::',+    \n"
                + "              ;;;;;+'::::::+       ;::,++     +':.::::::::,,,:'+   ;::,+':::'+      +':,,'    ;+:::, ;':::,'++++++++        ;+::,'::::+ ;;:::,+:':,+    \n"
                + "                  ;;;+::::,+       ;::,++     ++:':::::::::'.:+    +::,++'::,'      +':,,'    ;+:::, ;':::,+;;;;;;;         ;+::,+:'::   ;+::'::':+     \n"
                + "                    ;;::::,+       ;::,'+     ++++++:':::::'::++   +::,+++::,:      +':,,'    ;+::,' ;':::,+                ;;:::+:':'   ;+::+::::+     \n"
                + "           +++       ;':::,+       ;:::'+     ;+''''++++++++++++  ;+::,+;+:::,+     +'::,'    +:::,+ ;':::,+                 ;+:++'::+    ;+++:::,      \n"
                + "           +''+      +::::,+       ;::::+      +::::+;;;;;'''+++  ;+::,+;;':::++   ++'::,++   ::::'+ ;':::,+                 ;+:+'::'+    ;++':::,      \n"
                + "          ++:,:'''''':::::++       ;':::+     +'::,+     ;+:::,+  ;+::,+ ;+:::,+   ++'::,+'::::':,+  ;':::,+                  ++':::+     ;;+:::,+      \n"
                + "          +'::,,,,,,,,::::+        ;':::+     +'::,+     ;+'::,'  ;+::,+ ;+'::,:  ;++'. ,+::::'::,   ;':::,+++++++++++        ;+::::+     ;;+:::,+      \n"
                + "          +'':::::::::::::         ;::::+     ':::'+      ;+:::,  ;+::,+  ;+:::,+ ;++'':,+:::::::+   ;':. ,+::::::::,:        ;+'.::       ;:: :+       \n"
                + "          +'::'::::::::::+         ;::::+     ':::+       ;+:::,+ ;+::,+  ;;::::+ ;++'::,+''':::+;   ;':;.,+::::::::,:        ;+';::       ;:'.:+       \n"
                + "          ;;+'':::::::'+;          ;'::'+    ;++''        ;;'::'+ ;+:''+   ;+:::,+;++''::++++++;     ;':::,+:'++':::,:        ;+:::+       ;''''        \n"
                + "          ;;;++++++++++;           ;;++;+    ;;;++         ;''++  ;+':+    ;+':::+;;++++++;;;;;;     ;'::::+':,,::::::        ;;''++       ;++++        \n"
                + "            ;;;;;;;;;;             ;;; ;       ;;;         ;;;;   ;;;;      ;;;;;;  ;;;;;            ;;;;;;;;;;;;;;;;;         ;;;;         ;;;;        \n"
                + "                                                            ;;     ;;;      ;;;;;;                   ;;;;;;;;;;;;;;;;;          ;;                      \n"
                + "                                                                                                                                                        \n"
                + "                                                                                                                                                        \n"
                + "                        ++++         +,,,+      +:,,:+         +,,,,+           +++++             +::::,:,,,,,,,,,+   :,,,+      +,,,,                  \n"
                + "                        :,,,         +::,+      ':::,+        ;+::::+           :,,,+             +':.:,::::::::::+  ;::::+     +':::,                  \n"
                + "                       ;:::,        +::::+     +:: :,+        ;+::::+          ;::::,+            +''':,::::::::::+  ;+::::+    +:::,+                  \n"
                + "                       ;:::,+       +:::+      +::.:,+        ;+:::,+          ;::::,+            +'::,,:::::::::,+  ;+:::,+    ':':,+                  \n"
                + "                       ;+:':+       :::,      ;+::::+'        ;+':::+          ;':::,+            +':::,+++++++++++   ;+::,:   ;::::++####              \n"
                + "                       ;+:'::+     ;:::,      +':::,+'        ;+:':,+          ;':::,+            +'::,,;;;;;;;;;;    ;+:::,   +::::+ ####              \n"
                + "                        ;+::,+     +::,+      +::::,':+       ;+'::,+          ;+:::,+            ++::,,              ;;:::,+ ;+'+:,  +###'#            \n"
                + "                        ;+::,:    ;+::,+      +:::,:':+       ;+::::+           +:::,+            ++:::,              ;;::::+ +'+',:  ++##+'            \n"
                + "                     #++#;:::,    +:::+       ':::,+:::+      ;+:':,+           +::::,            +'':,,               ;+:::,++:::,+   #++#+#           \n"
                + "                    ##+##;:::,+   +::,+      ;:::::+:::+      ;+:':,+           +'::,,            +'':,,               ;+:::,+'::::+    +'+##           \n"
                + "                   #+###+#+:::+   :::,       +::::+'::,+      ;+::::+           ;+:::,            +':::,                ;+:::,::::+       ###           \n"
                + "                   #++##+#+::::+ ;:::,       +'::,+'::,:      ;+::::+           ;+'::,            +':::,++++++++        ;+:::::::,+        ##           \n"
                + "                  #++##+##;+:',+ +::,+      +':::,;;:::,+     ;+::::+           ;+:::,            +'';:,:::::::,        ;;::':::,:                      \n"
                + "                  #'###'# ;+::,:;+::,+     +++':::;;':::+     ;+::::+           ;+:::,            +''':,::::::,,        ;;::''::,:                      \n"
                + "                  #++#++  ;;:':,+::::+    +:::,,,,,,,,:''+++  ;+:::,+           ;+:::,            +'::,,':::''''         ;+:::::,+                      \n"
                + "                   #+#+#  ;;::::+:::+     +':.::::::::,,,:'+  ;+:::,+           ;+:::,+           +'::,,++++++++         ;+':':::+                      \n"
                + "                    '+#    ;+:::::::+     +':':::::::::'.:+   ;+:::,+           ;;:::,+           +'::,,;;;;;;;;          ;+::::+                       \n"
                + "                    +'     ;+::':::,+     ++:::':':::::'::+   ;+::::+            ;:'::+           +':::,                  ;+:::,+                       \n"
                + "                    #+      ;+'+:':,      ;+''''+++++++++':+  ;+:::,+            ;:':,+           +'::,,                  ;+:::,+                       \n"
                + "                     #      ;++':':,       +::::+;;;;;'''+++  ;+:':,+            ;:':,+           +'':,,                  ;+':::+                       \n"
                + "                            ;;+:':::      +'::,'     ;+:::,+  ;+::::+            ;:':,+           +':::,                  ;;':::+                       \n"
                + "                            ;;+::::'      +'::,+     ;+'::,+  ;+:'::+++++++++++  ;::::+++++++++++ +''::,++++++++++         +::::+                       \n"
                + "                             ;:: ::+      ':::'+      ;+:::,  ;+:: :+:::::::,:,+ ;::::':::::::,:+ +':.:,:::::::::,+       ;+':::+                       \n"
                + "                             ;:'.:'+      ':::+       ;+:::,+ ;+:'.:+:::::::::,+ ;':. :':::::::,+ +'';:,:::::::::,+       ;+':::+                       \n"
                + "                             ;':::+      ;++::        ;;:::'+ ;+::::+''+++::::,+ ;+:'::+::::::::+ +':::,':+++':::,+       ;+'::,+                       \n"
                + "                             ;++++       ;;;++         ;''++  ;+:'::+'::,::::::+ ;+':::+::::::,,+ +'':::'':,::::::+       ;+:',:+                       \n"
                + "                              ;;;;         ;;;         ;;;;   ;;;;;;;;;;;;;;;;;   ;;;;;;;;;;;;;;  ;;;;;;;;;;;;;;;;        ;;;;;;                        \n"
                + "                                                        ;;    ;;;;;;;;;;;;;;;;;   ;;;;;;;;;;;;;;  ;;;;;;;;;;;;;;;;        ;;;;;;                        ");
    }
}
