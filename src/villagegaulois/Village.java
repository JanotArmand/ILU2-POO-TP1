package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	 public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		 StringBuilder chaine = new StringBuilder();
		 chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+".\n");
		 int indiceEtal = marche.trouverEtalLibre();
		 if (indiceEtal!=-1) {
			 marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			 chaine.append(marche.etals[indiceEtal].afficherEtal());
		 }
		 return chaine.toString();
	 }
	 
	 public String rechercherVendeursProduit(String produit) {
			StringBuilder chaine = new StringBuilder();
			Etal[] etalsOccupes = marche.trouverEtals(produit);
			if (etalsOccupes.length==0) {
				chaine.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché.");
			} else if (etalsOccupes.length==1){
				chaine.append("Seul le vendeur "+etalsOccupes[0].getVendeur().getNom()+" propose des "+produit+" au marché.");
			} else {
				chaine.append("Les vendeurs qui proposent des "+produit+" sont :\n");
				for (int i=0;i<etalsOccupes.length;i++) {
					chaine.append("- "+etalsOccupes[i].getVendeur().getNom()+"\n");
				}
			}
			return chaine.toString();
		}
	 
	 public Etal rechercherEtal(Gaulois vendeur) {
			return marche.trouverVendeur(vendeur);
		}
		
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
		chaine.append(etal.libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \""+nom+"\" possède plusieurs étals :\n");
		Etal[] etals =marche.etals;
		int etal=0;
		int etalOccupe=0;
		while (etal<etals.length) {
			if (etals[etal].isEtalOccupe()) {
				chaine.append(etals[etal].afficherEtal());
				etalOccupe+=1;
			}
			etal+=1;
		}
		int etalRes=etals.length-etalOccupe;
		if (etalRes>0) {
			chaine.append("Il reste " + etalRes + " étals non utilisées dans le marché.");
		}
		
		return chaine.toString();
	}

		
	
	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			
			for (int i=0;i<nbEtals;i++) {
				etals[i]=new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			int etal=0;
			while ((etal<etals.length)&&(etals[etal].isEtalOccupe())) {
				etal+=1;
			}
			if (etals[etal].isEtalOccupe()) {
				return -1;
			} else {
				return etal;
			}
		}
		
		private Etal[] trouverEtals(String produit) {
			int etal = 0;
			int nbEtals = 0;
			while (etal<etals.length) {
				if ((etals[etal].isEtalOccupe())&&(etals[etal].contientProduit(produit))) {
					nbEtals+=1;
				}
				etal+=1;
			}
			Etal[] etalsOccupes = new Etal[nbEtals];
			etal=0;
			nbEtals=0;
			while (etal<etals.length) {
				if ((etals[etal].isEtalOccupe())&&(etals[etal].contientProduit(produit))) {
					etalsOccupes[nbEtals]=etals[etal];
					nbEtals+=1;
				}
				etal+=1;
			}
			return etalsOccupes;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			int etal = 0;
			while (etal<etals.length) {
				if (etals[etal].getVendeur()==gaulois) {
					return etals[etal];
				}
				etal+=1;
			}
			return null;
		}
		
		private void afficherMarche() {
			int etal=0;
			while (etal<etals.length) {
				if (etals[etal].isEtalOccupe()) {
					etals[etal].afficherEtal();
				}
				etal+=1;
			}
			int etalsRes=etals.length-etal;
			if (etalsRes>0) {
				System.out.println("Il reste " + etalsRes + " étals non utilisées dans le marché.");
			}
		}
	}
}

























