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
			Etal[] etalsOccupes = null;
			int etal = 0;
			int nbEtals = 0;
			while (etal<etals.length) {
				if (etals[etal].isEtalOccupe()) {
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

























